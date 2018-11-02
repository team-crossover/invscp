package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
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
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final SalaRepository salaRepo = new SalaRepository();
        final Sala sala = view.getSala();
        final Sala salaAntiga = salaRepo.getById(sala.getId());

        // TODO Dá erro se a sala de depósito estiver fora do departamento de patrimônio ou se já
        // existe outra sala de depósito.

        // VALIDAÇÃO DE DADOS

        if (sala.getNome() == null || sala.getNome().isEmpty()) {
            view.showError("O 'nome' é um campo obrigatório.");
            return;
        }
        if (sala.getIdPredio() == null) {
            view.showError("O 'prédio' é um campo obrigatório.");
            return;
        }
        if (sala.getPredio() == null) {
            view.showError("O 'prédio' selecionado não existe.");
            return;
        }
        if (sala.getIdDepartamento() == null) {
            view.showError("O 'departamento' é um campo obrigatório.");
            return;
        }
        if (sala.getDepartamento() == null) {
            view.showError("O 'departamento' selecionado não existe.");
            return;
        }
        if (sala.getTipo() == TipoSalaEnum.DEPOSITO
                && !sala.getId().equals(salaRepo.getDeDeposito().getId())) {
            view.showError("Já existe outra sala de depósito.");
            return;
        }
        if (sala.getTipo() == TipoSalaEnum.DEPOSITO
                && !sala.getIdDepartamento().equals(deptRepo.getDePatrimonio().getId())) {
            view.showError("A sala de depósito deve pertencer ao departamento de patrimônio.");
            return;
        }
        if (salaAntiga.getTipo() == TipoSalaEnum.DEPOSITO
                && sala.getTipo() != TipoSalaEnum.DEPOSITO) {
            view.showError("Não é possível remover a sala de depósito.");
        }

        if (sala.getId() == null) {
            onConfirmarAdicao(sala);
        } else {
            onConfirmarAtualizacao(salaAntiga, sala);
        }
    }

    private void onConfirmarAdicao(final Sala salaNova) {
        final SalaRepository salaRepo = new SalaRepository();
        salaRepo.add(salaNova);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }

    private void onConfirmarAtualizacao(final Sala salaAnterior, final Sala salaAtualizada) {
        final SalaRepository salaRepo = new SalaRepository();
        salaRepo.update(salaAtualizada);
        view.showSucesso();
        view.close();
        salasPresenter.updateSalas();
    }
}
