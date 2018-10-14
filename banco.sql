CREATE TABLE public.dog (
    id serial PRIMARY KEY,
    age integer,
    name character varying(255)
);

ALTER TABLE ONLY public.dog
	OWNER TO "invscpAdmin"