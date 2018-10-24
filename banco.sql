/*CREATE TABLE public.dog (
    id serial PRIMARY KEY,
    age integer,
    name character varying(255)
);

ALTER TABLE ONLY public.dog
	OWNER TO "invscpAdmin"*/
	
CREATE TABLE localizacao (
    id serial PRIMARY KEY,
    nome character varying(255),
    endereco text,
    cep integer,
    cidade character varying(255),
    uf character varying(2),
    pais character varying(2)
);

CREATE TABLE predio (
    id serial PRIMARY KEY,
    nome character varying(255),
    id_local integer references departamento(id)
);

CREATE TABLE sala (
    id serial PRIMARY KEY,
    nome character varying(255),
    tipo nome character varying(255),
    de_deposito boolean,
    id_predio integer references predio(id)
);

CREATE TABLE departamento (
    id serial PRIMARY KEY,
    nome character varying(255),
    de_patrimonio boolean,
    id_departamento integer references predio(id)
);

CREATE TABLE usuario (
    id serial PRIMARY KEY,
    login character varying(255),
    senha character varying(255),
    nome character varying(255),
    cpf character varying(10),
    email character varying(255)
);
