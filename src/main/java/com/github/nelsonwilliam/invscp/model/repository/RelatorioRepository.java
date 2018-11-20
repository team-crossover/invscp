package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
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
            PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM bem WHERE id_departamento=? GROUP BY id_sala");
            s.setObject(1, dept.getId(), Types.INTEGER);

            final ResultSet r = s.executeQuery();
            List<Integer> idBens = new ArrayList<Integer>();

            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                idBens.add(id);

            }
            relatorio.setIdBens(idBens);
            relatorio.setIdDepartamento(dept.getId());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return relatorio;
    }

}
