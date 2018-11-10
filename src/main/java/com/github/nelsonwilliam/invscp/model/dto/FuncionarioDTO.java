package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.enums.CargoEnum;

public class FuncionarioDTO implements DTO {

    private Integer id = null;

    private String login = null;

    private String senha = null;

    private String nome = null;

    private String cpf = null;

    private String email = null;

    private DepartamentoDTO departamento = null;

    /**
     * Permite verificar o cargo do funcionário sem necessitar de outras
     * requisições. É ignorado ao transformar o DTO de volta em Model, já que o
     * Model de Funcionario não armazena o cargo.
     */
    private CargoEnum cargo = null;

    public final Integer getId() {
        return id;
    }

    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getLogin() {
        return login;
    }

    public final void setLogin(final String login) {
        this.login = login;
    }

    public final String getSenha() {
        return senha;
    }

    public final void setSenha(final String senha) {
        this.senha = senha;
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(final String nome) {
        this.nome = nome;
    }

    public final String getCpf() {
        return cpf;
    }

    public final void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(final DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public final CargoEnum getCargo() {
        return cargo;
    }

    public final void setCargo(final CargoEnum cargo) {
        this.cargo = cargo;
    }

}
