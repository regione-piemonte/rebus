CREATE OR REPLACE
FUNCTION fnc_esisteidcontratto ( pIdContratto IN NUMERIC
                               ) RETURNS BOOLEAN LANGUAGE plpgsql AS
$function$
DECLARE
  bFound  BOOLEAN;
BEGIN
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bFound
    FROM  sirtplc_t_contratto
    WHERE id_contratto = pIdContratto;
  ----
  RETURN bFound;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_contratto_raggrupp_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_soggetto_giuridico)
  THEN
    RAISE EXCEPTION 'ID_SOGGETTO_GIURIDICO % non censito', NEW.id_soggetto_giuridico;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_contratto_raggrupp_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_soggetto_giuridico,-1) <> COALESCE(OLD.id_soggetto_giuridico,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_soggetto_giuridico)
    THEN
      RAISE EXCEPTION 'ID_SOGGETTO_GIURIDICO % non censito', NEW.id_soggetto_giuridico;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sost_sog_contr_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NEW.id_sog_giurid_committente IS NOT NULL
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_committente)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_COMMITTENTE % non censito', NEW.id_sog_giurid_committente;
    END IF;
  END IF;
  ----
  IF NEW.id_sog_giurid_esecutore IS NOT NULL
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_esecutore)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_ESECUTORE % non censito', NEW.id_sog_giurid_esecutore;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sost_sog_contr_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NEW.id_sog_giurid_committente IS NOT NULL
      AND COALESCE(NEW.id_sog_giurid_committente,-1) <> COALESCE(OLD.id_sog_giurid_committente,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_committente)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_COMMITTENTE % non censito', NEW.id_sog_giurid_committente;
    END IF;
  END IF;
  ----
  IF NEW.id_sog_giurid_esecutore IS NOT NULL
      AND COALESCE(NEW.id_sog_giurid_esecutore,-1) <> COALESCE(OLD.id_sog_giurid_esecutore,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_esecutore)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_ESECUTORE % non censito', NEW.id_sog_giurid_esecutore;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sost_sog_contr_raggr_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_soggetto_giuridico)
  THEN
    RAISE EXCEPTION 'ID_SOGGETTO_GIURIDICO % non censito', NEW.id_soggetto_giuridico;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_sost_sog_contr_raggr_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF COALESCE(NEW.id_soggetto_giuridico,-1) <> COALESCE(OLD.id_soggetto_giuridico,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_soggetto_giuridico)
    THEN
      RAISE EXCEPTION 'ID_SOGGETTO_GIURIDICO % non censito', NEW.id_soggetto_giuridico;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_contratto_allegato_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
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
FUNCTION fnc_trg_t_contratto_allegato_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
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
FUNCTION fnc_trg_t_contratto_bd() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdContrattoUtilizzato  BOOLEAN;
BEGIN
  -- REBUS
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdContrattoUtilizzato
    FROM  rebusp_r_proc_contratto
    WHERE id_contratto = OLD.id_contratto;
  IF bIdContrattoUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il contratto risulta utilizzato dalla tabella REBUSP_R_PROC_CONTRATTO (campo ID_CONTRATTO)';
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_contratto_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteEsistente  BOOLEAN;
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_committente)
  THEN
    RAISE EXCEPTION 'ID_SOG_GIURID_COMMITTENTE % non censito', NEW.id_sog_giurid_committente;
  END IF;
  ----
  IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_esecutore)
  THEN
    RAISE EXCEPTION 'ID_SOG_GIURID_ESECUTORE % non censito', NEW.id_sog_giurid_esecutore;
  END IF;
  ----
  NEW.cod_id_regionale := 'CDS' || LPAD(NEW.id_contratto::VARCHAR,4,'0');
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_contratto_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
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
  IF COALESCE(NEW.id_sog_giurid_committente,-1) <> COALESCE(OLD.id_sog_giurid_committente,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_committente)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_COMMITTENTE % non censito', NEW.id_sog_giurid_committente;
    END IF;
  END IF;
  ----
  IF COALESCE(NEW.id_sog_giurid_esecutore,-1) <> COALESCE(OLD.id_sog_giurid_esecutore,-1)
  THEN
    IF NOT FNC_EsisteIdSoggettoGiuridico(NEW.id_sog_giurid_esecutore)
    THEN
      RAISE EXCEPTION 'ID_SOG_GIURID_ESECUTORE % non censito', NEW.id_sog_giurid_esecutore;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_proroga_contratto_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
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
FUNCTION fnc_trg_t_proroga_contratto_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
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



ALTER FUNCTION fnc_esisteidcontratto ( NUMERIC )    OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_contratto_raggrupp_bi()    OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_contratto_raggrupp_bu()    OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_sost_sog_contr_bi()        OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_sost_sog_contr_bu()        OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_sost_sog_contr_raggr_bi()  OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_r_sost_sog_contr_raggr_bu()  OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_contratto_allegato_bi()    OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_contratto_allegato_bu()    OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_contratto_bd()             OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_contratto_bi()             OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_contratto_bu()             OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_proroga_contratto_bi()     OWNER TO sirtpl_contratti;
ALTER FUNCTION fnc_trg_t_proroga_contratto_bu()     OWNER TO sirtpl_contratti;



GRANT ALL ON FUNCTION fnc_esisteidcontratto(NUMERIC) TO public;
GRANT ALL ON FUNCTION fnc_esisteidcontratto(NUMERIC) TO sirtpl_contratti_rw;
GRANT ALL ON FUNCTION fnc_esisteidcontratto(NUMERIC) TO rebus;
----
GRANT ALL ON FUNCTION fnc_trg_r_contratto_raggrupp_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_contratto_raggrupp_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_contratto_raggrupp_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_contratto_raggrupp_bu() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_bu() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_raggr_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_raggr_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_raggr_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_sost_sog_contr_raggr_bu() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contratto_allegato_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contratto_allegato_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contratto_allegato_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contratto_allegato_bu() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bd() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bd() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contratto_bu() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_proroga_contratto_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_proroga_contratto_bi() TO sirtpl_contratti_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_proroga_contratto_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_proroga_contratto_bu() TO sirtpl_contratti_rw;
