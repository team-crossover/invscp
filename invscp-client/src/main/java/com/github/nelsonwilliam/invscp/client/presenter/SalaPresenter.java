package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.SalaView;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;

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
        if (salaDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (salaDTO.getId() == null) {
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
