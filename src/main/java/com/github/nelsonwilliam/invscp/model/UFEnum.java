package com.github.nelsonwilliam.invscp.model;

public enum UFEnum {	
	
	RO(1),
	AC(2),
	AM(3),
	RR(4),
	PA(5),
	AP(6),
	TO(7),
	MA(8),
	PI(9),
	CE(10),
	RN(11),
	PB(12),
	PE(13),
	AL(14),
	SE(15),
	BA(16),
	MG(17),
	ES(18),
	RJ(19),
	SP(20),
	PR(21),
	SC(22),
	RS(23),
	MS(24),
	MT(25),
	GO(26),
	DF(27);

	private final int valor;

	UFEnum(int valorOpcao){
		valor = valorOpcao;
	}

	public int getValor() {
		return valor;
	}
}
