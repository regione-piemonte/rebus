CREATE TABLE sirtpla_d_natura_giuridica
( id_natura_giuridica           NUMERIC(2)              NOT NULL
, cod_natura_giuridica          CHARACTER VARYING(10)   NOT NULL
, desc_natura_giuridica         CHARACTER VARYING(100)  NOT NULL
, desc_breve_natura_giuridica   CHARACTER VARYING(30)
, flg_patrimonio                CHARACTER VARYING(1)
, CONSTRAINT ak_d_natura_giuridica_01 UNIQUE (cod_natura_giuridica)
, CONSTRAINT ak_d_natura_giuridica_02 UNIQUE (desc_natura_giuridica)
, CONSTRAINT pk_d_natura_giuridica PRIMARY KEY (id_natura_giuridica)
);
CREATE TABLE sirtpla_d_ruolo_sog_giuridico
( id_ruolo_sog_giuridico    NUMERIC(2)              NOT NULL
, cod_ruolo_sog_giuridico   CHARACTER VARYING(1)    NOT NULL
, desc_ruolo_sog_giuridico  CHARACTER VARYING(15)   NOT NULL
, CONSTRAINT ak_d_ruolo_sog_giuridico_01 UNIQUE (cod_ruolo_sog_giuridico)
, CONSTRAINT ak_d_ruolo_sog_giuridico_02 UNIQUE (desc_ruolo_sog_giuridico)
, CONSTRAINT pk_d_ruolo_sog_giuridico PRIMARY KEY (id_ruolo_sog_giuridico)
);
CREATE TABLE sirtpla_d_tipo_ente
( id_tipo_ente    NUMERIC(2)              NOT NULL
, desc_tipo_ente  CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_ente_01 UNIQUE (desc_tipo_ente)
, CONSTRAINT pk_d_tipo_ente PRIMARY KEY (id_tipo_ente)
);
CREATE TABLE sirtpla_d_tipo_unione_sg
( id_tipo_unione_sg     NUMERIC(2)              NOT NULL
, desc_tipo_unione_sg   CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_unione_sg_01 UNIQUE (desc_tipo_unione_sg)
, CONSTRAINT pk_d_tipo_unione_sg PRIMARY KEY (id_tipo_unione_sg)
);
CREATE TABLE sirtpla_t_deposito
( id_deposito               NUMERIC(4)              NOT NULL  DEFAULT NextVal('seq_t_deposito'::regclass)
, denom_deposito            CHARACTER VARYING(100)
, toponimo_deposito         CHARACTER VARYING(15)
, indirizzo_deposito        CHARACTER VARYING(50)
, num_civico_deposito       CHARACTER VARYING(10)
, cap_deposito              CHARACTER VARYING(5)
, id_comune_deposito        NUMERIC(8)
, telefono_deposito         CHARACTER VARYING(20)
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMPTZ             NOT NULL  DEFAULT now()
, flag_deposito_prevalente  BOOL
, CONSTRAINT pk_t_deposito PRIMARY KEY (id_deposito)
);
CREATE TABLE sirtpla_d_tipo_sog_giuridico
( id_tipo_sog_giuridico       NUMERIC(2)              NOT NULL
, cod_tipo_sog_giuridico      CHARACTER VARYING(10)   NOT NULL
, desc_tipo_sog_giuridico     CHARACTER VARYING(100)  NOT NULL
, valido_per_sog_giurid       BOOL                    NOT NULL
, id_ruolo_sog_giuridico      NUMERIC(2)
, CONSTRAINT ak_d_tipo_sog_giuridico_01 UNIQUE (cod_tipo_sog_giuridico, valido_per_sog_giurid, id_ruolo_sog_giuridico)
, CONSTRAINT ak_d_tipo_sog_giuridico_02 UNIQUE (desc_tipo_sog_giuridico, valido_per_sog_giurid, id_ruolo_sog_giuridico)
, CONSTRAINT chk_d_tipo_sog_giuridico_01 CHECK ((valido_per_sog_giurid = ANY (ARRAY[false, true])))
, CONSTRAINT pk_d_tipo_sog_giuridico PRIMARY KEY (id_tipo_sog_giuridico)
, CONSTRAINT fk_d_tipo_sog_giuridico_01 FOREIGN KEY (id_ruolo_sog_giuridico) REFERENCES sirtpla_d_ruolo_sog_giuridico(id_ruolo_sog_giuridico)
);
CREATE TABLE sirtpla_t_soggetto_giuridico
( id_soggetto_giuridico     NUMERIC(4)              NOT NULL DEFAULT NextVal('seq_t_soggetto_giuridico'::regclass)
, id_tipo_sog_giuridico     NUMERIC(2)              NOT NULL
, id_natura_giuridica       NUMERIC(2)
, denominazione_breve       CHARACTER VARYING(100)
, denominazione_aaep        CHARACTER VARYING(100)
, denom_soggetto_giuridico  CHARACTER VARYING(100)
, partita_iva               CHARACTER VARYING(11)
, codice_fiscale            CHARACTER VARYING(16)
, cod_osservatorio_naz      CHARACTER VARYING(6)
, nome_rappr_legale         CHARACTER VARYING(50)
, cognome_rappr_legale      CHARACTER VARYING(50)
, toponimo_sede_legale      CHARACTER VARYING(15)
, indirizzo_sede_legale     CHARACTER VARYING(50)
, num_civico_sede_legale    CHARACTER VARYING(10)
, cap_sede_legale           CHARACTER VARYING(5)
, id_comune_sede_legale     NUMERIC(8)
, telefono_sede_legale      CHARACTER VARYING(20)
, fax_sede_legale           CHARACTER VARYING(20)
, email_sede_legale         CHARACTER VARYING(50)
, pec_sede_legale           CHARACTER VARYING(50)
, indirizzo_web             CHARACTER VARYING(100)
, numero_verde              CHARACTER VARYING(20)
, note                      CHARACTER VARYING(2000)
, logo                      BYTEA
, id_tipo_ente              NUMERIC(2)
, descrizione               CHARACTER VARYING(2000)
, data_inizio_attivita      DATE
, data_cessazione           DATE
, id_omnibus                NUMERIC(4)
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMPTZ             NOT NULL DEFAULT Now()
, cod_id_regionale          CHARACTER VARYING(15)   NOT NULL
, cod_csr_bip               NUMERIC(4)
, capitale_sociale          NUMERIC(11, 2)
, soc_unipersonale          CHARACTER VARYING(150)
, CONSTRAINT ak_t_soggetto_giuridico_01 UNIQUE (cod_csr_bip)
, CONSTRAINT pk_t_soggetto_giuridico PRIMARY KEY (id_soggetto_giuridico)
, CONSTRAINT fk_t_soggetto_giuridico_01 FOREIGN KEY (id_tipo_sog_giuridico) REFERENCES sirtpla_d_tipo_sog_giuridico(id_tipo_sog_giuridico)
, CONSTRAINT fk_t_soggetto_giuridico_02 FOREIGN KEY (id_natura_giuridica) REFERENCES sirtpla_d_natura_giuridica(id_natura_giuridica)
, CONSTRAINT fk_t_soggetto_giuridico_03 FOREIGN KEY (id_tipo_ente) REFERENCES sirtpla_d_tipo_ente(id_tipo_ente)
);
CREATE TABLE sirtpla_r_sog_giurid_deposito
( id_soggetto_giuridico     NUMERIC(4)    NOT NULL
, id_deposito               NUMERIC(4)    NOT NULL
, id_utente_aggiornamento   NUMERIC(6)    NOT NULL
, data_aggiornamento        TIMESTAMPTZ   NOT NULL  DEFAULT Now()
, CONSTRAINT pk_r_sog_giurid_deposito PRIMARY KEY (id_soggetto_giuridico, id_deposito)
, CONSTRAINT fk_r_sog_giurid_deposito_01 FOREIGN KEY (id_soggetto_giuridico) REFERENCES sirtpla_t_soggetto_giuridico(id_soggetto_giuridico)
, CONSTRAINT fk_r_sog_giurid_deposito_02 FOREIGN KEY (id_deposito) REFERENCES sirtpla_t_deposito(id_deposito)
);
CREATE TABLE sirtpla_r_unione_sog_giurid
( id_unione_sog_giurid      NUMERIC(5)    NOT NULL  DEFAULT NextVal('seq_r_unione_sog_giurid'::regclass)
, id_soggetto_giurid_orig   NUMERIC(4)    NOT NULL
, id_soggetto_giurid_dest   NUMERIC(4)    NOT NULL
, id_tipo_unione_sg         NUMERIC(2)    NOT NULL
, data_esecuzione           DATE          NOT NULL
, id_utente_aggiornamento   NUMERIC(6)    NOT NULL
, data_aggiornamento        TIMESTAMPTZ   NOT NULL  DEFAULT Now()
, CONSTRAINT ak_r_unione_sog_giurid_01 UNIQUE (id_soggetto_giurid_orig, id_soggetto_giurid_dest, id_tipo_unione_sg, data_esecuzione)
, CONSTRAINT pk_r_unione_sog_giurid PRIMARY KEY (id_unione_sog_giurid)
, CONSTRAINT fk_r_unione_sog_giurid_01 FOREIGN KEY (id_soggetto_giurid_orig) REFERENCES sirtpla_t_soggetto_giuridico(id_soggetto_giuridico)
, CONSTRAINT fk_r_unione_sog_giurid_02 FOREIGN KEY (id_soggetto_giurid_dest) REFERENCES sirtpla_t_soggetto_giuridico(id_soggetto_giuridico)
, CONSTRAINT fk_r_unione_sog_giurid_03 FOREIGN KEY (id_tipo_unione_sg) REFERENCES sirtpla_d_tipo_unione_sg(id_tipo_unione_sg)
);
CREATE TABLE sirtpla_t_dato_bancario
( id_dato_bancario          NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_t_dato_bancario'::regclass)
, id_soggetto_giuridico     NUMERIC(4)              NOT NULL
, iban                      CHARACTER VARYING(27)   NOT NULL
, note                      CHARACTER VARYING(200)
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMPTZ             NOT NULL  DEFAULT Now()
, flag_doatpl               BOOL                              DEFAULT FALSE
, CONSTRAINT ak_t_dato_bancario_01 UNIQUE (id_soggetto_giuridico, iban)
, CONSTRAINT pk_t_dato_bancario PRIMARY KEY (id_dato_bancario)
, CONSTRAINT fk_t_dato_bancario_01 FOREIGN KEY (id_soggetto_giuridico) REFERENCES sirtpla_t_soggetto_giuridico(id_soggetto_giuridico)
);




ALTER TABLE sirtpla_d_natura_giuridica      OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_d_ruolo_sog_giuridico   OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_d_tipo_ente             OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_d_tipo_unione_sg        OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_t_deposito              OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_d_tipo_sog_giuridico    OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_t_soggetto_giuridico    OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_r_sog_giurid_deposito   OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_r_unione_sog_giurid     OWNER TO sirtpl_aziende;
ALTER TABLE sirtpla_t_dato_bancario         OWNER TO sirtpl_aziende;




GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_natura_giuridica TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_natura_giuridica TO sirtpl_trasv_rw;
GRANT SELECT                          ON TABLE sirtpla_d_natura_giuridica TO rebus;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_natura_giuridica TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_d_natura_giuridica TO sirtpl_trasv_ro;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_ruolo_sog_giuridico TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_ruolo_sog_giuridico TO sirtpl_trasv;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_ruolo_sog_giuridico TO sirtpl_trasv_rw;
GRANT SELECT                          ON TABLE sirtpla_d_ruolo_sog_giuridico TO rebus;
GRANT SELECT                          ON TABLE sirtpla_d_ruolo_sog_giuridico TO sirtpl_trasv_ro;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_ente TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_ente TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_ente TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_ente TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_ente TO rebus;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_unione_sg TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_unione_sg TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_unione_sg TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_unione_sg TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_unione_sg TO rebus;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_deposito TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_deposito TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_deposito TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_t_deposito TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_t_deposito TO rebus;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_sog_giuridico TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_sog_giuridico TO sirtpl_trasv_rw;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_sog_giuridico TO rebus;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_d_tipo_sog_giuridico TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_d_tipo_sog_giuridico TO sirtpl_trasv_ro;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_soggetto_giuridico TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_soggetto_giuridico TO sirtpl_trasv_rw;
GRANT SELECT                          ON TABLE sirtpla_t_soggetto_giuridico TO rebus;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_soggetto_giuridico TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_t_soggetto_giuridico TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_t_soggetto_giuridico TO sirtpl_contratti;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_sog_giurid_deposito TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_sog_giurid_deposito TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_sog_giurid_deposito TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_r_sog_giurid_deposito TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_r_sog_giurid_deposito TO rebus;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_unione_sog_giurid TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_unione_sog_giurid TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_r_unione_sog_giurid TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_r_unione_sog_giurid TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_r_unione_sog_giurid TO rebus;
----
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_dato_bancario TO sirtpl_aziende_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_dato_bancario TO sirtpl_trasv_rw;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE sirtpla_t_dato_bancario TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtpla_t_dato_bancario TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtpla_t_dato_bancario TO rebus;
