package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.shared.model.enums.TipoEventoMovEnum;

public class EventoMovimentacaoDTO implements DTO {

    private static final long serialVersionUID = 7420149205107692656L;

    private Integer id = null;

    private TipoEventoMovEnum tipo = null;

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

    public TipoEventoMovEnum getTipo() {
        return tipo;
    }

    public void setTipo(final TipoEventoMovEnum tipo) {
        this.tipo = tipo;
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
