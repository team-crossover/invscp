package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.view.PredioView;

public class PredioSwingView extends JDialog implements PredioView {

    private static final long serialVersionUID = -3851382497211004322L;

    private final boolean isAdicionar;

    private Integer idPredio;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JList<Localizacao> listLocalizacoes;
    private JScrollPane scrollLocalizacoes;

    /**
     * @param departamento Prédios cujos valores serão exibidos inicialmente.
     *
     * @param isAdicionar Indica se a janela que será exibida será para adição de um novo predio
     *        (true) ou para atualização de um predio existente (false).
     */

    public PredioSwingView(final JFrame owner, final Predio predio, final boolean isAdicionar) {
        super(owner, isAdicionar ? "Adicionar prédio" : "Alterar prédio",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updatePredio(predio);
    }

    private void initialize() {
        setBounds(0, 0, 500, 300);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 15, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        final JLabel lblNome = new JLabel("Nome:");
        final GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.anchor = GridBagConstraints.EAST;
        gbc_lblNome.insets = new Insets(0, 0, 5, 5);
        gbc_lblNome.gridx = 1;
        gbc_lblNome.gridy = 1;
        getContentPane().add(lblNome, gbc_lblNome);

        fieldNome = new JTextField();
        final GridBagConstraints gbc_txtfldNome = new GridBagConstraints();
        gbc_txtfldNome.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldNome.gridx = 2;
        gbc_txtfldNome.gridy = 1;
        getContentPane().add(fieldNome, gbc_txtfldNome);
        fieldNome.setColumns(10);

        final JLabel lblChefe = new JLabel("Localização:");
        final GridBagConstraints gbc_lblChefe = new GridBagConstraints();
        gbc_lblChefe.anchor = GridBagConstraints.EAST;
        gbc_lblChefe.insets = new Insets(0, 0, 5, 5);
        gbc_lblChefe.gridx = 1;
        gbc_lblChefe.gridy = 2;
        getContentPane().add(lblChefe, gbc_lblChefe);

        final ListCellRenderer<? super Localizacao> localizacaoListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 337686638048057981L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value,
                    final int index, final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum", index, isSelected,
                            cellHasFocus);
                } else {
                    final String nome = ((Localizacao) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index, isSelected,
                            cellHasFocus);
                }
            }
        };

        scrollLocalizacoes = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 2;
        getContentPane().add(scrollLocalizacoes, gbc_scrollPane);

        listLocalizacoes = new JList<Localizacao>();
        listLocalizacoes.setModel(new DefaultListModel<Localizacao>());
        listLocalizacoes.setCellRenderer(localizacaoListRenderer);
        scrollLocalizacoes.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollLocalizacoes.setViewportView(listLocalizacoes);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 4;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 4;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updatePredio(final Predio predio) {
        if (predio == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idPredio = predio.getId();

        fieldNome.setText(predio.getNome());

        // Exibe as localizacoes na lista de localizacoes e seleciona o atual, se
        // tiver, ou 'Nenhum', se não tiver.
        final List<Localizacao> localizacoes = predio.getPossiveisLocalizacoes();
        final Localizacao localizacao = predio.getLocalizacao();
        final DefaultListModel<Localizacao> listLocalizacoesModel = (DefaultListModel<Localizacao>) listLocalizacoes
                .getModel();
        listLocalizacoesModel.clear();
        if (localizacao == null) {
            listLocalizacoesModel.add(0, null);
            listLocalizacoes.setSelectedIndex(0);
        } else {
            listLocalizacoesModel.add(0, localizacao);
            listLocalizacoes.setSelectedIndex(0);
        }
        for (final Localizacao l : localizacoes) {
            if (localizacao != null && !l.getId().equals(localizacao.getId())) {
                listLocalizacoesModel.addElement(l);
            }
        }

        revalidate();
        repaint();
    }

    @Override
    public void showError(final String message) {
        final String titulo = "Erro";
        final String messageCompleta = "Erro: " + message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo, JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void showSucesso() {
        final String titulo = "Sucesso";
        final String messageCompleta = isAdicionar ? "Predio adicionado!" : "Predio alterado!";
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void showInfo(final String message) {
        final String titulo = "Informação";
        JOptionPane.showMessageDialog(this, message, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public Predio getPredio() {
        final Predio predio = new Predio();
        predio.setId(idPredio);
        predio.setNome(fieldNome.getText());
        predio.setIdLocalizacao(listLocalizacoes.getSelectedValue() == null ? null
                : listLocalizacoes.getSelectedValue().getId());

        return predio;
    }

}
