package com.github.nelsonwilliam.invscp.model.dto;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.enums.CargoEnum;

public class FuncionarioDTO implements DTO<Funcionario> {

    /**
     * Permite verificar o cargo do funcionário sem necessitar de outras
     * requisições. É ignorado ao transformar o DTO de volta em Model, já que o
     * Model de Funcionario não armazena o cargo.
     */
    private CargoEnum cargo;

}
