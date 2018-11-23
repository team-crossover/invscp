package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Movimentacao;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class MovimentacaoRepository implements Repository<Movimentacao> {

    @Override
    public List<Movimentacao> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Movimentacao> movs = new ArrayList<Movimentacao>();
        try {
            final PreparedStatement s = connection
                    .prepareStatement("SELECT id,etapa,id_bem,id_sala_origem,"
                            + "id_sala_destino,num_guia_transporte"
                            + " FROM movimentacao ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String etapa = (String) r.getObject("etapa");
                final Integer idBem = (Integer) r.getObject("id_bem");
                final Integer idSalaOrigem = (Integer) r
                        .getObject("id_sala_origem");
                final Integer idSalaDestino = (Integer) r
                        .getObject("id_sala_destino");
                final String numGuiaTransporte = (String) r.
                        getObject("num_guia_transporte");

                final Movimentacao mov = new Movimentacao();
                mov.setId(id);
                mov.setEtapa(EtapaMovEnum.valueOf(etapa));
                mov.setIdBem(idBem);
                mov.setIdSalaOrigem(idSalaOrigem);
                mov.setIdSalaDestino(idSalaDestino);
                mov.setNumGuiaTransporte(numGuiaTransporte);
                movs.add(mov);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return movs;
    }

    @Override
    public Movimentacao getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Movimentacao mov = null;
        try {
            final PreparedStatement s = connection
                    .prepareStatement("SELECT etapa,id_bem,id_sala_origem,"
                            + "id_sala_destino FROM movimentacao WHERE id=?");
            s.setObject(1, id, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final String etapa = (String) r.getObject("etapa");
                final Integer idBem = (Integer) r.getObject("id_bem");
                final Integer idSalaOrigem = (Integer) r
                        .getObject("id_sala_origem");
                final Integer idSalaDestino = (Integer) r
                        .getObject("id_sala_destino");
                final String numGuiaTransporte = (String) r.
                        getObject("num_guia_transporte");

                mov = new Movimentacao();
                mov.setEtapa(EtapaMovEnum.valueOf(etapa));
                mov.setIdBem(idBem);
                mov.setIdSalaOrigem(idSalaOrigem);
                mov.setIdSalaDestino(idSalaDestino);
                mov.setNumGuiaTransporte(numGuiaTransporte);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return mov;
    }

    @Override
    public boolean add(final Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO movimentacao(etapa,id_bem,id_sala_origem,"
                                + "id_sala_destino,num_guia_transporte)"
                                + " VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getEtapa(), Types.VARCHAR);
                s.setObject(2, item.getIdBem(), Types.INTEGER);
                s.setObject(3, item.getIdSalaOrigem(), Types.INTEGER);
                s.setObject(4, item.getIdSalaDestino(), Types.INTEGER);
                s.setObject(5, item.getNumGuiaTransporte(), Types.VARCHAR);
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
                s.setObject(6, item.getNumGuiaTransporte(), Types.VARCHAR);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Movimentacao> items) {
        boolean added = false;
        for (final Movimentacao item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE movimentacao SET etapa=?, id_bem=?,"
                            + "id_sala_origem=?, id_sala_destino=?"
                            + "num_guia_transporte WHERE id=?");
            s.setObject(1, item.getEtapa(), Types.VARCHAR);
            s.setObject(2, item.getIdBem(), Types.INTEGER);
            s.setObject(3, item.getIdSalaOrigem(), Types.INTEGER);
            s.setObject(4, item.getIdSalaDestino(), Types.INTEGER);
            s.setObject(5, item.getNumGuiaTransporte(), Types.VARCHAR);
            s.setObject(6, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Movimentacao> items) {
        boolean updated = false;
        for (final Movimentacao item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Movimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection
                    .prepareStatement("DELETE FROM movimentacao WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Movimentacao> items) {
        boolean removed = false;
        for (final Movimentacao item : items) {
            removed |= remove(item);
        }
        return removed;
    }

    public boolean existsBemInMov(Integer idBem) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM movimentacao WHERE id_bem=? AND etapa=? OR etapa=? OR etapa=?");
            s.setObject(1, idBem, Types.INTEGER);
            s.setObject(2, EtapaMovEnum.AGUARDANDO_AC_ENTRADA, Types.VARCHAR);
            s.setObject(3, EtapaMovEnum.AGUARDANDO_AC_SAIDA, Types.VARCHAR);
            s.setObject(4, EtapaMovEnum.EM_MOVIMENTACAO, Types.VARCHAR);
            final ResultSet r = s.executeQuery();
            if (!r.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
