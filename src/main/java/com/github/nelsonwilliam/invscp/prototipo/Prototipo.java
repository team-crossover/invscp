package com.github.nelsonwilliam.invscp.prototipo;

import java.awt.EventQueue;
import java.sql.SQLException;

import com.github.nelsonwilliam.invscp.prototipo.presenter.CanilPresenter;
import com.github.nelsonwilliam.invscp.prototipo.view.swing.CanilSwingView;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class Prototipo {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DatabaseConnection.openConnection();

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
