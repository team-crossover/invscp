package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyException;
import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.server.InvSCPServer;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.model.Funcionario;
import com.github.nelsonwilliam.invscp.server.model.GrupoMaterial;
import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.server.model.Predio;
import com.github.nelsonwilliam.invscp.server.model.Sala;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.server.util.ServerSettings;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.TipoSalaEnum;

public class TesteGeral {

	private static Localizacao itemLocalTeste;
	private static Funcionario itemFuncionarioTeste;
	private static Funcionario itemFuncionarioComDepartamentoTeste;
	private static Funcionario itemChefeTeste;
	private static Funcionario itemSubChefeTeste;
	private static Predio itemPredioTeste;
	private static GrupoMaterial itemGrupoMaterialTeste;
	private static Departamento itemDepartamentoTeste;
	private static Sala itemSalaTeste;
	private static Bem itemBemTeste;

	public int getIdLocalizacaoInserido() {
		return itemLocalTeste.getId();
	}
	
	public int getIdFuncionarioInserido() {
		return itemFuncionarioTeste.getId();
	}
	
	public int getIdChefeInserido() {
		return itemChefeTeste.getId();
	}
	
	public int getIdSubChefeInserido() {
		return itemSubChefeTeste.getId();
	}
	
	public int getIdPredioInserido() {
		return itemPredioTeste.getId();
	}
	
	public int getIdGrupoMaterialInserido() {
		return itemGrupoMaterialTeste.getId();
	}
	
	public int getIdDepartamentoInserido() {
		return itemDepartamentoTeste.getId();
	}
	
	public int getIdFuncionarioComDepartamentoInserido() {
		return itemFuncionarioComDepartamentoTeste.getId();
	}
	
	public int getIdSalaInserido() {
		return itemSalaTeste.getId();
	}
	
	public int getIdBemInserido() {
		return itemBemTeste.getId();
	}
	
	/**
	 * É necessário conectar com e inicializar o banco para poder executar os
	 * testes. Este método é executado antes de todos os testes dessa classe.
	 * 
	 * @throws IOException
	 * @throws KeyException
	 */
	public void prepararBanco() throws KeyException, IOException {
		ServerSettings.readSettings();
		InvSCPServer.connectDatabase();
		InvSCPServer.initializeDatabase();
	}

	
	public void insereLocalizacaoTeste() {
		LocalizacaoRepository localRepository = new LocalizacaoRepository();
		itemLocalTeste = new Localizacao();

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
	}

	public void inserePredioTeste() {
		PredioRepository predioRep = new PredioRepository();
		itemPredioTeste = new Predio();

		itemPredioTeste.setNome("Predio teste - JUnit temporario");
		itemPredioTeste.setIdLocalizacao(itemLocalTeste.getId());

		predioRep.add(itemPredioTeste);

		// Pesquisa o idLocalizacao adicionado
		for (Predio item : predioRep.getAll()) {
			if (item.getNome() == "Predio teste - JUnit temporario") {
				itemPredioTeste.setId(item.getId());
			}
		}
	}

	public void insereFuncionarioTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		itemFuncionarioTeste = new Funcionario();

		itemFuncionarioTeste.setLogin("teste Funcionario teste - JUnit temporario");
		itemFuncionarioTeste.setSenha("12345");
		itemFuncionarioTeste.setNome("Funcionario teste - JUnit temporario");
		itemFuncionarioTeste.setCpf("00000000000");
		itemFuncionarioTeste.setEmail("teste@gmail.com");
		
		funcRep.add(itemFuncionarioTeste);

		// Pesquisa o id adicionado
		for (Funcionario item : funcRep.getAll()) {
			if (item.getNome() == "Funcionario teste - JUnit temporario") {
				itemFuncionarioTeste.setId(item.getId());
			}
		}
	}
	
	public void insereChefeDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		itemChefeTeste = new Funcionario();

		itemChefeTeste.setLogin("teste Chefe teste - JUnit temporario");
		itemChefeTeste.setSenha("12345");
		itemChefeTeste.setNome("Chefe teste - JUnit temporario");
		itemChefeTeste.setCpf("00000000000");
		itemChefeTeste.setEmail("teste@gmail.com");

		funcRep.add(itemChefeTeste);

		// Pesquisa o id adicionado
		for (Funcionario item : funcRep.getAll()) {
			if (item.getNome() == "Chefe teste - JUnit temporario") {
				itemChefeTeste.setId(item.getId());
			}
		}
	}

	public void insereChefeSubDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		itemSubChefeTeste = new Funcionario();

		itemSubChefeTeste.setLogin("teste sub Chefe Sub teste - JUnit temporario");
		itemSubChefeTeste.setSenha("12345");
		itemSubChefeTeste.setNome("Chefe Sub teste - JUnit temporario");
		itemSubChefeTeste.setCpf("00000000000");
		itemSubChefeTeste.setEmail("teste@gmail.com");

		funcRep.add(itemSubChefeTeste);

		// Pesquisa o id adicionado
		for (Funcionario item : funcRep.getAll()) {
			if (item.getNome() == "Chefe Sub teste - JUnit temporario") {
				itemSubChefeTeste.setId(item.getId());
			}
		}
	}

	public void insereDepartamentoTeste() {
		DepartamentoRepository departamentoRep = new DepartamentoRepository();
		itemDepartamentoTeste = new Departamento();

		itemDepartamentoTeste.setNome("Departamento teste - JUnit temporario");
		itemDepartamentoTeste.setDePatrimonio(false);
		itemDepartamentoTeste.setIdChefe(itemChefeTeste.getId());
		itemDepartamentoTeste.setIdChefeSubstituto(itemSubChefeTeste.getId());

		departamentoRep.add(itemDepartamentoTeste);

		// Pesquisa o idLocalizacao adicionado
		for (Departamento item : departamentoRep.getAll()) {
			if (item.getNome() == "Predio teste - JUnit temporario") {
				itemDepartamentoTeste.setId(item.getId());
			}
		}
	}

	public void insereFuncionarioComDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		itemFuncionarioComDepartamentoTeste = new Funcionario();

		itemFuncionarioComDepartamentoTeste.setLogin("teste Funcionario com departamento teste - JUnit temporario");
		itemFuncionarioComDepartamentoTeste.setSenha("12345");
		itemFuncionarioComDepartamentoTeste.setNome("Funcionario com departamento teste - JUnit temporario");
		itemFuncionarioComDepartamentoTeste.setCpf("00000000000");
		itemFuncionarioComDepartamentoTeste.setEmail("teste@gmail.com");
		itemFuncionarioComDepartamentoTeste.setIdDepartamento(itemDepartamentoTeste.getId());

		funcRep.add(itemFuncionarioComDepartamentoTeste);

		// Pesquisa o id adicionado
		for (Funcionario item : funcRep.getAll()) {
			if (item.getNome() == "Funcionario com departamento teste - JUnit temporario") {
				itemFuncionarioComDepartamentoTeste.setId(item.getId());
			}
		}
	}
	
	public void insereSalaTeste() {
		SalaRepository salaRepository = new SalaRepository();
		itemSalaTeste = new Sala();

		itemSalaTeste.setNome("Sala teste - JUnit temporario");
		itemSalaTeste.setTipo(TipoSalaEnum.DEPOSITO);
		itemSalaTeste.setIdPredio(itemPredioTeste.getId());
		itemSalaTeste.setIdDepartamento(itemDepartamentoTeste.getId());

		salaRepository.add(itemSalaTeste);

		// Pesquisa o idSala adicionado
		for (Sala item : salaRepository.getAll()) {
			if (item.getNome() == "Sala teste - JUnit temporario") {
				itemSalaTeste.setId(item.getId());
			}
		}
	}

	public void insereGrupoMaterialTeste() {
		GrupoMaterialRepository grupoMaterialRepository = new GrupoMaterialRepository();
		itemGrupoMaterialTeste = new GrupoMaterial();
		BigDecimal depreciacao = new BigDecimal(0.2);

		itemGrupoMaterialTeste.setNome("Grupo de Material teste - JUnit temporario");
		itemGrupoMaterialTeste.setVidaUtil(5);
		itemGrupoMaterialTeste.setDepreciacao(depreciacao);

		grupoMaterialRepository.add(itemGrupoMaterialTeste);

		// Pesquisa o idGrupoMaterial adicionado
		for (GrupoMaterial item : grupoMaterialRepository.getAll()) {
			if (item.getNome() == "Grupo de Material teste - JUnit temporario") {
				itemGrupoMaterialTeste.setId(item.getId());
			}
		}
	}

	public void insereBemPatrimonialTeste() {
		BemRepository bemRepository = new BemRepository();
		itemBemTeste = new Bem();
		long numTomb = 010101010101;

		itemBemTeste.setDescricao("Bem teste - JUnit temporario");
		itemBemTeste.setNumeroTombamento(numTomb);
		itemBemTeste.setDataCadastro(LocalDate.now());
		itemBemTeste.setDataAquisicao(LocalDate.now());
		itemBemTeste.setNumeroNotaFiscal("00000000");
		itemBemTeste.setEspecificacao("teste");
		itemBemTeste.setGarantia(LocalDate.now());
		itemBemTeste.setMarca("teste");
		itemBemTeste.setValorCompra(BigDecimal.ONE);
		itemBemTeste.setSituacao(BemSituacaoEnum.INCORPORADO);
		itemBemTeste.setIdSala(itemSalaTeste.getId());
		itemBemTeste.setIdDepartamento(itemDepartamentoTeste.getId());
		itemBemTeste.setIdGrupoMaterial(itemGrupoMaterialTeste.getId());

		bemRepository.add(itemBemTeste);

		// Pesquisa o idBem adicionado
		for (Bem item : bemRepository.getAll()) {
			if (item.getDescricao() == "Bem teste - JUnit temporario") {
				itemBemTeste.setId(item.getId());
			}
		}
	}

	public void deletarBemPatrimonialTeste() {
		BemRepository bemRepository = new BemRepository();
		bemRepository.remove(itemBemTeste);
	}
	
	public void deletarGrupoMaterialTeste() {
		GrupoMaterialRepository grupoMaterialRepository = new GrupoMaterialRepository();
		grupoMaterialRepository.remove(itemGrupoMaterialTeste);
	}
	
	public void deletarSalaTeste() {
		SalaRepository salaRepository = new SalaRepository();
		salaRepository.remove(itemSalaTeste);
	}
	
	public void deletarFuncionarioComDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		funcRep.remove(itemFuncionarioComDepartamentoTeste);
	}
	
	public void deletarDepartamentoTeste() {
		DepartamentoRepository departamentoRepository = new DepartamentoRepository();
		departamentoRepository.remove(itemDepartamentoTeste);
	}

	public void deletarFuncionarioTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		funcRep.remove(itemFuncionarioTeste);
	}
	
	public void deletarChefeSubDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		funcRep.remove(itemSubChefeTeste);
	}
	
	public void deletarChefeDepartamentoTeste() {
		FuncionarioRepository funcRep = new FuncionarioRepository();
		funcRep.remove(itemChefeTeste);
	}
	
	public void deletarPredioTeste() {
		PredioRepository predioRep = new PredioRepository();
		predioRep.remove(itemPredioTeste);
	}
	
	public void deletarLocalizacaoTeste() {
		LocalizacaoRepository localizacaoRep = new LocalizacaoRepository();
		localizacaoRep.remove(itemLocalTeste);
	}
}
