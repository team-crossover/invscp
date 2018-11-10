package com.github.nelsonwilliam.invscp.model.dto;

public class DepartamentoDTO implements DTO {

    private Integer id = null;

    private String nome = null;

    private Boolean dePatrimonio = null;

    private FuncionarioDTO chefe = null;

    private FuncionarioDTO chefeSubstituto = null;

    /**
     * Obtém o valor atual de id.
     *
     * @return O valor atual de id.
     */
    public final Integer getId() {
        return id;
    }

    /**
     * Atualiza o valor atual de id.
     *
     * @param newId O novo valor para id.
     */
    public final void setId(final Integer newId) {
        id = newId;
    }

    /**
     * Obtém o valor atual de nome.
     *
     * @return O valor atual de nome.
     */
    public final String getNome() {
        return nome;
    }

    /**
     * Atualiza o valor atual de nome.
     *
     * @param newNome O novo valor para nome.
     */
    public final void setNome(final String newNome) {
        nome = newNome;
    }

    /**
     * Obtém o valor atual de dePatrimonio.
     *
     * @return O valor atual de dePatrimonio.
     */
    public final Boolean getDePatrimonio() {
        return dePatrimonio;
    }

    /**
     * Atualiza o valor atual de dePatrimonio.
     *
     * @param newDePatrimonio O novo valor para dePatrimonio.
     */
    public final void setDePatrimonio(final Boolean newDePatrimonio) {
        dePatrimonio = newDePatrimonio;
    }

    /**
     * Obtém o valor atual de chefe.
     *
     * @return O valor atual de chefe.
     */
    public final FuncionarioDTO getChefe() {
        return chefe;
    }

    /**
     * Atualiza o valor atual de chefe.
     *
     * @param newChefe O novo valor para chefe.
     */
    public final void setChefe(final FuncionarioDTO newChefe) {
        chefe = newChefe;
    }

    /**
     * Obtém o valor atual de chefeSubstituto.
     *
     * @return O valor atual de chefeSubstituto.
     */
    public final FuncionarioDTO getChefeSubstituto() {
        return chefeSubstituto;
    }

    /**
     * Atualiza o valor atual de chefeSubstituto.
     *
     * @param newChefeSubstituto O novo valor para chefeSubstituto.
     */
    public final void setChefeSubstituto(
            final FuncionarioDTO newChefeSubstituto) {
        chefeSubstituto = newChefeSubstituto;
    }

}
