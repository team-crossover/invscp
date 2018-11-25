package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.enums.UFEnum;

public class LocalizacaoDTO implements DTO {

    private Integer id = null;

    private String nome = null;

    private String endereco = null;

    private String cep = null;

    private String cidade = null;

    private UFEnum uf = null;

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

    public final String getEndereco() {
        return endereco;
    }

    public final void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    public final String getCep() {
        return cep;
    }

    public final void setCep(final String cep) {
        this.cep = cep;
    }

    public final String getCidade() {
        return cidade;
    }

    public final void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public final UFEnum getUf() {
        return uf;
    }

    public final void setUf(final UFEnum uf) {
        this.uf = uf;
    }

    public String getUfString() {
        return uf.toString();
    }

    public void setUfString(final String uf) {
        this.uf = UFEnum.valueOf(uf);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocalizacaoDTO other = (LocalizacaoDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
