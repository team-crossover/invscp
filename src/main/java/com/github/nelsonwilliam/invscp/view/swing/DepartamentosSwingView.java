package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;

public class DepartamentosSwingView extends JPanel implements DepartamentosView {

	private static final long serialVersionUID = 8975992154717828680L;

	private JTable table;
	private JButton btnAdicionar;
	private JButton btnDeletar;

	public DepartamentosSwingView() {
		initialize();
	}

	private void initialize() {
		setBounds(0, 0, 500, 500);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "De patrimônio?" }) {
			private static final long serialVersionUID = -5079273255233169992L;
			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, Boolean.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		scrollPane.setViewportView(table);

		btnAdicionar = new JButton("Adicionar novo");
		add(btnAdicionar);

		btnDeletar = new JButton("Deletar selecionado(s)");
		add(btnDeletar);
	}

	@Override
	public void addAdicionarDepartamentoListener(ActionListener listener) {
		btnAdicionar.addActionListener(listener);
	}

	@Override
	public void addDeletarDepartamentosListener(ActionListener listener) {
		btnDeletar.addActionListener(listener);
	}

	@Override
	public void updateDepartamentos(List<Departamento> departamentos) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setNumRows(0);
		for (Departamento d : departamentos) {
			tableModel.addRow(new Object[] { d.getId(), d.getNome(), d.getDePatrimonio() });
		}

		revalidate();
		repaint();
	}

	@Override
	public List<Integer> getSelectedDepartamentosIds() {
		List<Integer> selectedDepartamentos = new ArrayList<Integer>();
		int[] selectedRows = table.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {
			int row = selectedRows[i];
			Integer id = (Integer) table.getModel().getValueAt(row, 0);
			selectedDepartamentos.add(id);
		}
		return selectedDepartamentos;
	}
}
