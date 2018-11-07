package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentoView;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

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
    }

    @SuppressWarnings("unused")
    private void onAdicionarDepartamento() {
        final Departamento novoDept = new Departamento();
        final DepartamentoView deptView = ViewFactory.createDepartamento(
                InvSCP.VIEW_IMPL, mainPresenter.getView(), novoDept, true);
        final DepartamentoPresenter deptPresenter = new DepartamentoPresenter(
                deptView, mainPresenter, this);
        deptView.setVisible(true);
    }

    private void onDeletarDepartamentos() {
        final List<Integer> selectedDeptIds = view
                .getSelectedDepartamentosIds();
        view.showConfirmacao(
                "Deletar " + selectedDeptIds.size() + " departamento(s)?.",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarDepartamentos(selectedDeptIds);
                    }
                });
    }

    private void deletarDepartamentos(final List<Integer> deptIds) {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final SalaRepository salaRepo = new SalaRepository();
        final Funcionario funcLogado = mainPresenter.getUsuario();

        // CONTROLE DE ACESSO

        if (funcLogado.isChefeDePatrimonio()) {
            view.showError(
                    "Apenas chefes de patrimônio podem deletar departamentos.");
            return;
        }

        int deletados = 0;
        for (final Integer id : deptIds) {
            final Departamento dept = deptRepo.getById(id);

            // VALIDAÇÃO DE DADOS

            if (dept.getDePatrimonio()) {
                view.showError("O departamento de patrimônio (" + dept.getNome()
                        + ") não pode ser deletado.");
                continue;
            }

            if (salaRepo.getByDepartamento(dept).size() > 0) {
                view.showError("O departamento " + dept.getNome()
                        + " não pode ser deletado pois existem salas pertecentes a ele.");
                continue;
            }

            // PRÉ-ATUALIZAÇÕES

            // Muda o departamento de todos os funcionarios do departamento
            // deletado
            // para nenhumm.
            final List<Funcionario> funcionarios = dept.getFuncionarios();
            for (final Funcionario func : funcionarios) {
                func.setIdDepartamento(null);
                funcRepo.update(func);
            }

            // REMOVE O ITEM

            deptRepo.remove(dept);
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

        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final Departamento selectedDepartamento = deptRepo
                .getById(selectedDeptIds.get(0));
        final DepartamentoView deptView = ViewFactory.createDepartamento(
                InvSCP.VIEW_IMPL, mainPresenter.getView(), selectedDepartamento,
                false);
        final DepartamentoPresenter deptPresenter = new DepartamentoPresenter(
                deptView, mainPresenter, this);
        deptView.setVisible(true);
    }

    public void updateDepartamentos() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final List<Departamento> depts = deptRepo.getAll();
        view.updateDepartamentos(depts);
    }

}
