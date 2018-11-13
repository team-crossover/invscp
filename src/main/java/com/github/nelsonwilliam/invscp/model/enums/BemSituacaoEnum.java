package com.github.nelsonwilliam.invscp.model.enums;

public enum BemSituacaoEnum {
    INCORPORADO(1, "Incorporado"), EM_CONSERTO(2,
            "Em conserto"), EM_MOVIMENTACAO(3,
                    "Em movimentação"), BAIXADO(4, "Baixado");

    private final int valor;
    private final String texto;

    private BemSituacaoEnum(final int valorOpcao, final String textoOpcao) {
        valor = valorOpcao;
        texto = textoOpcao;
    }

    public final int getValor() {
        return valor;
    }

    public final String getTexto() {
        return texto;
    }

}
