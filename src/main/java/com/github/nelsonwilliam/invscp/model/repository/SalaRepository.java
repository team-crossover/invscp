package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Sala;
//import com.github.nelsonwilliam.invscp.model.SalasEnum;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class SalaRepository implements Repository<Sala> {

	@Override
	public List<Sala> getAll() {
		Connection connection = DatabaseConnection.getConnection();
		List<Sala> salas = new ArrayList<Sala>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,tipo,de_deposito,id_predio,id_departamento FROM sala ORDER_BY id");
			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String nome = r.getString("nome");
				String tipo = r.getString("tipo");
				Boolean deDeposito = r.getBoolean("de_deposito");
				Integer idPredio = r.getInt("id_predio");
				Integer idDepartamento = r.getInt("id_departamento");

				Sala sala = new Sala();
				sala.setId(id);
				sala.setNome(nome);
				sala.setTipo(tipo);
				sala.setDeDeposito(deDeposito);
				sala.setIdPredio(idPredio);
				sala.setIdDepartamento(idDepartamento);
				salas.add(sala);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salas;
	}

	@Override
	public Sala getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Sala salas = null;
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,tipo,de_deposito,id_predio,id_departamento FROM sala WHERE id=?");
			ResultSet r = s.executeQuery();
			s.setInt(1, id);
			if (r.next()) {
				String nome = r.getString("nome");
				String tipo = r.getString("tipo");
				Boolean deDeposito = r.getBoolean("de_deposito");
				Integer idPredio = r.getInt("id_predio");
				Integer idDepartamento = r.getInt("id_departamento");

				Sala sala = new Sala();
				sala.setId(id);
				sala.setNome(nome);
				sala.setTipo(tipo);
				sala.setDeDeposito(deDeposito);
				sala.setIdPredio(idPredio);
				sala.setIdDepartamento(idDepartamento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salas;
	}

	@Override
	public boolean add(Sala item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			PreparedStatement s;
			if (item.getId() == null) {
				s = connection.prepareStatement(
						"INSERT INTO sala(nome,tipo,de_deposito,id_predio,id_departamento) VALUES (?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				s.setObject(1, item.getNome(), Types.VARCHAR);
				s.setObject(2, item.getTipo(), Types.VARCHAR);
				s.setObject(3, item.isDeDeposito(), Types.BOOLEAN);
				s.setObject(4, item.getIdPredio(), Types.INTEGER);
				s.setObject(5, item.getIdDepartamento(), Types.INTEGER);
				s.executeUpdate();
				// Atualiza o item adicionado com seu novo ID
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
					item.setId(id);
				}

			} else {
				s = connection.prepareStatement(
						"INSERT INTO sala(id,nome,tipo,de_deposito,id_predio,id_departamento) VALUES (?,?,?,?,?,?)");
				s.setObject(1, item.getNome(), Types.VARCHAR);
				s.setObject(2, item.getTipo(), Types.VARCHAR);
				s.setObject(3, item.isDeDeposito(), Types.BOOLEAN);
				s.setObject(4, item.getIdPredio(), Types.INTEGER);
				s.setObject(5, item.getIdDepartamento(), Types.INTEGER);
				s.executeUpdate();
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Sala> items) {
		boolean added = false;
		for (Sala item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Sala item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement(
					"UPDATE sala SET nome=?, tipo=?, de_deposito=?, id_predio=?, id_departamento");
			s.setObject(1, item.getNome(), Types.VARCHAR);
			s.setObject(2, item.getTipo(), Types.VARCHAR);
			s.setObject(3, item.isDeDeposito(), Types.BOOLEAN);
			s.setObject(4, item.getIdPredio(), Types.INTEGER);
			s.setObject(5, item.getIdDepartamento(), Types.INTEGER);
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Sala> items) {
		boolean updated = false;
		for (Sala item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Sala item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM sala WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Sala> items) {
		boolean removed = false;
		for (Sala item : items) {
			removed |= remove(item);
		}
		return removed;
	}

}
