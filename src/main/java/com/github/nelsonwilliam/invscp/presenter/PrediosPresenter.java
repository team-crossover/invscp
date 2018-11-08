package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
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
        final PredioDTO novoPredioDTO = new PredioDTO();
        novoPredioDTO.setValuesFromModel(novoPredio);

        final List<Localizacao> locas = novoPredio.getPossiveisLocalizacoes();
        final List<LocalizacaoDTO> locasDTOs = new ArrayList<LocalizacaoDTO>();
        for (final Localizacao l : locas) {
            final LocalizacaoDTO locaDTO = new LocalizacaoDTO();
            locaDTO.setValuesFromModel(l);
            locasDTOs.add(locaDTO);
        }

        final PredioView predioView = ViewFactory.createPredio(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), novoPredioDTO, true, locasDTOs);
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final PredioRepository predioRepo = new PredioRepository();

        int deletados = 0;
        for (int i = 0; i < prediosIds.size(); i++) {
            final Integer idPredio = prediosIds.get(i);

            try {
                Predio.validarDeletar(usuario, idPredio);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            final Predio predio = predioRepo.getById(idPredio);
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
        final PredioDTO selectedPredioDTO = new PredioDTO();
        selectedPredioDTO.setValuesFromModel(selectedPredio);

        final List<Localizacao> locas = selectedPredio
                .getPossiveisLocalizacoes();
        final List<LocalizacaoDTO> locasDTOs = new ArrayList<LocalizacaoDTO>();
        for (final Localizacao l : locas) {
            final LocalizacaoDTO locaDTO = new LocalizacaoDTO();
            locaDTO.setValuesFromModel(l);
            locasDTOs.add(locaDTO);
        }

        final PredioView predioView = ViewFactory.createPredio(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), selectedPredioDTO, false, locasDTOs);
        final PredioPresenter predioPresenter = new PredioPresenter(predioView,
                mainPresenter, this);
        predioView.setVisible(true);
    }

    public void updatePredios() {
        final PredioRepository predioRepo = new PredioRepository();
        final List<Predio> predios = predioRepo.getAll();
        final List<PredioDTO> prediosDTOs = new ArrayList<PredioDTO>();
        for (final Predio p : predios) {
            final PredioDTO predioDTO = new PredioDTO();
            predioDTO.setValuesFromModel(p);
            prediosDTOs.add(predioDTO);
        }
        view.updatePredios(prediosDTOs);
    }

}
