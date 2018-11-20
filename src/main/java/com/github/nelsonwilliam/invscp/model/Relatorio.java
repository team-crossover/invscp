package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.RelatorioDTO;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Relatorio implements Model<RelatorioDTO> {

    private static final long serialVersionUID = -2178389683553521306L;

    private Integer id = null;

    private Integer idBem = null;

    private Integer idDepartamento = null;

    private Integer idGrupoMaterial = null;

    private Integer idSala = null;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public void setValuesFromDTO(RelatorioDTO dto) {
        setId(dto.getId());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getDepartamento() != null) {
            setIdDepartamento(dto.getDepartamento().getId());
        }
        if (dto.getGrupoMaterial() != null) {
            setIdGrupoMaterial(dto.getGrupoMaterial().getId());
        }
        if (dto.getSala() != null) {
            setIdSala(dto.getSala().getId());
        }
    }

    @Override
    public RelatorioDTO toDTO() {
        final RelatorioDTO dto = new RelatorioDTO();
        dto.setId(id);
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idDepartamento != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(idDepartamento);
            dto.setDepartamento(dept == null ? null : dept.toDTO());
        }
        if (idGrupoMaterial != null) {
            final GrupoMaterialRepository repo = new GrupoMaterialRepository();
            final GrupoMaterial gm = repo.getById(idGrupoMaterial);
            dto.setGrupoMaterial(gm == null ? null : gm.toDTO());
        }
        if (idSala != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala sala = repo.getById(idSala);
            dto.setSala(sala == null ? null : sala.toDTO());
        }
        return dto;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(Integer idBem) {
        this.idBem = idBem;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdGrupoMaterial() {
        return idGrupoMaterial;
    }

    public void setIdGrupoMaterial(Integer idGrupoMaterial) {
        this.idGrupoMaterial = idGrupoMaterial;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

}
