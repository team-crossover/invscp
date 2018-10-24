package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;

import com.github.nelsonwilliam.invscp.model.Usuario;

/**
 * View da tela de início que é a primeira a ser exibida ao usuário.
 */
public interface InicioView extends View {

	// ----------------------------------------
	// Métodos para notificar ações do usuário.
	// ----------------------------------------

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja efetuar
	 * login.
	 */
	void addLoginListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja efetuar
	 * logout.
	 */
	void addLogoutListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja acessar a
	 * listagem de localizações.
	 */
	void addLocalizacoesListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja acessar a
	 * listagem de prédios.
	 */
	void addPrediosListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja acessar a
	 * listagem de salas.
	 */
	void addSalasListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja acessar a
	 * listagem de departamentos.
	 */
	void addDepartamentosListener(ActionListener listener);

	/**
	 * Adiciona um Listener para o quando o usuário informar que deseja acessar a
	 * listagem de usuários.
	 */
	void addUsuariosListener(ActionListener listener);

	// -------------------------------------------
	// Métodos para atualizar os valores exibidos.
	// -------------------------------------------

	/**
	 * Define qual usuário está logado atualmente. Caso nenhum esteja logado, o
	 * valor é null.
	 */
	void setUsuarioLogado(Usuario usuario);

	// ---------------------------------------------
	// Métodos para obter os valores de formulários.
	// ---------------------------------------------
	// Nenhum.

}
