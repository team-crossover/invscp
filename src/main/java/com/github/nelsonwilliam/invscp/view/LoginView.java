package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;

/**
 * Interface da View da janela de login.
 */
public interface LoginView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    /**
     * Adiciona um Listener para o quando o usuário confirmar o login.
     */
    void addConfirmListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Exibe a mensagem de que o login falhou.
     */
    void showLoginFailed(String motivo);

    /**
     * Fecha a View de login.
     */
    void close();

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém o valor do login inserido pelo usuário.
     */
    String getLogin();

    /**
     * Obtém o valor da senha inserida pelo usuário.
     */
    String getSenha();

}
