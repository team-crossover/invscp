package com.github.nelsonwilliam.invscp.model;

import java.util.List;

import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;

public class Predio implements Model {

    private static final long serialVersionUID = -2918874149512056756L;

    private Integer id = null;

    private String nome = null;

    private Integer idLocalizacao = null;

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

    public Integer getIdLocalizacao() {
        return idLocalizacao;
    }

    public Localizacao getLocalizacao() {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        return locaRepo.getById(idLocalizacao);
    }

    public void setIdLocalizacao(final Integer idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

    public List<Localizacao> getPossiveisLocalizacoes() {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        return locaRepo.getAll();
    }
}
