CREATE SEQUENCE seq_r_doc_contribuzione           INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_r_proc_contratto              INCREMENT BY 1 MINVALUE 1 MAXVALUE            9999999 START 1;
CREATE SEQUENCE seq_r_voce_costo_veicolo          INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_rebus_t_aziende               INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_bacini                INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_bandi                 INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_consorzi              INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_depositi              INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_gestori_trasporti     INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_messaggi              INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_storia_variaz_autobus INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_utenti                INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_variaz_autobus        INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_rebus_t_variaz_aziende        INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999 START 1;
CREATE SEQUENCE seq_t_assegnazione_risorse        INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_contribuzione               INCREMENT BY 1 MINVALUE 1 MAXVALUE               9999 START 1;
CREATE SEQUENCE seq_t_costo_fornitura             INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_dato_bonifico               INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_dato_fattura                INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_dato_messa_servizio         INCREMENT BY 1 MINVALUE 1 MAXVALUE               9999 START 1;
CREATE SEQUENCE seq_t_dato_spesa                  INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_documento                   INCREMENT BY 1 MINVALUE 1 MAXVALUE                999 START 1;
CREATE SEQUENCE seq_t_iter_procedimento           INCREMENT BY 1 MINVALUE 1 MAXVALUE            9999999 START 1;
CREATE SEQUENCE seq_t_ordine_acquisto             INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;
CREATE SEQUENCE seq_t_procedimento                INCREMENT BY 1 MINVALUE 1 MAXVALUE             999999 START 1;


ALTER SEQUENCE seq_r_doc_contribuzione            OWNER TO rebus;
ALTER SEQUENCE seq_r_proc_contratto               OWNER TO rebus;
ALTER SEQUENCE seq_r_voce_costo_veicolo           OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_aziende                OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_bacini                 OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_bandi                  OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_consorzi               OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_depositi               OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_gestori_trasporti      OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_messaggi               OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_storia_variaz_autobus  OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_utenti                 OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_variaz_autobus         OWNER TO rebus;
ALTER SEQUENCE seq_rebus_t_variaz_aziende         OWNER TO rebus;
ALTER SEQUENCE seq_t_assegnazione_risorse         OWNER TO rebus;
ALTER SEQUENCE seq_t_contribuzione                OWNER TO rebus;
ALTER SEQUENCE seq_t_costo_fornitura              OWNER TO rebus;
ALTER SEQUENCE seq_t_dato_bonifico                OWNER TO rebus;
ALTER SEQUENCE seq_t_dato_fattura                 OWNER TO rebus;
ALTER SEQUENCE seq_t_dato_messa_servizio          OWNER TO rebus;
ALTER SEQUENCE seq_t_dato_spesa                   OWNER TO rebus;
ALTER SEQUENCE seq_t_documento                    OWNER TO rebus;
ALTER SEQUENCE seq_t_iter_procedimento            OWNER TO rebus;
ALTER SEQUENCE seq_t_ordine_acquisto              OWNER TO rebus;
ALTER SEQUENCE seq_t_procedimento                 OWNER TO rebus;


GRANT ALL ON SEQUENCE seq_r_doc_contribuzione TO rebus;
GRANT ALL ON SEQUENCE seq_r_doc_contribuzione TO rebus_rw;
GRANT ALL ON SEQUENCE seq_r_doc_contribuzione TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_r_doc_contribuzione TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_r_proc_contratto TO rebus;
GRANT ALL ON SEQUENCE seq_r_proc_contratto TO rebus_rw;
GRANT ALL ON SEQUENCE seq_r_proc_contratto TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_r_proc_contratto TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_r_voce_costo_veicolo TO rebus;
GRANT ALL ON SEQUENCE seq_r_voce_costo_veicolo TO rebus_rw;
GRANT ALL ON SEQUENCE seq_r_voce_costo_veicolo TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_r_voce_costo_veicolo TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_rebus_t_aziende TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_aziende TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_aziende TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_aziende TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_bacini TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_bacini TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_bacini TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_bacini TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_bandi TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_bandi TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_bandi TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_bandi TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_consorzi TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_consorzi TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_consorzi TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_consorzi TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_depositi TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_depositi TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_depositi TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_depositi TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_gestori_trasporti TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_gestori_trasporti TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_gestori_trasporti TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_gestori_trasporti TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_messaggi TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_messaggi TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_messaggi TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_messaggi TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_storia_variaz_autobus TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_storia_variaz_autobus TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_storia_variaz_autobus TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_storia_variaz_autobus TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_utenti TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_utenti TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_utenti TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_utenti TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_autobus TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_autobus TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_autobus TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_autobus TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_aziende TO rebus;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_aziende TO rebus_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_aziende TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_rebus_t_variaz_aziende TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_t_assegnazione_risorse TO rebus;
GRANT ALL ON SEQUENCE seq_t_assegnazione_risorse TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_assegnazione_risorse TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_assegnazione_risorse TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_contribuzione TO rebus;
GRANT ALL ON SEQUENCE seq_t_contribuzione TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_contribuzione TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_contribuzione TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_costo_fornitura TO rebus;
GRANT ALL ON SEQUENCE seq_t_costo_fornitura TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_costo_fornitura TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_costo_fornitura TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_dato_bonifico TO rebus;
GRANT ALL ON SEQUENCE seq_t_dato_bonifico TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_dato_bonifico TO sirtpl_trasv_rw;
GRANT ALL ON SEQUENCE seq_t_dato_bonifico TO sirtpl_trasv;
----
GRANT ALL ON SEQUENCE seq_t_dato_fattura TO rebus;
GRANT ALL ON SEQUENCE seq_t_dato_fattura TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_dato_fattura TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_dato_fattura TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_dato_messa_servizio TO rebus;
GRANT ALL ON SEQUENCE seq_t_dato_messa_servizio TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_dato_messa_servizio TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_dato_messa_servizio TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_dato_spesa TO rebus;
GRANT ALL ON SEQUENCE seq_t_dato_spesa TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_dato_spesa TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_dato_spesa TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_documento TO rebus;
GRANT ALL ON SEQUENCE seq_t_documento TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_documento TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_documento TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_iter_procedimento TO rebus;
GRANT ALL ON SEQUENCE seq_t_iter_procedimento TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_iter_procedimento TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_iter_procedimento TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_ordine_acquisto TO rebus;
GRANT ALL ON SEQUENCE seq_t_ordine_acquisto TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_ordine_acquisto TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_ordine_acquisto TO sirtpl_trasv_rw;
----
GRANT ALL ON SEQUENCE seq_t_procedimento TO rebus;
GRANT ALL ON SEQUENCE seq_t_procedimento TO rebus_rw;
GRANT ALL ON SEQUENCE seq_t_procedimento TO sirtpl_trasv;
GRANT ALL ON SEQUENCE seq_t_procedimento TO sirtpl_trasv_rw;
