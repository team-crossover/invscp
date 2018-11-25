package com.github.nelsonwilliam.invscp.server.model.repository;

import java.time.LocalDateTime;

import com.github.nelsonwilliam.invscp.server.model.Funcionario;
import com.github.nelsonwilliam.invscp.server.model.GuiaTransporte;
import com.github.nelsonwilliam.invscp.server.model.Movimentacao;

public class GuiaTransporteRepository {

    public GuiaTransporte getByMovimentacao(Movimentacao mov,
            Funcionario usuario) {

        GuiaTransporte guia = new GuiaTransporte();
        guia.setMomentoGeracao(LocalDateTime.now());
        guia.setIdMovimentacao(mov.getId());
        guia.setIdUsuario(usuario.getId());
        return guia;
    }

}
