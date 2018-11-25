package com.github.nelsonwilliam.invscp.shared.model.dto;

public class PredioDTO implements DTO {

    private static final long serialVersionUID = -3225502253460762708L;

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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PredioDTO other = (PredioDTO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
