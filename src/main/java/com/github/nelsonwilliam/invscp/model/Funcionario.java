package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;

public class Funcionario implements Model {

	private static final long serialVersionUID = 5924596504372549531L;

	private Integer id = null;

	private String login = null;

	// TODO Armazenar apenas uma hash da senha, e não a senha inteira, para maior
	// segurança.

	private String senha = null;

	private String nome = "Funcionário";

	private String cpf = null;

	private String email = null;

	private Integer idDepartamento = null;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Departamento getDepartamento() {
		DepartamentoRepository deptRep = new DepartamentoRepository();
		return deptRep.getById(getIdDepartamento());
	}

	/**
	 * Verifica se este funcionário é chefe de departamento do seu departamento.
	 * Retorna falso caso seu departamento seja de patrimônio, nesse caso use
	 * isChefeDePatrimonio().
	 */
	public boolean isChefeDeDepartamento() {
		Departamento dept = getDepartamento();
		return !dept.getDePatrimonio()
				&& (dept.getIdChefe().equals(getId()) || dept.getIdChefeSubstituto().equals(getId()));
	}

	/**
	 * Verifica se este funcionário é chefe de patrimônio. Retorna falso caso seu
	 * departamento não seja de patrimônio, nesse caso use isChefeDeDepartamento().
	 */
	public boolean isChefeDePatrimonio() {
		Departamento dept = getDepartamento();
		return dept.getDePatrimonio()
				&& (dept.getIdChefe().equals(getId()) || dept.getIdChefeSubstituto().equals(getId()));
	}

}
