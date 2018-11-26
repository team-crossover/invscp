package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.client.view.FiltroBemView;
import com.github.nelsonwilliam.invscp.shared.model.dto.FiltroBemDTO;

public class FiltroBemPresenter extends Presenter<FiltroBemView> {

    @SuppressWarnings("unused")
    private final MainPresenter mainPresenter;
    private final BensPresenter bensPresenter;

    public FiltroBemPresenter(final FiltroBemView view,
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
        final FiltroBemDTO filtro = view.getFiltro();
        if (!filtro.isEmpty()) {
        bensPresenter.setFiltroBens(filtro);
        bensPresenter.updateBens();
        }
        view.close();
    }

}
