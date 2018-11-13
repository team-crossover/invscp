package com.github.nelsonwilliam.invscp.model.dto;

public class GrupoMaterialDTO implements DTO {

    private Integer id = null;

    private String nome = null;

    private Integer vidaUtil = null;

    private Float depreciacao = null;

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

    public final Integer getVidaUtil() {
        return vidaUtil;
    }

    public final void setVidaUtil(Integer vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public final Float getDepreciacao() {
        return depreciacao;
    }

    public final void setDepreciacao(Float depreciacao) {
        this.depreciacao = depreciacao;
    }

}
