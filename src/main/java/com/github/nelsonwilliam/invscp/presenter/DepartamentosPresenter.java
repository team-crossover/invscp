package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentoView;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;
import com.github.nelsonwilliam.invscp.view.swing.DepartamentoSwingView;

public class DepartamentosPresenter extends Presenter<DepartamentosView> {

	public DepartamentosPresenter(DepartamentosView view) {
		super(view);
		setupViewListeners();
		updateDepartamentos();
	}

	private void setupViewListeners() {
		view.addAdicionarDepartamentoListener((ActionEvent e) -> {
			onAdicionarDepartamento();
		});
		view.addDeletarDepartamentosListener((ActionEvent e) -> {
			onDeletarDepartamentos();
		});
		view.addAlterarDepartamentoListener((ActionEvent e) -> {
			onAlterarDepartamento();
		});
	}

	private void onAdicionarDepartamento() {
		Departamento novoDept = new Departamento();
		DepartamentoView deptView = new DepartamentoSwingView(null, novoDept, true);
		DepartamentoPresenter deptPresenter = new DepartamentoPresenter(deptView);
		deptPresenter.setSucessfullConfirmCallback((Departamento dept) -> {
			updateDepartamentos();
		});
		deptView.setVisible(true);
	}

	private void onDeletarDepartamentos() {
		List<Integer> selectedDeptIds = view.getSelectedDepartamentosIds();
		List<Departamento> selectedDepartamentos = new ArrayList<Departamento>();
		for (Integer id : selectedDeptIds) {
			Departamento dept = new Departamento();
			dept.setId(id); // Só salva o ID pois só precisa dele para deletar.
			selectedDepartamentos.add(dept);
		}

		DepartamentoRepository deptRepo = new DepartamentoRepository();
		deptRepo.remove(selectedDepartamentos);
		updateDepartamentos();
	}

	private void onAlterarDepartamento() {
		List<Integer> selectedDeptIds = view.getSelectedDepartamentosIds();
		if (selectedDeptIds.size() != 1) {
			throw new RuntimeException("Para alterar um elemento é necessário que apenas um esteja selecionado.");
		}

		DepartamentoRepository deptRepo = new DepartamentoRepository();
		Departamento selectedDepartamento = deptRepo.getById(selectedDeptIds.get(0));
		DepartamentoView deptView = new DepartamentoSwingView(null, selectedDepartamento, false);
		DepartamentoPresenter deptPresenter = new DepartamentoPresenter(deptView);
		deptPresenter.setSucessfullConfirmCallback((Departamento dept) -> {
			updateDepartamentos();
		});
		deptView.setVisible(true);
	}

	private void updateDepartamentos() {
		DepartamentoRepository deptRepo = new DepartamentoRepository();
		List<Departamento> depts = deptRepo.getAll();
		view.updateDepartamentos(depts);
	}

}
