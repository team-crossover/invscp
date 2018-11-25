package com.github.nelsonwilliam.invscp.shared.model.enums;

import java.io.Serializable;

public enum TipoSalaEnum implements Serializable {

    DE_AULA(1, "De aula"),
    LABORATORIO(2, "Laboratório"),
    ATELIE(3, "Ateliê"),
    DEPOSITO(4, "Depósito");

    private final int valor;
    private final String texto;

    TipoSalaEnum(final int valorOpcao, final String textoOpcao) {
        valor = valorOpcao;
        texto = textoOpcao;
    }

    public static TipoSalaEnum valueOfTexto(final String texto) {
        final TipoSalaEnum[] values = TipoSalaEnum.values();
        for (final TipoSalaEnum value : values) {
            if (value.getTexto().equals(texto)) {
                return value;
            }
        }
        return null;
    }

    public int getValor() {
        return valor;
    }

    public String getTexto() {
        return texto;
    }

}
