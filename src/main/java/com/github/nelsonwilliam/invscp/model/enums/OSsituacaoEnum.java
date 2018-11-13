package com.github.nelsonwilliam.invscp.model.enums;

public enum OSsituacaoEnum {

    CONCLUIDA(1, "Concluída"), PENDENTE(2, "Pendente");

    private final int valor;
    private final String texto;

    private OSsituacaoEnum(int valor, String texto) {
        this.valor = valor;
        this.texto = texto;
    }

    public final int getValor() {
        return valor;
    }

    public final String getTexto() {
        return texto;
    }

}
