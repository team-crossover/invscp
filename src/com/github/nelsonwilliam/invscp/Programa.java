package com.github.nelsonwilliam.invscp;

import java.awt.EventQueue;

import javax.persistence.EntityManager;

import com.github.nelsonwilliam.invscp.model.repository.PersistenceManager;
import com.github.nelsonwilliam.invscp.presenter.CanilPresenter;
import com.github.nelsonwilliam.invscp.view.swing.CanilSwingView;

public class Programa {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// Apenas para carregar/conectar com o Banco antes de exibir a interface, já que
		// pode demorar alguns segundos
		EntityManager em = PersistenceManager.getEntityManager();
		em.close();

		EventQueue.invokeLater(() -> {
			try {
				CanilSwingView view = new CanilSwingView();
				CanilPresenter presenter = new CanilPresenter(view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
