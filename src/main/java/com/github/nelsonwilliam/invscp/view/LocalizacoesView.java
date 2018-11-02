package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.model.Localizacao;

public interface LocalizacoesView extends View {
    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarLocalizacaoListener(ActionListener listener);

    void addDeletarLocalizacoesListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar uma localização.
     * Espera-se que quando este evento for invocado, haja exatamente UMA localização selecionada
     * (obtível através do método getSelectedLocalizacoesIds.
     */
    void addAlterarLocalizacaoListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateLocalizacoes(List<Localizacao> localizacoes);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todas as localizações atualmente selecionadas na tabela.
     */
    List<Integer> getSelectedLocalizacoesIds();

}
