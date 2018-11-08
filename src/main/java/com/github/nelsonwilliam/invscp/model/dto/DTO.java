package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Model;

public interface DTO<M extends Model> {

    /**
     * Define os valores deste DTO de acordo com o Model correspondente.
     */
    public void setValuesFromModel(M model);

    /**
     * Obtém um Model com os valores correspondente a este DTO.
     */
    public M toModel();

}
