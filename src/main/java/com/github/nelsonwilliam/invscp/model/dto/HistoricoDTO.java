package com.github.nelsonwilliam.invscp.model.dto;

public class HistoricoDTO implements DTO {

    private Integer id = null;

    private BemDTO bem = null;

    private MovimentacaoDTO movimentacao = null;
 
    private OrdemServicoDTO ordem = null;

    private BaixaDTO baixa = null;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BemDTO getBem() {
        return bem;
    }

    public void setBem(final BemDTO bem) {
        this.bem = bem;
    }

    public MovimentacaoDTO getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(final MovimentacaoDTO movimentacao) {
        this.movimentacao = movimentacao;
    }

    public OrdemServicoDTO getOrdem() {
        return ordem;
    }

    public void setOrdem(final OrdemServicoDTO ordem) {
        this.ordem = ordem;
    }

    public BaixaDTO getBaixa() {
        return baixa;
    }

    public void setBaixa(final BaixaDTO baixa) {
        this.baixa = baixa;
    }

}
