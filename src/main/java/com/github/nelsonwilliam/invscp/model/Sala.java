package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Sala implements Model<SalaDTO> {

    private static final long serialVersionUID = 509047893405447267L;

    private Integer id = null;

    private String nome = null;

    private TipoSalaEnum tipo = null;

    private Integer idPredio = null;

    private Integer idDepartamento = null;

    @Override
    public void setValuesFromDTO(final SalaDTO dto) {
        id = dto.getId();
        nome = dto.getNome();
        tipo = dto.getTipo();
        if (dto.getPredio() != null) {
            idPredio = dto.getPredio().getId();
        }
        if (dto.getDepartamento() != null) {
            idDepartamento = dto.getDepartamento().getId();
        }
    }

    @Override
    public SalaDTO toDTO() {
        final SalaDTO dto = new SalaDTO();
        dto.setId(id);
        dto.setNome(nome);
        dto.setTipo(tipo);
        if (idPredio != null) {
            final PredioRepository repo = new PredioRepository();
            final Predio predio = repo.getById(idPredio);
            dto.setPredio(predio == null ? null : predio.toDTO());
        }
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
     * @param idSala ID da sala a ser deletada.
     * @throws IllegalDeleteException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idSala) throws IllegalDeleteException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idSala == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover salas sem informar o ID.");
        }

        final SalaRepository salaRepo = new SalaRepository();
        final Sala sala = salaRepo.getById(idSala);

        if (sala == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar a sala desejada.");
        }

        // ------------------
        // CONTROLE DE ACESSO
        // ------------------
        // Verificar controle de acesso (O usuário pode alterar esse tipo
        // de elemento, com esses atributos? Etc.).

        // TODO ...

        // -----------------
        // VALIDADE DE DADOS
        // -----------------
        // Verificar validade dos dados (Os novos atributos do elemento são
        // válidos? Há itens que não podem ser modificados? Há itens
        // repetidos/duplicados/já utilizados?Há itens obrigatórios faltando?
        // Etc).

        // TODO Verificar se algum bem depende dessa sala.

        if (sala.getTipo() == TipoSalaEnum.DEPOSITO) {
            throw new IllegalDeleteException(
                    "Não é possível remover a sala de depósito.");
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
     * @param novaSala Sala a ser inserida. Se o ID for nulo, um novo valor será
     *        gerado.
     * @throws IllegalInsertException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarInserir(final FuncionarioDTO usuario,
            final SalaDTO novaSala) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final SalaRepository salaRepo = new SalaRepository();

        if (novaSala.getId() != null) {
            final Sala salaExistente = salaRepo.getById(novaSala.getId());
            if (salaExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir a sala pois o ID já existe.");
            }
        }

        // ------------------
        // CONTROLE DE ACESSO
        // ------------------
        // Verificar controle de acesso (O usuário pode alterar esse tipo
        // de elemento, com esses atributos? Etc.).

        // TODO ...

        // -----------------
        // VALIDADE DE DADOS
        // -----------------
        // Verificar validade dos dados (Os novos atributos do elemento são
        // válidos? Há itens que não podem ser modificados? Há itens
        // repetidos/duplicados/já utilizados?Há itens obrigatórios faltando?
        // Etc).

        try {
            validarCampos(novaSala);
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
     * @param idAntigaSala ID da sala a ser alterada. O ID não pode ser nulo,
     *        pois a sala deve existir atualmente.
     * @param novaSala Sala nova, com os novos dados desejados. O ID deve ser o
     *        mesmo do anterior.
     * @throws IllegalUpdateException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaSala, final SalaDTO novaSala)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigaSala == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma sala sem seu ID.");
        }

        final SalaRepository salaRepo = new SalaRepository();
        final Sala antigaSala = salaRepo.getById(idAntigaSala);

        if (antigaSala == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma sala inexistente.");
        }

        if (novaSala.getId() == null) {
            throw new IllegalUpdateException("Não é remover o ID da sala.");
        }

        if (!antigaSala.getId().equals(novaSala.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID da sala.");
        }

        // ------------------
        // CONTROLE DE ACESSO
        // ------------------
        // Verificar controle de acesso (O usuário pode alterar esse tipo
        // de elemento, com esses atributos? Etc.).

        // TODO ...

        // -----------------
        // VALIDADE DE DADOS
        // -----------------
        // Verificar validade dos dados (Os novos atributos do elemento são
        // válidos? Há itens que não podem ser modificados? Há itens
        // repetidos/duplicados/já utilizados?Há itens obrigatórios faltando?
        // Etc).

        if (antigaSala != null && antigaSala.getTipo() == TipoSalaEnum.DEPOSITO
                && novaSala.getTipo() != TipoSalaEnum.DEPOSITO) {
            throw new IllegalUpdateException(
                    "Não é possível remover a sala de depósito.");
        }

        try {
            validarCampos(novaSala);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }

    }

    /**
     * Valida regras de negócio comuns tanto para inserção quanto para
     * alteração.
     */
    private static void validarCampos(final SalaDTO sala) throws CRUDException {

        final SalaRepository salaRepo = new SalaRepository();
        final DepartamentoRepository deptRepo = new DepartamentoRepository();

        if (sala.getNome() == null || sala.getNome().isEmpty()) {
            throw new CRUDException("O 'nome' é um campo obrigatório.");
        }
        if (sala.getPredio() == null) {
            throw new CRUDException("O 'prédio' selecionado não existe.");
        }
        if (sala.getDepartamento() == null) {
            throw new CRUDException("O 'departamento' selecionado não existe.");
        }
        if (sala.getTipo() == null) {
            throw new CRUDException("O 'tipo' é um campo obrigatório.");
        }
        if (sala.getTipo() == TipoSalaEnum.DEPOSITO && (sala.getId() == null
                || !sala.getId().equals(salaRepo.getDeDeposito().getId()))) {
            throw new CRUDException("Já existe outra sala de depósito.");
        }
        if (sala.getTipo() == TipoSalaEnum.DEPOSITO
                && (sala.getDepartamento() == null || !sala.getDepartamento()
                        .getId().equals(deptRepo.getDePatrimonio().getId()))) {
            throw new CRUDException(
                    "A sala de depósito deve pertencer ao departamento de patrimônio.");
        }
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public Predio getPredio() {
        final PredioRepository predioRepo = new PredioRepository();
        return predioRepo.getById(idPredio);
    }

    public void setIdPredio(final Integer idPredio) {
        this.idPredio = idPredio;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public Departamento getDepartamento() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        return deptRepo.getById(idDepartamento);
    }

    public void setIdDepartamento(final Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public TipoSalaEnum getTipo() {
        return tipo;
    }

    public void setTipo(final TipoSalaEnum tipo) {
        this.tipo = tipo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

}
