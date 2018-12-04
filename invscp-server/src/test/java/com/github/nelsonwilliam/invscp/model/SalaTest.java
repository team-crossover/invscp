package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Sala;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;

public class SalaTest {
	private static TesteGeral teste;

	@BeforeAll
	public static void preCondicaoGeralTeste() throws KeyException, IOException {
		teste = new TesteGeral();
		teste.prepararBanco();

		teste.insereLocalizacaoTeste();
		teste.inserePredioTeste();
		teste.insereFuncionarioTeste();
		teste.insereChefeDepartamentoTeste();
		teste.insereChefeSubDepartamentoTeste();
		teste.insereDepartamentoTeste();
		teste.insereFuncionarioComDepartamentoTeste();
		teste.insereSalaTeste();
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		teste.deletarSalaTeste();
		teste.deletarFuncionarioComDepartamentoTeste();
		teste.deletarDepartamentoTeste();
		teste.deletarFuncionarioTeste();
		teste.deletarChefeSubDepartamentoTeste();
		teste.deletarChefeDepartamentoTeste();
		teste.deletarPredioTeste();
		teste.deletarLocalizacaoTeste();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarInserir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Insere uma Sala já existente
	 */
	@Test
	public void inserirSalaJaExistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final SalaDTO novaSala = new SalaDTO();

		novaSala.setId(teste.getIdSalaInserido());

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Sala.validarInserir(usuario, novaSala);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Alterar uma Sala sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarSalaSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final SalaDTO sala = new SalaDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Sala.validarAlterar(usuario, null, sala);
		});
	}

	/**
	 * Alterar uma localizacao inexistente deve causar uma exceção.
	 */
	@Test
	public void alterarSalaInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final SalaDTO sala = new SalaDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Sala.validarAlterar(usuario, 0, sala);
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarExcluir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Excluir uma Sala sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void deletarSalaSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Sala.validarDeletar(usuario, null);
		});
	}
	
	/**
	 * Excluir uma Sala inexistente deve causar uma exceção.
	 */
	@Test
	public void deletarLocalizacaoInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Sala.validarDeletar(usuario, 0);
		});
	}
}
