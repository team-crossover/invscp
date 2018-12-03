package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class FuncionarioTest {

	private static TesteGeral teste;

	@BeforeAll
	public static void preCondicaoGeralTeste() throws KeyException, IOException {
		teste = new TesteGeral();
		teste.prepararBanco();

		System.out.println("Registros de teste inseridos");
	}
	
	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		System.out.println("Teste executados");

		System.out.println("Registros de teste deletados");
	}
}
