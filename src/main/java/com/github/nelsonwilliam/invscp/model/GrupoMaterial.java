package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.GrupoMaterialDTO;

public class GrupoMaterial implements Model<GrupoMaterialDTO> {

    private static final long serialVersionUID = -6427352153107008850L;

    private Integer id = null;

    private String nome = null;

    private Integer vidaUtil = null;

    private Float depreciacao = null;

    @Override
    public void setValuesFromDTO(GrupoMaterialDTO dto) {
        setDepreciacao(dto.getDepreciacao());
        setId(dto.getVidaUtil());
        setNome(dto.getNome());
        setVidaUtil(dto.getVidaUtil());
    }

    @Override
    public GrupoMaterialDTO toDTO() {
        GrupoMaterialDTO dto = new GrupoMaterialDTO();
        dto.setDepreciacao(depreciacao);
        dto.setId(id);
        dto.setNome(nome);
        dto.setVidaUtil(vidaUtil);
        return null;
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

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}
