package com.github.nelsonwilliam.invscp.prototipo;

import java.awt.EventQueue;

import com.github.nelsonwilliam.invscp.prototipo.model.repository.DatabaseConnection;
import com.github.nelsonwilliam.invscp.prototipo.presenter.CanilPresenter;
import com.github.nelsonwilliam.invscp.prototipo.view.swing.CanilSwingView;

public class Prototipo {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		if (!DatabaseConnection.openConnection()) {
			System.exit(1);
		}

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
