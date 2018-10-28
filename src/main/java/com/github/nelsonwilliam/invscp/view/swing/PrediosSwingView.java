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

import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.view.PrediosView;

public class PrediosSwingView extends JPanel implements PrediosView {

    private static final long serialVersionUID = 5043497852044311548L;

    private JTable table;
    private JButton btnAdicionar;
    private JButton btnDeletar;
    private JPopupMenu popupMenu; // Popup exibido ao clicar com o botão direito
                                  // em um item da tabela
    private JMenuItem popupItemAlterar;

    public PrediosSwingView() {
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

        btnAdicionar = new JButton("Adicionar novo");
        add(btnAdicionar);

        btnDeletar = new JButton("Deletar selecionado(s)");
        add(btnDeletar);
    }

    @Override
    public void addAdicionarPredioListener(ActionListener listener) {
        btnAdicionar.addActionListener(listener);

    }

    @Override
    public void addDeletarPrediosListener(ActionListener listener) {
        btnDeletar.addActionListener(listener);

    }

    @Override
    public void addAlterarPredioListener(ActionListener listener) {
        popupItemAlterar.addActionListener(listener);

    }

    @Override
    public void updatePredio(List<Predio> predio) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setNumRows(0);
        for (Predio p : predio) {
            tableModel.addRow(new Object[] { p.getId(), p.getNome() });
        }

        revalidate();
        repaint();

    }

    @Override
    public List<Integer> getSelectedPrediosIds() {
        List<Integer> selectedPredios = new ArrayList<Integer>();
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            int row = selectedRows[i];
            Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedPredios.add(id);
        }
        return selectedPredios;
    }

}
