package com.github.nelsonwilliam.invscp.shared.model.enums;

import java.io.Serializable;

public enum TipoEventoMovEnum implements Serializable {

	CRIACAO(1, "Criação"),
	ACEITE_ENTRADA(2, "Aceite de entrada"),
	NEGACAO_ENTRADA(3, "Negação de entrada"),
	ACEITE_SAIDA(4, "Aceite de saída"),
	NEGACAO_SAIDA(5, "Negação de saída"),
	FINALIZACAO(6, "Finalização"),
	CANCELAMENTO(7, "Cancelamento");

    private final int valor;
    private final String texto;

    TipoEventoMovEnum(final int valorOpcao, final String textoOpcao) {
        valor = valorOpcao;
        texto = textoOpcao;
    }

    public static TipoEventoMovEnum valueOfTexto(final String texto) {
        final TipoEventoMovEnum[] values = TipoEventoMovEnum.values();
        for (final TipoEventoMovEnum value : values) {
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
