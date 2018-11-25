package com.github.nelsonwilliam.invscp.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

import com.github.nelsonwilliam.invscp.server.util.ClientHandler;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

/**
 * Classe responsável por iniciar a execução do servidor do InvSCP.
 */
public class InvSCPServer {

    private static boolean forceInitializeDatabase;

    private static int port = 9898;

    public static void main(final String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--forceInitialization")) {
                forceInitializeDatabase = true;
            }
        }

        connectDatabase();
        initializeDatabase();

        final ServerSocket listener = new ServerSocket(port);
        System.out.println("Conexão estabelecida na porta " + port + ".");
        System.out.println("Aguardando clientes...");
        int clientNumber = 0;
        try {
            while (true) {
                new ClientHandler(listener.accept(), clientNumber++).start();
            }
        } finally {
            System.out.println("");
            listener.close();
        }
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

}
