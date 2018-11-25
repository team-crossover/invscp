package com.github.nelsonwilliam.invscp.server.model;

import java.math.BigDecimal;

import com.github.nelsonwilliam.invscp.shared.model.dto.GrupoMaterialDTO;

public class GrupoMaterial implements Model<GrupoMaterialDTO> {

    private static final long serialVersionUID = -6427352153107008850L;

    private Integer id = null;

    private String nome = null;

    private Integer vidaUtil = null;

    private BigDecimal depreciacao = null;

    @Override
    public void setValuesFromDTO(final GrupoMaterialDTO dto) {
        setDepreciacao(dto.getDepreciacao());
        setId(dto.getVidaUtil());
        setNome(dto.getNome());
        setVidaUtil(dto.getVidaUtil());
    }

    @Override
    public GrupoMaterialDTO toDTO() {
        final GrupoMaterialDTO dto = new GrupoMaterialDTO();
        dto.setDepreciacao(depreciacao);
        dto.setId(id);
        dto.setNome(nome);
        dto.setVidaUtil(vidaUtil);
        return dto;
    }

    @Override
    public final Integer getId() {
        return id;
    }

    @Override
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
