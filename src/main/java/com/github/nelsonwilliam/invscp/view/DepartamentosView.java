package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;

public interface DepartamentosView extends View {

	// ----------------------------------------
	// Métodos para notificar ações do usuário.
	// ----------------------------------------

	void addAdicionarDepartamentoListener(ActionListener listener);

	void addDeletarDepartamentosListener(ActionListener listener);

	/**
	 * Adiciona o Listener para quando o usuário informar que quer alterar um
	 * departamento. Espera-se que quando este evento for invocado, haja exatamente
	 * UM departamento selecionado (obtível através do método
	 * getSelectedDepartamentosIds.
	 */
	void addAlterarDepartamentoListener(ActionListener listener);

	// -------------------------------------------
	// Métodos para atualizar os valores exibidos.
	// -------------------------------------------

	void updateDepartamentos(List<Departamento> departamentos);

	// ---------------------------------------------
	// Métodos para obter os valores de formulários.
	// ---------------------------------------------

	/**
	 * Obtém uma lista com os IDs de todos os departamentos atualmente selecionados
	 * na tabela.
	 */
	List<Integer> getSelectedDepartamentosIds();

}
