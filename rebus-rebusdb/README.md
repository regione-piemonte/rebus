# Prodotto - Componente
REBUS - REBUSDB

# Descrizione della componente
REBUSDB è la componente DB del prodotto REBUS.\
Il DBMS di riferimento è Postgresql 9.6 .\
Tramite gli script qui presenti viene creato e popolato lo schema dati usato dalle altre componenti.\
Questa componente include:
- script DDL per la creazione dello schema dati;
- script DML per il popolamento iniziale del DB;
- script per la definizione dei vincoli, delle viste e delle sequence;
- script per la definizione delle function e dei trigger.

# Configurazioni iniziali
Definire un DB "TRASPORTI" su una istanza DBMS Postgresql (versione 9.6 o superiore).


# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), predisporsi per poter eseguire gli script nella sequenza indicata nel seguito.

# Prerequisiti di sistema
DBMS Postgresql versione 9.6 o superiore, aver definito il DB "TRASPORTI" ed essere in possesso dell'utente di amministrazione,\
 ovvero con permessi adeguati all'esecuzione di istruzioni di creazione ruoli e schema.

# Installazione
Presupponendo di dover creare l'ambiente database da zero gli script da eseguire in sequenza sono:

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI AMMINISTRAZIONE:

iniziale-1.0.0-01-drops_schemas_roles.sql   ==> Serve per la riesecuzione, elimina tutto l'ambiente database.\
						Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla.

iniziale-1.0.0-02-create_roles.sql          ==> Crea tutti i ruoli ( rebus, sirtpl_aziende, sirtpl_contratti, sirtpl_trasv,\
								rebus_rw, sirtpl_aziende_rw, sirtpl_contratti_rw, sirtpl_trasv_rw e sirtpl_trasv_ro).

iniziale-1.0.0-03-create_schemas.sql        ==> Crea tutti gli schemi ( rebus, sirtpl_aziende, sirtpl_contratti, sirtpl_trasv).


---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI REBUS:

rebus-1.0.0-01-drops_objects.sql    ==> Serve per la riesecuzione, elimina tutti gli oggetti dello schema rebus\
					Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla.

rebus-1.0.0-02-set_searh_path.sql   ==> Setta opportunamente il parametro di sistema SEARCH_PATH.

rebus-1.0.0-03-create_sequences.sql ==> Crea tutte le sequence dello schema rebus.

rebus-1.0.0-04-create_tables.sql    ==> Crea tutte le tabelle dello schema rebus.

rebus-1.0.0-05-insert_data.sql      ==> Inserisce tutti i dati necessari in alcune tabelle dello schema rebus (es. tabelle di decodifica).

rebus-1.0.0-06-create_indexes.sql   ==> Crea tutti gli indici dello schema rebus.

rebus-1.0.0-07-create_functions.sql ==> Crea tutte le function dello schema rebus.

rebus-1.0.0-08-create_triggers.sql  ==> Crea tutti i triggers dello schema rebus.

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI SIRTPL_CONTRATTI:

sirtpl_contratti-1.0.0-01-drops_objects.sql     ==> Serve per la riesecuzione, elimina tutti gli oggetti dello schema sirtpl_contratti.\
						Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla.

sirtpl_contratti-1.0.0-02-create_sequences.sql  ==> Crea tutte le sequence dello schema sirtpl_contratti.

sirtpl_contratti-1.0.0-03-create_tables.sql     ==> Crea tutte le tabelle dello schema sirtpl_contratti.

sirtpl_contratti-1.0.0-04-insert_data.sql       ==> Inserisce tutti i dati necessari in alcune tabelle dello schema sirtpl_contratti (es. tabelle di decodifica).

sirtpl_contratti-1.0.0-05-create_functions.sql  ==> Crea tutte le function dello schema sirtpl_contratti.

sirtpl_contratti-1.0.0-06-create_triggers.sql   ==> Crea tutti i triggers dello schema sirtpl_contratti.

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI SIRTPL_TRASV:

sirtpl_trasv-1.0.0-01-drops_objects.sql     ==> Serve per la riesecuzione, elimina tutti gli oggetti dello schema sirtpl_trasv.\
						Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla.

sirtpl_trasv-1.0.0-02-set_searh_path.sql    ==> Setta opportunamente il parametro di sistema SEARCH_PATH.

sirtpl_trasv-1.0.0-03-create_tables.sql     ==> Crea tutte le tabelle dello schema sirtpl_trasv.

sirtpl_trasv-1.0.0-04-insert_data.sql       ==> Inserisce tutti i dati necessari in alcune tabelle dello schema sirtpl_trasv (es. tabelle di decodifica).

sirtpl_trasv-1.0.0-05-create_functions.sql  ==> Crea tutte le function dello schema sirtpl_trasv.

sirtpl_trasv-1.0.0-06-create_triggers.sql   ==> Crea tutti i triggers dello schema sirtpl_trasv.

sirtpl_trasv-1.0.0-07-create_views.sql      ==> Crea una parte delle viste dello schema sirtpl_trasv.

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI SIRTPL_AZIENDE:

sirtpl_aziende-1.0.0-01-drops_objects.sql     ==> Serve per la riesecuzione, elimina tutti gli oggetti dello schema sirtpl_aziende.\
						Può essere quindi saltato la prima volta o comunque quando non è ancora stato creato nulla.

sirtpl_aziende-1.0.0-02-set_searh_path.sql    ==> Setta opportunamente il parametro di sistema SEARCH_PATH

sirtpl_aziende-1.0.0-03-create_sequences.sql  ==> Crea tutte le sequence dello schema sirtpl_aziende

sirtpl_aziende-1.0.0-04-create_tables.sql     ==> Crea tutte le tabelle dello schema sirtpl_aziende

sirtpl_aziende-1.0.0-05-insert_data.sql       ==> Inserisce tutti i dati necessari in alcune tabelle dello schema sirtpl_aziende (es. tabelle di decodifica)

sirtpl_aziende-1.0.0-06-create_functions.sql  ==> Crea tutte le function dello schema sirtpl_aziende

sirtpl_aziende-1.0.0-07-create_triggers.sql   ==> Crea tutti i triggers dello schema sirtpl_aziende

sirtpl_aziende-1.0.0-08-create_views.sql      ==> Crea tutte le viste dello schema sirtpl_aziende

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI SIRTPL_TRASV:

sirtpl_trasv-1.0.0-08-create_views.sql  ==> Crea le viste mancanti dello schema sirtpl_trasv

---------------------------------------------------------------------------------------------------------\

CONNETTERSI ALLO USER DI REBUS:

rebus-1.0.0-09-create_views.sql     ==> Crea tutte le viste dello schema rebus


# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
Fare riferimento a quanto riportato nel file AUTHORS.txt.

# Copyrights

© Copyright Regione Piemonte – 2022 \ 

Vedere anche il file Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.\
SPDX-License-Identifier: EUPL-1.2-or-later.\
Vedere il file EUPL v1_2 IT-LICENSE.txt per i dettagli.

