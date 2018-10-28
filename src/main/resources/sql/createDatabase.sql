/* Cria as tabelas do InvSCP */

/* Deleta as tabelas anteriores */

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

/* Cria as novas tabelas */

CREATE TABLE localizacao (
    id serial PRIMARY KEY,
    nome character varying(255) NOT NULL,
    endereco text NOT NULL,
    cep integer NOT NULL,
    cidade character varying(255) NOT NULL,
    uf character varying(2) NOT NULL,
    pais character varying(2) NOT NULL
);

CREATE TABLE predio (
    id serial PRIMARY KEY,
    nome character varying(255) NOT NULL
);

CREATE TABLE sala (
    id serial PRIMARY KEY,
    nome character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    de_deposito boolean NOT NULL
);

CREATE TABLE funcionario (
    id serial PRIMARY KEY,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    cpf character varying(11) NOT NULL,
    email character varying(255) NOT NULL
);

CREATE TABLE departamento (
    id serial PRIMARY KEY,
    nome character varying(255) NOT NULL,
    de_patrimonio boolean NOT NULL
);

/* Adiciona as chaves estrangeiras */

ALTER TABLE predio
    ADD COLUMN id_localizacao integer references localizacao(id);

ALTER TABLE sala
    ADD COLUMN id_predio integer references predio(id),
    ADD COLUMN id_departamento integer references departamento(id);
    
ALTER TABLE funcionario
    ADD COLUMN id_departamento integer references departamento(id);

ALTER TABLE departamento
    ADD COLUMN id_chefe integer references funcionario(id),
    ADD COLUMN id_chefe_substituto integer references funcionario(id);

/* Adiciona os valores iniciais */

DO $$
DECLARE chefeId integer;
DECLARE departamentoId integer;
BEGIN
    INSERT INTO funcionario (login, senha, nome)
        VALUES ('admin', 'admin', 'Administrador') 
        RETURNING id INTO chefeId;

    INSERT INTO departamento (nome, de_patrimonio, id_chefe)
        VALUES ('Departamento de Patrimônio', TRUE, chefeId)
        RETURNING id INTO departamentoId;

    UPDATE funcionario
        SET id_departamento = departamentoId
        WHERE id = chefeId;
END $$;
