package com.github.nelsonwilliam.invscp.shared.model.enums;

import java.io.Serializable;

public enum EtapaMovEnum implements Serializable {

    INICIADA(0, "Iniciada"),
	AGUARDANDO_AC_SAIDA(1, "Aguardando aceite de saída"),
	AGUARDANDO_AC_ENTRADA(2, "Aguardando aceite de entrada"),
	EM_MOVIMENTACAO(3, "Em movimentação"),
	FINALIZADA(4, "Finalizada"),
	CANCELADA(5, "Cancelada");

	private int valor;

	private String texto;

	EtapaMovEnum(final int valorOpcao, final String textoOpcao) {
		valor = valorOpcao;
		texto = textoOpcao;
	}

	public static EtapaMovEnum valueOfTexto(final String texto) {
        final EtapaMovEnum[] values = EtapaMovEnum.values();
        for (final EtapaMovEnum value : values) {
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
