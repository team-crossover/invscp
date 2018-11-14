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

	public String getEtapa() {
	    return this.etapa.getTexto();
	}

	public void setEtapa(String etapa) {
	    this.etapa = EtapaMovEnum.valueOfTexto(etapa);
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer idValor) {
		this.id = idValor;
	}

	@Override
	public void setValuesFromDTO(MovimentacaoDTO model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MovimentacaoDTO toDTO() {
		// TODO Auto-generated method stub
		return null;
	}

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(Integer idBem) {
        this.idBem = idBem;
    }

    public Integer getIdSalaOrigem() {
        return idSalaOrigem;
    }

    public void setIdSalaOrigem(Integer idSalaOrigem) {
        this.idSalaOrigem = idSalaOrigem;
    }

    public Integer getIdSalaDestino() {
        return idSalaDestino;
    }

    public void setIdSalaDestino(Integer idSalaDestino) {
        this.idSalaDestino = idSalaDestino;
    }


}
