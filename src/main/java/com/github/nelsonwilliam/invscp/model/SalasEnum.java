package com.github.nelsonwilliam.invscp.model;

public enum SalasEnum {
	
	DE_AULA(1), LABORATORIO(2), ATELIE(3);
	
	private final int valor;
	SalasEnum(int valorOpcao){
		valor = valorOpcao;
	}
	
	public int getValor(){
		return valor;
	}

}
