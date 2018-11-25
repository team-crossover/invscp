package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.shared.model.dto.PredioDTO;

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

    void updatePredios(List<PredioDTO> predio);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os prédios atualmente selecionados na
     * tabela.
     */
    List<Integer> getSelectedPrediosIds();

}
