package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.client.view.BemFiltrarView;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class BemFiltrarPresenter extends Presenter<BemFiltrarView> {

    private final MainPresenter mainPresenter;
    private final BensPresenter bensPresenter;

    public BemFiltrarPresenter(final BemFiltrarView view,
            final MainPresenter mainPresenter,
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

        // ...

        view.close();
        bensPresenter.updateBens();
    }

}
