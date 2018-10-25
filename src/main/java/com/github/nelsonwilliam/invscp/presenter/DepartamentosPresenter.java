package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;

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
	}

	private void onAdicionarDepartamento() {
		Departamento dept = new Departamento();
		DepartamentoRepository deptRepo = new DepartamentoRepository();
		deptRepo.add(dept);
		updateDepartamentos();
	}

	private void onDeletarDepartamentos() {
		List<Integer> selectedDeptIds = view.getSelectedDepartamentosIds();
		List<Departamento> selectedDepartamentos = new ArrayList<Departamento>();
		for (Integer id : selectedDeptIds) {
			Departamento dept = new Departamento();
			dept.setId(id);
			selectedDepartamentos.add(dept);
		}

		DepartamentoRepository deptRepo = new DepartamentoRepository();
		deptRepo.remove(selectedDepartamentos);
		updateDepartamentos();
	}

	private void updateDepartamentos() {
		DepartamentoRepository deptRepo = new DepartamentoRepository();
		List<Departamento> depts = deptRepo.getAll();
		view.updateDepartamentos(depts);
	}

}
