package com.github.nelsonwilliam.invscp.server.model;

import java.io.Serializable;

import com.github.nelsonwilliam.invscp.shared.model.dto.DTO;

/**
 * Models são responsáveis por conter toda a lógica de negócio. Cada Model deve
 * representar uma entidade que mapeie o domínio da aplicação e/ou conter a
 * lógica para executar as regras do negócio.
 */
public interface Model<D extends DTO> extends Serializable {

    /**
     * Obtém o ID do item.
     *
     * @return O ID do item.
     */
    Integer getId();

    /**
     * Define o ID do item.
     *
     * @param integer Novo ID.
     */
    void setId(Integer integer);

    /**
     * Define os valores deste Model de acordo com o DTO correspondente.
     */
    public void setValuesFromDTO(D model);

    /**
     * Obtém um DTO com os valores correspondente a este Model.
     */
    public D toDTO();

}
