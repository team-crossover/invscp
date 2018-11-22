package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoMovEnum;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;

public class EventoMovimentacao implements Model<EventoMovimentacaoDTO> {

    private static final long serialVersionUID = 3192048412630028218L;

    private Integer id = null;

    private TipoMovEnum tipo = null;

    private LocalDate data = null;

    private String justificativa = null;

    private Integer idMovimentacao = null;

    private Integer idFuncionario = null;

    @Override
	public void setValuesFromDTO(final EventoMovimentacaoDTO dto) {
		setId(dto.getId());
		setTipo(dto.getTipo());
		setData(dto.getData());
		setJustificativa(dto.getJustificativa());
		if (dto.getMovimentacao() != null) {
		    setIdMovimentacao(dto.getMovimentacao().getId());
		}
		if (dto.getFuncionario() != null) {
		    setIdFuncionario(dto.getFuncionario().getId());
		}
	}

	@Override
	public EventoMovimentacaoDTO toDTO() {
		final EventoMovimentacaoDTO dto = new EventoMovimentacaoDTO();
		dto.setId(id);
		dto.setTipo(tipo);
		dto.setData(data);
		dto.setJustificativa(justificativa);
		if (idMovimentacao != null) {
		    final MovimentacaoRepository repo = new MovimentacaoRepository();
		    final Movimentacao mov = repo.getById(idMovimentacao);
		    dto.setMovimentacao(mov == null ? null : mov.toDTO());
		}
		if (idFuncionario != null) {
            final FuncionarioRepository repo = new FuncionarioRepository();
            final Funcionario func = repo.getById(idFuncionario);
            func.setIdDepartamento(null);
            dto.setFuncionario(func == null ? null : func.toDTO());
        }
		return dto;
	}

	@Override
	public Integer getId() {
	    return id;
	}

	@Override
	public void setId(final Integer idValor) {
	    this.id = idValor;
	}

	public TipoMovEnum getTipo() {
        return tipo;
    }

	public String getTipoString() {
	    return tipo.getTexto();
	}

    public void setTipo(final TipoMovEnum tipo) {
        this.tipo = tipo;
    }

    public void setTipoString(final String tipoNovo) {
        this.tipo = TipoMovEnum.valueOfTexto(tipoNovo);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(final LocalDate data) {
        this.data = data;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(final String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(final Integer idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(final Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
}
