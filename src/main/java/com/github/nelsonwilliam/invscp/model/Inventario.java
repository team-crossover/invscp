package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.InventarioDTO;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;

public class Inventario {

    private List<Integer> idBens = null;

    private LocalDateTime momentoGeracao = null;

    public void setValuesFromDTO(final InventarioDTO dto) {
        setMomentoGeracao(dto.getMomentoGeracao());
        if (dto.getBens() != null) {
            final List<Integer> idBens = new ArrayList<Integer>();
            for (final BemDTO bem : dto.getBens()) {
                idBens.add(bem.getId());
            }
            setIdBens(idBens);
        }
    }

    public InventarioDTO toDTO() {
        final InventarioDTO dto = new InventarioDTO();
        dto.setMomentoGeracao(momentoGeracao);
        if (idBens != null) {
            final List<BemDTO> bens = new ArrayList<BemDTO>();
            for (final Integer idBem : idBens) {
                final BemRepository repo = new BemRepository();
                final Bem bem = repo.getById(idBem);
                if (bem != null) {
                    bens.add(bem.toDTO());
                }
            }
            dto.setBens(bens);
        }
        return dto;
    }

    /**
     * Obtém o valor atual de idBens.
     *
     * @return O valor atual de idBens.
     */
    public final List<Integer> getIdBens() {
        return idBens;
    }

    /**
     * Atualiza o valor atual de idBens.
     *
     * @param newIdBens O novo valor para idBens.
     */
    public final void setIdBens(final List<Integer> newIdBens) {
        idBens = newIdBens;
    }

    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    public final void setMomentoGeracao(LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

}
