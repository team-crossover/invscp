package com.github.nelsonwilliam.invscp.server.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.model.Funcionario;
import com.github.nelsonwilliam.invscp.server.util.DatabaseConnection;

public class FuncionarioRepository implements Repository<Funcionario> {

    @Override
    public List<Funcionario> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Funcionario> funcs = new ArrayList<Funcionario>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario ORDER BY id");

            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                final Funcionario func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(idDepartamento);
                funcs.add(func);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return funcs;
    }

    public List<Funcionario> getAllExcetoChefes() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Funcionario> funcs = new ArrayList<Funcionario>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario"
                            + " WHERE (id NOT IN (SELECT id_chefe FROM departamento WHERE id_chefe IS NOT NULL) AND"
                            + "        id NOT IN (SELECT id_chefe_substituto FROM departamento WHERE id_chefe_substituto IS NOT NULL));");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");
                final Integer deptId = (Integer) r.getObject("id_departamento");

                final Funcionario func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(deptId);
                funcs.add(func);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return funcs;
    }

    @Override
    public Funcionario getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        Funcionario func = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario WHERE id=?");
            s.setObject(1, id, Types.INTEGER);

            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");

                func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(idDepartamento);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return func;
    }

    public Funcionario getByLogin(final String login) {
        final Connection connection = DatabaseConnection.getConnection();
        Funcionario func = null;
        try {
            final PreparedStatement s = connection
                    .prepareStatement("SELECT id FROM funcionario WHERE login=?");
            s.setObject(1, login, Types.VARCHAR);

            final ResultSet r = s.executeQuery();
            if (r.next()) {
                final Integer idFunc = (Integer) r.getObject("id");
                func = getById(idFunc);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return func;
    }

    public List<Funcionario> getSemDepartamento() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Funcionario> funcs = new ArrayList<Funcionario>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario WHERE id_departamento IS NULL");

            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");

                final Funcionario func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(null);
                funcs.add(func);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return funcs;
    }

    public List<Funcionario> getByDepartamento(final Departamento departamento) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Funcionario> funcs = new ArrayList<Funcionario>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario WHERE id_departamento=?");
            s.setObject(1, departamento.getId(), Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");

                final Funcionario func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(departamento.getId());
                funcs.add(func);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return funcs;
    }

    public List<Funcionario> getByDepartamentoExcetoChefes(final Departamento departamento) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<Funcionario> funcs = new ArrayList<Funcionario>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,login,senha,nome,cpf,email,id_departamento" + "	FROM funcionario"
                            + "	WHERE id_departamento=? AND (id NOT IN (SELECT id_chefe FROM departamento WHERE id_chefe IS NOT NULL AND id=?) AND"
                            + "								 id NOT IN (SELECT id_chefe_substituto FROM departamento WHERE id_chefe_substituto IS NOT NULL AND id=?))");
            s.setObject(1, departamento.getId(), Types.INTEGER);
            s.setObject(2, departamento.getId(), Types.INTEGER);
            s.setObject(3, departamento.getId(), Types.INTEGER);
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String login = (String) r.getObject("login");
                final String senha = (String) r.getObject("senha");
                final String nome = (String) r.getObject("nome");
                final String cpf = (String) r.getObject("cpf");
                final String email = (String) r.getObject("email");

                final Funcionario func = new Funcionario();
                func.setId(id);
                func.setLogin(login);
                func.setSenha(senha);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setEmail(email);
                func.setIdDepartamento(departamento.getId());
                funcs.add(func);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return funcs;
    }

    @Override
    public boolean add(final Funcionario item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO funcionario(login,senha,nome,cpf,email,id_departamento) VALUES (?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getLogin(), Types.VARCHAR);
                s.setObject(2, item.getSenha(), Types.VARCHAR);
                s.setObject(3, item.getNome(), Types.VARCHAR);
                s.setObject(4, item.getCpf(), Types.VARCHAR);
                s.setObject(5, item.getEmail(), Types.VARCHAR);
                s.setObject(6, item.getIdDepartamento(), Types.INTEGER);
                s.executeUpdate();

                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO funcionario(id,login,senha,nome,cpf,email,id_departamento) VALUES (?,?,?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getLogin(), Types.VARCHAR);
                s.setObject(3, item.getSenha(), Types.VARCHAR);
                s.setObject(4, item.getNome(), Types.VARCHAR);
                s.setObject(5, item.getCpf(), Types.VARCHAR);
                s.setObject(6, item.getEmail(), Types.VARCHAR);
                s.setObject(7, item.getIdDepartamento(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<Funcionario> items) {
        boolean added = false;
        for (final Funcionario item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final Funcionario item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE funcionario SET login=?,senha=?,nome=?,cpf=?,email=?,id_departamento=? WHERE id=?");
            s.setObject(1, item.getLogin(), Types.VARCHAR);
            s.setObject(2, item.getSenha(), Types.VARCHAR);
            s.setObject(3, item.getNome(), Types.VARCHAR);
            s.setObject(4, item.getCpf(), Types.VARCHAR);
            s.setObject(5, item.getEmail(), Types.VARCHAR);
            s.setObject(6, item.getIdDepartamento(), Types.INTEGER);
            s.setObject(7, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<Funcionario> items) {
        boolean updated = false;
        for (final Funcionario item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final Funcionario item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM funcionario WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<Funcionario> items) {
        boolean removed = false;
        for (final Funcionario item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
