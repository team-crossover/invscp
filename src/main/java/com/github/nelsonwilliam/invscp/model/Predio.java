package com.github.nelsonwilliam.invscp.model;

public class Predio implements Model {

    private static final long serialVersionUID = -2918874149512056756L;

    private Integer id = null;

    private String nome = "Predio";

    private Integer idLocalizacao = null;

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

    public Integer getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setIdLocalizacao(Integer idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    // public List<Sala> getSalas() {
    // SalaRepository salaRepo = new SalaRepository()
    // return
    // }

}
