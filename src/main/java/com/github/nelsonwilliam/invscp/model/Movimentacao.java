package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;

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
    public void setValuesFromDTO(final MovimentacaoDTO model) {
        // TODO Auto-generated method stub

    }

    @Override
    public MovimentacaoDTO toDTO() {
        // TODO Auto-generated method stub
        return null;
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
