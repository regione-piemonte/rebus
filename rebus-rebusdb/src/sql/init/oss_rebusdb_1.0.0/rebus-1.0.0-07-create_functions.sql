CREATE OR REPLACE
FUNCTION BonificaDispositivi() RETURNS INTEGER LANGUAGE plpgsql AS
$function$
DECLARE
  dispositivo                   RECORD;
  autobus                       RECORD;
  bEsiste                       BOOLEAN;
  sAltriDispositiviPrevenzInc   rebus_t_variaz_autobus.altri_dispositivi_prevenz_inc%TYPE;
  bSporcizia                    BOOLEAN;
BEGIN
/*
  DROP TABLE IF EXISTS z_rebus_t_variaz_autobus;
  CREATE TABLE z_rebus_t_variaz_autobus AS SELECT * FROM rebus_t_variaz_autobus;
  ----
  DROP TABLE IF EXISTS z_rebus_r_varautobus_dp;
  CREATE TABLE z_rebus_r_varautobus_dp AS SELECT * FROM rebus_r_varautobus_dp;
  ALTER TABLE z_rebus_r_varautobus_dp ADD CONSTRAINT pk_z_rebus_r_varautobus_dp PRIMARY KEY (id_dispositivo, id_variaz_autobus);
*/
    ----
  FOR dispositivo IN
  SELECT  id_dispositivo
        , UPPER(TRIM(descrizione))    AS  desc_dispositivo
        , LENGTH(TRIM(descrizione))   AS  lunghezza
    FROM  rebus_d_dispositivi_prevenz
    WHERE UPPER(TRIM(descrizione)) NOT IN ('ALTRO','ND','NO')
  LOOP
    FOR autobus IN
    SELECT  id_variaz_autobus 
          , POSITION(dispositivo.desc_dispositivo IN REPLACE(UPPER(TRIM(altri_dispositivi_prevenz_inc)),'ARS','ASR'))   AS  posizione
      FROM  rebus_t_variaz_autobus
      WHERE POSITION(UPPER(TRIM(dispositivo.desc_dispositivo)) IN REPLACE(UPPER(TRIM(altri_dispositivi_prevenz_inc)),'ARS','ASR')) <> 0
    LOOP
      SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
        INTO  bEsiste
        FROM  rebus_r_varautobus_dp
        WHERE id_dispositivo = dispositivo.id_dispositivo
          AND id_variaz_autobus = autobus.id_variaz_autobus;
      IF NOT bEsiste
      THEN
        INSERT INTO rebus_r_varautobus_dp (id_dispositivo, id_variaz_autobus) VALUES (dispositivo.id_dispositivo, autobus.id_variaz_autobus);
      END IF;
      UPDATE  rebus_t_variaz_autobus
        SET altri_dispositivi_prevenz_inc = TRIM(SUBSTR(altri_dispositivi_prevenz_inc,1,autobus.posizione-1) || SUBSTR(altri_dispositivi_prevenz_inc,autobus.posizione+dispositivo.lunghezza))
        WHERE id_variaz_autobus = autobus.id_variaz_autobus;
    END LOOP;
  END LOOP;
  ----
  -- Pulizia del campo
  LOOP
    bSporcizia = FALSE;
    ----
    FOR autobus IN
    SELECT  id_variaz_autobus 
          , TRIM(altri_dispositivi_prevenz_inc)     AS  altri_dispositivi_prevenz
      FROM  rebus_t_variaz_autobus
      WHERE altri_dispositivi_prevenz_inc IS NOT NULL
    LOOP
      sAltriDispositiviPrevenzInc := autobus.altri_dispositivi_prevenz;
      ----
      IF SUBSTR(sAltriDispositiviPrevenzInc,1,1) IN ('-',',')
      THEN
        sAltriDispositiviPrevenzInc := SUBSTR(autobus.altri_dispositivi_prevenz,2);
        bSporcizia = TRUE;
      END IF;
      ----
      IF SUBSTR(sAltriDispositiviPrevenzInc,LENGTH(sAltriDispositiviPrevenzInc)) IN ('-',',')
      THEN
        sAltriDispositiviPrevenzInc := SUBSTR(autobus.altri_dispositivi_prevenz,1,LENGTH(sAltriDispositiviPrevenzInc)-1);
        bSporcizia = TRUE;
      END IF;
      ----
      UPDATE  rebus_t_variaz_autobus
        SET altri_dispositivi_prevenz_inc = sAltriDispositiviPrevenzInc
        WHERE id_variaz_autobus = autobus.id_variaz_autobus;
    END LOOP;
    ----
    EXIT WHEN NOT bSporcizia;
  END LOOP;
  ----
  -- Verifico le mancanze dell'informazione 'ALTRO'
  FOR autobus IN
  SELECT  id_variaz_autobus 
        , altri_dispositivi_prevenz_inc
    FROM  rebus_t_variaz_autobus
    WHERE altri_dispositivi_prevenz_inc IS NOT NULL
      AND altri_dispositivi_prevenz_inc <> 'ND'
  LOOP
    SELECT  CASE WHEN COUNT(1) = 0 THEN FALSE ELSE TRUE END
      INTO  bEsiste
      FROM  rebus_r_varautobus_dp
      WHERE id_dispositivo = 8
        AND id_variaz_autobus = autobus.id_variaz_autobus;
    IF NOT bEsiste
    THEN
      INSERT INTO rebus_r_varautobus_dp (id_dispositivo, id_variaz_autobus) VALUES (8, autobus.id_variaz_autobus);
    END IF;
  END LOOP;
  ----
  RETURN 0;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_d_tipo_documento_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteContestoAttivo(NEW.id_contesto)
  THEN
    RAISE EXCEPTION 'ID_CONTESTO % non censito', NEW.id_contesto;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_d_tipo_documento_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.id_contesto,-1) <> COALESCE(OLD.id_contesto,-1)
  THEN
    IF NOT FNC_EsisteContestoAttivo(NEW.id_contesto)
    THEN
      RAISE EXCEPTION 'ID_CONTESTO % non censito', NEW.id_contesto;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_d_tipo_messaggio_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteContestoAttivo(NEW.id_contesto)
  THEN
    RAISE EXCEPTION 'ID_CONTESTO % non censito', NEW.id_contesto;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_d_tipo_messaggio_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.id_contesto,-1) <> COALESCE(OLD.id_contesto,-1)
  THEN
    IF NOT FNC_EsisteContestoAttivo(NEW.id_contesto)
    THEN
      RAISE EXCEPTION 'ID_CONTESTO % non censito', NEW.id_contesto;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_dato_spesa_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_dato_spesa_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_doc_contribuzione_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_doc_contribuzione_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_doc_variaz_autobus_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente)
  THEN
    RAISE EXCEPTION 'FK_UTENTE % non censito o non attivo', NEW.fk_utente;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_doc_variaz_autobus_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.fk_utente,-1) <> COALESCE(OLD.fk_utente,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente)
    THEN
      RAISE EXCEPTION 'FK_UTENTE % non censito o non attivo', NEW.fk_utente;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_proc_contratto_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
  END IF;
  ----
  IF NOT FNC_EsisteIdContratto(NEW.id_contratto)
  THEN
    RAISE EXCEPTION 'ID_CONTRATTO % non censito', NEW.id_contratto;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_proc_contratto_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.id_utente_aggiornamento,-1) <> COALESCE(OLD.id_utente_aggiornamento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_aggiornamento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_AGGIORNAMENTO % non censito o non attivo', NEW.id_utente_aggiornamento;
    END IF;
  END IF;
  ----
  IF COALESCE(NEW.id_contratto,-1) <> COALESCE(OLD.id_contratto,-1)
  THEN
    IF NOT FNC_EsisteIdContratto(NEW.id_contratto)
    THEN
      RAISE EXCEPTION 'ID_CONTRATTO % non censito', NEW.id_contratto;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_r_proc_documento_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_proc_documento_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_proc_veicolo_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_r_proc_veicolo_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_assegnazione_risorse_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_assegnazione_risorse_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_contribuzione_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_contribuzione_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_costo_fornitura_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_costo_fornitura_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_dato_messa_servizio_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_dato_messa_servizio_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_documento_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_inserimento)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_INSERIMENTO % non censito o non attivo', NEW.id_utente_inserimento;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_documento_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.id_utente_inserimento,-1) <> COALESCE(OLD.id_utente_inserimento,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.id_utente_inserimento)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_INSERIMENTO % non censito o non attivo', NEW.id_utente_inserimento;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_iter_procedimento_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteSogGiuridAttivo(NEW.id_utente_sog_giurid)
  THEN
    RAISE EXCEPTION 'ID_UTENTE_SOG_GIURID % non censito o non attivo', NEW.id_utente_sog_giurid;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_iter_procedimento_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.id_utente_sog_giurid,-1) <> COALESCE(OLD.id_utente_sog_giurid,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteSogGiuridAttivo(NEW.id_utente_sog_giurid)
    THEN
      RAISE EXCEPTION 'ID_UTENTE_SOG_GIURID % non censito o non attivo', NEW.id_utente_sog_giurid;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_messaggi_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_mittente)
  THEN
    RAISE EXCEPTION 'FK_UTENTE_MITTENTE % non censito o non attivo', NEW.fk_utente_mittente;
  END IF;
  ----
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_destinatario)
  THEN
    RAISE EXCEPTION 'FK_UTENTE_DESTINATARIO % non censito o non attivo', NEW.fk_utente_destinatario;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_messaggi_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.fk_utente_mittente,-1) <> COALESCE(OLD.fk_utente_mittente,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_mittente)
    THEN
      RAISE EXCEPTION 'FK_UTENTE_MITTENTE % non censito o non attivo', NEW.fk_utente_mittente;
    END IF;
  END IF;
  ----
  IF COALESCE(NEW.fk_utente_destinatario,-1) <> COALESCE(OLD.fk_utente_destinatario,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_destinatario)
    THEN
      RAISE EXCEPTION 'FK_UTENTE_DESTINATARIO % non censito o non attivo', NEW.fk_utente_destinatario;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_ordine_acquisto_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_ordine_acquisto_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_procedimento_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_procedimento_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
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
FUNCTION fnc_trg_t_storia_variaz_autobus_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_storia)
  THEN
    RAISE EXCEPTION 'FK_UTENTE_STORIA % non censito o non attivo', NEW.fk_utente_storia;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_storia_variaz_autobus_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.fk_utente_storia,-1) <> COALESCE(OLD.fk_utente_storia,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente_storia)
    THEN
      RAISE EXCEPTION 'FK_UTENTE_STORIA % non censito o non attivo', NEW.fk_utente_storia;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_variaz_autobus_bi() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente)
  THEN
    RAISE EXCEPTION 'FK_UTENTE % non censito o non attivo', NEW.fk_utente;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;


CREATE OR REPLACE
FUNCTION fnc_trg_t_variaz_autobus_bu() RETURNS TRIGGER LANGUAGE plpgsql AS
$function$
BEGIN
  IF COALESCE(NEW.fk_utente,-1) <> COALESCE(OLD.fk_utente,-1)
  THEN
    IF NOT FNC_EsisteIdUtenteAttivo(NEW.fk_utente)
    THEN
      RAISE EXCEPTION 'FK_UTENTE % non censito o non attivo', NEW.fk_utente;
    END IF;
  END IF;
  ----
  RETURN NEW;
END;
$function$
;




ALTER FUNCTION fnc_trg_t_assegnazione_risorse_bu()  OWNER TO rebus;
ALTER FUNCTION bonificadispositivi()                OWNER TO rebus;
ALTER FUNCTION fnc_trg_d_tipo_documento_bi()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_d_tipo_documento_bu()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_d_tipo_messaggio_bi()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_d_tipo_messaggio_bu()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_dato_spesa_bi()              OWNER TO rebus;
ALTER FUNCTION fnc_trg_dato_spesa_bu()              OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_doc_contribuzione_bi()     OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_doc_contribuzione_bu()     OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_doc_variaz_autobus_bi()    OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_doc_variaz_autobus_bu()    OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_contratto_bi()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_contratto_bu()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_documento_bi()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_documento_bu()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_veicolo_bi()          OWNER TO rebus;
ALTER FUNCTION fnc_trg_r_proc_veicolo_bu()          OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_assegnazione_risorse_bi()  OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_contribuzione_bi()         OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_contribuzione_bu()         OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_costo_fornitura_bi()       OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_costo_fornitura_bu()       OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_dato_messa_servizio_bi()   OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_dato_messa_servizio_bu()   OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_documento_bi()             OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_documento_bu()             OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_iter_procedimento_bi()     OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_iter_procedimento_bu()     OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_messaggi_bi()              OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_messaggi_bu()              OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_ordine_acquisto_bi()       OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_ordine_acquisto_bu()       OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_procedimento_bi()          OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_procedimento_bu()          OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_storia_variaz_autobus_bi() OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_storia_variaz_autobus_bu() OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_variaz_autobus_bi()        OWNER TO rebus;
ALTER FUNCTION fnc_trg_t_variaz_autobus_bu()        OWNER TO rebus;




GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION bonificadispositivi() TO public;
GRANT ALL ON FUNCTION bonificadispositivi() TO rebus;
GRANT ALL ON FUNCTION bonificadispositivi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_documento_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_d_tipo_messaggio_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_dato_spesa_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_doc_contribuzione_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_doc_variaz_autobus_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_contratto_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_documento_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_r_proc_veicolo_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_assegnazione_risorse_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_contribuzione_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_costo_fornitura_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_dato_messa_servizio_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_documento_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_documento_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_documento_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_documento_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_documento_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_documento_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_iter_procedimento_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_messaggi_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_ordine_acquisto_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_procedimento_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_storia_variaz_autobus_bu() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bi() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bi() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bi() TO rebus_rw;
----
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bu() TO public;
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bu() TO rebus;
GRANT ALL ON FUNCTION fnc_trg_t_variaz_autobus_bu() TO rebus_rw;
