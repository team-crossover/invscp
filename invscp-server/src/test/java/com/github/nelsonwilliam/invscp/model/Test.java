package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyException;
import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.server.InvSCPServer;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.server.model.Predio;
import com.github.nelsonwilliam.invscp.server.model.Sala;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.server.util.ServerSettings;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.TipoSalaEnum;

public class Test {

	/**
	 * É necessário conectar com e inicializar o banco para poder executar os
	 * testes. Este método é executado antes de todos os testes dessa classe.
	 * 
	 * @throws IOException
	 * @throws KeyException
	 */
	public static void prepararBanco() throws KeyException, IOException {
		ServerSettings.readSettings();
		InvSCPServer.connectDatabase();
		InvSCPServer.initializeDatabase();
	}

	public static int insereLocalizacaoTeste() {
		LocalizacaoRepository localRepository = new LocalizacaoRepository();
		Localizacao itemLocalTeste = new Localizacao();

		itemLocalTeste.setNome("Localizacao teste - JUnit temporario");
		itemLocalTeste.setEndereco("teste");
		itemLocalTeste.setCep("teste");
		itemLocalTeste.setCidade("teste");
		itemLocalTeste.setUfString("GO");

		localRepository.add(itemLocalTeste);

		// Pesquisa o idLocalizacao adicionado
		for (Localizacao item : localRepository.getAll()) {
			if (item.getNome() == "Localizacao teste - JUnit temporario") {
				itemLocalTeste.setId(item.getId());
			}
		}

		return itemLocalTeste.getId();
	}

	public static int inserePredioTeste() {
		PredioRepository predioRep = new PredioRepository();
		Predio itemPredioTeste = new Predio();

		itemPredioTeste.setNome("Predio teste - JUnit temporario");
		itemPredioTeste.setIdLocalizacao(insereLocalizacaoTeste());

		predioRep.add(itemPredioTeste);

		// Pesquisa o idLocalizacao adicionado
		for (Predio item : predioRep.getAll()) {
			if (item.getNome() == "Predio teste - JUnit temporario") {
				itemPredioTeste.setId(item.getId());
			}
		}

		return itemPredioTeste.getId();
	}
	

	public static void insereSalaTeste() {
		SalaRepository salaRepository = new SalaRepository();
		Sala itemTeste = new Sala();

		itemTeste.setNome("Sala teste");
		itemTeste.setTipo(TipoSalaEnum.DEPOSITO);
		//itemTeste.setIdPredio(idPredio);
		//itemTeste.setIdDepartamento(idDepartamento);

		salaRepository.add(itemTeste);
	}

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
}
