package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.security.AccessControlException;
import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.FuncionarioView;
import com.github.nelsonwilliam.invscp.view.FuncionariosView;
import com.github.nelsonwilliam.invscp.view.swing.FuncionarioSwingView;

public class FuncionariosPresenter extends Presenter<FuncionariosView> {

    private final MainPresenter mainPresenter;

    public FuncionariosPresenter(final FuncionariosView view, final MainPresenter mainPresenter) {
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
        final Funcionario funcLogado = mainPresenter.getFuncionarioLogado();
        final Funcionario novoFunc = new Funcionario();
        final FuncionarioView funcView = new FuncionarioSwingView((JFrame) mainPresenter.getView(),
                novoFunc, true);
        final FuncionarioPresenter funcPresenter = new FuncionarioPresenter(funcView, mainPresenter,
                this);
        funcView.setVisible(true);
    }

    private void onDeletarFuncionarios() {
        final List<Integer> selectedFuncIds = view.getSelectedFuncionariosIds();
        view.showConfirmacao("Deletar " + selectedFuncIds.size() + " funcionário(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarFuncionarios(selectedFuncIds);
                    }
                });
    }

    private void deletarFuncionarios(final List<Integer> funcIds) {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        int deletados = 0;
        for (final Integer id : funcIds) {
            final Funcionario func = funcRepo.getById(id);

            // VALIDAÇÃO DE DADOS

            if (func.isChefeDeDepartamentoPrincipal() || func.isChefeDePatrimonioPrincipal()) {
                view.showError("O funcionário " + func.getNome()
                        + " não pode ser deletado pois é chefe principal do departamento "
                        + func.getDepartamento().getNome() + ".");
                continue;
            }

            // PRÉ-ATUALIZAÇÕES

            if (func.isChefeDeDepartamentoSubstituto() || func.isChefeDePatrimonioSubstituto()) {
                final Departamento dept = func.getDepartamento();
                dept.setIdChefeSubstituto(null);
                deptRepo.update(dept);
                view.showInfo("O departamento " + func.getDepartamento().getNome()
                        + " não possui mais um chefe substituto.");
            }

            // REMOVE O ITEM

            funcRepo.remove(func);
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

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario selectedFuncionario = funcRepo.getById(selectedFuncIds.get(0));
        final FuncionarioView funcView = new FuncionarioSwingView((JFrame) mainPresenter.getView(),
                selectedFuncionario, false);
        final FuncionarioPresenter funcPresenter = new FuncionarioPresenter(funcView, mainPresenter,
                this);
        funcView.setVisible(true);
    }

    public void updateFuncionarios() {
        final Funcionario funcLogado = mainPresenter.getFuncionarioLogado();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final List<Funcionario> funcs;
        if (funcLogado.isChefeDePatrimonio()) {
            funcs = funcRepo.getAll();
        } else if (funcLogado.isChefeDeDepartamento()) {
            funcs = funcRepo.getByDepartamento(funcLogado.getDepartamento());
        } else {
            throw new AccessControlException("Apenas chefes devem gerenciar funcionários.");
        }
        view.updateFuncionarios(funcs);
    }

}
