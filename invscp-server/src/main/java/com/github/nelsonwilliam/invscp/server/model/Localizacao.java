package com.github.nelsonwilliam.invscp.server.model;

import com.github.nelsonwilliam.invscp.server.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.shared.exception.CRUDException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.UFEnum;

public class Localizacao implements Model<LocalizacaoDTO> {

    private static final long serialVersionUID = 6358078005778674613L;

    private Integer id = null;

    private String nome = null;

    private String endereco = null;

    private String cep = null;

    private String cidade = null;

    private UFEnum uf = null;

    @Override
    public void setValuesFromDTO(final LocalizacaoDTO dto) {
        setId(dto.getId());
        setNome(dto.getNome());
        setEndereco(dto.getEndereco());
        setCep(dto.getCep());
        setCidade(dto.getCidade());
        setUf(dto.getUf());
    }

    @Override
    public LocalizacaoDTO toDTO() {
        final LocalizacaoDTO dto = new LocalizacaoDTO();
        dto.setId(id);
        dto.setNome(nome);
        dto.setEndereco(endereco);
        dto.setCep(cep);
        dto.setCidade(cidade);
        dto.setUf(uf);
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
     * @param idLocalizacao ID da sala a ser deletada.
     * @throws IllegalDeleteException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idLocalizacao) throws IllegalDeleteException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idLocalizacao == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover localizações sem informar o ID.");
        }

        final PredioRepository predioRepo = new PredioRepository();
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao loca = locaRepo.getById(idLocalizacao);

        if (loca == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar a localização desejada.");
        }

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            throw new IllegalDeleteException(
                    "Você não tem permissão para deletar localizações.");
        }

        if (predioRepo.getByIdLocalizacao(loca.getId()).size() > 0) {
            throw new IllegalDeleteException(
                    "Não é possível deletar a localização " + loca.getNome()
                            + " pois existem prédios com esta localização.");
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
     * @param newLocaNova LOcalização a ser inserida. Se o ID for nulo, um novo
     *        valor será gerado.
     * @throws IllegalInsertException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarInserir(final FuncionarioDTO usuario,
            final LocalizacaoDTO newLocaNova) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();

        if (newLocaNova.getId() != null) {
            final Localizacao locaExistente = locaRepo
                    .getById(newLocaNova.getId());
            if (locaExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir a localização pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            throw new IllegalInsertException(
                    "Você não tem permissão para inserir localizações.");
        }

        try {
            validarCampos(newLocaNova);
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
     * @param idAntigaLoca ID da localização a ser alterada. O ID não pode ser
     *        nulo, pois a localização deve existir atualmente.
     * @param novaLoca Localização nova, com os novos dados desejados. O ID deve
     *        ser o mesmo do anterior.
     * @throws IllegalUpdateException Se não for possível inserir o novo
     *         elemento.
     */
    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaLoca, final LocalizacaoDTO novaLoca)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigaLoca == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma localização sem seu ID.");
        }

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao antigaLoca = locaRepo.getById(idAntigaLoca);

        if (antigaLoca == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar uma localização inexistente.");
        }

        if (novaLoca.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é possível remover o ID da localização.");
        }

        if (!antigaLoca.getId().equals(novaLoca.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID da localização.");
        }

        // CONTROLE DE ACESSO
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            throw new IllegalUpdateException(
                    "Você não tem permissão para alterar localizações.");
        }

        try {
            validarCampos(novaLoca);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }

    }

    /**
     * Valida regras de negócio comuns tanto para inserção quanto para
     * alteração.
     */
    private static void validarCampos(final LocalizacaoDTO loca)
            throws CRUDException {

        if (loca.getNome() == null || loca.getNome().isEmpty()) {
            throw new CRUDException("O 'nome' é um campo obrigatório.");
        }
        if (loca.getEndereco() == null || loca.getEndereco().isEmpty()) {
            throw new CRUDException("O 'endereço' é um campo obrigatório.");
        }
        if (loca.getCep() == null || loca.getCep().isEmpty()) {
            throw new CRUDException("O 'CEP' é um campo obrigatório.");
        }
        if (loca.getCep().length() != 8) {
            throw new CRUDException(
                    "O 'CEP' deve possuir exatamente 8 números, sem traços ou pontos.");
        }
        if (loca.getCidade() == null || loca.getCidade().isEmpty()) {
            throw new CRUDException("A 'cidade' é um campo obrigatório.");
        }
        if (loca.getUfString() == null || loca.getUfString().isEmpty()) {
            throw new CRUDException("A 'UF' é um campo obrigatório.");
        }
        if (loca.getUfString().length() != 2) {
            throw new CRUDException(
                    "O 'UF' deve possuir exatamente 2 caracteres.");
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public UFEnum getUf() {
        return uf;
    }

    public void setUf(final UFEnum uf) {
        this.uf = uf;
    }

    public String getUfString() {
        return uf.toString();
    }

    public void setUfString(final String uf) {
        this.uf = UFEnum.valueOf(uf);
    }

}
