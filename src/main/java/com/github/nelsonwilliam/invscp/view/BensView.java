package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;

public interface BensView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarBemListener(ActionListener listener);

    void addBaixarBemListener(ActionListener listener);

    void addOrdemServicoListener(ActionListener listener);

    void addDeletarBensListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar um
     * bem. Espera-se que quando este evento for invocado, haja exatamente UM
     * bem selecionado (obtível através do método getSelectedBensIds.
     */
    void addAlterarBemListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateBens(List<BemDTO> bem);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os bens atualmente selecionados na
     * tabela.
     */
    List<Integer> getSelectedBensIds();

}
