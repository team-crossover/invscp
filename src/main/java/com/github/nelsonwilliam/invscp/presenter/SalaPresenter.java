package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.SalaView;

public class SalaPresenter extends Presenter<SalaView> {

    private final MainPresenter mainPresenter;
    private final SalasPresenter salasPresenter;

    public SalaPresenter(final SalaView view, final MainPresenter mainPresenter,
            final SalasPresenter salasPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.salasPresenter = salasPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final SalaDTO salaDTO = view.getSala();

        if (salaDTO == null || salaDTO.getId() == null) {
            onConfirmarAdicao(usuario, salaDTO);
        } else {
            onConfirmarAtualizacao(usuario, salaDTO.getId(), salaDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final SalaDTO salaNova) {

        try {
            Client.requestValidarInserirSala(usuario, salaNova);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddSala(salaNova);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idSalaAnterior, final SalaDTO salaAtualizada) {

        try {
            Client.requestValidarAlterarSala(usuario, idSalaAnterior,
                    salaAtualizada);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateSala(salaAtualizada);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }
}
