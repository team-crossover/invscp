package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.view.InicioView;

/**
 * Presenter responsável pela InteressadoView.
 */
public class InicioPresenter extends Presenter<InicioView> {

	public InicioPresenter(InicioView view) {
		super(view);

		view.setUsuarioLogado(null);
	}

	@Override
	protected void setupViewListeners() {
		view.addLoginListener((ActionEvent e) -> {
			showLogin();
		});

		// TODO Fazer o resto da classe...
	}

	private void showLogin() {
		System.out.println("*Login*");
	}

}
