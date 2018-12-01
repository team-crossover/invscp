package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.InvSCPServer;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.server.util.ServerSettings;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

public class BemTest {

	

	@BeforeAll
	public static void insereBemPatrimonialTeste() {
		BemRepository bemRepository = new BemRepository();
		Bem itemTeste = new Bem();
		long numTomb = 010101010101;

		itemTeste.setDescricao("Bem teste");
		itemTeste.setNumeroTombamento(numTomb);
		itemTeste.setDataCadastro(LocalDate.now());
		itemTeste.setDataAquisicao(LocalDate.now());
		itemTeste.setNumeroNotaFiscal("00000000");
		itemTeste.setEspecificacao("teste");
		itemTeste.setGarantia(LocalDate.now());
		itemTeste.setMarca("teste");
		itemTeste.setValorCompra(BigDecimal.ONE);
		itemTeste.setSituacao(BemSituacaoEnum.INCORPORADO);

		bemRepository.add(itemTeste);
	}

	@AfterAll
	public static void deletaBemPatrimonialTeste() {
		BemRepository bemRepository = new BemRepository();
		Bem itemTeste = new Bem();

		for (Bem item : bemRepository.getAll()) {
			if (item.getNumeroTombamento() == 010101010101 && item.getDescricao() == "Bem teste") {
				itemTeste.setId(item.getId());
			}
		}

		bemRepository.remove(itemTeste);
	}

	/**
	 * Inserir bem quando o usuário não está logado (é nulo) deve causar uma
	 * exceção.
	 */
	@Test
	public void inserirBemSemEstarLogado() {
		final BemDTO novoBem = new BemDTO();
		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Bem.validarInserir(null, novoBem);
		});
	}

	/**
	 * Inserir um bem que já existe deve causar uma exceção.
	 */
	@Test
	public void inserirBemJaExistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final BemDTO novoBem = new BemDTO();
		BemRepository bemRepository = new BemRepository();

		for (Bem item : bemRepository.getAll()) {
			novoBem.setId(item.getId());
			break;
		}

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Bem.validarInserir(usuario, novoBem);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarDeletar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Deletar um bem sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void deletarBemSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Bem.validarDeletar(usuario, null);
		});
	}

	/**
	 * Deletar um bem informando um id inexistente deve causar uma exceção.
	 */
	@Test
	public void deletarBemComIdInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Bem.validarDeletar(usuario, 0);
		});
	}

}
