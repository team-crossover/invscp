package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.enums.MotivoBaixaEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
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
    public void setValuesFromDTO(BaixaDTO dto) {
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
            final Bem bem = repo.getById(idFuncionario);
            bem.setIdDepartamento(null);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        return dto;
    }

    // Isso existe?
    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idBaixa) throws IllegalDeleteException {
        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idBaixa == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover bens sem informar o ID.");
        }

        final BaixaRepository baixaRepo = new BaixaRepository();
        final Baixa baixa = baixaRepo.getById(idBaixa);

        if (baixa == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar a baixa desejado.");
        }

        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(baixa.getIdBem());
        final FuncionarioRepository funRepo = new FuncionarioRepository();
        final Funcionario func = funRepo.getById(baixa.getIdFuncionario());
        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalDeleteException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalDeleteException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item");
            }
            if (!usuario.getDepartamento().getId()
                    .equals(bem.getIdDepartamento())) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item");
            }
            if (!usuario.getId().equals(func.getId())) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item em nome de outro funcionário");
            }
        }

        // VALIDADE DE DADOS
        // TODO se o bem não está em movimentação
        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.getByBem(bem).size() > 0) {
            throw new IllegalDeleteException("Não é possível baixar o bem "
                    + bem.getDescricao()
                    + " pois ele possui ordens de serviço pendentes relaciondas a ele.");
        }
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
        // TODO se o bem não está em movimentação
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(novaBaixa.getBem().getId());
        if (baixaRepo.getByBem(bem).size() > 0) {
            throw new IllegalInsertException("Não é possível baixar o bem "
                    + bem.getDescricao() + " pois ele já foi baixado.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.getByBem(bem).size() > 0) {
            throw new IllegalInsertException("Não é possível baixar o bem "
                    + bem.getDescricao()
                    + " pois ele possui ordens de serviço pendentes relaciondas a ele.");
        }

        try {
            validarCampos(novaBaixa);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    private static void validarCampos(BaixaDTO baixa) throws CRUDException {
        if (baixa.getBem() == null) {
            throw new CRUDException("O 'Bem' selecionado não existe.");
        }
        // Deve ser definida pelo sistema
        if (baixa.getData() == null) {
            throw new CRUDException("'Data' é um campo obrigatório.");
        }
        // Definido pelo sistema - o autor da baixa
        if (baixa.getFuncionario() == null) {
            throw new CRUDException("O 'Funcionário' selecionado não existe.");
        }
        if (baixa.getMotivo() == null) {
            throw new CRUDException("'Motivo' é um campo obrigatório.");

        }
        // É um campo obrigatório mesmo?
        if (baixa.getObservacoes() == null
                || baixa.getObservacoes().isEmpty()) {
            throw new CRUDException("'Observações' é um campo obrigatório.");
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public MotivoBaixaEnum getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoBaixaEnum motivo) {
        this.motivo = motivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(Integer idBem) {
        this.idBem = idBem;
    }

}
