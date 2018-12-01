package com.github.nelsonwilliam.invscp.server.model;

import com.github.nelsonwilliam.invscp.server.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.shared.exception.CRUDException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.CargoEnum;

public class Funcionario implements Model<FuncionarioDTO> {

    private static final long serialVersionUID = 5924596504372549531L;

    private Integer id = null;

    private String login = null;

    private String senha = null;

    private String nome = null;

    private String cpf = null;

    private String email = null;

    private Integer idDepartamento = null;

    @Override
    public void setValuesFromDTO(final FuncionarioDTO dto) {
        setId(dto.getId());
        setLogin(dto.getLogin());
        setSenha(dto.getSenha());
        setNome(dto.getNome());
        setCpf(dto.getCpf());
        setEmail(dto.getEmail());
        if (dto.getDepartamento() != null) {
            setIdDepartamento(dto.getDepartamento().getId());
        }
    }

    @Override
    public FuncionarioDTO toDTO() {
        final FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(id);
        dto.setLogin(login);
        dto.setSenha(senha);
        dto.setNome(nome);
        dto.setCpf(cpf);
        dto.setEmail(email);
        dto.setCargo(getCargo());
        if (idDepartamento != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(idDepartamento);
            dto.setDepartamento(dept == null ? null : dept.toDTO());
        }
        return dto;
    }

    /**
     * Verifica se o elemento a ser deletado é válido, de acordo com as regras
     * de negócio. Caso seja válido, nada acontece. Caso não seja válido, é
     * lançada uma exeção com a mensagem de erro explicando o motivo. É
     * importante ressaltar que este método NÃO deleta o novo elemento, apenas
     * verifica a possibilidade de deletá-lo.
     *
     * @param usuario Funcionário autenticado que está tentando efetuar a
     *        operação.
     * @param idFunc ID do funcionário a ser deletado.
     * @throws IllegalDeleteException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idFunc) throws IllegalDeleteException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idFunc == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover funcionários sem informar o ID.");
        }

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = funcRepo.getById(idFunc);

        if (func == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar o funcionário desejado.");
        }

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar funcionários.");
            } else if (usuario.getDepartamento().getId() != func
                    .getIdDepartamento()) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar funcionários fora do seu departamento.");
            }
        }

        // -----------------
        // VALIDADE DE DADOS
        // -----------------
        // Verificar validade dos dados (Os novos atributos do elemento são
        // válidos? Há itens que não podem ser modificados? Há itens
        // repetidos/duplicados/já utilizados?Há itens obrigatórios faltando?
        // Etc).

        if (func.getCargo().isChefe()) {
            throw new IllegalDeleteException("O funcionário " + func.getNome()
                    + " não pode ser deletado pois é chefe do departamento "
                    + func.getDepartamento().getNome() + ".");
        }
    }

    /**
     * Verifica se o elemento a ser inserido é válido, de acordo com as regras
     * de negócio. Caso seja válido, nada acontece. Caso não seja válido, é
     * lançada uma exeção com a mensagem de erro explicando o motivo. É
     * importante ressaltar que este método NÃO insere o novo elemento, apenas
     * verifica a possibilidade de inserí-lo.
     *
     * @param usuario Funcionário autenticado que está tentando efetuar a
     *        operação.
     * @param novoFunc Funcionário a ser inserido. Se o ID for nulo, um novo
     *        valor será gerado.
     * @throws IllegalInsertException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarInserir(final FuncionarioDTO usuario,
            final FuncionarioDTO novoFunc) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final FuncionarioRepository funcRepo = new FuncionarioRepository();

        if (novoFunc.getId() != null) {
            final Funcionario funcExistente = funcRepo
                    .getById(novoFunc.getId());
            if (funcExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir o funcionário pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir funcionários.");
            } else if (usuario.getDepartamento().getId() != novoFunc
                    .getDepartamento().getId()) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir funcionários fora do seu departamento.");
            }
        }

        try {
            validarCampos(novoFunc);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    /**
     * Verifica se determinado elemento pode ser atualizado para os novos
     * valores desejados, de acordo com as regras de negócio. Caso seja válido,
     * nada acontece. Caso não seja válido, é lançada uma exeção com a mensagem
     * de erro explicando o motivo. É importante ressaltar que este método NÃO
     * altera o elemento, apenas verifica a possibilidade de alterá-lo.
     *
     * @param usuario Funcionário autenticado que está tentando efetuar a
     *        operação.
     * @param idAntigoFunc ID do funcionário a ser alterado. O ID não pode ser
     *        nulo, pois a sala deve existir atualmente.
     * @param novoFunc Sala nova, com os novos dados desejados. O ID deve ser o
     *        mesmo do anterior.
     * @throws IllegalUpdateException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigoFunc, final FuncionarioDTO novoFunc)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigoFunc == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um funcionário sem seu ID.");
        }

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario antigoFunc = funcRepo.getById(idAntigoFunc);

        if (antigoFunc == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um funcionário inexistente.");
        }

        if (novoFunc.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é remover o ID do funcionário.");
        }

        if (!antigoFunc.getId().equals(novoFunc.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID do funcionário.");
        }

        final boolean eraChefe = antigoFunc.getCargo().isChefe();

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar funcionários.");
            } else if (usuario.getDepartamento().getId() != antigoFunc
                    .getIdDepartamento()) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar funcionários fora do seu departamento.");
            }
        }

        // VALIDADE DE DADOS
        if (novoFunc.getDepartamento() != null) {
            if (eraChefe && !antigoFunc.getIdDepartamento()
                    .equals(novoFunc.getDepartamento().getId())) {
                throw new IllegalUpdateException(
                        "O 'departamento' deste funcionário não pode ser alterado pois ele(a) é chefe de seu departamento atual.");
            }
        }

        try {
            validarCampos(novoFunc);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    /**
     * Valida regras de negócio comuns tanto para inserção quanto para
     * alteração.
     */
    private static void validarCampos(final FuncionarioDTO func)
            throws CRUDException {

        if (func.getLogin() == null || func.getLogin().isEmpty()) {
            throw new CRUDException("O 'login' é um campo obrigatório.");
        }
        if (func.getSenha() == null || func.getSenha().isEmpty()) {
            throw new CRUDException("A 'senha' é um campo obrigatório.");
        }
        if (func.getNome() == null || func.getNome().isEmpty()) {
            throw new CRUDException("O 'nome' é um campo obrigatório.");
        }
        if (func.getCpf() == null) {
            throw new CRUDException("O 'CPF' é um campo obrigatório.");
        }
        if (func.getCpf().length() != 11) {
            throw new CRUDException(
                    "O 'CPF' deve possuir exatamente 11 números, sem traços ou pontos.");
        }
        if (func.getEmail() == null || func.getEmail().isEmpty()) {
            throw new CRUDException("O 'e-mail' é um campo obrigatório.");
        }
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(final Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public CargoEnum getCargo() {
        if (id == null) {
            return CargoEnum.INTERESSADO;
        } else if (isChefeDePatrimonioPrincipal()) {
            return CargoEnum.CHEFE_PATRIMONIO;
        } else if (isChefeDePatrimonioSubstituto()) {
            return CargoEnum.CHEFE_PATRIMONIO_SUBST;
        } else if (isChefeDeDepartamentoPrincipal()) {
            return CargoEnum.CHEFE_DEPT;
        } else if (isChefeDeDepartamentoSubstituto()) {
            return CargoEnum.CHEFE_DEPT_SUBST;
        } else {
            return CargoEnum.FUNCIONARIO;
        }
    }

    private Departamento getDepartamento() {
        if (getIdDepartamento() == null) {
            return null;
        }
        final DepartamentoRepository deptRep = new DepartamentoRepository();
        return deptRep.getById(getIdDepartamento());
    }

    private boolean isChefeDeDepartamentoPrincipal() {
        final Departamento dept = getDepartamento();
        return dept != null && !dept.getDePatrimonio()
                && dept.getIdChefe().equals(getId());
    }

    private boolean isChefeDeDepartamentoSubstituto() {
        final Departamento dept = getDepartamento();
        return dept != null && !dept.getDePatrimonio()
                && (dept.getIdChefeSubstituto() != null
                        && dept.getIdChefeSubstituto().equals(getId()));
    }

    private boolean isChefeDePatrimonioPrincipal() {
        final Departamento dept = getDepartamento();
        return dept != null && dept.getDePatrimonio()
                && dept.getIdChefe().equals(getId());
    }

    private boolean isChefeDePatrimonioSubstituto() {
        final Departamento dept = getDepartamento();
        return dept != null && dept.getDePatrimonio()
                && (dept.getIdChefeSubstituto() != null
                        && dept.getIdChefeSubstituto().equals(getId()));
    }

}
