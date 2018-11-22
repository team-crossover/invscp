package com.github.nelsonwilliam.invscp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.model.enums.OSSituacaoEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;

public class OrdemServico implements Model<OrdemServicoDTO> {

    private static final long serialVersionUID = 5301908503322503604L;

    private Integer id = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataConclusao = null;

    private BigDecimal valor = null;

    private OSSituacaoEnum situacao = null;

    private Integer idFuncionario = null;

    private Integer idBem = null;

    @Override
    public void setValuesFromDTO(final OrdemServicoDTO dto) {
        setDataCadastro(dto.getDataCadastro());
        setDataConclusao(dto.getDataConclusao());
        setId(dto.getId());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getFuncionario() != null) {
            setIdFuncionario(dto.getFuncionario().getId());
        }
        setSituacao(dto.getSituacao());
        setValor(dto.getValor());

    }

    @Override
    public OrdemServicoDTO toDTO() {
        final OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setDataCadastro(dataCadastro);
        dto.setDataConclusao(dataConclusao);
        dto.setId(id);
        dto.setValor(valor);
        dto.setSituacao(situacao);
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
            final Integer idOS) throws IllegalDeleteException {

        throw new IllegalDeleteException(
                "Não é possível deletar ordens de serviço.");
    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final OrdemServicoDTO novaOs) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        final BemRepository bemRepo = new BemRepository();
        final Integer idBem = bemRepo.getById(novaOs.getBem().getId()).getId();

        if (novaOs.getId() != null) {
            final OrdemServico osExistente = osRepo.getById(novaOs.getId());
            if (osExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir a ordem de serviço pois o ID já existe.");
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
                        "Você não tem permissão para inserir este item");
            }
            if (!usuario.getDepartamento()
                    .equals(novaOs.getBem().getDepartamento())) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir este item");
            }
            if (!usuario.equals(novaOs.getFuncionario())) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir este item em nome de outro funcionário");
            }
        }

        // VALIDADE DE DADOS
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.existsBemBaixado(idBem)) {
            throw new IllegalInsertException(
                    "Não é possível criar uma ordem de serviço para este bem pois ele já foi baixado.");
        }
        if (osRepo.existsBemPendente(idBem)) {
            throw new IllegalInsertException(
                    "Não é possível criar uma ordem de serviço para este bem pois ele possui já uma ordem de serviço pendente relacionda a ele.");
        }
        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        if (movRepo.existsBemInMov(idBem)) {
            throw new IllegalInsertException(
                    "Não é possível criar uma ordem de serviço para este bem pois ele possui uma movimentação pendente relacionda a ele.");
        }

        try {
            validarCampos(novaOs);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaOs, final OrdemServicoDTO novaOs)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigaOs == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma Ordem de Serviço sem seu ID.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        final OrdemServico antigaOs = osRepo.getById(idAntigaOs);

        if (antigaOs == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma Ordem de Serviço inexistente.");
        }

        if (antigaOs.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é remover o ID da Ordem de Serviço.");
        }

        if (!antigaOs.getId().equals(novaOs.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID da Ordem de Serviço.");
        }

        // CONTROLE DE ACESSO

        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(antigaOs.getIdBem());
        if (usuario == null) {
            throw new IllegalUpdateException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalUpdateException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar este item");
            }
            if (!usuario.getDepartamento().getId()
                    .equals(bem.getIdDepartamento())) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar este item");
            }
            if (!usuario.equals(novaOs.getFuncionario())) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar este item em nome de outro funcionário");
            }
        }

        // VALIDADE DE DADOS
        if (antigaOs.getSituacao() == OSSituacaoEnum.CONCLUIDA) {
            throw new IllegalUpdateException(
                    "Não é permitido fazer alterações em ordens de serviço já concluídas.");
        }

        try {
            validarCampos(novaOs);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    private static void validarCampos(final OrdemServicoDTO os)
            throws CRUDException {
        // Definido pelo sistema
        if (os.getBem() == null) {
            throw new CRUDException("O 'Bem' não pode ser vazio.");
        }
        // Definida pelo sistema - criação da OS
        if (os.getDataCadastro() == null) {
            throw new CRUDException(
                    "'Data de cadastro' é um campo obrigatório.");
        }
        // Definido pelo sistema - autor da operação
        if (os.getFuncionario() == null) {
            throw new CRUDException("O 'Funcionário' não pode ser vazio.");
        }
        // Situação é definida pelo sistema
        if (os.getSituacao() == null) {
            throw new CRUDException("A 'Situação' deve ser definida.");
        }
        if (os.getValor() == null) {
            throw new CRUDException("'Valor' é um campo obrigatório.");
        }
    }

    public static List<String> posInserir(final FuncionarioDTO usuario,
            final OrdemServicoDTO ordem) {

        final List<String> messages = new ArrayList<String>();
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(ordem.getBem().getId());

        // A situação do bem deve ser alterada para EM CONSERTO após criar a
        // baixa dele.
        bem.setSituacao(BemSituacaoEnum.EM_CONSERTO);
        bemRepo.update(bem);
        messages.add(
                "O bem " + bem.getDescricao() + " está agora 'Em conserto'.");

        return messages;
    }

    public static List<String> posConcluir(final FuncionarioDTO usuario,
            final OrdemServicoDTO ordem) {

        final List<String> messages = new ArrayList<String>();
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(ordem.getBem().getId());

        // A situação do bem deve ser alterada de volta pra INCORPORADO após
        // concluir a ordem de serviço.
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bemRepo.update(bem);
        messages.add("O bem " + bem.getDescricao()
                + " não está mais 'em conserto'.");

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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(final LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(final LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    public final OSSituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(final OSSituacaoEnum situacao) {
        this.situacao = situacao;
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

    public Boolean isPendente() {
        return situacao.equals(OSSituacaoEnum.PENDENTE);
    }

    public void concluir() {
        situacao = OSSituacaoEnum.CONCLUIDA;
    }

}
