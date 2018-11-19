package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Movimentacao implements Model<MovimentacaoDTO> {

	/**
	 *
	 */
    private static final long serialVersionUID = 1L;

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private Integer idBem = null;

    private Integer idSalaOrigem = null;

    private Integer idSalaDestino = null;

    @Override
    public void setValuesFromDTO(final MovimentacaoDTO dto) {
        setId(dto.getId());
        setEtapa(dto.getEtapa());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getSalaOrigem() != null) {
            setIdSalaOrigem(dto.getSalaOrigem().getId());
        }
        if (dto.getSalaDestino() != null) {
            setIdSalaDestino(dto.getSalaDestino().getId());
        }

    }

    @Override
    public MovimentacaoDTO toDTO() {
        final MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setId(id);
        dto.setEtapa(etapa);
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            bem.setIdDepartamento(null);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idSalaOrigem != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaO = repo.getById(idSalaOrigem);
            salaO.setIdDepartamento(null);
            dto.setSalaOrigem(salaO == null ? null : salaO.toDTO());
        }
        if (idSalaDestino != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaD = repo.getById(idSalaOrigem);
            salaD.setIdDepartamento(null);
            dto.setSalaDestino(salaD == null ? null : salaD.toDTO());
        }
        return dto;
    }

    public EtapaMovEnum getEtapa() {
        return etapa;
	}

    public void setEtapa(final EtapaMovEnum etapa) {
        this.etapa = etapa;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(final Integer idValor) {
		id = idValor;
	}

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(final Integer idBem) {
        this.idBem = idBem;
    }

    public Integer getIdSalaOrigem() {
        return idSalaOrigem;
    }

    public void setIdSalaOrigem(final Integer idSalaOrigem) {
        this.idSalaOrigem = idSalaOrigem;
    }

    public Integer getIdSalaDestino() {
        return idSalaDestino;
    }

    public void setIdSalaDestino(final Integer idSalaDestino) {
        this.idSalaDestino = idSalaDestino;
    }


}
