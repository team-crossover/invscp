package com.github.nelsonwilliam.invscp.model.dto;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;

public class BemDTO implements DTO {

    private Integer id = null;

    private String descricao = null;

    private Long numeroTombamento = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataAquisicao = null;

    private String numeroNotaFiscal = null;

    private String especificacao = null;

    private LocalDate garantia = null;

    private String marca = null;

    private Float valorCompra = null;

    private BemSituacaoEnum situacao = null;

    private GrupoMaterialDTO grupoMaterial = null;

    private SalaDTO sala = null;

    private DepartamentoDTO departamento = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public final Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public final void setNumeroTombamento(Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public final void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public final String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public final void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public final String getEspecificacao() {
        return especificacao;
    }

    public final void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public final LocalDate getGarantia() {
        return garantia;
    }

    public final void setGarantia(LocalDate garantia) {
        this.garantia = garantia;
    }

    public final String getMarca() {
        return marca;
    }

    public final void setMarca(String marca) {
        this.marca = marca;
    }

    public final Float getValorCompra() {
        return valorCompra;
    }

    public final void setValorCompra(Float valorCompra) {
        this.valorCompra = valorCompra;
    }

    public final BemSituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(BemSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final GrupoMaterialDTO getGrupoMaterial() {
        return grupoMaterial;
    }

    public final void setGrupoMaterial(GrupoMaterialDTO grupoMaterial) {
        this.grupoMaterial = grupoMaterial;
    }

    public final SalaDTO getSala() {
        return sala;
    }

    public final void setSala(SalaDTO sala) {
        this.sala = sala;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

}
