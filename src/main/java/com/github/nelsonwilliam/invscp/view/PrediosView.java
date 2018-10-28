package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Predio;

public interface PrediosView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarPredioListener(ActionListener listener);

    void addDeletarPrediosListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar um
     * predio. Espera-se que quando este evento for invocado, haja exatamente UM
     * predio selecionado (obtível através do método getSelectedPrediosIds.
     */
    void addAlterarPredioListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updatePredio(List<Predio> predio);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os prédios atualmente selecionados na
     * tabela.
     */
    List<Integer> getSelectedPrediosIds();

}
