package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class FuncionarioRepository implements Repository<Funcionario> {

	@Override
	public List<Funcionario> getAll() {
		Connection connection = DatabaseConnection.getConnection();
		List<Funcionario> funcs = new ArrayList<Funcionario>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario ORDER BY id");

			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String login = r.getString("login");
				String senha = r.getString("senha");
				String nome = r.getString("nome");
				String cpf = r.getString("cpf");
				String email = r.getString("email");
				Integer idDepartamento = r.getInt("id_departamento");

				Funcionario func = new Funcionario();
				func.setId(id);
				func.setLogin(login);
				func.setSenha(senha);
				func.setNome(nome);
				func.setCpf(cpf);
				func.setEmail(email);
				func.setIdDepartamento(idDepartamento);
				funcs.add(func);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return funcs;
	}

	@Override
	public Funcionario getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Funcionario func = null;
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario WHERE id=?");
			s.setInt(1, id);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				String login = r.getString("login");
				String senha = r.getString("senha");
				String nome = r.getString("nome");
				String cpf = r.getString("cpf");
				String email = r.getString("email");
				Integer idDepartamento = r.getInt("id_departamento");

				func = new Funcionario();
				func.setId(id);
				func.setLogin(login);
				func.setSenha(senha);
				func.setNome(nome);
				func.setCpf(cpf);
				func.setEmail(email);
				func.setIdDepartamento(idDepartamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return func;
	}

	public Funcionario getByLogin(String login) {
		Connection connection = DatabaseConnection.getConnection();
		Funcionario func = null;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT id FROM funcionario WHERE login=?");
			s.setString(1, login);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer idFunc = r.getInt("id");
				func = getById(idFunc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return func;
	}

	public List<Funcionario> getByDepartamento(Departamento departamento) {
		Connection connection = DatabaseConnection.getConnection();
		List<Funcionario> funcs = new ArrayList<Funcionario>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,login,senha,nome,cpf,email,id_departamento FROM funcionario WHERE id_departamento=?");
			s.setObject(1, departamento.getId(), Types.INTEGER);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String login = r.getString("login");
				String senha = r.getString("senha");
				String nome = r.getString("nome");
				String cpf = r.getString("cpf");
				String email = r.getString("email");

				Funcionario func = new Funcionario();
				func.setId(id);
				func.setLogin(login);
				func.setSenha(senha);
				func.setNome(nome);
				func.setCpf(cpf);
				func.setEmail(email);
				func.setIdDepartamento(departamento.getId());
				funcs.add(func);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return funcs;
	}

	public List<Funcionario> getByDepartamentoExcetoChefes(Departamento departamento) {
		Connection connection = DatabaseConnection.getConnection();
		List<Funcionario> funcs = new ArrayList<Funcionario>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT id,login,senha,nome,cpf,email,id_departamento"
					+ "	FROM funcionario"
					+ "	WHERE id_departamento=? AND (id NOT IN (SELECT id_chefe FROM departamento WHERE id=?) AND"
					+ "								 id NOT IN (SELECT id_chefe_substituto FROM departamento WHERE id=?))");
			s.setObject(1, departamento.getId(), Types.INTEGER);
			s.setObject(2, departamento.getId(), Types.INTEGER);
			s.setObject(3, departamento.getId(), Types.INTEGER);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String login = r.getString("login");
				String senha = r.getString("senha");
				String nome = r.getString("nome");
				String cpf = r.getString("cpf");
				String email = r.getString("email");

				Funcionario func = new Funcionario();
				func.setId(id);
				func.setLogin(login);
				func.setSenha(senha);
				func.setNome(nome);
				func.setCpf(cpf);
				func.setEmail(email);
				func.setIdDepartamento(departamento.getId());
				funcs.add(func);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return funcs;
	}

	@Override
	public boolean add(Funcionario item) {
		Connection connection = DatabaseConnection.getConnection();
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
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Funcionario> items) {
		boolean added = false;
		for (Funcionario item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Funcionario item) {
		Connection connection = DatabaseConnection.getConnection();
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
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Funcionario> items) {
		boolean updated = false;
		for (Funcionario item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Funcionario item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM funcionario WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Funcionario> items) {
		boolean removed = false;
		for (Funcionario item : items) {
			removed |= remove(item);
		}
		return removed;
	}

}
