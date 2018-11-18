package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;

public class EventoMovimentacao implements Model<EventoMovimentacaoDTO> {

    private static final long serialVersionUID = 3192048412630028218L;

    private Integer id = null;

    private String tipo = null;

    private LocalDate data = null;

    private String justificativa = null;

    private Integer idMovimentacao = null;

    private Integer idFuncionario = null;

	@Override
	public Integer getId() {
	    return id;
	}

	@Override
	public void setId(Integer idValor) {
	    this.id = idValor;
	}

	public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(Integer idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
	public void setValuesFromDTO(EventoMovimentacaoDTO model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventoMovimentacaoDTO toDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
