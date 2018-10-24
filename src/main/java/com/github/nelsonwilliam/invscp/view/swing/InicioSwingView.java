package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Usuario;
import com.github.nelsonwilliam.invscp.view.InicioView;

/**
 * Implementação em Swing da InicioView.
 */
public class InicioSwingView implements InicioView {

	private Usuario usuarioLogado;

	private JButton btnLogin;

	public InicioSwingView() {
		initializeFrame();
	}

	private void initializeFrame() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnLogin = new JButton("Login");
		frame.getContentPane().add(btnLogin, BorderLayout.SOUTH);

		// TODO Exibir os botões adequados dependendo do papel do usuário logado. Teria
		// que atualizar isso sempre que usuário logado é alterado...

		frame.setVisible(true);
	}

	@Override
	public void addLoginListener(ActionListener listener) {
		btnLogin.addActionListener(listener);
	}

	@Override
	public void addLogoutListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLocalizacoesListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPrediosListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSalasListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDepartamentosListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUsuariosListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;

		// TODO Auto-generated method stub
	}

}
