package com.github.nelsonwilliam.invscp.model.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.github.nelsonwilliam.invscp.model.Model;

/**
 * Os Repositories responsáveis por fazer a comunicação com o Banco de dados,
 * oferecendo todas as consultas necessárias acerca de determinado Model, com
 * base nas necessidades das regras de negócio.
 * 
 * @param <M>
 */
public abstract class Repository<M extends Model> {

	/**
	 * Retorna o objeto Class para a classe do Model deste Repository.
	 * 
	 * @return
	 */
	public abstract Class<M> getModelClass();

	/**
	 * Obtém todos os itens do Model deste Repository.
	 * 
	 * @return
	 */
	public List<M> getAll() {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<M> cq = cb.createQuery(getModelClass());
			Root<M> rootEntry = cq.from(getModelClass());
			CriteriaQuery<M> all = cq.select(rootEntry);
			TypedQuery<M> allQuery = em.createQuery(all);
			return allQuery.getResultList();
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Obtém o item com o ID especificado.
	 * 
	 * @param id
	 * @return
	 */
	public M getById(Integer id) {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		try {
			return em.find(getModelClass(), id);
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Adiciona um novo item ao Repository.
	 * 
	 * @param item
	 */
	public void add(M item) {
		add(Collections.singletonList(item));
	}

	/**
	 * Adiciona múltiplos novos itens ao Repository.
	 * 
	 * @param items
	 */
	public void add(Iterable<M> items) {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		try {
			for (M item : items) {
				em.persist(item);
			}
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Atualiza um item do Repository.
	 * 
	 * @param item
	 */
	public void update(M item) {
		update(Collections.singletonList(item));
	}

	/**
	 * Atualiza múltiplos novos itens do Repository.
	 * 
	 * @param items
	 */
	public void update(Iterable<M> items) {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		try {
			for (M item : items) {
				em.merge(item);
			}
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}

	/**
	 * Remove um item do Repository.
	 * 
	 * @param item
	 */
	public void remove(M item) {
		remove(Collections.singletonList(item));
	}

	/**
	 * Remove múltiplos novos itens do Repository.
	 * 
	 * @param items
	 */
	public void remove(Iterable<M> items) {
		EntityManager em = PersistenceManager.getEntityManager();
		em.getTransaction().begin();
		try {
			for (M item : items) {
				M managedItem = em.merge(item);
				em.remove(managedItem);
			}
		} finally {
			em.getTransaction().commit();
			em.close();
		}
	}
}
