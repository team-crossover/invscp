package com.github.nelsonwilliam.invscp.model;

public class Dog implements Model {

	private static final long serialVersionUID = 1698598770917624810L;

	private Integer id;
	private String name;
	private Integer age;

	@Override
	public Integer getId() {
		return this.id;
	}

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
