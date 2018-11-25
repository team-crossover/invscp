package com.github.nelsonwilliam.invscp.server.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.github.nelsonwilliam.invscp.shared.util.Resources;

/**
 * Classe utilitária que permite fazer a conexão com o Banco de dados.
 */
public class DatabaseConnection {

    // TODO Permitir um banco pra testes.

    private static Connection connection;

    /**
     * Abre a conexão com o banco de dados.
     *
     * @throws ClassNotFoundException Se não for encontrado o driver necessário
     *         para conexão com o banco.
     * @throws SQLException Se não for possível conectar-se com o banco.
     */
    public static void openConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        final String url = "jdbc:postgresql://" + ServerSettings.DATABASE_HOST
                + ":" + ServerSettings.DATABASE_PORT + "/"
                + ServerSettings.DATABASE_NAME;
        final Properties props = new Properties();
        props.setProperty("user", ServerSettings.DATABASE_USER);
        props.setProperty("password", ServerSettings.DATABASE_PASSWORD);
        connection = DriverManager.getConnection(url, props);
    }

    /**
     * Obtém a conexão atual com o banco de dados.
     *
     * @return Conexão com o banco de dados.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Encerra a conexão atual com o banco de dados.
     *
     * @throws SQLException Se não for possível encerrar a conexão.
     */
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Verifica se o banco de dados do InvSCP já foi inicializado, ou seja, já
     * teve executou script de criação.
     *
     * @return Se o banco de dados já foi inicializado.
     * @throws FileNotFoundException
     * @throws SQLException
     */
    public static boolean wasDatabaseInitialized()
            throws FileNotFoundException, SQLException {

        final PreparedStatement hasSistemaTable = connection.prepareStatement(
                "SELECT 1 FROM INFORMATION_SCHEMA.Tables WHERE TABLE_NAME='sistema'");
        final ResultSet result = hasSistemaTable.executeQuery();
        if (!result.next()) {
            // Se não retornou nada é porque a tabela sistema não existe, logo,
            // o banco deve ser inicializado.
            return false;
        }

        final PreparedStatement getVersao =
                connection.prepareStatement("SELECT versao_db FROM sistema");
        final ResultSet result2 = getVersao.executeQuery();
        if (result2.next()) {
            final Integer versaoDb = (Integer) result2.getObject("versao_db");
            return versaoDb == ServerSettings.DATABASE_VERSAO;
        } else {
            // Se a versão não é a esperada (VERSAO_DB), então o banco deve ser
            // inicializado.
            return false;
        }
    }

    /**
     * Executa o script de criação do banco de dados, apagando quaisquer valores
     * pré-existentes.
     *
     * @throws SQLException
     * @throws IOException
     */
    public static void initializeDatabase()
            throws FileNotFoundException, SQLException {
        final String createScript =
                Resources.readResource("sql/createDatabase.sql");
        final PreparedStatement stmt =
                connection.prepareStatement(createScript);
        stmt.executeUpdate();
    }
}
