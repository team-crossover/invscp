package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Departamento;

public class DepartamentoDTO implements DTO<Departamento> {

    private Integer id = null;

    private String nome = null;

    // TODO TERMINAR

    @Override
    public void fromModel(final Departamento newModel) {
        id = newModel.getId();
        nome = newModel.getNome();

        // TODO TERMINAR
    }

    @Override
    public Departamento toModel() {
        final Departamento dept = new Departamento();

        dept.setId(id);
        dept.setNome(nome);

        // TODO TERMINAR

        return null;
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
