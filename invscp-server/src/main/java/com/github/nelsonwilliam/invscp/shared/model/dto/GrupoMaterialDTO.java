package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.math.BigDecimal;

public class GrupoMaterialDTO implements DTO {

    private static final long serialVersionUID = 6474210844547892964L;

    private Integer id = null;

    private String nome = null;

    private Integer vidaUtil = null;

    private BigDecimal depreciacao = null;

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

    public final Integer getVidaUtil() {
        return vidaUtil;
    }

    public final void setVidaUtil(final Integer vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public final BigDecimal getDepreciacao() {
        return depreciacao;
    }

    public final void setDepreciacao(final BigDecimal depreciacao) {
        this.depreciacao = depreciacao;
    }

}
