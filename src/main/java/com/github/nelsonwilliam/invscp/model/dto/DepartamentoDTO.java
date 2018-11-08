package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;

public class DepartamentoDTO implements DTO<Departamento> {

    private Integer id = null;

    private String nome = null;

    private Boolean dePatrimonio = null;

    private FuncionarioDTO chefe = null;

    private FuncionarioDTO chefeSubstituto = null;

    @Override
    public void setValuesFromModel(final Departamento newModel) {
        id = newModel.getId();
        nome = newModel.getNome();
        dePatrimonio = newModel.getDePatrimonio();

        if (newModel.getIdChefe() != null) {
            final FuncionarioRepository funcRepo = new FuncionarioRepository();
            final Funcionario func = funcRepo.getById(newModel.getIdChefe());
            func.setIdDepartamento(null);

            chefe = new FuncionarioDTO();
            chefe.setValuesFromModel(func);
        }
        if (newModel.getIdChefeSubstituto() != null) {
            final FuncionarioRepository funcRepo = new FuncionarioRepository();
            final Funcionario func = funcRepo
                    .getById(newModel.getIdChefeSubstituto());
            func.setIdDepartamento(null);

            chefeSubstituto = new FuncionarioDTO();
            chefeSubstituto.setValuesFromModel(func);
        }
    }

    @Override
    public Departamento toModel() {
        final Departamento dept = new Departamento();
        dept.setId(id);
        dept.setNome(nome);
        dept.setDePatrimonio(dePatrimonio);
        if (chefe != null) {
            dept.setIdChefe(chefe.getId());
        }
        if (chefeSubstituto != null) {
            dept.setIdChefeSubstituto(chefeSubstituto.getId());
        }
        return dept;
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
     * Obtém o valor atual de dePatrimonio.
     *
     * @return O valor atual de dePatrimonio.
     */
    public final Boolean getDePatrimonio() {
        return dePatrimonio;
    }

    /**
     * Atualiza o valor atual de dePatrimonio.
     *
     * @param newDePatrimonio O novo valor para dePatrimonio.
     */
    public final void setDePatrimonio(final Boolean newDePatrimonio) {
        dePatrimonio = newDePatrimonio;
    }

    /**
     * Obtém o valor atual de chefe.
     *
     * @return O valor atual de chefe.
     */
    public final FuncionarioDTO getChefe() {
        return chefe;
    }

    /**
     * Atualiza o valor atual de chefe.
     *
     * @param newChefe O novo valor para chefe.
     */
    public final void setChefe(final FuncionarioDTO newChefe) {
        chefe = newChefe;
    }

    /**
     * Obtém o valor atual de chefeSubstituto.
     *
     * @return O valor atual de chefeSubstituto.
     */
    public final FuncionarioDTO getChefeSubstituto() {
        return chefeSubstituto;
    }

    /**
     * Atualiza o valor atual de chefeSubstituto.
     *
     * @param newChefeSubstituto O novo valor para chefeSubstituto.
     */
    public final void setChefeSubstituto(
            final FuncionarioDTO newChefeSubstituto) {
        chefeSubstituto = newChefeSubstituto;
    }

}
