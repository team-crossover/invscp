package com.github.nelsonwilliam.invscp.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import com.github.nelsonwilliam.invscp.server.util.ClientHandler;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;
import com.github.nelsonwilliam.invscp.server.util.ServerSettings;

/**
 * Classe responsável por iniciar a execução do servidor do InvSCP.
 */
public class InvSCPServer {

    private static boolean forceInitializeDatabase;

    public static void main(final String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--forceInitialization")) {
                forceInitializeDatabase = true;
            }
        }

        connectDatabase();
        initializeDatabase();
        run();
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
                    .wasDatabaseInitialized();
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

    /*
     * Lida com as conexões de clientes que forem recebidas.
     */
    private static void run() {
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(ServerSettings.SERVER_PORT);
        } catch (final IOException e) {
            System.out.println("Não foi possível iniciar o servidor: " + e);
            return;
        }

        System.out.println("Conexão estabelecida na porta "
                + ServerSettings.SERVER_PORT + ".");
        System.out.println("Aguardando clientes...");

        int clientNumber = 1;
        try {
            while (true) {
                try {
                    new ClientHandler(listener.accept(), clientNumber++)
                            .start();
                } catch (final IOException e) {
                    System.out.println(
                            "Não foi possível lidar com novo cliente: " + e);
                }
            }
        } finally {
            try {
                listener.close();
            } catch (final IOException e) {
                System.out.println(
                        "Não foi possível encerrar a conexão com cliente: "
                                + e);
                return;
            }
        }
    }
}
