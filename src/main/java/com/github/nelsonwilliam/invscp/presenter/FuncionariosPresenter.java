package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.FuncionarioView;
import com.github.nelsonwilliam.invscp.view.FuncionariosView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class FuncionariosPresenter extends Presenter<FuncionariosView> {

    private final MainPresenter mainPresenter;

    public FuncionariosPresenter(final FuncionariosView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateFuncionarios();
    }

    private void setupViewListeners() {
        view.addAdicionarFuncionarioListener((final ActionEvent e) -> {
            onAdicionarFuncionario();
        });
        view.addDeletarFuncionariosListener((final ActionEvent e) -> {
            onDeletarFuncionarios();
        });
        view.addAlterarFuncionarioListener((final ActionEvent e) -> {
            onAlterarFuncionario();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarFuncionario() {
        final FuncionarioDTO novoFunc = new FuncionarioDTO();
        final List<DepartamentoDTO> depts = Client
                .requestGetPossiveisDepartamentosParaFuncionario(novoFunc);

        final FuncionarioView funcView = ViewFactory.createFuncionario(
                mainPresenter.getView(), novoFunc, true, depts);
        final FuncionarioPresenter funcPresenter = new FuncionarioPresenter(
                funcView, mainPresenter, this);
        funcView.setVisible(true);
    }

    private void onDeletarFuncionarios() {
        final List<Integer> selectedFuncIds = view.getSelectedFuncionariosIds();
        view.showConfirmacao(
                "Deletar " + selectedFuncIds.size() + " funcionário(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarFuncionarios(selectedFuncIds);
                    }
                });
    }

    private void deletarFuncionarios(final List<Integer> funcIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (final Integer idFunc : funcIds) {

            try {
                Client.requestValidarDeleteFuncionario(usuario, idFunc);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeleteFuncionario(idFunc);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Exclusão de funcionário(s) concluída.");
        }
        updateFuncionarios();
    }

    @SuppressWarnings("unused")
    private void onAlterarFuncionario() {
        final List<Integer> selectedFuncIds = view.getSelectedFuncionariosIds();
        if (selectedFuncIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedFuncId = selectedFuncIds.get(0);
        final FuncionarioDTO selectedFuncionario = Client
                .requestGetFuncionarioById(selectedFuncId);
        final List<DepartamentoDTO> depts = Client
                .requestGetPossiveisDepartamentosParaFuncionario(
                        selectedFuncionario);

        final FuncionarioView funcView = ViewFactory.createFuncionario(
                mainPresenter.getView(), selectedFuncionario, false, depts);
        final FuncionarioPresenter funcPresenter = new FuncionarioPresenter(
                funcView, mainPresenter, this);
        funcView.setVisible(true);
    }

    public void updateFuncionarios() {
        final List<FuncionarioDTO> funcs = Client
                .requestGetFuncionariosByUsuario(mainPresenter.getUsuario());
        view.updateFuncionarios(funcs);
    }

}
