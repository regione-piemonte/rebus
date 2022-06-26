CREATE TABLE rebus_d_categoria_veicolo
( id_categoria_veicolo    INT4                  NOT NULL
, cod_categoria_veicolo   CHARACTER VARYING(2)  NOT NULL
, categoria_veicolo       CHARACTER VARYING(25) NOT NULL
, CONSTRAINT pk_d_categoria_veicolo PRIMARY KEY (id_categoria_veicolo)
);
CREATE TABLE rebus_d_classe_amb_euro
( id_classe_ambientale  INT4                    NOT NULL
, descrizione           CHARACTER VARYING(50)
, CONSTRAINT pk_rebus_d_classe_amb_euro PRIMARY KEY (id_classe_ambientale)
);
CREATE TABLE rebus_d_classe_veicolo
( id_classe_veicolo   INT4                    NOT NULL
, descrizione         CHARACTER VARYING(50)
, CONSTRAINT pk_rebus_d_classe_veicolo PRIMARY KEY (id_classe_veicolo)
);
CREATE TABLE rebus_d_dispositivi_prevenz
( id_dispositivo  int4                    NOT NULL
, descrizione     CHARACTER VARYING(200)
, CONSTRAINT pk_rebus_d_dispositivi_prevenz PRIMARY KEY (id_dispositivo)
);
CREATE TABLE rebus_d_dotazione_disabili
( id_dotazione_disabili   INT4                    NOT NULL
, descrizione             CHARACTER VARYING(75)
, CONSTRAINT pk_rebus_d_dotazione_disabili PRIMARY KEY (id_dotazione_disabili)
);
CREATE TABLE rebus_d_impianti_audio
( id_impianti_audio   INT4                    NOT NULL
, descrizione         CHARACTER VARYING(200)
, CONSTRAINT pk_rebus_d_impianti_audio PRIMARY KEY (id_impianti_audio)
);
CREATE TABLE rebus_d_impianti_visivi
( id_impianti_visivi  INT4                    NOT NULL
, descrizione         CHARACTER VARYING(200)
, CONSTRAINT pk_rebus_d_impianti_visivi PRIMARY KEY (id_impianti_visivi)
);
CREATE TABLE rebus_d_proprieta
( id_proprieta  INT4                    NOT NULL
, descrizione   CHARACTER VARYING(200)
, CONSTRAINT pk_rebus_d_proprieta PRIMARY KEY (id_proprieta)
);
CREATE TABLE rebus_d_ruolo_azienda
( id_ruolo_azienda  INT4                    NOT NULL
, descrizione       CHARACTER VARYING(50)
, CONSTRAINT pk_rebus_d_ruolo_azienda PRIMARY KEY (id_ruolo_azienda)
);
CREATE TABLE rebus_d_tipo_alimentazione
( id_tipo_alimentazione   INT4                    NOT NULL
, descrizione             CHARACTER VARYING(20)
, CONSTRAINT pk_rebus_d_tipo_alimentazione PRIMARY KEY (id_tipo_alimentazione)
);
CREATE TABLE rebus_d_tipo_allestimento
( id_tipo_allestimento  INT4                    NOT NULL
, descrizione           CHARACTER VARYING(30)
, CONSTRAINT pk_rebus_d_tipo_allestimento PRIMARY KEY (id_tipo_allestimento)
);
CREATE TABLE rebus_d_tipo_documento
( id_tipo_documento   int4                      NOT NULL
, descrizione         CHARACTER VARYING(75)     NOT NULL
, desc_estesa         CHARACTER VARYING(1000)
, id_contesto         NUMERIC(1)                NOT NULL
, ordinamento         INT4
, CONSTRAINT pk_rebus_d_tipo_documento PRIMARY KEY (id_tipo_documento)
);
CREATE TABLE rebus_d_tipo_immatricol
( id_tipo_immatricolazione  INT4                    NOT NULL
, descrizione               CHARACTER VARYING(75)
, CONSTRAINT pk_rebus_d_tipo_immatricol PRIMARY KEY (id_tipo_immatricolazione)
);
CREATE TABLE rebus_d_tipo_messaggio
( id_tipo_messaggio   INT4                    NOT NULL
, descrizione         CHARACTER VARYING(100)
, id_contesto         NUMERIC(1)              NOT NULL
, CONSTRAINT pk_rebus_d_tipo_messaggio PRIMARY KEY (id_tipo_messaggio)
);
CREATE TABLE rebus_d_tipo_messaggio_sistema
( id_tipo_messaggio_sistema   INT4                      NOT NULL
, testo                       CHARACTER VARYING(1000)   NOT NULL
, CONSTRAINT ak_d_tipo_messaggio_sistema_01 UNIQUE (testo)
, CONSTRAINT pk_d_tipo_messaggio_sistema PRIMARY KEY (id_tipo_messaggio_sistema)
);
CREATE TABLE rebus_r_azienda_azienda
( id_azienda        INT4  NOT NULL
, id_azienda_padre  INT4  NOT NULL
, data_fus_incorp   DATE  NOT NULL
, CONSTRAINT pk_rebus_r_azienda_azienda PRIMARY KEY (id_azienda, id_azienda_padre, data_fus_incorp)
);
CREATE TABLE rebus_t_bacini
( id_bacino       INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_bacini'::regclass)
, denominazione   CHARACTER VARYING(100)
, descrizione     CHARACTER VARYING(300)
, data_fine_val   DATE
, CONSTRAINT pk_rebus_t_bacini PRIMARY KEY (id_bacino)
);
CREATE TABLE rebus_t_bandi
( id_bando      INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_bandi'::regclass)
, anno          INT4
, descrizione   CHARACTER VARYING(300)
, CONSTRAINT pk_rebus_t_bandi PRIMARY KEY (id_bando)
);
CREATE TABLE rebus_t_consorzi
( id_consorzio    INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_consorzi'::regclass)
, denominazione   CHARACTER VARYING(200)
, CONSTRAINT pk_rebus_t_consorzi PRIMARY KEY (id_consorzio)
);
CREATE TABLE rebus_t_gestori_trasporti
( id_gestore              INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_gestori_trasporti'::regclass)
, fk_azienda              INT4                    NOT NULL
, codice_fiscale_gestore  CHARACTER VARYING(16)
, cognome                 CHARACTER VARYING(255)
, nome                    CHARACTER VARYING(255)
, data_inizio_val         DATE
, data_fine_val           DATE
, CONSTRAINT pk_rebus_t_gestori_trasporti PRIMARY KEY (id_gestore)
);
CREATE TABLE rebus_t_stati
( id_stato        INT4                    NOT NULL
, ds_stato        CHARACTER VARYING(200)
, data_fine_val   DATE
, CONSTRAINT pk_rebus_t_stati PRIMARY KEY (id_stato)
);
CREATE TABLE rebusc_d_portabici
( id_portabici          NUMERIC                 NOT NULL
, desc_portabici        CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE
, CONSTRAINT ak_d_portabici_01 UNIQUE (desc_portabici, data_inizio_validita)
, CONSTRAINT pk_d_portabici PRIMARY KEY (id_portabici)
);
CREATE TABLE rebusc_d_sistema_bigliettazione
( id_sistema_bigliettazione     NUMERIC                 NOT NULL
, desc_sistema_bigliettazione   CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita          DATE                    NOT NULL
, data_fine_validita            DATE
, CONSTRAINT ak_d_sistema_bigliettazione_01 UNIQUE (desc_sistema_bigliettazione, data_inizio_validita)
, CONSTRAINT pk_d_sistema_bigliettazione PRIMARY KEY (id_sistema_bigliettazione)
);
CREATE TABLE rebusc_d_sistema_localizzazione
( id_sistema_localizzazione     NUMERIC                 NOT NULL
, desc_sistema_localizzazione   CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita          DATE                    NOT NULL
, data_fine_validita            DATE
, CONSTRAINT ak_d_sistema_localizzazione_01 UNIQUE (desc_sistema_localizzazione, data_inizio_validita)
, CONSTRAINT pk_d_sistema_localizzazione PRIMARY KEY (id_sistema_localizzazione)
);
CREATE TABLE rebusc_d_sistema_videosorveglianza
( id_sistema_videosorveglianza    NUMERIC                 NOT NULL
, desc_sistema_videosorveglianza  CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita            DATE                    NOT NULL
, data_fine_validita              DATE
, CONSTRAINT ak_d_sistema_videosorveglianza_01 UNIQUE (desc_sistema_videosorveglianza, data_inizio_validita)
, CONSTRAINT pk_d_sistema_videosorveglianza PRIMARY KEY (id_sistema_videosorveglianza)
);
CREATE TABLE rebusc_d_tipo_doc_quietanza
( id_tipo_doc_quietanza     NUMERIC                 NOT NULL
, desc_tipo_doc_quietanza   CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita      DATE                    NOT NULL
, data_fine_validita        DATE
, CONSTRAINT ak_d_tipo_doc_quietanza_01 UNIQUE (desc_tipo_doc_quietanza, data_inizio_validita)
, CONSTRAINT pk_d_tipo_doc_quietanza PRIMARY KEY (id_tipo_doc_quietanza)
);
CREATE TABLE rebusc_d_tipo_garanzia
( id_tipo_garanzia      NUMERIC                 NOT NULL
, desc_tipo_garanzia    CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE
, CONSTRAINT ak_d_tipo_garanzia_01 UNIQUE (desc_tipo_garanzia, data_inizio_validita)
, CONSTRAINT pk_d_tipo_garanzia PRIMARY KEY (id_tipo_garanzia)
);
CREATE TABLE rebusc_d_tipo_sostituzione
( id_tipo_sostituzione    NUMERIC                 NOT NULL
, desc_tipo_sostituzione  CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita    DATE                    NOT NULL
, data_fine_validita      DATE
, CONSTRAINT ak_d_tipo_sostituzione_01 UNIQUE (desc_tipo_sostituzione, data_inizio_validita)
, CONSTRAINT pk_d_tipo_sostituzione PRIMARY KEY (id_tipo_sostituzione)
);
CREATE TABLE rebusc_d_voce_costo
( id_voce_costo         NUMERIC(2)              NOT NULL
, desc_voce_costo       CHARACTER VARYING(50)   NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE
, CONSTRAINT ak_d_voce_costo_01 UNIQUE (desc_voce_costo)
, CONSTRAINT pk_d_voce_costo PRIMARY KEY (id_voce_costo)
);
CREATE TABLE rebusc_t_atto_assegnazione
( id_atto_assegnazione  NUMERIC(3)              NOT NULL
, desc_breve            CHARACTER VARYING(50)   NOT NULL
, desc_estesa           CHARACTER VARYING(100)
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE
, CONSTRAINT ak_rebusc_t_atto_assegnazione_01 UNIQUE (desc_breve, data_inizio_validita)
, CONSTRAINT ak_rebusc_t_atto_assegnazione_02 UNIQUE (desc_estesa, data_inizio_validita)
, CONSTRAINT pk_rebusc_t_atto_assegnazione PRIMARY KEY (id_atto_assegnazione)
);
CREATE TABLE rebusc_t_dato_bonifico
( id_dato_bonifico  NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_t_dato_bonifico'::regclass)
, data_bonifico     DATE                    NOT NULL
, importo_bonifico  FLOAT4                  NOT NULL
, cro               CHARACTER VARYING(11)   NOT NULL
, CONSTRAINT pk_rebusc_t_dato_bonifico PRIMARY KEY (id_dato_bonifico)
);
CREATE TABLE rebusc_t_dato_spesa
( id_dato_spesa                       NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_t_dato_spesa'::regclass)
, contributo_erogabile_reg            FLOAT8
, contributo_regionale_ff             FLOAT8
, nr_atto_liquidazione_rp_amp_ant     CHARACTER VARYING(25)
, nr_atto_liquidazione_rp_amp_sal     CHARACTER VARYING(25)
, data_atto_liquidazione_rp_amp_ant   DATE
, data_atto_liquidazione_rp_amp_sal   DATE
, nr_determina_rp_amp_ant             CHARACTER VARYING(25)
, nr_determina_rp_amp_sal             CHARACTER VARYING(25)
, data_determina_rp_amp_ant           DATE
, data_determina_rp_amp_sal           DATE
, nr_atto_liquidazione_amp_az_ant     CHARACTER VARYING(25)
, nr_atto_liquidazione_amp_az_sal     CHARACTER VARYING(25)
, data_atto_liquidazione_amp_az_ant   DATE
, data_atto_liquidazione_amp_az_sal   DATE
, nr_determina_amp_az_ant             CHARACTER VARYING(25)
, nr_determina_amp_az_sal             CHARACTER VARYING(25)
, data_determina_amp_az_ant           DATE
, data_determina_amp_az_sal           DATE
, importo_anticipo                    FLOAT8
, importo_saldo                       FLOAT8
, id_utente_aggiornamento             NUMERIC(6)              NOT NULL
, data_aggiornamento                  TIMESTAMP               NOT NULL
, CONSTRAINT pk_rebusc_t_dato_spesa PRIMARY KEY (id_dato_spesa)
);
CREATE TABLE rebusc_t_ordine_acquisto
( id_ordine_acquisto        NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_t_ordine_acquisto'::regclass)
, cup_master                CHARACTER VARYING(15)
, cup                       CHARACTER VARYING(15)
, cig                       CHARACTER VARYING(15)
, data_aggiudicazione       DATE
, data_stipula              DATE
, numero_ordine             CHARACTER VARYING(15)
, data_ordine               DATE
, fornitore                 CHARACTER VARYING(200)
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMP               NOT NULL
, CONSTRAINT pk_rebusc_t_ordine_acquisto PRIMARY KEY (id_ordine_acquisto)
);
CREATE TABLE rebusm_d_tipo_monitoraggio
( id_tipo_monitoraggio    NUMERIC(1)              NOT NULL
, cod_tipo_monitoraggio   CHARACTER VARYING(1)    NOT NULL
, descrizione             CHARACTER VARYING(25)   NOT NULL
, CONSTRAINT ak_rebusm_d_tipo_monitoraggio_01 UNIQUE (cod_tipo_monitoraggio)
, CONSTRAINT ak_rebusm_d_tipo_monitoraggio_02 UNIQUE (descrizione)
, CONSTRAINT pk_rebusm_d_tipo_monitoraggio PRIMARY KEY (id_tipo_monitoraggio)
);
CREATE TABLE rebusp_d_motorizzazione
( id_motorizzazione     NUMERIC(4)              NOT NULL
, desc_motorizzazione   CHARACTER VARYING(100)  NOT NULL
, indirizzo             CHARACTER VARYING(200)
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, pec                   CHARACTER VARYING(50)
, CONSTRAINT ak_d_motorizzazione_01 UNIQUE (desc_motorizzazione, data_inizio_validita)
, CONSTRAINT pk_d_motorizzazione PRIMARY KEY (id_motorizzazione)
);
CREATE TABLE rebusp_d_stato_iter
( id_stato_iter         NUMERIC(2)              NOT NULL
, desc_stato_iter       CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_stato_iter_01 UNIQUE (desc_stato_iter, data_inizio_validita)
, CONSTRAINT pk_d_stato_iter PRIMARY KEY (id_stato_iter)
);
CREATE TABLE rebusp_d_tipo_procedimento
( id_tipo_procedimento    NUMERIC(2)              NOT NULL
, cod_tipo_procedimento   CHARACTER VARYING(2)
, desc_tipo_procedimento  CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita    DATE                    NOT NULL
, data_fine_validita      DATE
, CONSTRAINT ak_d_tipo_procedimento_01 UNIQUE (cod_tipo_procedimento, data_inizio_validita)
, CONSTRAINT ak_d_tipo_procedimento_02 UNIQUE (desc_tipo_procedimento, data_inizio_validita)
, CONSTRAINT pk_d_tipo_procedimento PRIMARY KEY (id_tipo_procedimento)
);
CREATE TABLE rebusp_d_tipo_ruolo
( id_tipo_ruolo         NUMERIC(2)              NOT NULL
, desc_tipo_ruolo       CHARACTER VARYING(100)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, CONSTRAINT ak_d_tipo_ruolo_01 UNIQUE (desc_tipo_ruolo, data_inizio_validita)
, CONSTRAINT pk_d_tipo_ruolo PRIMARY KEY (id_tipo_ruolo)
);
CREATE TABLE rebusp_d_tipo_stampa
( id_tipo_stampa        NUMERIC(4)              NOT NULL
, descrizione           CHARACTER VARYING(200)  NOT NULL
, data_inizio_validita  DATE                    NOT NULL
, data_fine_validita    DATE
, "template"            BYTEA
, CONSTRAINT ak_d_tipo_stampa_01 UNIQUE (descrizione, data_inizio_validita)
, CONSTRAINT pk_d_tipo_stampa PRIMARY KEY (id_tipo_stampa)
);
CREATE TABLE rebusp_r_proc_contratto_linea
( id_proc_contratto   NUMERIC(7)  NOT NULL
, id_linea            NUMERIC(8)  NOT NULL
, CONSTRAINT pk_r_proc_contratto_linea PRIMARY KEY (id_proc_contratto, id_linea)
);
CREATE TABLE rebusp_t_documento
( id_documento            NUMERIC(3)              NOT NULL  DEFAULT NextVal('seq_t_documento'::regclass)
, documento               BYTEA                   NOT NULL
, descrizione             CHARACTER VARYING(300)  NOT NULL
, nome_file               CHARACTER VARYING(100)  NOT NULL
, data_inserimento        TIMESTAMPTZ             NOT NULL  DEFAULT now()
, id_utente_inserimento   NUMERIC(6)              NOT NULL
, CONSTRAINT ak_t_documento_01 UNIQUE (descrizione, nome_file)
, CONSTRAINT pk_t_documento PRIMARY KEY (id_documento)
);
CREATE TABLE rebus_d_tipologia_dimens
( id_tipo_allestimento  INT4                  NOT NULL
, progr_tipo_dimens     INT4                  NOT NULL
, lunghezza_min         FLOAT4
, lunghezza_max         FLOAT4
, tipologia_dimens      CHARACTER VARYING(50)
, CONSTRAINT pk_rebus_d_tipologia_dimens PRIMARY KEY (id_tipo_allestimento, progr_tipo_dimens)
, CONSTRAINT fk_rebus_d_tipo_allestim_02 FOREIGN KEY (id_tipo_allestimento) REFERENCES rebus_d_tipo_allestimento(id_tipo_allestimento)
);
CREATE TABLE rebus_r_consorzi_aziende
( id_consorzio      INT4  NOT NULL
, id_azienda        INT4  NOT NULL
, fk_ruolo_azienda  INT4  NOT NULL
, CONSTRAINT pk_rebus_r_consorzi_aziende PRIMARY KEY (id_consorzio, id_azienda)
, CONSTRAINT fk_rebus_d_ruolo_azienda_01 FOREIGN KEY (fk_ruolo_azienda) REFERENCES rebus_d_ruolo_azienda(id_ruolo_azienda)
, CONSTRAINT fk_rebus_t_consorzi_02 FOREIGN KEY (id_consorzio) REFERENCES rebus_t_consorzi(id_consorzio)
);
CREATE TABLE rebus_r_contratti_servizio
( id_contratto_servizio   INT4 NOT NULL
, fk_bacino               INT4
, fk_consorzio            INT4 NOT NULL
, fk_azienda              INT4 NOT NULL
, data_sottoscrizione     DATE
, data_ini_val            DATE
, data_fine_val           DATE
, CONSTRAINT pk_rebus_r_contratti_servizio PRIMARY KEY (id_contratto_servizio)
, CONSTRAINT fk_rebus_t_bacini_01 FOREIGN KEY (fk_bacino) REFERENCES rebus_t_bacini(id_bacino)
, CONSTRAINT fk_rebus_t_consorzi_01 FOREIGN KEY (fk_consorzio) REFERENCES rebus_t_consorzi(id_consorzio)
);
CREATE TABLE rebus_r_doc_variaz_autobus
( id_variaz_autobus   INT4                    NOT NULL
, id_tipo_documento   INT4                    NOT NULL
, documento           BYTEA
, nome_file           CHARACTER VARYING(100)
, data_caricamento    DATE
, note                CHARACTER VARYING(200)
, fk_utente           INT4
, CONSTRAINT pk_rebus_r_doc_variaz_autobus PRIMARY KEY (id_variaz_autobus, id_tipo_documento)
, CONSTRAINT fk_rebus_d_tipo_documento_01 FOREIGN KEY (id_tipo_documento) REFERENCES rebus_d_tipo_documento(id_tipo_documento)
);
CREATE TABLE rebus_t_autobus
( primo_telaio                  CHARACTER VARYING(25)   NOT NULL
, fk_bando                      INT4
, data_prima_immatricolazione   DATE
, marca                         CHARACTER VARYING(200)  NOT NULL
, modello                       CHARACTER VARYING(200)  NOT NULL
, CONSTRAINT pk_rebus_t_autobus PRIMARY KEY (primo_telaio)
, CONSTRAINT fk_rebus_t_bandi_01 FOREIGN KEY (fk_bando) REFERENCES rebus_t_bandi(id_bando)
);
CREATE TABLE rebus_t_regioni
( id_regione      INT4                    NOT NULL
, fk_stato        INT4                    NOT NULL
, denominazione   CHARACTER VARYING(50)
, codice_istat    CHARACTER VARYING(6)
, sigla_regione   CHARACTER VARYING(5)
, data_fine_val   DATE
, CONSTRAINT pk_rebus_t_regioni PRIMARY KEY (id_regione)
, CONSTRAINT fk_rebus_t_stati_01 FOREIGN KEY (fk_stato) REFERENCES rebus_t_stati(id_stato)
);
CREATE TABLE rebus_t_storia_variaz_autobus
( id_storia_variaz_autobus        INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_storia_variaz_autobus'::regclass)
, fk_azienda                      INT4
, n_telaio                        CHARACTER VARYING(25)   NOT NULL
, n_targa                         CHARACTER VARYING(25)   NOT NULL
, data_ultima_immatricolazione    TIMESTAMP
, fk_tipo_alimentazione           INT4                    NOT NULL
, n_posti_sedere                  INT4                    NOT NULL
, n_posti_in_piedi                INT4                    NOT NULL
, n_posti_riservati               INT4
, omologazione_direttiva_europea  CHARACTER VARYING(300)
, fk_classe_ambientale_euro       INT4                    NOT NULL
, flg_filtro_fap                  CHARACTER VARYING(1)
, fk_dotazione_disabili           INT4                    NOT NULL
, fk_impianti_audio               INT4                    NOT NULL
, fk_impianti_visivi              INT4                    NOT NULL
, flg_rilevatore_bip              CHARACTER VARYING(1)
, prezzo_tot_acquisto             FLOAT8
, contributo_pubblico_acquisto    FLOAT8
, flg_veicolo_assicurato          CHARACTER VARYING(1)
, data_ultima_revisione           TIMESTAMP
, note                            TEXT
, primo_telaio                    CHARACTER VARYING(25)   NOT NULL
, n_matricola_aziendale           CHARACTER VARYING(20)
, fk_tipo_immatricolazione        INT4                    NOT NULL
, ente_autorizz_prima_imm         CHARACTER VARYING(200)
, fk_classe_veicolo               INT4                    NOT NULL
, flg_due_piani                   CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_snodato                     CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, caratteristiche_particolari     CHARACTER VARYING(255)
, altra_alimentazione             CHARACTER VARYING(100)
, fk_tipo_allestimento            INT4                    NOT NULL
, progr_tipo_dimens               INT4                    NOT NULL
, lunghezza                       FLOAT8                  NOT NULL
, numero_porte                    INT4
, posti_carrozzina                INT4                    NOT NULL
, flg_impianto_condizionamento    CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_cabina_guida_isolata        BPCHAR(1)
, altri_dispositivi_prevenz_inc   CHARACTER VARYING(255)
, flg_otx                         CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_avm                         CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_contapasseggeri             CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, fk_proprieta_leasing            INT4                    NOT NULL
, flg_conteggiato_miv             CHARACTER VARYING(1)
, fk_deposito                     INT4
, tmp_indirizzo_dep               CHARACTER VARYING(300)
, nota_riservata_azienda          TEXT
, nota_riservata_amp              TEXT
, nota_riservata_rp               TEXT
, data_aggiornamento              TIMESTAMP               NOT NULL
, flg_verificato_azienda          CHARACTER VARYING(1)    NOT NULL  DEFAULT 'N'::CHARACTER VARYING
, flg_verificato_amp              BPCHAR(1)                         DEFAULT 'N'::BPCHAR
, flg_alienato                    CHARACTER VARYING(1)
, flg_richiesta_contr             CHARACTER VARYING(1)
, anno_sost_prog                  NUMERIC(4)
, fk_utente                       INT4                    NOT NULL  DEFAULT 1
, fk_utente_storia                INT4
, data_aggiornamento_storia       TIMESTAMP
, data_inserimento                TIMESTAMP
, motivazione                     CHARACTER VARYING(500)
, CONSTRAINT alien2_s_n_r CHECK (((flg_alienato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('R'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT avm2_s_n_u CHECK (((flg_avm)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT cab2_s_n CHECK ((flg_cabina_guida_isolata = ANY (ARRAY['S'::BPCHAR, 'N'::BPCHAR])))
, CONSTRAINT cond2_s_n_u CHECK (((flg_impianto_condizionamento)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT contap2_s_n CHECK (((flg_contapasseggeri)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT dom_val2_amp_s_n CHECK ((flg_verificato_amp = ANY (ARRAY['S'::BPCHAR, 'N'::BPCHAR, 'U'::BPCHAR])))
, CONSTRAINT dom_val2_az_s_n CHECK (((flg_verificato_azienda)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT duep2_s_n_u CHECK (((flg_due_piani)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT fa2p_s_n CHECK (((flg_filtro_fap)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT otx2_s_n CHECK (((flg_otx)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT pk_rebus_t_storia_variaz_autobus PRIMARY KEY (id_storia_variaz_autobus)
, CONSTRAINT rico2_s_n CHECK (((flg_richiesta_contr)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT ril2_bip_s_n CHECK (((flg_rilevatore_bip)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT snod2_s_n_u CHECK (((flg_snodato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT v_assic2_s_n CHECK (((flg_veicolo_assicurato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT fk_rebus_d_classe_amb_euro_01 FOREIGN KEY (fk_classe_ambientale_euro) REFERENCES rebus_d_classe_amb_euro(id_classe_ambientale)
, CONSTRAINT fk_rebus_d_classe_veicolo_01 FOREIGN KEY (fk_classe_veicolo) REFERENCES rebus_d_classe_veicolo(id_classe_veicolo)
, CONSTRAINT fk_rebus_d_dotaz_disabili_01 FOREIGN KEY (fk_dotazione_disabili) REFERENCES rebus_d_dotazione_disabili(id_dotazione_disabili)
, CONSTRAINT fk_rebus_d_impianti_audio_01 FOREIGN KEY (fk_impianti_audio) REFERENCES rebus_d_impianti_audio(id_impianti_audio)
, CONSTRAINT fk_rebus_d_impianti_visivi_01 FOREIGN KEY (fk_impianti_visivi) REFERENCES rebus_d_impianti_visivi(id_impianti_visivi)
, CONSTRAINT fk_rebus_d_proprieta_01 FOREIGN KEY (fk_proprieta_leasing) REFERENCES rebus_d_proprieta(id_proprieta)
, CONSTRAINT fk_rebus_d_tipo_alimentazione_ FOREIGN KEY (fk_tipo_alimentazione) REFERENCES rebus_d_tipo_alimentazione(id_tipo_alimentazione)
, CONSTRAINT fk_rebus_d_tipo_immatricol_01 FOREIGN KEY (fk_tipo_immatricolazione) REFERENCES rebus_d_tipo_immatricol(id_tipo_immatricolazione)
, CONSTRAINT fk_rebus_d_tipologia_dimens_01 FOREIGN KEY (fk_tipo_allestimento,progr_tipo_dimens) REFERENCES rebus_d_tipologia_dimens(id_tipo_allestimento,progr_tipo_dimens)
, CONSTRAINT fk_rebus_t_autobus_02 FOREIGN KEY (primo_telaio) REFERENCES rebus_t_autobus(primo_telaio)
);
CREATE TABLE rebus_t_variaz_autobus
( id_variaz_autobus               INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_variaz_autobus'::regclass)
, fk_azienda                      INT4
, n_telaio                        CHARACTER VARYING(25)   NOT NULL
, n_targa                         CHARACTER VARYING(25)
, data_ultima_immatricolazione    TIMESTAMP
, fk_tipo_alimentazione           INT4                    NOT NULL
, n_posti_sedere                  INT4                    NOT NULL
, n_posti_in_piedi                INT4                    NOT NULL
, n_posti_riservati               INT4
, omologazione_direttiva_europea  CHARACTER VARYING(300)
, fk_classe_ambientale_euro       INT4                    NOT NULL
, flg_filtro_fap                  CHARACTER VARYING(1)
, fk_dotazione_disabili           INT4                    NOT NULL
, fk_impianti_audio               INT4                    NOT NULL
, fk_impianti_visivi              INT4                    NOT NULL
, flg_rilevatore_bip              CHARACTER VARYING(1)
, prezzo_tot_acquisto             FLOAT8
, contributo_pubblico_acquisto    FLOAT8
, flg_veicolo_assicurato          CHARACTER VARYING(1)
, data_ultima_revisione           DATE
, note                            TEXT
, primo_telaio                    CHARACTER VARYING(25)   NOT NULL
, n_matricola_aziendale           CHARACTER VARYING(20)
, fk_tipo_immatricolazione        INT4                    NOT NULL
, ente_autorizz_prima_imm         CHARACTER VARYING(200)
, fk_classe_veicolo               INT4                    NOT NULL
, flg_due_piani                   CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_snodato                     CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, caratteristiche_particolari     CHARACTER VARYING(255)
, altra_alimentazione             CHARACTER VARYING(100)
, fk_tipo_allestimento            INT4                    NOT NULL
, progr_tipo_dimens               INT4                    NOT NULL
, lunghezza                       FLOAT8                  NOT NULL
, numero_porte                    INT4
, posti_carrozzina                INT4                    NOT NULL
, flg_impianto_condizionamento    CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_cabina_guida_isolata        BPCHAR(1)
, altri_dispositivi_prevenz_inc   CHARACTER VARYING(255)
, flg_otx                         CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_avm                         CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, flg_contapasseggeri             CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, fk_proprieta_leasing            INT4                    NOT NULL
, flg_conteggiato_miv             CHARACTER VARYING(1)    NOT NULL  DEFAULT 'U'::CHARACTER VARYING
, fk_deposito                     INT4
, tmp_indirizzo_dep               CHARACTER VARYING(300)
, nota_riservata_azienda          TEXT
, nota_riservata_amp              TEXT
, nota_riservata_rp               TEXT
, data_aggiornamento              TIMESTAMP NOT NULL
, flg_verificato_azienda          CHARACTER VARYING(1)    NOT NULL  DEFAULT 'N'::CHARACTER VARYING
, flg_verificato_amp              BPCHAR(1)               NOT NULL  DEFAULT 'U'::BPCHAR
, flg_alienato                    CHARACTER VARYING(1)              DEFAULT 'N'::CHARACTER VARYING
, flg_richiesta_contr             CHARACTER VARYING(1)
, anno_sost_prog                  NUMERIC(4)
, fk_utente                       INT4                    NOT NULL  DEFAULT 9
, data_inserimento                TIMESTAMP
, motivazione                     CHARACTER VARYING(500)
, fk_portabici                    NUMERIC
, fk_sistema_videosorveglianza    NUMERIC
, fk_sistema_localizzazione       NUMERIC
, flg_bip_cablato                 BOOL
, flg_contapasseggeri_integrato   BOOL
, flg_sistemi_protezione_autista  BOOL
, altri_allestimenti              CHARACTER VARYING(200)
, flg_contribuzione               BOOL                    NOT NULL DEFAULT FALSE
, fk_categoria_veicolo            INT4                    NOT NULL
, CONSTRAINT ak_t_variaz_autobus_01 UNIQUE (primo_telaio)
, CONSTRAINT alien_s_n_r CHECK (((flg_alienato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('R'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT avm_s_n_u CHECK (((flg_avm)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT cab_s_n CHECK ((flg_cabina_guida_isolata = ANY (ARRAY['S'::BPCHAR, 'N'::BPCHAR])))
, CONSTRAINT cond_s_n_u CHECK (((flg_impianto_condizionamento)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT cont_miv_s_n_u CHECK (((flg_conteggiato_miv)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT contap_s_n CHECK (((flg_contapasseggeri)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT dom_val_amp_s_n CHECK ((flg_verificato_amp = ANY (ARRAY['S'::BPCHAR, 'N'::BPCHAR, 'U'::BPCHAR])))
, CONSTRAINT dom_val_az_s_n CHECK (((flg_verificato_azienda)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT duep_s_n_u CHECK (((flg_due_piani)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT fap_s_n CHECK (((flg_filtro_fap)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT otx_s_n CHECK (((flg_otx)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT pk_rebus_t_variaz_autobus PRIMARY KEY (id_variaz_autobus)
, CONSTRAINT rico_s_n CHECK (((flg_richiesta_contr)::text = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT ril_bip_s_n CHECK (((flg_rilevatore_bip)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT snod_s_n_u CHECK (((flg_snodato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT, ('U'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT v_assic_s_n CHECK (((flg_veicolo_assicurato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT fk_rebus_d_classe_amb_euro_01 FOREIGN KEY (fk_classe_ambientale_euro) REFERENCES rebus_d_classe_amb_euro(id_classe_ambientale)
, CONSTRAINT fk_rebus_d_classe_veicolo_01 FOREIGN KEY (fk_classe_veicolo) REFERENCES rebus_d_classe_veicolo(id_classe_veicolo)
, CONSTRAINT fk_rebus_d_dotaz_disabili_01 FOREIGN KEY (fk_dotazione_disabili) REFERENCES rebus_d_dotazione_disabili(id_dotazione_disabili)
, CONSTRAINT fk_rebus_d_impianti_audio_01 FOREIGN KEY (fk_impianti_audio) REFERENCES rebus_d_impianti_audio(id_impianti_audio)
, CONSTRAINT fk_rebus_d_impianti_visivi_01 FOREIGN KEY (fk_impianti_visivi) REFERENCES rebus_d_impianti_visivi(id_impianti_visivi)
, CONSTRAINT fk_rebus_d_proprieta_01 FOREIGN KEY (fk_proprieta_leasing) REFERENCES rebus_d_proprieta(id_proprieta)
, CONSTRAINT fk_rebus_d_tipo_alimentazione_ FOREIGN KEY (fk_tipo_alimentazione) REFERENCES rebus_d_tipo_alimentazione(id_tipo_alimentazione)
, CONSTRAINT fk_rebus_d_tipo_immatricol_01 FOREIGN KEY (fk_tipo_immatricolazione) REFERENCES rebus_d_tipo_immatricol(id_tipo_immatricolazione)
, CONSTRAINT fk_rebus_d_tipologia_dimens_01 FOREIGN KEY (fk_tipo_allestimento,progr_tipo_dimens) REFERENCES rebus_d_tipologia_dimens(id_tipo_allestimento,progr_tipo_dimens)
, CONSTRAINT fk_rebus_t_autobus_02 FOREIGN KEY (primo_telaio) REFERENCES rebus_t_autobus(primo_telaio)
, CONSTRAINT fk_rebus_t_variaz_autobus_01 FOREIGN KEY (fk_portabici) REFERENCES rebusc_d_portabici(id_portabici)
, CONSTRAINT fk_rebus_t_variaz_autobus_02 FOREIGN KEY (fk_sistema_videosorveglianza) REFERENCES rebusc_d_sistema_videosorveglianza(id_sistema_videosorveglianza)
, CONSTRAINT fk_rebus_t_variaz_autobus_03 FOREIGN KEY (fk_sistema_localizzazione) REFERENCES rebusc_d_sistema_localizzazione(id_sistema_localizzazione)
, CONSTRAINT fk_rebus_t_variaz_autobus_04 FOREIGN KEY (fk_categoria_veicolo) REFERENCES rebus_d_categoria_veicolo(id_categoria_veicolo)
);
CREATE TABLE rebusc_r_doc_contribuzione
( id_doc_contribuzione      NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_r_doc_contribuzione'::regclass)
, id_tipo_documento         INT4                    NOT NULL
, documento                 BYTEA                   NOT NULL
, nome_file                 CHARACTER VARYING(100)  NOT NULL
, data_caricamento          TIMESTAMP NOT NULL
, id_utente_aggiornamento   NUMERIC(6)
, CONSTRAINT ak_rebusc_r_doc_contribuzione_01 UNIQUE (id_tipo_documento)
, CONSTRAINT pk_rebusc_r_doc_contribuzione PRIMARY KEY (id_doc_contribuzione)
, CONSTRAINT fk_rebusc_r_doc_contribuzione_02 FOREIGN KEY (id_tipo_documento) REFERENCES rebus_d_tipo_documento(id_tipo_documento)
);
CREATE TABLE rebusc_t_costo_fornitura
( id_costo_fornitura        NUMERIC(6)  NOT NULL  DEFAULT NextVal('seq_t_costo_fornitura'::regclass)
, id_doc_contribuzione      NUMERIC(6)
, costo_ammissibile_reg     FLOAT8
, costo_ammissibile_ff      FLOAT8
, id_utente_aggiornamento   NUMERIC(6)  NOT NULL
, data_aggiornamento        TIMESTAMP   NOT NULL
, CONSTRAINT pk_rebusc_t_costo_fornitura PRIMARY KEY (id_costo_fornitura)
, CONSTRAINT fk_rebusc_t_costo_fornitura_01 FOREIGN KEY (id_doc_contribuzione) REFERENCES rebusc_r_doc_contribuzione(id_doc_contribuzione)
);
CREATE TABLE rebusc_t_dato_fattura
( id_dato_fattura         NUMERIC(6)              NOT NULL  DEFAULT NextVal('seq_t_dato_fattura'::regclass)
, id_dato_spesa           NUMERIC(6)              NOT NULL
, id_doc_contribuzione    NUMERIC(6)
, numero                  CHARACTER VARYING(25)
, "data"                  DATE
, importo                 FLOAT8
, nr_quietanza_az_forn    CHARACTER VARYING(25)
, data_quietanza_az_forn  DATE
, id_tipo_doc_quietanza   NUMERIC
, CONSTRAINT ak_rebusc_t_dato_fattura_01 UNIQUE (id_dato_spesa, id_doc_contribuzione)
, CONSTRAINT pk_rebusc_t_dato_fattura PRIMARY KEY (id_dato_fattura)
, CONSTRAINT fk_rebusc_t_dato_fattura_01 FOREIGN KEY (id_dato_spesa) REFERENCES rebusc_t_dato_spesa(id_dato_spesa)
, CONSTRAINT fk_rebusc_t_dato_fattura_02 FOREIGN KEY (id_doc_contribuzione) REFERENCES rebusc_r_doc_contribuzione(id_doc_contribuzione)
, CONSTRAINT fk_rebusc_t_dato_fattura_03 FOREIGN KEY (id_tipo_doc_quietanza) REFERENCES rebusc_d_tipo_doc_quietanza(id_tipo_doc_quietanza)
);
CREATE TABLE rebusc_t_dato_messa_servizio
( id_dato_messa_servizio    NUMERIC(4)              NOT NULL  DEFAULT NextVal('seq_t_dato_messa_servizio'::regclass)
, id_tipo_sostituzione      NUMERIC
, nr_carta_circolazione     CHARACTER VARYING(11)
, flg_pannello              BOOL
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        DATE                    NOT NULL
, primo_telaio_sost         CHARACTER VARYING(25)
, CONSTRAINT pk_rebusc_t_dato_messa_servizio PRIMARY KEY (id_dato_messa_servizio)
, CONSTRAINT fk_rebusc_t_dato_messa_servizio_01 FOREIGN KEY (primo_telaio_sost) REFERENCES rebus_t_autobus(primo_telaio)
, CONSTRAINT rebusc_d_tipo_sostituzione_rebusc_t_dato_messa_servizio FOREIGN KEY (id_tipo_sostituzione) REFERENCES rebusc_d_tipo_sostituzione(id_tipo_sostituzione)
);
CREATE TABLE rebusc_t_fonte_finanziamento
( id_fonte_finanziamento  NUMERIC(2)              NOT NULL
, id_atto_assegnazione    NUMERIC(3)              NOT NULL
, desc_breve              CHARACTER VARYING(50)   NOT NULL
, desc_estesa             CHARACTER VARYING(100)
, data_inizio_validita    DATE NOT NULL
, data_fine_validita      DATE
, CONSTRAINT ak_rebusc_t_fonte_finanziamento_01 UNIQUE (id_atto_assegnazione, desc_breve, data_inizio_validita)
, CONSTRAINT ak_rebusc_t_fonte_finanziamento_02 UNIQUE (id_atto_assegnazione, desc_estesa, data_inizio_validita)
, CONSTRAINT pk_rebusc_t_fonte_finanziamento PRIMARY KEY (id_fonte_finanziamento)
, CONSTRAINT fk_rebusc_t_fonte_finanziamento_01 FOREIGN KEY (id_atto_assegnazione) REFERENCES rebusc_t_atto_assegnazione(id_atto_assegnazione)
);
CREATE TABLE rebusm_d_campagna
( id_campagna               NUMERIC(3)              NOT NULL
, descrizione               CHARACTER VARYING(100)  NOT NULL
, id_tipo_monitoraggio      NUMERIC(1)              NOT NULL
, data_inizio_validita      DATE                    NOT NULL
, data_fine_validita        DATE                    NOT NULL
, data_inizio_restituzione  DATE                    NOT NULL
, data_fine_restituzione    DATE                    NOT NULL
, CONSTRAINT ak_rebusm_d_campagna_01 UNIQUE (descrizione, id_tipo_monitoraggio, data_inizio_validita, data_fine_validita)
, CONSTRAINT pk_rebusm_d_campagna PRIMARY KEY (id_campagna)
, CONSTRAINT fk_rebusm_d_campagna_01 FOREIGN KEY (id_tipo_monitoraggio) REFERENCES rebusm_d_tipo_monitoraggio(id_tipo_monitoraggio)
);
CREATE TABLE rebusm_r_doc_misurazione
( id_doc_misurazione        NUMERIC(6)              NOT NULL
, id_tipo_documento         INT4                    NOT NULL
, documento                 BYTEA                   NOT NULL
, nome_file                 CHARACTER VARYING(100)  NOT NULL
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_caricamento          TIMESTAMP               NOT NULL
, CONSTRAINT pk_rebusm_r_doc_misurazione PRIMARY KEY (id_doc_misurazione)
, CONSTRAINT fk_rebusm_r_doc_misurazione_01 FOREIGN KEY (id_tipo_documento) REFERENCES rebus_d_tipo_documento(id_tipo_documento)
);
CREATE TABLE rebusm_t_misurazione
( id_misurazione            NUMERIC(6)              NOT NULL
, primo_telaio              CHARACTER VARYING(25)   NOT NULL
, id_doc_misurazione        NUMERIC(6)
, data_inizio               DATE                    NOT NULL
, data_fine                 DATE                    NOT NULL
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMP               NOT NULL
, CONSTRAINT ak_rebusm_t_misurazione_01 UNIQUE (primo_telaio, data_inizio, data_fine)
, CONSTRAINT pk_rebusm_t_misurazione PRIMARY KEY (id_misurazione)
, CONSTRAINT fk_rebusm_t_misurazione_01 FOREIGN KEY (primo_telaio) REFERENCES rebus_t_autobus(primo_telaio)
, CONSTRAINT fk_rebusm_t_misurazione_02 FOREIGN KEY (id_doc_misurazione) REFERENCES rebusm_r_doc_misurazione(id_doc_misurazione)
);
CREATE TABLE rebusp_c_tipo_proc_tipo_doc
( id_tipo_procedimento  NUMERIC(2)  NOT NULL
, id_tipo_documento     INT4        NOT NULL
, CONSTRAINT pk_c_tipo_proc_tipo_doc PRIMARY KEY (id_tipo_procedimento, id_tipo_documento)
, CONSTRAINT fk_c_tipo_proc_tipo_doc_01 FOREIGN KEY (id_tipo_procedimento) REFERENCES rebusp_d_tipo_procedimento(id_tipo_procedimento)
, CONSTRAINT fk_c_tipo_proc_tipo_doc_02 FOREIGN KEY (id_tipo_documento) REFERENCES rebus_d_tipo_documento(id_tipo_documento)
);
CREATE TABLE rebusp_d_motivazione
( id_motivazione        NUMERIC(3)              NOT NULL
, desc_motivazione      CHARACTER VARYING(100)  NOT NULL
, flg_motiv_altro       BOOL                    NOT NULL  DEFAULT FALSE
, data_inizio_validita  DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita    DATE
, id_tipo_procedimento  NUMERIC(2)              NOT NULL
, CONSTRAINT ak_d_motivazione_01 UNIQUE (desc_motivazione, data_inizio_validita, id_tipo_procedimento)
, CONSTRAINT pk_d_motivazione PRIMARY KEY (id_motivazione)
, CONSTRAINT fk_d_motivazione_01 FOREIGN KEY (id_tipo_procedimento) REFERENCES rebusp_d_tipo_procedimento(id_tipo_procedimento)
);
CREATE TABLE rebusp_d_transizione_automa
( id_transizione_automa   NUMERIC(2)              NOT NULL
, id_stato_iter_da        NUMERIC(2)              NOT NULL
, id_stato_iter_a         NUMERIC(2)
, titolo                  CHARACTER VARYING(50)
, "label"                 CHARACTER VARYING(50)
, testo                   CHARACTER VARYING(50)
, condizioni              TEXT
, flg_note_obbligatorie   BOOL                    NOT NULL
, id_tipo_messaggio       INT4
, data_inizio_validita    DATE                    NOT NULL  DEFAULT 'now'::TEXT::DATE
, data_fine_validita      DATE
, flag_default            BOOL                    NOT NULL
, CONSTRAINT ak_d_transizione_automa_01 UNIQUE (id_stato_iter_da, id_stato_iter_a, data_inizio_validita)
, CONSTRAINT chk_d_transizione_automa_01 CHECK ((flg_note_obbligatorie = ANY (ARRAY[false, true])))
, CONSTRAINT pk_d_transizione_automa PRIMARY KEY (id_transizione_automa)
, CONSTRAINT fk_d_transizione_automa_01 FOREIGN KEY (id_stato_iter_da) REFERENCES rebusp_d_stato_iter(id_stato_iter)
, CONSTRAINT fk_d_transizione_automa_02 FOREIGN KEY (id_stato_iter_a) REFERENCES rebusp_d_stato_iter(id_stato_iter)
, CONSTRAINT fk_d_transizione_automa_03 FOREIGN KEY (id_tipo_messaggio) REFERENCES rebus_d_tipo_messaggio(id_tipo_messaggio)
);
CREATE TABLE rebusp_t_procedimento
( id_procedimento           numeric(6)              NOT NULL  DEFAULT NextVal('seq_t_procedimento'::regclass)
, id_motorizzazione         numeric(4)
, id_tipo_procedimento      numeric(2)              NOT NULL
, id_motivazione            numeric(3)
, testo_motiv_altro         CHARACTER VARYING(200)
, ruolo_firma               CHARACTER VARYING(75)
, ruolo_firma_ente          CHARACTER VARYING(75)
, nominativo_firma          CHARACTER VARYING(100)
, nominativo_firma_ente     CHARACTER VARYING(100)
, premesse                  text
, prescrizioni              text
, num_procedimento          numeric(4)
, note                      CHARACTER VARYING(2000)
, id_utente_aggiornamento   numeric(6)              NOT NULL
, data_aggiornamento        timestamptz             NOT NULL  DEFAULT now()
, flg_firma_digitale_ente   bool                              DEFAULT FALSE
, flg_firma_digitale        bool                              DEFAULT FALSE
, CONSTRAINT pk_t_procedimento PRIMARY KEY (id_procedimento)
, CONSTRAINT fk_d_procedimento_02 FOREIGN KEY (id_tipo_procedimento) REFERENCES rebusp_d_tipo_procedimento(id_tipo_procedimento)
, CONSTRAINT fk_t_procedimento_01 FOREIGN KEY (id_motorizzazione) REFERENCES rebusp_d_motorizzazione(id_motorizzazione)
, CONSTRAINT fk_t_procedimento_03 FOREIGN KEY (id_motivazione) REFERENCES rebusp_d_motivazione(id_motivazione)
);
CREATE TABLE rebusp_t_sub_procedimento
( id_procedimento         NUMERIC(6)  NOT NULL
, id_sub_procedimento_1   NUMERIC(6)  NOT NULL
, id_sub_procedimento_2   NUMERIC(6)  NOT NULL
, CONSTRAINT pk_t_sub_procedimento PRIMARY KEY (id_procedimento, id_sub_procedimento_1, id_sub_procedimento_2)
, CONSTRAINT fk_t_sub_procedimento_01 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_t_sub_procedimento_02 FOREIGN KEY (id_sub_procedimento_1) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_t_sub_procedimento_03 FOREIGN KEY (id_sub_procedimento_2) REFERENCES rebusp_t_procedimento(id_procedimento)
);
CREATE TABLE rebus_r_azienda_autobus
( primo_telaio        CHARACTER VARYING(25)   NOT NULL
, fk_azienda          INT4                    NOT NULL
, data_aggiornamento  TIMESTAMP               NOT NULL
, data_alienazione    TIMESTAMP
, CONSTRAINT pk_rebus_r_azienda_autobus PRIMARY KEY (primo_telaio, fk_azienda, data_aggiornamento)
, CONSTRAINT fk_rebus_t_autobus_01 FOREIGN KEY (primo_telaio) REFERENCES rebus_t_autobus(primo_telaio)
);
CREATE TABLE rebus_r_storia_varautobus_dp
( id_dispositivo            INT4  NOT NULL
, id_storia_variaz_autobus  INT4  NOT NULL
, CONSTRAINT pk_rebus_r_storia_varautob_dp PRIMARY KEY (id_dispositivo, id_storia_variaz_autobus)
, CONSTRAINT fk_rebus_d_disp_prevenz_02 FOREIGN KEY (id_dispositivo) REFERENCES rebus_d_dispositivi_prevenz(id_dispositivo)
, CONSTRAINT fk_rebus_t_stor_varautobus_01 FOREIGN KEY (id_storia_variaz_autobus) REFERENCES rebus_t_storia_variaz_autobus(id_storia_variaz_autobus)
);
CREATE TABLE rebus_r_varautobus_dp
( id_dispositivo      int4  NOT NULL
, id_variaz_autobus   int4  NOT NULL
, CONSTRAINT pk_rebus_r_varautobus_dp PRIMARY KEY (id_dispositivo, id_variaz_autobus)
, CONSTRAINT fk_rebus_d_disp_prevenz_01 FOREIGN KEY (id_dispositivo) REFERENCES rebus_d_dispositivi_prevenz(id_dispositivo)
, CONSTRAINT fk_rebus_t_varautobus_01 FOREIGN KEY (id_variaz_autobus) REFERENCES rebus_t_variaz_autobus(id_variaz_autobus)
);
CREATE TABLE rebus_t_messaggi
( id_messaggio                    INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_messaggi'::regclass)
, fk_tipo_messaggio               INT4                    NOT NULL
, fk_utente_mittente              INT4                    NOT NULL
, fk_utente_destinatario          INT4                    NOT NULL
, fk_variaz_autobus               INT4
, fk_storia_variaz_autobus        INT4
, messaggio                       CHARACTER VARYING(300)
, data_creazione                  TIMESTAMP
, flg_letto                       CHARACTER VARYING(1)
, data_lettura                    TIMESTAMP
, flg_archiviato                  CHARACTER VARYING(1)
, data_archiviazione              TIMESTAMP
, fk_storia_variaz_autobus_succ   INT4
, fk_procedimento                 NUMERIC(6)
, fk_tipo_messaggio_sistema       INT4
, CONSTRAINT msg_archiviato_s_n CHECK (((flg_archiviato)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT msg_letto_s_n CHECK (((flg_letto)::TEXT = ANY (ARRAY[('S'::CHARACTER VARYING)::TEXT, ('N'::CHARACTER VARYING)::TEXT])))
, CONSTRAINT pk_rebus_t_messaggi PRIMARY KEY (id_messaggio)
, CONSTRAINT fk_d_tipo_messaggio_sistema_01 FOREIGN KEY (fk_tipo_messaggio_sistema) REFERENCES rebus_d_tipo_messaggio_sistema(id_tipo_messaggio_sistema)
, CONSTRAINT fk_rebus_d_tipo_messaggio_01 FOREIGN KEY (fk_tipo_messaggio) REFERENCES rebus_d_tipo_messaggio(id_tipo_messaggio)
, CONSTRAINT fk_rebus_t_storia_variaz_autobus_msg_1 FOREIGN KEY (fk_storia_variaz_autobus) REFERENCES rebus_t_storia_variaz_autobus(id_storia_variaz_autobus)
, CONSTRAINT fk_rebus_t_variaz_autobus_msg_1 FOREIGN KEY (fk_variaz_autobus) REFERENCES rebus_t_variaz_autobus(id_variaz_autobus)
, CONSTRAINT fk_t_messaggi_01 FOREIGN KEY (fk_storia_variaz_autobus_succ) REFERENCES rebus_t_storia_variaz_autobus(id_storia_variaz_autobus)
, CONSTRAINT fk_t_messaggi_02 FOREIGN KEY (fk_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
);
CREATE TABLE rebus_t_province
( id_provincia      INT4                    NOT NULL
, fk_regione        INT4                    NOT NULL
, ds_provincia      CHARACTER VARYING(30)
, codice_istat      CHARACTER VARYING(4)
, sigla_provincia   CHARACTER VARYING(4)
, data_fine_val     DATE
, CONSTRAINT pk_rebus_t_province PRIMARY KEY (id_provincia)
, CONSTRAINT fk_rebus_t_regioni_01 FOREIGN KEY (fk_regione) REFERENCES rebus_t_regioni(id_regione)
);
CREATE TABLE rebusc_r_doc_dato_messa_servizio
( id_dato_messa_servizio  NUMERIC(4)  NOT NULL
, id_doc_contribuzione    NUMERIC(6)  NOT NULL
, CONSTRAINT pk_rebusc_r_doc_dato_messa_servizio PRIMARY KEY (id_dato_messa_servizio, id_doc_contribuzione)
, CONSTRAINT fk_rebusc_r_doc_dato_messa_servizio_01 FOREIGN KEY (id_dato_messa_servizio) REFERENCES rebusc_t_dato_messa_servizio(id_dato_messa_servizio)
, CONSTRAINT fk_rebusc_r_doc_dato_messa_servizio_02 FOREIGN KEY (id_doc_contribuzione) REFERENCES rebusc_r_doc_contribuzione(id_doc_contribuzione)
);
CREATE TABLE rebusc_r_fattura_bonifico
( id_dato_fattura   NUMERIC(6)  NOT NULL
, id_dato_bonifico  NUMERIC(6)  NOT NULL
, CONSTRAINT pk_rebusc_r_fattura_bonifico PRIMARY KEY (id_dato_fattura, id_dato_bonifico)
, CONSTRAINT fk_rebusc_r_fattura_bonifico_01 FOREIGN KEY (id_dato_fattura) REFERENCES rebusc_t_dato_fattura(id_dato_fattura)
, CONSTRAINT fk_rebusc_r_fattura_bonifico_02 FOREIGN KEY (id_dato_bonifico) REFERENCES rebusc_t_dato_bonifico(id_dato_bonifico)
);
CREATE TABLE rebusc_r_voce_costo_fornitura
( id_voce_costo_fornitura   NUMERIC(6)  NOT NULL  DEFAULT NextVal('seq_r_voce_costo_veicolo'::regclass)
, id_voce_costo             NUMERIC(2)  NOT NULL
, importo                   FLOAT8
, id_costo_fornitura        NUMERIC(6)
, CONSTRAINT pk_r_voce_costo_fornitura PRIMARY KEY (id_voce_costo_fornitura)
, CONSTRAINT fk_r_voce_costo_fornitura_01 FOREIGN KEY (id_voce_costo) REFERENCES rebusc_d_voce_costo(id_voce_costo)
, CONSTRAINT fk_r_voce_costo_fornitura_02 FOREIGN KEY (id_costo_fornitura) REFERENCES rebusc_t_costo_fornitura(id_costo_fornitura)
);
CREATE TABLE rebusc_t_assegnazione_risorse
( id_assegnazione_risorse   NUMERIC(6)  NOT NULL  DEFAULT NextVal('seq_t_assegnazione_risorse'::regclass)
, id_fonte_finanziamento    NUMERIC(2)  NOT NULL
, contributo_statale        FLOAT8
, contributo_regionale_agg  FLOAT8
, id_utente_aggiornamento   NUMERIC(6)  NOT NULL
, data_aggiornamento        TIMESTAMP   NOT NULL
, CONSTRAINT pk_rebusc_t_assegnazione_risorse PRIMARY KEY (id_assegnazione_risorse)
, CONSTRAINT fk_rebusc_t_assegnazione_risorse_03 FOREIGN KEY (id_fonte_finanziamento) REFERENCES rebusc_t_fonte_finanziamento(id_fonte_finanziamento)
);
CREATE TABLE rebusc_t_contribuzione
( id_contribuzione          NUMERIC(4)  NOT NULL  DEFAULT NextVal('seq_t_contribuzione'::regclass)
, id_assegnazione_risorse   NUMERIC(6)
, id_ordine_acquisto        NUMERIC(6)
, id_costo_fornitura        NUMERIC(6)
, id_dato_spesa             NUMERIC(6)
, id_dato_messa_servizio    NUMERIC(4)
, id_procedimento           NUMERIC(6)  NOT NULL
, id_utente_aggiornamento   NUMERIC(6)  NOT NULL
, data_aggiornamento        TIMESTAMP   NOT NULL
, CONSTRAINT ak_rebusc_t_contribuzione_01 UNIQUE (id_procedimento)
, CONSTRAINT pk_rebusc_t_contribuzione PRIMARY KEY (id_contribuzione)
, CONSTRAINT fk_rebusc_t_contribuzione_01 FOREIGN KEY (id_assegnazione_risorse) REFERENCES rebusc_t_assegnazione_risorse(id_assegnazione_risorse)
, CONSTRAINT fk_rebusc_t_contribuzione_02 FOREIGN KEY (id_ordine_acquisto) REFERENCES rebusc_t_ordine_acquisto(id_ordine_acquisto)
, CONSTRAINT fk_rebusc_t_contribuzione_03 FOREIGN KEY (id_costo_fornitura) REFERENCES rebusc_t_costo_fornitura(id_costo_fornitura)
, CONSTRAINT fk_rebusc_t_contribuzione_04 FOREIGN KEY (id_dato_spesa) REFERENCES rebusc_t_dato_spesa(id_dato_spesa)
, CONSTRAINT fk_rebusc_t_contribuzione_05 FOREIGN KEY (id_dato_messa_servizio) REFERENCES rebusc_t_dato_messa_servizio(id_dato_messa_servizio)
, CONSTRAINT fk_rebusc_t_contribuzione_06 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
);
CREATE TABLE rebusm_r_campagna_misurazione
( id_campagna     NUMERIC(3)  NOT NULL
, id_misurazione  NUMERIC(6)  NOT NULL
, CONSTRAINT pk_rebusm_r_campagna_misurazione PRIMARY KEY (id_campagna, id_misurazione)
, CONSTRAINT fk_rebusm_r_campagna_misurazione_01 FOREIGN KEY (id_campagna) REFERENCES rebusm_d_campagna(id_campagna)
, CONSTRAINT fk_rebusm_r_campagna_misurazione_02 FOREIGN KEY (id_misurazione) REFERENCES rebusm_t_misurazione(id_misurazione)
);
CREATE TABLE rebusm_t_mis_emissioni
( id_misurazione          NUMERIC(6)  NOT NULL
, id_tipo_allestimento    INT4        NOT NULL
, km_percorsi             NUMERIC     NOT NULL
, emissioni_co2           NUMERIC     NOT NULL
, emissioni_nox           NUMERIC     NOT NULL
, emissioni_pm10_peso     NUMERIC     NOT NULL
, emissioni_pm10_numero   NUMERIC     NOT NULL
, CONSTRAINT pk_rebusm_t_mis_emissioni PRIMARY KEY (id_misurazione)
, CONSTRAINT fk_rebusm_t_mis_emissioni_01 FOREIGN KEY (id_misurazione) REFERENCES rebusm_t_misurazione(id_misurazione)
, CONSTRAINT fk_rebusm_t_mis_emissioni_02 FOREIGN KEY (id_tipo_allestimento) REFERENCES rebus_d_tipo_allestimento(id_tipo_allestimento)
);
CREATE TABLE rebusm_t_mis_portabici
( id_misurazione              NUMERIC(6)              NOT NULL
, id_portabici                NUMERIC                 NOT NULL
, flag_sensoristica_pb        NUMERIC                 NOT NULL
, num_bici_trasportabili      NUMERIC                 NOT NULL
, tot_bici_trasportate        NUMERIC                 NOT NULL
, mesi_utilizzo_prevalente    CHARACTER VARYING(40)   NOT NULL
, giorni_utilizzo_prevalente  CHARACTER VARYING(40)   NOT NULL
, CONSTRAINT pk_rebusm_t_mis_portabici PRIMARY KEY (id_misurazione)
, CONSTRAINT fk_rebusm_t_mis_portabici_01 FOREIGN KEY (id_misurazione) REFERENCES rebusm_t_misurazione(id_misurazione)
, CONSTRAINT fk_rebusm_t_mis_portabici_02 FOREIGN KEY (id_portabici) REFERENCES rebusc_d_portabici(id_portabici)
);
CREATE TABLE rebusp_r_proc_contratto
( id_proc_contratto         NUMERIC(7)    NOT NULL  DEFAULT NextVal('seq_r_proc_contratto'::regclass)
, id_procedimento           NUMERIC(6)    NOT NULL
, id_contratto              NUMERIC(5)    NOT NULL
, id_tipo_ruolo             NUMERIC(2)
, id_utente_aggiornamento   NUMERIC(6)    NOT NULL
, data_aggiornamento        TIMESTAMPTZ   NOT NULL  DEFAULT now()
, CONSTRAINT ak_r_proc_contratto_01 UNIQUE (id_procedimento, id_contratto, id_tipo_ruolo)
, CONSTRAINT pk_r_proc_contratto PRIMARY KEY (id_proc_contratto)
, CONSTRAINT fk_r_proc_contratto_01 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_r_proc_contratto_02 FOREIGN KEY (id_tipo_ruolo) REFERENCES rebusp_d_tipo_ruolo(id_tipo_ruolo)
);
CREATE TABLE rebusp_r_proc_documento
( id_procedimento           NUMERIC(6)              NOT NULL
, id_tipo_documento         INT4                    NOT NULL
, documento                 BYTEA                   NOT NULL
, nome_file                 CHARACTER VARYING(100)  NOT NULL
, data_caricamento          TIMESTAMPTZ             NOT NULL DEFAULT now()
, note                      CHARACTER VARYING(200)
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, CONSTRAINT ak_r_proc_documento_01 UNIQUE (nome_file, data_caricamento)
, CONSTRAINT pk_r_proc_documento PRIMARY KEY (id_procedimento, id_tipo_documento)
, CONSTRAINT fk_r_proc_documento_01 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_r_proc_documento_02 FOREIGN KEY (id_tipo_documento) REFERENCES rebus_d_tipo_documento(id_tipo_documento)
);
CREATE TABLE rebusp_r_proc_veicolo
( id_procedimento           NUMERIC(6)              NOT NULL
, primo_telaio              CHARACTER VARYING(25)   NOT NULL
, id_utente_aggiornamento   NUMERIC(6)              NOT NULL
, data_aggiornamento        TIMESTAMPTZ             NOT NULL DEFAULT now()
, CONSTRAINT pk_r_proc_veicolo PRIMARY KEY (id_procedimento, primo_telaio)
, CONSTRAINT fk_r_proc_veicolo_01 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_r_proc_veicolo_02 FOREIGN KEY (primo_telaio) REFERENCES rebus_t_autobus(primo_telaio)
);
CREATE TABLE rebusp_t_iter_procedimento
( id_iter_procedimento  NUMERIC(7)              NOT NULL  DEFAULT NextVal('seq_t_iter_procedimento'::regclass)
, id_procedimento       NUMERIC(6)              NOT NULL
, id_stato_iter         NUMERIC(2)              NOT NULL
, note                  CHARACTER VARYING(500)
, data_inizio_validita  TIMESTAMPTZ             NOT NULL  DEFAULT now()
, data_fine_validita    TIMESTAMPTZ
, id_utente_sog_giurid  NUMERIC(7)
, CONSTRAINT ak_t_iter_procedimento_01 UNIQUE (id_procedimento, id_stato_iter, data_inizio_validita)
, CONSTRAINT pk_t_iter_procedimento PRIMARY KEY (id_iter_procedimento)
, CONSTRAINT fk_t_iter_procedimento_01 FOREIGN KEY (id_procedimento) REFERENCES rebusp_t_procedimento(id_procedimento)
, CONSTRAINT fk_t_iter_procedimento_02 FOREIGN KEY (id_stato_iter) REFERENCES rebusp_d_stato_iter(id_stato_iter)
);
CREATE TABLE rebus_t_comuni
( id_comune       INT4                    NOT NULL
, codice_istat    CHARACTER VARYING(255)
, ds_comune       CHARACTER VARYING(50)
, fk_provincia    INT4                    NOT NULL
, data_fine_val   DATE
, CONSTRAINT pk_rebus_t_comuni PRIMARY KEY (id_comune)
, CONSTRAINT fk_rebus_t_province_01 FOREIGN KEY (fk_provincia) REFERENCES rebus_t_province(id_provincia)
);
CREATE TABLE rebus_t_depositi
( id_deposito     INT4                    NOT NULL  DEFAULT NextVal('seq_rebus_t_depositi'::regclass)
, indirizzo_dep   CHARACTER VARYING(300)
, fk_comune       INT4                    NOT NULL
, CONSTRAINT pk_rebus_t_depositi PRIMARY KEY (id_deposito)
, CONSTRAINT fk_rebus_t_comuni_02 FOREIGN KEY (fk_comune) REFERENCES rebus_t_comuni(id_comune)
);
CREATE TABLE rebusc_r_oggetto_fattura
( id_dato_fattura           NUMERIC(6)  NOT NULL
, id_voce_costo_fornitura   NUMERIC(6)  NOT NULL
, CONSTRAINT pk_rebusc_r_oggetto_fattura PRIMARY KEY (id_dato_fattura, id_voce_costo_fornitura)
, CONSTRAINT fk_rebusc_r_oggetto_fattura_01 FOREIGN KEY (id_dato_fattura) REFERENCES rebusc_t_dato_fattura(id_dato_fattura)
, CONSTRAINT fk_rebusc_r_oggetto_fattura_02 FOREIGN KEY (id_voce_costo_fornitura) REFERENCES rebusc_r_voce_costo_fornitura(id_voce_costo_fornitura)
);
CREATE TABLE rebus_r_depositi_azienda
( id_deposito   INT4  NOT NULL
, id_azienda    INT4  NOT NULL
, CONSTRAINT pk_rebus_r_depositi_azienda PRIMARY KEY (id_deposito, id_azienda)
, CONSTRAINT fk_rebus_t_depositi_01 FOREIGN KEY (id_deposito) REFERENCES rebus_t_depositi(id_deposito)
);



ALTER TABLE rebus_d_categoria_veicolo                   OWNER TO rebus;
ALTER TABLE rebus_d_classe_amb_euro                     OWNER TO rebus;
ALTER TABLE rebus_d_classe_veicolo                      OWNER TO rebus;
ALTER TABLE rebus_d_dispositivi_prevenz                 OWNER TO rebus;
ALTER TABLE rebus_d_dotazione_disabili                  OWNER TO rebus;
ALTER TABLE rebus_d_impianti_audio                      OWNER TO rebus;
ALTER TABLE rebus_d_impianti_visivi                     OWNER TO rebus;
ALTER TABLE rebus_d_proprieta                           OWNER TO rebus;
ALTER TABLE rebus_d_ruolo_azienda                       OWNER TO rebus;
ALTER TABLE rebus_d_tipo_alimentazione                  OWNER TO rebus;
ALTER TABLE rebus_d_tipo_allestimento                   OWNER TO rebus;
ALTER TABLE rebus_d_tipo_documento                      OWNER TO rebus;
ALTER TABLE rebus_d_tipo_immatricol                     OWNER TO rebus;
ALTER TABLE rebus_d_tipo_messaggio                      OWNER TO rebus;
ALTER TABLE rebus_d_tipo_messaggio_sistema              OWNER TO rebus;
ALTER TABLE rebus_r_azienda_azienda                     OWNER TO rebus;
ALTER TABLE rebus_t_bacini                              OWNER TO rebus;
ALTER TABLE rebus_t_bandi                               OWNER TO rebus;
ALTER TABLE rebus_t_consorzi                            OWNER TO rebus;
ALTER TABLE rebus_t_gestori_trasporti                   OWNER TO rebus;
ALTER TABLE rebus_t_stati                               OWNER TO rebus;
ALTER TABLE rebusc_d_portabici                          OWNER TO rebus;
ALTER TABLE rebusc_d_sistema_bigliettazione             OWNER TO rebus;
ALTER TABLE rebusc_d_sistema_localizzazione             OWNER TO rebus;
ALTER TABLE rebusc_d_sistema_videosorveglianza          OWNER TO rebus;
ALTER TABLE rebusc_d_tipo_doc_quietanza                 OWNER TO rebus;
ALTER TABLE rebusc_d_tipo_garanzia                      OWNER TO rebus;
ALTER TABLE rebusc_d_tipo_sostituzione                  OWNER TO rebus;
ALTER TABLE rebusc_d_voce_costo                         OWNER TO rebus;
ALTER TABLE rebusc_t_atto_assegnazione                  OWNER TO rebus;
ALTER TABLE rebusc_t_dato_bonifico                      OWNER TO rebus;
ALTER TABLE rebusc_t_dato_spesa                         OWNER TO rebus;
ALTER TABLE rebusc_t_ordine_acquisto                    OWNER TO rebus;
ALTER TABLE rebusm_d_tipo_monitoraggio                  OWNER TO rebus;
ALTER TABLE rebusp_d_motorizzazione                     OWNER TO rebus;
ALTER TABLE rebusp_d_stato_iter                         OWNER TO rebus;
ALTER TABLE rebusp_d_tipo_procedimento                  OWNER TO rebus;
ALTER TABLE rebusp_d_tipo_ruolo                         OWNER TO rebus;
ALTER TABLE rebusp_d_tipo_stampa                        OWNER TO rebus;
ALTER TABLE rebusp_r_proc_contratto_linea               OWNER TO rebus;
ALTER TABLE rebusp_t_documento                          OWNER TO rebus;
ALTER TABLE rebus_d_tipologia_dimens                    OWNER TO rebus;
ALTER TABLE rebus_r_consorzi_aziende                    OWNER TO rebus;
ALTER TABLE rebus_r_contratti_servizio                  OWNER TO rebus;
ALTER TABLE rebus_r_doc_variaz_autobus                  OWNER TO rebus;
ALTER TABLE rebus_t_autobus                             OWNER TO rebus;
ALTER TABLE rebus_t_regioni                             OWNER TO rebus;
ALTER TABLE rebus_t_storia_variaz_autobus               OWNER TO rebus;
ALTER TABLE rebus_t_variaz_autobus                      OWNER TO rebus;
ALTER TABLE rebusc_r_doc_contribuzione                  OWNER TO rebus;
ALTER TABLE rebusc_t_costo_fornitura                    OWNER TO rebus;
ALTER TABLE rebusc_t_dato_fattura                       OWNER TO rebus;
ALTER TABLE rebusc_t_dato_messa_servizio                OWNER TO rebus;
ALTER TABLE rebusc_t_fonte_finanziamento                OWNER TO rebus;
ALTER TABLE rebusm_d_campagna                           OWNER TO rebus;
ALTER TABLE rebusm_r_doc_misurazione                    OWNER TO rebus;
ALTER TABLE rebusm_t_misurazione                        OWNER TO rebus;
ALTER TABLE rebusp_c_tipo_proc_tipo_doc                 OWNER TO rebus;
ALTER TABLE rebusp_d_motivazione                        OWNER TO rebus;
ALTER TABLE rebusp_d_transizione_automa                 OWNER TO rebus;
ALTER TABLE rebusp_t_procedimento                       OWNER TO rebus;
ALTER TABLE rebusp_t_sub_procedimento                   OWNER TO rebus;
ALTER TABLE rebus_r_azienda_autobus                     OWNER TO rebus;
ALTER TABLE rebus_r_storia_varautobus_dp                OWNER TO rebus;
ALTER TABLE rebus_r_varautobus_dp                       OWNER TO rebus;
ALTER TABLE rebus_t_messaggi                            OWNER TO rebus;
ALTER TABLE rebus_t_province                            OWNER TO rebus;
ALTER TABLE rebusc_r_doc_dato_messa_servizio            OWNER TO rebus;
ALTER TABLE rebusc_r_fattura_bonifico                   OWNER TO rebus;
ALTER TABLE rebusc_r_voce_costo_fornitura               OWNER TO rebus;
ALTER TABLE rebusc_t_assegnazione_risorse               OWNER TO rebus;
ALTER TABLE rebusc_t_contribuzione                      OWNER TO rebus;
ALTER TABLE rebusm_r_campagna_misurazione               OWNER TO rebus;
ALTER TABLE rebusm_t_mis_emissioni                      OWNER TO rebus;
ALTER TABLE rebusm_t_mis_portabici                      OWNER TO rebus;
ALTER TABLE rebusp_r_proc_contratto                     OWNER TO rebus;
ALTER TABLE rebusp_r_proc_documento                     OWNER TO rebus;
ALTER TABLE rebusp_r_proc_veicolo                       OWNER TO rebus;
ALTER TABLE rebusp_t_iter_procedimento                  OWNER TO rebus;
ALTER TABLE rebus_t_comuni                              OWNER TO rebus;
ALTER TABLE rebus_t_depositi                            OWNER TO rebus;
ALTER TABLE rebusc_r_oggetto_fattura                    OWNER TO rebus;
ALTER TABLE rebus_r_depositi_azienda                    OWNER TO rebus;



GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_categoria_veicolo TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_categoria_veicolo TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_categoria_veicolo TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_amb_euro TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_amb_euro TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_amb_euro TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_classe_amb_euro TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_veicolo TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_veicolo TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_classe_veicolo TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_classe_veicolo TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dispositivi_prevenz TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dispositivi_prevenz TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dispositivi_prevenz TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_dispositivi_prevenz TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dotazione_disabili TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dotazione_disabili TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_dotazione_disabili TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_dotazione_disabili TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_audio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_audio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_audio TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_impianti_audio TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_visivi TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_visivi TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_impianti_visivi TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_impianti_visivi TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_proprieta TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_proprieta TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_proprieta TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_proprieta TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_ruolo_azienda TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_ruolo_azienda TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_ruolo_azienda TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_ruolo_azienda TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_alimentazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_alimentazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_alimentazione TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_alimentazione TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_allestimento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_allestimento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_allestimento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_allestimento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_documento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_documento TO sirtpl_contratti;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_documento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_documento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_documento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_immatricol TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_immatricol TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_immatricol TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_immatricol TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_messaggio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_messaggio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_messaggio TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_messaggio TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipo_messaggio_sistema TO rebus_rw;
GRANT SELECT                         ON TABLE rebus_d_tipo_messaggio_sistema TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_azienda TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_azienda TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_azienda TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_azienda_azienda TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bacini TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bacini TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bacini TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_bacini TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bandi TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bandi TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_bandi TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_bandi TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_consorzi TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_consorzi TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_consorzi TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_consorzi TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_gestori_trasporti TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_gestori_trasporti TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_gestori_trasporti TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_gestori_trasporti TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_stati TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_stati TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_stati TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_stati TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_portabici TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_portabici TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_portabici TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_bigliettazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_bigliettazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_bigliettazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_localizzazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_localizzazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_localizzazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_videosorveglianza TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_videosorveglianza TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_sistema_videosorveglianza TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_doc_quietanza TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_doc_quietanza TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_doc_quietanza TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_garanzia TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_garanzia TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_garanzia TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_sostituzione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_sostituzione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_tipo_sostituzione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_voce_costo TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_voce_costo TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_d_voce_costo TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_atto_assegnazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_atto_assegnazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_atto_assegnazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_bonifico TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_bonifico TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_bonifico TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_spesa TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_spesa TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_spesa TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_ordine_acquisto TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_ordine_acquisto TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_ordine_acquisto TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_tipo_monitoraggio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_tipo_monitoraggio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_tipo_monitoraggio TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motorizzazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motorizzazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motorizzazione TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_d_motorizzazione TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_stato_iter TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_stato_iter TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_stato_iter TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_d_stato_iter TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_procedimento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_procedimento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_procedimento TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_d_tipo_procedimento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_ruolo TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_ruolo TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_ruolo TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_d_tipo_ruolo TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_stampa TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_stampa TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_tipo_stampa TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_d_tipo_stampa TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto_linea TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto_linea TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto_linea TO sirtpl_trasv_rw;
GRANT SELECT ON TABLE rebusp_r_proc_contratto_linea TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_documento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_documento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_documento TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipologia_dimens TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipologia_dimens TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_d_tipologia_dimens TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_d_tipologia_dimens TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_consorzi_aziende TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_consorzi_aziende TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_consorzi_aziende TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_consorzi_aziende TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_contratti_servizio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_contratti_servizio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_contratti_servizio TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_contratti_servizio TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_doc_variaz_autobus TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_doc_variaz_autobus TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_doc_variaz_autobus TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_doc_variaz_autobus TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_autobus TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_autobus TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_autobus TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_autobus TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_regioni TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_regioni TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_regioni TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_regioni TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_storia_variaz_autobus TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_storia_variaz_autobus TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_storia_variaz_autobus TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_storia_variaz_autobus TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_variaz_autobus TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_variaz_autobus TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_variaz_autobus TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_variaz_autobus TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_contribuzione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_contribuzione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_contribuzione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_costo_fornitura TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_costo_fornitura TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_costo_fornitura TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_fattura TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_fattura TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_fattura TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_messa_servizio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_messa_servizio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_dato_messa_servizio TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_fonte_finanziamento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_fonte_finanziamento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_fonte_finanziamento TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_campagna TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_campagna TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_d_campagna TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_doc_misurazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_doc_misurazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_doc_misurazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_misurazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_misurazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_misurazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_c_tipo_proc_tipo_doc TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_c_tipo_proc_tipo_doc TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_c_tipo_proc_tipo_doc TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_c_tipo_proc_tipo_doc TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motivazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motivazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_motivazione TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_d_motivazione TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_transizione_automa TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_transizione_automa TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_d_transizione_automa TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_d_transizione_automa TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_procedimento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_procedimento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_procedimento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_t_procedimento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_sub_procedimento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_sub_procedimento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_sub_procedimento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_t_sub_procedimento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_autobus TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_autobus TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_azienda_autobus TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_azienda_autobus TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_storia_varautobus_dp TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_storia_varautobus_dp TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_storia_varautobus_dp TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_storia_varautobus_dp TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_varautobus_dp TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_varautobus_dp TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_varautobus_dp TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_varautobus_dp TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_messaggi TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_messaggi TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_messaggi TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_messaggi TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_province TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_province TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_province TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_province TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_dato_messa_servizio TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_dato_messa_servizio TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_doc_dato_messa_servizio TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_fattura_bonifico TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_fattura_bonifico TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_fattura_bonifico TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_voce_costo_fornitura TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_voce_costo_fornitura TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_voce_costo_fornitura TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_assegnazione_risorse TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_assegnazione_risorse TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_assegnazione_risorse TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_contribuzione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_contribuzione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_t_contribuzione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_campagna_misurazione TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_campagna_misurazione TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_r_campagna_misurazione TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_emissioni TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_emissioni TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_emissioni TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_portabici TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_portabici TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusm_t_mis_portabici TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto TO rebus_rw;
GRANT SELECT                         ON TABLE rebusp_r_proc_contratto TO sirtpl_contratti;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_contratto TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_r_proc_contratto TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_documento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_documento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_documento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_r_proc_documento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_veicolo TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_veicolo TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_r_proc_veicolo TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_r_proc_veicolo TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_iter_procedimento TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_iter_procedimento TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusp_t_iter_procedimento TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebusp_t_iter_procedimento TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_comuni TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_comuni TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_comuni TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_comuni TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_depositi TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_depositi TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_t_depositi TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_t_depositi TO sirtpl_trasv_ro;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_oggetto_fattura TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_oggetto_fattura TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebusc_r_oggetto_fattura TO sirtpl_trasv_rw;
----
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_depositi_azienda TO rebus_rw;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_depositi_azienda TO sirtpl_trasv;
GRANT UPDATE, INSERT, SELECT, DELETE ON TABLE rebus_r_depositi_azienda TO sirtpl_trasv_rw;
GRANT SELECT                         ON TABLE rebus_r_depositi_azienda TO sirtpl_trasv_ro;
