package com.github.nelsonwilliam.invscp.presenter;

import java.awt.Window;
import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;
import com.github.nelsonwilliam.invscp.view.LoginView;
import com.github.nelsonwilliam.invscp.view.MainView;
import com.github.nelsonwilliam.invscp.view.MenuView;
import com.github.nelsonwilliam.invscp.view.swing.DepartamentosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LoginSwingView;

/**
 * Presenter responsável pela MainView e a MenuView contida nela.
 */
public class MainPresenter extends Presenter<MainView> {

	private final MenuView menuView;

	private Funcionario funcionarioLogado;

	public MainPresenter(MainView mainView, MenuView menuView) {
		super(mainView);
		this.menuView = menuView;
		setupViewListeners();
	}

	private void setupViewListeners() {
		menuView.addLoginListener((ActionEvent e) -> {
			onLogin();
		});
		menuView.addLogoutListener((ActionEvent e) -> {
			onLogout();
		});
		menuView.addDepartamentosListener((ActionEvent e) -> {
			showDepartamentos();
		});
	}

	private void onLogin() {
		LoginView loginView = new LoginSwingView((Window) view);
		LoginPresenter loginPresenter = new LoginPresenter(loginView);
		loginPresenter.setSucessfullLoginCallback((Funcionario funcionario) -> {
			funcionarioLogado = funcionario;
			menuView.updateFuncionarioLogado(funcionario);
			showNothing();
		});
		loginView.setVisible(true);
	}

	private void onLogout() {
		funcionarioLogado = null;
		menuView.updateFuncionarioLogado(null);
		showNothing();
	}

	private void showNothing() {
		view.updateSelectedView(null);
	}

	@SuppressWarnings("unused")
	private void showDepartamentos() {
		// Apenas funcionários logados podem manter departamentos
		if (funcionarioLogado == null) {
			showNothing();
			return;
		}

		DepartamentosView deptView = new DepartamentosSwingView();
		DepartamentosPresenter deptPresenter = new DepartamentosPresenter(deptView);
		view.updateSelectedView(deptView);
	}
}
