package com.github.nelsonwilliam.invscp.view;

/**
 * Interface da View principal que contém a MenuView e a View que foi selecionada no menu.
 */
public interface MainView extends View {

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Define qual View deve ser exibida junto à MenuView.
     */
    void updateSelectedView(View view);

}
