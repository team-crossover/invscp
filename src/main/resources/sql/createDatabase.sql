/* Cria as tabelas do InvSCP */

/* Deleta as tabelas anteriores */

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

/* Cria as novas tabelas */

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
    nome character varying(255)
);

CREATE TABLE sala (
    id serial PRIMARY KEY,
    nome character varying(255),
    tipo character varying(255),
    de_deposito boolean
);

CREATE TABLE usuario (
    id serial PRIMARY KEY,
    login character varying(255),
    senha character varying(255),
    nome character varying(255),
    cpf character varying(10),
    email character varying(255)
);

CREATE TABLE departamento (
    id serial PRIMARY KEY,
    nome character varying(255),
    de_patrimonio boolean
);

/* Adiciona as chaves estrangeiras */

ALTER TABLE predio
    ADD COLUMN id_localizacao integer references localizacao(id);

ALTER TABLE sala
    ADD COLUMN id_predio integer references predio(id),
    ADD COLUMN id_departamento integer references departamento(id);
    
ALTER TABLE usuario
    ADD COLUMN id_departamento integer references departamento(id);

ALTER TABLE departamento
    ADD COLUMN id_chefe integer references usuario(id),
    ADD COLUMN id_chefe_substituto integer references usuario(id);
