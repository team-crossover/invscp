package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.OrdemServicoDTO;

public interface OrdensServicoView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarOrdemServicoListener(ActionListener listener);

    // void addDeletarOrdensServicoListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar uma
     * ordem de serviço. Espera-se que quando este evento for invocado, haja
     * exatamente UMA ordem de serviço selecionado (obtível através do método
     * getSelectedOrdensServicoIds.
     */
    void addAlterarOrdemServicoListener(ActionListener listener);

    void addConcluirOrdemServicoListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateOrdensServico(BemDTO bem, List<OrdemServicoDTO> predio);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos as ordens de serviço atualmente
     * selecionados na tabela.
     */
    List<Integer> getSelectedOrdensServicoIds();

}
