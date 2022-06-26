CREATE OR REPLACE VIEW rebus_r_utente_az_ente AS
SELECT  utente.id_utente_sog_giurid                                                                           AS  id_r_utente_az_ente
      , utente.id_utente                                                                                      AS  fk_utente
      , CASE WHEN ruolosoggiur.cod_ruolo_sog_giuridico::text = 'E'::text  THEN utente.id_soggetto_giuridico
                                                                          ELSE NULL::NUMERIC
        END                                                                                                   AS  fk_azienda
      , CASE WHEN tipsoggiur.cod_tipo_sog_giuridico::text = 'E'::text THEN utente.id_soggetto_giuridico
                                                                      ELSE NULL::NUMERIC
        END                                                                                                   AS  fk_ente
      , utente.data_inizio_validita                                                                           AS  data_appartenenza
      , utente.data_fine_validita
  FROM                sirtpl_r_utente_sog_giurid      utente
      ,               sirtpla_t_soggetto_giuridico    soggiur
      ,               sirtpla_d_tipo_sog_giuridico    tipsoggiur
          LEFT  JOIN  sirtpla_d_ruolo_sog_giuridico   ruolosoggiur  ON  tipsoggiur.id_ruolo_sog_giuridico = ruolosoggiur.id_ruolo_sog_giuridico
  WHERE utente.id_soggetto_giuridico  = soggiur.id_soggetto_giuridico
    AND soggiur.id_tipo_sog_giuridico = tipsoggiur.id_tipo_sog_giuridico;



CREATE OR REPLACE VIEW v_sirtpla_aziende AS
SELECT  soggiurid.id_soggetto_giuridico     AS  id_azienda
      , soggiurid.denominazione_breve       AS  denominazione
      , naturagiurid.desc_natura_giuridica  AS natura_giuridica
  FROM    sirtpla_t_soggetto_giuridico    soggiurid
     JOIN sirtpla_d_tipo_sog_giuridico    tiposoggiurid   ON  soggiurid.id_tipo_sog_giuridico      = tiposoggiurid.id_tipo_sog_giuridico
     JOIN sirtpla_d_natura_giuridica      naturagiurid    ON  soggiurid.id_natura_giuridica        = naturagiurid.id_natura_giuridica
     JOIN sirtpla_d_ruolo_sog_giuridico   ruolosoggiurid  ON  tiposoggiurid.id_ruolo_sog_giuridico = ruolosoggiurid.id_ruolo_sog_giuridico
  WHERE ruolosoggiurid.cod_ruolo_sog_giuridico::text = 'E'::TEXT;



CREATE OR REPLACE VIEW rebus_t_aziende AS
SELECT  v_sirtpla_aziende.id_azienda
      , v_sirtpla_aziende.denominazione
      , v_sirtpla_aziende.natura_giuridica
  FROM  v_sirtpla_aziende;



CREATE OR REPLACE VIEW rebus_t_enti AS
SELECT  soggiur.id_soggetto_giuridico   AS  id_ente
      , soggiur.denominazione_breve     AS  denominazione
  FROM    sirtpla_t_soggetto_giuridico  soggiur
    JOIN  sirtpla_d_tipo_sog_giuridico  tipsoggiur  ON  soggiur.id_tipo_sog_giuridico = tipsoggiur.id_tipo_sog_giuridico
  WHERE tipsoggiur.cod_tipo_sog_giuridico::TEXT = 'E'::TEXT;



CREATE OR REPLACE VIEW rebus_t_utenti AS
SELECT  sirtpl_t_utente.id_utente
      , sirtpl_t_utente.codice_fiscale        AS  cf
      , sirtpl_t_utente.nome
      , sirtpl_t_utente.cognome
      , sirtpl_t_utente.data_inizio_validita  AS  data_creazione
      , sirtpl_t_utente.data_fine_validita    AS  data_cancellazione
  FROM  sirtpl_t_utente;



CREATE OR REPLACE VIEW v_autobus AS
SELECT rebus_t_autobus.primo_telaio,
    rebus_t_autobus.data_prima_immatricolazione,
    rebus_t_autobus.marca,
    rebus_t_autobus.modello,
    aa.fk_azienda,
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::text
            ELSE 'N'::text
        END AS contribuito,
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 3 THEN rebus_t_autobus.data_prima_immatricolazione + '10 years'::interval
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 1 THEN rebus_t_autobus.data_prima_immatricolazione + '8 years'::interval
            ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END AS scad_vincoli_no_alien,
    rebus_t_variaz_autobus.data_aggiornamento,
    rebus_t_variaz_autobus.id_variaz_autobus,
    aa.data_aggiornamento AS data_aggiornamento_aa,
    rebus_t_variaz_autobus.n_telaio::text AS n_telaio,
    rebus_t_variaz_autobus.n_targa::text AS n_targa,
    rebus_t_variaz_autobus.data_ultima_immatricolazione,
    rebus_t_variaz_autobus.fk_tipo_alimentazione,
    rebus_t_variaz_autobus.n_posti_sedere,
    rebus_t_variaz_autobus.n_posti_in_piedi,
    rebus_t_variaz_autobus.n_posti_riservati,
    rebus_t_variaz_autobus.omologazione_direttiva_europea::text AS omologazione_direttiva_europea,
    rebus_t_variaz_autobus.fk_classe_ambientale_euro,
    rebus_t_variaz_autobus.flg_filtro_fap::text AS flg_filtro_fap,
    rebus_t_variaz_autobus.fk_dotazione_disabili,
    rebus_t_variaz_autobus.fk_impianti_audio,
    rebus_t_variaz_autobus.fk_impianti_visivi,
    rebus_t_variaz_autobus.flg_rilevatore_bip::text AS flg_rilevatore_bip,
    rebus_t_variaz_autobus.prezzo_tot_acquisto,
    rebus_t_variaz_autobus.contributo_pubblico_acquisto,
    rebus_t_variaz_autobus.flg_veicolo_assicurato::text AS flg_veicolo_assicurato,
    rebus_t_variaz_autobus.data_ultima_revisione,
    rebus_t_variaz_autobus.note,
    rebus_t_variaz_autobus.n_matricola_aziendale::text AS n_matricola_aziendale,
    rebus_t_variaz_autobus.fk_tipo_immatricolazione,
    rebus_t_variaz_autobus.ente_autorizz_prima_imm::text AS ente_autorizz_prima_imm,
    rebus_t_variaz_autobus.fk_classe_veicolo,
    rebus_t_variaz_autobus.flg_due_piani::text AS flg_due_piani,
    rebus_t_variaz_autobus.flg_snodato::text AS flg_snodato,
    rebus_t_variaz_autobus.caratteristiche_particolari::text AS caratteristiche_particolari,
    rebus_t_variaz_autobus.altra_alimentazione::text AS altra_alimentazione,
    rebus_t_variaz_autobus.fk_tipo_allestimento,
    rebus_t_variaz_autobus.progr_tipo_dimens,
    rebus_t_variaz_autobus.lunghezza,
    rebus_t_variaz_autobus.numero_porte,
    rebus_t_variaz_autobus.posti_carrozzina,
    rebus_t_variaz_autobus.flg_impianto_condizionamento::text AS flg_impianto_condizionamento,
    rebus_t_variaz_autobus.flg_cabina_guida_isolata,
    rebus_t_variaz_autobus.altri_dispositivi_prevenz_inc::text AS altri_dispositivi_prevenz_inc,
    rebus_t_variaz_autobus.flg_otx::text AS flg_otx,
    rebus_t_variaz_autobus.flg_avm::text AS flg_avm,
    rebus_t_variaz_autobus.flg_contapasseggeri::text AS flg_contapasseggeri,
    rebus_t_variaz_autobus.fk_proprieta_leasing,
    rebus_t_variaz_autobus.flg_conteggiato_miv::text AS flg_conteggiato_miv,
    rebus_t_variaz_autobus.fk_deposito AS fk_deposito_prevalente,
    rebus_t_variaz_autobus.tmp_indirizzo_dep::text AS tmp_indirizzo_dep,
    rebus_t_variaz_autobus.nota_riservata_azienda,
    rebus_t_variaz_autobus.nota_riservata_amp,
    rebus_t_variaz_autobus.nota_riservata_rp,
    rebus_t_variaz_autobus.flg_verificato_azienda::text AS flg_verificato_azienda,
    rebus_t_variaz_autobus.flg_verificato_amp,
    rebus_t_variaz_autobus.flg_alienato::text AS flg_alienato,
    CASE  WHEN rebus_t_variaz_autobus.flg_alienato = 'N'::CHARACTER VARYING THEN 'Attivo'::TEXT
          WHEN rebus_t_variaz_autobus.flg_alienato = 'S'::CHARACTER VARYING THEN 'Alienato'::TEXT
          WHEN rebus_t_variaz_autobus.flg_alienato = 'R'::CHARACTER VARYING THEN 'Ritirato'::TEXT
    END                                                                                             AS  stato_tpl,
    rebus_t_variaz_autobus.flg_richiesta_contr::text AS flg_richiesta_contr,
    rebus_t_variaz_autobus.anno_sost_prog,
    aa.data_alienazione,
        CASE
            WHEN rebus_t_variaz_autobus.n_targa IS NULL THEN TRUE
            ELSE false
        END AS flg_da_immatricolare,
    rebus_t_variaz_autobus.flg_contribuzione,
    ((cv.categoria_veicolo::text || ' ('::text) || cv.cod_categoria_veicolo::text) || ')'::text AS categoria_veicolo,
    rebus_t_variaz_autobus.fk_categoria_veicolo,
        CASE
            WHEN (EXISTS ( SELECT 1
               FROM rebusc_t_dato_messa_servizio dms
              WHERE rebus_t_autobus.primo_telaio::text = dms.primo_telaio_sost::text AND (EXISTS ( SELECT 1
                       FROM rebusp_t_iter_procedimento ip,
                        rebusc_t_contribuzione c
                      WHERE dms.id_dato_messa_servizio = c.id_dato_messa_servizio AND c.id_procedimento = ip.id_procedimento AND ip.id_stato_iter = 61::numeric)) AND (EXISTS ( SELECT 1
                       FROM rebusp_t_iter_procedimento ip,
                        rebusc_t_contribuzione c
                      WHERE dms.id_dato_messa_servizio = c.id_dato_messa_servizio AND c.id_procedimento = ip.id_procedimento AND ip.id_stato_iter = 62::numeric)))) THEN true
            ELSE FALSE
        END AS sostituito,
    rebus_t_variaz_autobus.fk_portabici,
    p.desc_portabici AS portabici,
    rebus_t_variaz_autobus.fk_sistema_videosorveglianza,
    sv.desc_sistema_videosorveglianza AS sistema_videosorveglianza,
    rebus_t_variaz_autobus.fk_sistema_localizzazione,
    sl.desc_sistema_localizzazione AS tipologia_avm,
    rebus_t_variaz_autobus.flg_contapasseggeri_integrato,
    rebus_t_variaz_autobus.flg_bip_cablato,
    rebus_t_variaz_autobus.flg_sistemi_protezione_autista,
    rebus_t_variaz_autobus.altri_allestimenti
   FROM rebus_t_autobus
     JOIN rebus_t_variaz_autobus ON rebus_t_autobus.primo_telaio::text = rebus_t_variaz_autobus.primo_telaio::text
     JOIN rebus_r_azienda_autobus aa ON aa.primo_telaio::text = rebus_t_variaz_autobus.primo_telaio::text
     JOIN rebus_d_categoria_veicolo cv ON rebus_t_variaz_autobus.fk_categoria_veicolo = cv.id_categoria_veicolo
     LEFT JOIN rebusc_d_portabici p ON rebus_t_variaz_autobus.fk_portabici = p.id_portabici
     LEFT JOIN rebusc_d_sistema_videosorveglianza sv ON rebus_t_variaz_autobus.fk_sistema_videosorveglianza = sv.id_sistema_videosorveglianza
     LEFT JOIN rebusc_d_sistema_localizzazione sl ON rebus_t_variaz_autobus.fk_sistema_videosorveglianza = sl.id_sistema_localizzazione
  WHERE aa.data_alienazione IS NULL OR aa.data_alienazione IS NOT NULL AND NOT (EXISTS ( SELECT 1
           FROM rebus_r_azienda_autobus aa1
          WHERE aa.primo_telaio::text = aa1.primo_telaio::text AND aa1.data_alienazione IS NULL))
  ORDER BY aa.data_alienazione DESC;



CREATE OR REPLACE VIEW v_autobus_small AS
SELECT  MAX(rebus_t_variaz_autobus.data_aggiornamento) AS data_aggiornamento,
    MAX(rebus_t_variaz_autobus.id_variaz_autobus) AS id_variaz_autobus,
    rebus_t_autobus.primo_telaio,
    rebus_t_autobus.fk_bando,
    rebus_t_autobus.marca,
    rebus_t_autobus.modello,
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::text
            ELSE 'N'::text
        END AS contribuito,
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 3 THEN rebus_t_autobus.data_prima_immatricolazione + '10 years'::INTERVAL
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 1 THEN rebus_t_autobus.data_prima_immatricolazione + '8 years'::INTERVAL
            ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END AS scad_vincoli_no_alien,
    rebus_t_autobus.data_prima_immatricolazione,
    rebus_t_variaz_autobus.fk_tipo_allestimento
   FROM rebus_t_autobus
     JOIN rebus_t_variaz_autobus ON rebus_t_autobus.primo_telaio::text = rebus_t_variaz_autobus.primo_telaio::text
  GROUP BY rebus_t_autobus.primo_telaio, rebus_t_autobus.fk_bando, rebus_t_autobus.marca, rebus_t_autobus.modello, (
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 3 THEN rebus_t_autobus.data_prima_immatricolazione + '10 years'::INTERVAL
            WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL AND rebus_t_variaz_autobus.fk_tipo_allestimento = 1 THEN rebus_t_autobus.data_prima_immatricolazione + '8 years'::INTERVAL
            ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END), rebus_t_autobus.data_prima_immatricolazione, rebus_t_variaz_autobus.fk_tipo_allestimento
  ORDER BY rebus_t_autobus.primo_telaio;



CREATE OR REPLACE VIEW v_autobus_small2 AS
SELECT  rebus_t_variaz_autobus.data_aggiornamento
      , rebus_t_variaz_autobus.id_variaz_autobus
      , rebus_t_autobus.primo_telaio
      , rebus_t_autobus.fk_bando
      , rebus_t_autobus.marca
      , rebus_t_autobus.modello
      , CASE WHEN rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::TEXT
                                                                                            ELSE 'N'::TEXT
        END                                                                                                   AS  contribuito
      , CASE  WHEN  rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION
                AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL
                AND rebus_t_variaz_autobus.fk_tipo_allestimento = 3                             THEN rebus_t_autobus.data_prima_immatricolazione + '10 years'::INTERVAL
              WHEN  rebus_t_variaz_autobus.contributo_pubblico_acquisto > 0::DOUBLE PRECISION
                AND rebus_t_autobus.data_prima_immatricolazione IS NOT NULL
                AND rebus_t_variaz_autobus.fk_tipo_allestimento = 1                             THEN rebus_t_autobus.data_prima_immatricolazione + '8 years'::INTERVAL
                                                                                                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END                                                                                                   AS  scad_vincoli_no_alien
      , rebus_t_autobus.data_prima_immatricolazione
      , rebus_t_variaz_autobus.fk_tipo_allestimento
  FROM  rebus_t_autobus
      , rebus_t_variaz_autobus
  WHERE rebus_t_autobus.primo_telaio::text = rebus_t_variaz_autobus.primo_telaio::TEXT
    AND ((rebus_t_variaz_autobus.data_aggiornamento, rebus_t_variaz_autobus.primo_telaio::text) IN  ( SELECT  MAX(rebus_t_variaz_autobus_1.data_aggiornamento)  AS  max
                                                                                                            , rebus_t_variaz_autobus_1.primo_telaio
                                                                                                        FROM  rebus_t_variaz_autobus rebus_t_variaz_autobus_1
                                                                                                        GROUP BY  rebus_t_variaz_autobus_1.primo_telaio
                                                                                                    )
        );



CREATE OR REPLACE VIEW v_veicoli_procedimenti AS
WITH proroga AS ( SELECT  sirtplc_t_proroga_contratto.id_contratto
                        , MAX(sirtplc_t_proroga_contratto.data_fine_proroga)  AS  dfp
                    FROM  sirtplc_t_proroga_contratto
                    GROUP BY  sirtplc_t_proroga_contratto.id_contratto
                )
SELECT  va.id_variaz_autobus  AS  id_va
      , (((date_part('year'::text, ip10.data_inizio_validita) || '/'::text) ||
        CASE
            WHEN p_so1.num_procedimento IS NOT NULL OR p_so2.num_procedimento IS NOT NULL THEN 'SO'::character varying
            ELSE tp.cod_tipo_procedimento
        END::text) || '/'::text) || COALESCE(p_so1.num_procedimento, p_so2.num_procedimento, p.num_procedimento) AS num_proc,
    tp.desc_tipo_procedimento::text ||
        CASE
            WHEN p_so1.num_procedimento IS NOT NULL OR p_so2.num_procedimento IS NOT NULL THEN ' (sostituzione)'::text
            ELSE ''::text
        END AS tipo_proc,
    si.desc_stato_iter AS stato_proc,
    TO_CHAR(ip.data_inizio_validita, 'DD/MM/YYYY'::text) AS data_stato,
    (c.cod_id_nazionale::text || ' - '::text) || c.desc_contratto::text AS contratto,
        CASE "position"(sc_ente.alias::text, '<b>'::text)
            WHEN 1 THEN REPLACE(sc_ente.alias::text, '<b>'::text, ''::text)::CHARACTER VARYING
            ELSE sc_ente.alias
        END AS ente_comm,
        CASE "position"(sc_esec.alias::text, '<b>'::text)
            WHEN 1 THEN REPLACE(sc_esec.alias::text, '<b>'::text, ''::text)::CHARACTER VARYING
            ELSE sc_esec.alias
        END AS esec_tit,
    pd_r.nome_file AS richiesta,
    pd_a.nome_file AS autorizzazione,
        CASE
            WHEN p_so1.num_procedimento IS NOT NULL THEN p_so1.id_procedimento
            WHEN p_so2.num_procedimento IS NOT NULL THEN p_so2.id_procedimento
            ELSE p.id_procedimento
        END AS id_proc,
        CASE
            WHEN p_so1.num_procedimento IS NOT NULL OR p_so2.num_procedimento IS NOT NULL THEN 2::NUMERIC(2,0)
            ELSE p.id_tipo_procedimento
        END AS id_tipo_proc
  FROM          rebusp_t_procedimento       p
          JOIN  rebusp_t_iter_procedimento  ip        ON    p.id_procedimento = ip.id_procedimento
                                                        AND ip.data_fine_validita IS NULL
          JOIN  rebusp_t_iter_procedimento  ip10      ON  p.id_procedimento = ip10.id_procedimento
                                                        AND ip10.id_stato_iter = 10::NUMERIC
          JOIN  rebusp_d_tipo_procedimento  tp        ON  p.id_tipo_procedimento = tp.id_tipo_procedimento
          JOIN  rebusp_d_stato_iter         si        ON  ip.id_stato_iter = si.id_stato_iter
          JOIN  rebusp_r_proc_veicolo       pv        ON  p.id_procedimento = pv.id_procedimento
          JOIN  rebus_t_autobus             a         ON  pv.primo_telaio::text = a.primo_telaio::text
          JOIN  rebus_t_variaz_autobus      va        ON  a.primo_telaio::text = va.primo_telaio::text
    LEFT  JOIN  rebusp_r_proc_documento     pd_r      ON  p.id_procedimento = pd_r.id_procedimento
                                                        AND pd_r.id_tipo_documento = 7
    LEFT  JOIN  rebusp_r_proc_documento     pd_a      ON  p.id_procedimento = pd_a.id_procedimento
                                                        AND pd_a.id_tipo_documento = 8
          JOIN  rebusp_r_proc_contratto     pc        ON  p.id_procedimento = pc.id_procedimento
          JOIN  sirtplc_t_contratto         c         ON  pc.id_contratto = c.id_contratto
    LEFT  JOIN  proroga                               ON  c.id_contratto = proroga.id_contratto
    LEFT  JOIN  rebusp_t_sub_procedimento   sp1       ON  p.id_procedimento = sp1.id_sub_procedimento_1
    LEFT  JOIN  rebusp_t_procedimento       p_so1     ON  sp1.id_procedimento = p_so1.id_procedimento
    LEFT  JOIN  rebusp_t_sub_procedimento   sp2       ON  p.id_procedimento = sp2.id_sub_procedimento_2
    LEFT  JOIN  rebusp_t_procedimento       p_so2     ON  sp2.id_procedimento = p_so2.id_procedimento
          JOIN  v_soggetti_coinvolti        sc_ente   ON  c.id_contratto = sc_ente.idc
                                                        AND sc_ente.ruolo::text = 'Ente committente'::text
                                                        AND (now()::DATE <= COALESCE(proroga.dfp, c.data_fine_validita)
                                                        AND now()::DATE >= sc_ente.d_ini
                                                        AND now()::DATE <= sc_ente.d_fin OR now()::DATE > COALESCE(proroga.dfp, c.data_fine_validita)
                                                        AND sc_ente.d_fin = COALESCE(proroga.dfp, c.data_fine_validita))
          JOIN  v_soggetti_coinvolti        sc_esec   ON  c.id_contratto = sc_esec.idc
                                                        AND sc_esec.ruolo::text = 'Esecutore titolare'::text
                                                        AND (now()::DATE <= COALESCE(proroga.dfp, c.data_fine_validita)
                                                        AND now()::DATE >= sc_esec.d_ini
                                                        AND now()::DATE <= sc_esec.d_fin OR now()::DATE > COALESCE(proroga.dfp, c.data_fine_validita)
                                                        AND sc_esec.d_fin = COALESCE(proroga.dfp, c.data_fine_validita));





CREATE OR REPLACE VIEW v_export_ricerca_autobus AS
SELECT varaut.data_aggiornamento,
    az.id_azienda,
    az.denominazione,
    varaut.n_telaio,
    varaut.n_targa,
    aut.data_prima_immatricolazione,
    varaut.data_ultima_immatricolazione,
    tipalim.descrizione AS alimentazione,
    varaut.n_posti_sedere,
    varaut.n_posti_in_piedi,
    varaut.n_posti_riservati,
    varaut.omologazione_direttiva_europea,
    clambeur.descrizione AS omologazione_classe,
    varaut.flg_filtro_fap,
    dotdis.descrizione AS facilitazione_disabili,
    impaud.descrizione AS impianti_audio,
    impvis.descrizione AS impianti_visivi,
    varaut.flg_rilevatore_bip,
    varaut.prezzo_tot_acquisto,
    varaut.contributo_pubblico_acquisto,
    varaut.flg_veicolo_assicurato,
    varaut.data_ultima_revisione,
    varaut.note,
    varaut.primo_telaio,
    varaut.n_matricola_aziendale,
    tipimm.descrizione AS tipo_immatricolazione,
    varaut.ente_autorizz_prima_imm,
    clveic.descrizione AS classe_veicolo,
    aut.marca,
    aut.modello,
    varaut.flg_due_piani,
    varaut.flg_snodato::text AS flg_snodato,
    varaut.caratteristiche_particolari,
    varaut.fk_tipo_alimentazione,
    varaut.altra_alimentazione,
    tipallest.descrizione AS tipo_allestimento,
    varaut.lunghezza,
    varaut.numero_porte,
    varaut.posti_carrozzina,
    varaut.flg_impianto_condizionamento,
    varaut.flg_cabina_guida_isolata,
    dispord.list AS dispositivi_prevenz,
    varaut.altri_dispositivi_prevenz_inc,
    varaut.flg_otx,
    varaut.flg_avm,
    varaut.flg_contapasseggeri,
    prop.descrizione AS proprieta_leasing,
    varaut.flg_conteggiato_miv,
    ((((((((((((depcom.denom_deposito::text || ' - '::text) || depcom.toponimo_deposito::text) || ' '::text) || depcom.indirizzo_deposito::text) || ' '::text) || depcom.num_civico_deposito::text) || ', '::text) || depcom.cap_deposito::text) || ' '::text) || depcom.denom_comune::text) || ' ('::text) || depcom.sigla_provincia::text) || ')'::text AS deposito,
    raz.data_alienazione,
    tipdim.tipologia_dimens,
    CASE  WHEN varaut.flg_alienato = 'N'::CHARACTER VARYING THEN 'Attivo'::TEXT
          WHEN varaut.flg_alienato = 'S'::CHARACTER VARYING THEN 'Alienato'::TEXT
          WHEN varaut.flg_alienato = 'R'::CHARACTER VARYING THEN 'Ritirato'::TEXT
    END                                                                             AS  stato_tpl,
        CASE
            WHEN varaut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::TEXT
            ELSE 'N'::text
        END AS contribuito,
        CASE
            WHEN varaut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND aut.data_prima_immatricolazione IS NOT NULL AND varaut.fk_tipo_allestimento = 3 THEN aut.data_prima_immatricolazione + '10 years'::INTERVAL
            WHEN varaut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND aut.data_prima_immatricolazione IS NOT NULL AND varaut.fk_tipo_allestimento = 1 THEN aut.data_prima_immatricolazione + '8 years'::INTERVAL
            ELSE NULL::timestamp without time zone
        END AS scad_vincoli_no_alien,
    varaut.nota_riservata_azienda,
    varaut.nota_riservata_amp,
    varaut.nota_riservata_rp,
    varaut.flg_richiesta_contr,
    varaut.anno_sost_prog,
    varaut.data_inserimento,
    varaut.flg_verificato_azienda,
    varaut.flg_verificato_amp,
        CASE
            WHEN (( SELECT count(*) AS count
               FROM rebus_r_doc_variaz_autobus
              WHERE rebus_r_doc_variaz_autobus.id_variaz_autobus = varaut.id_variaz_autobus)) > 0 THEN TRUE
            ELSE FALSE
        END AS presenza_allegati,
    varaut.motivazione,
    bus_proc.proc AS procedimenti,
    varaut.id_variaz_autobus AS id_va,
        CASE
            WHEN bus_proc.proc IS NOT NULL THEN TRUE
            ELSE FALSE
        END AS oggetto_richieste,
        CASE
            WHEN (EXISTS ( SELECT 1
               FROM rebusp_t_procedimento p,
                rebusp_r_proc_veicolo pv
              WHERE p.id_procedimento = pv.id_procedimento AND pv.primo_telaio::text = varaut.primo_telaio::text AND p.id_tipo_procedimento = 7::NUMERIC)) THEN TRUE
            ELSE false
        END AS presenza_rendicontazione,
        CASE
            WHEN varaut.flg_verificato_azienda::text <> 'S'::text AND bus_proc.proc IS NULL AND NOT (EXISTS ( SELECT 1
               FROM rebusp_t_procedimento p,
                rebusp_r_proc_veicolo pv
              WHERE p.id_procedimento = pv.id_procedimento AND pv.primo_telaio::text = varaut.primo_telaio::text AND p.id_tipo_procedimento = 7::NUMERIC)) THEN true
            ELSE false
        END AS eliminabile,
    ((cv.categoria_veicolo::text || ' ('::text) || cv.cod_categoria_veicolo::text) || ')'::text AS categoria_veicolo,
    varaut.fk_categoria_veicolo,
        CASE
            WHEN varaut.flg_alienato::text = 'N'::text THEN TRUE
            ELSE FALSE
        END AS attivo,
        CASE
            WHEN varaut.flg_alienato::text = 'R'::text THEN TRUE
            ELSE FALSE
        END AS ritirato,
        CASE
            WHEN raz.data_alienazione IS NOT NULL THEN TRUE
            ELSE FALSE
        END AS alienato_azienda,
        CASE
            WHEN max_prop.data_alien IS NOT NULL THEN TRUE
            ELSE FALSE
        END AS alienato_no_azienda,
        CASE
            WHEN varaut.n_targa IS NULL THEN TRUE
            ELSE FALSE
        END AS da_immatricolare,
        CASE
            WHEN raz.data_alienazione IS NOT NULL AND (EXISTS ( SELECT DISTINCT 1
               FROM rebus_r_azienda_autobus aa
              WHERE raz.primo_telaio::text = aa.primo_telaio::text AND aa.data_aggiornamento > raz.data_aggiornamento)) THEN TRUE
            ELSE FALSE
        END AS proprieta_precedente
   FROM rebus_t_variaz_autobus varaut
     JOIN rebus_r_azienda_autobus raz ON varaut.primo_telaio::text = raz.primo_telaio::text
     JOIN rebus_t_aziende az ON raz.fk_azienda::NUMERIC = az.id_azienda
     JOIN rebus_t_autobus aut ON varaut.primo_telaio::text = aut.primo_telaio::text
     JOIN rebus_d_tipo_alimentazione tipalim ON varaut.fk_tipo_alimentazione = tipalim.id_tipo_alimentazione
     JOIN rebus_d_classe_amb_euro clambeur ON varaut.fk_classe_ambientale_euro = clambeur.id_classe_ambientale
     JOIN rebus_d_dotazione_disabili dotdis ON varaut.fk_dotazione_disabili = dotdis.id_dotazione_disabili
     JOIN rebus_d_impianti_audio impaud ON varaut.fk_impianti_audio = impaud.id_impianti_audio
     JOIN rebus_d_impianti_visivi impvis ON varaut.fk_impianti_visivi = impvis.id_impianti_visivi
     JOIN rebus_d_tipo_immatricol tipimm ON varaut.fk_tipo_immatricolazione = tipimm.id_tipo_immatricolazione
     JOIN rebus_d_classe_veicolo clveic ON varaut.fk_classe_veicolo = clveic.id_classe_veicolo
     JOIN rebus_d_tipo_allestimento tipallest ON varaut.fk_tipo_allestimento = tipallest.id_tipo_allestimento
     JOIN ( SELECT
                CASE
                    WHEN string_agg(disp.descrizione::text, ', '::text) = 'ND'::text THEN NULL::text
                    ELSE string_agg(disp.descrizione::text, ', '::text)
                END AS list,
            disp.id_variaz_autobus
           FROM ( SELECT vdp.id_variaz_autobus,
                    vdp.id_dispositivo,
                    dp.descrizione
                   FROM rebus_r_varautobus_dp vdp
                     JOIN rebus_d_dispositivi_prevenz dp ON vdp.id_dispositivo = dp.id_dispositivo
                  ORDER BY vdp.id_variaz_autobus, vdp.id_dispositivo) disp
          GROUP BY disp.id_variaz_autobus) dispord ON varaut.id_variaz_autobus = dispord.id_variaz_autobus
     JOIN rebus_d_proprieta prop ON varaut.fk_proprieta_leasing = prop.id_proprieta
     JOIN rebus_d_tipologia_dimens tipdim ON varaut.progr_tipo_dimens = tipdim.progr_tipo_dimens AND varaut.fk_tipo_allestimento = tipdim.id_tipo_allestimento
     LEFT JOIN ( SELECT dep.id_deposito,
            dep.denom_deposito,
            dep.toponimo_deposito,
            dep.indirizzo_deposito,
            dep.num_civico_deposito,
            dep.cap_deposito,
            com.denom_comune,
            com.sigla_provincia
           FROM sirtpla_t_deposito dep
             JOIN v_comuni_all com ON dep.id_comune_deposito = com.id_comune AND dep.data_aggiornamento >= com.data_inizio_validita_comune AND dep.data_aggiornamento <= COALESCE(com.data_fine_validita_comune, to_date('31/12/9999'::text, 'DD/MM/YYYY'::text))) depcom ON varaut.fk_deposito::numeric = depcom.id_deposito
     LEFT JOIN ( SELECT string_agg(vp.num_proc, ','::text) AS proc,
            vp.id_va
           FROM ( SELECT v_veicoli_procedimenti.id_va,
                    v_veicoli_procedimenti.num_proc
                   FROM v_veicoli_procedimenti
                  ORDER BY v_veicoli_procedimenti.id_va, v_veicoli_procedimenti.num_proc DESC) vp
          GROUP BY vp.id_va) bus_proc ON varaut.id_variaz_autobus = bus_proc.id_va
     JOIN rebus_d_categoria_veicolo cv ON varaut.fk_categoria_veicolo = cv.id_categoria_veicolo
     JOIN ( SELECT rebus_r_azienda_autobus.primo_telaio,
            NULLIF(MAX(COALESCE(rebus_r_azienda_autobus.data_alienazione, TO_DATE('01/01/3000'::text, 'DD/MM/YYYY'::text)::TIMESTAMP WITHOUT TIME ZONE)), TO_DATE('01/01/3000'::text, 'DD/MM/YYYY'::text)) AS data_alien
           FROM rebus_r_azienda_autobus
          GROUP BY rebus_r_azienda_autobus.primo_telaio) max_prop ON raz.primo_telaio::text = max_prop.primo_telaio::text
  WHERE 1 = 1
  ORDER BY varaut.data_aggiornamento DESC;



CREATE OR REPLACE VIEW v_export_ricerca_contribuzione AS
WITH voci AS (
         SELECT vc.id_voce_costo,
            vc.desc_voce_costo,
            vcf.importo,
            vcf.id_costo_fornitura
           FROM rebusc_r_voce_costo_fornitura vcf
             JOIN rebusc_d_voce_costo vc ON vcf.id_voce_costo = vc.id_voce_costo
        )
 SELECT COALESCE(sg.denominazione_breve, sg1.denominazione_breve) AS azienda_ricerca,
    va.id_variaz_autobus AS id_va,
    va.primo_telaio,
    ff.id_fonte_finanziamento,
    ff.desc_breve AS fonte_finanziamento,
    tali.id_tipo_alimentazione,
    tali.descrizione AS tipo_alimentazione,
    tall.id_tipo_allestimento,
    tall.descrizione AS tipo_allestimento,
    TO_CHAR(maxip41.max41, 'DD/MM/YYYY'::text) AS trasmessa_a,
    TO_CHAR(ip61.data_inizio_validita, 'DD/MM/YYYY'::text) AS validata_a,
    TO_CHAR(maxip42.max42, 'DD/MM/YYYY'::text) AS trasmessa_b,
    TO_CHAR(ip62.data_inizio_validita, 'DD/MM/YYYY'::text) AS validata_b,
    va.flg_verificato_azienda,
    va.flg_verificato_amp,
    p.id_procedimento,
    COALESCE(aa.fk_azienda, aa1.fk_azienda) AS id_azienda,
    ((((((((((((((COALESCE(sg.denominazione_aaep, sg1.denominazione_aaep)::text || ' '::text) || COALESCE(ng.desc_breve_natura_giuridica, ng1.desc_breve_natura_giuridica)::text) || ', '::text) || COALESCE(sg.toponimo_sede_legale, sg1.toponimo_sede_legale)::text) || ' '::text) || COALESCE(sg.indirizzo_sede_legale, sg1.indirizzo_sede_legale)::text) || ' '::text) || COALESCE(sg.num_civico_sede_legale, sg1.num_civico_sede_legale)::text) || ' '::text) || COALESCE(sg.cap_sede_legale, sg1.cap_sede_legale)::text) || ', '::text) || COALESCE(com.denom_comune, com1.denom_comune)::text) || ' - '::text) || 'P. IVA '::text) || COALESCE(sg.partita_iva, sg1.partita_iva)::text AS azienda,
    va.n_telaio AS telaio,
    va.n_targa AS targa,
    TO_CHAR(a.data_prima_immatricolazione::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::text) AS data_prima_immatricolazione,
    dms.nr_carta_circolazione,
    a.marca,
    a.modello,
    va.omologazione_direttiva_europea AS classe_ambientale,
    cae.descrizione AS classe_ambientale_euro,
    cv.descrizione AS classe_veicolo,
    va.lunghezza,
    td.tipologia_dimens AS tipologia_dimensionale,
    va.n_posti_sedere,
    va.n_posti_in_piedi,
    va.n_posti_riservati,
    va.posti_carrozzina,
    CASE  WHEN va.flg_avm = 'U'::CHARACTER VARYING  THEN ''::TEXT
          WHEN va.flg_avm = 'S'::CHARACTER VARYING  THEN 'Si'::TEXT
          WHEN va.flg_avm = 'N'::CHARACTER VARYING  THEN 'No'::TEXT
    END                                                               AS  avm,
    sl.desc_sistema_localizzazione AS tipologia_avm,
    va.flg_rilevatore_bip,
    CASE  WHEN va.flg_bip_cablato IS NULL THEN ''::TEXT
          WHEN va.flg_bip_cablato         THEN 'Si'::TEXT
          WHEN NOT va.flg_bip_cablato     THEN 'No'::TEXT
    END                                                               AS  bip_cablato,
    pb.desc_portabici,
    sv.desc_sistema_videosorveglianza,
    dd.descrizione AS dotazioni_disabili,
    ia.descrizione AS impianti_audio,
    iv.descrizione AS impianti_video,
    CASE  WHEN va.flg_contapasseggeri = 'U'::CHARACTER VARYING  THEN ''::CHARACTER VARYING
          WHEN va.flg_contapasseggeri = 'S'::CHARACTER VARYING  THEN 'Si'::CHARACTER VARYING
          WHEN va.flg_contapasseggeri = 'N'::CHARACTER VARYING  THEN 'No'::CHARACTER VARYING
    END                                                               AS  contapasseggeri,
    CASE  WHEN va.flg_contapasseggeri_integrato IS NULL THEN ''::TEXT
          WHEN va.flg_contapasseggeri_integrato         THEN 'Si'::TEXT
          WHEN NOT va.flg_contapasseggeri_integrato     THEN 'No'::TEXT
    END                                                               AS  contapasseggeri_integrato,
    CASE  WHEN va.flg_sistemi_protezione_autista      THEN 'Si'::TEXT
          WHEN NOT va.flg_sistemi_protezione_autista  THEN 'No'::TEXT
    END                                                               AS  protezione_autista,
    CASE  WHEN va.flg_impianto_condizionamento = 'U'::CHARACTER VARYING THEN ''::TEXT
          WHEN va.flg_impianto_condizionamento = 'S'::CHARACTER VARYING THEN 'Si'::TEXT
          WHEN va.flg_impianto_condizionamento = 'N'::CHARACTER VARYING THEN 'No'::TEXT
    END                                                               AS  impianto_condizionamento,
    va.flg_filtro_fap,
    va.altri_allestimenti,
    va.flg_conteggiato_miv,
    aass.desc_breve,
    ar.contributo_statale + ar.contributo_regionale_agg AS contributo_pubblico,
    ar.contributo_statale,
    ar.contributo_regionale_agg AS contributo_regionale_aggiuntivo,
    cf.costo_ammissibile_reg,
    cf.costo_ammissibile_ff,
    ds.contributo_erogabile_reg,
    ds.contributo_regionale_ff,
    ds.nr_determina_rp_amp_ant,
    ds.data_determina_rp_amp_ant,
    ds.nr_atto_liquidazione_rp_amp_ant,
    ds.data_atto_liquidazione_rp_amp_ant,
    ds.nr_determina_rp_amp_sal,
    ds.data_determina_rp_amp_sal,
    ds.nr_atto_liquidazione_rp_amp_sal,
    ds.data_atto_liquidazione_rp_amp_sal,
    ds.nr_determina_amp_az_ant,
    ds.data_determina_amp_az_ant,
    ds.nr_atto_liquidazione_amp_az_ant,
    ds.data_atto_liquidazione_amp_az_ant,
    ds.nr_determina_amp_az_sal,
    ds.data_determina_amp_az_sal,
    ds.nr_atto_liquidazione_amp_az_sal,
    ds.data_atto_liquidazione_amp_az_sal,
    oa.cup_master,
    oa.cup,
    oa.cig,
    oa.data_aggiudicazione,
    oa.data_stipula,
    oa.data_ordine,
    oa.numero_ordine,
    oa.fornitore,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 1::NUMERIC) AS costo_autobus_climatizzato,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 7::NUMERIC) AS costo_sistemi_pmr,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 5::NUMERIC) AS costo_contapasseggeri,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 10::NUMERIC) AS costo_indicatori_linea_percorso,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 8::NUMERIC) AS costo_dispositivi_localizzazione,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 3::NUMERIC) AS costo_sistemi_videosorveglianza,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 6::NUMERIC) AS costo_sistemi_protezione_conducente,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 2::NUMERIC) AS costo_portabici,
    ( SELECT voci.importo
           FROM voci
          WHERE voci.id_costo_fornitura = cf.id_costo_fornitura AND voci.id_voce_costo = 9::NUMERIC) AS costo_predisposizioni_bip,
    tdq.desc_tipo_doc_quietanza AS tipo_doc_quietanza,
    df.nr_quietanza_az_forn AS nr_quietanza,
    df.data_quietanza_az_forn AS data_quietanza,
    dms.primo_telaio_sost AS telaio_veicolo_sostituito,
    NULL::UNKNOWN AS targa_sostituzione,
    CASE  WHEN dms.flg_pannello     THEN 'Si'::TEXT
          WHEN NOT dms.flg_pannello THEN 'No'::TEXT
    END                                                           AS  presenza_pannello,
        CASE
            WHEN (EXISTS ( SELECT 1
               FROM rebusp_t_iter_procedimento ip61_1
              WHERE ip61_1.id_procedimento = p.id_procedimento AND ip61_1.id_stato_iter = 61::NUMERIC)) AND (EXISTS ( SELECT 1
               FROM rebusp_t_iter_procedimento ip62_1
              WHERE ip62_1.id_procedimento = p.id_procedimento AND ip62_1.id_stato_iter = 62::NUMERIC)) THEN TRUE
            ELSE FALSE
        END AS rendicontazione_completa
   FROM rebus_t_variaz_autobus va
     JOIN rebus_t_autobus a ON va.primo_telaio::text = a.primo_telaio::text
     LEFT JOIN ( SELECT pv1.id_procedimento,
            pv1.primo_telaio,
            pv1.id_utente_aggiornamento,
            pv1.data_aggiornamento
           FROM rebusp_r_proc_veicolo pv1,
            rebusp_t_procedimento p1
          WHERE pv1.id_procedimento = p1.id_procedimento AND p1.id_tipo_procedimento = 7::NUMERIC) pv ON a.primo_telaio::text = pv.primo_telaio::text
     LEFT JOIN ( SELECT rebusp_t_procedimento.id_procedimento,
            rebusp_t_procedimento.id_motorizzazione,
            rebusp_t_procedimento.id_tipo_procedimento,
            rebusp_t_procedimento.id_motivazione,
            rebusp_t_procedimento.testo_motiv_altro,
            rebusp_t_procedimento.ruolo_firma,
            rebusp_t_procedimento.ruolo_firma_ente,
            rebusp_t_procedimento.nominativo_firma,
            rebusp_t_procedimento.nominativo_firma_ente,
            rebusp_t_procedimento.premesse,
            rebusp_t_procedimento.prescrizioni,
            rebusp_t_procedimento.num_procedimento,
            rebusp_t_procedimento.note,
            rebusp_t_procedimento.id_utente_aggiornamento,
            rebusp_t_procedimento.data_aggiornamento,
            rebusp_t_procedimento.flg_firma_digitale_ente,
            rebusp_t_procedimento.flg_firma_digitale
           FROM rebusp_t_procedimento
          WHERE rebusp_t_procedimento.id_tipo_procedimento = 7::NUMERIC) p ON pv.id_procedimento = p.id_procedimento
     LEFT JOIN rebusp_t_iter_procedimento ip10 ON p.id_procedimento = ip10.id_procedimento AND ip10.id_stato_iter = 10::NUMERIC
     LEFT JOIN rebus_r_azienda_autobus aa1 ON va.primo_telaio::text = aa1.primo_telaio::text AND aa1.data_alienazione IS NULL
     LEFT JOIN sirtpla_t_soggetto_giuridico sg1 ON aa1.fk_azienda::NUMERIC = sg1.id_soggetto_giuridico
     LEFT JOIN sirtpla_d_natura_giuridica ng1 ON sg1.id_natura_giuridica = ng1.id_natura_giuridica
     LEFT JOIN sirtpl_d_comune com1 ON sg1.id_comune_sede_legale = com1.id_comune
     LEFT JOIN rebus_r_azienda_autobus aa ON va.primo_telaio::text = aa.primo_telaio::text AND ip10.data_inizio_validita >= aa.data_aggiornamento AND ip10.data_inizio_validita <= COALESCE(aa.data_alienazione::TIMESTAMP WITH TIME ZONE, ip10.data_inizio_validita)
     LEFT JOIN sirtpla_t_soggetto_giuridico sg ON aa.fk_azienda::NUMERIC = sg.id_soggetto_giuridico
     LEFT JOIN sirtpla_d_natura_giuridica ng ON sg.id_natura_giuridica = ng.id_natura_giuridica
     LEFT JOIN sirtpl_d_comune com ON sg.id_comune_sede_legale = com.id_comune
     LEFT JOIN ( SELECT MAX(ip.data_inizio_validita) AS max41,
            ip.id_procedimento
           FROM rebusp_t_iter_procedimento ip
          WHERE ip.id_stato_iter = 41::NUMERIC AND NOT (EXISTS ( SELECT 1
                   FROM rebusp_t_iter_procedimento ip1
                  WHERE ip1.id_procedimento = ip.id_procedimento AND ip.id_stato_iter = 51::NUMERIC AND ip1.id_iter_procedimento > ip.id_iter_procedimento))
          GROUP BY ip.id_procedimento) maxip41 ON p.id_procedimento = maxip41.id_procedimento
     LEFT JOIN ( SELECT MAX(ip.data_inizio_validita) AS max42,
            ip.id_procedimento
           FROM rebusp_t_iter_procedimento ip
          WHERE ip.id_stato_iter = 42::NUMERIC AND NOT (EXISTS ( SELECT 1
                   FROM rebusp_t_iter_procedimento ip1
                  WHERE ip1.id_procedimento = ip.id_procedimento AND ip.id_stato_iter = 52::NUMERIC AND ip1.id_iter_procedimento > ip.id_iter_procedimento))
          GROUP BY ip.id_procedimento) maxip42 ON p.id_procedimento = maxip42.id_procedimento
     LEFT JOIN ( SELECT ip.data_inizio_validita,
            ip.id_procedimento
           FROM rebusp_t_iter_procedimento ip
          WHERE ip.id_stato_iter = 61::NUMERIC) ip61 ON p.id_procedimento = ip61.id_procedimento
     LEFT JOIN ( SELECT ip.data_inizio_validita,
            ip.id_procedimento
           FROM rebusp_t_iter_procedimento ip
          WHERE ip.id_stato_iter = 62::NUMERIC) ip62 ON p.id_procedimento = ip62.id_procedimento
     LEFT JOIN rebusc_t_contribuzione c ON p.id_procedimento = c.id_procedimento
     LEFT JOIN rebusc_t_assegnazione_risorse ar ON c.id_assegnazione_risorse = ar.id_assegnazione_risorse
     LEFT JOIN rebusc_t_fonte_finanziamento ff ON ar.id_fonte_finanziamento = ff.id_fonte_finanziamento
     LEFT JOIN rebusc_t_atto_assegnazione aass ON aass.id_atto_assegnazione = ff.id_atto_assegnazione
     JOIN rebus_d_tipo_alimentazione tali ON va.fk_tipo_alimentazione = tali.id_tipo_alimentazione
     JOIN rebus_d_tipo_allestimento tall ON va.fk_tipo_allestimento = tall.id_tipo_allestimento
     JOIN rebus_d_classe_veicolo cv ON va.fk_classe_veicolo = cv.id_classe_veicolo
     JOIN rebus_d_classe_amb_euro cae ON va.fk_classe_ambientale_euro = cae.id_classe_ambientale
     LEFT JOIN rebus_d_tipologia_dimens td ON va.progr_tipo_dimens = td.progr_tipo_dimens AND va.lunghezza >= td.lunghezza_min AND va.lunghezza <= td.lunghezza_max AND va.fk_tipo_allestimento = td.id_tipo_allestimento
     LEFT JOIN rebusc_d_portabici pb ON va.fk_portabici = pb.id_portabici
     LEFT JOIN rebusc_d_sistema_videosorveglianza sv ON va.fk_sistema_videosorveglianza = sv.id_sistema_videosorveglianza
     JOIN rebus_d_dotazione_disabili dd ON va.fk_dotazione_disabili = dd.id_dotazione_disabili
     JOIN rebus_d_impianti_audio ia ON va.fk_impianti_audio = ia.id_impianti_audio
     JOIN rebus_d_impianti_visivi iv ON va.fk_impianti_visivi = iv.id_impianti_visivi
     LEFT JOIN rebusc_d_sistema_localizzazione sl ON va.fk_sistema_localizzazione = sl.id_sistema_localizzazione
     LEFT JOIN rebusc_t_costo_fornitura cf ON c.id_costo_fornitura = cf.id_costo_fornitura
     LEFT JOIN rebusc_t_dato_spesa ds ON c.id_dato_spesa = ds.id_dato_spesa
     LEFT JOIN rebusc_t_dato_fattura df ON ds.id_dato_spesa = df.id_dato_spesa AND df.id_tipo_doc_quietanza IS NOT NULL
     LEFT JOIN rebusc_d_tipo_doc_quietanza tdq ON df.id_tipo_doc_quietanza = tdq.id_tipo_doc_quietanza
     LEFT JOIN rebusc_t_ordine_acquisto oa ON c.id_ordine_acquisto = oa.id_ordine_acquisto
     LEFT JOIN rebusc_t_dato_messa_servizio dms ON c.id_dato_messa_servizio = dms.id_dato_messa_servizio
     LEFT JOIN rebusc_d_tipo_sostituzione ts ON dms.id_tipo_sostituzione = ts.id_tipo_sostituzione
  WHERE va.flg_contribuzione = TRUE;



CREATE OR REPLACE VIEW v_storia_autobus AS
SELECT stovaraut.data_aggiornamento,
    az.id_azienda,
    az.denominazione,
    stovaraut.n_telaio,
    stovaraut.n_targa,
    aut.data_prima_immatricolazione,
    stovaraut.data_ultima_immatricolazione,
    tipalim.descrizione AS alimentazione,
    stovaraut.n_posti_sedere,
    stovaraut.n_posti_in_piedi,
    stovaraut.n_posti_riservati,
    stovaraut.omologazione_direttiva_europea,
    clambeur.descrizione AS omologazione_classe,
    stovaraut.flg_filtro_fap,
    dotdis.descrizione AS facilitazione_disabili,
    impaud.descrizione AS impianti_audio,
    impvis.descrizione AS impianti_visivi,
    stovaraut.flg_rilevatore_bip,
    stovaraut.prezzo_tot_acquisto,
    stovaraut.contributo_pubblico_acquisto,
    stovaraut.flg_veicolo_assicurato,
    stovaraut.data_ultima_revisione,
    stovaraut.note,
    stovaraut.primo_telaio,
    stovaraut.n_matricola_aziendale,
    tipimmatr.descrizione AS tipo_immatricolazione,
    stovaraut.ente_autorizz_prima_imm,
    clveic.descrizione AS classe_veicolo,
    aut.marca,
    aut.modello,
    stovaraut.flg_due_piani,
    stovaraut.flg_snodato::text AS flg_snodato,
    stovaraut.caratteristiche_particolari,
    stovaraut.fk_tipo_alimentazione,
    stovaraut.altra_alimentazione,
    tipallest.descrizione AS tipo_allestimento,
    stovaraut.lunghezza,
    stovaraut.numero_porte,
    stovaraut.posti_carrozzina,
    stovaraut.flg_impianto_condizionamento,
    stovaraut.flg_cabina_guida_isolata,
    dispord.list AS dispositivi_prevenz,
    stovaraut.altri_dispositivi_prevenz_inc,
    stovaraut.flg_otx,
    stovaraut.flg_avm,
    stovaraut.flg_contapasseggeri,
    prop.descrizione AS proprieta_leasing,
    stovaraut.flg_conteggiato_miv,
    ((((((((((((depcom.denom_deposito::text || ' - '::text) || depcom.toponimo_deposito::text) || ' '::text) || depcom.indirizzo_deposito::text) || ' '::text) || depcom.num_civico_deposito::text) || ', '::text) || depcom.cap_deposito::text) || ' '::text) || depcom.denom_comune::text) || ' ('::text) || depcom.sigla_provincia::text) || ')'::text AS deposito,
    tipdim.tipologia_dimens,
    CASE  WHEN stovaraut.flg_alienato = 'N'::CHARACTER VARYING  THEN 'Attivo'::TEXT
          WHEN stovaraut.flg_alienato = 'S'::CHARACTER VARYING  THEN 'Alienato'::TEXT
          WHEN stovaraut.flg_alienato = 'R'::CHARACTER VARYING  THEN 'Ritirato'::TEXT
    END                                                                                         AS  stato_tpl,
    raz.data_alienazione,
        CASE
            WHEN stovaraut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION THEN 'S'::text
            ELSE 'N'::text
        END AS contribuito,
        CASE
            WHEN stovaraut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND aut.data_prima_immatricolazione IS NOT NULL AND stovaraut.fk_tipo_allestimento = 3 THEN aut.data_prima_immatricolazione + '10 years'::INTERVAL
            WHEN stovaraut.contributo_pubblico_acquisto > 0::DOUBLE PRECISION AND aut.data_prima_immatricolazione IS NOT NULL AND stovaraut.fk_tipo_allestimento = 1 THEN aut.data_prima_immatricolazione + '8 years'::INTERVAL
            ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END AS scad_vincoli_no_alien,
    stovaraut.nota_riservata_azienda,
    stovaraut.nota_riservata_amp,
    stovaraut.nota_riservata_rp,
    stovaraut.flg_richiesta_contr,
    stovaraut.anno_sost_prog,
    stovaraut.data_inserimento,
    stovaraut.data_aggiornamento_storia,
    stovaraut.flg_verificato_azienda,
    stovaraut.flg_verificato_amp,
    stovaraut.motivazione
   FROM rebus_t_storia_variaz_autobus stovaraut
     JOIN rebus_r_azienda_autobus raz ON stovaraut.primo_telaio::text = raz.primo_telaio::text AND (raz.data_alienazione IS NOT NULL AND raz.data_aggiornamento < stovaraut.data_aggiornamento_storia OR raz.data_alienazione IS NULL AND raz.data_aggiornamento <= stovaraut.data_aggiornamento)
     JOIN rebus_t_aziende az ON raz.fk_azienda::NUMERIC = az.id_azienda
     JOIN rebus_t_autobus aut ON stovaraut.primo_telaio::text = aut.primo_telaio::text
     JOIN rebus_d_tipo_alimentazione tipalim ON stovaraut.fk_tipo_alimentazione = tipalim.id_tipo_alimentazione
     JOIN rebus_d_classe_amb_euro clambeur ON stovaraut.fk_classe_ambientale_euro = clambeur.id_classe_ambientale
     JOIN rebus_d_dotazione_disabili dotdis ON stovaraut.fk_dotazione_disabili = dotdis.id_dotazione_disabili
     JOIN rebus_d_impianti_audio impaud ON stovaraut.fk_impianti_audio = impaud.id_impianti_audio
     JOIN rebus_d_impianti_visivi impvis ON stovaraut.fk_impianti_visivi = impvis.id_impianti_visivi
     JOIN rebus_d_tipo_immatricol tipimmatr ON stovaraut.fk_tipo_immatricolazione = tipimmatr.id_tipo_immatricolazione
     JOIN rebus_d_classe_veicolo clveic ON stovaraut.fk_classe_veicolo = clveic.id_classe_veicolo
     JOIN rebus_d_tipo_allestimento tipallest ON stovaraut.fk_tipo_allestimento = tipallest.id_tipo_allestimento
     JOIN ( SELECT
                CASE
                    WHEN string_agg(disp.descrizione::text, ', '::text) = 'ND'::text THEN NULL::text
                    ELSE string_agg(disp.descrizione::text, ', '::text)
                END AS list,
            disp.id_storia_variaz_autobus
           FROM ( SELECT vdp.id_storia_variaz_autobus,
                    vdp.id_dispositivo,
                    dp.descrizione
                   FROM rebus_r_storia_varautobus_dp vdp
                     JOIN rebus_d_dispositivi_prevenz dp ON vdp.id_dispositivo = dp.id_dispositivo
                  ORDER BY vdp.id_storia_variaz_autobus, vdp.id_dispositivo) disp
          GROUP BY disp.id_storia_variaz_autobus) dispord ON stovaraut.id_storia_variaz_autobus = dispord.id_storia_variaz_autobus
     JOIN rebus_d_proprieta prop ON stovaraut.fk_proprieta_leasing = prop.id_proprieta
     JOIN rebus_d_tipologia_dimens tipdim ON stovaraut.progr_tipo_dimens = tipdim.progr_tipo_dimens AND stovaraut.fk_tipo_allestimento = tipdim.id_tipo_allestimento
     LEFT JOIN  ( SELECT  dep.id_deposito
                        , dep.denom_deposito
                        , dep.toponimo_deposito
                        , dep.indirizzo_deposito
                        , dep.num_civico_deposito
                        , dep.cap_deposito
                        , com.denom_comune
                        , com.sigla_provincia
                    FROM    sirtpla_t_deposito  dep
                      JOIN  v_comuni_all        com ON    dep.id_comune_deposito =  com.id_comune
                                                      AND dep.data_aggiornamento >= com.data_inizio_validita_comune
                                                      AND dep.data_aggiornamento <= COALESCE(com.data_fine_validita_comune, TO_DATE('31/12/9999'::TEXT, 'DD/MM/YYYY'::TEXT))
                )       depcom  ON  stovaraut.fk_deposito::NUMERIC = depcom.id_deposito;



CREATE OR REPLACE VIEW v_export_ricerca_storia_autobus AS
SELECT v_export_ricerca_autobus.data_aggiornamento,
    v_export_ricerca_autobus.id_azienda,
    v_export_ricerca_autobus.denominazione,
    v_export_ricerca_autobus.n_telaio,
    v_export_ricerca_autobus.n_targa,
    v_export_ricerca_autobus.data_prima_immatricolazione,
    v_export_ricerca_autobus.data_ultima_immatricolazione,
    v_export_ricerca_autobus.alimentazione,
    v_export_ricerca_autobus.n_posti_sedere,
    v_export_ricerca_autobus.n_posti_in_piedi,
    v_export_ricerca_autobus.n_posti_riservati,
    v_export_ricerca_autobus.omologazione_direttiva_europea,
    v_export_ricerca_autobus.omologazione_classe,
    v_export_ricerca_autobus.flg_filtro_fap,
    v_export_ricerca_autobus.facilitazione_disabili,
    v_export_ricerca_autobus.impianti_audio,
    v_export_ricerca_autobus.impianti_visivi,
    v_export_ricerca_autobus.flg_rilevatore_bip,
    v_export_ricerca_autobus.prezzo_tot_acquisto,
    v_export_ricerca_autobus.contributo_pubblico_acquisto,
    v_export_ricerca_autobus.flg_veicolo_assicurato,
    v_export_ricerca_autobus.data_ultima_revisione,
    v_export_ricerca_autobus.note,
    v_export_ricerca_autobus.primo_telaio,
    v_export_ricerca_autobus.n_matricola_aziendale,
    v_export_ricerca_autobus.tipo_immatricolazione,
    v_export_ricerca_autobus.ente_autorizz_prima_imm,
    v_export_ricerca_autobus.classe_veicolo,
    v_export_ricerca_autobus.marca,
    v_export_ricerca_autobus.modello,
    v_export_ricerca_autobus.flg_due_piani,
    v_export_ricerca_autobus.flg_snodato,
    v_export_ricerca_autobus.caratteristiche_particolari,
    v_export_ricerca_autobus.fk_tipo_alimentazione,
    v_export_ricerca_autobus.altra_alimentazione,
    v_export_ricerca_autobus.tipo_allestimento,
    v_export_ricerca_autobus.lunghezza,
    v_export_ricerca_autobus.numero_porte,
    v_export_ricerca_autobus.posti_carrozzina,
    v_export_ricerca_autobus.flg_impianto_condizionamento,
    v_export_ricerca_autobus.flg_cabina_guida_isolata,
    v_export_ricerca_autobus.dispositivi_prevenz,
    v_export_ricerca_autobus.altri_dispositivi_prevenz_inc,
    v_export_ricerca_autobus.flg_otx,
    v_export_ricerca_autobus.flg_avm,
    v_export_ricerca_autobus.flg_contapasseggeri,
    v_export_ricerca_autobus.proprieta_leasing,
    v_export_ricerca_autobus.flg_conteggiato_miv,
    v_export_ricerca_autobus.deposito,
    v_export_ricerca_autobus.tipologia_dimens,
    v_export_ricerca_autobus.stato_tpl,
    v_export_ricerca_autobus.data_alienazione,
    v_export_ricerca_autobus.contribuito,
    v_export_ricerca_autobus.scad_vincoli_no_alien,
    v_export_ricerca_autobus.nota_riservata_azienda,
    v_export_ricerca_autobus.nota_riservata_amp,
    v_export_ricerca_autobus.nota_riservata_rp,
    v_export_ricerca_autobus.flg_richiesta_contr,
    v_export_ricerca_autobus.anno_sost_prog,
    v_export_ricerca_autobus.data_inserimento,
    NULL::TIMESTAMP WITHOUT TIME ZONE AS data_aggiornamento_storia,
    v_export_ricerca_autobus.flg_verificato_azienda,
    v_export_ricerca_autobus.flg_verificato_amp,
    v_export_ricerca_autobus.motivazione,
    v_export_ricerca_autobus.categoria_veicolo
   FROM v_export_ricerca_autobus
UNION ALL
 SELECT v_storia_autobus.data_aggiornamento,
    v_storia_autobus.id_azienda,
    v_storia_autobus.denominazione,
    v_storia_autobus.n_telaio,
    v_storia_autobus.n_targa,
    v_storia_autobus.data_prima_immatricolazione,
    v_storia_autobus.data_ultima_immatricolazione,
    v_storia_autobus.alimentazione,
    v_storia_autobus.n_posti_sedere,
    v_storia_autobus.n_posti_in_piedi,
    v_storia_autobus.n_posti_riservati,
    v_storia_autobus.omologazione_direttiva_europea,
    v_storia_autobus.omologazione_classe,
    v_storia_autobus.flg_filtro_fap,
    v_storia_autobus.facilitazione_disabili,
    v_storia_autobus.impianti_audio,
    v_storia_autobus.impianti_visivi,
    v_storia_autobus.flg_rilevatore_bip,
    v_storia_autobus.prezzo_tot_acquisto,
    v_storia_autobus.contributo_pubblico_acquisto,
    v_storia_autobus.flg_veicolo_assicurato,
    v_storia_autobus.data_ultima_revisione,
    v_storia_autobus.note,
    v_storia_autobus.primo_telaio,
    v_storia_autobus.n_matricola_aziendale,
    v_storia_autobus.tipo_immatricolazione,
    v_storia_autobus.ente_autorizz_prima_imm,
    v_storia_autobus.classe_veicolo,
    v_storia_autobus.marca,
    v_storia_autobus.modello,
    v_storia_autobus.flg_due_piani,
    v_storia_autobus.flg_snodato,
    v_storia_autobus.caratteristiche_particolari,
    v_storia_autobus.fk_tipo_alimentazione,
    v_storia_autobus.altra_alimentazione,
    v_storia_autobus.tipo_allestimento,
    v_storia_autobus.lunghezza,
    v_storia_autobus.numero_porte,
    v_storia_autobus.posti_carrozzina,
    v_storia_autobus.flg_impianto_condizionamento,
    v_storia_autobus.flg_cabina_guida_isolata,
    v_storia_autobus.dispositivi_prevenz,
    v_storia_autobus.altri_dispositivi_prevenz_inc,
    v_storia_autobus.flg_otx,
    v_storia_autobus.flg_avm,
    v_storia_autobus.flg_contapasseggeri,
    v_storia_autobus.proprieta_leasing,
    v_storia_autobus.flg_conteggiato_miv,
    v_storia_autobus.deposito,
    v_storia_autobus.tipologia_dimens,
    v_storia_autobus.stato_tpl,
    v_storia_autobus.data_alienazione,
    v_storia_autobus.contribuito,
    v_storia_autobus.scad_vincoli_no_alien,
    v_storia_autobus.nota_riservata_azienda,
    v_storia_autobus.nota_riservata_amp,
    v_storia_autobus.nota_riservata_rp,
    v_storia_autobus.flg_richiesta_contr,
    v_storia_autobus.anno_sost_prog,
    v_storia_autobus.data_inserimento,
    v_storia_autobus.data_aggiornamento_storia,
    v_storia_autobus.flg_verificato_azienda,
    v_storia_autobus.flg_verificato_amp,
    v_storia_autobus.motivazione,
    NULL::text AS categoria_veicolo
   FROM v_storia_autobus;



CREATE OR REPLACE VIEW v_procedimenti_veicoli AS
SELECT p.id_procedimento AS id_p,
    NULL::NUMERIC AS sub1,
    NULL::NUMERIC AS sub2,
    va.n_targa AS targa,
    va.primo_telaio AS telaio,
    a.marca,
    a.modello,
    c.descrizione AS classe,
    ta.descrizione AS allestimento,
    va.lunghezza,
    TO_CHAR(a.data_prima_immatricolazione::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::text) AS data_prima_immatricolazione,
    TO_CHAR(va.data_ultima_immatricolazione, 'DD/MM/YYYY'::text) AS data_ultima_immatricolazione,
    tdoc.descrizione AS documento,
    doc.id_tipo_documento AS id_tipo_doc,
    va.id_variaz_autobus AS id_va
   FROM rebusp_t_procedimento p
     JOIN rebusp_r_proc_veicolo pv ON p.id_procedimento = pv.id_procedimento
     JOIN rebus_t_autobus a ON pv.primo_telaio::text = a.primo_telaio::text
     JOIN rebus_t_variaz_autobus va ON a.primo_telaio::text = va.primo_telaio::text
     JOIN rebus_d_classe_amb_euro c ON va.fk_classe_ambientale_euro = c.id_classe_ambientale
     JOIN rebus_d_tipo_allestimento ta ON va.fk_tipo_allestimento = ta.id_tipo_allestimento
     LEFT JOIN ( SELECT min(rebus_r_doc_variaz_autobus.id_tipo_documento) AS id_tipo_documento,
            rebus_r_doc_variaz_autobus.id_variaz_autobus
           FROM rebus_r_doc_variaz_autobus
          GROUP BY rebus_r_doc_variaz_autobus.id_variaz_autobus) doc ON va.id_variaz_autobus = doc.id_variaz_autobus
     LEFT JOIN rebus_d_tipo_documento tdoc ON doc.id_tipo_documento = tdoc.id_tipo_documento
  WHERE NOT (EXISTS ( SELECT 1
           FROM rebusp_t_sub_procedimento sp
          WHERE sp.id_sub_procedimento_1 = p.id_procedimento)) AND NOT (EXISTS ( SELECT 1
           FROM rebusp_t_sub_procedimento sp
          WHERE sp.id_sub_procedimento_2 = p.id_procedimento))
UNION
 SELECT p.id_procedimento AS id_p,
    sp1.id_sub_procedimento_1 AS sub1,
    NULL::NUMERIC AS sub2,
    va.n_targa AS targa,
    va.primo_telaio AS telaio,
    a.marca,
    a.modello,
    c.descrizione AS classe,
    ta.descrizione AS allestimento,
    va.lunghezza,
    TO_CHAR(a.data_prima_immatricolazione::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::text) AS data_prima_immatricolazione,
    TO_CHAR(va.data_ultima_immatricolazione, 'DD/MM/YYYY'::text) AS data_ultima_immatricolazione,
    tdoc.descrizione AS documento,
    doc.id_tipo_documento AS id_tipo_doc,
    va.id_variaz_autobus AS id_va
   FROM rebusp_t_procedimento p
     JOIN rebusp_t_sub_procedimento sp1 ON p.id_procedimento = sp1.id_procedimento
     JOIN rebusp_r_proc_veicolo pv ON sp1.id_sub_procedimento_1 = pv.id_procedimento
     JOIN rebus_t_autobus a ON pv.primo_telaio::text = a.primo_telaio::text
     JOIN rebus_t_variaz_autobus va ON a.primo_telaio::text = va.primo_telaio::text
     JOIN rebus_d_classe_amb_euro c ON va.fk_classe_ambientale_euro = c.id_classe_ambientale
     JOIN rebus_d_tipo_allestimento ta ON va.fk_tipo_allestimento = ta.id_tipo_allestimento
     LEFT JOIN ( SELECT MIN(rebus_r_doc_variaz_autobus.id_tipo_documento) AS id_tipo_documento,
            rebus_r_doc_variaz_autobus.id_variaz_autobus
           FROM rebus_r_doc_variaz_autobus
          GROUP BY rebus_r_doc_variaz_autobus.id_variaz_autobus) doc ON va.id_variaz_autobus = doc.id_variaz_autobus
     LEFT JOIN rebus_d_tipo_documento tdoc ON doc.id_tipo_documento = tdoc.id_tipo_documento
UNION
 SELECT p.id_procedimento AS id_p,
    NULL::NUMERIC AS sub1,
    sp2.id_sub_procedimento_2 AS sub2,
    va.n_targa AS targa,
    va.primo_telaio AS telaio,
    a.marca,
    a.modello,
    c.descrizione AS classe,
    ta.descrizione AS allestimento,
    va.lunghezza,
    TO_CHAR(a.data_prima_immatricolazione::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::text) AS data_prima_immatricolazione,
    TO_CHAR(va.data_ultima_immatricolazione, 'DD/MM/YYYY'::text) AS data_ultima_immatricolazione,
    tdoc.descrizione AS documento,
    doc.id_tipo_documento AS id_tipo_doc,
    va.id_variaz_autobus AS id_va
   FROM rebusp_t_procedimento p
     JOIN rebusp_t_sub_procedimento sp2 ON p.id_procedimento = sp2.id_procedimento
     JOIN rebusp_r_proc_veicolo pv ON sp2.id_sub_procedimento_2 = pv.id_procedimento
     JOIN rebus_t_autobus a ON pv.primo_telaio::text = a.primo_telaio::text
     JOIN rebus_t_variaz_autobus va ON a.primo_telaio::text = va.primo_telaio::text
     JOIN rebus_d_classe_amb_euro c ON va.fk_classe_ambientale_euro = c.id_classe_ambientale
     JOIN rebus_d_tipo_allestimento ta ON va.fk_tipo_allestimento = ta.id_tipo_allestimento
     LEFT JOIN ( SELECT min(rebus_r_doc_variaz_autobus.id_tipo_documento) AS id_tipo_documento,
            rebus_r_doc_variaz_autobus.id_variaz_autobus
           FROM rebus_r_doc_variaz_autobus
          GROUP BY rebus_r_doc_variaz_autobus.id_variaz_autobus) doc ON va.id_variaz_autobus = doc.id_variaz_autobus
     LEFT JOIN rebus_d_tipo_documento tdoc ON doc.id_tipo_documento = tdoc.id_tipo_documento
  ORDER BY 1, 2, 3, 4;



CREATE OR REPLACE VIEW v_sirtpl_utente AS
SELECT  sirtpl_t_utente.id_utente
      , sirtpl_t_utente.codice_fiscale        AS  cf
      , sirtpl_t_utente.nome
      , sirtpl_t_utente.cognome
      , sirtpl_t_utente.data_inizio_validita  AS  data_creazione
      , sirtpl_t_utente.data_fine_validita    AS  data_cancellazione
  FROM  sirtpl_t_utente;



CREATE OR REPLACE VIEW v_sirtpl_utente_ente AS
SELECT  utente.id_utente_sog_giurid   AS  id_r_utente_az_ente
      , utente.id_utente              AS  fk_utente
      , CASE WHEN tipsoggiur.cod_tipo_sog_giuridico::TEXT = 'A'::TEXT THEN utente.id_soggetto_giuridico
                                                                      ELSE NULL::INTEGER::NUMERIC
        END                                                                                               AS  fk_azienda
      , CASE WHEN tipsoggiur.cod_tipo_sog_giuridico::TEXT = 'E'::TEXT THEN utente.id_soggetto_giuridico
                                                                      ELSE NULL::INTEGER::NUMERIC
        END                                                                                               AS  fk_ente
      , utente.data_inizio_validita                                                                       AS  data_appartenenza
  FROM    sirtpl_r_utente_sog_giurid    utente
    JOIN  sirtpla_t_soggetto_giuridico  soggiur     ON  utente.id_soggetto_giuridico  = soggiur.id_soggetto_giuridico
    JOIN  sirtpla_d_tipo_sog_giuridico  tipsoggiur  ON  soggiur.id_tipo_sog_giuridico = tipsoggiur.id_tipo_sog_giuridico;



CREATE OR REPLACE VIEW v_sirtpla_enti AS
SELECT  soggiur.id_soggetto_giuridico     AS  id_ente
      , soggiur.denom_soggetto_giuridico  AS  denominazione
  FROM    sirtpla_t_soggetto_giuridico  soggiur
    JOIN  sirtpla_d_tipo_sog_giuridico  tipsoggiur  ON  soggiur.id_tipo_sog_giuridico = tipsoggiur.id_tipo_sog_giuridico
  WHERE tipsoggiur.cod_tipo_sog_giuridico::text = 'E'::TEXT;



CREATE OR REPLACE VIEW v_varautobus_no_dispositivi AS
SELECT  rebus_t_variaz_autobus.id_variaz_autobus
      , rebus_t_variaz_autobus.fk_azienda
      , rebus_t_variaz_autobus.primo_telaio
  FROM          rebus_t_variaz_autobus
    LEFT  JOIN  rebus_r_varautobus_dp   ON  rebus_t_variaz_autobus.id_variaz_autobus = rebus_r_varautobus_dp.id_variaz_autobus
  GROUP BY  rebus_t_variaz_autobus.id_variaz_autobus
          , rebus_t_variaz_autobus.fk_azienda
          , rebus_t_variaz_autobus.primo_telaio
          , rebus_r_varautobus_dp.id_variaz_autobus
  HAVING  rebus_r_varautobus_dp.id_variaz_autobus IS NULL
  ORDER BY  rebus_t_variaz_autobus.id_variaz_autobus;



ALTER TABLE rebus_r_utente_az_ente          OWNER TO rebus;
ALTER TABLE rebus_t_aziende                 OWNER TO rebus;
ALTER TABLE rebus_t_enti                    OWNER TO rebus;
ALTER TABLE rebus_t_utenti                  OWNER TO rebus;
ALTER TABLE v_autobus                       OWNER TO rebus;
ALTER TABLE v_autobus_small                 OWNER TO rebus;
ALTER TABLE v_autobus_small2                OWNER TO rebus;
ALTER TABLE v_export_ricerca_autobus        OWNER TO rebus;
ALTER TABLE v_export_ricerca_contribuzione  OWNER TO rebus;
ALTER TABLE v_export_ricerca_storia_autobus OWNER TO rebus;
ALTER TABLE v_procedimenti_veicoli          OWNER TO rebus;
ALTER TABLE v_sirtpl_utente                 OWNER TO rebus;
ALTER TABLE v_sirtpl_utente_ente            OWNER TO rebus;
ALTER TABLE v_sirtpla_aziende               OWNER TO rebus;
ALTER TABLE v_sirtpla_enti                  OWNER TO rebus;
ALTER TABLE v_storia_autobus                OWNER TO rebus;
ALTER TABLE v_varautobus_no_dispositivi     OWNER TO rebus;
ALTER TABLE v_veicoli_procedimenti          OWNER TO rebus;



GRANT ALL ON TABLE rebus_r_utente_az_ente TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_r_utente_az_ente TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_r_utente_az_ente TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_r_utente_az_ente TO sirtpl_trasv;
GRANT SELECT ON TABLE rebus_r_utente_az_ente TO sirtpl_trasv_ro;
--
GRANT ALL ON TABLE rebus_t_aziende TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_aziende TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_aziende TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_aziende TO sirtpl_trasv;
--
GRANT ALL ON TABLE rebus_t_enti TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_enti TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_enti TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_enti TO sirtpl_trasv;
GRANT SELECT ON TABLE rebus_t_enti TO sirtpl_trasv_ro;
--
GRANT ALL ON TABLE rebus_t_utenti TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_utenti TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_utenti TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebus_t_utenti TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE rebus_t_utenti TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_autobus TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus TO rebus_rw;
GRANT SELECT ON TABLE v_autobus TO sirtpl_trasv;
GRANT SELECT ON TABLE v_autobus TO sirtpl_trasv_rw;
--
GRANT ALL ON TABLE v_autobus_small TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_autobus_small TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_autobus_small2 TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small2 TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small2 TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_autobus_small2 TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_autobus_small2 TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_export_ricerca_autobus TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_autobus TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_autobus TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_autobus TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_export_ricerca_contribuzione TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_contribuzione TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_contribuzione TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_contribuzione TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_export_ricerca_storia_autobus TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_export_ricerca_storia_autobus TO rebus_rw;
GRANT SELECT ON TABLE v_export_ricerca_storia_autobus TO sirtpl_trasv;
GRANT SELECT ON TABLE v_export_ricerca_storia_autobus TO sirtpl_trasv_rw;
--
GRANT ALL ON TABLE v_procedimenti_veicoli TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_procedimenti_veicoli TO rebus_rw;
GRANT SELECT ON TABLE v_procedimenti_veicoli TO sirtpl_trasv;
GRANT SELECT ON TABLE v_procedimenti_veicoli TO sirtpl_trasv_rw;
--
GRANT ALL ON TABLE v_sirtpl_utente TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_sirtpl_utente TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_sirtpl_utente_ente TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente_ente TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente_ente TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_sirtpl_utente_ente TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpl_utente_ente TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_sirtpla_aziende TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_aziende TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_aziende TO sirtpl_trasv_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_aziende TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_sirtpla_enti TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_enti TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_enti TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_sirtpla_enti TO sirtpl_trasv_ro;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_sirtpla_enti TO sirtpl_trasv;
--
GRANT ALL ON TABLE v_storia_autobus TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_storia_autobus TO rebus_rw;
GRANT SELECT ON TABLE v_storia_autobus TO sirtpl_trasv;
GRANT SELECT ON TABLE v_storia_autobus TO sirtpl_trasv_rw;
--
GRANT ALL ON TABLE v_varautobus_no_dispositivi TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_varautobus_no_dispositivi TO rebus_rw;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_varautobus_no_dispositivi TO sirtpl_trasv;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_varautobus_no_dispositivi TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE v_varautobus_no_dispositivi TO sirtpl_trasv_ro;
--
GRANT ALL ON TABLE v_veicoli_procedimenti TO rebus;
GRANT SELECT, DELETE, UPDATE, INSERT ON TABLE v_veicoli_procedimenti TO rebus_rw;
GRANT SELECT ON TABLE v_veicoli_procedimenti TO sirtpl_trasv;
GRANT SELECT ON TABLE v_veicoli_procedimenti TO sirtpl_trasv_rw;
