package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.BemView;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class BemPresenter extends Presenter<BemView> {

    private final MainPresenter mainPresenter;
    private final BensPresenter bensPresenter;

    public BemPresenter(final BemView view, final MainPresenter mainPresenter,
            final BensPresenter bensPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.bensPresenter = bensPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final BemDTO bemDTO = view.getBem();
        if (bemDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (bemDTO.getId() == null) {
            onConfirmarAdicao(usuario, bemDTO);
        } else {
            onConfirmarAtualizacao(usuario, bemDTO.getId(), bemDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final BemDTO deptNovo) {

        try {
            Client.requestValidarInserirBem(usuario, deptNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddBem(deptNovo);
        view.showSucesso();
        view.close();
        bensPresenter.updateBens();
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idDeptAnterior, final BemDTO deptAtualizado) {

        try {
            Client.requestValidarAlterarBem(usuario, idDeptAnterior,
                    deptAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateBem(deptAtualizado);
        view.showSucesso();
        view.close();
        bensPresenter.updateBens();
    }
}
