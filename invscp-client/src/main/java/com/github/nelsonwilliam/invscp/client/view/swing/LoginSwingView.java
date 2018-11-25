package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.nelsonwilliam.invscp.client.view.LoginView;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LoginSwingView extends JDialog implements LoginView {

    private static final long serialVersionUID = -5241111701730056643L;

    private JButton btnConfirm;
    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginSwingView(final JFrame owner) {
        super(owner, "Login", ModalityType.APPLICATION_MODAL);
        initialize();
    }

    private void initialize() {
        setBounds(0, 0, 250, 165);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 75, 150, 25 };
        gridBagLayout.rowHeights = new int[] { 25, 20, 20, 25, 20, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        final JLabel lblLogin = new JLabel("Login");
        GridBagConstraints gbc_lblLogin = new GridBagConstraints();
        gbc_lblLogin.anchor = GridBagConstraints.WEST;
        gbc_lblLogin.insets = new Insets(0, 0, 5, 0);
        gbc_lblLogin.gridx = 1;
        gbc_lblLogin.gridy = 1;
        getContentPane().add(lblLogin, gbc_lblLogin);

        loginField = new JTextField();
        GridBagConstraints gbc_loginField = new GridBagConstraints();
        gbc_loginField.fill = GridBagConstraints.HORIZONTAL;
        gbc_loginField.insets = new Insets(0, 0, 5, 0);
        gbc_loginField.gridx = 2;
        gbc_loginField.gridy = 1;
        getContentPane().add(loginField, gbc_loginField);
        loginField.setColumns(10);

        final JLabel lblSenha = new JLabel("Senha");
        GridBagConstraints gbc_lblSenha = new GridBagConstraints();
        gbc_lblSenha.anchor = GridBagConstraints.WEST;
        gbc_lblSenha.insets = new Insets(0, 0, 5, 0);
        gbc_lblSenha.gridx = 1;
        gbc_lblSenha.gridy = 2;
        getContentPane().add(lblSenha, gbc_lblSenha);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.anchor = GridBagConstraints.NORTH;
        gbc_passwordField.insets = new Insets(0, 0, 5, 0);
        gbc_passwordField.gridx = 2;
        gbc_passwordField.gridy = 2;
        getContentPane().add(passwordField, gbc_passwordField);

        btnConfirm = new JButton("Confirmar");
        GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
        gbc_btnConfirm.anchor = GridBagConstraints.NORTH;
        gbc_btnConfirm.gridwidth = 2;
        gbc_btnConfirm.gridx = 1;
        gbc_btnConfirm.gridy = 4;
        getContentPane().add(btnConfirm, gbc_btnConfirm);
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
    public void addConfirmListener(final ActionListener listener) {
        btnConfirm.addActionListener(listener);
    }

    @Override
    public void showLoginFailed(final String motivo) {
        final String motivoCompleto =
                "Não foi possível efetuar login: " + motivo;
        final String titulo = "Falha ao efetuar login";
        JOptionPane.showMessageDialog(this, motivoCompleto, titulo,
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public String getLogin() {
        return loginField.getText();
    }

    @Override
    public String getSenha() {
        final char[] passwordChars = passwordField.getPassword();
        final String string = new String(passwordChars);

        // Para maior segurança, é recomendado preencher a array de caracteres
        // da senha
        // com zeros após utilizá-la.
        for (int i = 0; i < passwordChars.length; i++) {
            passwordChars[i] = '0';
        }
        return string;
    }
}
