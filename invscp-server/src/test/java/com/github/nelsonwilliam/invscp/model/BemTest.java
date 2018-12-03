package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class BemTest {

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

		System.out.println("Registros de teste inseridos");
	}

	@AfterAll
	public static void posCondicaoGeralTeste() throws KeyException, IOException {
		System.out.println("Teste executados");

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

		System.out.println("Registros de teste deletados");
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

		novoBem.setId(teste.getIdBemInserido());

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Bem.validarInserir(usuario, novoBem);
		});
	}

	/**
	 * Inserir um bem com um usuário que não possui um departamento deve causar uma
	 * exceção.
	 */
	@Test
	public void inserirBemComUsuarioSemDepartamento() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final BemDTO novoBem = new BemDTO();

		usuario.setId(teste.getIdFuncionarioInserido());

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Bem.validarInserir(usuario, novoBem);
		});
	}

	/**
	 * Inserir um bem que já existe deve causar uma exceção.
	 */
	@Test
	public void inserirBemComUsuarioComDepartamentoQueNaoEChefe() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final BemDTO novoBem = new BemDTO();

		Assertions.assertThrows(IllegalInsertException.class, () -> {
			Bem.validarInserir(usuario, novoBem);
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA validarAlterar
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Alterar um bem sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarBemSemId() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final BemDTO novoBem = new BemDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Bem.validarAlterar(usuario, null, novoBem);
		});
	}

	/**
	 * Alterar um bem sem informar um id (null) deve causar uma exceção.
	 */
	@Test
	public void alterarBemInexistente() {
		final FuncionarioDTO usuario = new FuncionarioDTO();
		final BemDTO novoBem = new BemDTO();

		Assertions.assertThrows(IllegalUpdateException.class, () -> {
			Bem.validarAlterar(usuario, 0, novoBem);
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

	/**
	 * Deletar bem quando o usuário não está logado (é nulo) deve causar uma
	 * exceção.
	 */
	@Test
	public void deletarBemSemEstarLogado() {
		Assertions.assertThrows(IllegalDeleteException.class, () -> {
			Bem.validarDeletar(null, teste.getIdBemInserido());
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TESTES PARA getDepreciacoesPorAno
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Verificação do calculo de depreciação inválido
	 */
	@Test
	public void calculoDepreciacaoInvalido() {
		Bem bem = new Bem();
		BigDecimal valorCompra = new BigDecimal(2000);

		bem.setValorCompra(valorCompra);
		bem.setDataAquisicao(LocalDate.now());
		bem.setIdGrupoMaterial(teste.getIdGrupoMaterialInserido());

		BigDecimal[] depreciacaoEsperada = new BigDecimal[5];

		depreciacaoEsperada[0] = new BigDecimal(800);
		depreciacaoEsperada[1] = new BigDecimal(600);
		depreciacaoEsperada[2] = new BigDecimal(400);
		depreciacaoEsperada[3] = new BigDecimal(200);
		depreciacaoEsperada[4] = new BigDecimal(0.1);
		
		BigDecimal[] depreciacaoCalculada = bem.getDepreciacoesPorAno();
		depreciacaoCalculada = bem.getDepreciacoesPorAno();
		
		boolean validaDepreciacao = true;

		if(depreciacaoEsperada.length != depreciacaoCalculada.length) {
			validaDepreciacao = false;
		}
		else {
			for (int i = 0; i < depreciacaoEsperada.length; i++) {
				if (depreciacaoEsperada[i] != depreciacaoCalculada[i]) {
					validaDepreciacao = false;
				}
			}	
		}
		
		Assertions.assertFalse(validaDepreciacao);
	}

	/**
	 * Verificação do calculo de depreciação inválido
	 */
	@Test
	public void calculoDepreciacaoValido() {
		Bem bem = new Bem();
		BigDecimal valorCompra = new BigDecimal(1000);
		LocalDate dataAquisicao = LocalDate.parse("2013-01-01");
		
		bem.setValorCompra(valorCompra);
		bem.setDataAquisicao(dataAquisicao);
		bem.setIdGrupoMaterial(teste.getIdGrupoMaterialInserido());

		BigDecimal[] depreciacaoEsperada = new BigDecimal[6];

		depreciacaoEsperada[0] = new BigDecimal(1000);
		depreciacaoEsperada[1] = new BigDecimal(800);
		depreciacaoEsperada[2] = new BigDecimal(600);
		depreciacaoEsperada[3] = new BigDecimal(400);
		depreciacaoEsperada[4] = new BigDecimal(200);
		depreciacaoEsperada[5] = new BigDecimal(0.01);
		
		BigDecimal[] depreciacaoCalculada = bem.getDepreciacoesPorAno();
		depreciacaoCalculada = bem.getDepreciacoesPorAno();
		
		boolean validaDepreciacao = true;

		if(depreciacaoEsperada.length != depreciacaoCalculada.length) {
			validaDepreciacao = false;
		}
		else {
			for (int i = 0; i < depreciacaoEsperada.length; i++) {
				String depreciacaoEspFormatada = String.format("%.2f", depreciacaoEsperada[i]);
				String depreciacaoCalFormatada = String.format("%.2f", depreciacaoCalculada[i]);

				if (!depreciacaoEspFormatada.equals(depreciacaoCalFormatada)) {
					validaDepreciacao = false;
				}
			}	
		}
		
		Assertions.assertTrue(validaDepreciacao);
	}
}
