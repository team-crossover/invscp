package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class PredioRepository implements Repository<Predio> {

	@Override
	public List<Predio> getAll() {
		Connection connection = DatabaseConnection.getConnection();
		List<Predio> predios = new ArrayList<Predio>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,id_localizacao FROM predio ORDER_BY id");
			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String nome = r.getString("nome");
				Integer idLocalizacao = r.getInt("id_localizacao");

				Predio predio = new Predio();
				predio.setId(id);
				predio.setNome(nome);
				predio.setIdLocalizacao(idLocalizacao);
				predios.add(predio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return predios;
	}

	@Override
	public Predio getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Predio predio = null;
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,id_localizacao FROM predio WHERE id=?");
			ResultSet r = s.executeQuery();
			s.setInt(1, id);
			if (r.next()) {
				String nome = r.getString("nome");
				Integer idLocalizacao = r.getInt("id_localizacao");

				predio = new Predio();
				predio.setNome(nome);
				predio.setIdLocalizacao(idLocalizacao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return predio;
	}

	@Override
	public boolean add(Predio item) {
		Connection connection = DatabaseConnection.getConnection();
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
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Predio> items) {
		boolean added = false;
		for (Predio item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Predio item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement(
					"UPDATE predio SET nome=?, id_localizacao=?");
			s.setObject(1, item.getNome(), Types.VARCHAR);
			s.setObject(2, item.getIdLocalizacao(), Types.INTEGER);
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Predio> items) {
		boolean updated = false;
		for (Predio item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Predio item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM predio WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Predio> items) {
		boolean removed = false;
		for (Predio item : items) {
			removed |= remove(item);
		}
		return removed;
	}

}
