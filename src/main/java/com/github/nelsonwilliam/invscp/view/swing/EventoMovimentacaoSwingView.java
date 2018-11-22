package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoMovEnum;
import com.github.nelsonwilliam.invscp.view.EventoMovimentacaoView;

public class EventoMovimentacaoSwingView extends JDialog
        implements EventoMovimentacaoView {

    private static final long serialVersionUID = -1119086918035833897L;

    private final boolean isAdicionar;

    private Integer idEventoMovimentacao;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JComboBox<TipoMovEnum> comboTipo;
    private JLabel lblTipo;
    private JLabel lblData;
    private JLabel lblJustificativa;
    private JTextPane fieldJustificativa;
    private JLabel fieldData;

    public EventoMovimentacaoSwingView(final JFrame owner,
            final EventoMovimentacaoDTO eventoMovimentacao,
            final boolean isAdicionar) {

        super(owner,
                isAdicionar ? "Adicionar evento de movimentação"
                        : "Ver evento de movimentação",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateEventoMovimentacao(eventoMovimentacao);
    }

    private void initialize() {
        setBounds(0, 0, 500, 379);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25 };
        gridBagLayout.rowHeights = new int[] { 25, 0, 0, 99, 25, 0, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
                0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblTipo = new JLabel("Tipo:");
        final GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipo.gridx = 1;
        gbc_lblTipo.gridy = 1;
        getContentPane().add(lblTipo, gbc_lblTipo);

        final ListCellRenderer<? super TipoMovEnum> TipoMovimentacaoListRenderer = new DefaultListCellRenderer() {

            private static final long serialVersionUID = 5767610022451495626L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((TipoMovEnum) value).getTexto();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        comboTipo = new JComboBox<TipoMovEnum>();
        comboTipo.setModel(
                new DefaultComboBoxModel<TipoMovEnum>(TipoMovEnum.values()));
        comboTipo.setRenderer(TipoMovimentacaoListRenderer);
        final GridBagConstraints gbc_comboTipo = new GridBagConstraints();
        gbc_comboTipo.insets = new Insets(0, 0, 5, 5);
        gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboTipo.gridx = 2;
        gbc_comboTipo.gridy = 1;
        getContentPane().add(comboTipo, gbc_comboTipo);

        lblData = new JLabel("Data:");
        final GridBagConstraints gbc_lblData = new GridBagConstraints();
        gbc_lblData.anchor = GridBagConstraints.EAST;
        gbc_lblData.insets = new Insets(0, 0, 5, 5);
        gbc_lblData.gridx = 1;
        gbc_lblData.gridy = 2;
        getContentPane().add(lblData, gbc_lblData);

        fieldData = new JLabel("");
        GridBagConstraints gbc_fieldData = new GridBagConstraints();
        gbc_fieldData.anchor = GridBagConstraints.WEST;
        gbc_fieldData.insets = new Insets(0, 0, 5, 5);
        gbc_fieldData.gridx = 2;
        gbc_fieldData.gridy = 2;
        getContentPane().add(fieldData, gbc_fieldData);

        lblJustificativa = new JLabel("Justificativa:");
        final GridBagConstraints gbc_lblJustificativa = new GridBagConstraints();
        gbc_lblJustificativa.anchor = GridBagConstraints.EAST;
        gbc_lblJustificativa.insets = new Insets(0, 0, 5, 5);
        gbc_lblJustificativa.gridx = 1;
        gbc_lblJustificativa.gridy = 3;
        getContentPane().add(lblJustificativa, gbc_lblJustificativa);

        fieldJustificativa = new JTextPane();
        final GridBagConstraints gbc_fieldJustificativa = new GridBagConstraints();
        gbc_fieldJustificativa.insets = new Insets(0, 0, 5, 5);
        gbc_fieldJustificativa.fill = GridBagConstraints.BOTH;
        gbc_fieldJustificativa.gridx = 2;
        gbc_fieldJustificativa.gridy = 3;
        fieldJustificativa
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        getContentPane().add(fieldJustificativa, gbc_fieldJustificativa);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 5;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 5;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateEventoMovimentacao(
            final EventoMovimentacaoDTO eventoMovimentacao) {
        if (eventoMovimentacao == null) {
            throw new NullPointerException();
        }

        // Apenas pode alterar baixa se ela estiver sendo adicionada agora
        btnConfirmar.setEnabled(isAdicionar);
        fieldData.setEnabled(isAdicionar);
        fieldJustificativa.setEnabled(isAdicionar);
        comboTipo.setEnabled(isAdicionar);

        idEventoMovimentacao = eventoMovimentacao.getId();

        fieldData.setText(eventoMovimentacao.getData()
                .format(DateTimeFormatter.ISO_DATE));

        comboTipo.setSelectedItem(eventoMovimentacao.getTipo());
        fieldJustificativa.setText(eventoMovimentacao.getJustificativa());

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
    public void showSucesso() {
        final String titulo = "Sucesso";
        final String messageCompleta = isAdicionar
                ? "Evento de movimentação adicionado!"
                : "Evento de movimentação alterado!";
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
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public EventoMovimentacaoDTO getEventoMovimentacao() {
        final EventoMovimentacaoDTO eventoMovimentacao = new EventoMovimentacaoDTO();
        eventoMovimentacao.setId(idEventoMovimentacao);
        eventoMovimentacao.setJustificativa(fieldJustificativa.getText());

        try {
            eventoMovimentacao.setData(LocalDate.parse(fieldData.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data da baixa é inválido.");
            return null;
        }

        // eventoMovimentacao.setFuncionario(funcionario);
        // eventoMovimentacao.setMovimentacao(movimentacao);

        return eventoMovimentacao;
    }

}
