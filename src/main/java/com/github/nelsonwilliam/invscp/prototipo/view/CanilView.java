package com.github.nelsonwilliam.invscp.prototipo.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.TableModelListener;

import com.github.nelsonwilliam.invscp.prototipo.model.Dog;

public interface CanilView extends View {

	// ----------------------------------------
	// Métodos para notificar ações do usuário.
	// ----------------------------------------

	void addAddDogListener(ActionListener listener);

	void addDeleteDogsListener(ActionListener listener);

	void addUpdatedDogListener(TableModelListener listener);

	// -------------------------------------------
	// Métodos para atualizar os valores exibidos.
	// -------------------------------------------

	void setDogs(List<Dog> dogs);

}
