package com.github.nelsonwilliam.invscp.server.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.model.Historico;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class HistoricoRepository {

    public Historico getByBem(Bem bem) {
        final Connection connection = DatabaseConnection.getConnection();
        Historico his = new Historico();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM movimentacao WHERE id_bem=?");
            s.setObject(1, bem.getId(), Types.INTEGER);
            final ResultSet r = s.executeQuery();
            List<Integer> idMovimentacoes = new ArrayList<Integer>();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                idMovimentacoes.add(id);
            }

            final PreparedStatement s1 = connection.prepareStatement(
                    "SELECT id FROM ordem_servico WHERE id_bem=?");
            s1.setObject(1, bem.getId(), Types.INTEGER);
            final ResultSet r1 = s1.executeQuery();
            List<Integer> idOrdens = new ArrayList<Integer>();
            while (r1.next()) {
                final Integer id = (Integer) r1.getObject("id");
                idOrdens.add(id);
            }

            final PreparedStatement s2 = connection.prepareStatement(
                    "SELECT id FROM baixa WHERE id_bem=?");
            s2.setObject(1, bem.getId(), Types.INTEGER);
            final ResultSet r2 = s2.executeQuery();
            Integer idBaixa = null;
            if (r2.next()) {
                idBaixa = (Integer) r2.getObject("id");
            }

            his.setMomentoGeracao(LocalDateTime.now());
            his.setIdBem(bem.getId());
            his.setIdMovimentacoes(idMovimentacoes);
            his.setIdOrdens(idOrdens);
            his.setIdBaixa(idBaixa);
            
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return his;
    }

}
