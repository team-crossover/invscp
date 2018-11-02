package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;

import com.github.nelsonwilliam.invscp.model.Funcionario;

public interface FuncionarioView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addConfirmarListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Atualiza todos os valores da janela para exibir os dados do funcionário passado.
     */
    void updateFuncionario(Funcionario funcionario);

    /**
     * Exibe uma mensagem de erro com a mensagem especificada. Por exemplo, pode ser chamado para
     * avisar que algum campo obrigatório foi deixado vazio.
     */
    void showError(String message);

    void showInfo(String message);

    /**
     * Exibe uma mensagem de que o item foi alterado/adicionado com sucesso.
     */
    void showSucesso();

    /**
     * Fecha a View.
     */
    void close();

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém o funcionário com os valores atualmente exibidos no formulário que o usuário pode ou
     * não ter alterado.
     */
    Funcionario getFuncionario();
}
