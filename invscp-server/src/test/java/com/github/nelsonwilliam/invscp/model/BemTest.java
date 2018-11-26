package com.github.nelsonwilliam.invscp.model;

import java.io.IOException;
import java.security.KeyException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.nelsonwilliam.invscp.server.InvSCPServer;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.util.ServerSettings;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;

public class BemTest {

    /**
     * É necessário conectar com e inicializar o banco para poder executar os
     * testes. Este método é executado antes de todos os testes dessa classe.
     * 
     * @throws IOException
     * @throws KeyException
     */
    @BeforeAll
    public static void prepararBanco() throws KeyException, IOException {
        ServerSettings.readSettings();
        InvSCPServer.connectDatabase();
        InvSCPServer.initializeDatabase();
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
