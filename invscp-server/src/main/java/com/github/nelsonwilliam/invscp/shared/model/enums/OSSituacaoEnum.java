package com.github.nelsonwilliam.invscp.shared.model.enums;

public enum OSSituacaoEnum {

    CONCLUIDA(1, "Concluída"), PENDENTE(2, "Pendente");

    private final int valor;
    private final String texto;

    private OSSituacaoEnum(final int valor, final String texto) {
        this.valor = valor;
        this.texto = texto;
    }

    public static OSSituacaoEnum valueOfTexto(final String texto) {
        final OSSituacaoEnum[] values = OSSituacaoEnum.values();
        for (final OSSituacaoEnum value : values) {
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
