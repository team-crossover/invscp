package com.github.nelsonwilliam.invscp.model;

import java.util.List;

import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;

public class Sala implements Model {

    private static final long serialVersionUID = 509047893405447267L;

    private Integer id = null;

    private String nome = null;

    private TipoSalaEnum tipo = null;;

    private Integer idPredio = null;

    private Integer idDepartamento = null;

    public Integer getIdPredio() {
        return idPredio;
    }

    public Predio getPredio() {
        final PredioRepository predioRepo = new PredioRepository();
        return predioRepo.getById(idPredio);
    }

    public void setIdPredio(final Integer idPredio) {
        this.idPredio = idPredio;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public Departamento getDepartamento() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        return deptRepo.getById(idDepartamento);
    }

    public void setIdDepartamento(final Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public TipoSalaEnum getTipo() {
        return tipo;
    }

    public String getTipoString() {
        return tipo.toString();
    }

    public void setTipo(final TipoSalaEnum tipo) {
        this.tipo = tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = TipoSalaEnum.valueOf(tipo);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public List<Predio> getPossiveisPredios() {
        final PredioRepository predioRepo = new PredioRepository();
        return predioRepo.getAll();
    }

    public List<Departamento> getPossiveisDepartamentos() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        return deptRepo.getAll();
    }
}
