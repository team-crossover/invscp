package com.github.nelsonwilliam.invscp.model.dto;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.enums.TipoMovEnum;

public class EventoMovimentacaoDTO implements DTO {

    private Integer id = null;

    private TipoMovEnum tipo = null;

    private LocalDate data = null;

    private String justificativa = null;

    private MovimentacaoDTO movimentacao = null;

    private FuncionarioDTO funcionario = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public TipoMovEnum getTipo() {
        return tipo;
    }

    public String getTipoString() {
        return tipo.getTexto();
    }

    public void setTipo(final TipoMovEnum tipo) {
        this.tipo = tipo;
    }

    public void setTipoString(final String tipoNovo) {
        this.tipo = TipoMovEnum.valueOfTexto(tipoNovo);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(final LocalDate data) {
        this.data = data;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(final String justificativa) {
        this.justificativa = justificativa;
    }

    public MovimentacaoDTO getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(final MovimentacaoDTO movimentacao) {
        this.movimentacao = movimentacao;
    }

    public FuncionarioDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(final FuncionarioDTO funcionario) {
        this.funcionario = funcionario;
    }

}
