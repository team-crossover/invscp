package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class FuncionarioRepository implements Repository<Funcionario> {

	@Override
	public List<Funcionario> getAll() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public boolean add(Funcionario item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Iterable<Funcionario> items) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Funcionario item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Iterable<Funcionario> items) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Funcionario item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Iterable<Funcionario> items) {
		// TODO Auto-generated method stub
		return false;
	}

}
