package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.FuncionarioView;

public class FuncionarioPresenter extends Presenter<FuncionarioView> {

    private final MainPresenter mainPresenter;
    private final FuncionariosPresenter funcsPresenter;

    public FuncionarioPresenter(final FuncionarioView view,
            final MainPresenter mainPresenter,
            final FuncionariosPresenter funcsPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.funcsPresenter = funcsPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final FuncionarioDTO funcDTO = view.getFuncionario();

        if (funcDTO == null || funcDTO.getId() == null) {
            onConfirmarAdicao(usuario, funcDTO);
        } else {
            onConfirmarAtualizacao(usuario, funcDTO.getId(), funcDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final FuncionarioDTO funcNovo) {

        try {
            Client.requestValidarInserirFuncionario(usuario, funcNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddFuncionario(funcNovo);
        view.showSucesso();
        view.close();
        funcsPresenter.updateFuncionarios();

        // Se o funcionário atualizado é o que estava logado, atualiza o main
        // para garantir dados atualizados são exibidos no menu.
        if (usuario.getId().equals(funcNovo.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
            return;
        }
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idFuncAnterior, final FuncionarioDTO funcAtualizado) {

        try {
            Client.requestValidarAlterarFuncionario(usuario, idFuncAnterior,
                    funcAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateFuncionario(funcAtualizado);
        view.showSucesso();
        view.close();
        funcsPresenter.updateFuncionarios();

        // Se o funcionário atualizado é o que estava logado, atualiza o main
        // para garantir dados atualizados são exibidos no menu.
        if (usuario.getId().equals(funcAtualizado.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
            return;
        }
    }
}
