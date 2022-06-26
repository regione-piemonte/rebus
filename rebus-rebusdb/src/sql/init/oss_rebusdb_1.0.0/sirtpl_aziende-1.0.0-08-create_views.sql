CREATE OR REPLACE VIEW v_depositi AS
SELECT d.id_deposito,
        CASE
            WHEN tiposogg.cod_tipo_sog_giuridico::text = 'E'::text THEN NULL::character varying
            ELSE soggetto_giuridico.denominazione_aaep
        END AS denominazione_aaep,
    natura.desc_natura_giuridica AS natura_giuridica,
    soggetto_giuridico.partita_iva::text AS partita_iva,
    soggetto_giuridico.codice_fiscale,
    soggetto_giuridico.cod_osservatorio_naz,
    soggetto_giuridico.cod_id_regionale AS codregionale,
    soggetto_giuridico.telefono_sede_legale::text AS telefono_sede_legale,
    soggetto_giuridico.fax_sede_legale::text AS fax_sede_legale,
    soggetto_giuridico.email_sede_legale,
    soggetto_giuridico.pec_sede_legale,
    d.denom_deposito,
    ((((((((upper(d.toponimo_deposito::text) || ' '::text) || upper(d.indirizzo_deposito::text)) || ' '::text) || d.num_civico_deposito::text) || ' - '::text) || COALESCE(d.cap_deposito, ''::character varying)::text) || ' '::text) || c.denom_comune::text) ||
        CASE
            WHEN p.id_provincia IS NOT NULL THEN (' ('::text || p.sigla_provincia::text) || ')'::text
            ELSE NULL::text
        END AS indirizzo_doposito
   FROM sirtpla_t_soggetto_giuridico soggetto_giuridico
     JOIN sirtpla_d_tipo_sog_giuridico tiposogg ON soggetto_giuridico.id_tipo_sog_giuridico = tiposogg.id_tipo_sog_giuridico
     LEFT JOIN sirtpla_d_natura_giuridica natura ON soggetto_giuridico.id_natura_giuridica = natura.id_natura_giuridica
     LEFT JOIN sirtpl_d_comune comune ON soggetto_giuridico.id_comune_sede_legale = comune.id_comune
     LEFT JOIN sirtpl_d_provincia provincia ON comune.id_provincia = provincia.id_provincia
     LEFT JOIN sirtpla_d_tipo_ente tipoente ON soggetto_giuridico.id_tipo_ente = tipoente.id_tipo_ente
     LEFT JOIN sirtpla_r_sog_giurid_deposito sgd ON soggetto_giuridico.id_soggetto_giuridico = sgd.id_soggetto_giuridico
     LEFT JOIN sirtpla_t_deposito d ON sgd.id_deposito = d.id_deposito
     LEFT JOIN sirtpl_d_comune c ON d.id_comune_deposito = c.id_comune
     LEFT JOIN sirtpl_d_provincia p ON c.id_provincia = p.id_provincia
  WHERE tiposogg.valido_per_sog_giurid AND tiposogg.id_ruolo_sog_giuridico IS NOT NULL AND tiposogg.id_tipo_sog_giuridico = 2::numeric
  ORDER BY soggetto_giuridico.denominazione_breve;



ALTER TABLE v_depositi OWNER TO sirtpl_aziende;



GRANT UPDATE, SELECT, INSERT, DELETE  ON TABLE v_depositi TO sirtpl_aziende_rw;
GRANT SELECT                          ON TABLE v_depositi TO sirtpl_trasv;
GRANT SELECT                          ON TABLE v_depositi TO sirtpl_trasv_rw;
GRANT SELECT                          ON TABLE v_depositi TO sirtpl_trasv_ro;
