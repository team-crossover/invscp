package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;

public class SalaDTO implements DTO<Sala> {

    private Integer id = null;

    private String nome = null;

    private TipoSalaEnum tipo = null;

    private PredioDTO predio = null;

    private DepartamentoDTO departamento = null;

    @Override
    public void setValuesFromModel(Sala model) {
        this.id = model.getId();
        this.nome = model.getNome();
        this.tipo = model.getTipo();

        if (model.getIdDepartamento() != null) {
            final PredioRepository repo = new PredioRepository();
            final Predio predio = repo.getById(model.getIdDepartamento());

            this.predio = new PredioDTO();
            this.predio.setValuesFromModel(predio);
        }

        if (model.getIdDepartamento() != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(model.getIdDepartamento());

            this.departamento = new DepartamentoDTO();
            this.departamento.setValuesFromModel(dept);
        }

    }

    @Override
    public Sala toModel() {
        Sala sala = new Sala();
        sala.setId(id);
        sala.setNome(nome);
        sala.setTipo(tipo);
        if (departamento != null) {
            sala.setIdDepartamento(departamento.getId());
        }
        if (predio != null) {
            sala.setIdPredio(predio.getId());
        }

        return sala;
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        this.nome = nome;
    }

    public final TipoSalaEnum getTipo() {
        return tipo;
    }

    public final void setTipo(TipoSalaEnum tipo) {
        this.tipo = tipo;
    }

    public final PredioDTO getPredio() {
        return predio;
    }

    public final void setPredio(PredioDTO predio) {
        this.predio = predio;
    }

    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public final void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

}
