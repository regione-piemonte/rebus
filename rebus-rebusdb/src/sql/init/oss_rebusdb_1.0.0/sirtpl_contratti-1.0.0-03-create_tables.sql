CREATE TABLE sirtplc_d_ambito_servizio
( id_ambito_servizio    NUMERIC(2)              NOT NULL
, desc_ambito_servizio  CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_ambito_servizio_01 UNIQUE (desc_ambito_servizio)
, CONSTRAINT pk_d_ambito_servizio PRIMARY KEY (id_ambito_servizio)
);
CREATE TABLE sirtplc_d_bacino
( id_bacino             NUMERIC(2)              NOT NULL
, denom_bacino          CHARACTER VARYING(30)   NOT NULL
, desc_bacino           CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_bacino_01 UNIQUE (denom_bacino, data_inizio_validita)
, CONSTRAINT ak_d_bacino_02 UNIQUE (desc_bacino, data_inizio_validita)
, CONSTRAINT pk_d_bacino PRIMARY KEY (id_bacino)
);
CREATE TABLE sirtplc_d_modalita_affidamento
( id_modalita_affidamento     NUMERIC(2)              NOT NULL
, desc_modalita_affidamento   CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_modalita_affidamento_01 UNIQUE (desc_modalita_affidamento)
, CONSTRAINT pk_d_modalita_affidamento PRIMARY KEY (id_modalita_affidamento)
);
CREATE TABLE sirtplc_d_tipo_affidamento
( id_tipo_affidamento     NUMERIC(2)              NOT NULL
, desc_tipo_affidamento   CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_affidamento_01 UNIQUE (desc_tipo_affidamento)
, CONSTRAINT pk_d_tipo_affidamento PRIMARY KEY (id_tipo_affidamento)
);
CREATE TABLE sirtplc_d_tipo_percorenza
( id_tipo_percorrenza     NUMERIC(2)              NOT NULL
, desc_tipo_percorrenza   CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_percorenza_01 UNIQUE (desc_tipo_percorrenza)
, CONSTRAINT pk_d_tipo_percorenza PRIMARY KEY (id_tipo_percorrenza)
);
CREATE TABLE sirtplc_d_tipo_raggruppamento
( id_tipo_raggruppamento    NUMERIC(2)              NOT NULL
, desc_tipo_raggruppamento  CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_raggruppamemto_01 UNIQUE (desc_tipo_raggruppamento)
, CONSTRAINT pk_d_tipo_raggruppamento PRIMARY KEY (id_tipo_raggruppamento)
);
CREATE TABLE sirtplc_d_tipo_sostituzione
( id_tipo_sostituzione    NUMERIC(2)              NOT NULL
, desc_tipo_sostituzione  CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipo_sostituzione_01 UNIQUE (desc_tipo_sostituzione)
, CONSTRAINT pk_d_tipo_sostituzione PRIMARY KEY (id_tipo_sostituzione)
);
CREATE TABLE sirtplc_d_tipologia_servizio
( id_tipologia_servizio     NUMERIC(2)              NOT NULL
, desc_tipologia_servizio   CHARACTER VARYING(100)  NOT NULL
, CONSTRAINT ak_d_tipologia_servizio_01 UNIQUE (desc_tipologia_servizio)
, CONSTRAINT pk_d_tipologia_servizio PRIMARY KEY (id_tipologia_servizio)
);
CREATE TABLE sirtplc_d_vincolo
( id_vincolo            NUMERIC(3)              NOT NULL
, desc_vincolo          CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_vincolo_01 UNIQUE (desc_vincolo, data_inizio_validita)
, CONSTRAINT pk_d_vincolo PRIMARY KEY (id_vincolo)
);
CREATE TABLE sirtplc_r_amb_tip_servizio
( id_amb_tip_servizio     NUMERIC(4)  NOT NULL
, id_ambito_servizio      NUMERIC(2)  NOT NULL
, id_tipologia_servizio   NUMERIC(2)  NOT NULL
, CONSTRAINT ak_r_amb_tip_servizio_01 UNIQUE (id_ambito_servizio, id_tipologia_servizio)
, CONSTRAINT pk_r_amb_tip_servizio PRIMARY KEY (id_amb_tip_servizio)
, CONSTRAINT fk_d_amb_tip_servizio_01 FOREIGN KEY (id_ambito_servizio) REFERENCES sirtplc_d_ambito_servizio(id_ambito_servizio)
, CONSTRAINT fk_d_amb_tip_servizio_02 FOREIGN KEY (id_tipologia_servizio) REFERENCES sirtplc_d_tipologia_servizio(id_tipologia_servizio)
);
CREATE TABLE sirtplc_t_contratto
( id_contratto                    NUMERIC(5)              NOT NULL DEFAULT NextVal('seq_t_contratto'::regclass)
, id_contratto_padre              NUMERIC(5)
, cod_id_nazionale                CHARACTER VARYING(15)
, cod_id_regionale                CHARACTER VARYING(15)   NOT NULL
, num_repertorio                  CHARACTER VARYING(15)
, id_sog_giurid_committente       NUMERIC(4)              NOT NULL
, id_sog_giurid_esecutore         NUMERIC(4)              NOT NULL
, id_tipo_sog_giurid_esec         NUMERIC(2)              NOT NULL
, id_tipo_raggr_sog_giurid_esec   NUMERIC(2)
, id_tipo_affidamento             NUMERIC(2)
, id_modalita_affidamento         NUMERIC(2)
, accordo_programma               CHARACTER VARYING(2000)
, gross_cost                      BOOL                    NOT NULL DEFAULT FALSE
, cig                             CHARACTER VARYING(20)
, data_stipula                    DATE
, desc_contratto                  CHARACTER VARYING(100)  NOT NULL
, id_bacino                       NUMERIC(2)
, data_inizio_validita            DATE
, data_fine_validita              DATE
, id_utente_aggiornamento         NUMERIC(6)              NOT NULL
, data_aggiornamento              TIMESTAMPTZ             NOT NULL  DEFAULT Now()
, CONSTRAINT chk_t_contratto_01 CHECK ((gross_cost = ANY (ARRAY[FALSE, TRUE])))
, CONSTRAINT pk_t_contratto PRIMARY KEY (id_contratto)
, CONSTRAINT fk_t_contratto_01 FOREIGN KEY (id_contratto_padre) REFERENCES sirtplc_t_contratto(id_contratto)
, CONSTRAINT fk_t_contratto_02 FOREIGN KEY (id_tipo_affidamento) REFERENCES sirtplc_d_tipo_affidamento(id_tipo_affidamento)
, CONSTRAINT fk_t_contratto_03 FOREIGN KEY (id_modalita_affidamento) REFERENCES sirtplc_d_modalita_affidamento(id_modalita_affidamento)
, CONSTRAINT fk_t_contratto_04 FOREIGN KEY (id_tipo_raggr_sog_giurid_esec) REFERENCES sirtplc_d_tipo_raggruppamento(id_tipo_raggruppamento)
, CONSTRAINT fk_t_contratto_05 FOREIGN KEY (id_bacino) REFERENCES sirtplc_d_bacino(id_bacino)
);
CREATE TABLE sirtplc_t_contratto_allegato
( id_contratto_allegato     NUMERIC(5)              NOT NULL  DEFAULT NextVal('seq_t_contratto_allegato'::regclass)
, id_contratto              NUMERIC(5)              NOT NULL
, id_tipo_documento         INT4                    NOT NULL
, note                      CHARACTER VARYING(200)
, allegato                  BYTEA
, nome_file                 CHARACTER VARYING(100)  NOT NULL
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMPTZ             NOT NULL  DEFAULT Now()
, CONSTRAINT pk_t_contratto_allegato PRIMARY KEY (id_contratto_allegato)
, CONSTRAINT fk_t_contratto_allegato_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
);
CREATE TABLE sirtplc_t_percorrenza
( id_percorrenza          NUMERIC(6)              NOT NULL DEFAULT NextVal('seq_t_percorrenza'::regclass)
, id_contratto            NUMERIC(5)              NOT NULL
, id_tipo_percorrenza     NUMERIC(2)              NOT NULL
, anno                    NUMERIC(4)              NOT NULL
, versione                NUMERIC(2)              NOT NULL
, chilometri_programmati  NUMERIC(7)
, data_compilazione       DATE                    NOT NULL
, note                    CHARACTER VARYING(500)
, CONSTRAINT pk_t_percorrenza PRIMARY KEY (id_percorrenza)
, CONSTRAINT fk_t_percorrenza_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
, CONSTRAINT fk_t_percorrenza_02 FOREIGN KEY (id_tipo_percorrenza) REFERENCES sirtplc_d_tipo_percorenza(id_tipo_percorrenza)
);
CREATE TABLE sirtplc_t_proroga_contratto
( id_proroga_contratto      NUMERIC(6)                NOT NULL  DEFAULT NextVal('seq_t_proroga_contratto'::regclass)
, id_contratto              NUMERIC(5)                NOT NULL
, atto_proroga              CHARACTER VARYING(2000)   NOT NULL
, data_fine_proroga         DATE
, id_utente_aggiornamento   NUMERIC(6)                NOT NULL
, data_aggiornamento        TIMESTAMPTZ               NOT NULL  DEFAULT now()
, CONSTRAINT ak_t_proroga_contratto_01 UNIQUE (id_contratto, data_fine_proroga)
, CONSTRAINT pk_t_proroga_contratto PRIMARY KEY (id_proroga_contratto)
, CONSTRAINT fk_t_proroga_contratto_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
);
CREATE TABLE sirtplc_r_contr_amb_tip_serv
( id_contratto          NUMERIC(5)  NOT NULL
, id_amb_tip_servizio   NUMERIC(4)  NOT NULL
, CONSTRAINT pk_r_contr_amb_tip_serv PRIMARY KEY (id_contratto, id_amb_tip_servizio)
, CONSTRAINT fk_r_contr_amb_tip_serv_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
, CONSTRAINT fk_r_contr_amb_tip_serv_02 FOREIGN KEY (id_amb_tip_servizio) REFERENCES sirtplc_r_amb_tip_servizio(id_amb_tip_servizio)
);
CREATE TABLE sirtplc_r_contratto_raggrupp
( id_contratto_raggrupp   NUMERIC(6)  NOT NULL  DEFAULT NextVal('seq_r_contratto_raggrupp'::regclass)
, id_contratto            NUMERIC(5)  NOT NULL
, id_soggetto_giuridico   NUMERIC(4)  NOT NULL
, capofila                BOOL        NOT NULL
, CONSTRAINT ak_r_contratto_raggrupp_01 UNIQUE (id_contratto, id_soggetto_giuridico, capofila)
, CONSTRAINT pk_r_contratto_raggrupp PRIMARY KEY (id_contratto_raggrupp)
, CONSTRAINT fk_r_contratto_raggrupp_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
);
CREATE TABLE sirtplc_r_contratto_vincolo
( id_contratto_vincolo  NUMERIC(6)  NOT NULL DEFAULT nextval('seq_r_contratto_vincolo'::regclass)
, id_contratto          NUMERIC(5)  NOT NULL
, id_vincolo            NUMERIC(3)  NOT NULL
, valore                NUMERIC(10) NOT NULL
, data_inizio_validita  DATE        NOT NULL DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT pk_r_contratto_vincolo PRIMARY KEY (id_contratto_vincolo)
, CONSTRAINT fk_r_contratto_vincolo_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
, CONSTRAINT fk_r_contratto_vincolo_02 FOREIGN KEY (id_vincolo) REFERENCES sirtplc_d_vincolo(id_vincolo)
);
CREATE TABLE sirtplc_r_sost_sog_contr
( id_sost_sog_contr           NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_r_sost_sog_contr'::regclass)
, id_contratto                NUMERIC(5)              NOT NULL
, id_sog_giurid_committente   NUMERIC(4)
, id_sog_giurid_esecutore     NUMERIC(4)
, atto_sostituzione           CHARACTER VARYING(2000)
, data_sostituzione           DATE
, id_tipo_sostituzione        NUMERIC(2)              NOT NULL
, data_fine_sostituzione      DATE
, CONSTRAINT pk_r_sost_sog_contr PRIMARY KEY (id_sost_sog_contr)
, CONSTRAINT fk_r_sost_sog_contr_01 FOREIGN KEY (id_contratto) REFERENCES sirtplc_t_contratto(id_contratto)
, CONSTRAINT fk_r_sost_sog_contr_02 FOREIGN KEY (id_tipo_sostituzione) REFERENCES sirtplc_d_tipo_sostituzione(id_tipo_sostituzione)
);
CREATE TABLE sirtplc_r_sost_sog_contr_raggr
( id_sost_sog_contr_raggr   NUMERIC(7)                NOT NULL  DEFAULT NextVal('seq_r_sost_sog_contr_raggr'::regclass)
, id_contratto_raggrupp     NUMERIC(6)                NOT NULL
, id_soggetto_giuridico     NUMERIC(4)                NOT NULL
, atto_sostituzione         CHARACTER VARYING(2000)
, data_sostituzione         DATE
, id_tipo_sostituzione      NUMERIC(2)                NOT NULL
, data_fine_sostituzione    DATE
, CONSTRAINT ak_r_sost_sog_contr_raggr_01 UNIQUE (id_contratto_raggrupp, id_soggetto_giuridico, data_sostituzione, id_tipo_sostituzione)
, CONSTRAINT pk_r_sost_sog_contr_raggr PRIMARY KEY (id_sost_sog_contr_raggr)
, CONSTRAINT fk_r_sost_sog_contr_raggr_01 FOREIGN KEY (id_contratto_raggrupp) REFERENCES sirtplc_r_contratto_raggrupp(id_contratto_raggrupp)
, CONSTRAINT fk_r_sost_sog_contr_raggr_02 FOREIGN KEY (id_tipo_sostituzione) REFERENCES sirtplc_d_tipo_sostituzione(id_tipo_sostituzione)
);




ALTER TABLE sirtplc_d_ambito_servizio       OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_bacino                OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_modalita_affidamento  OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_tipo_affidamento      OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_tipo_percorenza       OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_tipo_raggruppamento   OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_tipo_sostituzione     OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_tipologia_servizio    OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_d_vincolo               OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_amb_tip_servizio      OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_t_contratto             OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_t_contratto_allegato    OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_t_percorrenza           OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_t_proroga_contratto     OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_contr_amb_tip_serv    OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_contratto_raggrupp    OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_contratto_vincolo     OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_sost_sog_contr        OWNER TO sirtpl_contratti;
ALTER TABLE sirtplc_r_sost_sog_contr_raggr  OWNER TO sirtpl_contratti;




GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_ambito_servizio TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_ambito_servizio TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_ambito_servizio TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_ambito_servizio TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_ambito_servizio TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_bacino TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_bacino TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_bacino TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_bacino TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_bacino TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_modalita_affidamento TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_modalita_affidamento TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_modalita_affidamento TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_modalita_affidamento TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_modalita_affidamento TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_affidamento TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_affidamento TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_affidamento TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_tipo_affidamento TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_affidamento TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_percorenza TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_percorenza TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_percorenza TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_tipo_percorenza TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_percorenza TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_raggruppamento TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_raggruppamento TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_raggruppamento TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_tipo_raggruppamento TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_raggruppamento TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_sostituzione TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_sostituzione TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_sostituzione TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_tipo_sostituzione TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipo_sostituzione TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipologia_servizio TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipologia_servizio TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipologia_servizio TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_d_tipologia_servizio TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_tipologia_servizio TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_vincolo TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_vincolo TO sirtpl_trasv;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_vincolo TO sirtpl_contratti_rw;
GRANT SELECT                          ON TABLE sirtplc_d_vincolo TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_d_vincolo TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_amb_tip_servizio TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_amb_tip_servizio TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_amb_tip_servizio TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_amb_tip_servizio TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_amb_tip_servizio TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_t_contratto TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtplc_t_contratto TO sirtpl_aziende;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto_allegato TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto_allegato TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto_allegato TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_t_contratto_allegato TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_contratto_allegato TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_percorrenza TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_percorrenza TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_percorrenza TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_t_percorrenza TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_percorrenza TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_proroga_contratto TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_proroga_contratto TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_proroga_contratto TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_t_proroga_contratto TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_t_proroga_contratto TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contr_amb_tip_serv TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contr_amb_tip_serv TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contr_amb_tip_serv TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_contr_amb_tip_serv TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contr_amb_tip_serv TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_raggrupp TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_raggrupp TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_raggrupp TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_contratto_raggrupp TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtplc_r_contratto_raggrupp TO sirtpl_aziende;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_raggrupp TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_vincolo TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_vincolo TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_vincolo TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_contratto_vincolo TO sirtpl_trasv_ro;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_contratto_vincolo TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_sost_sog_contr TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtplc_r_sost_sog_contr TO sirtpl_aziende;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr TO rebus;
----
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr_raggr TO sirtpl_contratti_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr_raggr TO sirtpl_trasv_rw;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr_raggr TO sirtpl_trasv;
GRANT SELECT                          ON TABLE sirtplc_r_sost_sog_contr_raggr TO sirtpl_trasv_ro;
GRANT SELECT                          ON TABLE sirtplc_r_sost_sog_contr_raggr TO sirtpl_aziende;
GRANT DELETE, UPDATE, SELECT, INSERT  ON TABLE sirtplc_r_sost_sog_contr_raggr TO rebus;
