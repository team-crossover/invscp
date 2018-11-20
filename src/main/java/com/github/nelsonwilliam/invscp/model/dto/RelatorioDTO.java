package com.github.nelsonwilliam.invscp.model.dto;

import java.util.List;

public class RelatorioDTO implements DTO {

    private DepartamentoDTO departamento = null;

    private List<BemDTO> bens = null;

    /**
     * Obtém o valor atual de departamento.
     *
     * @return O valor atual de departamento.
     */
    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    /**
     * Atualiza o valor atual de departamento.
     *
     * @param newDepartamento O novo valor para departamento.
     */
    public final void setDepartamento(final DepartamentoDTO newDepartamento) {
        departamento = newDepartamento;
    }

    /**
     * Obtém o valor atual de bens.
     *
     * @return O valor atual de bens.
     */
    public final List<BemDTO> getBens() {
        return bens;
    }

    /**
     * Atualiza o valor atual de bens.
     *
     * @param newBens O novo valor para bens.
     */
    public final void setBens(final List<BemDTO> newBens) {
        bens = newBens;
    }


}
