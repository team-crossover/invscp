package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.util.List;

import com.github.nelsonwilliam.invscp.shared.model.enums.EtapaMovEnum;

public class MovimentacaoDTO implements DTO {

    private static final long serialVersionUID = 3759719184230530488L;

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private BemDTO bem = null;

    private SalaDTO salaOrigem = null;

    private SalaDTO salaDestino = null;

    private List<EventoMovimentacaoDTO> eventos = null;

    private String numGuiaTransporte = null;

    public boolean isInterna() {
        return salaOrigem.getDepartamento().getId()
                .equals(salaDestino.getDepartamento().getId());
    }

    public boolean isParaMesmaCidade() {
        return salaOrigem.getPredio().getLocalizacao().getUf()
                .equals(salaDestino.getPredio().getLocalizacao().getUf())
                && salaOrigem.getPredio().getLocalizacao().getCidade().equals(
                        salaDestino.getPredio().getLocalizacao().getCidade());
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public EtapaMovEnum getEtapa() {
        return etapa;
    }

    public void setEtapa(final EtapaMovEnum etapa) {
        this.etapa = etapa;
    }

    public BemDTO getBem() {
        return bem;
    }

    public void setBem(final BemDTO bem) {
        this.bem = bem;
    }

    public SalaDTO getSalaOrigem() {
        return salaOrigem;
    }

    public void setSalaOrigem(final SalaDTO salaOrigem) {
        this.salaOrigem = salaOrigem;
    }

    public SalaDTO getSalaDestino() {
        return salaDestino;
    }

    public void setSalaDestino(final SalaDTO salaDestino) {
        this.salaDestino = salaDestino;
    }

    public final List<EventoMovimentacaoDTO> getEventos() {
        return eventos;
    }

    public final void setEventos(final List<EventoMovimentacaoDTO> newEventos) {
        eventos = newEventos;
    }

    public String getNumGuiaTransporte() {
        return numGuiaTransporte;
    }

    public void setNumGuiaTransporte(final String numGuiaTransporte) {
        this.numGuiaTransporte = numGuiaTransporte;
    }

}
