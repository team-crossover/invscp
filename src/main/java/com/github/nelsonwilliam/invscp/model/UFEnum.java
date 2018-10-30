package com.github.nelsonwilliam.invscp.model;

public enum UFEnum {

    AL(0), AM(1), AP(2), BA(3), CE(4), DF(5), ES(6), GO(7), MA(8), MG(9), MS(
            10), MT(11), PA(12), PB(13), PE(14), PI(15), PR(16), RJ(17), RN(
                    18), RO(19), RR(20), RS(21), SC(22), SE(23), SP(24), TO(25);

    private final int valor;

    UFEnum(int valorOpcao) {
        valor = valorOpcao;
    }

    public int getValor() {
        return valor;
    }
}
