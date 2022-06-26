CREATE OR REPLACE
FUNCTION fnc_esisteidsoggettogiuridico  ( pIdSoggettoGiuridico  IN  NUMERIC
                                        ) RETURNS BOOLEAN LANGUAGE plpgsql AS
$function$
DECLARE
  bFound  BOOLEAN;
BEGIN
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bFound
    FROM  sirtpla_t_soggetto_giuridico
    WHERE id_soggetto_giuridico = pIdSoggettoGiuridico;
  ----
  RETURN bFound;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_bi_sirtpla_t_soggetto_giuridico() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  nCodTipoSogGiuridico  sirtpla_d_tipo_sog_giuridico.cod_tipo_sog_giuridico%TYPE;
  bValidoPerSogGiurid   sirtpla_d_tipo_sog_giuridico.valido_per_sog_giurid%TYPE;
BEGIN
  SELECT  cod_tipo_sog_giuridico
        , valido_per_sog_giurid
    INTO  nCodTipoSogGiuridico
        , bValidoPerSogGiurid
    FROM  sirtpla_d_tipo_sog_giuridico
    WHERE id_tipo_sog_giuridico = NEW.id_tipo_sog_giuridico;
  IF nCodTipoSogGiuridico = 'A' THEN
    NEW.cod_id_regionale := 'SGA' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
  ELSEIF nCodTipoSogGiuridico = 'E' THEN
    IF bValidoPerSogGiurid THEN
      NEW.cod_id_regionale := 'SGE' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
    ELSE
      NEW.cod_id_regionale := 'SGD' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sog_giurid_deposito_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sog_giurid_deposito_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_unione_sog_giurid_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_unione_sog_giurid_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_dato_bancario_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_dato_bancario_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_deposito_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_deposito_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_soggetto_giuridico_bd() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteUtilizzato   BOOLEAN;
BEGIN
  -- SIRTPL_CONTRATTI
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_r_contratto_raggrupp
    WHERE id_soggetto_giuridico = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_R_CONTRATTO_RAGGRUPP (campo ID_SOGGETTO_GIURIDICO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_r_sost_sog_contr
    WHERE id_sog_giurid_committente = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_R_SOST_SOG_CONTR (campo ID_SOG_GIURID_COMMITTENTE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_r_sost_sog_contr
    WHERE id_sog_giurid_esecutore = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_R_SOST_SOG_CONTR (campo ID_SOG_GIURID_ESECUTORE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_r_sost_sog_contr_raggr
    WHERE id_soggetto_giuridico = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_R_SOST_SOG_CONTR_RAGGR (campo ID_SOGGETTO_GIURIDICO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_contratto
    WHERE id_sog_giurid_committente = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_T_CONTRATTO (campo ID_SOG_GIURID_COMMITTENTE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_contratto
    WHERE id_sog_giurid_esecutore = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_T_CONTRATTO (campo ID_SOG_GIURID_ESECUTORE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_contratto
    WHERE id_sog_giurid_esecutore = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPLC_T_CONTRATTO (campo ID_SOG_GIURID_ESECUTORE)';
  END IF;
  ----
  -- SIRTPL_TRASV
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpl_r_utente_sog_giurid
    WHERE id_soggetto_giuridico = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPL_R_UTENTE_SOG_GIURID (campo ID_SOGGETTO_GIURIDICO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpl_r_utente_sog_giurid
    WHERE id_soggetto_giuridico = OLD.id_soggetto_giuridico;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il soggetto giuridico risulta utilizzato dalla tabella SIRTPL_R_UTENTE_SOG_GIURID (campo ID_SOGGETTO_GIURIDICO)';
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_soggetto_giuridico_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente    BOOLEAN;
  nCodTipoSogGiuridico  sirtpla_d_tipo_sog_giuridico.cod_tipo_sog_giuridico%TYPE;
  bValidoPerSogGiurid   sirtpla_d_tipo_sog_giuridico.valido_per_sog_giurid%TYPE;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  SELECT  cod_tipo_sog_giuridico
        , valido_per_sog_giurid
    INTO  nCodTipoSogGiuridico
        , bValidoPerSogGiurid
    FROM  sirtpla_d_tipo_sog_giuridico
    WHERE id_tipo_sog_giuridico = NEW.id_tipo_sog_giuridico;
  IF nCodTipoSogGiuridico = 'A' THEN
    NEW.cod_id_regionale := 'SGA' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
  ELSEIF nCodTipoSogGiuridico = 'E' THEN
    IF bValidoPerSogGiurid THEN
      NEW.cod_id_regionale := 'SGE' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
    ELSE
      NEW.cod_id_regionale := 'SGD' || LPAD(NEW.id_soggetto_giuridico::VARCHAR,4,'0');
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_soggetto_giuridico_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;




ALTER FUNCTION fnc_esisteidsoggettogiuridico ( NUMERIC )  OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_bi_sirtpla_t_soggetto_giuridico()  OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_r_sog_giurid_deposito_bi()         OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_r_sog_giurid_deposito_bu()         OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_r_unione_sog_giurid_bi()           OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_r_unione_sog_giurid_bu()           OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_dato_bancario_bi()               OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_dato_bancario_bu()               OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_deposito_bi()                    OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_deposito_bu()                    OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_soggetto_giuridico_bd()          OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_soggetto_giuridico_bi()          OWNER TO sirtpl_aziende;
ALTER FUNCTION fnc_trg_t_soggetto_giuridico_bu()          OWNER TO sirtpl_aziende;




GRANT ALL ON FUNCTION fnc_esisteidsoggettogiuridico ( NUMERIC ) TO public;
GRANT ALL ON FUNCTION fnc_esisteidsoggettogiuridico ( NUMERIC ) TO sirtpl_aziende_rw;
GRANT ALL ON FUNCTION fnc_esisteidsoggettogiuridico ( NUMERIC ) TO sirtpl_contratti;
----
GRANT ALL ON FUNCTION fnc_trg_bi_sirtpla_t_soggetto_giuridico() TO public;
GRANT ALL ON FUNCTION fnc_trg_bi_sirtpla_t_soggetto_giuridico() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sog_giurid_deposito_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sog_giurid_deposito_bi() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sog_giurid_deposito_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sog_giurid_deposito_bu() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_unione_sog_giurid_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_unione_sog_giurid_bi() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_unione_sog_giurid_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_unione_sog_giurid_bu() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_dato_bancario_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_dato_bancario_bi() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_dato_bancario_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_dato_bancario_bu() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_deposito_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_deposito_bi() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_deposito_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_deposito_bu() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bd() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bd() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bi() TO sirtpl_aziende_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_soggetto_giuridico_bu() TO sirtpl_aziende_rw;
