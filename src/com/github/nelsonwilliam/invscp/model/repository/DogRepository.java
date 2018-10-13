package com.github.nelsonwilliam.invscp.model.repository;

import com.github.nelsonwilliam.invscp.model.Dog;

public class DogRepository extends Repository<Dog> {

	@Override
	public Class<Dog> getModelClass() {
		return Dog.class;
	}

}
