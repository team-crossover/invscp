package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;

public interface MovimentacoesView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addVerMovimentacaoListener(ActionListener listener);

    void addAceitarSaidaListener(ActionListener listener);

    void addNegarSaidaListener(ActionListener listener);

    void addAceitarEntradaListener(ActionListener listener);

    void addNegarEntradaListener(ActionListener listener);

    void addCancelarListener(ActionListener listener);

    void addFinalizarListener(ActionListener listener);

    void addEventosMovimentacaoListener(ActionListener listener);

    void addGerarGuiaTransporte(ActionListener listener);

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
