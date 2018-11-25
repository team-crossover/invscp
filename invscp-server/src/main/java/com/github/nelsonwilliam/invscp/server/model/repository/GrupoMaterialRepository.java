package com.github.nelsonwilliam.invscp.server.model.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.GrupoMaterial;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class GrupoMaterialRepository implements Repository<GrupoMaterial> {

    @Override
    public List<GrupoMaterial> getAll() {
    	final Connection connection = DatabaseConnection.getConnection();
        final List<GrupoMaterial> grupos = new ArrayList<GrupoMaterial>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,vida_util,depreciacao FROM grupo_material ORDER BY nome ASC");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final Integer vidaUtil = (Integer) r.getObject("vida_util");
                final BigDecimal depreciacao =
                        (BigDecimal) r.getObject("depreciacao");

                final GrupoMaterial grupo = new GrupoMaterial();
                grupo.setId(id);
                grupo.setNome(nome);
                grupo.setVidaUtil(vidaUtil);
                grupo.setDepreciacao(depreciacao);
                grupos.add(grupo);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public GrupoMaterial getById(final Integer id) {
    	final Connection connection = DatabaseConnection.getConnection();
    	GrupoMaterial grupo = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,vida_util,depreciacao FROM grupo_material WHERE id=?");
            s.setObject(1, id, Types.INTEGER);

            final ResultSet r = s.executeQuery();
            if (r.next()) {
            	final String nome = (String) r.getObject("nome");
                final Integer vidaUtil = (Integer) r.getObject("vida_util");
                final BigDecimal depreciacao =
                        (BigDecimal) r.getObject("depreciacao");

                grupo = new GrupoMaterial();
                grupo.setId(id);
                grupo.setNome(nome);
                grupo.setVidaUtil(vidaUtil);
                grupo.setDepreciacao(depreciacao);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return grupo;
    }

    @Override
    public boolean add(final GrupoMaterial item) {
    	final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO grupo_material(nome,vida_util,depreciacao) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getNome(), Types.VARCHAR);
                s.setObject(2, item.getVidaUtil(), Types.INTEGER);
                s.setObject(3, item.getDepreciacao(), Types.NUMERIC);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO grupo_material(id,nome,vida_util,depreciacao) VALUES (?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getNome(), Types.VARCHAR);
                s.setObject(3, item.getVidaUtil(), Types.INTEGER);
                s.setObject(4, item.getDepreciacao(), Types.NUMERIC);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<GrupoMaterial> items) {
    	boolean added = false;
        for (final GrupoMaterial item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final GrupoMaterial item) {
    	final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE grupo_material SET nome=?, vida_util=?, depreciacao=? WHERE id=?");
            s.setObject(1, item.getNome(), Types.VARCHAR);
            s.setObject(2, item.getVidaUtil(), Types.INTEGER);
            s.setObject(3, item.getDepreciacao(), Types.NUMERIC);
            s.setObject(4, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<GrupoMaterial> items) {
    	boolean updated = false;
        for (final GrupoMaterial item : items) {
            updated |= update(item);
        }
        return updated;    }

    @Override
    public boolean remove(final GrupoMaterial item) {
    	final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM grupo_material WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<GrupoMaterial> items) {
    	boolean removed = false;
        for (final GrupoMaterial item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
