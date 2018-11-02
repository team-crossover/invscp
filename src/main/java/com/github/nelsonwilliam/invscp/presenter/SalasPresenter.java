package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.view.SalaView;
import com.github.nelsonwilliam.invscp.view.SalasView;
import com.github.nelsonwilliam.invscp.view.swing.SalaSwingView;

public class SalasPresenter extends Presenter<SalasView> {
    private final MainPresenter mainPresenter;

    public SalasPresenter(final SalasView view, final MainPresenter mainPresenter) {
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
        final Sala novoSala = new Sala();
        final SalaView salaView = new SalaSwingView((JFrame) mainPresenter.getView(), novoSala,
                true);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView, mainPresenter, this);
        salaView.setVisible(true);
    }

    private void onDeletarSalas() {
        final List<Integer> selectedPrdiosIds = view.getSelectedSalasIds();
        view.showConfirmacao("Deletar " + selectedPrdiosIds.size() + " salas(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarSalas(selectedPrdiosIds);
                    }
                });
    }

    private void deletarSalas(final List<Integer> salasIds) {
        final SalaRepository salaRepo = new SalaRepository();
        int deletados = 0;
        for (int i = 0; i < salasIds.size(); i++) {
            final Sala sala = salaRepo.getById(salasIds.get(i));

            // VALIDAÇÃO DE DADOS

            if (sala.getTipo() == TipoSalaEnum.DEPOSITO) {
                view.showError("Não é possível remover a sala de depósito.");
                continue;
            }

            // TODO Verificar se algum bem depende dessa sala.

            // REMOVE O ITEM

            salaRepo.remove(sala);
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

        final SalaRepository salaRepo = new SalaRepository();
        final Sala selectedSala = salaRepo.getById(selectedSalaIds.get(0));
        final SalaView salaView = new SalaSwingView((JFrame) mainPresenter.getView(), selectedSala,
                false);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView, mainPresenter, this);
        salaView.setVisible(true);
    }

    public void updateSalas() {
        final SalaRepository salaRepo = new SalaRepository();
        final List<Sala> salas = salaRepo.getAll();
        view.updateSalas(salas);
    }

}
