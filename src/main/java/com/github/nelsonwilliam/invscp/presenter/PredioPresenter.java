package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.view.PredioView;

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
        final Funcionario usuario = mainPresenter.getUsuario();
        final PredioDTO predioDTO = view.getPredio();
        final Predio predio = predioDTO == null ? null : predioDTO.toModel();

        if (predio.getId() == null) {
            onConfirmarAdicao(usuario, predio);
        } else {
            onConfirmarAtualizacao(usuario, predio.getId(), predio);
        }
    }

    private void onConfirmarAdicao(final Funcionario usuario,
            final Predio predioNovo) {

        try {
            Predio.validarInserir(usuario, predioNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final PredioRepository predioRepo = new PredioRepository();
        predioRepo.add(predioNovo);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();

    }

    private void onConfirmarAtualizacao(final Funcionario usuario,
            final Integer idPredioAnterior, final Predio predioAtualizado) {

        try {
            Predio.validarAlterar(usuario, idPredioAnterior, predioAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        final PredioRepository predioRepo = new PredioRepository();
        predioRepo.update(predioAtualizado);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();
    }
}
