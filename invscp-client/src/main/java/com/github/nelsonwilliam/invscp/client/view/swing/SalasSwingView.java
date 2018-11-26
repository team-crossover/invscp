package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.github.nelsonwilliam.invscp.client.view.SalasView;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;

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
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 199, 249, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
        gridBagLayout.rowHeights = new int[] { 422, 25, 0 };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        add(scrollPane, gbc_scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
                "ID", "Nome", "Prédio", "Departamento", "Tipo" }) {
            private static final long serialVersionUID = -6152171932737868693L;
            Class<?>[] columnTypes = new Class[] { Integer.class, String.class,
                    String.class, String.class, String.class };

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        });
        table.getColumnModel().getColumn(0).setMinWidth(75);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getColumnModel().getColumn(2).setMinWidth(200);
        table.getColumnModel().getColumn(3).setMinWidth(200);
        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(100);
        table.setAutoCreateRowSorter(true);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollPane.setViewportView(table);
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (table.getPreferredSize().width < scrollPane.getWidth()) {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                } else {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }
            }
        });

        popupItemAlterar = new JMenuItem("Alterar");

        popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
                // Garante que ao clicar com o botão direito em um item para
                // exibir o menu, o o
                // único item selecionado da tabela.
                SwingUtilities.invokeLater(() -> {
                    final int rowAtPoint = table.rowAtPoint(SwingUtilities
                            .convertPoint(popupMenu, new Point(0, 0), table));
                    if (rowAtPoint > -1) {
                        table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(final PopupMenuEvent e) {
            }
        });
        popupMenu.add(popupItemAlterar);
        table.setComponentPopupMenu(popupMenu);

        btnAdicionar = new JButton("Adicionar nova");
        final GridBagConstraints gbc_btnAdicionar = new GridBagConstraints();
        gbc_btnAdicionar.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnAdicionar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAdicionar.gridx = 1;
        gbc_btnAdicionar.gridy = 1;
        add(btnAdicionar, gbc_btnAdicionar);

        btnDeletar = new JButton("Deletar selecionada(s)");
        final GridBagConstraints gbc_btnDeletar = new GridBagConstraints();
        gbc_btnDeletar.insets = new Insets(0, 0, 0, 5);
        gbc_btnDeletar.anchor = GridBagConstraints.SOUTHEAST;
        gbc_btnDeletar.gridx = 2;
        gbc_btnDeletar.gridy = 1;
        add(btnDeletar, gbc_btnDeletar);

        // Deixa em ordem crescente com base no nome.
        table.getRowSorter().toggleSortOrder(1);
    }

    @Override
    public void addAdicionarSalaListener(final ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }

    @Override
    public void addDeletarSalasListener(final ActionListener listener) {
        btnDeletar.addActionListener(listener);
    }

    @Override
    public void addAlterarSalaListener(final ActionListener listener) {
        popupItemAlterar.addActionListener(listener);
    }

    @Override
    public void updateSalas(final List<SalaDTO> salas) {
        final DefaultTableModel tableModel = (DefaultTableModel) table
                .getModel();
        tableModel.setNumRows(0);
        for (final SalaDTO s : salas) {
            tableModel.addRow(new Object[] { s.getId(), s.getNome(),
                    s.getPredio() == null ? "Nenhum" : s.getPredio().getNome(),
                    s.getDepartamento() == null ? "Nenhum"
                            : s.getDepartamento().getNome(),
                    s.getTipo() == null ? "Nenhum" : s.getTipo().getTexto() });
        }

        revalidate();
        repaint();

    }

    @Override
    public void showError(final String message) {
        final String titulo = "Erro";
        final String messageCompleta = "Erro: " + message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSucesso(final String message) {
        final String titulo = "Sucesso";
        final String messageCompleta = message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showConfirmacao(final String message,
            final Consumer<Boolean> responseCallback) {
        final String titulo = "Confirmação";
        final int resposta = JOptionPane.showConfirmDialog(this, message,
                titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        responseCallback.accept(resposta == JOptionPane.YES_OPTION);
    }

    @Override
    public void showInfo(final String message) {
        final String titulo = "Informação";
        JOptionPane.showMessageDialog(this, message, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public List<Integer> getSelectedSalasIds() {
        final List<Integer> selectedSalas = new ArrayList<Integer>();
        final int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            final int row = table.convertRowIndexToModel(selectedRows[i]);
            final Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedSalas.add(id);
        }
        return selectedSalas;
    }
}
