package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
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
        final DepartamentoDTO novoDeptDTO = new DepartamentoDTO();
        novoDeptDTO.setValuesFromModel(novoDept);

        final List<Funcionario> chefes = novoDept.getPossiveisChefes();
        final List<FuncionarioDTO> chefesDTOs = new ArrayList<FuncionarioDTO>();
        for (final Funcionario c : chefes) {
            final FuncionarioDTO chefeDTO = new FuncionarioDTO();
            chefeDTO.setValuesFromModel(c);
            chefesDTOs.add(chefeDTO);
        }

        final DepartamentoView deptView = ViewFactory.createDepartamento(
                InvSCP.VIEW_IMPL, mainPresenter.getView(), novoDeptDTO, true,
                chefesDTOs, chefesDTOs);
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final DepartamentoRepository deptRepo = new DepartamentoRepository();

        int deletados = 0;
        for (final Integer idDept : deptIds) {
            try {
                Departamento.validarDeletar(usuario, idDept);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            final Departamento dept = deptRepo.getById(idDept);
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
        final DepartamentoDTO selectedDepartamentoDTO = new DepartamentoDTO();
        selectedDepartamentoDTO.setValuesFromModel(selectedDepartamento);

        final List<Funcionario> chefes = selectedDepartamento
                .getPossiveisChefes();
        final List<FuncionarioDTO> chefesDTOs = new ArrayList<FuncionarioDTO>();
        for (final Funcionario c : chefes) {
            final FuncionarioDTO chefeDTO = new FuncionarioDTO();
            chefeDTO.setValuesFromModel(c);
            chefesDTOs.add(chefeDTO);
        }

        final DepartamentoView deptView = ViewFactory.createDepartamento(
                InvSCP.VIEW_IMPL, mainPresenter.getView(),
                selectedDepartamentoDTO, false, chefesDTOs, chefesDTOs);
        final DepartamentoPresenter deptPresenter = new DepartamentoPresenter(
                deptView, mainPresenter, this);
        deptView.setVisible(true);
    }

    public void updateDepartamentos() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final List<Departamento> depts = deptRepo.getAll();
        final List<DepartamentoDTO> deptsDTOs = new ArrayList<DepartamentoDTO>();
        for (final Departamento d : depts) {
            final DepartamentoDTO deptDTO = new DepartamentoDTO();
            deptDTO.setValuesFromModel(d);
            deptsDTOs.add(deptDTO);
        }
        view.updateDepartamentos(deptsDTOs);
    }

}
