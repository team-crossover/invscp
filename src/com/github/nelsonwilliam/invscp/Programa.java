package com.github.nelsonwilliam.invscp;

import java.awt.EventQueue;

import com.github.nelsonwilliam.invscp.model.repository.DatabaseConnection;
import com.github.nelsonwilliam.invscp.presenter.CanilPresenter;
import com.github.nelsonwilliam.invscp.view.swing.CanilSwingView;

public class Programa {

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
