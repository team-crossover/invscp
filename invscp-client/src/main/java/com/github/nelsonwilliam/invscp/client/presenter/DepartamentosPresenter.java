package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.DepartamentoView;
import com.github.nelsonwilliam.invscp.client.view.DepartamentosView;
import com.github.nelsonwilliam.invscp.client.view.ViewFactory;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.RelatorioDTO;
import com.github.nelsonwilliam.invscp.shared.util.Relatorios;

public class DepartamentosPresenter extends Presenter<DepartamentosView> {

    private final MainPresenter mainPresenter;

    public DepartamentosPresenter(final DepartamentosView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateDepartamentos();
    }

    private void setupViewListeners() {
        view.addAdicionarDepartamentoListener((final ActionEvent e) -> {
            onAdicionarDepartamento();
        });
        view.addDeletarDepartamentosListener((final ActionEvent e) -> {
            onDeletarDepartamentos();
        });
        view.addAlterarDepartamentoListener((final ActionEvent e) -> {
            onAlterarDepartamento();
        });
        view.addGerarRelatorioListener((final ActionEvent e) -> {
            onGerarRelatorio();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarDepartamento() {
        final DepartamentoDTO novoDept = new DepartamentoDTO();
        final List<FuncionarioDTO> chefes = Client
                .requestGetPossiveisChefesParaDepartamento(novoDept);
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final DepartamentoView deptView = ViewFactory.createDepartamento(
                mainPresenter.getView(), novoDept, true, chefes, chefes,
                usuario);
        final DepartamentoPresenter deptPresenter = new DepartamentoPresenter(
                deptView, mainPresenter, this);
        deptView.setVisible(true);
    }

    private void onDeletarDepartamentos() {
        final List<Integer> selectedDeptIds = view
                .getSelectedDepartamentosIds();
        if (selectedDeptIds.size() < 1) {
            view.showError("Nenhum item foi selecionado.");
            return;
        }

        view.showConfirmacao(
                "Deletar " + selectedDeptIds.size() + " departamento(s)?.",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarDepartamentos(selectedDeptIds);
                    }
                });
    }

    private void deletarDepartamentos(final List<Integer> deptIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (final Integer idDept : deptIds) {
            try {
                Client.requestValidarDeleteDepartamento(usuario, idDept);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeleteDepartamento(idDept);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Departamento(s) deletado(s) com sucesso.");
        }
        updateDepartamentos();
    }

    @SuppressWarnings("unused")
    private void onAlterarDepartamento() {
        final List<Integer> selectedDeptIds = view
                .getSelectedDepartamentosIds();
        if (selectedDeptIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedDeptId = selectedDeptIds.get(0);
        final DepartamentoDTO selectedDepartamento = Client
                .requestGetDepartamentoById(selectedDeptId);
        final List<FuncionarioDTO> chefes = Client
                .requestGetPossiveisChefesParaDepartamento(
                        selectedDepartamento);
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final DepartamentoView deptView = ViewFactory.createDepartamento(
                mainPresenter.getView(), selectedDepartamento, false, chefes,
                chefes, usuario);
        final DepartamentoPresenter deptPresenter = new DepartamentoPresenter(
                deptView, mainPresenter, this);
        deptView.setVisible(true);
    }

    private void onGerarRelatorio() {
        final List<Integer> selectedDeptIds = view
                .getSelectedDepartamentosIds();
        if (selectedDeptIds.size() != 1) {
            throw new RuntimeException(
                    "Para gerar relatório de departamento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedDeptId = selectedDeptIds.get(0);
        final DepartamentoDTO selectedDepartamento = Client
                .requestGetDepartamentoById(selectedDeptId);

        final RelatorioDTO relatorio = Client
                .requestGerarRelatorioDept(selectedDepartamento);

        final String fileName = "relatorio-dept-" + selectedDepartamento.getId()
                + "_"
                + relatorio.getMomentoGeracao().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"))
                + ".html";

        String htmlText = null;
        File file = null;
        try {
            htmlText = relatorio.toHtml();
            file = Relatorios.salvar("relatorios/" + fileName, htmlText);
        } catch (final IOException e) {
            view.showError("Não foi possível gerar o relatório.");
            e.printStackTrace();
            return;
        }

        view.showSucesso("Relatório gerado e salvo em:\n"
                + file.toPath().toAbsolutePath());
    }

    public void updateDepartamentos() {
        final List<DepartamentoDTO> depts = Client.requestGetDepartamentos();
        view.updateDepartamentos(depts, mainPresenter.getUsuario());
    }

}
