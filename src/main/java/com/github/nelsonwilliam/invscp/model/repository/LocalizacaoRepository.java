package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class LocalizacaoRepository implements Repository<Localizacao> {

    @Override
    public List<Localizacao> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Localizacao> locals = new ArrayList<Localizacao>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,endereco,cep,cidade,uf FROM localizacao ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String nome = (String) r.getObject("nome");
                final String endereco = (String) r.getObject("endereco");
                final String cep = (String) r.getObject("cep");
                final String cidade = (String) r.getObject("cidade");
                final String uf = (String) r.getObject("uf");

                final Localizacao loc = new Localizacao();
                loc.setId(id);
                loc.setNome(nome);
                loc.setEndereco(endereco);
                loc.setCep(cep);
                loc.setCidade(cidade);
                loc.setUfString(uf);
                locals.add(loc);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return locals;
    }

    @Override
    public Localizacao getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Localizacao local = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,nome,endereco,cep,cidade,uf FROM localizacao WHERE id=?");
            s.setObject(1, id, Types.INTEGER);
            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final String nome = (String) r.getObject("nome");
                final String endereco = (String) r.getObject("endereco");
                final String cep = (String) r.getObject("cep");
                final String cidade = (String) r.getObject("cidade");
                final String uf = (String) r.getObject("uf");

                local = new Localizacao();
                local.setId(id);
                local.setNome(nome);
                local.setEndereco(endereco);
                local.setCep(cep);
                local.setCidade(cidade);
                local.setUfString(uf);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return local;
    }

    @Override
    public boolean add(final Localizacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO localizacao(nome,endereco,cep,cidade,uf) VALUES (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getNome(), Types.VARCHAR);
                s.setObject(2, item.getEndereco(), Types.VARCHAR);
                s.setObject(3, item.getCep(), Types.VARCHAR);
                s.setObject(4, item.getCidade(), Types.VARCHAR);
                s.setObject(5, item.getUfString(), Types.VARCHAR);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO localizacao(id,nome,endereco,cep,cidade,uf) VALUES (?,?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getNome(), Types.VARCHAR);
                s.setObject(3, item.getEndereco(), Types.VARCHAR);
                s.setObject(4, item.getCep(), Types.VARCHAR);
                s.setObject(5, item.getCidade(), Types.VARCHAR);
                s.setObject(6, item.getUfString(), Types.VARCHAR);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Localizacao> items) {
        boolean added = false;
        for (final Localizacao item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Localizacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE localizacao SET nome=?, endereco=?, cep=?, cidade=?, uf=? WHERE id=?");
            s.setObject(1, item.getNome(), Types.VARCHAR);
            s.setObject(2, item.getEndereco(), Types.VARCHAR);
            s.setObject(3, item.getCep(), Types.VARCHAR);
            s.setObject(4, item.getCidade(), Types.VARCHAR);
            s.setObject(5, item.getUfString(), Types.VARCHAR);
            s.setObject(6, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Localizacao> items) {
        boolean updated = false;
        for (final Localizacao item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Localizacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection
                    .prepareStatement("DELETE FROM localizacao WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Localizacao> items) {
        boolean removed = false;
        for (final Localizacao item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
