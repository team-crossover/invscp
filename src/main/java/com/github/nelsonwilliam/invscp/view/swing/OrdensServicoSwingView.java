package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.view.OrdensServicoView;

public class OrdensServicoSwingView extends JDialog
        implements OrdensServicoView {

    private static final long serialVersionUID = -8909697493196510494L;

    private JTable table;
    private JButton btnAdicionar;
    private JPopupMenu popupMenu;
    private JMenuItem popupItemAlterar;
    private JMenuItem popupItemConcluir;

    public OrdensServicoSwingView(final JFrame owner) {
        super(owner);
        initialize();
    }

    private void initialize() {
        setTitle("Ordens de serviço");
        setBounds(0, 0, 700, 300);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 15, 0, 199, 249, 0, 15 };
        gridBagLayout.rowHeights = new int[] { 15, 422, 25, 15, 0 };
        gridBagLayout.columnWeights =
                new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
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
                new String[] { "ID", "Data de cadastro", "Data de conclusão",
                        "Valor", "Situação", "Responsável" }) {

            private static final long serialVersionUID = 780952094315363108L;
            Class<?>[] columnTypes = new Class[] { Integer.class,
                    String.class, String.class, Float.class, String.class,
                    String.class };

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
        table.getColumnModel().getColumn(1).setMinWidth(125);
        table.getColumnModel().getColumn(1).setMaxWidth(125);
        table.getColumnModel().getColumn(2).setMinWidth(125);
        table.getColumnModel().getColumn(2).setMaxWidth(125);
        table.getColumnModel().getColumn(3).setMinWidth(125);
        table.getColumnModel().getColumn(3).setMaxWidth(125);
        table.getColumnModel().getColumn(4).setMinWidth(150);
        table.getColumnModel().getColumn(4).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setMinWidth(200);
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
        popupItemConcluir = new JMenuItem("Concluir");

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
        popupMenu.add(popupItemAlterar);
        popupMenu.addSeparator();
        popupMenu.add(popupItemConcluir);
        table.setComponentPopupMenu(popupMenu);

        btnAdicionar = new JButton("Adicionar novo");
        final GridBagConstraints gbc_btnAdicionar = new GridBagConstraints();
        gbc_btnAdicionar.gridwidth = 2;
        gbc_btnAdicionar.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnAdicionar.insets = new Insets(0, 0, 5, 5);
        gbc_btnAdicionar.gridx = 1;
        gbc_btnAdicionar.gridy = 2;
        getContentPane().add(btnAdicionar, gbc_btnAdicionar);
    }

    @Override
    public void addAdicionarOrdemServicoListener(
            final ActionListener listener) {
        btnAdicionar.addActionListener(listener);

    }

    @Override
    public void addAlterarOrdemServicoListener(final ActionListener listener) {
        popupItemAlterar.addActionListener(listener);

    }

    @Override
    public void addConcluirOrdemServicoListener(final ActionListener listener) {
        popupItemConcluir.addActionListener(listener);

    }

    @Override
    public void updateOrdensServico(final BemDTO bem,
            final List<OrdemServicoDTO> ordemServico) {

        setTitle("Ordens de serviço de " + bem.getDescricao());

        // Apenas pode adicionar nova OS se o bem estiver incorporado.
        btnAdicionar
                .setEnabled(bem.getSituacao() == BemSituacaoEnum.INCORPORADO);

        final DefaultTableModel tableModel = (DefaultTableModel) table
                .getModel();
        tableModel.setNumRows(0);
        for (final OrdemServicoDTO o : ordemServico) {
            tableModel.addRow(new Object[] { o.getId(),
                    o.getDataCadastro().format(DateTimeFormatter.ISO_DATE),
                    o.getDataConclusao() == null ? "Pendente"
                            : o.getDataConclusao()
                                    .format(DateTimeFormatter.ISO_DATE),
                    o.getValor(), o.getSituacao().getTexto(),
                    o.getFuncionario().getNome() });
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
    public List<Integer> getSelectedOrdensServicoIds() {
        final List<Integer> selectedOrdensServico = new ArrayList<Integer>();
        final int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            final int row = table.convertRowIndexToModel(selectedRows[i]);
            final Integer id = (Integer) table.getModel().getValueAt(row, 0);
            selectedOrdensServico.add(id);
        }
        return selectedOrdensServico;
    }

}
