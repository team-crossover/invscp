package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.nelsonwilliam.invscp.view.LoginView;

public class LoginSwingView extends JDialog implements LoginView {

	private static final long serialVersionUID = -5241111701730056643L;

	private JButton btnConfirm;
	private JTextField loginField;
	private JPasswordField passwordField;

	public LoginSwingView(Window owner) {
		super(owner, "Login", ModalityType.APPLICATION_MODAL);
		initialize();
	}

	private void initialize() {
		setBounds(150, 150, 214, 139);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(53, 74, 104, 25);
		getContentPane().add(btnConfirm);

		loginField = new JTextField();
		loginField.setBounds(74, 12, 124, 19);
		getContentPane().add(loginField);
		loginField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(74, 43, 124, 19);
		getContentPane().add(passwordField);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(12, 14, 66, 15);
		getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 45, 66, 15);
		getContentPane().add(lblSenha);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public void close() {
		super.setVisible(false);
		super.dispose();
	}

	@Override
	public void addConfirmListener(ActionListener listener) {
		btnConfirm.addActionListener(listener);
	}

	@Override
	public void showLoginFailed(String motivo) {
		String motivoCompleto = "Não foi possível efetuar login: " + motivo;
		String titulo = "Falha ao efetuar login";
		JOptionPane.showMessageDialog(this, motivoCompleto, titulo, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public String getLogin() {
		return loginField.getText();
	}

	@Override
	public String getSenha() {
		char[] passwordChars = passwordField.getPassword();
		String string = new String(passwordChars);

		// Para maior segurança, é recomendado preencher a array de caracteres da senha
		// com zeros após utilizá-la.
		for (int i = 0; i < passwordChars.length; i++) {
			passwordChars[i] = '0';
		}
		return string;
	}
}
