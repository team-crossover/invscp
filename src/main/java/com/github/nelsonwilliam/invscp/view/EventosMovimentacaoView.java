package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;

public interface EventosMovimentacaoView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addVerEventoMovimentacaoListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateEventosMovimentacao(MovimentacaoDTO movimentacao,
            List<EventoMovimentacaoDTO> eventoMovimentacao);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os eventos de movimentação atualmente
     * selecionados na tabela.
     */
    List<Integer> getSelectedEventosMovimentacaoIds();

}
