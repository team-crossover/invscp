package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Predio;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.PredioDTO;

public class PredioTest {

	private static TesteGeral teste;

	@BeforeAll
	public static void preCondicaoGeralTeste() throws KeyException, IOException {
		teste = new TesteGeral();
		teste.prepararBanco();

		teste.insereLocalizacaoTeste();
		teste.inserePredioTeste();
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		teste.deletarPredioTeste();
		teste.deletarLocalizacaoTeste();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarInserir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Insere um Predio sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void inserirPredioJaExistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final PredioDTO novoPred = new PredioDTO();

		novoPred.setId(teste.getIdPredioInserido());

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Predio.validarInserir(usuario, novoPred);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Alterar um Predio sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarPredioSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final PredioDTO pred = new PredioDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Predio.validarAlterar(usuario, null, pred);
		});
	}

	/**
	 * Alterar um Predio inexistente deve causar uma exceção.
	 */
	@Test
	public void alterarPredioInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final PredioDTO pred = new PredioDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Predio.validarAlterar(usuario, 0, pred);
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarExcluir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Excluir um Predio sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void deletarPredioSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Predio.validarDeletar(usuario, null);
		});
	}
	
	/**
	 * Excluir uma Predio inexistente deve causar uma exceção.
	 */
	@Test
	public void deletarPredioInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Predio.validarDeletar(usuario, 0);
		});
	}
}
