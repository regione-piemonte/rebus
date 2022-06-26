CREATE OR REPLACE VIEW v_comuni_all AS
SELECT  com.id_comune
      , com.cod_istat_comune
      , com.cod_belfiore_comune
      , com.denom_comune
      , com.data_inizio_validita    AS  data_inizio_validita_comune
      , com.data_fine_validita      AS  data_fine_validita_comune
      , prov.id_provincia
      , prov.cod_istat_provincia
      , prov.denom_provincia
      , prov.sigla_provincia
      , prov.data_inizio_validita   AS  data_inizio_validita_provincia
      , prov.data_fine_validita     AS  data_fine_validita_provincia
  FROM    sirtpl_d_comune     com
    JOIN  sirtpl_d_provincia  prov   ON  com.id_provincia = prov.id_provincia
UNION
SELECT  com.id_comune
      , com.cod_istat_comune
      , com.cod_belfiore_comune
      , com.denom_comune
      , com.data_inizio_validita    AS  data_inizio_validita_comune
      , com.data_fine_validita      AS  data_fine_validita_comune
      , prov.id_provincia
      , prov.cod_istat_provincia
      , prov.denom_provincia
      , prov.sigla_provincia
      , prov.data_inizio_validita   AS  data_inizio_validita_provincia
      , prov.data_fine_validita     AS  data_fine_validita_provincia
  FROM    sirtpl_s_comune     com
    JOIN  sirtpl_s_provincia  prov  ON  com.id_provincia = prov.id_provincia
;





ALTER TABLE v_comuni_all  OWNER TO sirtpl_trasv;
