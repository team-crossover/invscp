package com.github.nelsonwilliam.invscp.model;

import java.io.Serializable;

/**
 * Models são responsáveis por conter toda a lógica de negócio. Cada Model deve representar uma
 * entidade que mapeie o domínio da aplicação e/ou conter a lógica para executar as regras do
 * negócio.
 */
public interface Model extends Serializable {

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

}
