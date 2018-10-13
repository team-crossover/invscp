package com.github.nelsonwilliam.invscp.model.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe utilitária que permite obter os EntityManagers do JPA que são
 * utilizados para fazer consultas ao Banco de dados.
 */
public class PersistenceManager {

	private static String PERSISTENCE_UNIT_NAME = "invscp";

	private static EntityManagerFactory emFactory;

	static {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}

	public static void close() {
		emFactory.close();
	}
}