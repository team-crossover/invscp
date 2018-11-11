package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

public class OrdemServico {

	private Integer id = null;

	private LocalDate dataCadastro = null;

	private LocalDate dataConclusao = null;

	private Float valor = null;

	private String situacao = null;

	private Integer idFuncionario = null;

	private Integer idBem = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDate dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Integer getIdBem() {
		return idBem;
	}

	public void setIdBem(Integer idBem) {
		this.idBem = idBem;
	}
}
