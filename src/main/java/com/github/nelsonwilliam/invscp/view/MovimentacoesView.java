package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;

public interface MovimentacoesView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarMovimentacaoListener(ActionListener listener);

    void addGerarRelatorioListener(ActionListener listener);

    void addDeletarMovimentacoesListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar uma
     * movimentação. Espera-se que quando este evento for invocado, haja
     * exatamente UMA movimentação selecionado (obtível através do método
     * getSelectedMovimentacoesIds.
     */
    void addAlterarMovimentacaoListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateMovimentacoes(List<MovimentacaoDTO> movimentacao);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos as movimentações atualmente
     * selecionados na tabela.
     */
    List<Integer> getSelectedMovimentacoesIds();

}
