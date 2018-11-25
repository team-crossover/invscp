package com.github.nelsonwilliam.invscp.client.view.swing;

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
import javax.swing.SwingConstants;

import com.github.nelsonwilliam.invscp.client.view.BaixaView;
import com.github.nelsonwilliam.invscp.shared.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.MotivoBaixaEnum;

public class BaixaSwingView extends JDialog implements BaixaView {

    private static final long serialVersionUID = -5273196120052445323L;

    private final boolean isAdicionar;

    private Integer idBaixa;
    private Integer idBem;
    private Integer idResponsavel;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblDataDaBaixa;
    private JLabel fieldData;
    private JLabel lblResponsvel;
    private JLabel fieldFuncionario;
    private JLabel lblObservaes;
    private JTextPane fieldObservacoes;
    private JComboBox<MotivoBaixaEnum> comboMotivo;
    private JLabel lblMotivo;
    private JLabel lblBem;
    private JLabel fieldBem;

    public BaixaSwingView(final JFrame owner, final BaixaDTO baixa,
            final boolean isAdicionar) {

        super(owner, isAdicionar ? "Adicionar baixa" : "Ver baixa",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateBaixa(baixa);
    }

    private void initialize() {
        setBounds(0, 0, 500, 379);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25 };
        gridBagLayout.rowHeights =
                new int[] { 25, 25, 25, 25, 0, 0, 25, 0, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblBem = new JLabel("Bem:");
        final GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.fill = GridBagConstraints.VERTICAL;
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 1;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        final GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.fill = GridBagConstraints.VERTICAL;
        gbc_fieldBem.anchor = GridBagConstraints.WEST;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 1;
        getContentPane().add(fieldBem, gbc_fieldBem);

        lblDataDaBaixa = new JLabel("Data da baixa:");
        final GridBagConstraints gbc_lblDataDaBaixa = new GridBagConstraints();
        gbc_lblDataDaBaixa.anchor = GridBagConstraints.EAST;
        gbc_lblDataDaBaixa.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDaBaixa.gridx = 1;
        gbc_lblDataDaBaixa.gridy = 2;
        getContentPane().add(lblDataDaBaixa, gbc_lblDataDaBaixa);

        fieldData = new JLabel("");
        final GridBagConstraints gbc_fieldData = new GridBagConstraints();
        gbc_fieldData.anchor = GridBagConstraints.WEST;
        gbc_fieldData.insets = new Insets(0, 0, 5, 5);
        gbc_fieldData.gridx = 2;
        gbc_fieldData.gridy = 2;
        getContentPane().add(fieldData, gbc_fieldData);

        lblResponsvel = new JLabel("Responsável:");
        final GridBagConstraints gbc_lblResponsvel = new GridBagConstraints();
        gbc_lblResponsvel.anchor = GridBagConstraints.EAST;
        gbc_lblResponsvel.insets = new Insets(0, 0, 5, 5);
        gbc_lblResponsvel.gridx = 1;
        gbc_lblResponsvel.gridy = 3;
        getContentPane().add(lblResponsvel, gbc_lblResponsvel);

        fieldFuncionario = new JLabel("");
        fieldFuncionario.setHorizontalAlignment(SwingConstants.TRAILING);
        final GridBagConstraints gbc_fieldFuncionario = new GridBagConstraints();
        gbc_fieldFuncionario.anchor = GridBagConstraints.WEST;
        gbc_fieldFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFuncionario.gridx = 2;
        gbc_fieldFuncionario.gridy = 3;
        getContentPane().add(fieldFuncionario, gbc_fieldFuncionario);

        lblMotivo = new JLabel("Motivo:");
        final GridBagConstraints gbc_motivo = new GridBagConstraints();
        gbc_motivo.anchor = GridBagConstraints.EAST;
        gbc_motivo.insets = new Insets(0, 0, 5, 5);
        gbc_motivo.gridx = 1;
        gbc_motivo.gridy = 4;
        getContentPane().add(lblMotivo, gbc_motivo);

        final ListCellRenderer<? super MotivoBaixaEnum> MotivoBaixaListRenderer = new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            7367925493420821685L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((MotivoBaixaEnum) value).getTexto();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        comboMotivo = new JComboBox<MotivoBaixaEnum>();
        comboMotivo.setModel(new DefaultComboBoxModel<MotivoBaixaEnum>(
                MotivoBaixaEnum.values()));
        comboMotivo.setRenderer(MotivoBaixaListRenderer);
        final GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 4;
        getContentPane().add(comboMotivo, gbc_textField);

        lblObservaes = new JLabel("Observações:");
        final GridBagConstraints gbc_lblObservaes = new GridBagConstraints();
        gbc_lblObservaes.fill = GridBagConstraints.VERTICAL;
        gbc_lblObservaes.anchor = GridBagConstraints.EAST;
        gbc_lblObservaes.insets = new Insets(0, 0, 5, 5);
        gbc_lblObservaes.gridx = 1;
        gbc_lblObservaes.gridy = 5;
        getContentPane().add(lblObservaes, gbc_lblObservaes);

        fieldObservacoes = new JTextPane();
        final GridBagConstraints gbc_fieldObservacoes = new GridBagConstraints();
        gbc_fieldObservacoes.insets = new Insets(0, 0, 5, 5);
        gbc_fieldObservacoes.fill = GridBagConstraints.BOTH;
        gbc_fieldObservacoes.gridx = 2;
        gbc_fieldObservacoes.gridy = 5;
        fieldObservacoes
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        getContentPane().add(fieldObservacoes, gbc_fieldObservacoes);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 7;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 7;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateBaixa(final BaixaDTO baixa) {
        if (baixa == null) {
            throw new NullPointerException();
        }

        // Apenas pode alterar baixa se ela estiver sendo adicionada agora
        btnConfirmar.setEnabled(isAdicionar);
        fieldBem.setEnabled(isAdicionar);
        fieldData.setEnabled(isAdicionar);
        fieldFuncionario.setEnabled(isAdicionar);
        comboMotivo.setEnabled(isAdicionar);
        fieldObservacoes.setEnabled(isAdicionar);

        // O ID da baixa sendo exibido é armazenado para que seja
        // possível retorná-la na hora de salvar o departamento.
        idBaixa = baixa.getId();
        idBem = baixa.getBem().getId();
        idResponsavel = baixa.getFuncionario().getId();

        fieldData.setText(
                    baixa.getData().format(DateTimeFormatter.ISO_DATE));
        fieldFuncionario.setText(baixa.getFuncionario().getNome());
        comboMotivo.setSelectedItem(baixa.getMotivo());
        fieldObservacoes.setText(baixa.getObservacoes());
        fieldBem.setText(baixa.getBem().getDescricao());
        fieldFuncionario.setText(baixa.getFuncionario().getNome());

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
        final String messageCompleta = isAdicionar ? "Baixa adicionada!"
                : "Baixa alterada!";
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
    public BaixaDTO getBaixa() {
        final BaixaDTO baixa = new BaixaDTO();
        baixa.setId(idBaixa);
        baixa.setMotivo((MotivoBaixaEnum) comboMotivo.getSelectedItem());
        baixa.setObservacoes(fieldObservacoes.getText());

        final BemDTO placeholderBem = new BemDTO();
        placeholderBem.setId(idBem);
        baixa.setBem(placeholderBem);

        final FuncionarioDTO placeholderFunc = new FuncionarioDTO();
        placeholderFunc.setId(idResponsavel);
        baixa.setFuncionario(placeholderFunc);

        try {
            baixa.setData(LocalDate.parse(fieldData.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data da baixa é inválido.");
            return null;
        }

        return baixa;
    }

}
