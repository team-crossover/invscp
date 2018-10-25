package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.view.MenuView;

public class MenuSwingView extends JPanel implements MenuView {

	private static final long serialVersionUID = 8975992154717828680L;

	private JLabel lblSessao;
	private JButton btnLogin;
	private JButton btnLogout;
	private JButton btnDepartamentos;

	public MenuSwingView() {
		initialize();
		updateFuncionarioLogado(null);
	}

	private void initialize() {
		setLayout(new GridLayout(10, 1, 10, 10));

		lblSessao = new JLabel("Sessão desconhecida");
		btnLogin = new JButton("Login");
		btnLogout = new JButton("Logout");
		btnDepartamentos = new JButton("Departamentos");
	}

	@Override
	public void updateFuncionarioLogado(Funcionario funcionario) {
		removeAll();

		boolean isInteressado = (funcionario == null);
		boolean isFuncionario = (funcionario != null);
		boolean isChefeDeDepartamento = isFuncionario && funcionario.isChefeDeDepartamento();
		boolean isChefeDePatrimonio = isFuncionario && funcionario.isChefeDePatrimonio();

		// TODO Exibir os botões adequados dependendo do papel do funcionário logado.

		if (isInteressado) {
			lblSessao.setText("Sessão anônima");
			add(lblSessao);
			add(btnLogin);
		} else {
			lblSessao.setText("Sessão de " + funcionario.getNome());
			add(lblSessao);
			add(btnLogout);
			add(btnDepartamentos);

			if (isChefeDeDepartamento) {
				// ...
			} else if (isChefeDePatrimonio) {
				// ...
			}
		}

		revalidate();
		repaint();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public void addLoginListener(ActionListener listener) {
		btnLogin.addActionListener(listener);
	}

	@Override
	public void addLogoutListener(ActionListener listener) {
		btnLogout.addActionListener(listener);
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
		btnDepartamentos.addActionListener(listener);
	}

	@Override
	public void addFuncionariosListener(ActionListener listener) {
		// TODO Auto-generated method stub

	}

}
