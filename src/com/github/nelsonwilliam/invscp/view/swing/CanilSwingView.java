package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.github.nelsonwilliam.invscp.model.Dog;
import com.github.nelsonwilliam.invscp.view.CanilView;

/**
 * Implementação em Swing da CanilView.
 */
public class CanilSwingView implements CanilView {

	private JTable tableDogs;
	private JButton btnAddDog;
	private JButton btnDeleteDogs;
	private JScrollPane scrollPane;
	private JPanel btnsPanel;

	public CanilSwingView() {
		initializeFrame();
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	private void initializeFrame() {
		JFrame frame = new JFrame();

		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);

		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableDogs = new JTable();
		tableDogs.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Idade" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDogs.getColumnModel().getColumn(1).setPreferredWidth(200);

		scrollPane.setViewportView(tableDogs);

		btnsPanel = new JPanel();
		frame.getContentPane().add(btnsPanel, BorderLayout.SOUTH);

		btnAddDog = new JButton("Adicionar dog");
		btnsPanel.add(btnAddDog);
		btnDeleteDogs = new JButton("Deletar dogs");
		btnsPanel.add(btnDeleteDogs);

		frame.setVisible(true);
	}

	// ----------------------------------------
	// Métodos para notificar ações do usuário.
	// ----------------------------------------

	@Override
	public void addAddDogListener(ActionListener listener) {
		btnAddDog.addActionListener(listener);
	}

	@Override
	public void addDeleteDogsListener(ActionListener listener) {
		btnDeleteDogs.addActionListener(listener);
	}

	@Override
	public void addUpdatedDogListener(TableModelListener listener) {
		tableDogs.getModel().addTableModelListener(listener);
	}

	// -------------------------------------------
	// Métodos para atualizar os valores exibidos.
	// -------------------------------------------

	@Override
	public void setDogs(List<Dog> dogs) {
		DefaultTableModel tableModel = (DefaultTableModel) tableDogs.getModel();
		tableModel.setNumRows(0);
		dogs.sort((Dog a, Dog b) -> {
			return a.getId() - b.getId();
		});
		for (Dog dog : dogs) {
			tableModel.addRow(new Object[] { dog.getId(), dog.getName(), dog.getAge() });
		}
	}
}
