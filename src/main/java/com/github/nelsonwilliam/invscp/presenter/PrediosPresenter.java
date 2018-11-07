package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.view.PredioView;
import com.github.nelsonwilliam.invscp.view.PrediosView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class PrediosPresenter extends Presenter<PrediosView> {

    private final MainPresenter mainPresenter;

    public PrediosPresenter(final PrediosView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updatePredios();
    }

    private void setupViewListeners() {
        view.addAdicionarPredioListener((final ActionEvent e) -> {
            onAdicionarPredio();
        });
        view.addDeletarPrediosListener((final ActionEvent e) -> {
            onDeletarPredios();
        });
        view.addAlterarPredioListener((final ActionEvent e) -> {
            onAlterarPredio();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarPredio() {
        final Predio novoPredio = new Predio();
        final PredioView predioView = ViewFactory.createPredio(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), novoPredio, true);
        final PredioPresenter predioPresenter = new PredioPresenter(predioView,
                mainPresenter, this);
        predioView.setVisible(true);
    }

    private void onDeletarPredios() {
        final List<Integer> selectedPrdiosIds = view.getSelectedPrediosIds();
        view.showConfirmacao(
                "Deletar " + selectedPrdiosIds.size() + " prédios(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarPredios(selectedPrdiosIds);
                    }
                });
    }

    private void deletarPredios(final List<Integer> prediosIds) {
        final SalaRepository salaRepo = new SalaRepository();
        final PredioRepository predioRepo = new PredioRepository();
        int deletados = 0;
        for (int i = 0; i < prediosIds.size(); i++) {
            final Predio predio = predioRepo.getById(prediosIds.get(i));

            // VALIDAÇÃO DE DADOS
            if (salaRepo.getByPredio(predio).size() > 0) {
                view.showError(
                        "Não é possível deletar o prédio " + predio.getNome()
                                + " pois existem salas com este prédio.");
                continue;
            }

            // REMOVE O ITEM

            if (predioRepo.remove(predio)) {
                deletados++;
            }
        }

        if (deletados > 0) {
            view.showSucesso("Exclusão de prédio(s) concluída.");
        }
        updatePredios();
    }

    @SuppressWarnings("unused")
    private void onAlterarPredio() {
        final List<Integer> selectedPredioIds = view.getSelectedPrediosIds();
        if (selectedPredioIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final PredioRepository predioRepo = new PredioRepository();
        final Predio selectedPredio = predioRepo
                .getById(selectedPredioIds.get(0));
        final PredioView predioView = ViewFactory.createPredio(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), selectedPredio, false);
        final PredioPresenter predioPresenter = new PredioPresenter(predioView,
                mainPresenter, this);
        predioView.setVisible(true);
    }

    public void updatePredios() {
        final PredioRepository predioRepo = new PredioRepository();
        final List<Predio> predios = predioRepo.getAll();
        view.updatePredios(predios);
    }

}
