CREATE TRIGGER trg_t_contratto_bi
  BEFORE INSERT ON sirtplc_t_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contratto_bi();
CREATE TRIGGER trg_t_contratto_bu
  BEFORE UPDATE ON sirtplc_t_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contratto_bu();
CREATE TRIGGER trg_t_contratto_bd
  BEFORE DELETE ON sirtplc_t_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contratto_bd();

CREATE TRIGGER trg_t_contratto_allegato_bi
  BEFORE INSERT ON sirtplc_t_contratto_allegato
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contratto_allegato_bi();
CREATE TRIGGER trg_t_contratto_allegato_bu
  BEFORE UPDATE ON sirtplc_t_contratto_allegato
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_contratto_allegato_bu();

CREATE TRIGGER trg_t_proroga_contratto_bi
  BEFORE INSERT ON sirtplc_t_proroga_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_proroga_contratto_bi();
CREATE TRIGGER trg_t_proroga_contratto_bu
  BEFORE UPDATE ON sirtplc_t_proroga_contratto
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_proroga_contratto_bu();

CREATE TRIGGER trg_r_contratto_raggrupp_bi
  BEFORE INSERT ON sirtplc_r_contratto_raggrupp
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_contratto_raggrupp_bi();
CREATE TRIGGER trg_r_contratto_raggrupp_bu
  BEFORE UPDATE ON sirtplc_r_contratto_raggrupp
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_contratto_raggrupp_bu();

CREATE TRIGGER trg_r_sost_sog_contr_bi
  BEFORE INSERT ON sirtplc_r_sost_sog_contr
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sost_sog_contr_bi();
CREATE TRIGGER trg_r_sost_sog_contr_bu
  BEFORE UPDATE ON sirtplc_r_sost_sog_contr
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sost_sog_contr_bu();

CREATE TRIGGER trg_r_sost_sog_contr_raggr_bi
  BEFORE INSERT ON sirtplc_r_sost_sog_contr_raggr
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sost_sog_contr_raggr_bi();
CREATE TRIGGER trg_r_sost_sog_contr_raggr_bu
  BEFORE UPDATE ON sirtplc_r_sost_sog_contr_raggr
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sost_sog_contr_raggr_bu();
