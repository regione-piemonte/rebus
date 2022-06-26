CREATE OR REPLACE VIEW v_soggetti_coinvolti_periodi AS
WITH proroga AS (
         SELECT sirtplc_t_proroga_contratto.id_contratto,
            max(sirtplc_t_proroga_contratto.data_fine_proroga) AS dfp
           FROM sirtplc_t_proroga_contratto
          GROUP BY sirtplc_t_proroga_contratto.id_contratto
        ), vista AS (
         SELECT sogg_coinv_per.idc,
            sogg_coinv_per.alias,
            sogg_coinv_per.ruolo,
            sogg_coinv_per.d_ini,
            sogg_coinv_per.d_fin,
            sogg_coinv_per.id_sg_aff,
            sogg_coinv_per.sg_aff,
            sogg_coinv_per.id_sg_sost,
            sogg_coinv_per.sg_sost,
            sogg_coinv_per.atto,
            sogg_coinv_per.id_sg,
            sogg_coinv_per.ord,
                CASE
                    WHEN to_date(
                    CASE "position"(sogg_coinv_per.d_fin, 'b'::text)
                        WHEN 2 THEN "substring"(sogg_coinv_per.d_fin, 4, 10)
                        ELSE sogg_coinv_per.d_fin
                    END, 'DD/MM/YYYY'::text) < COALESCE(( SELECT proroga.dfp
                       FROM proroga
                      WHERE proroga.id_contratto = sogg_coinv_per.idc), ( SELECT c.data_fine_validita
                       FROM sirtplc_t_contratto c
                      WHERE c.id_contratto = sogg_coinv_per.idc)) THEN true
                    ELSE false
                END AS scaduto
           FROM ( SELECT sogcoinv.idc,
                    sogcoinv.alias,
                    sogcoinv.ruolo::character varying(100) AS ruolo,
                    sogcoinv.d_ini,
                    sogcoinv.d_fin,
                    sogcoinv.id_sg_aff,
                    sogcoinv.sg_aff::character varying(100) AS sg_aff,
                    sogcoinv.id_sg_sost,
                    sogcoinv.sg_sost::character varying(100) AS sg_sost,
                    sogcoinv.atto::character varying(100) AS atto,
                    sogcoinv.ord,
                    sogcoinv.id_sg
                   FROM ( SELECT contr.id_contratto AS idc,
                            soggiur.denominazione_breve AS alias,
                            'Ente committente'::text AS ruolo,
                            to_char(COALESCE(contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            COALESCE(
                                CASE
                                    WHEN (EXISTS ( SELECT 1
                                       FROM sirtplc_r_sost_sog_contr sgc
                                      WHERE sgc.id_sog_giurid_committente IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT ('<b>'::text || to_char((sgc.data_sostituzione - 1)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text
                                       FROM sirtplc_r_sost_sog_contr sgc
                                      WHERE sgc.id_sog_giurid_committente IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)
                                    ELSE NULL::text
                                END, to_char(proroga.dfp::timestamp with time zone, 'DD/MM/YYYY'::text), to_char(contr.data_fine_validita::timestamp with time zone, 'DD/MM/YYYY'::text), to_char('now'::text::date::timestamp with time zone, 'DD/MM/YYYY'::text)) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            NULL::text AS atto,
                            1 AS ord,
                            soggiur.id_soggetto_giuridico AS id_sg
                           FROM sirtpla_t_soggetto_giuridico soggiur
                             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiur.denominazione_breve::text ||
                                CASE
                                    WHEN contr.id_tipo_sog_giurid_esec = 3::numeric THEN (' ('::text || ((( SELECT sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
                                       FROM sirtplc_d_tipo_raggruppamento
                                      WHERE sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento = contr.id_tipo_raggr_sog_giurid_esec))::text)) || ')'::text
                                    ELSE ''::text
                                END AS alias,
                            'Esecutore titolare'::text AS ruolo,
                            to_char(COALESCE(contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            COALESCE(
                                CASE
                                    WHEN (EXISTS ( SELECT 1
                                       FROM sirtplc_r_sost_sog_contr sgc
                                      WHERE sgc.id_sog_giurid_esecutore IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT ('<b>'::text || to_char((sgc.data_sostituzione - 1)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text
                                       FROM sirtplc_r_sost_sog_contr sgc
                                      WHERE sgc.id_sog_giurid_esecutore IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)
                                    ELSE NULL::text
                                END, to_char(proroga.dfp::timestamp with time zone, 'DD/MM/YYYY'::text), to_char(contr.data_fine_validita::timestamp with time zone, 'DD/MM/YYYY'::text), to_char('now'::text::date::timestamp with time zone, 'DD/MM/YYYY'::text)) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            NULL::text AS atto,
                            5 AS ord,
                            soggiur.id_soggetto_giuridico AS id_sg
                           FROM sirtpla_t_soggetto_giuridico soggiur
                             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_esecutore
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiur.denominazione_breve::text ||
                                CASE
                                    WHEN contrraggr.capofila THEN ' (capofila)'::text
                                    ELSE ''::text
                                END AS alias,
                            'Parte del raggruppamento'::text AS ruolo,
                            to_char(COALESCE(contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            COALESCE(to_char(soggiur.data_cessazione::timestamp with time zone, 'DD/MM/YYYY'::text),
                                CASE
                                    WHEN (EXISTS ( SELECT 1
                                       FROM sirtplc_r_sost_sog_contr_raggr sgcr
                                         JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                                      WHERE cr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico AND sgcr.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT ('<b>'::text || to_char((sgcr.data_sostituzione - 1)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text
                                       FROM sirtplc_r_sost_sog_contr_raggr sgcr
                                         JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                                      WHERE cr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico AND sgcr.id_tipo_sostituzione = 2::numeric AND cr.id_contratto = contr.id_contratto)
                                    ELSE NULL::text
                                END, to_char(proroga.dfp::timestamp with time zone, 'DD/MM/YYYY'::text), to_char(contr.data_fine_validita::timestamp with time zone, 'DD/MM/YYYY'::text), to_char('now'::text::date::timestamp with time zone, 'DD/MM/YYYY'::text)) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            NULL::text AS atto,
                            10 AS ord,
                            soggiur.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                             JOIN sirtpla_t_soggetto_giuridico soggiur ON contrraggr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE contr.id_sog_giurid_esecutore <> contrraggr.id_soggetto_giuridico
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiursubaff.denominazione_breve AS alias,
                            'Subaffidataria'::text AS ruolo,
                            to_char(COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            to_char(COALESCE(soggiursubaff.data_cessazione, sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            soggiuresec.id_soggetto_giuridico AS id_sg_aff,
                            soggiuresec.denominazione_breve AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            sostsogcontr.atto_sostituzione AS atto,
                            15 AS ord,
                            soggiursubaff.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
                             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON contr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontr.id_tipo_sostituzione = 1::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiursubaff.denominazione_breve AS alias,
                            'Subaffidataria'::text AS ruolo,
                            to_char(COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            to_char(COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            soggiurraggr.id_soggetto_giuridico AS id_sg_aff,
                            soggiurraggr.denominazione_breve AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            sostsogcontrraggr.atto_sostituzione AS atto,
                            16 AS ord,
                            soggiursubaff.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
                             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontrraggr.id_tipo_sostituzione = 1::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiursubaff.denominazione_breve AS alias,
                            'Somministrazione di personale'::text AS ruolo,
                            to_char(COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            to_char(COALESCE(soggiursubaff.data_cessazione, sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            soggiuresec.id_soggetto_giuridico AS id_sg_aff,
                            soggiuresec.denominazione_breve AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            sostsogcontr.atto_sostituzione AS atto,
                            20 AS ord,
                            soggiursubaff.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
                             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON contr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontr.id_tipo_sostituzione = 3::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            soggiursubaff.denominazione_breve AS alias,
                            'Somministrazione di personale'::text AS ruolo,
                            to_char(COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_ini,
                            to_char(COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            soggiurraggr.id_soggetto_giuridico AS id_sg_aff,
                            soggiurraggr.denominazione_breve AS sg_aff,
                            NULL::numeric AS id_sg_sost,
                            NULL::text AS sg_sost,
                            sostsogcontrraggr.atto_sostituzione AS atto,
                            21 AS ord,
                            soggiursubaff.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
                             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontrraggr.id_tipo_sostituzione = 3::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            ('<b>'::text || soggiurcomm.denominazione_breve::text) || '<b>'::text AS alias,
                            'Ente committente'::text AS ruolo,
                            ('<b>'::text || to_char(COALESCE(sostsogcomm.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text AS d_ini,
                            to_char(COALESCE(sostsogcomm.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            soggiurcommsost.id_soggetto_giuridico AS id_sg_sost,
                            soggiurcommsost.denominazione_breve AS sg_sost,
                            sostsogcomm.atto_sostituzione AS atto,
                            1 AS ord,
                            soggiurcomm.id_soggetto_giuridico AS id_sg
                           FROM sirtpla_t_soggetto_giuridico soggiur
                             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
                             JOIN sirtplc_r_sost_sog_contr sostsogcomm ON contr.id_contratto = sostsogcomm.id_contratto
                             JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON sostsogcomm.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiurcommsost ON contr.id_sog_giurid_committente = soggiurcommsost.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcomm.id_tipo_sostituzione = 2::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            ('<b>'::text || soggiuresec.denominazione_breve::text) || '<b>'::text AS alias,
                            'Esecutore titolare'::text AS ruolo,
                            ('<b>'::text || to_char(COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text AS d_ini,
                            to_char(COALESCE(sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            soggiuresecsost.id_soggetto_giuridico AS id_sg_sost,
                            soggiuresecsost.denominazione_breve AS sg_sost,
                            sostsogcontr.atto_sostituzione AS atto,
                            5 AS ord,
                            soggiuresec.id_soggetto_giuridico AS id_sg
                           FROM sirtpla_t_soggetto_giuridico soggiur
                             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
                             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
                             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON sostsogcontr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiuresecsost ON contr.id_sog_giurid_esecutore = soggiuresecsost.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontr.id_tipo_sostituzione = 2::numeric
                        UNION
                         SELECT contr.id_contratto AS idc,
                            ('<b>'::text || soggiursubaff.denominazione_breve::text) || '<b>'::text AS alias,
                            'Parte del raggruppamento'::text AS ruolo,
                            ('<b>'::text || to_char(COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text)) || '<b>'::text AS d_ini,
                            to_char(COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date)::timestamp with time zone, 'DD/MM/YYYY'::text) AS d_fin,
                            NULL::numeric AS id_sg_aff,
                            NULL::text AS sg_aff,
                            soggiurraggr.id_soggetto_giuridico AS id_sg_sost,
                            soggiurraggr.denominazione_breve AS sg_sost,
                            sostsogcontrraggr.atto_sostituzione AS atto,
                            18 AS ord,
                            soggiursubaff.id_soggetto_giuridico AS id_sg
                           FROM sirtplc_t_contratto contr
                             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
                             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
                             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                          WHERE sostsogcontrraggr.id_tipo_sostituzione = 2::numeric) sogcoinv
                  ORDER BY sogcoinv.idc, sogcoinv.ord, sogcoinv.alias) sogg_coinv_per
        )
 SELECT vista.idc,
    vista.alias,
    vista.ruolo,
    vista.d_ini,
    vista.d_fin,
    vista.id_sg_aff,
    vista.sg_aff,
    vista.id_sg_sost,
    vista.sg_sost,
    vista.atto,
    vista.id_sg,
    vista.ord,
    vista.scaduto,
        CASE
            WHEN vista.scaduto THEN NOT vista.scaduto
            WHEN NOT vista.scaduto AND to_date(
            CASE "position"(vista.d_fin, 'b'::text)
                WHEN 2 THEN "substring"(vista.d_fin, 4, 10)
                ELSE vista.d_fin
            END, 'DD/MM/YYYY'::text) < 'now'::text::date THEN false
            ELSE NOT vista.scaduto
        END AS attivo
   FROM vista
  ORDER BY vista.idc, vista.ord, (
        CASE
            WHEN vista.scaduto THEN 0
            ELSE 1
        END) DESC, vista.alias
;




CREATE OR REPLACE VIEW v_contratti_soggetti AS
  WITH vista AS
  ( SELECT  contr_sogg.id_sg
          , contr_sogg.contratto
          , contr_sogg.ente_comm
          , contr_sogg.d_ini_c
          , contr_sogg.d_fin_c
          , contr_sogg.ruolo
          , contr_sogg.capofila
          , contr_sogg.sg_aff
          , contr_sogg.d_ini_a
          , contr_sogg.d_fin_a
          , contr_sogg.id_c
          , CASE   WHEN     contr_sogg.d_fin_c < 'now'::TEXT::DATE
                        OR  TO_DATE ( CASE "position"(contr_sogg.d_fin_a, 'b'::TEXT)
                                        WHEN  2 THEN "substring"(contr_sogg.d_fin_a, 4, 10)
                                                ELSE contr_sogg.d_fin_a
                                      END
                                            , 'DD/MM/YYYY'::TEXT
                                    ) < contr_sogg.d_fin_c
              THEN  TRUE
              ELSE  FALSE
            END                                                                     AS  scaduto
      FROM  ( WITH proroga AS
              ( SELECT  sirtplc_t_proroga_contratto.id_contratto
                      , MAX(sirtplc_t_proroga_contratto.data_fine_proroga)  AS  dfp
                  FROM  sirtplc_t_proroga_contratto
                  GROUP BY  sirtplc_t_proroga_contratto.id_contratto
              )
              SELECT  soggiur.id_soggetto_giuridico                                                                                                 AS id_sg
                    , COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT   AS  contratto
                    , soggiur.denominazione_breve                                                                                                   AS  ente_comm
                    , COALESCE(contr.data_inizio_validita,'now'::TEXT::DATE)                                                                        AS  d_ini_c
                    , COALESCE(proroga.dfp, contr.data_fine_validita,'now'::TEXT::DATE)                                                             AS  d_fin_c
                    , 'Ente committente'::TEXT                                                                                                      AS  ruolo
                    , NULL::TEXT                                                                                                                    AS  capofila
                    , NULL::TEXT                                                                                                                    AS  sg_aff
                    , TO_CHAR(COALESCE(contr.data_inizio_validita,'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE,'DD/MM/YYYY'::TEXT)                  AS  d_ini_a
                    , COALESCE( CASE  WHEN  ( EXISTS  ( SELECT  1
                                                          FROM  sirtplc_r_sost_sog_contr
                                                          WHERE id_sog_giurid_committente IS NOT NULL
                                                            AND id_contratto = contr.id_contratto
                                                            AND id_tipo_sostituzione = 2::NUMERIC
                                                      )
                                            )
                                  THEN  ( SELECT  ('<b>'::TEXT || TO_CHAR((data_sostituzione - 1)::TIMESTAMP WITH TIME ZONE,'DD/MM/YYYY'::TEXT)) || '<b>'::TEXT
                                            FROM  sirtplc_r_sost_sog_contr
                                            WHERE id_sog_giurid_committente IS NOT NULL
                                              AND id_contratto = contr.id_contratto
                                              AND id_tipo_sostituzione = 2::NUMERIC
                                        )
                                  ELSE  NULL::TEXT
                                END
                              , TO_CHAR(proroga.dfp::TIMESTAMP WITH TIME ZONE,'DD/MM/YYYY'::TEXT), TO_CHAR(contr.data_fine_validita::TIMESTAMP WITH TIME ZONE,'DD/MM/YYYY'::TEXT), TO_CHAR('now'::TEXT::DATE::TIMESTAMP WITH TIME ZONE,'DD/MM/YYYY'::TEXT)
                              )                                                                                                                     AS  d_fin_a
                    , contr.id_contratto                                                                                                            AS  id_c
                FROM          sirtplc_t_contratto           contr
                  JOIN        sirtpla_t_soggetto_giuridico  soggiur   ON  contr.id_sog_giurid_committente = soggiur.id_soggetto_giuridico
                  LEFT  JOIN  proroga                                 ON  contr.id_contratto              = proroga.id_contratto
              UNION
              SELECT  sogesec.id_soggetto_giuridico                                                                                                 AS  id_sg
                    , COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT   AS  contratto
                    , sogcomm.denominazione_breve                                                                                                   AS  ente_comm
                    , COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE)                                                                       AS  d_ini_c
                    , COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)                                                            AS  d_fin_c
                    , 'Esecutore titolare'::TEXT                                                                                                    AS  ruolo
                    , NULL::TEXT                                                                                                                    AS  capofila
                    , NULL::TEXT                                                                                                                    AS  sg_aff
                    , TO_CHAR(COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)                AS  d_ini_a
                    , COALESCE( CASE  WHEN  ( EXISTS  ( SELECT  1
                                                          FROM  sirtplc_r_sost_sog_contr
                                                          WHERE id_sog_giurid_esecutore IS NOT NULL
                                                            AND id_contratto = contr.id_contratto
                                                            AND id_tipo_sostituzione = 2::NUMERIC
                                                      )
                                            )
                                  THEN  ( SELECT  ('<b>'::TEXT || TO_CHAR((data_sostituzione - 1)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)) || '<b>'::TEXT
                                            FROM sirtplc_r_sost_sog_contr
                                            WHERE id_sog_giurid_esecutore IS NOT NULL
                                              AND id_contratto = contr.id_contratto
                                              AND id_tipo_sostituzione = 2::NUMERIC
                                        )
                                  ELSE  NULL::TEXT
                                END
                              , TO_CHAR(proroga.dfp::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT), TO_CHAR(contr.data_fine_validita::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT), TO_CHAR('now'::TEXT::DATE::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)
                              )                                                                                                                     AS  d_fin_a
                    , contr.id_contratto                                                                                                            AS  id_c
                FROM          sirtplc_t_contratto contr
                        JOIN  sirtpla_t_soggetto_giuridico  sogcomm ON  contr.id_sog_giurid_committente = sogcomm.id_soggetto_giuridico
                        JOIN  sirtpla_t_soggetto_giuridico  sogesec ON  contr.id_sog_giurid_esecutore   = sogesec.id_soggetto_giuridico
                  LEFT  JOIN  proroga                               ON  contr.id_contratto              = proroga.id_contratto
              UNION
              SELECT  sograggr.id_soggetto_giuridico                                                                                                AS  id_sg
                    , COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT   AS  contratto
                    , sogcomm.denominazione_breve                                                                                                   AS  ente_comm
                    , COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE)                                                                       AS  d_ini_c
                    , COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)                                                            AS  d_fin_c
                    , 'Parte del raggruppamento'::TEXT                                                                                              AS  ruolo
                    , sograggrcf.denominazione_breve                                                                                                AS  capofila
                    , NULL::TEXT                                                                                                                    AS  sg_aff
                    , TO_CHAR(COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)                AS  d_ini_a
                    , COALESCE(TO_CHAR(sograggr.data_cessazione::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT),
                        CASE
                            WHEN (EXISTS ( SELECT 1
                               FROM sirtplc_r_sost_sog_contr_raggr sgcr
                                 JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                              WHERE cr.id_soggetto_giuridico = sograggr.id_soggetto_giuridico AND cr.id_contratto = contr.id_contratto AND sgcr.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT ('<b>'::text || TO_CHAR((sgcr.data_sostituzione - 1)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)) || '<b>'::TEXT
                               FROM sirtplc_r_sost_sog_contr_raggr sgcr
                                 JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                              WHERE cr.id_soggetto_giuridico = sograggr.id_soggetto_giuridico AND cr.id_contratto = contr.id_contratto AND sgcr.id_tipo_sostituzione = 2::NUMERIC)
                            ELSE NULL::TEXT
                        END, TO_CHAR(proroga.dfp::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT), TO_CHAR(contr.data_fine_validita::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT), to_char('now'::text::date::timestamp with time zone, 'DD/MM/YYYY'::text)) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico sograggr ON contrraggr.id_soggetto_giuridico = sograggr.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico sogcomm ON contr.id_sog_giurid_committente = sogcomm.id_soggetto_giuridico
                     JOIN sirtplc_r_contratto_raggrupp contrraggrcf ON contr.id_contratto = contrraggrcf.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico sograggrcf ON contrraggrcf.id_soggetto_giuridico = sograggrcf.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE contrraggrcf.capofila AND sograggrcf.id_soggetto_giuridico <> sograggr.id_soggetto_giuridico
                UNION
                 SELECT soggiursubaff.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT AS contratto,
                    sogcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Subaffidataria'::text AS ruolo,
                    NULL::text AS capofila,
                    soggiuraff.denominazione_breve AS sg_aff,
                    TO_CHAR(COALESCE(soggiurcontr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_ini_a,
                    TO_CHAR(COALESCE(soggiursubaff.data_cessazione, soggiursubaff.data_cessazione, soggiurcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_sost_sog_contr soggiurcontr ON contr.id_contratto = soggiurcontr.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON soggiurcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico sogcomm ON contr.id_sog_giurid_committente = sogcomm.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiuraff ON contr.id_sog_giurid_esecutore = soggiuraff.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontr.id_tipo_sostituzione = 1::NUMERIC
                UNION
                 SELECT soggiursubaff.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::text AS contratto,
                    soggiurcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::text::date) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::text::date) AS d_fin_c,
                    'Somministrazione di personale'::text AS ruolo,
                    NULL::TEXT AS capofila,
                    soggiuraff.denominazione_breve AS sg_aff,
                    TO_CHAR(COALESCE(soggiurcontr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_ini_a,
                    TO_CHAR(COALESCE(soggiursubaff.data_cessazione, soggiurcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::Date)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_sost_sog_contr soggiurcontr ON contr.id_contratto = soggiurcontr.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON soggiurcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON contr.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiuraff ON contr.id_sog_giurid_esecutore = soggiuraff.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontr.id_tipo_sostituzione = 3::numeric
                UNION
                 SELECT soggiursubaff.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::text, contr.cod_id_nazionale::text || ' - '::text, ''::text) || contr.desc_contratto::text AS contratto,
                    soggiurcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Subaffidataria'::TEXT AS ruolo,
                    soggiurraggrcf.denominazione_breve AS capofila,
                    soggiuraff.denominazione_breve AS sg_aff,
                    TO_CHAR(COALESCE(soggiurcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_ini_a,
                    TO_CHAR(COALESCE(soggiursubaff.data_cessazione, soggiurcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                     JOIN sirtplc_r_sost_sog_contr_raggr soggiurcontrraggr ON contrraggr.id_contratto_raggrupp = soggiurcontrraggr.id_contratto_raggrupp
                     JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON soggiurcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON contr.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                     JOIN sirtplc_r_contratto_raggrupp contrraggrcf ON contr.id_contratto = contrraggrcf.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiurraggrcf ON contrraggrcf.id_soggetto_giuridico = soggiurraggrcf.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiuraff ON contrraggr.id_soggetto_giuridico = soggiuraff.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontrraggr.id_tipo_sostituzione = 1::NUMERIC AND contrraggrcf.capofila
                UNION
                 SELECT soggiursubaff.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT AS contratto,
                    soggiurcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Somministrazione di personale'::TEXT AS ruolo,
                    soggiurraggrcf.denominazione_breve AS capofila,
                    soggiuraff.denominazione_breve AS sg_aff,
                    TO_CHAR(COALESCE(soggiurcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_ini_a,
                    TO_CHAR(COALESCE(soggiursubaff.data_cessazione, soggiurcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                     JOIN sirtplc_r_sost_sog_contr_raggr soggiurcontrraggr ON contrraggr.id_contratto_raggrupp = soggiurcontrraggr.id_contratto_raggrupp
                     JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON soggiurcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON contr.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                     JOIN sirtplc_r_contratto_raggrupp contrraggrcf ON contr.id_contratto = contrraggrcf.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiurraggrcf ON contrraggrcf.id_soggetto_giuridico = soggiurraggrcf.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiuraff ON contrraggr.id_soggetto_giuridico = soggiuraff.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontrraggr.id_tipo_sostituzione = 3::NUMERIC AND contrraggrcf.capofila
                UNION
                 SELECT soggiursubentr.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT AS contratto,
                    soggiursubentr.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Ente committente'::TEXT AS ruolo,
                    NULL::TEXT AS capofila,
                    NULL::TEXT AS sg_aff,
                    ('<b>'::TEXT || TO_CHAR(COALESCE(soggiurcontr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)) || '<b>'::TEXT AS d_ini_a,
                    to_char(COALESCE(soggiurcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_sost_sog_contr soggiurcontr ON contr.id_contratto = soggiurcontr.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiursubentr ON soggiurcontr.id_sog_giurid_committente = soggiursubentr.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontr.id_tipo_sostituzione = 2::NUMERIC
                UNION
                 SELECT soggiursubentr.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT AS contratto,
                    soggiurcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Esecutore titolare'::TEXT AS ruolo,
                    NULL::TEXT AS capofila,
                    NULL::TEXT AS sg_aff,
                    ('<b>'::TEXT || TO_CHAR(COALESCE(soggiurcontr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)) || '<b>'::text AS d_ini_a,
                    to_char(COALESCE(soggiurcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_sost_sog_contr soggiurcontr ON contr.id_contratto = soggiurcontr.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiursubentr ON soggiurcontr.id_sog_giurid_esecutore = soggiursubentr.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON contr.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontr.id_tipo_sostituzione = 2::NUMERIC
                UNION
                 SELECT soggiursubaff.id_soggetto_giuridico AS id_sg,
                    COALESCE(contr.cod_id_nazionale::TEXT, contr.cod_id_nazionale::TEXT || ' - '::TEXT, ''::TEXT) || contr.desc_contratto::TEXT AS contratto,
                    soggiurcomm.denominazione_breve AS ente_comm,
                    COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini_c,
                    COALESCE(proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin_c,
                    'Parte del raggruppamento'::TEXT AS ruolo,
                    soggiurraggrcf.denominazione_breve AS capofila,
                    NULL::TEXT AS sg_aff,
                    ('<b>'::TEXT || TO_CHAR(COALESCE(soggiurcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT)) || '<b>'::text AS d_ini_a,
                    to_char(COALESCE(soggiursubaff.data_cessazione, soggiurcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE)::TIMESTAMP WITH TIME ZONE, 'DD/MM/YYYY'::TEXT) AS d_fin_a,
                    contr.id_contratto AS id_c
                   FROM sirtplc_t_contratto contr
                     JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
                     JOIN sirtplc_r_sost_sog_contr_raggr soggiurcontrraggr ON contrraggr.id_contratto_raggrupp = soggiurcontrraggr.id_contratto_raggrupp
                     JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON soggiurcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
                     JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON contr.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
                     JOIN sirtplc_r_contratto_raggrupp contrraggrcf ON contr.id_contratto = contrraggrcf.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico soggiurraggrcf ON contrraggrcf.id_soggetto_giuridico = soggiurraggrcf.id_soggetto_giuridico
                     LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
                  WHERE soggiurcontrraggr.id_tipo_sostituzione = 2::NUMERIC AND contrraggrcf.capofila) contr_sogg
        )
( SELECT vista.id_sg,
    vista.contratto,
    vista.ente_comm,
    vista.d_ini_c,
    vista.d_fin_c,
    vista.ruolo,
    vista.capofila,
    vista.sg_aff,
    vista.d_ini_a,
    vista.d_fin_a,
    vista.id_c,
    vista.scaduto
   FROM vista
  WHERE NOT vista.scaduto
  ORDER BY vista.d_ini_c DESC)
UNION ALL
( SELECT vista.id_sg,
    vista.contratto,
    vista.ente_comm,
    vista.d_ini_c,
    vista.d_fin_c,
    vista.ruolo,
    vista.capofila,
    vista.sg_aff,
    vista.d_ini_a,
    vista.d_fin_a,
    vista.id_c,
    vista.scaduto
   FROM vista
  WHERE vista.scaduto
  ORDER BY vista.d_fin_c DESC)
;




CREATE OR REPLACE VIEW v_export_ricerca_contratti AS
SELECT contratto.cod_id_nazionale,
    contratto.cod_id_regionale AS codregionale,
    contratto.num_repertorio,
    bacino.denom_bacino AS bacino,
    tipo_affidamento.desc_tipo_affidamento AS tipo_affidamento,
    modalita_affidamento.desc_modalita_affidamento AS modalita_affidamento,
    contratto.id_sog_giurid_committente,
    contratto.id_sog_giurid_esecutore,
    ( SELECT string_agg(table_temp.colomn, ','::TEXT) AS string_agg
           FROM sirtplc_r_contr_amb_tip_serv tip_serv
             JOIN ( SELECT tip_servizio.id_amb_tip_servizio,
                    (ambser.desc_ambito_servizio::TEXT || ' - '::TEXT) || ts.desc_tipologia_servizio::TEXT AS colomn
                   FROM sirtplc_r_amb_tip_servizio tip_servizio
                     JOIN sirtplc_d_ambito_servizio ambser ON ambser.id_ambito_servizio = tip_servizio.id_ambito_servizio
                     JOIN sirtplc_d_tipologia_servizio ts ON ts.id_tipologia_servizio = tip_servizio.id_tipologia_servizio
                  GROUP BY tip_servizio.id_amb_tip_servizio, ((ambser.desc_ambito_servizio::TEXT || ' - '::TEXT) || ts.desc_tipologia_servizio::TEXT)
                  ORDER BY tip_servizio.id_amb_tip_servizio) table_temp ON table_temp.id_amb_tip_servizio = tip_serv.id_amb_tip_servizio
          WHERE tip_serv.id_contratto = contratto.id_contratto) AS tipologia_ambito_servizio,
    contratto.accordo_programma,
    contratto.gross_cost,
    contratto.cig,
    contratto.data_stipula,
    contratto.data_inizio_validita,
    contratto.data_fine_validita,
    ente_committente.denom_soggetto_giuridico AS sog_giurid_committente,
    ente_committente.denominazione_breve AS sog_giurid_committente_breve,
    ente_committente.denominazione_aaep AS sog_giurid_committente_aaep,
    soggetto_giuridico.denom_soggetto_giuridico AS sog_giurid_esecutore,
    soggetto_giuridico.denominazione_breve AS sog_giurid_esecutore_breve,
    soggetto_giuridico.denominazione_aaep AS sog_giurid_esecutore_aaep,
    tipo_sog.desc_tipo_sog_giuridico AS tipo_sog_giurid_esec,
        CASE
            WHEN tipo_sog.id_tipo_sog_giuridico = 3::NUMERIC THEN raggruppamento.desc_tipo_raggruppamento::text
            ELSE NULL::text
        END AS tipo_raggr_sog_giurid_esec,
        CASE
            WHEN tipo_sog.id_tipo_sog_giuridico = 3::NUMERIC THEN azienda_mandataria.denom_soggetto_giuridico::text
            ELSE NULL::text
        END AS azienda_mandataria,
        CASE
            WHEN tipo_sog.id_tipo_sog_giuridico = 3::NUMERIC THEN azienda_mandataria.denominazione_breve::text
            ELSE NULL::text
        END AS azienda_mandataria_breve,
        CASE
            WHEN tipo_sog.id_tipo_sog_giuridico = 3::NUMERIC THEN azienda_mandataria.denominazione_aaep::text
            ELSE NULL::TEXT
        END AS azienda_mandataria_aaep,
    ( SELECT string_agg(sg.denom_soggetto_giuridico::TEXT, ','::TEXT) AS string_agg
           FROM sirtplc_r_contratto_raggrupp contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico sg ON contratto_raggrupp.id_soggetto_giuridico = sg.id_soggetto_giuridico
          WHERE contratto_raggrupp.id_contratto = contratto.id_contratto AND contratto_raggrupp.capofila IS FALSE) AS composizione_raggruppamento,
    ( SELECT string_agg(sg.denominazione_breve::TEXT, ','::TEXT) AS string_agg
           FROM sirtplc_r_contratto_raggrupp contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico sg ON contratto_raggrupp.id_soggetto_giuridico = sg.id_soggetto_giuridico
          WHERE contratto_raggrupp.id_contratto = contratto.id_contratto AND contratto_raggrupp.capofila IS FALSE) AS composizione_raggruppamento_breve,
        CASE
            WHEN tipo_sog.id_tipo_sog_giuridico = 3::NUMERIC THEN azienda_mandataria.cod_osservatorio_naz::TEXT
            ELSE NULL::TEXT
        END AS cod_oss_azienda_mandataria,
    contratto.desc_contratto,
    contratto.id_utente_aggiornamento AS utente_aggiornamento,
    contratto.data_aggiornamento,
    max_proroga.atto_proroga,
    max_proroga.data_fine_proroga,
    max_proroga.id_utente_aggiornamento,
    ente_committente.cod_osservatorio_naz AS cod_oss_sog_giurid_committente,
    soggetto_giuridico.cod_osservatorio_naz AS cod_oss_sog_giurid_esecutore
   FROM sirtplc_t_contratto contratto
     LEFT JOIN sirtplc_d_bacino bacino ON contratto.id_bacino = bacino.id_bacino
     LEFT JOIN sirtplc_d_tipo_affidamento tipo_affidamento ON contratto.id_tipo_affidamento = tipo_affidamento.id_tipo_affidamento
     LEFT JOIN sirtplc_d_modalita_affidamento modalita_affidamento ON contratto.id_modalita_affidamento = modalita_affidamento.id_modalita_affidamento
     JOIN sirtpla_t_soggetto_giuridico ente_committente ON contratto.id_sog_giurid_committente = ente_committente.id_soggetto_giuridico
     JOIN sirtpla_t_soggetto_giuridico soggetto_giuridico ON contratto.id_sog_giurid_esecutore = soggetto_giuridico.id_soggetto_giuridico
     JOIN sirtpla_d_tipo_sog_giuridico tipo_sog ON contratto.id_tipo_sog_giurid_esec = tipo_sog.id_tipo_sog_giuridico
     LEFT JOIN sirtplc_d_tipo_raggruppamento raggruppamento ON contratto.id_tipo_raggr_sog_giurid_esec = raggruppamento.id_tipo_raggruppamento
     LEFT JOIN ( SELECT contratto_raggrupp.id_contratto,
            sogg_azienda_mandataria.denom_soggetto_giuridico,
            sogg_azienda_mandataria.denominazione_breve,
            sogg_azienda_mandataria.denominazione_aaep,
            sogg_azienda_mandataria.cod_osservatorio_naz
           FROM sirtplc_r_contratto_raggrupp contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico sogg_azienda_mandataria ON sogg_azienda_mandataria.id_soggetto_giuridico = contratto_raggrupp.id_soggetto_giuridico
          WHERE contratto_raggrupp.capofila IS TRUE) azienda_mandataria ON contratto.id_contratto = azienda_mandataria.id_contratto
     LEFT JOIN ( SELECT proroga.id_proroga_contratto,
            proroga.id_contratto,
            proroga.atto_proroga,
            proroga.data_fine_proroga,
            proroga.id_utente_aggiornamento,
            proroga.data_aggiornamento
           FROM sirtplc_t_proroga_contratto proroga
             JOIN ( SELECT sirtplc_t_proroga_contratto.id_contratto,
                    sirtplc_t_proroga_contratto.data_fine_proroga,
                    max(sirtplc_t_proroga_contratto.data_fine_proroga) OVER (PARTITION BY sirtplc_t_proroga_contratto.id_contratto) AS max_data_proroga
                   FROM sirtplc_t_proroga_contratto) ricerca_proroga ON ricerca_proroga.id_contratto = proroga.id_contratto AND proroga.data_fine_proroga = ricerca_proroga.max_data_proroga
          WHERE ricerca_proroga.data_fine_proroga = ricerca_proroga.max_data_proroga) max_proroga ON max_proroga.id_contratto = contratto.id_contratto
;





CREATE OR REPLACE VIEW v_export_ricerca_sogg_giur AS
SELECT soggetto_giuridico.id_soggetto_giuridico,
    soggetto_giuridico.id_tipo_sog_giuridico AS id_tipo_soggetto_giuridico,
    tiposogg.desc_tipo_sog_giuridico AS tipo_soggetto_giuridico,
    natura.desc_natura_giuridica AS natura_giuridica,
    BTRIM(CONCAT(soggetto_giuridico.denominazione_breve, ' ', COALESCE(natura.desc_breve_natura_giuridica, ''::CHARACTER VARYING))) AS denominazione_ricerca,
    soggetto_giuridico.denominazione_breve,
        CASE
            WHEN tiposogg.cod_tipo_sog_giuridico::TEXT = 'E'::TEXT THEN NULL::CHARACTER VARYING
            ELSE soggetto_giuridico.denominazione_aaep
        END AS denominazione_aaep,
    soggetto_giuridico.denom_soggetto_giuridico,
    soggetto_giuridico.partita_iva,
    soggetto_giuridico.codice_fiscale,
    soggetto_giuridico.cod_osservatorio_naz,
    CONCAT(soggetto_giuridico.nome_rappr_legale, ' ', soggetto_giuridico.cognome_rappr_legale) AS rappresentante_legale,
    BTRIM(CONCAT(
        CASE
            WHEN soggetto_giuridico.toponimo_sede_legale IS NOT NULL THEN CONCAT(soggetto_giuridico.toponimo_sede_legale, ' ')
            ELSE ''::TEXT
        END,
        CASE
            WHEN soggetto_giuridico.indirizzo_sede_legale IS NOT NULL THEN CONCAT(soggetto_giuridico.indirizzo_sede_legale, ' ')
            ELSE ''::TEXT
        END,
        CASE
            WHEN soggetto_giuridico.num_civico_sede_legale IS NOT NULL THEN CONCAT(soggetto_giuridico.num_civico_sede_legale, ' ')
            ELSE ''::TEXT
        END,
        CASE
            WHEN COALESCE(soggetto_giuridico.toponimo_sede_legale, soggetto_giuridico.indirizzo_sede_legale, soggetto_giuridico.num_civico_sede_legale) IS NOT NULL AND COALESCE(soggetto_giuridico.cap_sede_legale, comune.denom_comune) IS NOT NULL THEN '- '::text
            ELSE ''::TEXT
        END,
        CASE
            WHEN soggetto_giuridico.cap_sede_legale IS NOT NULL THEN CONCAT(soggetto_giuridico.cap_sede_legale, ' ')
            ELSE ''::TEXT
        END,
        CASE
            WHEN comune.denom_comune IS NOT NULL THEN CONCAT(comune.denom_comune, ' (', provincia.sigla_provincia, ')')
            ELSE ''::TEXT
        END)) AS indirizzo_sede_legale,
    soggetto_giuridico.telefono_sede_legale,
    soggetto_giuridico.fax_sede_legale,
    soggetto_giuridico.email_sede_legale,
    soggetto_giuridico.pec_sede_legale,
    soggetto_giuridico.indirizzo_web,
    soggetto_giuridico.numero_verde,
    soggetto_giuridico.note,
    tipoente.desc_tipo_ente AS tipo_ente,
    soggetto_giuridico.data_inizio_attivita,
    soggetto_giuridico.data_cessazione,
    soggetto_giuridico.id_omnibus,
    soggetto_giuridico.id_utente_aggiornamento,
    soggetto_giuridico.data_aggiornamento,
    'N/A' AS bacino_appart_ente,
    soggetto_giuridico.cod_id_regionale AS codregionale,
    soggetto_giuridico.cod_csr_bip,
    COALESCE(scp.max_att::BOOLEAN, FALSE) AS attivo_tpl,
        CASE
            WHEN soggetto_giuridico.data_cessazione IS NOT NULL THEN true
            ELSE FALSE
        END AS cessato
   FROM sirtpla_t_soggetto_giuridico soggetto_giuridico
     JOIN sirtpla_d_tipo_sog_giuridico tiposogg ON soggetto_giuridico.id_tipo_sog_giuridico = tiposogg.id_tipo_sog_giuridico
     LEFT JOIN sirtpla_d_natura_giuridica natura ON soggetto_giuridico.id_natura_giuridica = natura.id_natura_giuridica
     LEFT JOIN sirtpl_d_comune comune ON soggetto_giuridico.id_comune_sede_legale = comune.id_comune
     LEFT JOIN sirtpl_d_provincia provincia ON comune.id_provincia = provincia.id_provincia
     LEFT JOIN sirtpla_d_tipo_ente tipoente ON soggetto_giuridico.id_tipo_ente = tipoente.id_tipo_ente
     LEFT JOIN ( SELECT max(att_scad.att) AS max_att,
            att_scad.id_sg
           FROM ( SELECT DISTINCT COALESCE(NULLIF(v_soggetti_coinvolti_periodi.attivo, TRUE)::INTEGER, 1) AS att,
                    v_soggetti_coinvolti_periodi.id_sg
                   FROM v_soggetti_coinvolti_periodi) att_scad
          GROUP BY att_scad.id_sg) scp ON soggetto_giuridico.id_soggetto_giuridico = scp.id_sg
  WHERE tiposogg.valido_per_sog_giurid AND tiposogg.id_ruolo_sog_giuridico IS NOT NULL
  ORDER BY soggetto_giuridico.denominazione_breve
;





CREATE OR REPLACE VIEW v_ext_sdp AS
WITH proroga AS (
         SELECT sirtplc_t_proroga_contratto.id_contratto,
            max(sirtplc_t_proroga_contratto.data_fine_proroga) AS dfp
           FROM sirtplc_t_proroga_contratto
          GROUP BY sirtplc_t_proroga_contratto.id_contratto
        ), vista AS (
         SELECT tab.cod_reg_contratto,
            tab.desc_contratto,
            tab.data_inizio_validita_contratto,
            tab.data_fine_validita_contratto,
            tab.cod_reg_soggetto,
            tab.denom_soggetto_alias,
            tab.denom_soggetto_infocamere,
            tab.ruolo_soggetto,
            tab.cod_reg_subaffidante,
            tab.denom_subaffidante_alias,
            tab.denom_subaffidante_infocamere,
            tab.cod_reg_sostituito,
            tab.denom_sostituito_alias,
            tab.denom_sostituito_infocamere,
                CASE
                    WHEN tab.cod_reg_sostituito IS NOT NULL THEN 'subentro'::text || tab.nodo_timeline_subentro
                    ELSE NULL::TEXT
                END AS nodo_timeline_subentro,
            tab.id_timeline_subentro,
            tab.data_inizio_attivita_soggetto,
            tab.data_fine_attivita_soggetto,
            tab.iban,
                CASE
                    WHEN 'now'::TEXT::DATE >= tab.data_inizio_attivita_soggetto AND 'now'::TEXT::DATE <= tab.data_fine_attivita_soggetto THEN TRUE
                    ELSE false
                END AS flg_soggetto_attivo
           FROM ( SELECT c.cod_id_regionale AS cod_reg_contratto,
                    c.desc_contratto,
                    c.data_inizio_validita AS data_inizio_validita_contratto,
                    COALESCE(p.dfp, c.data_fine_validita, 'now'::TEXT::DATE) AS data_fine_validita_contratto,
                    sg.cod_id_regionale AS cod_reg_soggetto,
                        CASE "position"(scp.alias::text, '<b>'::TEXT)
                            WHEN 1 THEN BTRIM(scp.alias::TEXT, '<b>'::TEXT)::CHARACTER VARYING
                            ELSE scp.alias
                        END AS denom_soggetto_alias,
                    sg.denominazione_aaep AS denom_soggetto_infocamere,
                    scp.ruolo AS ruolo_soggetto,
                    sg_aff.cod_id_regionale AS cod_reg_subaffidante,
                    scp.sg_aff AS denom_subaffidante_alias,
                    sg_aff.denominazione_aaep AS denom_subaffidante_infocamere,
                    sg_sost.cod_id_regionale AS cod_reg_sostituito,
                    scp.sg_sost AS denom_sostituito_alias,
                    sg_sost.denominazione_aaep AS denom_sostituito_infocamere,
                    ROW_NUMBER() OVER (PARTITION BY c.cod_id_regionale, (sg_sost.cod_id_regionale IS NOT NULL) ORDER BY (to_date(
                        CASE "position"(scp.d_ini, 'b'::text)
                            WHEN 2 THEN "substring"(scp.d_ini, 4, 10)
                            ELSE scp.d_ini
                        END, 'DD/MM/YYYY'::TEXT))) AS nodo_timeline_subentro,
                    NULL::UNKNOWN AS id_timeline_subentro,
                    TO_DATE(
                        CASE "position"(scp.d_ini, 'b'::TEXT)
                            WHEN 2 THEN "substring"(scp.d_ini, 4, 10)
                            ELSE scp.d_ini
                        END, 'DD/MM/YYYY'::TEXT) AS data_inizio_attivita_soggetto,
                    TO_DATE(
                        CASE "position"(scp.d_fin, 'b'::TEXT)
                            WHEN 2 THEN "substring"(scp.d_fin, 4, 10)
                            ELSE scp.d_fin
                        END, 'DD/MM/YYYY'::TEXT) AS data_fine_attivita_soggetto,
                    db.iban
                   FROM v_soggetti_coinvolti_periodi scp
                     JOIN sirtplc_t_contratto c ON scp.idc = c.id_contratto
                     JOIN sirtpla_t_soggetto_giuridico sg ON scp.id_sg = sg.id_soggetto_giuridico
                     LEFT JOIN proroga p ON scp.idc = p.id_contratto
                     LEFT JOIN sirtpla_t_soggetto_giuridico sg_aff ON scp.id_sg_aff = sg_aff.id_soggetto_giuridico
                     LEFT JOIN sirtpla_t_soggetto_giuridico sg_sost ON scp.id_sg_sost = sg_sost.id_soggetto_giuridico
                     LEFT JOIN sirtpla_t_dato_bancario db ON sg.id_soggetto_giuridico = db.id_soggetto_giuridico AND db.flag_doatpl
                  ORDER BY c.cod_id_regionale) tab
        )
 SELECT v1.cod_reg_contratto,
    v1.desc_contratto,
    v1.data_inizio_validita_contratto,
    v1.data_fine_validita_contratto,
    v1.cod_reg_soggetto,
    v1.denom_soggetto_alias,
    v1.denom_soggetto_infocamere,
    v1.ruolo_soggetto,
    v1.cod_reg_subaffidante,
    v1.denom_subaffidante_alias,
    v1.denom_subaffidante_infocamere,
    v1.cod_reg_sostituito,
    v1.denom_sostituito_alias,
    v1.denom_sostituito_infocamere,
        CASE
            WHEN v1.cod_reg_sostituito IS NULL AND (EXISTS ( SELECT 1
               FROM vista v
              WHERE v.cod_reg_sostituito::TEXT = v1.cod_reg_soggetto::TEXT AND v.cod_reg_contratto::TEXT = v1.cod_reg_contratto::TEXT)) THEN ( SELECT v.nodo_timeline_subentro
               FROM vista v
              WHERE v.cod_reg_sostituito::TEXT = v1.cod_reg_soggetto::TEXT AND v.cod_reg_contratto::TEXT = v1.cod_reg_contratto::TEXT)
            ELSE v1.nodo_timeline_subentro
        END AS nodo_timeline_subentro,
        CASE
            WHEN v1.cod_reg_sostituito IS NOT NULL THEN 1
            WHEN v1.cod_reg_sostituito IS NULL AND (EXISTS ( SELECT 1
               FROM vista v
              WHERE v.cod_reg_sostituito::TEXT = v1.cod_reg_soggetto::TEXT AND v.cod_reg_contratto::TEXT = v1.cod_reg_contratto::TEXT)) THEN 0
            ELSE NULL::integer
        END AS id_timeline_subentro,
    v1.data_inizio_attivita_soggetto,
    v1.data_fine_attivita_soggetto,
    v1.iban,
    v1.flg_soggetto_attivo
   FROM vista v1
;





CREATE OR REPLACE VIEW v_soggetti_coinvolti AS
WITH proroga AS (
         SELECT sirtplc_t_proroga_contratto.id_contratto,
            max(sirtplc_t_proroga_contratto.data_fine_proroga) AS dfp
           FROM sirtplc_t_proroga_contratto
          GROUP BY sirtplc_t_proroga_contratto.id_contratto
        )
 SELECT sogcoinv.idc,
    sogcoinv.alias,
    sogcoinv.ruolo::CHARACTER VARYING(100) AS ruolo,
    sogcoinv.per_conto_di::CHARACTER VARYING(100) AS per_conto_di,
    sogcoinv.id_sg,
    sogcoinv.d_ini,
    sogcoinv.d_fin
   FROM ( SELECT contr.id_contratto AS idc,
            soggiur.denominazione_breve AS alias,
            'Ente committente'::TEXT AS ruolo,
            NULL::TEXT AS per_conto_di,
            1 AS ord,
            soggiur.id_soggetto_giuridico AS id_sg,
            COALESCE(contr.data_inizio_validita, 'now'::TEXT::date) AS d_ini,
            COALESCE(
                CASE
                    WHEN (EXISTS ( SELECT 1
                       FROM sirtplc_r_sost_sog_contr sgc
                      WHERE sgc.id_sog_giurid_committente IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT sgc.data_sostituzione - 1
                       FROM sirtplc_r_sost_sog_contr sgc
                      WHERE sgc.id_sog_giurid_committente IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)
                    ELSE NULL::date
                END, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::date) AS d_fin
           FROM sirtpla_t_soggetto_giuridico soggiur
             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
        UNION
         SELECT contr.id_contratto AS idc,
            soggiur.denominazione_breve::TEXT ||
                CASE
                    WHEN contr.id_tipo_sog_giurid_esec = 3::NUMERIC THEN (' ('::TEXT || ((( SELECT sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
                       FROM sirtplc_d_tipo_raggruppamento
                      WHERE sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento = contr.id_tipo_raggr_sog_giurid_esec))::text)) || ')'::text
                    ELSE ''::TEXT
                END AS alias,
            'Esecutore titolare'::TEXT AS ruolo,
            NULL::TEXT AS per_conto_di,
            5 AS ord,
            soggiur.id_soggetto_giuridico AS id_sg,
            COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini,
            COALESCE(
                CASE
                    WHEN (EXISTS ( SELECT 1
                       FROM sirtplc_r_sost_sog_contr sgc
                      WHERE sgc.id_sog_giurid_esecutore IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)) THEN ( SELECT sgc.data_sostituzione - 1
                       FROM sirtplc_r_sost_sog_contr sgc
                      WHERE sgc.id_sog_giurid_esecutore IS NOT NULL AND sgc.id_contratto = contr.id_contratto AND sgc.id_tipo_sostituzione = 2::numeric)
                    ELSE NULL::date
                END, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin
           FROM sirtpla_t_soggetto_giuridico soggiur
             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_esecutore
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
        UNION
         SELECT contr.id_contratto AS idc,
            soggiur.denominazione_breve::TEXT ||
                CASE
                    WHEN contrraggr.capofila THEN ' (capofila)'::TEXT
                    ELSE ''::TEXT
                END AS alias,
            'Parte del raggruppamento'::TEXT AS ruolo,
            NULL::TEXT AS per_conto_di,
            10 AS ord,
            soggiur.id_soggetto_giuridico AS id_sg,
            COALESCE(contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini,
            COALESCE(soggiur.data_cessazione,
                CASE
                    WHEN (EXISTS ( SELECT 1
                       FROM sirtplc_r_sost_sog_contr_raggr sgcr
                         JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                      WHERE cr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico AND sgcr.id_tipo_sostituzione = 2::numeric AND cr.id_contratto = contr.id_contratto)) THEN ( SELECT sgcr.data_sostituzione - 1
                       FROM sirtplc_r_sost_sog_contr_raggr sgcr
                         JOIN sirtplc_r_contratto_raggrupp cr ON cr.id_contratto_raggrupp = sgcr.id_contratto_raggrupp
                      WHERE cr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico AND sgcr.id_tipo_sostituzione = 2::numeric AND cr.id_contratto = contr.id_contratto)
                    ELSE NULL::DATE
                END, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
             JOIN sirtpla_t_soggetto_giuridico soggiur ON contrraggr.id_soggetto_giuridico = soggiur.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE contr.id_sog_giurid_esecutore <> contrraggr.id_soggetto_giuridico
        UNION
         SELECT contr.id_contratto AS idc,
            soggiursubaff.denominazione_breve AS alias,
            'Subaffidataria'::text AS ruolo,
            soggiuresec.denominazione_breve AS per_conto_di,
            15 AS ord,
            soggiursubaff.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date) AS d_ini,
            COALESCE(soggiursubaff.data_cessazione, sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON contr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE sostsogcontr.id_tipo_sostituzione = 1::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            soggiursubaff.denominazione_breve AS alias,
            'Subaffidataria'::text AS ruolo,
            soggiurraggr.denominazione_breve AS per_conto_di,
            16 AS ord,
            soggiursubaff.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date) AS d_ini,
            COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE sostsogcontrraggr.id_tipo_sostituzione = 1::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            soggiursubaff.denominazione_breve AS alias,
            'Somministrazione di personale'::text AS ruolo,
            soggiuresec.denominazione_breve AS per_conto_di,
            20 AS ord,
            soggiursubaff.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date) AS d_ini,
            COALESCE(soggiursubaff.data_cessazione, sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON contr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontr.id_sog_giurid_esecutore = soggiursubaff.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE sostsogcontr.id_tipo_sostituzione = 3::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            soggiursubaff.denominazione_breve AS alias,
            'Somministrazione di personale'::TEXT AS ruolo,
            soggiurraggr.denominazione_breve AS per_conto_di,
            21 AS ord,
            soggiursubaff.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::text::date) AS d_ini,
            COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::text::date) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE sostsogcontrraggr.id_tipo_sostituzione = 3::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            ('<b>'::TEXT || soggiurcomm.denominazione_breve::TEXT) || '<b>'::TEXT AS alias,
            'Ente committente'::TEXT AS ruolo,
            NULL::TEXT AS per_conto_di,
            3 AS ord,
            soggiurcomm.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcomm.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini,
            COALESCE(sostsogcomm.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin
           FROM sirtpla_t_soggetto_giuridico soggiur
             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
             JOIN sirtplc_r_sost_sog_contr sostsogcomm ON contr.id_contratto = sostsogcomm.id_contratto
             JOIN sirtpla_t_soggetto_giuridico soggiurcomm ON sostsogcomm.id_sog_giurid_committente = soggiurcomm.id_soggetto_giuridico
          WHERE sostsogcomm.id_tipo_sostituzione = 2::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            ('<b>'::TEXT || soggiuresec.denominazione_breve::text) || '<b>'::TEXT AS alias,
            'Esecutore titolare'::TEXT AS ruolo,
            NULL::TEXT AS per_conto_di,
            12 AS ord,
            soggiuresec.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini,
            COALESCE(sostsogcontr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin
           FROM sirtpla_t_soggetto_giuridico soggiur
             JOIN sirtplc_t_contratto contr ON soggiur.id_soggetto_giuridico = contr.id_sog_giurid_committente
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
             JOIN sirtplc_r_sost_sog_contr sostsogcontr ON contr.id_contratto = sostsogcontr.id_contratto
             JOIN sirtpla_t_soggetto_giuridico soggiuresec ON sostsogcontr.id_sog_giurid_esecutore = soggiuresec.id_soggetto_giuridico
          WHERE sostsogcontr.id_tipo_sostituzione = 2::NUMERIC
        UNION
         SELECT contr.id_contratto AS idc,
            ('<b>'::TEXT || soggiursubaff.denominazione_breve::TEXT) || '<b>'::TEXT AS alias,
            'Parte del raggruppamento'::TEXT AS ruolo,
            soggiurraggr.denominazione_breve AS per_conto_di,
            18 AS ord,
            soggiursubaff.id_soggetto_giuridico AS id_sg,
            COALESCE(sostsogcontrraggr.data_sostituzione, contr.data_inizio_validita, 'now'::TEXT::DATE) AS d_ini,
            COALESCE(soggiursubaff.data_cessazione, sostsogcontrraggr.data_fine_sostituzione, proroga.dfp, contr.data_fine_validita, 'now'::TEXT::DATE) AS d_fin
           FROM sirtplc_t_contratto contr
             JOIN sirtplc_r_contratto_raggrupp contrraggr ON contr.id_contratto = contrraggr.id_contratto
             JOIN sirtplc_r_sost_sog_contr_raggr sostsogcontrraggr ON contrraggr.id_contratto_raggrupp = sostsogcontrraggr.id_contratto_raggrupp
             JOIN sirtpla_t_soggetto_giuridico soggiurraggr ON contrraggr.id_soggetto_giuridico = soggiurraggr.id_soggetto_giuridico
             JOIN sirtpla_t_soggetto_giuridico soggiursubaff ON sostsogcontrraggr.id_soggetto_giuridico = soggiursubaff.id_soggetto_giuridico
             LEFT JOIN proroga ON contr.id_contratto = proroga.id_contratto
          WHERE sostsogcontrraggr.id_tipo_sostituzione = 2::NUMERIC) sogcoinv
  ORDER BY sogcoinv.idc, sogcoinv.ord, sogcoinv.alias
;





ALTER TABLE v_contratti_soggetti          OWNER TO sirtpl_trasv;
ALTER TABLE v_export_ricerca_contratti    OWNER TO sirtpl_trasv;
ALTER TABLE v_export_ricerca_sogg_giur    OWNER TO sirtpl_trasv;
ALTER TABLE v_ext_sdp                     OWNER TO sirtpl_trasv;
ALTER TABLE v_soggetti_coinvolti          OWNER TO sirtpl_trasv;
ALTER TABLE v_soggetti_coinvolti_periodi  OWNER TO sirtpl_trasv;
