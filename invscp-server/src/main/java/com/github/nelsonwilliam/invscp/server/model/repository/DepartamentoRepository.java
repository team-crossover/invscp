package com.github.nelsonwilliam.invscp.server.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class DepartamentoRepository implements Repository<Departamento> {

    @Override
    public List<Departamento> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Departamento> depts = new ArrayList<Departamento>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,de_patrimonio,id_chefe,id_chefe_substituto FROM departamento ORDER BY id ASC");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final Boolean dePatrimonio = r.getBoolean("de_patrimonio");
                final Integer idChefe = (Integer) r.getObject("id_chefe");
                final Integer idChefeSubstituto = (Integer) r.getObject("id_chefe_substituto");

                final Departamento dept = new Departamento();
                dept.setId(id);
                dept.setNome(nome);
                dept.setDePatrimonio(dePatrimonio);
                dept.setIdChefe(idChefe);
                dept.setIdChefeSubstituto(idChefeSubstituto);
                depts.add(dept);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return depts;
    }

    public Departamento getDePatrimonio() {
        final Connection connection = DatabaseConnection.getConnection();
        final Departamento dept = new Departamento();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,de_patrimonio,id_chefe,id_chefe_substituto FROM departamento WHERE de_patrimonio=true LIMIT 1");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final Boolean dePatrimonio = r.getBoolean("de_patrimonio");
                final Integer idChefe = (Integer) r.getObject("id_chefe");
                final Integer idChefeSubstituto = (Integer) r.getObject("id_chefe_substituto");
                dept.setId(id);
                dept.setNome(nome);
                dept.setDePatrimonio(dePatrimonio);
                dept.setIdChefe(idChefe);
                dept.setIdChefeSubstituto(idChefeSubstituto);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return dept;
    }

    @Override
    public Departamento getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Departamento dept = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,de_patrimonio,id_chefe,id_chefe_substituto FROM departamento WHERE id=?");
            s.setObject(1, id, Types.INTEGER);

            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final String nome = (String) r.getObject("nome");
                final Boolean dePatrimonio = r.getBoolean("de_patrimonio");
                final Integer idChefe = (Integer) r.getObject("id_chefe");
                final Integer idChefeSubstituto = (Integer) r.getObject("id_chefe_substituto");

                dept = new Departamento();
                dept.setId(id);
                dept.setNome(nome);
                dept.setDePatrimonio(dePatrimonio);
                dept.setIdChefe(idChefe);
                dept.setIdChefeSubstituto(idChefeSubstituto);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return dept;
    }

    /**
     * Obtém o departamento cujo o chefe (ou o chefe substituto) tenha determinado ID de
     * funcionário. Retora nulo caso nenhum departamento tenha chefe com tal ID.
     */
    public Departamento getByChefeOrSubstitutoId(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Departamento dept = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id FROM departamento WHERE id_chefe=? OR id_chefe_substituto=? LIMIT 1");
            s.setObject(1, id, Types.INTEGER);
            s.setObject(2, id, Types.INTEGER);

            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final Integer idDept = (Integer) r.getObject("id");
                dept = getById(idDept);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return dept;
    }

    @Override
    public boolean add(final Departamento item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO departamento(nome,de_patrimonio,id_chefe,id_chefe_substituto) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getNome(), Types.VARCHAR);
                s.setObject(2, item.getDePatrimonio(), Types.BOOLEAN);
                s.setObject(3, item.getIdChefe(), Types.INTEGER);
                s.setObject(4, item.getIdChefeSubstituto(), Types.INTEGER);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO departamento(id,nome,de_patrimonio,id_chefe,id_chefe_substituto) VALUES (?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getNome(), Types.VARCHAR);
                s.setObject(3, item.getDePatrimonio(), Types.BOOLEAN);
                s.setObject(4, item.getIdChefe(), Types.INTEGER);
                s.setObject(5, item.getIdChefeSubstituto(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Departamento> items) {
        boolean added = false;
        for (final Departamento item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Departamento item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE departamento SET nome=?, de_patrimonio=?, id_chefe=?, id_chefe_substituto=? WHERE id=?");
            s.setObject(1, item.getNome(), Types.VARCHAR);
            s.setObject(2, item.getDePatrimonio(), Types.BOOLEAN);
            s.setObject(3, item.getIdChefe(), Types.INTEGER);
            s.setObject(4, item.getIdChefeSubstituto(), Types.INTEGER);
            s.setObject(5, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Departamento> items) {
        boolean updated = false;
        for (final Departamento item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Departamento item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM departamento WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Departamento> items) {
        boolean removed = false;
        for (final Departamento item : items) {
            removed |= remove(item);
        }
        return removed;
    }
}
