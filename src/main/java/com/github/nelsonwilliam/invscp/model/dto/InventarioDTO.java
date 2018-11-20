package com.github.nelsonwilliam.invscp.model.dto;

import java.util.List;

public class InventarioDTO implements DTO {

    private List<BemDTO> bens = null;

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
