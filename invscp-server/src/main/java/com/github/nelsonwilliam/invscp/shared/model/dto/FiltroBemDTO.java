package com.github.nelsonwilliam.invscp.shared.model.dto;

import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

public class FiltroBemDTO implements DTO {

    private static final long serialVersionUID = -2777572098127366339L;

    private String descricao;

    private Integer numeroTombamento;

    private BemSituacaoEnum situacao;

    private Integer idDepartamento;

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
    public final Integer getNumeroTombamento() {
        return numeroTombamento;
    }

    /**
     * Atualiza o valor atual de numeroTombamento.
     * 
     * @param newNumeroTombamento O novo valor de numeroTombamento.
     */
    public final void setNumeroTombamento(final Integer newNumeroTombamento) {
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
     * Obtém o valor atual de idDepartamento
     * 
     * @return O valor atual de idDepartamento.
     */
    public final Integer getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * Atualiza o valor atual de idDepartamento.
     * 
     * @param newIdDepartamento O novo valor de idDepartamento.
     */
    public final void setIdDepartamento(final Integer newIdDepartamento) {
        idDepartamento = newIdDepartamento;
    }

    /**
     * Obtém o valor atual de serialversionuid
     * 
     * @return O valor atual de serialversionuid.
     */
    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}