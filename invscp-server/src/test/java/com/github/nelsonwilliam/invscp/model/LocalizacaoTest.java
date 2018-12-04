package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
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
		teste.insereFuncionarioTeste();
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		teste.deletarLocalizacaoTeste();
		teste.deletarFuncionarioTeste();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarInserir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Insere uma localizacao sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void inserirLocalizacaoJaExistente() {
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
	
	/**
	 * Alterar uma localizacao sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarLocalizacaoSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final LocalizacaoDTO novaLocal = new LocalizacaoDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Localizacao.validarAlterar(usuario, null, novaLocal);
		});
	}

	/**
	 * Alterar uma localizacao inexistente deve causar uma exceção.
	 */
	@Test
	public void alterarLocalizacaoInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final LocalizacaoDTO novaLocal = new LocalizacaoDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Localizacao.validarAlterar(usuario, 0, novaLocal);
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarExcluir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Excluir uma localizacao sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void deletarLocalizacaoSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Localizacao.validarDeletar(usuario, null);
		});
	}
	
	/**
	 * Excluir uma localizacao inexistente deve causar uma exceção.
	 */
	@Test
	public void deletarLocalizacaoInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Localizacao.validarDeletar(usuario, 0);
		});
	}
}
