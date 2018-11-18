package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
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
        final PredioDTO novoPredio = new PredioDTO();
        final List<LocalizacaoDTO> locas = Client
                .requestGetPossiveisLocalizacoesParaPredio(novoPredio);

        final PredioView predioView = ViewFactory
                .createPredio(mainPresenter.getView(), novoPredio, true, locas);
        final PredioPresenter predioPresenter = new PredioPresenter(predioView,
                mainPresenter, this);
        predioView.setVisible(true);
    }

    private void onDeletarPredios() {
        final List<Integer> selectedPrdiosIds = view.getSelectedPrediosIds();
        if (selectedPrdiosIds.size() < 1) {
            view.showError("Nenhum item foi selecionado.");
            return;
        }

        view.showConfirmacao(
                "Deletar " + selectedPrdiosIds.size() + " prédios(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarPredios(selectedPrdiosIds);
                    }
                });
    }

    private void deletarPredios(final List<Integer> prediosIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (int i = 0; i < prediosIds.size(); i++) {
            final Integer idPredio = prediosIds.get(i);

            try {
                Client.requestValidarDeletePredio(usuario, idPredio);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeletePredio(idPredio);
            deletados++;
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

        final Integer selectedPredioId = selectedPredioIds.get(0);
        final PredioDTO selectedPredio = Client
                .requestGetPredioById(selectedPredioId);
        final List<LocalizacaoDTO> locas = Client
                .requestGetPossiveisLocalizacoesParaPredio(selectedPredio);

        final PredioView predioView = ViewFactory.createPredio(
                mainPresenter.getView(), selectedPredio, false, locas);
        final PredioPresenter predioPresenter = new PredioPresenter(predioView,
                mainPresenter, this);
        predioView.setVisible(true);
    }

    public void updatePredios() {
        final List<PredioDTO> predios = Client.requestGetPredios();
        view.updatePredios(predios);
    }

}
