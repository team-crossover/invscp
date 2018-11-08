package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
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
        final Funcionario novoFunc = new Funcionario();
        final FuncionarioDTO novoFuncDTO = new FuncionarioDTO();
        novoFuncDTO.setValuesFromModel(novoFunc);

        final List<Departamento> depts = novoFunc.getOutrosDepartamentos();
        final List<DepartamentoDTO> deptsDTOs = new ArrayList<DepartamentoDTO>();
        for (final Departamento d : depts) {
            final DepartamentoDTO deptDTO = new DepartamentoDTO();
            deptDTO.setValuesFromModel(d);
            deptsDTOs.add(deptDTO);
        }

        final FuncionarioView funcView = ViewFactory.createFuncionario(
                InvSCP.VIEW_IMPL, mainPresenter.getView(), novoFuncDTO, true,
                deptsDTOs);
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();

        int deletados = 0;
        for (final Integer idFunc : funcIds) {

            try {
                Funcionario.validarDeletar(usuario, idFunc);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            final Funcionario func = funcRepo.getById(idFunc);
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
        final Funcionario selectedFuncionario = funcRepo
                .getById(selectedFuncIds.get(0));
        final FuncionarioDTO selectedFuncionarioDTO = new FuncionarioDTO();
        selectedFuncionarioDTO.setValuesFromModel(selectedFuncionario);

        final List<Departamento> depts = selectedFuncionario
                .getOutrosDepartamentos();
        final List<DepartamentoDTO> deptsDTOs = new ArrayList<DepartamentoDTO>();
        for (final Departamento d : depts) {
            final DepartamentoDTO deptDTO = new DepartamentoDTO();
            deptDTO.setValuesFromModel(d);
            deptsDTOs.add(deptDTO);
        }

        final FuncionarioView funcView = ViewFactory.createFuncionario(
                InvSCP.VIEW_IMPL, mainPresenter.getView(),
                selectedFuncionarioDTO, false, deptsDTOs);
        final FuncionarioPresenter funcPresenter = new FuncionarioPresenter(
                funcView, mainPresenter, this);
        funcView.setVisible(true);
    }

    public void updateFuncionarios() {
        final Funcionario funcLogado = mainPresenter.getUsuario();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final List<Funcionario> funcs;
        if (funcLogado.isChefeDePatrimonio()) {
            funcs = funcRepo.getAll();
        } else if (funcLogado.isChefeDeDepartamento()) {
            funcs = funcRepo.getByDepartamento(funcLogado.getDepartamento());
        } else {
            throw new AccessControlException(
                    "Apenas chefes devem gerenciar funcionários.");
        }

        final List<FuncionarioDTO> funcsDTOs = new ArrayList<FuncionarioDTO>();
        for (final Funcionario f : funcs) {
            final FuncionarioDTO funcDTO = new FuncionarioDTO();
            funcDTO.setValuesFromModel(f);
            funcsDTOs.add(funcDTO);
        }
        view.updateFuncionarios(funcsDTOs);
    }

}
