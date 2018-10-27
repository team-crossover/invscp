package com.github.nelsonwilliam.invscp.model;

public class Sala {
	
	private Integer id = null;

	private String nome = "Sala";
	
	private boolean deDeposito = false;
	
	private SalasEnum tipoSala;

	public SalasEnum getTipoSala() {
		return tipoSala;
	}

	public void setTipoSala(SalasEnum tipoSala) {
		this.tipoSala = tipoSala;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isDeDeposito() {
		return deDeposito;
	}

	public void setDeDeposito(boolean deDeposito) {
		this.deDeposito = deDeposito;
	}

}

