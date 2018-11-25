package com.github.nelsonwilliam.invscp.shared.model.enums;

import java.io.Serializable;

public enum BemSituacaoEnum implements Serializable {
    INCORPORADO(1, "Incorporado"), EM_CONSERTO(2,
            "Em conserto"), EM_MOVIMENTACAO(3,
                    "Em movimentação"), BAIXADO(4, "Baixado");

    private final int valor;
    private final String texto;

    private BemSituacaoEnum(final int valorOpcao, final String textoOpcao) {
        valor = valorOpcao;
        texto = textoOpcao;
    }

    public static BemSituacaoEnum valueOfTexto(final String texto) {
        final BemSituacaoEnum[] values = BemSituacaoEnum.values();
        for (final BemSituacaoEnum value : values) {
            if (value.getTexto().equals(texto)) {
                return value;
            }
        }
        return null;
    }

    public final int getValor() {
        return valor;
    }

    public final String getTexto() {
        return texto;
    }

}
