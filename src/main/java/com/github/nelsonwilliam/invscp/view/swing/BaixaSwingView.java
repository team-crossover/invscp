package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.view.BaixaView;

public class BaixaSwingView extends JDialog implements BaixaView {

    private static final long serialVersionUID = -1305178034887973868L;

    private final boolean isAdicionar;

    private Integer idBem;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JTextField fieldCidade;
    private JTextField fieldCEP;
    private JTextPane fieldEndereco;
    private JComboBox<UFEnum> comboUf;

    /**
     * @param localizacao Localizações cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova localização (true) ou para atualização de uma
     *        localização existente (false).
     */
    public LocalizacaoSwingView(final JFrame owner,
            final LocalizacaoDTO localizacao, final boolean isAdicionar) {

        super(owner,
                isAdicionar ? "Adicionar localizacao" : "Alterar localizacao",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateLocalizacao(localizacao);
    }

    private void initialize() {
        setBounds(0, 0, 500, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 0, 0, 30, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0 };
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

        final JLabel lblEndereco = new JLabel("Endereco:");
        final GridBagConstraints gbc_lblEndereco = new GridBagConstraints();
        gbc_lblEndereco.insets = new Insets(0, 0, 5, 5);
        gbc_lblEndereco.anchor = GridBagConstraints.EAST;
        gbc_lblEndereco.gridx = 1;
        gbc_lblEndereco.gridy = 2;
        getContentPane().add(lblEndereco, gbc_lblEndereco);

        fieldEndereco = new JTextPane();
        final GridBagConstraints gbc_txtfldEndereco = new GridBagConstraints();
        gbc_txtfldEndereco.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldEndereco.fill = GridBagConstraints.BOTH;
        gbc_txtfldEndereco.gridx = 2;
        gbc_txtfldEndereco.gridy = 2;
        getContentPane().add(fieldEndereco, gbc_txtfldEndereco);
        fieldEndereco.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        final JLabel lblCep = new JLabel("CEP:");
        final GridBagConstraints gbc_lblCep = new GridBagConstraints();
        gbc_lblCep.insets = new Insets(0, 0, 5, 5);
        gbc_lblCep.anchor = GridBagConstraints.EAST;
        gbc_lblCep.gridx = 1;
        gbc_lblCep.gridy = 3;
        getContentPane().add(lblCep, gbc_lblCep);

        fieldCEP = new JTextField();
        fieldCEP.setText((String) null);
        fieldCEP.setColumns(10);
        final GridBagConstraints gbc_fieldCEP = new GridBagConstraints();
        gbc_fieldCEP.insets = new Insets(0, 0, 5, 5);
        gbc_fieldCEP.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldCEP.gridx = 2;
        gbc_fieldCEP.gridy = 3;
        getContentPane().add(fieldCEP, gbc_fieldCEP);

        final JLabel lblCidade = new JLabel("Cidade:");
        final GridBagConstraints gbc_lblCidade = new GridBagConstraints();
        gbc_lblCidade.insets = new Insets(0, 0, 5, 5);
        gbc_lblCidade.anchor = GridBagConstraints.EAST;
        gbc_lblCidade.gridx = 1;
        gbc_lblCidade.gridy = 4;
        getContentPane().add(lblCidade, gbc_lblCidade);

        fieldCidade = new JTextField();
        fieldCidade.setText((String) null);
        fieldCidade.setColumns(10);
        final GridBagConstraints gbc_fieldCidade = new GridBagConstraints();
        gbc_fieldCidade.insets = new Insets(0, 0, 5, 5);
        gbc_fieldCidade.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldCidade.gridx = 2;
        gbc_fieldCidade.gridy = 4;
        getContentPane().add(fieldCidade, gbc_fieldCidade);

        final JLabel lblUf = new JLabel("UF:");
        final GridBagConstraints gbc_lblUf = new GridBagConstraints();
        gbc_lblUf.insets = new Insets(0, 0, 5, 5);
        gbc_lblUf.anchor = GridBagConstraints.EAST;
        gbc_lblUf.gridx = 1;
        gbc_lblUf.gridy = 5;
        getContentPane().add(lblUf, gbc_lblUf);

        comboUf = new JComboBox<UFEnum>();
        comboUf.setModel(new DefaultComboBoxModel<UFEnum>(UFEnum.values()));
        final GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 5;
        getContentPane().add(comboUf, gbc_comboBox);

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
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateLocalizacao(final LocalizacaoDTO localizacao) {
        if (localizacao == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idLocalizacao = localizacao.getId();

        fieldNome.setText(localizacao.getNome());
        fieldEndereco.setText(localizacao.getEndereco());
        fieldCEP.setText(localizacao.getCep());
        fieldCidade.setText(localizacao.getCidade());
        comboUf.setSelectedItem(localizacao.getUf());
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
        final String messageCompleta = isAdicionar ? "Localizacao adicionada!"
                : "Localizacao alterada!";
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
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public LocalizacaoDTO getLocalizacao() {
        final LocalizacaoDTO localizacao = new LocalizacaoDTO();
        localizacao.setId(idLocalizacao);
        localizacao.setNome(fieldNome.getText());
        localizacao.setEndereco(fieldEndereco.getText());
        localizacao.setCidade(fieldCidade.getText());
        localizacao.setCep(fieldCEP.getText());
        localizacao.setUf((UFEnum) comboUf.getSelectedItem());
        return localizacao;
    }

}
