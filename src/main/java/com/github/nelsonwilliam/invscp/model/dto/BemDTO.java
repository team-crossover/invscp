package com.github.nelsonwilliam.invscp.model.dto;

import java.math.BigDecimal;
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

    private BigDecimal valorCompra = null;

    private BemSituacaoEnum situacao = null;

    private GrupoMaterialDTO grupoMaterial = null;

    private SalaDTO sala = null;

    private DepartamentoDTO departamento = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public final Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public final void setNumeroTombamento(final Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(final LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public final void setDataAquisicao(final LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public final String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public final void setNumeroNotaFiscal(final String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public final String getEspecificacao() {
        return especificacao;
    }

    public final void setEspecificacao(final String especificacao) {
        this.especificacao = especificacao;
    }

    public final LocalDate getGarantia() {
        return garantia;
    }

    public final void setGarantia(final LocalDate garantia) {
        this.garantia = garantia;
    }

    public final String getMarca() {
        return marca;
    }

    public final void setMarca(final String marca) {
        this.marca = marca;
    }

    public final BigDecimal getValorCompra() {
        return valorCompra;
    }

    public final void setValorCompra(final BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public final BemSituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(final BemSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final GrupoMaterialDTO getGrupoMaterial() {
        return grupoMaterial;
    }

    public final void setGrupoMaterial(final GrupoMaterialDTO grupoMaterial) {
        this.grupoMaterial = grupoMaterial;
    }

    public final SalaDTO getSala() {
        return sala;
    }

    public final void setSala(final SalaDTO sala) {
        this.sala = sala;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(final DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

}
