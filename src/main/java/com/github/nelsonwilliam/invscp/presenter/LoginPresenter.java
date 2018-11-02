package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.LoginView;

public class LoginPresenter extends Presenter<LoginView> {

    private Consumer<Funcionario> sucessfullLoginCallback;

    private final MainPresenter mainPresenter;

    public LoginPresenter(final LoginView view, final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmListener((final ActionEvent e) -> {
            onConfirmLogin();
        });
    }

    private void onConfirmLogin() {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();

        final String login = view.getLogin();
        final Funcionario funcionario = funcRepo.getByLogin(login);
        if (funcionario == null) {
            view.showLoginFailed("Login desconhecido.");
            return;
        }

        final String senha = view.getSenha();
        if (!senha.equals(funcionario.getSenha())) {
            view.showLoginFailed("Senha incorreta.");
            return;
        }

        view.close();
        mainPresenter.setIdFuncionarioLogado(funcionario.getId());
    }

}
