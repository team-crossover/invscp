package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.LoginView;

public class LoginPresenter extends Presenter<LoginView> {

	private Consumer<Funcionario> sucessfullLoginCallback;

	public LoginPresenter(LoginView view) {
		super(view);
		setupViewListeners();
	}

	/**
	 * Define a função que será chamada quando o login for efetuado com sucesso. O
	 * parâmetro da função é o funcionário que foi autenticado.
	 */
	public void setSucessfullLoginCallback(Consumer<Funcionario> callback) {
		this.sucessfullLoginCallback = callback;
	}

	private void setupViewListeners() {
		view.addConfirmListener((ActionEvent e) -> {
			onConfirmLogin();
		});
	}

	private void onConfirmLogin() {
		FuncionarioRepository funcRepo = new FuncionarioRepository();

		String login = view.getLogin();
		Funcionario funcionario = funcRepo.getByLogin(login);
		if (funcionario == null) {
			view.showLoginFailed("Login desconhecido.");
			return;
		}

		String senha = view.getSenha();
		if (!senha.equals(funcionario.getSenha())) {
			view.showLoginFailed("Senha incorreta.");
			return;
		}

		sucessfullLoginCallback.accept(funcionario);
	}

}
