package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;

public class PredioDTO implements DTO<Predio> {

    private Integer id = null;

    private String nome = null;

    private LocalizacaoDTO localizacao = null;

    @Override
    public void setValuesFromModel(Predio model) {
        this.id = model.getId();
        this.nome = model.getNome();

        if (model.getIdLocalizacao() != null) {
            final LocalizacaoRepository repo = new LocalizacaoRepository();
            final Localizacao local = repo.getById(model.getIdLocalizacao());

            this.localizacao = new LocalizacaoDTO();
            this.localizacao.setValuesFromModel(local);
        }

    }

    @Override
    public Predio toModel() {
        final Predio predio = new Predio();
        predio.setId(id);
        predio.setNome(nome);
        if (localizacao != null) {
            predio.setIdLocalizacao(localizacao.getId());
        }

        return predio;
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        this.nome = nome;
    }

    public final LocalizacaoDTO getLocalizacao() {
        return localizacao;
    }

    public final void setLocalizacao(LocalizacaoDTO localizacao) {
        this.localizacao = localizacao;
    }

}
