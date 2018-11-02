package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;

import com.github.nelsonwilliam.invscp.model.Localizacao;

public interface LocalizacaoView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addConfirmarListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Atualiza todos os valores da janela para exibir os dados da localização passado.
     */
    void updateLocalizacao(Localizacao localizacao);

    /**
     * Exibe uma mensagem de erro com a mensagem especificada. Por exemplo, pode ser chamado para
     * avisar que algum campo obrigatório foi deixado vazio.
     */
    void showError(String message);

    void showInfo(String message);

    /**
     * Exibe uma mensagem de que o item foi alterado/adicionado com sucesso.
     */
    void showSucesso();

    /**
     * Fecha a View.
     */
    void close();

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém a localização com os valores atualmente exibidos no formulário que o usuário pode ou
     * não ter alterado.
     */
    Localizacao getLocalizacao();
}
