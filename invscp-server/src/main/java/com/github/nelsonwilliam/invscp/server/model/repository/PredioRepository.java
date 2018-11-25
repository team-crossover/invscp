package com.github.nelsonwilliam.invscp.server.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.Predio;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class PredioRepository implements Repository<Predio> {

    @Override
    public List<Predio> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Predio> predios = new ArrayList<Predio>();
        try {
            final PreparedStatement s = connection
                    .prepareStatement("SELECT id,nome,id_localizacao FROM predio ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final Integer idLocalizacao = (Integer) r.getObject("id_localizacao");

                final Predio predio = new Predio();
                predio.setId(id);
                predio.setNome(nome);
                predio.setIdLocalizacao(idLocalizacao);
                predios.add(predio);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return predios;
    }
    
    public List<Predio> getByIdLocalizacao(final Integer idLocalizacao) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Predio> predios = new ArrayList<Predio>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,id_localizacao FROM predio WHERE id_localizacao=? ORDER BY id");
            s.setObject(1, idLocalizacao, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");

                final Predio predio = new Predio();
                predio.setId(id);
                predio.setNome(nome);
                predio.setIdLocalizacao(idLocalizacao);
                predios.add(predio);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return predios;
    }

    @Override
    public Predio getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Predio predio = null;
        try {
            final PreparedStatement s = connection
                    .prepareStatement("SELECT id,nome,id_localizacao FROM predio WHERE id=?");
            s.setObject(1, id, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final String nome = (String) r.getObject("nome");
                final Integer idLocalizacao = (Integer) r.getObject("id_localizacao");
                predio = new Predio();
                predio.setId(id);
                predio.setNome(nome);
                predio.setIdLocalizacao(idLocalizacao);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return predio;
    }

    @Override
    public boolean add(final Predio item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO predio(nome,id_localizacao) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getNome(), Types.VARCHAR);
                s.setObject(2, item.getIdLocalizacao(), Types.INTEGER);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO predio(id,nome,id_localizacao) VALUES (?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getNome(), Types.VARCHAR);
                s.setObject(3, item.getIdLocalizacao(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Predio> items) {
        boolean added = false;
        for (final Predio item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Predio item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection
                    .prepareStatement("UPDATE predio SET nome=?, id_localizacao=? WHERE id=?");
            s.setObject(1, item.getNome(), Types.VARCHAR);
            s.setObject(2, item.getIdLocalizacao(), Types.INTEGER);
            s.setObject(3, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Predio> items) {
        boolean updated = false;
        for (final Predio item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Predio item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM predio WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Predio> items) {
        boolean removed = false;
        for (final Predio item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
