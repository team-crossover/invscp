package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.OSsituacaoEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;

public class OrdemServico implements Model<OrdemServicoDTO> {

    private static final long serialVersionUID = 5301908503322503604L;

    private Integer id = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataConclusao = null;

    private Float valor = null;

    private OSsituacaoEnum situacao = null;

    private Integer idFuncionario = null;

    private Integer idBem = null;

    @Override
    public void setValuesFromDTO(OrdemServicoDTO dto) {
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
        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setDataCadastro(dataCadastro);
        dto.setDataConclusao(dataConclusao);
        dto.setId(id);
        dto.setValor(valor);
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

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idOs) throws IllegalDeleteException {
        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idOs == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover ordens de serviço sem informar o ID.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        final OrdemServico os = osRepo.getById(idOs);

        if (os == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar a ordem de serviço desejada.");
        }

        // CONTROLE DE ACESSO
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(os.getIdBem());
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final Departamento dept = deptRepo.getById(bem.getIdDepartamento());
        final FuncionarioRepository funRepo = new FuncionarioRepository();
        final Funcionario func = funRepo.getById(os.getIdFuncionario());
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
            if (!usuario.getDepartamento().equals(dept)) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item");
            }
            if (!usuario.getId().equals(func.getId())) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item em nome de outro funcionário");
            }
        }

        // VALIDADE DE DADOS
        if (os.isPendente()) {
            throw new IllegalDeleteException(
                    "Não é possível deletar a ordem de serviço pois ela ainda não foi concluída");
        }

    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final OrdemServicoDTO novaOs) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();

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

        // VALIDADE DE DADOS?

        try {
            validarCampos(novaOs);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    // Tem isso aqui?
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
        BemRepository bemRepo = new BemRepository();
        Bem bem = bemRepo.getById(antigaOs.getIdBem());
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
        // TODO se o bem n está em movimentação
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.getByBem(bem).size() > 0) {
            throw new IllegalUpdateException(
                    "Não é possível criar uma ordem de serviço para o bem "
                            + bem.getDescricao() + " pois ele já foi baixado.");
        }

        if (osRepo.getByBem(bemRepo.getById(antigaOs.getIdBem())).size() > 0) {
            throw new IllegalUpdateException(
                    "Não é possível criar uma ordem de serviço para o bem "
                            + bem.getDescricao()
                            + " pois ele possui ordens de serviço pendentes relaciondas a ele.");
        }

        try {
            validarCampos(novaOs);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    private static void validarCampos(OrdemServicoDTO os) throws CRUDException {
        // Definido pelo sistema
        if (os.getBem() == null) {
            throw new CRUDException("O 'Bem' selecionado não existe.");
        }
        // Definida pelo sistema - criação da OS
        if (os.getDataCadastro() == null) {
            throw new CRUDException(
                    "'Data de cadastro' é um campo obrigatório.");
        }
        // Definido pelo sistema - autor da operação
        if (os.getFuncionario() == null) {
            throw new CRUDException("O 'Funcionário' selecionado não existe.");
        }
        // Situação é definida pelo sistema
        // if (os.getSituacao() == null) {
        // throw new CRUDException("A 'Situação' deve ser definida.");
        // }
        if (os.getValor() == null) {
            throw new CRUDException("'Valor' é um campo obrigatório.");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public final OSsituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(OSsituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final String getSituacaoString() {
        return this.situacao.toString();
    }

    public final void setSituacaoString(final String sit) {
        this.situacao = OSsituacaoEnum.valueOf(sit);
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

    public Boolean isPendente() {
        return situacao.equals(OSsituacaoEnum.PENDENTE);
    }

    public void concluir() {
        this.situacao = OSsituacaoEnum.CONCLUIDA;
    }

}
