package com.github.nelsonwilliam.invscp;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.github.nelsonwilliam.invscp.presenter.InicioPresenter;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;
import com.github.nelsonwilliam.invscp.view.InicioView;
import com.github.nelsonwilliam.invscp.view.swing.InicioSwingView;

/**
 * Classe responsável por iniciar a execução do InvSCP, começando exibindo a
 * tela inicial.
 */
public class InvSCP {

	private static boolean forceInitializeDatabase;

	public static void main(String[] args) {

		// TODO Remover este parâmetro criado apenas para testes iniciais.
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--forceInitialize")) {
				forceInitializeDatabase = true;
			}
		}

		connectDatabase();
		initializeDatabase();
		showInicio();
	}

	/**
	 * Inicia a conexão com o banco de dados.
	 */
	private static void connectDatabase() {
		try {
			DatabaseConnection.openConnection();
			System.out.println("Conexão com o banco de dados estabelecida.");
		} catch (ClassNotFoundException e) {
			System.out.println("O JDBC Driver do PostgreSQL não foi encontrado.");
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("Não foi possível connectar com o banco de dados.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Inicializa o banco de dados caso este não tiver sido inicializado ainda.
	 */
	private static void initializeDatabase() {
		boolean databaseWasInitialized = true;

		try {
			databaseWasInitialized = DatabaseConnection.databaseWasInitialized();
		} catch (FileNotFoundException | SQLException e) {
			System.out.println("Não foi possível verificar se o banco de dados foi inicializado.");
			e.printStackTrace();
			System.exit(1);
		}

		if (!databaseWasInitialized || forceInitializeDatabase) {
			try {
				DatabaseConnection.initializeDatabase();
				System.out.println("O banco de dados foi inicializado (o script de criação foi executado).");
			} catch (SQLException | IOException e) {
				System.out.println("Não foi possível inicializar o banco de dados.");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * Exibe a tela de início.
	 */
	@SuppressWarnings("unused")
	private static void showInicio() {
		EventQueue.invokeLater(() -> {
			try {
				InicioView inicioView = new InicioSwingView();
				InicioPresenter inicioPresenter = new InicioPresenter(inicioView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
