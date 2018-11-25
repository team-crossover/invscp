package com.github.nelsonwilliam.invscp.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;

import com.github.nelsonwilliam.invscp.client.presenter.MainPresenter;
import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.MainView;
import com.github.nelsonwilliam.invscp.client.view.MenuView;
import com.github.nelsonwilliam.invscp.client.view.ViewFactory;

/**
 * Classe responsável por iniciar a execução do cliente do InvSCP, começando
 * exibindo a tela inicial.
 */
public class InvSCPClient {

    public static void main(final String[] args)
            throws UnknownHostException, ClassNotFoundException, IOException {

        Client.connect();
        showMainView();
    }

    /**
     * Exibe a tela principal.
     */
    @SuppressWarnings("unused")
    public static void showMainView() {
        EventQueue.invokeLater(() -> {
            // Define a aparência do Swing
            try {
                UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
            } catch (final UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            // Cria e exibe a tela main com o menu
            try {
                final MenuView menuView = ViewFactory.createMenu();
                final MainView mainView = ViewFactory.createMain(menuView);
                final MainPresenter mainPresenter = new MainPresenter(mainView,
                        menuView);
                menuView.setVisible(true);
                mainView.setVisible(true);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }
}
