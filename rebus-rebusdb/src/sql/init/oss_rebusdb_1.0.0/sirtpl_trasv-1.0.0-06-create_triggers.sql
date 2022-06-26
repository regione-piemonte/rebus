CREATE TRIGGER trg_d_contesto_bd
  BEFORE DELETE ON sirtpl_d_contesto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_d_contesto_bd();


CREATE TRIGGER trg_t_utente_bd
  BEFORE DELETE ON sirtpl_t_utente
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_utente_bd();


CREATE TRIGGER trg_r_utente_sog_giurid_bi
  BEFORE INSERT ON sirtpl_r_utente_sog_giurid
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_utente_sog_giurid_bi();
CREATE TRIGGER trg_r_utente_sog_giurid_bu
  BEFORE UPDATE ON sirtpl_r_utente_sog_giurid
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_utente_sog_giurid_bu();
