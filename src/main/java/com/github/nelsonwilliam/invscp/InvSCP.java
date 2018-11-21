package com.github.nelsonwilliam.invscp;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;

import com.github.nelsonwilliam.invscp.presenter.MainPresenter;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;
import com.github.nelsonwilliam.invscp.view.MainView;
import com.github.nelsonwilliam.invscp.view.MenuView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

/**
 * Classe responsável por iniciar a execução do InvSCP, começando exibindo a
 * tela inicial.
 */
public class InvSCP {

    private static boolean forceInitializeDatabase;

    public static void main(final String[] args) {
        connectDatabase();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--forceInitialization")) {
                forceInitializeDatabase = true;
            }
        }

        // TODO Remover isto (usado apenas para desenvolvimento)
        // forceInitializeDatabase = true;

        initializeDatabase();
        showMainView();
    }

    /**
     * Inicia a conexão com o banco de dados.
     */
    public static void connectDatabase() {
        try {
            DatabaseConnection.openConnection();
            System.out.println("Conexão com o banco de dados estabelecida.");
        } catch (final ClassNotFoundException e) {
            System.out
                    .println("O JDBC Driver do PostgreSQL não foi encontrado.");
            e.printStackTrace();
            System.exit(1);
        } catch (final SQLException e) {
            System.out.println(
                    "Não foi possível connectar com o banco de dados.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Inicializa o banco de dados caso este não tiver sido inicializado ainda.
     */
    public static void initializeDatabase() {
        boolean databaseWasInitialized = true;

        try {
            databaseWasInitialized = DatabaseConnection
                    .databaseWasInitialized();
        } catch (FileNotFoundException | SQLException e) {
            System.out.println(
                    "Não foi possível verificar se o banco de dados foi inicializado.");
            e.printStackTrace();
            System.exit(1);
        }

        if (!databaseWasInitialized || forceInitializeDatabase) {
            try {
                DatabaseConnection.initializeDatabase();
                System.out.println(
                        "O banco de dados foi inicializado (quaisquer dados existentes foram apagados).");
            } catch (SQLException | IOException e) {
                System.out.println(
                        "Não foi possível inicializar o banco de dados.");
                e.printStackTrace();
                System.exit(1);
            }
        }
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
