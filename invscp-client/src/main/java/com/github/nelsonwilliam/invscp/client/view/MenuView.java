package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.ActionListener;

import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

/**
 * Interface da View do menu que contém as opções para alterar quais entidades
 * estão sendo mantidas e para login/logout.
 */
public interface MenuView extends View {

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
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de localizações.
     */
    void addLocalizacoesListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de prédios.
     */
    void addPrediosListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de salas.
     */
    void addSalasListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de departamentos.
     */
    void addDepartamentosListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de funcionários.
     */
    void addFuncionariosListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de bens.
     */
    void addBensListener(ActionListener listener);

    /**
     * Adiciona um Listener para o quando o usuário informar que deseja acessar
     * a listagem de movimentações.
     */
    void addMovimentacoesListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Define qual usuário está logado atualmente. Caso nenhum esteja logado, o
     * valor deve ser null.
     */
    void updateUsuario(FuncionarioDTO funcionario);

}
