package com.github.nelsonwilliam.invscp.model.dto;

import java.util.List;

import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;

public class MovimentacaoDTO implements DTO {

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private BemDTO bem = null;

    private SalaDTO salaOrigem = null;

    private SalaDTO salaDestino = null;

    private List<EventoMovimentacaoDTO> eventos = null;

    private String numGuiaTransporte = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public EtapaMovEnum getEtapa() {
        return etapa;
    }

    public String getEtapaString() {
        return etapa.getTexto();
    }

    public void setEtapa(final EtapaMovEnum etapa) {
        this.etapa = etapa;
    }

    public void setEtapaString(final String etapaNova) {
        this.etapa = EtapaMovEnum.valueOfTexto(etapaNova);
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

    public final void setEventos(List<EventoMovimentacaoDTO> newEventos) {
        eventos = newEventos;
    }

    public String getNumGuiaTransporte() {
        return numGuiaTransporte;
    }

    public void setNumGuiaTransporte(String numGuiaTransporte) {
        this.numGuiaTransporte = numGuiaTransporte;
    }

}
