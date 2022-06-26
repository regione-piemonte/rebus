CREATE TRIGGER trg_t_deposito_bi
  BEFORE INSERT ON sirtpla_t_deposito
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_deposito_bi();
CREATE TRIGGER trg_t_deposito_bu
  BEFORE UPDATE ON sirtpla_t_deposito
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_deposito_bu();


CREATE TRIGGER trg_sirtpla_t_soggetto_giuridico_bi
  BEFORE INSERT ON sirtpla_t_soggetto_giuridico
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_bi_sirtpla_t_soggetto_giuridico();
CREATE TRIGGER trg_t_soggetto_giuridico_bi
  BEFORE INSERT ON sirtpla_t_soggetto_giuridico
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_soggetto_giuridico_bi();
CREATE TRIGGER trg_t_soggetto_giuridico_bu
  BEFORE UPDATE ON sirtpla_t_soggetto_giuridico
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_soggetto_giuridico_bu();
CREATE TRIGGER trg_t_soggetto_giuridico_bd
  BEFORE DELETE ON sirtpla_t_soggetto_giuridico
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_soggetto_giuridico_bd();


CREATE TRIGGER trg_r_sog_giurid_deposito_bi
  BEFORE INSERT ON sirtpla_r_sog_giurid_deposito
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sog_giurid_deposito_bi();
CREATE TRIGGER trg_r_sog_giurid_deposito_bu
  BEFORE UPDATE ON sirtpla_r_sog_giurid_deposito
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_sog_giurid_deposito_bu();


CREATE TRIGGER trg_r_unione_sog_giurid_bi
  BEFORE INSERT ON sirtpla_r_unione_sog_giurid
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_unione_sog_giurid_bi();
CREATE TRIGGER trg_r_unione_sog_giurid_bu
  BEFORE UPDATE ON sirtpla_r_unione_sog_giurid
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_r_unione_sog_giurid_bu();


CREATE TRIGGER trg_t_dato_bancario_bi
  BEFORE INSERT ON sirtpla_t_dato_bancario
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_dato_bancario_bi();
CREATE TRIGGER trg_t_dato_bancario_bu
  BEFORE UPDATE ON sirtpla_t_dato_bancario
  FOR EACH ROW EXECUTE PROCEDURE fnc_trg_t_dato_bancario_bu();
