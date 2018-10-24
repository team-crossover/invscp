package com.github.nelsonwilliam.invscp.model;

public class Usuario implements Model {

	private static final long serialVersionUID = 5924596504372549531L;

	private Integer id;

	// TODO Fazer o resto da classe...

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
