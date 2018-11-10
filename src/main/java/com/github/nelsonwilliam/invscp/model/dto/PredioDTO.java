package com.github.nelsonwilliam.invscp.model.dto;

public class PredioDTO implements DTO {

    private Integer id = null;

    private String nome = null;

    private LocalizacaoDTO localizacao = null;

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

    public final LocalizacaoDTO getLocalizacao() {
        return localizacao;
    }

    public final void setLocalizacao(final LocalizacaoDTO localizacao) {
        this.localizacao = localizacao;
    }

}
