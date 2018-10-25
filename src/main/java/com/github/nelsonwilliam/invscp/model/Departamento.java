package com.github.nelsonwilliam.invscp.model;

public class Departamento implements Model {

	private static final long serialVersionUID = -4732191299693035638L;

	private Integer id = null;

	private String nome = "Departamento";

	private boolean dePatrimonio = false;

	private Integer idChefe = null;

	private Integer idChefeSubstituto = null;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdChefe() {
		return idChefe;
	}

	public void setIdChefe(Integer idChefe) {
		this.idChefe = idChefe;
	}

	public Integer getIdChefeSubstituto() {
		return idChefeSubstituto;
	}

	public void setIdChefeSubstituto(Integer idChefeSubstituto) {
		this.idChefeSubstituto = idChefeSubstituto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getDePatrimonio() {
		return dePatrimonio;
	}

	public void setDePatrimonio(Boolean dePatrimonio) {
		this.dePatrimonio = dePatrimonio;
	}
}
