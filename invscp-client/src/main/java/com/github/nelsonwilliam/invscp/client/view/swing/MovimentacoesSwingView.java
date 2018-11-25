package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
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

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.MovimentacoesView;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.EtapaMovEnum;

public class MovimentacoesSwingView extends JPanel
        implements MovimentacoesView {

    private static final long serialVersionUID = -9210173032768696552L;

    private JTable table;
    private JPopupMenu popupMenu;
    private JMenuItem popupItemVer;
    private JMenuItem popupItemAceitarSaida;
    private JMenuItem popupItemNegarSaida;
    private JMenuItem popupItemAceitarEntrada;
    private JMenuItem popupItemNegarEntrada;
    private JMenuItem popupItemCancelar;
    private JMenuItem popupItemFinalizar;
    private JMenuItem popupItemEventos;
    private JMenuItem popupItemGerarGuiaTransporte;

    public MovimentacoesSwingView() {
        initialize();
    }

    private void initialize() {
        setBounds(0, 0, 850, 700);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 199, 249, 0 };
        gridBagLayout.rowHeights = new int[] { 422, 25, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        popupItemVer = new JMenuItem("Ver");

        popupItemAceitarSaida = new JMenuItem("Aceitar saída");
        popupItemNegarSaida = new JMenuItem("Recusar saída");
        popupItemAceitarEntrada = new JMenuItem("Aceitar entrada");
        popupItemNegarEntrada = new JMenuItem("Recusar entrada");
        popupItemCancelar = new JMenuItem("Cancelar");
        popupItemFinalizar = new JMenuItem("Finalizar");
        popupItemEventos = new JMenuItem("Eventos de movimentação...");
        popupItemGerarGuiaTransporte =
                new JMenuItem("Gerar guia de autorização de transporte...");

        popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
                // Garante que ao clicar com o botão direito em um item para
                // exibir o menu, o único item selecionado da tabela será o item
                // clicado.
                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(mousePoint, table);
                final int rowAtPoint = table.rowAtPoint(mousePoint);
                if (rowAtPoint > -1) {
                    table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                }

                refreshPopupMenuItems();
            }

            @Override
            public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(final PopupMenuEvent e) {
            }
        });

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
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "Etapa", "Bem", "Sala de origem",
                        "Sala de destino", "Nº da guia de transporte" }) {
            private static final long serialVersionUID = -5079273255233169992L;
            Class<?>[] columnTypes = new Class[] { Integer.class, String.class,
                    String.class, String.class, String.class, String.class };

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
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMinWidth(175);
        table.getColumnModel().getColumn(3).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(4).setMinWidth(150);
        table.getColumnModel().getColumn(4).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(200);
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
        table.setComponentPopupMenu(popupMenu);
    }

    private void refreshPopupMenuItems() {
        // TODO Não deveria estar usando o Client diretamente. Usar o Presenter
        // para a comunicação com Model...
        Integer selectedMovId = getSelectedMovimentacoesIds().get(0);
        MovimentacaoDTO mov = Client.requestGetMovimentacaoById(selectedMovId);

        popupMenu.removeAll();
        popupMenu.add(popupItemVer);

        if (mov.getEtapa() == EtapaMovEnum.AGUARDANDO_AC_ENTRADA) {
            popupMenu.addSeparator();
            popupMenu.add(popupItemAceitarEntrada);
            popupMenu.add(popupItemNegarEntrada);
            popupMenu.add(popupItemCancelar);
        } else if (mov.getEtapa() == EtapaMovEnum.AGUARDANDO_AC_SAIDA) {
            popupMenu.addSeparator();
            popupMenu.add(popupItemAceitarSaida);
            popupMenu.add(popupItemNegarSaida);
            popupMenu.add(popupItemCancelar);
        } else if (mov.getEtapa() == EtapaMovEnum.EM_MOVIMENTACAO) {
            popupMenu.addSeparator();
            popupMenu.add(popupItemFinalizar);
            popupMenu.add(popupItemCancelar);
        }

        popupMenu.addSeparator();
        popupMenu.add(popupItemEventos);

        if (!mov.isParaMesmaCidade()) {
            popupMenu.add(popupItemGerarGuiaTransporte);
        }
    }

    @Override
    public void addVerMovimentacaoListener(final ActionListener listener) {
        popupItemVer.addActionListener(listener);
    }

    @Override
    public void addAceitarSaidaListener(final ActionListener listener) {
        popupItemAceitarSaida.addActionListener(listener);
    }

    @Override
    public void addNegarSaidaListener(final ActionListener listener) {
        popupItemNegarSaida.addActionListener(listener);
    }

    @Override
    public void addAceitarEntradaListener(final ActionListener listener) {
        popupItemAceitarEntrada.addActionListener(listener);
    }

    @Override
    public void addNegarEntradaListener(final ActionListener listener) {
        popupItemNegarEntrada.addActionListener(listener);
    }

    @Override
    public void addFinalizarListener(final ActionListener listener) {
        popupItemFinalizar.addActionListener(listener);
    }

    @Override
    public void addCancelarListener(final ActionListener listener) {
        popupItemCancelar.addActionListener(listener);
    }

    @Override
    public void addEventosMovimentacaoListener(final ActionListener listener) {
        popupItemEventos.addActionListener(listener);
    }

    @Override
    public void addGerarGuiaTransporte(final ActionListener listener) {
        popupItemGerarGuiaTransporte.addActionListener(listener);
    }

    @Override
    public void updateMovimentacoes(final List<MovimentacaoDTO> movimentacao) {
        final DefaultTableModel tableModel =
                (DefaultTableModel) table.getModel();
        tableModel.setNumRows(0);
        for (final MovimentacaoDTO m : movimentacao) {
            tableModel.addRow(new Object[] { m.getId(), m.getEtapa().getTexto(),
                    m.getBem().getDescricao(), m.getSalaOrigem().getNome(),
                    m.getSalaDestino().getNome(),
                    m.getNumGuiaTransporte() == null ? "Nenhum"
                            : m.getNumGuiaTransporte() });
        }

        // Deixa em ordem decrescente com base no ID, para que as movimentações
        // novas fiquem primeiro.
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
    public List<Integer> getSelectedMovimentacoesIds() {
        final List<Integer> selectedMovimentacoes = new ArrayList<Integer>();
        final int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            final int row = table.convertRowIndexToModel(selectedRows[i]);
            final Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedMovimentacoes.add(id);
        }
        return selectedMovimentacoes;
    }
}
