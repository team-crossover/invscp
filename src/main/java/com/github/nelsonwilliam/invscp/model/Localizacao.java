package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.UFEnum;

public class Localizacao implements Model {

    private static final long serialVersionUID = 6358078005778674613L;

    private Integer id = null;

    private String nome = "Localizacao";

    private String endereco;

    private String cep;

    private String cidade;

    private UFEnum uf;

    private String pais;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public UFEnum getUf() {
        return uf;
    }

    public String getUfString() {
    	return uf.toString();
    }

    public void setUf(String uf) {
        this.uf = UFEnum.valueOf(uf);
    }

    public void setUf(UFEnum uf) {
    	this.uf = uf;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    // public List<Predio> getPredios() {
    // PredioRepository predRepo = new PredioRepository()
    // return
    // }
}
