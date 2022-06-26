CREATE SCHEMA rebus             AUTHORIZATION rebus;
CREATE SCHEMA sirtpl_aziende    AUTHORIZATION sirtpl_aziende;
CREATE SCHEMA sirtpl_contratti  AUTHORIZATION sirtpl_contratti;
CREATE SCHEMA sirtpl_trasv      AUTHORIZATION sirtpl_trasv;


GRANT USAGE ON SCHEMA rebus TO rebus_rw;
GRANT USAGE ON SCHEMA rebus TO sirtpl_trasv_rw;
GRANT USAGE ON SCHEMA rebus TO sirtpl_trasv_ro;
GRANT USAGE ON SCHEMA rebus TO sirtpl_trasv;
GRANT USAGE ON SCHEMA rebus TO sirtpl_contratti;
----
GRANT USAGE ON SCHEMA sirtpl_aziende TO sirtpl_aziende_rw;
GRANT USAGE ON SCHEMA sirtpl_aziende TO sirtpl_trasv_rw;
GRANT USAGE ON SCHEMA sirtpl_aziende TO rebus;
GRANT USAGE ON SCHEMA sirtpl_aziende TO sirtpl_trasv;
GRANT USAGE ON SCHEMA sirtpl_aziende TO sirtpl_trasv_ro;
GRANT USAGE ON SCHEMA sirtpl_aziende TO sirtpl_contratti;
----
GRANT USAGE ON SCHEMA sirtpl_contratti TO sirtpl_contratti_rw;
GRANT USAGE ON SCHEMA sirtpl_contratti TO sirtpl_trasv_rw;
GRANT USAGE ON SCHEMA sirtpl_contratti TO sirtpl_trasv;
GRANT USAGE ON SCHEMA sirtpl_contratti TO sirtpl_trasv_ro;
GRANT USAGE ON SCHEMA sirtpl_contratti TO sirtpl_aziende;
GRANT USAGE ON SCHEMA sirtpl_contratti TO rebus;
----
GRANT USAGE ON SCHEMA sirtpl_trasv TO sirtpl_trasv_rw;
GRANT USAGE ON SCHEMA sirtpl_trasv TO rebus;
GRANT USAGE ON SCHEMA sirtpl_trasv TO sirtpl_trasv_ro;
GRANT USAGE ON SCHEMA sirtpl_trasv TO sirtpl_contratti;
GRANT USAGE ON SCHEMA sirtpl_trasv TO sirtpl_aziende;
GRANT USAGE ON SCHEMA sirtpl_trasv TO sirtpl_aziende_rw;
