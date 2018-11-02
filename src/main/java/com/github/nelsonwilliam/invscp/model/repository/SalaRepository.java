package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class SalaRepository implements Repository<Sala> {

    @Override
    public List<Sala> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Sala> salas = new ArrayList<Sala>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,tipo,id_predio,id_departamento FROM sala ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final String tipo = (String) r.getObject("tipo");
                final Integer idPredio = (Integer) r.getObject("id_predio");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                final Sala sala = new Sala();
                sala.setId(id);
                sala.setNome(nome);
                sala.setTipo(tipo);
                sala.setIdPredio(idPredio);
                sala.setIdDepartamento(idDepartamento);
                salas.add(sala);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return salas;
    }

    public List<Sala> getByPredio(final Predio predio) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Sala> salas = new ArrayList<Sala>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,tipo,id_predio,id_departamento FROM sala WHERE id_predio=? ORDER BY id");
            s.setObject(1, predio.getId(), Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final String tipo = (String) r.getObject("tipo");
                final Integer idPredio = (Integer) r.getObject("id_predio");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                final Sala sala = new Sala();
                sala.setId(id);
                sala.setNome(nome);
                sala.setTipo(tipo);
                sala.setIdPredio(idPredio);
                sala.setIdDepartamento(idDepartamento);
                salas.add(sala);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return salas;
    }

    public List<Sala> getByDepartamento(final Departamento dept) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Sala> salas = new ArrayList<Sala>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,tipo,id_predio,id_departamento FROM sala WHERE id_departamento=? ORDER BY id");
            s.setObject(1, dept.getId(), Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final String tipo = (String) r.getObject("tipo");
                final Integer idPredio = (Integer) r.getObject("id_predio");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                final Sala sala = new Sala();
                sala.setId(id);
                sala.setNome(nome);
                sala.setTipo(tipo);
                sala.setIdPredio(idPredio);
                sala.setIdDepartamento(idDepartamento);
                salas.add(sala);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return salas;
    }

    public Sala getDeDeposito() {
        final Connection connection = DatabaseConnection.getConnection();
        Sala sala = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,tipo,id_predio,id_departamento FROM sala WHERE tipo='DEPOSITO'");
            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final String tipo = (String) r.getObject("tipo");
                final Integer idPredio = (Integer) r.getObject("id_predio");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                sala = new Sala();
                sala.setId(id);
                sala.setNome(nome);
                sala.setTipo(tipo);
                sala.setIdPredio(idPredio);
                sala.setIdDepartamento(idDepartamento);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return sala;
    }

    @Override
    public Sala getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Sala sala = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,tipo,id_predio,id_departamento FROM sala WHERE id=?");
            s.setObject(1, id, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final String nome = (String) r.getObject("nome");
                final String tipo = (String) r.getObject("tipo");
                final Integer idPredio = (Integer) r.getObject("id_predio");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                sala = new Sala();
                sala.setId(id);
                sala.setNome(nome);
                sala.setTipo(tipo);
                sala.setIdPredio(idPredio);
                sala.setIdDepartamento(idDepartamento);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return sala;
    }

    @Override
    public boolean add(final Sala item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO sala(nome,tipo,id_predio,id_departamento) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getNome(), Types.VARCHAR);
                s.setObject(2, item.getTipoString(), Types.VARCHAR);
                s.setObject(3, item.getIdPredio(), Types.INTEGER);
                s.setObject(4, item.getIdDepartamento(), Types.INTEGER);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO sala(id,nome,tipo,id_predio,id_departamento) VALUES (?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getNome(), Types.VARCHAR);
                s.setObject(3, item.getTipoString(), Types.VARCHAR);
                s.setObject(4, item.getIdPredio(), Types.INTEGER);
                s.setObject(5, item.getIdDepartamento(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Sala> items) {
        boolean added = false;
        for (final Sala item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Sala item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE sala SET nome=?, tipo=?, id_predio=?, id_departamento WHERE id=?");
            s.setObject(1, item.getNome(), Types.VARCHAR);
            s.setObject(2, item.getTipoString(), Types.VARCHAR);
            s.setObject(3, item.getIdPredio(), Types.INTEGER);
            s.setObject(4, item.getIdDepartamento(), Types.INTEGER);
            s.setObject(5, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Sala> items) {
        boolean updated = false;
        for (final Sala item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Sala item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM sala WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Sala> items) {
        boolean removed = false;
        for (final Sala item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
