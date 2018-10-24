package com.github.nelsonwilliam.invscp.prototipo.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.repository.Repository;
import com.github.nelsonwilliam.invscp.prototipo.model.Dog;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class DogRepository implements Repository<Dog> {

	@Override
	public List<Dog> getAll() {
		Connection connection = DatabaseConnection.getConnection();
		List<Dog> dogs = new ArrayList<Dog>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT id,name,age FROM dog");
			ResultSet r = s.executeQuery();
			while (r.next()) {
				Dog dog = new Dog();
				dog.setId(r.getInt("id"));
				dog.setName(r.getString("name"));
				dog.setAge(r.getInt("age"));
				dogs.add(dog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dogs;
	}

	@Override
	public Dog getById(Integer id) {
		Connection connection = DatabaseConnection.getConnection();
		Dog dog = null;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT id,name,age FROM dog WHERE id=?");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				dog = new Dog();
				dog.setId(r.getInt("id"));
				dog.setName(r.getString("name"));
				dog.setAge(r.getInt("age"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dog;
	}

	@Override
	public boolean add(Dog item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			PreparedStatement s;
			if (item.getId() == null) {
				s = connection.prepareStatement("INSERT INTO dog(name,age) VALUES (?,?)");
				s.setString(1, item.getName());
				s.setInt(2, item.getAge());
			} else {
				s = connection.prepareStatement("INSERT INTO dog(id,name,age) VALUES (?,?,?)");
				s.setInt(1, item.getId());
				s.setString(2, item.getName());
				s.setInt(3, item.getAge());
			}
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean add(Iterable<Dog> items) {
		boolean added = false;
		for (Dog item : items) {
			added |= add(item);
		}
		return added;
	}

	@Override
	public boolean update(Dog item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("UPDATE dog SET name=?, age=? WHERE id=?");
			s.setString(1, item.getName());
			s.setInt(2, item.getAge());
			s.setInt(3, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Iterable<Dog> items) {
		boolean updated = false;
		for (Dog item : items) {
			updated |= update(item);
		}
		return updated;
	}

	@Override
	public boolean remove(Dog item) {
		Connection connection = DatabaseConnection.getConnection();
		try {
			if (item.getId() == null) {
				return false;
			}
			PreparedStatement s;
			s = connection.prepareStatement("DELETE FROM dog WHERE id=?");
			s.setInt(1, item.getId());
			s.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(Iterable<Dog> items) {
		boolean removed = false;
		for (Dog item : items) {
			removed |= remove(item);
		}
		return removed;
	}

}
