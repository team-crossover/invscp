package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.enums.UFEnum;

public class LocalizacaoDTO implements DTO<Localizacao> {

    private Integer id = null;

    private String nome = null;

    private String endereco = null;

    private String cep = null;

    private String cidade = null;

    private UFEnum uf = null;

    @Override
    public void setValuesFromModel(Localizacao model) {
        this.id = model.getId();
        this.nome = model.getNome();
        this.endereco = model.getEndereco();
        this.cep = model.getCep();
        this.cidade = model.getCidade();
        this.uf = model.getUf();

    }

    @Override
    public Localizacao toModel() {
        final Localizacao local = new Localizacao();
        local.setCep(cep);
        local.setCidade(cidade);
        local.setEndereco(endereco);
        local.setId(id);
        local.setNome(nome);
        local.setUf(uf);

        return local;
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

    public final String getEndereco() {
        return endereco;
    }

    public final void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public final String getCep() {
        return cep;
    }

    public final void setCep(String cep) {
        this.cep = cep;
    }

    public final String getCidade() {
        return cidade;
    }

    public final void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public final UFEnum getUf() {
        return uf;
    }

    public final void setUf(UFEnum uf) {
        this.uf = uf;
    }

    public String getUfString() {
        return uf.toString();
    }

    public void setUfString(final String uf) {
        this.uf = UFEnum.valueOf(uf);
    }

}
