package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.PredioView;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.PredioDTO;

public class PredioPresenter extends Presenter<PredioView> {

    private final MainPresenter mainPresenter;
    private final PrediosPresenter prediosPresenter;

    public PredioPresenter(final PredioView view,
            final MainPresenter mainPresenter,
            final PrediosPresenter prediosPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.prediosPresenter = prediosPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final PredioDTO predioDTO = view.getPredio();
        if (predioDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (predioDTO.getId() == null) {
            onConfirmarAdicao(usuario, predioDTO);
        } else {
            onConfirmarAtualizacao(usuario, predioDTO.getId(), predioDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final PredioDTO predioNovo) {

        try {
            Client.requestValidarInserirPredio(usuario, predioNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddPredio(predioNovo);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();

    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idPredioAnterior, final PredioDTO predioAtualizado) {

        try {
            Client.requestValidarAlterarPredio(usuario, idPredioAnterior,
                    predioAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdatePredio(predioAtualizado);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();
    }
}
