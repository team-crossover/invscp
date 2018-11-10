package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Predio implements Model<PredioDTO> {

    private static final long serialVersionUID = -2918874149512056756L;

    private Integer id = null;

    private String nome = null;

    private Integer idLocalizacao = null;

    @Override
    public void setValuesFromDTO(final PredioDTO dto) {
        setId(dto.getId());
        setNome(dto.getNome());
        if (dto.getLocalizacao() != null) {
            setIdLocalizacao(dto.getLocalizacao().getId());
        }
    }

    @Override
    public PredioDTO toDTO() {
        final PredioDTO dto = new PredioDTO();
        dto.setId(id);
        dto.setNome(nome);
        if (idLocalizacao != null) {
            final LocalizacaoRepository repo = new LocalizacaoRepository();
            final Localizacao local = repo.getById(idLocalizacao);
            dto.setLocalizacao(local == null ? null : local.toDTO());
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
     * @param idPredio ID do prédio a ser deletado.
     * @throws IllegalDeleteException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idPredio) throws IllegalDeleteException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idPredio == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover prédios sem informar o ID.");
        }

        final SalaRepository salaRepo = new SalaRepository();
        final PredioRepository predioRepo = new PredioRepository();
        final Predio predio = predioRepo.getById(idPredio);

        if (predio == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar o prédio desejada.");
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

        if (salaRepo.getByPredio(predio).size() > 0) {
            throw new IllegalDeleteException(
                    "Não é possível deletar o prédio " + predio.getNome()
                            + " pois existem salas com este prédio.");
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
     * @param novoPredio Prédio a ser inserido. Se o ID for nulo, um novo valor
     *        será gerado.
     * @throws IllegalInsertException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarInserir(final FuncionarioDTO usuario,
            final PredioDTO novoPredio) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final PredioRepository predioRepo = new PredioRepository();

        if (novoPredio.getId() != null) {
            final Predio predioExistente = predioRepo
                    .getById(novoPredio.getId());
            if (predioExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir o prédio pois o ID já existe.");
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
            validarCampos(novoPredio);
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
     * @param idAntigoPredio ID do prédio a ser alterado. O ID não pode ser
     *        nulo, pois o prédio deve existir atualmente.
     * @param novoPredio Prédio novo, com os novos dados desejados. O ID deve
     *        ser o mesmo do anterior.
     * @throws IllegalUpdateException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigoPredio, final PredioDTO novoPredio)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigoPredio == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um prédio sem seu ID.");
        }

        final PredioRepository predioRepo = new PredioRepository();
        final Predio antigoPredio = predioRepo.getById(idAntigoPredio);

        if (antigoPredio == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um prédio inexistente.");
        }

        if (novoPredio.getId() == null) {
            throw new IllegalUpdateException("Não é remover o ID do prédio.");
        }

        if (!antigoPredio.getId().equals(novoPredio.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID do prédio.");
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
            validarCampos(novoPredio);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }

    }

    /**
     * Valida regras de negócio comuns tanto para inserção quanto para
     * alteração.
     */
    private static void validarCampos(final PredioDTO predio)
            throws CRUDException {

        if (predio.getNome() == null || predio.getNome().isEmpty()) {
            throw new CRUDException("O 'nome' é um campo obrigatório.");
        }
        if (predio.getLocalizacao() == null) {
            throw new CRUDException("A 'localização' é um campo obrigatório.");
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

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Integer getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(final Integer idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

}
