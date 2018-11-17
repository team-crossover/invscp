package com.github.nelsonwilliam.invscp.model.dto;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.enums.MotivoBaixaEnum;

public class BaixaDTO implements DTO {

    private Integer id = null;

    private LocalDate data = null;

    private MotivoBaixaEnum motivo = null;

    private String observacoes = null;

    private FuncionarioDTO funcionario = null;

    private BemDTO bem = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final LocalDate getData() {
        return data;
    }

    public final void setData(LocalDate data) {
        this.data = data;
    }

    public final MotivoBaixaEnum getMotivo() {
        return motivo;
    }

    public final void setMotivo(MotivoBaixaEnum motivo) {
        this.motivo = motivo;
    }

    public final String getObservacoes() {
        return observacoes;
    }

    public final void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public final FuncionarioDTO getFuncionario() {
        return funcionario;
    }

    public final void setFuncionario(FuncionarioDTO funcionario) {
        this.funcionario = funcionario;
    }

    public final BemDTO getBem() {
        return bem;
    }

    public final void setBem(BemDTO bem) {
        this.bem = bem;
    }

}
