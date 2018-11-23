package com.github.nelsonwilliam.invscp.model;

import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.EventoMovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Movimentacao implements Model<MovimentacaoDTO> {

    private static final long serialVersionUID = 336584770255104292L;

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private Integer idBem = null;

    private Integer idSalaOrigem = null;

    private Integer idSalaDestino = null;

    private String numGuiaTransporte = null;

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
        setNumGuiaTransporte(dto.getNumGuiaTransporte());

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

        final EventoMovimentacaoRepository eventoRepo =
                new EventoMovimentacaoRepository();
        final List<EventoMovimentacaoDTO> eventosDto = new ArrayList<>();
        final List<EventoMovimentacao> eventos =
                eventoRepo.getByMovimentacao(this);
        for (EventoMovimentacao ev : eventos) {
            eventosDto.add(ev.toDTO());
        }
        dto.setEventos(eventosDto);
        dto.setNumGuiaTransporte(numGuiaTransporte);

        return dto;
    }

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idMov) throws IllegalDeleteException {

        throw new IllegalDeleteException(
                "Não é possível deletar movimentações.");
    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final BaixaDTO novaMov)
            throws IllegalUpdateException {

        throw new IllegalUpdateException(
                "Não é possível alterar movimentações.");
    }

    public static void validarInserir(final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {
        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    public static void validarAceiteSaida(final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {
        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    public static void validarCancelar(final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {
        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    private static void validarCampos(final MovimentacaoDTO mov)
            throws CRUDException {
        if (mov.getBem() == null) {
            throw new CRUDException("O 'Bem' não pode ser vazio.");
        }
        // Definida inicialmente pelo sistema
        if (mov.getEtapa() == null) {
            throw new CRUDException("A 'Etapa' deve ser definida.");
        }
        if (mov.getSalaOrigem() == null) {
            throw new CRUDException("A 'Sala de Origem' não pode ser vazia.");
        }
        if (mov.getSalaDestino() == null) {
            throw new CRUDException("A 'Sala de Destino' não pode ser vazia.");
        }
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

    public String getNumGuiaTransporte() {
        return numGuiaTransporte;
    }

    public void setNumGuiaTransporte(String numGuiaTransporte) {
        this.numGuiaTransporte = numGuiaTransporte;
    }

}
