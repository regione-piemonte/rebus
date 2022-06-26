CREATE OR REPLACE
FUNCTION fnc_esistecontestoattivo  ( pIdContesto   IN  NUMERIC
                                                ) RETURNS BOOLEAN LANGUAGE plpgsql AS
$function$
DECLARE
  bFound  BOOLEAN;
BEGIN
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bFound
    FROM  sirtpl_d_contesto
    WHERE id_contesto = pIdContesto;
  ----
  RETURN bFound;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_esisteidutenteattivo  ( pIdUtente   IN  NUMERIC
                                                ) RETURNS BOOLEAN LANGUAGE plpgsql AS
$function$
DECLARE
  bFound  BOOLEAN;
BEGIN
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bFound
    FROM  sirtpl_t_utente
    WHERE id_utente = pIdUtente
      AND CURRENT_DATE BETWEEN data_inizio_validita AND COALESCE(data_fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY'));
  ----
  RETURN bFound;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_esisteidutentesoggiuridattivo ( pIdUtenteSogGiurid  IN  NUMERIC
                                                        ) RETURNS BOOLEAN LANGUAGE plpgsql AS
$function$
DECLARE
  bFound  BOOLEAN;
BEGIN
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bFound
    FROM  sirtpl_r_utente_sog_giurid
    WHERE id_utente_sog_giurid = pIdUtenteSogGiurid
      AND CURRENT_DATE BETWEEN data_inizio_validita AND COALESCE(data_fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY'));
  ----
  RETURN bFound;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_d_contesto_bd() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdContestoUtilizzato   BOOLEAN;
BEGIN
  -- REBUS_D_TIPO_DOCUMENTO
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdContestoUtilizzato
    FROM  rebus_d_tipo_documento
    WHERE id_contesto = OLD.id_contesto;
  IF bIdContestoUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il contesto risulta utilizzato dalla tabella REBUS_D_TIPO_DOCUMENTO (campo ID_CONTESTO)';
  END IF;
  -- REBUS_D_TIPO_MESSAGGIO
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdContestoUtilizzato
    FROM  rebus_d_tipo_messaggio
    WHERE id_contesto = OLD.id_contesto;
  IF bIdContestoUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè il contesto risulta utilizzato dalla tabella REBUS_D_TIPO_MESSAGGIO (campo ID_CONTESTO)';
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_utente_sog_giurid_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
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
FUNCTION fnc_trg_r_utente_sog_giurid_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
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
FUNCTION fnc_trg_t_utente_bd() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
DECLARE
  bIdUtenteUtilizzato   BOOLEAN;
BEGIN
  -- SIRTPL_AZIENDE
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_r_sog_giurid_deposito
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_R_SOG_GIURID_DEPOSITO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_r_unione_sog_giurid
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_R_UNIONE_SOG_GIURID (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_t_dato_bancario
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_T_DATO_BANCARIO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_t_deposito
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_T_DEPOSITO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_t_soggetto_giuridico
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_T_SOGGETTO_GIURIDICO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_t_soggetto_giuridico_pi
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_T_SOGGETTO_GIURIDICO_PI (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtpla_t_soggetto_giuridico_pi
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLA_T_SOGGETTO_GIURIDICO_PI (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  -- SIRTPL_CONTRATTI
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_contratto
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLC_T_CONTRATTO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_contratto_allegato
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLC_T_CONTRATTO_ALLEGATO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  sirtplc_t_proroga_contratto
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella SIRTPLC_T_PROROGA_CONTRATTO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  -- REBUS
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_r_proc_contratto
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_R_PROC_CONTRATTO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_r_proc_documento
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_R_PROC_DOCUMENTO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_r_proc_veicolo
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_R_PROC_VEICOLO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_t_documento
    WHERE id_utente_inserimento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_T_DOCUMENTO (campo ID_UTENTE_INSERIMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_t_iter_procedimento
    WHERE id_utente_sog_giurid = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_T_ITER_PROCEDIMENTO (campo ID_UTENTE_SOG_GIURID)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebusp_t_procedimento
    WHERE id_utente_aggiornamento = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUSP_T_PROCEDIMENTO (campo ID_UTENTE_AGGIORNAMENTO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_r_doc_variaz_autobus
    WHERE fk_utente = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_R_DOC_VARIAZ_AUTOBUS (campo FK_UTENTE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_t_messaggi
    WHERE fk_utente_mittente = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_T_MESSAGGI (campo FK_UTENTE_MITTENTE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_t_messaggi
    WHERE fk_utente_destinatario = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_T_MESSAGGI (campo FK_UTENTE_DESTINATARIO)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_t_storia_variaz_autobus
    WHERE fk_utente = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_T_STORIA_VARIAZ_AUTOBUS (campo FK_UTENTE)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_t_storia_variaz_autobus
    WHERE fk_utente_storia = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_T_STORIA_VARIAZ_AUTOBUS (campo FK_UTENTE_STORIA)';
  END IF;
  ----
  SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
    INTO  bIdUtenteUtilizzato
    FROM  rebus_t_variaz_autobus
    WHERE fk_utente = OLD.id_utente;
  IF bIdUtenteUtilizzato THEN
    RAISE EXCEPTION 'Cancellazione impedita perchè l''utente risulta utilizzato dalla tabella REBUS_T_VARIAZ_AUTOBUS (campo FK_UTENTE)';
  END IF;
  ----
  RETURN NEW;
END;
$function$
;




ALTER FUNCTION fnc_esistecontestoattivo ( NUMERIC )           OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_esisteidutenteattivo ( NUMERIC )           OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC )  OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_trg_d_contesto_bd()                        OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_trg_r_utente_sog_giurid_bi()               OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_trg_r_utente_sog_giurid_bu()               OWNER TO sirtpl_trasv;
ALTER FUNCTION fnc_trg_t_utente_bd()                          OWNER TO sirtpl_trasv;




GRANT ALL ON FUNCTION fnc_esistecontestoattivo ( NUMERIC ) TO public;
GRANT ALL ON FUNCTION fnc_esistecontestoattivo ( NUMERIC ) TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_esistecontestoattivo ( NUMERIC ) TO sirtpl_trasv_ro;
GRANT ALL ON FUNCTION fnc_esistecontestoattivo ( NUMERIC ) TO rebus;
----
GRANT ALL ON FUNCTION fnc_esisteidutenteattivo ( NUMERIC ) TO public;
GRANT ALL ON FUNCTION fnc_esisteidutenteattivo ( NUMERIC ) TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_esisteidutenteattivo ( NUMERIC ) TO sirtpl_trasv_ro;
GRANT ALL ON FUNCTION fnc_esisteidutenteattivo ( NUMERIC ) TO sirtpl_aziende;
GRANT ALL ON FUNCTION fnc_esisteidutenteattivo ( NUMERIC ) TO sirtpl_contratti;
----
GRANT ALL ON FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC ) TO public;
GRANT ALL ON FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC ) TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC ) TO sirtpl_trasv_ro;
GRANT ALL ON FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC ) TO sirtpl_aziende;
GRANT ALL ON FUNCTION fnc_esisteidutentesoggiuridattivo ( NUMERIC ) TO sirtpl_contratti;
----
GRANT ALL ON FUNCTION fnc_trg_d_contesto_bd() TO public;
GRANT ALL ON FUNCTION fnc_trg_d_contesto_bd() TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_trg_d_contesto_bd() TO sirtpl_trasv_ro;
----
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bi() TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bi() TO sirtpl_trasv_ro;
----
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bu() TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_trg_r_utente_sog_giurid_bu() TO sirtpl_trasv_ro;
----
GRANT ALL ON FUNCTION fnc_trg_t_utente_bd() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_utente_bd() TO sirtpl_trasv_rw;
GRANT ALL ON FUNCTION fnc_trg_t_utente_bd() TO sirtpl_trasv_ro;
