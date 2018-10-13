package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import com.github.nelsonwilliam.invscp.model.Dog;
import com.github.nelsonwilliam.invscp.model.repository.DogRepository;
import com.github.nelsonwilliam.invscp.view.CanilView;

public class CanilPresenter extends Presenter<CanilView> {

	private DogRepository dogRepository;

	public CanilPresenter(CanilView view) {
		super(view);
		dogRepository = new DogRepository();

		updateDogs();
	}

	@Override
	protected void setupViewListeners() {
		view.addAddDogListener((ActionEvent e) -> {
			onAddDog();
		});

		view.addDeleteDogsListener((ActionEvent e) -> {
			onDeleteDogs();
		});

		view.addUpdatedDogListener((TableModelEvent e) -> {
			if (e.getType() == TableModelEvent.UPDATE) {
				int row = e.getFirstRow();
				TableModel model = (TableModel) e.getSource();
				Dog dog = new Dog();
				dog.setId((Integer) model.getValueAt(row, 0));
				dog.setName((String) model.getValueAt(row, 1));
				dog.setAge((Integer) model.getValueAt(row, 2));
				dogRepository.update(dog);
				updateDogs();
			}
		});
	}

	// ---------------------------------------------
	// Métodos para reagir às interações do usuário.
	// ---------------------------------------------

	/**
	 * Adiciona um novo cachorro e atualiza a lista de cachorros.
	 */
	private void onAddDog() {
		Random r = new Random();
		Dog dog = new Dog();
		dog.setAge(r.nextInt(16));
		dog.setName("Algum dog");
		dogRepository.add(dog);
		updateDogs();
	}

	/**
	 * Deleta todos os cachorros e atualiza a lista de cachorros.
	 */
	private void onDeleteDogs() {
		List<Dog> dogs = dogRepository.getAll();
		dogRepository.remove(dogs);
		updateDogs();
	}

	// ---------------------------------------------------
	// Métodos para atualizar os valores exibidos na View.
	// ---------------------------------------------------

	/**
	 * Atualiza a lista de cachorros.
	 */
	private void updateDogs() {
		view.setDogs(dogRepository.getAll());
	}

}
