package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentoView;

public class DepartamentoPresenter extends Presenter<DepartamentoView> {

    private final MainPresenter mainPresenter;
    private final DepartamentosPresenter deptsPresenter;

    public DepartamentoPresenter(final DepartamentoView view,
            final MainPresenter mainPresenter,
            final DepartamentosPresenter deptsPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.deptsPresenter = deptsPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final Funcionario usuario = mainPresenter.getUsuario();
        final DepartamentoDTO deptDTO = view.getDepartamento();
        final Departamento dept = deptDTO == null ? null : deptDTO.toModel();

        if (dept.getId() == null) {
            onConfirmarAdicao(usuario, dept);
        } else {
            onConfirmarAtualizacao(usuario, dept.getId(), dept);
        }
    }

    private void onConfirmarAdicao(final Funcionario usuario,
            final Departamento deptNovo) {

        try {
            Departamento.validarInserir(usuario, deptNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        deptRepo.add(deptNovo);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();

        // Executa as pós-alterações e exibe as mensagens resultantes.
        final List<String> messages = Departamento.posAlterar(usuario,
                deptNovo);
        for (final String message : messages) {
            view.showInfo(message);
        }

        // Se o departamento do funcionario logado tiver sido alterado, força a
        // reatualização da exibição da tela para este usuário
        if (usuario.getIdDepartamento().equals(deptNovo.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
        }
    }

    private void onConfirmarAtualizacao(final Funcionario usuario,
            final Integer idDeptAnterior, final Departamento deptAtualizado) {

        try {
            Departamento.validarAlterar(usuario, idDeptAnterior,
                    deptAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        deptRepo.update(deptAtualizado);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();

        // Executa as pós-alterações e exibe as mensagens resultantes.
        final List<String> messages = Departamento.posAlterar(usuario,
                deptAtualizado);
        for (final String message : messages) {
            view.showInfo(message);
        }

        // Se o departamento do funcionario logado tiver sido alterado, força a
        // reatualização da exibição da tela para este usuário
        if (usuario.getIdDepartamento().equals(deptAtualizado.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
        }
    }
}
