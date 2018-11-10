package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.SalaView;
import com.github.nelsonwilliam.invscp.view.SalasView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class SalasPresenter extends Presenter<SalasView> {
    private final MainPresenter mainPresenter;

    public SalasPresenter(final SalasView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateSalas();
    }

    private void setupViewListeners() {
        view.addAdicionarSalaListener((final ActionEvent e) -> {
            onAdicionarSala();
        });
        view.addDeletarSalasListener((final ActionEvent e) -> {
            onDeletarSalas();
        });
        view.addAlterarSalaListener((final ActionEvent e) -> {
            onAlterarSala();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarSala() {
        final SalaDTO novoSala = new SalaDTO();
        final List<PredioDTO> predios = Client
                .requestGetPossiveisPrediosParaSala(novoSala);
        final List<DepartamentoDTO> depts = Client
                .requestGetPossiveisDepartamentosParaSala(novoSala);

        final SalaView salaView = ViewFactory.createSala(
                mainPresenter.getView(), novoSala, false, predios, depts);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView,
                mainPresenter, this);
        salaView.setVisible(true);
    }

    private void onDeletarSalas() {
        final List<Integer> selectedPrdiosIds = view.getSelectedSalasIds();
        view.showConfirmacao(
                "Deletar " + selectedPrdiosIds.size() + " salas(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarSalas(selectedPrdiosIds);
                    }
                });
    }

    private void deletarSalas(final List<Integer> salasIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (int i = 0; i < salasIds.size(); i++) {
            final Integer idSala = salasIds.get(i);

            try {
                Client.requestValidarDeleteSala(usuario, idSala);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeleteSala(idSala);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Exclusão de sala(s) concluída.");
        }
        updateSalas();
    }

    @SuppressWarnings("unused")
    private void onAlterarSala() {
        final List<Integer> selectedSalaIds = view.getSelectedSalasIds();
        if (selectedSalaIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedSalaId = selectedSalaIds.get(0);
        final SalaDTO selectedSala = Client.requestGetSalaById(selectedSalaId);
        final List<PredioDTO> predios = Client
                .requestGetPossiveisPrediosParaSala(selectedSala);
        final List<DepartamentoDTO> depts = Client
                .requestGetPossiveisDepartamentosParaSala(selectedSala);

        final SalaView salaView = ViewFactory.createSala(
                mainPresenter.getView(), selectedSala, false, predios, depts);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView,
                mainPresenter, this);
        salaView.setVisible(true);
    }

    public void updateSalas() {
        final List<SalaDTO> salas = Client.requestGetSalas();
        view.updateSalas(salas);
    }

}
