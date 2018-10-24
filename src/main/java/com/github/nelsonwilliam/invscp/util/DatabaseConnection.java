package com.github.nelsonwilliam.invscp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Classe utilitária que permite fazer a conexão com o Banco de dados.
 */
public class DatabaseConnection {

	private static String IP = "localhost";
	private static String PORT = "5432";
	private static String DATABASE_NAME = "inventory";
	private static String USER = "invscpAdmin";
	private static String PASSWORD = "12345";

	private static Connection connection;

	/**
	 * Abre a conexão com o banco de dados.
	 * 
	 * @throws ClassNotFoundException
	 *             Se não for encontrado o driver necessário para conexão com o
	 *             banco.
	 * @throws SQLException
	 *             Se não for possível conectar-se com o banco.
	 */
	public static void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");

		String url = "jdbc:postgresql://" + IP + ":" + PORT + "/" + DATABASE_NAME;
		Properties props = new Properties();
		props.setProperty("user", USER);
		props.setProperty("password", PASSWORD);
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
	 * @throws SQLException
	 *             Se não for possível encerrar a conexão.
	 */
	public static void closeConnection() throws SQLException {
		connection.close();
	}

	/**
	 * Verifica se o banco de dados do InvSCP já foi inicializado, ou seja, já teve
	 * executou script de criação.
	 * 
	 * @return Se o banco de dados já foi inicializado.
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public static boolean databaseWasInitialized() throws FileNotFoundException, SQLException {
		String checkScript = readResource("sql/checkDatabase.sql");
		PreparedStatement stmt = connection.prepareStatement(checkScript);
		ResultSet result = stmt.executeQuery();

		// Se retornou algo (provavelmente o valor "1") é porque todas as tabelas
		// existem.
		return result.next();
	}

	/**
	 * Executa o script de criação do banco de dados, apagando quaisquer valores
	 * pré-existentes.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void initializeDatabase() throws FileNotFoundException, SQLException {
		String createScript = readResource("sql/createDatabase.sql");
		PreparedStatement stmt = connection.prepareStatement(createScript);
		stmt.executeUpdate();
	}

	private static String readResource(String path) throws FileNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(path);
		if (stream == null) {
			throw new FileNotFoundException();
		}
		Scanner scanner = new Scanner(stream);
		scanner.useDelimiter("\\A");
		String string = scanner.next();
		scanner.close();
		return string;
	}
}