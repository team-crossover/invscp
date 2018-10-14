package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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

	public static boolean openConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("O JDBC Driver do PostgreSQL não foi encontrado.");
			e.printStackTrace();
			return false;
		}

		try {
			String url = "jdbc:postgresql://" + IP + ":" + PORT + "/" + DATABASE_NAME;
			Properties props = new Properties();
			props.setProperty("user", USER);
			props.setProperty("password", PASSWORD);
			connection = DriverManager.getConnection(url, props);
			System.out.println("Conexão com banco de dados estabelecida...");
			return true;
		} catch (SQLException e) {
			System.out.println("Não foi possível connectar com o banco de dados '" + DATABASE_NAME + "'.");
			e.printStackTrace();
			return false;
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static boolean closeConnection() {
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Não foi possível fechar a conexão com o banco de dados.");
			e.printStackTrace();
			return false;
		}
	}

	public static void createDatabase() throws SQLException {
		Statement stmt = getConnection().createStatement();
		String sql = "CREATE TABLE public.dog (\r\n" + "    id serial PRIMARY KEY,\r\n" + "    age integer,\r\n"
				+ "    name character varying(255)\r\n" + ");\r\n" + "";
		stmt.executeUpdate(sql);

		System.out.println("O banco de dados foi criado com sucesso...");
	}
}