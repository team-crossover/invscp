package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.model.enums.MotivoBaixaEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;

public class Baixa implements Model<BaixaDTO> {

    private static final long serialVersionUID = 3942513871242698053L;

    private Integer id = null;

    private LocalDate data = null;

    private MotivoBaixaEnum motivo = null;

    private String observacoes = null;

    private Integer idFuncionario = null;

    private Integer idBem = null;

    @Override
    public void setValuesFromDTO(final BaixaDTO dto) {
        setData(dto.getData());
        setId(dto.getId());
        setMotivo(dto.getMotivo());
        setObservacoes(dto.getObservacoes());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getFuncionario() != null) {
            setIdFuncionario(dto.getFuncionario().getId());
        }
    }

    @Override
    public BaixaDTO toDTO() {
        final BaixaDTO dto = new BaixaDTO();
        dto.setData(data);
        dto.setId(id);
        dto.setMotivo(motivo);
        dto.setObservacoes(observacoes);
        if (idFuncionario != null) {
            final FuncionarioRepository repo = new FuncionarioRepository();
            final Funcionario func = repo.getById(idFuncionario);
            func.setIdDepartamento(null);
            dto.setFuncionario(func == null ? null : func.toDTO());
        }
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            bem.setIdDepartamento(null);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        return dto;
    }

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idBaixa) throws IllegalDeleteException {

        throw new IllegalDeleteException("Não é possível deletar baixas.");
    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigoBaixa, final BaixaDTO novoBaixa)
            throws IllegalUpdateException {

        throw new IllegalUpdateException("Não é possível alterar baixas.");
    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final BaixaDTO novaBaixa) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final BaixaRepository baixaRepo = new BaixaRepository();

        if (novaBaixa.getId() != null) {
            final Baixa baixaExistente = baixaRepo.getById(novaBaixa.getId());
            if (baixaExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir a sala pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalInsertException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalInsertException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalInsertException(
                        "Você não tem permissão para baixar bens");
            }
            if (!usuario.getDepartamento()
                    .equals(novaBaixa.getBem().getDepartamento())) {
                throw new IllegalInsertException(
                        "Você não tem permissão para baixar bens");
            }
            if (!usuario.equals(novaBaixa.getFuncionario())) {
                throw new IllegalInsertException(
                        "Você não tem permissão para baixar este item em nome de outro funcionário");
            }
        }

        // VALIDADE DE DADOS
        final BemRepository bemRepo = new BemRepository();
        final Integer idDoBem = bemRepo.getById(novaBaixa.getBem().getId())
                .getId();
        if (baixaRepo.existsBemBaixado(idDoBem)) {
            throw new IllegalInsertException(
                    "Não é possível baixar o bem pois ele já foi baixado.");
        }
        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.existsBemPendente(idDoBem)) {
            throw new IllegalInsertException(
                    "Não é possível baixar o bem pois ele possui uma ordem de serviço pendentes relacionda a ele.");
        }
        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        if (movRepo.existsBemInMov(idDoBem)) {
            throw new IllegalInsertException(
                    "Não é possível baixar o bem pois ele possui uma movimentação pendente relacionda a ele.");
        }

        try {
            validarCampos(novaBaixa);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    private static void validarCampos(final BaixaDTO baixa)
            throws CRUDException {
        if (baixa.getBem() == null) {
            throw new CRUDException("O 'Bem' não pode ser vazio.");
        }
        // Deve ser definida pelo sistema
        if (baixa.getData() == null) {
            throw new CRUDException("'Data' é um campo obrigatório.");
        }
        // Definido pelo sistema - o autor da baixa
        if (baixa.getFuncionario() == null) {
            throw new CRUDException("O 'Funcionário' não pode ser vazio.");
        }
        if (baixa.getMotivo() == null) {
            throw new CRUDException("'Motivo' é um campo obrigatório.");

        }

    }

    public static List<String> posInserir(final FuncionarioDTO usuario,
            final BaixaDTO baixa) {

        final List<String> messages = new ArrayList<String>();
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(baixa.getBem().getId());

        // A situação do bem deve ser alterada para BAIXADO após criar a baixa
        // dele.
        bem.setSituacao(BemSituacaoEnum.BAIXADO);
        bemRepo.update(bem);
        messages.add("O bem " + bem.getDescricao() + " está agora 'baixado'.");

        return messages;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(final LocalDate data) {
        this.data = data;
    }

    public MotivoBaixaEnum getMotivo() {
        return motivo;
    }

    public void setMotivo(final MotivoBaixaEnum motivo) {
        this.motivo = motivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(final Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(final Integer idBem) {
        this.idBem = idBem;
    }

}
