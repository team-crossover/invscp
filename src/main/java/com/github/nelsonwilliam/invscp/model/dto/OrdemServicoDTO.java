package com.github.nelsonwilliam.invscp.model.dto;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.enums.OSsituacaoEnum;

public class OrdemServicoDTO implements DTO {
    private Integer id = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataConclusao = null;

    private Float valor = null;

    private OSsituacaoEnum situacao = null;

    private FuncionarioDTO funcionario = null;

    private BemDTO bem = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public final void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public final Float getValor() {
        return valor;
    }

    public final void setValor(Float valor) {
        this.valor = valor;
    }

    public final OSsituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(OSsituacaoEnum situacao) {
        this.situacao = situacao;
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
