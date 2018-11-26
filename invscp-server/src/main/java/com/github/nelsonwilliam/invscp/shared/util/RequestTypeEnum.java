package com.github.nelsonwilliam.invscp.shared.util;

import java.io.Serializable;

public enum RequestTypeEnum implements Serializable {

    ADD_BAIXA,
    ADD_BEM,
    ADD_DEPARTAMENTO,
    ADD_EVENTO_MOVIMENTACAO,
    ADD_FUNCIONARIO,
    ADD_LOCALIZACAO,
    ADD_MOVIMENTACAO,
    ADD_ORDEM_SERVICO,
    ADD_PREDIO,
    ADD_SALA,
    DELETE_BAIXA,
    DELETE_BEM,
    DELETE_DEPARTAMENTO,
    DELETE_EVENTO_MOVIMENTACAO,
    DELETE_FUNCIONARIO,
    DELETE_LOCALIZACAO,
    DELETE_MOVIMENTACAO,
    DELETE_ORDEM_SERVICO,
    DELETE_PREDIO,
    DELETE_SALA,
    GERAR_GUIA_TRANSPORTE,
    GERAR_HISTORICO,
    GERAR_INVENTARIO,
    GERAR_NUM_GUIA_TRANSPORTE,
    GERAR_RELATORIO_DEPT,
    GET_BAIXA_BY_ID,
    GET_BAIXA_BY_ID_BEM,
    GET_BAIXAS,
    GET_BEM_BY_ID,
    GET_BENS,
    GET_DEPARTAMENTO_BY_ID,
    GET_DEPARTAMENTOS,
    GET_EVENTO_MOVIMENTACAO_BY_ID,
    GET_EVENTOS_MOVIMENTACAO_BY_MOVIMENTACAO,
    GET_FUNCIONARIO_BY_ID,
    GET_FUNCIONARIOS,
    GET_FUNCIONARIOS_BY_LOGIN,
    GET_FUNCIONARIOS_BY_USUARIO,
    GET_GRUPO_MATERIAL_BY_ID,
    GET_GRUPOS_MATERIAIS,
    GET_LOCALIZACAO_BY_ID,
    GET_LOCALIZACOES,
    GET_MOVIMENTACAO_BY_ID,
    GET_MOVIMENTACOES,
    GET_ORDEM_SERVICO_BY_ID,
    GET_ORDENS_SERVICO,
    GET_ORDENS_SERVICO_BY_BEM,
    GET_POSSIVEIS_CHEFES_PARA_DEPARTAMENTO,
    GET_POSSIVEIS_DEPARTAMENTOS_PARA_FUNCIONARIO,
    GET_POSSIVEIS_LOCALIZACOES_PARA_PREDIO,
    GET_PREDIO_BY_ID,
    GET_PREDIOS,
    GET_PREDIOS_BY_LOCALIZACAO,
    GET_SALA_BY_ID,
    GET_SALA_DEPOSITO,
    GET_SALAS,
    GET_SALAS_BY_PREDIO,
    POS_ALTERAR_DEPARTAMENTO,
    POS_CONCLUIR_ORDEM_SERVICO,
    POS_INSERIR_BAIXA,
    POS_INSERIR_EVENTO_MOVIMENTACAO,
    POS_INSERIR_MOVIMENTACAO,
    POS_INSERIR_ORDEM_SERVICO,
    UPDATE_BAIXA,
    UPDATE_BEM,
    UPDATE_DEPARTAMENTO,
    UPDATE_EVENTO_MOVIMENTACAO,
    UPDATE_FUNCIONARIO,
    UPDATE_LOCALIZACAO,
    UPDATE_MOVIMENTACAO,
    UPDATE_ORDEM_SERVICO,
    UPDATE_PREDIO,
    UPDATE_SALA,
    VALIDAR_ALTERAR_BAIXA,
    VALIDAR_ALTERAR_BEM,
    VALIDAR_ALTERAR_DEPARTAMENTO,
    VALIDAR_ALTERAR_EVENTO_MOVIMENTACAO,
    VALIDAR_ALTERAR_FUNCIONARIO,
    VALIDAR_ALTERAR_LOCALIZACAO,
    VALIDAR_ALTERAR_MOVIMENTACAO,
    VALIDAR_ALTERAR_ORDEM_SERVICO,
    VALIDAR_ALTERAR_PREDIO,
    VALIDAR_ALTERAR_SALA,
    VALIDAR_DELETE_BAIXA,
    VALIDAR_DELETE_BEM,
    VALIDAR_DELETE_DEPARTAMENTO,
    VALIDAR_DELETE_EVENTO_MOVIMENTACAO,
    VALIDAR_DELETE_FUNCIONARIO,
    VALIDAR_DELETE_LOCALIZACAO,
    VALIDAR_DELETE_MOVIMENTACAO,
    VALIDAR_DELETE_ORDEM_SERVICO,
    VALIDAR_DELETE_PREDIO,
    VALIDAR_DELETE_SALA,
    VALIDAR_INSERIR_BAIXA,
    VALIDAR_INSERIR_BEM,
    VALIDAR_INSERIR_DEPARTAMENTO,
    VALIDAR_INSERIR_EVENTO_MOVIMENTACAO,
    VALIDAR_INSERIR_FUNCIONARIO,
    VALIDAR_INSERIR_LOCALIZACAO,
    VALIDAR_INSERIR_MOVIMENTACAO,
    VALIDAR_INSERIR_ORDEM_SERVICO,
    VALIDAR_INSERIR_PREDIO,
    VALIDAR_INSERIR_SALA,
    GET_BENS_FILTRADOS,

}
