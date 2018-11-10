package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;

public class SalaDTO implements DTO {

    private Integer id = null;

    private String nome = null;

    private TipoSalaEnum tipo = null;

    private PredioDTO predio = null;

    private DepartamentoDTO departamento = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(final String nome) {
        this.nome = nome;
    }

    public final TipoSalaEnum getTipo() {
        return tipo;
    }

    public final void setTipo(final TipoSalaEnum tipo) {
        this.tipo = tipo;
    }

    public final PredioDTO getPredio() {
        return predio;
    }

    public final void setPredio(final PredioDTO predio) {
        this.predio = predio;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(final DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

}
