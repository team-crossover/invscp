package com.github.nelsonwilliam.invscp.shared.model.dto;

import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

public class FiltroBemDTO implements DTO {

    private static final long serialVersionUID = -2777572098127366339L;

    private String descricao = null;

    private String numeroTombamento = null;

    private BemSituacaoEnum situacao = null;

    private DepartamentoDTO departamento = null;

    public boolean isEmpty() {
        return (descricao == null || descricao.isEmpty())
                && numeroTombamento == null && situacao == null
                && departamento == null;
    }

    /**
     * Obtém o valor atual de descricao
     *
     * @return O valor atual de descricao.
     */
    public final String getDescricao() {
        return descricao;
    }

    /**
     * Atualiza o valor atual de descricao.
     *
     * @param newDescricao O novo valor de descricao.
     */
    public final void setDescricao(final String newDescricao) {
        descricao = newDescricao;
    }

    /**
     * Obtém o valor atual de numeroTombamento
     *
     * @return O valor atual de numeroTombamento.
     */
    public final String getNumeroTombamento() {
        return numeroTombamento;
    }

    /**
     * Atualiza o valor atual de numeroTombamento.
     *
     * @param newNumeroTombamento O novo valor de numeroTombamento.
     */
    public final void setNumeroTombamento(final String newNumeroTombamento) {
        numeroTombamento = newNumeroTombamento;
    }

    /**
     * Obtém o valor atual de situacao
     *
     * @return O valor atual de situacao.
     */
    public final BemSituacaoEnum getSituacao() {
        return situacao;
    }

    /**
     * Atualiza o valor atual de situacao.
     *
     * @param newSituacao O novo valor de situacao.
     */
    public final void setSituacao(final BemSituacaoEnum newSituacao) {
        situacao = newSituacao;
    }

    /**
     * Obtém o valor atual de departamento
     *
     * @return O valor atual de departamento.
     */
    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    /**
     * Atualiza o valor atual de departamento.
     *
     * @param newIdDepartamento O novo valor de departamento.
     */
    public final void setDepartamento(final DepartamentoDTO newDepartamento) {
        departamento = newDepartamento;
    }

}
