package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final FuncionarioDTO funcDTO = view.getFuncionario();
        final Funcionario func = funcDTO == null ? null : funcDTO.toModel();

        if (func.getId() == null) {
            onConfirmarAdicao(usuario, func);
        } else {
            onConfirmarAtualizacao(usuario, func.getId(), func);
        }
    }

    private void onConfirmarAdicao(final Funcionario usuario,
            final Funcionario funcNovo) {

        try {
            Funcionario.validarInserir(usuario, funcNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        funcRepo.add(funcNovo);
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

    private void onConfirmarAtualizacao(final Funcionario usuario,
            final Integer idFuncAnterior, final Funcionario funcAtualizado) {

        try {
            Funcionario.validarAlterar(usuario, idFuncAnterior, funcAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        funcRepo.update(funcAtualizado);
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
