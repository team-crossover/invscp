package com.github.nelsonwilliam.invscp.model.dto;

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
    public void fromModel(final Sala newModel) {
        id = newModel.getId();
        nome = newModel.getNome();
        tipo = newModel.getTipo();
        if (newModel.getIdPredio() != null) {
            final PredioRepository predioRepo = new PredioRepository();
            predio = new PredioDTO();
            predio.fromModel(predioRepo.getById(newModel.getIdPredio()));
        }
        if (newModel.getIdDepartamento() != null) {
            final DepartamentoRepository deptRepo = new DepartamentoRepository();
            departamento = new DepartamentoDTO();
            departamento
                    .fromModel(deptRepo.getById(newModel.getIdDepartamento()));
        }
    }

    @Override
    public Sala toModel() {
        final Sala sala = new Sala();
        sala.setId(id);
        sala.setNome(nome);
        sala.setTipo(tipo);
        if (predio != null) {
            sala.setIdPredio(predio.getId());
        }
        if (departamento != null) {
            sala.setIdDepartamento(departamento.getId());
        }
        return sala;
    }

    /**
     * Obtém o valor atual de id.
     *
     * @return O valor atual de id.
     */
    public final Integer getId() {
        return id;
    }

    /**
     * Atualiza o valor atual de id.
     *
     * @param newId O novo valor para id.
     */
    public final void setId(final Integer newId) {
        id = newId;
    }

    /**
     * Obtém o valor atual de nome.
     *
     * @return O valor atual de nome.
     */
    public final String getNome() {
        return nome;
    }

    /**
     * Atualiza o valor atual de nome.
     *
     * @param newNome O novo valor para nome.
     */
    public final void setNome(final String newNome) {
        nome = newNome;
    }

    /**
     * Obtém o valor atual de tipo.
     *
     * @return O valor atual de tipo.
     */
    public final TipoSalaEnum getTipo() {
        return tipo;
    }

    /**
     * Atualiza o valor atual de tipo.
     *
     * @param newTipo O novo valor para tipo.
     */
    public final void setTipo(final TipoSalaEnum newTipo) {
        tipo = newTipo;
    }

    /**
     * Obtém o valor atual de predio.
     *
     * @return O valor atual de predio.
     */
    public final PredioDTO getPredio() {
        return predio;
    }

    /**
     * Atualiza o valor atual de predio.
     *
     * @param newPredio O novo valor para predio.
     */
    public final void setPredio(final PredioDTO newPredio) {
        predio = newPredio;
    }

    /**
     * Obtém o valor atual de departamento.
     *
     * @return O valor atual de departamento.
     */
    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    /**
     * Atualiza o valor atual de departamento.
     *
     * @param newDepartamento O novo valor para departamento.
     */
    public final void setDepartamento(final DepartamentoDTO newDepartamento) {
        departamento = newDepartamento;
    }

}
