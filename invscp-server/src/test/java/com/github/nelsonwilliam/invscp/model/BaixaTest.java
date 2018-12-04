package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Baixa;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class BaixaTest {

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
		teste.insereGrupoMaterialTeste();
		teste.insereBemPatrimonialTeste();
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		teste.deletarBemPatrimonialTeste();
		teste.deletarGrupoMaterialTeste();
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
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void validarAlterar() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Baixa.validarAlterar(usuario, 0, null);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarDeletar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void validarDeletar() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Baixa.validarDeletar(usuario, 0);
		});
	}
}
