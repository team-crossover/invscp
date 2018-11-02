package com.github.nelsonwilliam.invscp.model;

import java.util.List;

import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;

public class Funcionario implements Model {

    private static final long serialVersionUID = 5924596504372549531L;

    private Integer id = null;

    private String login = null;

    // TODO Armazenar apenas uma hash da senha, e não a senha inteira, para maior
    // segurança.

    private String senha = null;

    private String nome = null;

    private String cpf = null;

    private String email = null;

    private Integer idDepartamento = null;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(final Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Departamento getDepartamento() {
        if (getIdDepartamento() == null) {
            return null;
        }
        final DepartamentoRepository deptRep = new DepartamentoRepository();
        return deptRep.getById(getIdDepartamento());
    }

    public List<Departamento> getOutrosDepartamentos() {
        final DepartamentoRepository deptRep = new DepartamentoRepository();
        final List<Departamento> departamentos = deptRep.getAll();
        if (getDepartamento() != null) {
            for (final Departamento dept : departamentos) {
                if (dept.getId().equals(getDepartamento().getId())) {
                    departamentos.remove(dept);
                    break;
                }
            }
        }
        return departamentos;
    }

    public boolean isChefe() {
        final Departamento dept = getDepartamento();
        return dept != null
                && (dept.getIdChefe().equals(getId()) || (dept.getIdChefeSubstituto() != null
                        && dept.getIdChefeSubstituto().equals(getId())));
    }

    public boolean isChefeDeDepartamento() {
        final Departamento dept = getDepartamento();
        return dept != null && !dept.getDePatrimonio()
                && (dept.getIdChefe().equals(getId()) || (dept.getIdChefeSubstituto() != null
                        && dept.getIdChefeSubstituto().equals(getId())));
    }

    public boolean isChefeDeDepartamentoPrincipal() {
        final Departamento dept = getDepartamento();
        return dept != null && !dept.getDePatrimonio() && dept.getIdChefe().equals(getId());
    }

    public boolean isChefeDeDepartamentoSubstituto() {
        final Departamento dept = getDepartamento();
        return dept != null && !dept.getDePatrimonio() && (dept.getIdChefeSubstituto() != null
                && dept.getIdChefeSubstituto().equals(getId()));
    }

    public boolean isChefeDePatrimonio() {
        final Departamento dept = getDepartamento();
        return dept != null && dept.getDePatrimonio()
                && (dept.getIdChefe().equals(getId()) || (dept.getIdChefeSubstituto() != null
                        && dept.getIdChefeSubstituto().equals(getId())));
    }

    public boolean isChefeDePatrimonioPrincipal() {
        final Departamento dept = getDepartamento();
        return dept != null && dept.getDePatrimonio() && dept.getIdChefe().equals(getId());
    }

    public boolean isChefeDePatrimonioSubstituto() {
        final Departamento dept = getDepartamento();
        return dept != null && dept.getDePatrimonio() && (dept.getIdChefeSubstituto() != null
                && dept.getIdChefeSubstituto().equals(getId()));
    }

    public String getCargo() {
        if (id == null) {
            return "Interessado";
        } else if (isChefeDePatrimonioPrincipal()) {
            return "Chefe de patrimônio";
        } else if (isChefeDePatrimonioSubstituto()) {
            return "Chefe de patrimônio (subst.)";
        } else if (isChefeDeDepartamentoPrincipal()) {
            return "Chefe de departamento";
        } else if (isChefeDeDepartamentoSubstituto()) {
            return "Chefe de departamento (subst.)";
        } else {
            return "Funcionário";
        }
    }

}
