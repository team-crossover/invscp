package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.model.Movimentacao;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class MovimentacaoTest {

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
		teste.insereSalaTeste();
		teste.insereSalaIITeste();
	}
	
	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		teste.deletarSalaIITeste();
		teste.deletarSalaTeste();
		teste.deletarFuncionarioComDepartamentoTeste();
		teste.deletarDepartamentoTeste();
		teste.deletarFuncionarioTeste();
		teste.deletarChefeSubDepartamentoTeste();
		teste.deletarChefeDepartamentoTeste();
		teste.deletarPredioTeste();
		teste.deletarLocalizacaoTeste();
	}
	
	/**
	 * Verifica se uma movimentação realizada com salas do mesmo departamento é dada com interna
	 */
	@Test
	public void validaMovimentacaoInterna() {
		Movimentacao mov = new Movimentacao();

		mov.setIdSalaOrigem(teste.getIdSalaInserido());
		mov.setIdSalaDestino(teste.getIdSalaIInserido());

		Assertions.assertTrue(mov.isInterna());
	}
}
