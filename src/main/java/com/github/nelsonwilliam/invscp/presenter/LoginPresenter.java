package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.LoginView;

public class LoginPresenter extends Presenter<LoginView> {

    private final MainPresenter mainPresenter;

    public LoginPresenter(final LoginView view,
            final MainPresenter mainPresenter) {
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
        final String login = view.getLogin();
        final FuncionarioDTO funcionario = Client
                .requestGetFuncionarioByLogin(login);
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
        mainPresenter.setIdUsuario(funcionario.getId());
    }

}
