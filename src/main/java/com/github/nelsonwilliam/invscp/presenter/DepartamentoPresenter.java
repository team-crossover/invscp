package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentoView;

public class DepartamentoPresenter extends Presenter<DepartamentoView> {

	private Consumer<Departamento> sucessfullConfirmCallback;

	public DepartamentoPresenter(DepartamentoView view) {
		super(view);
		setupViewListeners();
	}

	/**
	 * Define a função que será chamada quando o departamento for confirmado com
	 * sucesso.
	 */
	public void setSucessfullConfirmCallback(Consumer<Departamento> callback) {
		this.sucessfullConfirmCallback = callback;
	}

	private void setupViewListeners() {
		view.addConfirmarListener((ActionEvent e) -> {
			onConfirmar();
		});
	}

	private void onConfirmar() {
		Departamento deptView = view.getDepartamento();
		if (deptView.getId() == null) {
			onConfirmarAdicao(deptView);
		} else {
			DepartamentoRepository deptRepo = new DepartamentoRepository();
			Departamento deptAntigo = deptRepo.getById(deptView.getId());
			onConfirmarAtualizacao(deptAntigo, deptView);
		}
	}

	private void onConfirmarAdicao(Departamento deptNovo) {
		// TODO Checar regras de negócio pra ver se a atualização é permitida...

		DepartamentoRepository deptRepo = new DepartamentoRepository();
		if (deptRepo.add(deptNovo)) {
			view.showSucesso();
			view.close();
			sucessfullConfirmCallback.accept(deptNovo);
		}
	}

	private void onConfirmarAtualizacao(Departamento deptAnterior, Departamento deptAtualizado) {
		// TODO Checar regras de negócio pra ver se a atualização é permitida...

		DepartamentoRepository deptRepo = new DepartamentoRepository();
		if (deptRepo.update(deptAtualizado)) {
			view.showSucesso();
			view.close();
			sucessfullConfirmCallback.accept(deptAtualizado);
		}
	}
}
