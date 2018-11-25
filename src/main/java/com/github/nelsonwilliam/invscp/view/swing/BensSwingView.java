package com.github.nelsonwilliam.invscp.view.swing;

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

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.BensView;

public class BensSwingView extends JPanel implements BensView {

    private static final long serialVersionUID = 5043497852744311548L;

    private JTable table;
    private JButton btnAdicionar;
    private JButton btnDeletar;
    private JButton btnGerarInventario;
    private JPopupMenu popupMenu; // Popup exibido ao clicar com o botão direito
                                  // em um item da tabela
    private JMenuItem popupItemAlterar;
    private JMenuItem popupItemMover;
    private JMenuItem popupItemBaixa;
    private JMenuItem popupItemOrdemServico;
    private JMenuItem popupItemHistorico;
    private JPanel panel;

    public BensSwingView() {
        initialize();
    }

    private void initialize() {
        setBounds(0, 0, 673, 500);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 199, 249 };
        gridBagLayout.rowHeights = new int[] { 422, 25, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0 };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        final JScrollPane scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        add(scrollPane, gbc_scrollPane);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "Número de tombamento", "Descrição",
                        "Situação", "Sala", "Departamento",
                        "Grupo material" }) {
            private static final long serialVersionUID = -5025798173394078963L;
            Class<?>[] columnTypes = new Class[] { Integer.class, Long.class,
                    String.class, BemSituacaoEnum.class, SalaDTO.class,
                    DepartamentoDTO.class, GrupoMaterialDTO.class };

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
        table.getColumnModel().getColumn(1).setMinWidth(175);
        table.getColumnModel().getColumn(1).setMaxWidth(175);
        table.getColumnModel().getColumn(2).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(4).setMinWidth(150);
        table.getColumnModel().getColumn(4).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(200);
        table.getColumnModel().getColumn(6).setMinWidth(200);
        table.getColumnModel().getColumn(6).setMaxWidth(200);
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
        popupItemMover = new JMenuItem("Mover");
        popupItemBaixa = new JMenuItem("Baixa...");
        popupItemOrdemServico = new JMenuItem("Ordens de serviço...");
        popupItemHistorico = new JMenuItem("Gerar histórico...");

        popupMenu = new JPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
                // Garante que ao clicar com o botão direito em um item para
                // exibir o menu, o único item selecionado da tabela será o item
                // clicado.
                final Point mousePoint = MouseInfo.getPointerInfo().getLocation();
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
        table.setComponentPopupMenu(popupMenu);

        panel = new JPanel();
        final GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.WEST;
        gbc_panel.fill = GridBagConstraints.VERTICAL;
        gbc_panel.insets = new Insets(0, 0, 0, 5);
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        add(panel, gbc_panel);

        final GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 135, 135, 0 };
        gbl_panel.rowHeights = new int[] { 25, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        btnAdicionar = new JButton("Adicionar novo");
        final GridBagConstraints gbc_btnAdicionar = new GridBagConstraints();
        gbc_btnAdicionar.anchor = GridBagConstraints.WEST;
        gbc_btnAdicionar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAdicionar.gridx = 0;
        gbc_btnAdicionar.gridy = 0;
        panel.add(btnAdicionar, gbc_btnAdicionar);

        btnGerarInventario = new JButton("Gerar inventário");
        final GridBagConstraints gbc_btnInventario = new GridBagConstraints();
        gbc_btnInventario.anchor = GridBagConstraints.WEST;
        gbc_btnInventario.gridx = 1;
        gbc_btnInventario.gridy = 0;
        panel.add(btnGerarInventario, gbc_btnInventario);

        btnDeletar = new JButton("Deletar selecionado(s)");
        final GridBagConstraints gbc_btnDeletar = new GridBagConstraints();
        gbc_btnDeletar.anchor = GridBagConstraints.SOUTHEAST;
        gbc_btnDeletar.gridx = 2;
        gbc_btnDeletar.gridy = 1;
        add(btnDeletar, gbc_btnDeletar);
    }

    private void refreshPopupMenuItems() {
        // TODO Não deveria estar usando o Client diretamente. Usar o Presenter
        // para a comunicação com Model...
        final Integer selectedBemId = getSelectedBensIds().get(0);
        final BemDTO bem = Client.requestGetBemById(selectedBemId);

        popupMenu.removeAll();

        popupMenu.add(popupItemAlterar);
        popupMenu.add(popupItemMover);
        popupMenu.addSeparator();
        popupMenu.add(popupItemBaixa);
        popupMenu.add(popupItemOrdemServico);
        popupMenu.addSeparator();
        popupMenu.add(popupItemHistorico);

        popupItemAlterar.setEnabled(bem.getSituacao() == BemSituacaoEnum.INCORPORADO);
        popupItemMover.setEnabled(bem.getSituacao() == BemSituacaoEnum.INCORPORADO);
        popupItemBaixa.setEnabled(bem.getSituacao() == BemSituacaoEnum.BAIXADO
                || bem.getSituacao() == BemSituacaoEnum.INCORPORADO);
        popupItemBaixa.setText(bem.getSituacao() == BemSituacaoEnum.BAIXADO ? "Ver baixa..." : "Realizar baixa...");
    }

    @Override
    public void addGerarHistoricoListener(final ActionListener listener) {
        popupItemHistorico.addActionListener(listener);

    }

    @Override
    public void addAdicionarBemListener(final ActionListener listener) {
        btnAdicionar.addActionListener(listener);

    }

    @Override
    public void addDeletarBensListener(final ActionListener listener) {
        btnDeletar.addActionListener(listener);

    }

    @Override
    public void addAlterarBemListener(final ActionListener listener) {
        popupItemAlterar.addActionListener(listener);

    }

    @Override
    public void addMoverBemListener(final ActionListener listener) {
        popupItemMover.addActionListener(listener);

    }

    @Override
    public void addBaixarBemListener(final ActionListener listener) {
        popupItemBaixa.addActionListener(listener);

    }

    @Override
    public void addOrdemServicoListener(final ActionListener listener) {
        popupItemOrdemServico.addActionListener(listener);

    }

    @Override
    public void addGerarInventarioListener(final ActionListener listener) {
        btnGerarInventario.addActionListener(listener);

    }

    @Override
    public void updateBens(final List<BemDTO> bens) {
        final DefaultTableModel tableModel =
                (DefaultTableModel) table.getModel();
        tableModel.setNumRows(0);
        for (final BemDTO b : bens) {
            tableModel.addRow(new Object[] { b.getId(), b.getNumeroTombamento(),
                    b.getDescricao(),
                    b.getSituacao() == null ? "Nenhuma"
                            : b.getSituacao().getTexto(),
                    b.getSala() == null ? "Nenhuma" : b.getSala().getNome(),
                    b.getDepartamento() == null ? "Nenhum"
                            : b.getDepartamento().getNome(),
                    b.getGrupoMaterial() == null ? "Nenhum"
                            : b.getGrupoMaterial().getNome() });
        }

        // Deixa em ordem crescente com base no número de tombamento.
        table.getRowSorter().toggleSortOrder(1);

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
    public List<Integer> getSelectedBensIds() {
        final List<Integer> selectedBens = new ArrayList<Integer>();
        final int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            final int row = table.convertRowIndexToModel(selectedRows[i]);
            final Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedBens.add(id);
        }
        return selectedBens;
    }

}
