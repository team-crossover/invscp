package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.LocalizacaoDTO;

public class LocalizacaoTest {

	private static TesteGeral teste;

	@BeforeAll
	public static void preCondicaoGeralTeste() throws KeyException, IOException {
		teste = new TesteGeral();
		teste.prepararBanco();

		teste.insereLocalizacaoTeste();

		System.out.println("Registros de teste inseridos");
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		System.out.println("Testes executados");

		teste.deletarLocalizacaoTeste();

		System.out.println("Registros de teste deletados");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarInserir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Insere uma localizacao sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void inserirBemJaExistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final LocalizacaoDTO novaLocal = new LocalizacaoDTO();

		novaLocal.setId(teste.getIdLocalizacaoInserido());
		
		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Localizacao.validarInserir(usuario, novaLocal);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarExcluir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
