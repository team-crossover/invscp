package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Predio;

public class PredioDTO implements DTO<Predio> {

    private Integer id = null;

    private String nome = null;

    // TODO TERMINAR

    @Override
    public void fromModel(final Predio newModel) {
        id = newModel.getId();
        nome = newModel.getNome();

        // TODO TERMINAR
    }

    @Override
    public Predio toModel() {
        final Predio predio = new Predio();

        predio.setId(id);
        predio.setNome(nome);

        // TODO TERMINAR

        return predio;
    }

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

}
