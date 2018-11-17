package com.github.nelsonwilliam.invscp.model.enums;

public enum MotivoBaixaEnum {

    INSERVIVEL(1, "Inservível"), QUEBRA_DESGASTE_AVARIA(2,
            "Quebra, desgaste ou avaria"), VENDA_ANTERIOR(3,
                    "Venda em exercícios Anteriores"), VENDA_DIRETA_LEILAO(4,
                            "Venda direta ou leilão"), EXTRAVIO(5,
                                    "Extravio"), FURTO_ROUBO(6,
                                            "Furto ou roubo");

    private final int valor;
    private final String texto;

    private MotivoBaixaEnum(int valor, String texto) {
        this.valor = valor;
        this.texto = texto;
    }

    public static String[] textos() {
        final MotivoBaixaEnum[] values = MotivoBaixaEnum.values();
        final String[] textos = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            textos[i] = values[i].getTexto();
        }
        return textos;
    }

    public static MotivoBaixaEnum valueOfTexto(final String texto) {
        final MotivoBaixaEnum[] values = MotivoBaixaEnum.values();
        for (final MotivoBaixaEnum value : values) {
            if (value.toString().equals(texto)) {
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
