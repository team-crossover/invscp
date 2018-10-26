package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class DepartamentoRepository implements Repository<Departamento> {

	@Override
	public List<Departamento> getAll() {
		Connection connection = DatabaseConnection.getConnection();
		List<Departamento> depts = new ArrayList<Departamento>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,de_patrimonio,id_chefe,id_chefe_substituto FROM departamento ORDER BY id");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				Integer id = r.getInt("id");
				String nome = r.getString("nome");
				Boolean dePatrimonio = r.getBoolean("de_patrimonio");
				Integer idChefe = r.getInt("id_chefe");
				Integer idChefeSubstituto = r.getInt("id_chefe_substituto");

				Departamento dept = new Departamento();
				dept.setId(id);
				dept.setNome(nome);
				dept.setDePatrimonio(dePatrimonio);
				dept.setIdChefe(idChefe);
				dept.setIdChefeSubstituto(idChefeSubstituto);
				depts.add(dept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depts;
	}

	@Override
	public Departamento getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Departamento dept = null;
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,de_patrimonio,id_chefe,id_chefe_substituto FROM departamento WHERE id=?");
			s.setInt(1, id);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				String nome = r.getString("nome");
				Boolean dePatrimonio = r.getBoolean("de_patrimonio");
				Integer idChefe = r.getInt("id_chefe");
				Integer idChefeSubstituto = r.getInt("id_chefe_substituto");

				dept = new Departamento();
				dept.setId(id);
				dept.setNome(nome);
				dept.setDePatrimonio(dePatrimonio);
				dept.setIdChefe(idChefe);
				dept.setIdChefeSubstituto(idChefeSubstituto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	/**
	 * Obtém o departamento cujo o chefe (ou o chefe substituto) tenha determinado
	 * ID de funcionário. Retora nulo caso nenhum departamento tenha chefe com tal
	 * ID.
	 */
	public Departamento getByChefeOrSubstitutoId(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Departamento dept = null;
		try {
			PreparedStatement s = connection
					.prepareStatement("SELECT id FROM departamento WHERE id_chefe=? OR id_chefe_substituto=?");
			s.setInt(1, id);
			s.setInt(2, id);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer idDept = r.getInt("id");
				dept = getById(idDept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	@Override
	public boolean add(Departamento item) {
		Connection connection = DatabaseConnection.getConnection();
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
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Departamento> items) {
		boolean added = false;
		for (Departamento item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Departamento item) {
		Connection connection = DatabaseConnection.getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Departamento> items) {
		boolean updated = false;
		for (Departamento item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Departamento item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM departamento WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Departamento> items) {
		boolean removed = false;
		for (Departamento item : items) {
			removed |= remove(item);
		}
		return removed;
	}
}
