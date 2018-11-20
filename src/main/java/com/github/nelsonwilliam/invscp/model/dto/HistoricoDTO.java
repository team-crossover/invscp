package com.github.nelsonwilliam.invscp.model.dto;

import java.util.List;

public class HistoricoDTO implements DTO {

    private BemDTO bem = null;

    private List<MovimentacaoDTO> movimentacoes = null;

    private List<OrdemServicoDTO> ordens = null;

    private BaixaDTO baixa = null;

    /**
     * Obtém o valor atual de bem.
     *
     * @return O valor atual de bem.
     */
    public final BemDTO getBem() {
        return bem;
    }

    /**
     * Atualiza o valor atual de bem.
     *
     * @param newBem O novo valor para bem.
     */
    public final void setBem(final BemDTO newBem) {
        bem = newBem;
    }

    /**
     * Obtém o valor atual de movimentacoes.
     *
     * @return O valor atual de movimentacoes.
     */
    public final List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    /**
     * Atualiza o valor atual de movimentacoes.
     *
     * @param newMovimentacoes O novo valor para movimentacoes.
     */
    public final void setMovimentacoes(
            final List<MovimentacaoDTO> newMovimentacoes) {
        movimentacoes = newMovimentacoes;
    }

    /**
     * Obtém o valor atual de ordens.
     *
     * @return O valor atual de ordens.
     */
    public final List<OrdemServicoDTO> getOrdens() {
        return ordens;
    }

    /**
     * Atualiza o valor atual de ordens.
     *
     * @param newOrdens O novo valor para ordens.
     */
    public final void setOrdens(final List<OrdemServicoDTO> newOrdens) {
        ordens = newOrdens;
    }

    /**
     * Obtém o valor atual de baixa.
     *
     * @return O valor atual de baixa.
     */
    public final BaixaDTO getBaixa() {
        return baixa;
    }

    /**
     * Atualiza o valor atual de baixa.
     *
     * @param newBaixa O novo valor para baixa.
     */
    public final void setBaixa(final BaixaDTO newBaixa) {
        baixa = newBaixa;
    }

}
