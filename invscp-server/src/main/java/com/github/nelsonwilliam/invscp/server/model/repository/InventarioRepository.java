package com.github.nelsonwilliam.invscp.server.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.Inventario;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class InventarioRepository {

    public Inventario get() {
        final Connection connection = DatabaseConnection.getConnection();
        Inventario inv = new Inventario();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM bem");
            
            final ResultSet r = s.executeQuery();
            final List<Integer> idBens = new ArrayList<Integer>();

            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                idBens.add(id);
            }

            inv.setIdBens(idBens);
            inv.setMomentoGeracao(LocalDateTime.now());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return inv;
    }

}
