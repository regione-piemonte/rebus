DROP FUNCTION IF EXISTS fnc_trg_t_soggetto_giuridico_bu()           CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_soggetto_giuridico_bi()           CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_soggetto_giuridico_bd()           CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_deposito_bu()                     CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_deposito_bi()                     CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_dato_bancario_bu()                CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_dato_bancario_bi()                CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_unione_sog_giurid_bu()            CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_unione_sog_giurid_bi()            CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sog_giurid_deposito_bu()          CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sog_giurid_deposito_bi()          CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_bi_sirtpla_t_soggetto_giuridico()   CASCADE;
DROP FUNCTION IF EXISTS fnc_esisteidsoggettogiuridico ( NUMERIC
                                                      )             CASCADE;



DROP TABLE IF EXISTS sirtpla_d_natura_giuridica     CASCADE;
DROP TABLE IF EXISTS sirtpla_d_ruolo_sog_giuridico  CASCADE;
DROP TABLE IF EXISTS sirtpla_d_tipo_ente            CASCADE;
DROP TABLE IF EXISTS sirtpla_d_tipo_unione_sg       CASCADE;
DROP TABLE IF EXISTS sirtpla_t_deposito             CASCADE;
DROP TABLE IF EXISTS sirtpla_d_tipo_sog_giuridico   CASCADE;
DROP TABLE IF EXISTS sirtpla_t_soggetto_giuridico   CASCADE;
DROP TABLE IF EXISTS sirtpla_r_sog_giurid_deposito  CASCADE;
DROP TABLE IF EXISTS sirtpla_r_unione_sog_giurid    CASCADE;
DROP TABLE IF EXISTS sirtpla_t_dato_bancario        CASCADE;




DROP SEQUENCE IF EXISTS seq_r_unione_sog_giurid;
DROP SEQUENCE IF EXISTS seq_t_dato_bancario;
DROP SEQUENCE IF EXISTS seq_t_deposito;
DROP SEQUENCE IF EXISTS seq_t_soggetto_giuridico;
