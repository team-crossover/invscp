package com.github.nelsonwilliam.invscp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.model.dto.BemDTO;

public class BemTest {

    /**
     * É necessário conectar com e inicializar o banco para poder executar os
     * testes. Este método é executado antes de todos os testes dessa classe.
     */
    @BeforeAll
    public static void prepararBanco() {
        // TODO Conectar-se a um banco exclusivo para os testes, para não
        // bagunçar os dados do banco de dados do usuário.
        InvSCP.connectDatabase();
        InvSCP.initializeDatabase();
    }

    /**
     * Inserir bem quando o usuário não está logado (é nulo) deve causar uma
     * exceção.
     */
    @Test
    public void inserirBemSemEstarLogado() {
        final BemDTO novoBem = new BemDTO();
        Assertions.assertThrows(IllegalInsertException.class, () -> {
            Bem.validarInserir(null, novoBem);
        });
    }

}
