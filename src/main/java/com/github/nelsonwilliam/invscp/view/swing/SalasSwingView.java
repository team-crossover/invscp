package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.view.SalasView;

public class SalasSwingView extends JPanel implements SalasView {

    private static final long serialVersionUID = 578940426638949081L;

    private JTable table;
    private JButton btnAdicionar;
    private JButton btnDeletar;
    private JPopupMenu popupMenu; // Popup exibido ao clicar com o botão direito
                                  // em um item da tabela
    private JMenuItem popupItemAlterar;

    public SalasSwingView() {
        initialize();
    }

    private void initialize() {
        setBounds(0, 0, 500, 500);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "Nome" }));
        scrollPane.setViewportView(table);

        popupItemAlterar = new JMenuItem("Alterar");

        popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // Garante que ao clicar com o botão direito em um item para
                // exibir o menu, o
                // o único item selecionado da tabela.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(
                                SwingUtilities.convertPoint(popupMenu,
                                        new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint,
                                    rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        popupMenu.add(popupItemAlterar);
        table.setComponentPopupMenu(popupMenu);

        btnAdicionar = new JButton("Adicionar nova");
        add(btnAdicionar);

        btnDeletar = new JButton("Deletar selecionada(s)");
        add(btnDeletar);
    }

    @Override
    public void addAdicionarSalaListener(ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }

    @Override
    public void addDeletarSalasListener(ActionListener listener) {
        btnDeletar.addActionListener(listener);
    }

    @Override
    public void addAlterarSalaListener(ActionListener listener) {
        popupItemAlterar.addActionListener(listener);
    }

    @Override
    public void updateSala(List<Sala> sala) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setNumRows(0);
        for (Sala s : sala) {
            tableModel.addRow(new Object[] { s.getId(), s.getNome() });
        }

        revalidate();
        repaint();

    }

    @Override
    public List<Integer> getSelectedSalasIds() {
        List<Integer> selectedSalas = new ArrayList<Integer>();
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            int row = selectedRows[i];
            Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedSalas.add(id);
        }
        return selectedSalas;
    }
}
