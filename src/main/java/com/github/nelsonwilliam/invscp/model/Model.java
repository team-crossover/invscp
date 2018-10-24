package com.github.nelsonwilliam.invscp.model;

import java.io.Serializable;

/**
 * Models são responsáveis por conter toda a lógica de negócio. Cada Model deve
 * representar uma entidade que mapeie o domínio da aplicação e contenha as
 * regras relevantes ao negócio.
 */
public interface Model extends Serializable {

	Integer getId();

	void setId(Integer integer);

}
