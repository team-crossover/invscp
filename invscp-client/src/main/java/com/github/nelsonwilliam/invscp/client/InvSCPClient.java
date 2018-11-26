package com.github.nelsonwilliam.invscp.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.security.KeyException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;

import com.github.nelsonwilliam.invscp.client.presenter.MainPresenter;
import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.util.ClientSettings;
import com.github.nelsonwilliam.invscp.client.view.MainView;
import com.github.nelsonwilliam.invscp.client.view.MenuView;
import com.github.nelsonwilliam.invscp.client.view.ViewFactory;

/**
 * Classe responsável por iniciar a execução do cliente do InvSCP, começando
 * exibindo a tela inicial.
 */
public class InvSCPClient {

    public static void main(final String[] args) {

        try {
            ClientSettings.readSettings();
        } catch (final IOException | KeyException e) {
            System.out.println("Não foi possível ler as configurações.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try {
            Client.connect();
        } catch (ClassNotFoundException | IOException e) {
            System.out
                    .println("Não foi possível conectar com o servidor: " + e);
            System.exit(1);
        }

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
                UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
            } catch (final UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }

            final MenuView menuView = ViewFactory.createMenu();
            final MainView mainView = ViewFactory.createMain(menuView);
            final MainPresenter mainPresenter =
                    new MainPresenter(mainView, menuView);
            menuView.setVisible(true);
            mainView.setVisible(true);
        });
    }
}
