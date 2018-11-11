package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;

public class Bem implements Model<BemDTO>{

	private Integer id = null;

	private String descricao = null;

	private Integer numeroTombamento = null;

	private LocalDate dataCadastro = null;

	private LocalDate dataAquisicao = null;

	private String numeroNotaFiscal = null;

	private String especificacao = null;

	private String garantia = null;

	private String marca = null;

	private Float valorCompra = null;

	private String situacao = null;

	private Integer idSala = null;

	private Integer idDepartamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNumeroTombamento() {
		return numeroTombamento;
	}

	public void setNumeroTombamento(Integer numeroTombamento) {
		this.numeroTombamento = numeroTombamento;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(LocalDate dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Float getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Float valorCompra) {
		this.valorCompra = valorCompra;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getIdSala() {
		return idSala;
	}

	public void setIdSala(Integer idSala) {
		this.idSala = idSala;
	}

	public Integer getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	@Override
	public void setValuesFromDTO(BemDTO model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BemDTO toDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
