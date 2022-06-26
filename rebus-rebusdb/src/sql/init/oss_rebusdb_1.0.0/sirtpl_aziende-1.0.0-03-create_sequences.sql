CREATE SEQUENCE seq_r_unione_sog_giurid   INCREMENT BY 1 MINVALUE 1 MAXVALUE  99999 START 1;
CREATE SEQUENCE seq_t_dato_bancario       INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999 START 1;
CREATE SEQUENCE seq_t_deposito            INCREMENT BY 1 MINVALUE 1 MAXVALUE   9999 START 1;
CREATE SEQUENCE seq_t_soggetto_giuridico  INCREMENT BY 1 MINVALUE 1 MAXVALUE   9999 START 1;



ALTER SEQUENCE seq_r_unione_sog_giurid  OWNER TO sirtpl_aziende;
ALTER SEQUENCE seq_t_dato_bancario      OWNER TO sirtpl_aziende;
ALTER SEQUENCE seq_t_deposito           OWNER TO sirtpl_aziende;
ALTER SEQUENCE seq_t_soggetto_giuridico OWNER TO sirtpl_aziende;



GRANT ALL           ON SEQUENCE seq_r_unione_sog_giurid TO sirtpl_aziende_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_r_unione_sog_giurid TO sirtpl_trasv_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_r_unione_sog_giurid TO sirtpl_trasv;
----
GRANT ALL           ON SEQUENCE seq_t_dato_bancario TO sirtpl_aziende_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_dato_bancario TO sirtpl_trasv_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_dato_bancario TO sirtpl_trasv;
----
GRANT ALL           ON SEQUENCE seq_t_deposito TO sirtpl_aziende;
GRANT ALL           ON SEQUENCE seq_t_deposito TO sirtpl_aziende_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_deposito TO sirtpl_trasv_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_deposito TO sirtpl_trasv;
----
GRANT ALL           ON SEQUENCE seq_t_soggetto_giuridico TO sirtpl_aziende;
GRANT ALL           ON SEQUENCE seq_t_soggetto_giuridico TO sirtpl_aziende_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_soggetto_giuridico TO sirtpl_trasv_rw;
GRANT SELECT, USAGE ON SEQUENCE seq_t_soggetto_giuridico TO sirtpl_trasv;
