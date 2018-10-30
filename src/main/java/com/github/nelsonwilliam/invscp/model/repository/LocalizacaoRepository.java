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
		Connection connection = DatabaseConnection.getConnection();
		List<Localizacao> locals = new ArrayList<Localizacao>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,endereco,cep,cidade,uf,pais FROM localizacao ORDER BY id");
			ResultSet r = s.executeQuery();
			if (r.next()) {
				Integer id = r.getInt("id");
				String nome = r.getString("nome");
				String endereco = r.getString("endereco");
				String cep = r.getString("cep");
				String cidade = r.getString("cidade");
				String uf = r.getString("uf");
				String pais = r.getString("pais");

				Localizacao loc = new Localizacao();
				loc.setId(id);
				loc.setNome(nome);
				loc.setEndereco(endereco);
				loc.setCep(cep);
				loc.setCidade(cidade);
				loc.setUf(uf);
				loc.setPais(pais);
				locals.add(loc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locals;
	}

	@Override
	public Localizacao getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Localizacao local = null;
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT id,nome,endereco,cep,cidade,uf,pais FROM localizacao WHERE id=?");
			s.setInt(1, id);

			ResultSet r = s.executeQuery();
			if (r.next()) {
				String nome = r.getString("nome");
				String endereco = r.getString("endereco");
				String cep = r.getString("cep");
				String cidade = r.getString("cidade");
				String uf = r.getString("uf");
				String pais = r.getString("pais");

				local = new Localizacao();
				local.setId(id);
				local.setNome(nome);
				local.setEndereco(endereco);
				local.setCep(cep);
				local.setCidade(cidade);
				local.setUf(uf);
				local.setPais(pais);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return local;
	}

	@Override
	public boolean add(Localizacao item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			PreparedStatement s;
			if (item.getId() == null) {
				s = connection.prepareStatement(
						"INSERT INTO localizacao(nome,endereco,cep,cidade,uf,pais) VALUES (?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				s.setObject(1, item.getNome(), Types.VARCHAR);
				s.setObject(2, item.getEndereco(), Types.VARCHAR);
				s.setObject(3, item.getCep(), Types.INTEGER);
				s.setObject(4, item.getCidade(), Types.VARCHAR);
				s.setObject(5, item.getUfString(), Types.VARCHAR);
				s.setObject(6, item.getPais(), Types.VARCHAR);
				s.executeUpdate();
				// Atualiza o item adicionado com seu novo ID
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()) {
					Integer id = rs.getInt(1);
					item.setId(id);
				}

			} else {
				s = connection.prepareStatement(
						"INSERT INTO localizacao(id,nome,endereco,cep,cidade,uf,pais) VALUES (?,?,?,?,?,?,?)");
				s.setObject(1, item.getId(), Types.INTEGER);
				s.setObject(2, item.getNome(), Types.VARCHAR);
				s.setObject(3, item.getEndereco(), Types.VARCHAR);
				s.setObject(4, item.getCep(), Types.INTEGER);
				s.setObject(5, item.getCidade(), Types.VARCHAR);
				s.setObject(6, item.getUfString(), Types.VARCHAR);
				s.setObject(7, item.getPais(), Types.VARCHAR);
				s.executeUpdate();
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Localizacao> items) {
		boolean added = false;
		for (Localizacao item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Localizacao item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement(
					"UPDATE localizacao SET nome=?, endereco=?, cep=?, cidade=?, uf=?, pais=? WHERE id=?");
			s.setObject(1, item.getNome(), Types.VARCHAR);
			s.setObject(2, item.getEndereco(), Types.VARCHAR);
			s.setObject(3, item.getCep(), Types.INTEGER);
			s.setObject(4, item.getCidade(), Types.VARCHAR);
			s.setObject(5, item.getUfString(), Types.VARCHAR);
			s.setObject(6, item.getPais(), Types.VARCHAR);
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Localizacao> items) {
		boolean updated = false;
		for (Localizacao item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Localizacao item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM localizacao WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Localizacao> items) {
		boolean removed = false;
		for (Localizacao item : items) {
			removed |= remove(item);
		}
		return removed;
	}

}
