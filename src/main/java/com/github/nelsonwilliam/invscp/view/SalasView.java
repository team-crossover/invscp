package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Sala;

public interface SalasView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarSalaListener(ActionListener listener);

    void addDeletarSalasListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar uma
     * sala. Espera-se que quando este evento for invocado, haja exatamente UMA
     * sala selecionado (obtível através do método getSelectedPrediosIds.
     */
    void addAlterarSalaListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateSala(List<Sala> sala);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos as salas atualmente selecionados na
     * tabela.
     */
    List<Integer> getSelectedSalasIds();

}
