package com.github.nelsonwilliam.invscp.model.dto;

public class RelatorioDTO implements DTO {

    private Integer id = null;

    private BemDTO bem = null;

    private DepartamentoDTO departamento = null;

    private GrupoMaterialDTO grupoMaterial = null;

    private SalaDTO sala = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BemDTO getBem() {
        return bem;
    }

    public void setBem(final BemDTO bem) {
        this.bem = bem;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(final DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public GrupoMaterialDTO getGrupoMaterial() {
        return grupoMaterial;
    }

    public void setGrupoMaterial(final GrupoMaterialDTO grupoMaterial) {
        this.grupoMaterial = grupoMaterial;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(final SalaDTO sala) {
        this.sala = sala;
    }

}
