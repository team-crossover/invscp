package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.WindowEvent;
import java.util.function.Consumer;

/**
 * Interface da View principal que contém a MenuView e a View que foi selecionada no menu.
 */
public interface MainView extends View {

    void addCloseListener(Consumer<WindowEvent> e);

    void updateSelectedView(View view);

}
