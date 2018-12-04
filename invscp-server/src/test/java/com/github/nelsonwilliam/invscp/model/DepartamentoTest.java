package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.LocalizacaoDTO;

public class DepartamentoTest {

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
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
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
	 * Insere um departamento sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void inserirDepartamentoJaExistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final DepartamentoDTO novaDep = new DepartamentoDTO();

		novaDep.setId(teste.getIdDepartamentoInserido());

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Departamento.validarInserir(usuario, novaDep);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Alterar um departamento sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarDepartamentoSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final DepartamentoDTO dep = new DepartamentoDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Departamento.validarAlterar(usuario, null, dep);
		});
	}

	/**
	 * Alterar um departamento inexistente deve causar uma exceção.
	 */
	@Test
	public void alterarDepartamentoInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final DepartamentoDTO dep = new DepartamentoDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Departamento.validarAlterar(usuario, 0, dep);
		});
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarExcluir
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Excluir um deparatmento sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void deletarDepartamentoSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Departamento.validarDeletar(usuario, null);
		});
	}
	
	/**
	 * Excluir uma Departamento inexistente deve causar uma exceção.
	 */
	@Test
	public void deletarDepartamentoInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();

		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Localizacao.validarDeletar(usuario, 0);
		});
	}
}
