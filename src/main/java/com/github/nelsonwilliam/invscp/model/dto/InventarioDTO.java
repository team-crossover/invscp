package com.github.nelsonwilliam.invscp.model.dto;

public class InventarioDTO implements DTO {

    private Integer id = null;

    private BemDTO bem = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BemDTO getBem() {
        return bem;
    }

    public void setBem(final BemDTO bem) {
        this.bem = bem;
    }

}
