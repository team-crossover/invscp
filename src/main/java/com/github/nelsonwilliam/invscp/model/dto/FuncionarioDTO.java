package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.enums.CargoEnum;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;

public class FuncionarioDTO implements DTO<Funcionario> {

    /**
     * Permite verificar o cargo do funcionário sem necessitar de outras
     * requisições. É ignorado ao transformar o DTO de volta em Model, já que o
     * Model de Funcionario não armazena o cargo.
     */
    private CargoEnum cargo;

    private Integer id = null;

    private String login = null;

    private String senha = null;

    private String nome = null;

    private String cpf = null;

    private String email = null;

    private DepartamentoDTO departamento = null;

    @Override
    public void setValuesFromModel(Funcionario model) {
        this.cargo = model.getCargo();
        this.id = model.getId();
        this.login = model.getLogin();
        this.senha = model.getSenha();
        this.nome = model.getNome();
        this.cpf = model.getCpf();
        this.email = model.getEmail();

        if (model.getIdDepartamento() != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(model.getIdDepartamento());

            this.departamento = new DepartamentoDTO();
            this.departamento.setValuesFromModel(dept);
        }

    }

    @Override
    public Funcionario toModel() {
        final Funcionario func = new Funcionario();
        func.setCpf(cpf);
        func.setEmail(email);
        func.setId(id);
        if (departamento != null) {
            func.setIdDepartamento(departamento.getId());
        }
        func.setLogin(login);
        func.setSenha(senha);
        func.setNome(nome);
        return func;
    }

    public final CargoEnum getCargo() {
        return cargo;
    }

    public final void setCargo(CargoEnum cargo) {
        this.cargo = cargo;
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getLogin() {
        return login;
    }

    public final void setLogin(String login) {
        this.login = login;
    }

    public final String getSenha() {
        return senha;
    }

    public final void setSenha(String senha) {
        this.senha = senha;
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        this.nome = nome;
    }

    public final String getCpf() {
        return cpf;
    }

    public final void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

}
