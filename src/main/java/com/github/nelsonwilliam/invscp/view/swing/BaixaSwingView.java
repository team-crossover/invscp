package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.view.BaixaView;

public class BaixaSwingView extends JDialog implements BaixaView {

    private static final long serialVersionUID = -5273196120052445323L;

    private final boolean isAdicionar;

    private Integer idBaixa;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblDataDaBaixa;
    private JLabel fieldData;
    private JLabel lblResponsvel;
    private JLabel fieldFuncionario;
    private JLabel lblObservaes;
    private JTextField fieldObservacoes;
    private JTextField fieldMotivo;
    private JLabel lblMotivo;
    private JLabel lblBem;
    private JLabel fieldBem;

    /**
     * @param baixa Baixas cujos valores serão exibidos inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova baixa (true) ou para atualização de uma baixa
     *        existente (false).
     */

    public BaixaSwingView(final JFrame owner, final BaixaDTO baixa,
            final boolean isAdicionar, final List<BemDTO> bens) {

        super(owner, isAdicionar ? "Adicionar baixa" : "Alterar baixa",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateBaixa(baixa, bens);
    }

    private void initialize() {
        setBounds(0, 0, 500, 379);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 0, -87, 0, 0, 0, 0,
                15, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblBem = new JLabel("Bem:");
        GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 1;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.anchor = GridBagConstraints.WEST;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 1;
        getContentPane().add(fieldBem, gbc_fieldBem);

        lblDataDaBaixa = new JLabel("Data da baixa:");
        GridBagConstraints gbc_lblDataDaBaixa = new GridBagConstraints();
        gbc_lblDataDaBaixa.anchor = GridBagConstraints.EAST;
        gbc_lblDataDaBaixa.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDaBaixa.gridx = 1;
        gbc_lblDataDaBaixa.gridy = 3;
        getContentPane().add(lblDataDaBaixa, gbc_lblDataDaBaixa);

        fieldData = new JLabel("");
        GridBagConstraints gbc_fieldData = new GridBagConstraints();
        gbc_fieldData.anchor = GridBagConstraints.WEST;
        gbc_fieldData.insets = new Insets(0, 0, 5, 5);
        gbc_fieldData.gridx = 2;
        gbc_fieldData.gridy = 3;
        getContentPane().add(fieldData, gbc_fieldData);

        lblResponsvel = new JLabel("Responsável:");
        GridBagConstraints gbc_lblResponsvel = new GridBagConstraints();
        gbc_lblResponsvel.anchor = GridBagConstraints.EAST;
        gbc_lblResponsvel.insets = new Insets(0, 0, 5, 5);
        gbc_lblResponsvel.gridx = 1;
        gbc_lblResponsvel.gridy = 5;
        getContentPane().add(lblResponsvel, gbc_lblResponsvel);

        fieldFuncionario = new JLabel("");
        fieldFuncionario.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_fieldFuncionario = new GridBagConstraints();
        gbc_fieldFuncionario.anchor = GridBagConstraints.WEST;
        gbc_fieldFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFuncionario.gridx = 2;
        gbc_fieldFuncionario.gridy = 5;
        getContentPane().add(fieldFuncionario, gbc_fieldFuncionario);

        lblMotivo = new JLabel("Motivo:");
        GridBagConstraints gbc_motivo = new GridBagConstraints();
        gbc_motivo.anchor = GridBagConstraints.EAST;
        gbc_motivo.insets = new Insets(0, 0, 5, 5);
        gbc_motivo.gridx = 1;
        gbc_motivo.gridy = 7;
        getContentPane().add(lblMotivo, gbc_motivo);

        fieldMotivo = new JTextField();
        fieldMotivo.setColumns(10);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 7;
        getContentPane().add(fieldMotivo, gbc_textField);

        lblObservaes = new JLabel("Observações:");
        GridBagConstraints gbc_lblObservaes = new GridBagConstraints();
        gbc_lblObservaes.anchor = GridBagConstraints.EAST;
        gbc_lblObservaes.insets = new Insets(0, 0, 5, 5);
        gbc_lblObservaes.gridx = 1;
        gbc_lblObservaes.gridy = 9;
        getContentPane().add(lblObservaes, gbc_lblObservaes);

        fieldObservacoes = new JTextField();
        GridBagConstraints gbc_fieldObservacoes = new GridBagConstraints();
        gbc_fieldObservacoes.insets = new Insets(0, 0, 5, 5);
        gbc_fieldObservacoes.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldObservacoes.gridx = 2;
        gbc_fieldObservacoes.gridy = 9;
        getContentPane().add(fieldObservacoes, gbc_fieldObservacoes);
        fieldObservacoes.setColumns(10);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 11;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 11;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateBaixa(final BaixaDTO baixa, final List<BemDTO> bens) {

        if (baixa == null) {
            throw new NullPointerException();
        }

        // O ID da baixa sendo exibido é armazenado para que seja
        // possível
        // retorná-la na hora de salvar o departamento.
        idBaixa = baixa.getId();

        fieldData.setText(baixa.getData().toString());
        fieldFuncionario.setText(baixa.getFuncionario().getNome());
        fieldMotivo.setText(baixa.getMotivo());
        fieldObservacoes.setText(baixa.getObservacoes());
        fieldBem.setText(baixa.getBem().getDescricao());

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
        // baixa.setBem(fieldBem.getText());
        // baixa.setFuncionario(fieldFuncionario.getText());
        baixa.setData(LocalDate.parse(fieldData.getText(),
                DateTimeFormatter.BASIC_ISO_DATE));
        baixa.setMotivo(fieldMotivo.getText());
        baixa.setObservacoes(fieldObservacoes.getText());
        return baixa;
    }

}
