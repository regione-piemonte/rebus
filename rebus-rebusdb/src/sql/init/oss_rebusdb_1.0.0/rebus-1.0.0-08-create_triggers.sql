CREATE TRIGGER trg_d_tipo_documento_bi
  BEFORE INSERT ON rebus_d_tipo_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_d_tipo_documento_bi();
CREATE TRIGGER trg_d_tipo_documento_bu
  BEFORE UPDATE ON rebus_d_tipo_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_d_tipo_documento_bu();

CREATE TRIGGER trg_d_tipo_messaggio_bi
  BEFORE INSERT ON rebus_d_tipo_messaggio
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_d_tipo_messaggio_bi();
CREATE TRIGGER trg_d_tipo_messaggio_bu
  BEFORE UPDATE ON rebus_d_tipo_messaggio
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_d_tipo_messaggio_bu();

CREATE TRIGGER fnc_trg_dato_spesa_bi
  BEFORE INSERT ON rebusc_t_dato_spesa
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_dato_spesa_bi();
CREATE TRIGGER trg_dato_spesa_bu
  BEFORE UPDATE ON rebusc_t_dato_spesa
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_dato_spesa_bu();

CREATE TRIGGER fnc_trg_t_ordine_acquisto_bi
  BEFORE INSERT ON rebusc_t_ordine_acquisto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_ordine_acquisto_bi();
CREATE TRIGGER trg_t_ordine_acquisto_bu
  BEFORE UPDATE ON rebusc_t_ordine_acquisto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_ordine_acquisto_bu();

CREATE TRIGGER trg_t_documento_bi
  BEFORE INSERT ON rebusp_t_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_documento_bi();
CREATE TRIGGER trg_t_documento_bu
  BEFORE UPDATE ON rebusp_t_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_documento_bu();

CREATE TRIGGER trg_r_doc_variaz_autobus_bi
  BEFORE INSERT ON rebus_r_doc_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_doc_variaz_autobus_bi();
CREATE TRIGGER trg_r_doc_variaz_autobus_bu
  BEFORE UPDATE ON rebus_r_doc_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_doc_variaz_autobus_bu();

CREATE TRIGGER trg_t_storia_variaz_autobus_bi
  BEFORE INSERT ON rebus_t_storia_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_storia_variaz_autobus_bi();
CREATE TRIGGER trg_t_storia_variaz_autobus_bu
  BEFORE UPDATE ON rebus_t_storia_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_storia_variaz_autobus_bu();

CREATE TRIGGER trg_t_variaz_autobus_bi
  BEFORE INSERT ON rebus_t_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_variaz_autobus_bi();
CREATE TRIGGER trg_t_variaz_autobus_bu
  BEFORE UPDATE ON rebus_t_variaz_autobus
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_variaz_autobus_bu();

CREATE TRIGGER fnc_trg_r_doc_contribuzione_bi
  BEFORE INSERT ON rebusc_r_doc_contribuzione
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_doc_contribuzione_bi();
CREATE TRIGGER trg_r_doc_contribuzione_bu
  BEFORE UPDATE ON rebusc_r_doc_contribuzione
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_doc_contribuzione_bu();

CREATE TRIGGER fnc_trg_t_costo_fornitura_bi
  BEFORE INSERT ON rebusc_t_costo_fornitura
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_costo_fornitura_bi();
CREATE TRIGGER trg_t_costo_fornitura_bu
  BEFORE UPDATE ON rebusc_t_costo_fornitura
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_costo_fornitura_bu();

CREATE TRIGGER fnc_trg_t_dato_messa_servizio_bi
  BEFORE INSERT ON rebusc_t_dato_messa_servizio
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_dato_messa_servizio_bi();
CREATE TRIGGER trg_t_dato_messa_servizio_bu
  BEFORE UPDATE ON rebusc_t_dato_messa_servizio
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_dato_messa_servizio_bu();

CREATE TRIGGER trg_t_procedimento_bi
  BEFORE INSERT ON rebusp_t_procedimento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_procedimento_bi();
CREATE TRIGGER trg_t_procedimento_bu
  BEFORE UPDATE ON rebusp_t_procedimento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_procedimento_bu();

CREATE TRIGGER trg_t_messaggi_bi
  BEFORE INSERT ON rebus_t_messaggi
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_messaggi_bi();
CREATE TRIGGER trg_t_messaggi_bu
  BEFORE UPDATE ON rebus_t_messaggi
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_messaggi_bu();

CREATE TRIGGER fnc_trg_t_assegnazione_risorse_bi
  BEFORE INSERT ON rebusc_t_assegnazione_risorse
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_assegnazione_risorse_bi();
CREATE TRIGGER trg_t_assegnazione_risorse_bu
  BEFORE UPDATE ON rebusc_t_assegnazione_risorse
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_assegnazione_risorse_bu();

CREATE TRIGGER fnc_trg_t_contribuzione_bi
  BEFORE INSERT ON rebusc_t_contribuzione
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contribuzione_bi();
CREATE TRIGGER trg_t_contribuzione_bu
  BEFORE UPDATE ON rebusc_t_contribuzione
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contribuzione_bu();

CREATE TRIGGER trg_r_proc_contratto_bi
  BEFORE INSERT ON rebusp_r_proc_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_contratto_bi();
CREATE TRIGGER trg_r_proc_contratto_bu
  BEFORE UPDATE ON rebusp_r_proc_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_contratto_bu();

CREATE TRIGGER trg_r_proc_documento_bi
  BEFORE INSERT ON rebusp_r_proc_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_documento_bi();
CREATE TRIGGER trg_r_proc_documento_bu
  BEFORE UPDATE ON rebusp_r_proc_documento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_documento_bu();

CREATE TRIGGER trg_r_proc_veicolo_bi
  BEFORE INSERT ON rebusp_r_proc_veicolo
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_veicolo_bi();
CREATE TRIGGER trg_r_proc_veicolo_bu
  BEFORE UPDATE ON rebusp_r_proc_veicolo
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_proc_veicolo_bu();

CREATE TRIGGER trg_t_iter_procedimento_bi
  BEFORE INSERT ON rebusp_t_iter_procedimento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_iter_procedimento_bi();
CREATE TRIGGER trg_t_iter_procedimento_bu
  BEFORE UPDATE ON rebusp_t_iter_procedimento
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_iter_procedimento_bu();
