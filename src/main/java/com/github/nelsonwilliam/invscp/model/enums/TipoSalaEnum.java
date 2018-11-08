package com.github.nelsonwilliam.invscp.model.enums;

public enum TipoSalaEnum {

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

    public static String[] textos() {
        final TipoSalaEnum[] values = TipoSalaEnum.values();
        final String[] textos = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            textos[i] = values[i].getTexto();
        }
        return textos;
    }

    public static TipoSalaEnum valueOfTexto(final String texto) {
        final TipoSalaEnum[] values = TipoSalaEnum.values();
        for (final TipoSalaEnum value : values) {
            if (value.toString().equals(texto)) {
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
