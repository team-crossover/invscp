package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Relatorio;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class RelatorioRepository {

    public Relatorio getByDepartamento(final Departamento dept) {
        final Connection connection = DatabaseConnection.getConnection();
        final Relatorio relatorio = new Relatorio();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM bem WHERE id_departamento=? ORDER BY id_sala");
            s.setObject(1, dept.getId(), Types.INTEGER);

            final ResultSet r = s.executeQuery();
            final List<Integer> idBens = new ArrayList<Integer>();

            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                idBens.add(id);

            }
            relatorio.setIdBens(idBens);
            relatorio.setIdDepartamento(dept.getId());
            relatorio.setMomentoGeracao(LocalDateTime.now());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return relatorio;
    }

}
