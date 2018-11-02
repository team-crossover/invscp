package com.github.nelsonwilliam.invscp.model.repository;

import java.util.List;

import com.github.nelsonwilliam.invscp.model.Model;

/**
 * Repositories são responsáveis por fazer a comunicação com o Banco de dados, oferecendo todas as
 * consultas necessárias acerca de determinado Model, com base nas necessidades das regras de
 * negócio.
 *
 * @param <M> Classe do Model atendido por este Repository.
 */
public interface Repository<M extends Model> {

    /**
     * Obtém todos os itens.
     * 
     * @return
     */
    List<M> getAll();

    /**
     * Obtém o item com o ID especificado.
     *
     * @param id
     * @return
     */
    M getById(Integer id);

    /**
     * Adiciona um novo item ao Repository. <br>
     * Caso o ID do item não seja especificado, o banco de dados gerará um ID automáticamente.
     *
     * @param item Item a ser adicionado.
     * @return True se o item foi adicionado com sucesso.
     */
    boolean add(M item);

    /**
     * Adiciona múltiplos novos itens ao Repository. <br>
     * Caso o ID de algum item não seja especificado, o banco de dados gerará um ID automáticamente.
     *
     * @param items Itens a serem adicionados.
     * @return True se pelo menos um dos itens tiver sido adicionado com sucesso.
     */
    boolean add(Iterable<M> items);

    /**
     * Atualiza um item do Repository.
     *
     * @param item Item a ser atualizado.
     * @return True se o item foi atualizado com sucesso.
     */
    boolean update(M item);

    /**
     * Atualiza múltiplos novos itens do Repository.
     *
     * @param items Itens a serem atualizados.
     * @return True se pelo menos um dos itens tiver sido atualizado com sucesso.
     */
    boolean update(Iterable<M> items);

    /**
     * Remove um item do Repository.
     *
     * @param item Item a ser removido.
     * @return True se o item foi removido com sucesso.
     */
    boolean remove(M item);

    /**
     * Remove múltiplos novos itens do Repository.
     *
     * @param items Itens a serem removidos.
     * @return True se pelo menos um dos itens tiver sido removido com sucesso.
     */
    boolean remove(Iterable<M> items);

}
