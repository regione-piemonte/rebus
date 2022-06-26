CREATE TABLE sirtpl_d_contesto
( id_contesto     NUMERIC(1)              NOT NULL
, cod_contesto    BPCHAR(1)               NOT NULL
, desc_contesto   CHARACTER VARYING(25)   NOT NULL
, CONSTRAINT ak_d_contesto_01 UNIQUE (cod_contesto)
, CONSTRAINT ak_d_contesto_02 UNIQUE (desc_contesto)
, CONSTRAINT pk_d_contesto PRIMARY KEY (id_contesto)
);
CREATE TABLE sirtpl_d_nazione
( id_nazione            NUMERIC(4)              NOT NULL
, cod_istat_nazione     CHARACTER VARYING(3)
, cod_belfiore_nazione  CHARACTER VARYING(4)
, denom_nazione         CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, dt_id_stato           NUMERIC(20)
, dt_id_stato_prev      NUMERIC(20)
, dt_id_stato_next      NUMERIC(20)
, CONSTRAINT ak_d_nazione_01 UNIQUE (cod_istat_nazione, cod_belfiore_nazione, denom_nazione, data_inizio_validita)
, CONSTRAINT pk_d_nazione PRIMARY KEY (id_nazione)
);
CREATE TABLE sirtpl_d_toponimo
( id_toponimo     NUMERIC(3)              NOT NULL
, desc_toponimo   CHARACTER VARYING(50)   NOT NULL
, CONSTRAINT ak_d_toponimo_01 UNIQUE (desc_toponimo)
, CONSTRAINT pk_d_toponimo PRIMARY KEY (id_toponimo)
);
CREATE TABLE sirtpl_t_utente
( id_utente             NUMERIC(6)              NOT NULL
, descrizione           CHARACTER VARYING(100)
, codice_fiscale        CHARACTER VARYING(16)
, cognome               CHARACTER VARYING(50)
, nome                  CHARACTER VARYING(50)
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_t_utente_01 UNIQUE (descrizione, codice_fiscale, cognome, nome, data_inizio_validita)
, CONSTRAINT pk_t_utente PRIMARY KEY (id_utente)
);
CREATE TABLE sirtpl_d_regione
( id_regione            NUMERIC(5)              NOT NULL
, cod_istat_regione     CHARACTER VARYING(2)
, denom_regione         CHARACTER VARYING(100)  NOT NULL
, id_nazione            NUMERIC(4)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_regione_01 UNIQUE (cod_istat_regione, denom_regione, id_nazione, data_inizio_validita)
, CONSTRAINT pk_d_regione PRIMARY KEY (id_regione)
, CONSTRAINT fk_d_regione_01 FOREIGN KEY (id_nazione) REFERENCES sirtpl_d_nazione(id_nazione)
);
CREATE TABLE sirtpl_r_utente_sog_giurid
( id_utente_sog_giurid    NUMERIC(7)  NOT NULL
, id_utente               NUMERIC(6)  NOT NULL
, id_soggetto_giuridico   NUMERIC(4)  NOT NULL
, data_inizio_validita    DATE        NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita      DATE
, CONSTRAINT ak_r_utente_sog_giurid_01 UNIQUE (id_utente, id_soggetto_giuridico, data_inizio_validita)
, CONSTRAINT pk_r_utente_sog_giurid PRIMARY KEY (id_utente_sog_giurid)
, CONSTRAINT fk_r_utente_sog_giurid_01 FOREIGN KEY (id_utente) REFERENCES sirtpl_t_utente(id_utente)
);
CREATE TABLE sirtpl_s_nazione
( id_s_nazione          NUMERIC(6)              NOT NULL
, id_nazione            NUMERIC(4)              NOT NULL
, cod_istat_nazione     CHARACTER VARYING(3)    NOT NULL
, cod_belfiore_nazione  CHARACTER VARYING(4)
, denom_nazione         CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE                    NOT NULL
, dt_id_stato           NUMERIC(20)
, dt_id_stato_prev      NUMERIC(20)
, dt_id_stato_next      NUMERIC(20)
, CONSTRAINT pk_s_nazione PRIMARY KEY (id_s_nazione)
, CONSTRAINT fk_s_nazione_01 FOREIGN KEY (id_nazione) REFERENCES sirtpl_d_nazione(id_nazione)
);
CREATE TABLE sirtpl_s_regione
( id_s_regione          NUMERIC(7)              NOT NULL
, id_regione            NUMERIC(5)              NOT NULL
, cod_istat_regione     CHARACTER VARYING(2)
, denom_regione         CHARACTER VARYING(100)  NOT NULL
, id_nazione            NUMERIC(4)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE                    NOT NULL
, CONSTRAINT pk_s_regione PRIMARY KEY (id_s_regione)
, CONSTRAINT fk_s_regione_01 FOREIGN KEY (id_nazione) REFERENCES sirtpl_d_nazione(id_nazione)
, CONSTRAINT fk_s_regione_02 FOREIGN KEY (id_regione) REFERENCES sirtpl_d_regione(id_regione)
);
CREATE TABLE sirtpl_d_provincia
( id_provincia          NUMERIC(6)              NOT NULL
, cod_istat_provincia   CHARACTER VARYING(3)
, denom_provincia       CHARACTER VARYING(100)  NOT NULL
, sigla_provincia       CHARACTER VARYING(2)
, id_regione            NUMERIC(5)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_provincia_01 UNIQUE (cod_istat_provincia, denom_provincia, id_regione, data_inizio_validita)
, CONSTRAINT pk_d_provincia PRIMARY KEY (id_provincia)
, CONSTRAINT fk_d_provincia_01 FOREIGN KEY (id_regione) REFERENCES sirtpl_d_regione(id_regione)
);
CREATE TABLE sirtpl_s_provincia
( id_s_provincia        NUMERIC(8)              NOT NULL
, id_provincia          NUMERIC(6)              NOT NULL
, cod_istat_provincia   CHARACTER VARYING(3)
, denom_provincia       CHARACTER VARYING(100)  NOT NULL
, sigla_provincia       CHARACTER VARYING(2)
, id_regione            NUMERIC(5)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE                    NOT NULL
, CONSTRAINT pk_s_provincia PRIMARY KEY (id_s_provincia)
, CONSTRAINT fk_s_provincia_01 FOREIGN KEY (id_provincia) REFERENCES sirtpl_d_provincia(id_provincia)
, CONSTRAINT fk_s_provincia_02 FOREIGN KEY (id_regione) REFERENCES sirtpl_d_regione(id_regione)
);
CREATE TABLE sirtpl_d_comune
( id_comune             NUMERIC(8)              NOT NULL
, cod_istat_comune      CHARACTER VARYING(6)
, cod_belfiore_comune   CHARACTER VARYING(4)
, denom_comune          CHARACTER VARYING(100)  NOT NULL
, id_provincia          NUMERIC(6)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, dt_id_comune          NUMERIC(20)
, dt_id_comune_prev     NUMERIC(20)
, dt_id_comune_next     NUMERIC(20)
, CONSTRAINT ak_d_comune_01 UNIQUE (cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, data_inizio_validita)
, CONSTRAINT pk_d_comune PRIMARY KEY (id_comune)
, CONSTRAINT fk_d_comune_01 FOREIGN KEY (id_provincia) REFERENCES sirtpl_d_provincia(id_provincia)
);
CREATE TABLE sirtpl_s_comune
( id_s_comune           NUMERIC(10)             NOT NULL
, id_comune             NUMERIC(8)              NOT NULL
, cod_istat_comune      CHARACTER VARYING(6)
, cod_belfiore_comune   CHARACTER VARYING(4)
, denom_comune          CHARACTER VARYING(100)  NOT NULL
, id_provincia          NUMERIC(6)              NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE                    NOT NULL
, dt_id_comune          NUMERIC(20)
, dt_id_comune_prev     NUMERIC(20)
, dt_id_comune_next     NUMERIC(20)
, CONSTRAINT pk_s_comune PRIMARY KEY (id_s_comune)
, CONSTRAINT fk_s_comune_01 FOREIGN KEY (id_comune) REFERENCES sirtpl_d_comune(id_comune)
, CONSTRAINT fk_s_comune_02 FOREIGN KEY (id_provincia) REFERENCES sirtpl_d_provincia(id_provincia)
);




ALTER TABLE sirtpl_d_contesto           OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_d_nazione            OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_d_toponimo           OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_t_utente             OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_d_regione            OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_r_utente_sog_giurid  OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_s_nazione            OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_s_regione            OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_d_provincia          OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_s_provincia          OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_d_comune             OWNER TO sirtpl_trasv;
ALTER TABLE sirtpl_s_comune             OWNER TO sirtpl_trasv;
