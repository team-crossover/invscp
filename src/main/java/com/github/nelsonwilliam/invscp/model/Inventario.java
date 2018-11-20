package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.InventarioDTO;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;

public class Inventario {

    private static final long serialVersionUID = 8991213633188631666L;

    private final List<Integer> idBens = null;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer integer) {
        id = integer;
    }

    @Override
    public void setValuesFromDTO(final InventarioDTO dto) {
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
    }

    @Override
    public InventarioDTO toDTO() {
        final InventarioDTO dto = new InventarioDTO();
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        return dto;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(final Integer idBem) {
        this.idBem = idBem;
    }

}
