package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.enums.UFEnum;

public class Localizacao implements Model {

    private static final long serialVersionUID = 6358078005778674613L;

    private Integer id = null;

    private String nome = null;

    private String endereco = null;

    private String cep = null;

    private String cidade = null;

    private UFEnum uf = null;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public UFEnum getUf() {
        return uf;
    }

    public String getUfString() {
        return uf.toString();
    }

    public void setUf(final String uf) {
        this.uf = UFEnum.valueOf(uf);
    }

    public void setUf(final UFEnum uf) {
        this.uf = uf;
    }

    // public List<Predio> getPredios() {
    // PredioRepository predRepo = new PredioRepository()
    // return
    // }
}
