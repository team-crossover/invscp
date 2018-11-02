package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.view.PredioView;

public class PredioPresenter extends Presenter<PredioView> {

    private final MainPresenter mainPresenter;
    private final PrediosPresenter prediosPresenter;

    public PredioPresenter(final PredioView view, final MainPresenter mainPresenter,
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
        final PredioRepository predioRepo = new PredioRepository();
        final Predio predio = view.getPredio();
        final Predio predioAntigo = predioRepo.getById(predio.getId());

        // VALIDAÇÃO DE DADOS

        if (predio.getNome() == null || predio.getNome().isEmpty()) {
            view.showError("O 'nome' é um campo obrigatório.");
            return;
        }
        if (predio.getIdLocalizacao() == null) {
            view.showError("A 'localização' é um campo obrigatório.");
            return;
        }
        if (predio.getLocalizacao() == null) {
            view.showError("A 'localização' selecionada não existe.");
            return;
        }

        // APLICA A ALTERAÇÃO

        if (predio.getId() == null) {
            onConfirmarAdicao(predio);
        } else {
            onConfirmarAtualizacao(predioAntigo, predio);
        }
    }

    private void onConfirmarAdicao(final Predio predioNovo) {
        final PredioRepository predioRepo = new PredioRepository();
        predioRepo.add(predioNovo);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();

    }

    private void onConfirmarAtualizacao(final Predio predioAnterior,
            final Predio predioAtualizado) {
        final PredioRepository predioRepo = new PredioRepository();
        predioRepo.update(predioAtualizado);
        view.showSucesso();
        view.close();
        prediosPresenter.updatePredios();
    }
}
