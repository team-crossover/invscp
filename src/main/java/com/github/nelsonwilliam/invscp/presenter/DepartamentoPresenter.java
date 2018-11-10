package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
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
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final DepartamentoDTO deptDTO = view.getDepartamento();

        if (deptDTO.getId() == null) {
            onConfirmarAdicao(usuario, deptDTO);
        } else {
            onConfirmarAtualizacao(usuario, deptDTO.getId(), deptDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final DepartamentoDTO deptNovo) {

        try {
            Client.requestValidarInserirDepartamento(usuario, deptNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddDepartamento(deptNovo);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();

        // Executa as pós-alterações e exibe as mensagens resultantes.
        final List<String> messages = Client
                .requestPosAlterarDepartamento(usuario, deptNovo);
        for (final String message : messages) {
            view.showInfo(message);
        }

        // Se o departamento do funcionario logado tiver sido alterado, força a
        // reatualização da exibição da tela para este usuário
        if (usuario.getDepartamento() != null
                && usuario.getDepartamento().getId().equals(deptNovo.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
        }
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idDeptAnterior,
            final DepartamentoDTO deptAtualizado) {

        try {
            Client.requestValidarAlterarDepartamento(usuario, idDeptAnterior,
                    deptAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateDepartamento(deptAtualizado);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();

        // Executa as pós-alterações e exibe as mensagens resultantes.
        final List<String> messages = Client
                .requestPosAlterarDepartamento(usuario, deptAtualizado);
        for (final String message : messages) {
            view.showInfo(message);
        }

        // Se o departamento do funcionario logado tiver sido alterado, força a
        // reatualização da exibição da tela para este usuário
        if (usuario.getDepartamento() != null && usuario.getDepartamento()
                .getId().equals(deptAtualizado.getId())) {
            mainPresenter.setIdUsuario(usuario.getId());
        }
    }
}
