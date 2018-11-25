package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoEventoMovEnum;
import com.github.nelsonwilliam.invscp.view.EventosMovimentacaoView;

public class EventosMovimentacaoSwingView extends JDialog
        implements EventosMovimentacaoView {

    private static final long serialVersionUID = -737080941309607491L;

    private JTable table;
    private JPopupMenu popupMenu;
    private JMenuItem popupItemVer;

    public EventosMovimentacaoSwingView(final JFrame owner) {
        super(owner);
        initialize();
    }

    private void initialize() {
        setTitle("Eventos de movimentação");
        setBounds(0, 0, 585, 300);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 15, 0, 121, 249, 0, 15 };
        gridBagLayout.rowHeights = new int[] { 15, 422, 15, 0 };
        gridBagLayout.columnWeights =
                new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        getContentPane().add(scrollPane, gbc_scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "Tipo", "Funcionario", "Data" }) {

            private static final long serialVersionUID = -4677927079422034326L;

            Class<?>[] columnTypes =
                    new Class[] { Integer.class, TipoEventoMovEnum.class,
                            FuncionarioDTO.class, LocalDate.class };

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
        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
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

        popupItemVer = new JMenuItem("Ver");

        popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
                // Garante que ao clicar com o botão direito em um item para
                // exibir o menu, o o
                // único item selecionado da tabela será o item clicado.
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
        popupMenu.add(popupItemVer);
        table.setComponentPopupMenu(popupMenu);
    }

    @Override
    public void addVerEventoMovimentacaoListener(ActionListener listener) {
        popupItemVer.addActionListener(listener);
    }

    @Override
    public void updateEventosMovimentacao(final MovimentacaoDTO movimentacao,
            final List<EventoMovimentacaoDTO> eventoMovimentacao) {

        setTitle("Eventos da movimentacao do bem "
                + movimentacao.getBem().getDescricao());

        final DefaultTableModel tableModel =
                (DefaultTableModel) table.getModel();
        tableModel.setNumRows(0);
        for (final EventoMovimentacaoDTO e : eventoMovimentacao) {
            tableModel.addRow(new Object[] { e.getId(), e.getTipo().getTexto(),
                    e.getFuncionario().getNome(),
                    e.getData().format(DateTimeFormatter.ISO_DATE) });
        }

        // Deixa em ordem decrescente com base no ID, para que os eventos novos
        // fiquem primeiro.
        table.getRowSorter().toggleSortOrder(0);
        table.getRowSorter().toggleSortOrder(0);

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
    public void showInfo(final String message) {
        final String titulo = "Informação";
        JOptionPane.showMessageDialog(this, message, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public List<Integer> getSelectedEventosMovimentacaoIds() {
        final List<Integer> selectedEventosMovimentacao =
                new ArrayList<Integer>();
        final int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            final int row = table.convertRowIndexToModel(selectedRows[i]);
            final Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedEventosMovimentacao.add(id);
        }
        return selectedEventosMovimentacao;
    }

}
