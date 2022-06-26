CREATE SEQUENCE seq_r_contratto_raggrupp    INCREMENT BY 1 MINVALUE 1 MAXVALUE  999999 START 1;
CREATE SEQUENCE seq_r_contratto_vincolo     INCREMENT BY 1 MINVALUE 1 MAXVALUE  999999 START 1;
CREATE SEQUENCE seq_r_sost_sog_contr        INCREMENT BY 1 MINVALUE 1 MAXVALUE  999999 START 1;
CREATE SEQUENCE seq_r_sost_sog_contr_raggr  INCREMENT BY 1 MINVALUE 1 MAXVALUE 9999999 START 1;
CREATE SEQUENCE seq_t_contratto             INCREMENT BY 1 MINVALUE 1 MAXVALUE   99999 START 1;
CREATE SEQUENCE seq_t_contratto_allegato    INCREMENT BY 1 MINVALUE 1 MAXVALUE   99999 START 1;
CREATE SEQUENCE seq_t_percorrenza           INCREMENT BY 1 MINVALUE 1 MAXVALUE  999999 START 1;
CREATE SEQUENCE seq_t_proroga_contratto     INCREMENT BY 1 MINVALUE 1 MAXVALUE  999999 START 1;



ALTER SEQUENCE seq_r_contratto_raggrupp   OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_r_contratto_vincolo    OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_r_sost_sog_contr       OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_r_sost_sog_contr_raggr OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_t_contratto            OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_t_contratto_allegato   OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_t_percorrenza          OWNER TO sirtpl_contratti;
ALTER SEQUENCE seq_t_proroga_contratto    OWNER TO sirtpl_contratti;



GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_raggrupp TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_raggrupp TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_r_contratto_raggrupp TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_raggrupp TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_vincolo TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_vincolo TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_r_contratto_vincolo TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_r_contratto_vincolo TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_r_sost_sog_contr TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr_raggr TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr_raggr TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_r_sost_sog_contr_raggr TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_r_sost_sog_contr_raggr TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_t_contratto TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto_allegato TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto_allegato TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_t_contratto_allegato TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_t_contratto_allegato TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_t_percorrenza TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_t_percorrenza TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_t_percorrenza TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_t_percorrenza TO sirtpl_trasv_rw;
----
GRANT USAGE, SELECT ON SEQUENCE seq_t_proroga_contratto TO rebus;
GRANT USAGE, SELECT ON SEQUENCE seq_t_proroga_contratto TO sirtpl_trasv;
GRANT ALL           ON SEQUENCE seq_t_proroga_contratto TO sirtpl_contratti_rw;
GRANT USAGE, SELECT ON SEQUENCE seq_t_proroga_contratto TO sirtpl_trasv_rw;
