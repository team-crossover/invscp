package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Movimentacao;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class MovimentacaoRepository implements Repository<Movimentacao> {

    @Override
    public List<Movimentacao> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Movimentacao> movs = new ArrayList<Movimentacao>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,etapa,id_bem,id_sala_origem,"
                    + "id_sala_destino FROM movimentacao ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String etapa = (String) r.getObject("etapa");
                final Integer idBem = (Integer) r.getObject("id_bem");
                final Integer idSalaOrigem = (Integer) r.getObject("id_sala_origem");
                final Integer idSalaDestino = (Integer) r.getObject("id_sala_destino");

                final Movimentacao mov = new Movimentacao();
                mov.setId(id);
                mov.setEtapa(etapa);
                mov.setIdBem(idBem);
                mov.setIdSalaOrigem(idSalaOrigem);
                mov.setIdSalaDestino(idSalaDestino);
                movs.add(mov);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return movs;
    }

    @Override
    public Movimentacao getById(Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Movimentacao mov = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT etapa,id_bem,id_sala_origem,"
                    + "id_sala_destino FROM movimentacao WHERE id=?");
            s.setObject(1, id, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final String etapa = (String) r.getObject("etapa");
                final Integer idBem = (Integer) r.getObject("id_bem");
                final Integer idSalaOrigem = (Integer) r.getObject("id_sala_origem");
                final Integer idSalaDestino = (Integer) r.getObject("id_sala_destino");

                mov = new Movimentacao();
                mov.setEtapa(etapa);
                mov.setIdBem(idBem);
                mov.setIdSalaOrigem(idSalaOrigem);
                mov.setIdSalaDestino(idSalaDestino);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return mov;
    }

    @Override
    public boolean add(Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO movimentacao(etapa,id_bem,id_sala_origem,"
                        + "id_sala_destino) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getEtapa(), Types.VARCHAR);
                s.setObject(2, item.getIdBem(), Types.INTEGER);
                s.setObject(3, item.getIdSalaOrigem(), Types.INTEGER);
                s.setObject(4, item.getIdSalaDestino(), Types.INTEGER);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO movimentacao(id,etapa,id_bem,"
                        + "id_sala_origem,id_sala_destino) VALUES (?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getEtapa(), Types.VARCHAR);
                s.setObject(3, item.getIdBem(), Types.INTEGER);
                s.setObject(4, item.getIdSalaOrigem(), Types.INTEGER);
                s.setObject(5, item.getIdSalaDestino(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Iterable<Movimentacao> items) {
        boolean added = false;
        for (final Movimentacao item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE movimentacao SET etapa=?, id_bem=?,"
                    + "id_sala_origem=?, id_sala_destino=? WHERE id=?");
            s.setObject(1, item.getEtapa(), Types.VARCHAR);
            s.setObject(2, item.getIdBem(), Types.INTEGER);
            s.setObject(3, item.getIdSalaOrigem(), Types.INTEGER);
            s.setObject(4, item.getIdSalaDestino(), Types.INTEGER);
            s.setObject(5, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Iterable<Movimentacao> items) {
        boolean updated = false;
        for (final Movimentacao item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM movimentacao WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Iterable<Movimentacao> items) {
        boolean removed = false;
        for (final Movimentacao item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
