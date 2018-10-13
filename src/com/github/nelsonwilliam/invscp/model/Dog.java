package com.github.nelsonwilliam.invscp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dog")
public class Dog implements Model {

	private static final long serialVersionUID = 1698598770917624810L;

	@Id
	@GeneratedValue
	protected Integer id;

	private String name;

	private Integer age;

	/**
	 * Obtém o ID deste item.
	 * 
	 * @return
	 */
	@Override
	public Integer getId() {
		return this.id;
	}

	/**
	 * Define o ID deste item.
	 * 
	 * @param id
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
