package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final SalaDTO salaDTO = view.getSala();
        final Sala sala = salaDTO == null ? null : salaDTO.toModel();

        if (sala.getId() == null) {
            onConfirmarAdicao(usuario, sala);
        } else {
            onConfirmarAtualizacao(usuario, sala.getId(), sala);
        }
    }

    private void onConfirmarAdicao(final Funcionario usuario,
            final Sala salaNova) {

        try {
            Sala.validarInserir(usuario, salaNova);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final SalaRepository salaRepo = new SalaRepository();
        salaRepo.add(salaNova);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }

    private void onConfirmarAtualizacao(final Funcionario usuario,
            final Integer idSalaAnterior, final Sala salaAtualizada) {

        try {
            Sala.validarAlterar(usuario, idSalaAnterior, salaAtualizada);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final SalaRepository salaRepo = new SalaRepository();
        salaRepo.update(salaAtualizada);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }
}
