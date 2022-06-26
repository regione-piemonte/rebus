DROP FUNCTION IF EXISTS fnc_trg_t_utente_bd()                         CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_utente_sog_giurid_bu()              CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_utente_sog_giurid_bi()              CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_d_contesto_bd()                       CASCADE;
DROP FUNCTION IF EXISTS fnc_esisteidutentesoggiuridattivo ( NUMERIC
                                                          )           CASCADE;
DROP FUNCTION IF EXISTS fnc_esisteidutenteattivo  ( NUMERIC
                                                  )                   CASCADE;
DROP FUNCTION IF EXISTS fnc_esistecontestoattivo  ( NUMERIC
                                                  )                   CASCADE;



DROP TABLE IF EXISTS sirtpl_d_contesto            CASCADE;
DROP TABLE IF EXISTS sirtpl_d_nazione             CASCADE;
DROP TABLE IF EXISTS sirtpl_d_toponimo            CASCADE;
DROP TABLE IF EXISTS sirtpl_t_utente              CASCADE;
DROP TABLE IF EXISTS sirtpl_d_regione             CASCADE;
DROP TABLE IF EXISTS sirtpl_r_utente_sog_giurid   CASCADE;
DROP TABLE IF EXISTS sirtpl_s_nazione             CASCADE;
DROP TABLE IF EXISTS sirtpl_s_regione             CASCADE;
DROP TABLE IF EXISTS sirtpl_d_provincia           CASCADE;
DROP TABLE IF EXISTS sirtpl_s_provincia           CASCADE;
DROP TABLE IF EXISTS sirtpl_d_comune              CASCADE;
DROP TABLE IF EXISTS sirtpl_s_comune              CASCADE;
