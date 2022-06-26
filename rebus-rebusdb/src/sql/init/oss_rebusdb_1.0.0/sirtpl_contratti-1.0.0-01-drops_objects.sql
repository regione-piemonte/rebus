DROP FUNCTION IF EXISTS fnc_trg_t_proroga_contratto_bu()      CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_proroga_contratto_bi()      CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_contratto_bu()              CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_contratto_bi()              CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_contratto_bd()              CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_contratto_allegato_bu()     CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_t_contratto_allegato_bi()     CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sost_sog_contr_raggr_bu()   CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sost_sog_contr_raggr_bi()   CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sost_sog_contr_bu()         CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_sost_sog_contr_bi()         CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_contratto_raggrupp_bu()     CASCADE;
DROP FUNCTION IF EXISTS fnc_trg_r_contratto_raggrupp_bi()     CASCADE;
DROP FUNCTION IF EXISTS fnc_esisteidcontratto ( NUMERIC
                                              )               CASCADE;



DROP TABLE IF EXISTS sirtplc_d_ambito_servizio        CASCADE;
DROP TABLE IF EXISTS sirtplc_d_bacino                 CASCADE;
DROP TABLE IF EXISTS sirtplc_d_modalita_affidamento   CASCADE;
DROP TABLE IF EXISTS sirtplc_d_tipo_affidamento       CASCADE;
DROP TABLE IF EXISTS sirtplc_d_tipo_percorenza        CASCADE;
DROP TABLE IF EXISTS sirtplc_d_tipo_raggruppamento    CASCADE;
DROP TABLE IF EXISTS sirtplc_d_tipo_sostituzione      CASCADE;
DROP TABLE IF EXISTS sirtplc_d_tipologia_servizio     CASCADE;
DROP TABLE IF EXISTS sirtplc_d_vincolo                CASCADE;
DROP TABLE IF EXISTS sirtplc_r_amb_tip_servizio       CASCADE;
DROP TABLE IF EXISTS sirtplc_t_contratto              CASCADE;
DROP TABLE IF EXISTS sirtplc_t_contratto_allegato     CASCADE;
DROP TABLE IF EXISTS sirtplc_t_percorrenza            CASCADE;
DROP TABLE IF EXISTS sirtplc_t_proroga_contratto      CASCADE;
DROP TABLE IF EXISTS sirtplc_r_contratto_raggrupp     CASCADE;
DROP TABLE IF EXISTS sirtplc_r_contratto_vincolo      CASCADE;
DROP TABLE IF EXISTS sirtplc_r_sost_sog_contr         CASCADE;
DROP TABLE IF EXISTS sirtplc_r_sost_sog_contr_raggr   CASCADE;



DROP SEQUENCE IF EXISTS seq_r_contratto_raggrupp;
DROP SEQUENCE IF EXISTS seq_r_contratto_vincolo;
DROP SEQUENCE IF EXISTS seq_r_sost_sog_contr;
DROP SEQUENCE IF EXISTS seq_r_sost_sog_contr_raggr;
DROP SEQUENCE IF EXISTS seq_t_contratto;
DROP SEQUENCE IF EXISTS seq_t_contratto_allegato;
DROP SEQUENCE IF EXISTS seq_t_percorrenza;
DROP SEQUENCE IF EXISTS seq_t_proroga_contratto;
