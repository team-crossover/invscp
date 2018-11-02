package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.Funcionario;

public interface FuncionariosView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarFuncionarioListener(ActionListener listener);

    void addDeletarFuncionariosListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar um funcionario. Espera-se
     * que quando este evento for invocado, haja exatamente UM funcionario selecionado (obtível
     * através do método getSelectedFuncionariosIds.
     */
    void addAlterarFuncionarioListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateFuncionarios(List<Funcionario> funcionarios);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os funcionarios atualmente selecionados na tabela.
     */
    List<Integer> getSelectedFuncionariosIds();

}
