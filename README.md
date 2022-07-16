# Prodotto

REBUS : Parco BUS TPL - Settore Mobilità e Trasporti Regione Piemonte

# Descrizione del prodotto

Il prodotto REBUS è costituito da due moduli applicativi (web application) per la gestione unificata e l’aggiornamento delle informazioni relative:
 
-	ai veicoli adibiti al trasporto pubblico locale (TPL) - Parco autobus TPL Piemonte;

-	ai contratti di servizio del TPL e ai soggetti coinvolti negli stessi contratti - Contratti e soggetti giuridici TPL.

Il prodotto prevede che l’accesso sia consentito solo agli utenti abilitati, i quali, a seguito della autenticazione su una componente, possono passare all’altra senza doversi autenticare nuovamente. L’accesso alle funzionalità dipende dal profilo associato al singolo utente abilitato.

Il sistema è utilizzato da personale dalle aziende di trasporto pubblico (TPL) che operano in Piemonte, dell’Agenzia per la Mobilità Piemontese (AMP) e della Regione Piemonte.

Parco autobus TPL Piemonte:

Tale modulo consente il censimento delle informazioni sugli autobus adibiti al trasporto pubblico locale.

L’attività di aggiornamento dei dati della propria flotta è di esclusiva competenza e responsabilità dell’azienda proprietaria, anche ai fini di specifici adempimenti previsti in Regione Piemonte (Debito Informativo Trasporti -DIT, ai sensi della Determinazione Dirigenziale n. 4292 del 18.12.2017).

L’azienda, tramite il sistema, può inserire nuove schede veicolo, ricercarle, consultarle e modificarle. In particolare, l’inserimento di nuovi veicoli in anagrafica può avvenire tramite interfaccia (un veicolo alla volta), oppure tramite il caricamento di un file Excel (consigliato nel caso si vogliano inserire più veicoli contemporaneamente).

La ricerca e la consultazione sono facilitate dalla presenza di numerosi filtri e dalla possibilità di esportare in file MS Excel i risultati della ricerca.

L’azienda può gestire e consultare solo i dati relativi alla propria flotta, AMP e Regione Piemonte possono ricercare e consultare i dati di tutti i veicoli inseriti in anagrafica dalle aziende. 

Il sistema include funzionalità a supporto del rilascio dei nulla osta da parte dell’Ente committente (al momento solo AMP), necessari alle aziende per procedere con le richieste di prima immatricolazione, re-immatricolazione, alienazione e sostituzione presso la Motorizzazione civile.

Possibili future evoluzioni del sistema riguardano la gestione delle richieste di autorizzazione all’uso in linea e fuori linea.


Contratti e soggetti giuridici TPL:
 
Il modulo consente il censimento e l'aggiornamento continuo e unificato delle anagrafiche dei contratti inerenti ai servizi di TPL e dei relativi soggetti giuridici coinvolti (aziende ed enti pubblici), garantendo la corrispondenza con i dati dell’Osservatorio nazionale sulle politiche del TPL tramite corrispondenza univoca dei codici identificativi.

I dati relativi ai contratti sono gestiti da Regione Piemonte e AMP, e consultabili da tutte le aziende. Regione Piemonte e AMP gestiscono anche l’inserimento in anagrafica dei soggetti di alcune informazioni identificative. L’aggiornamento di altre informazioni è invece a cura del singolo soggetto (rappresentante legale, indirizzo sede legale, etc.).

Tali anagrafiche possono essere utilizzate dagli altri sistemi del Sistema Informativo Regionale Trasporti che include i sistemi relativi al parco autobus, alla dotazione organica delle aziende, alla programmazione dei servizi del TPL. Nella versione attuale non sono disponibili servizi di esposizione per tali dati.


Struttura del prodotto:

Il prodotto è strutturato nelle seguenti componenti specifiche:
- [rebusdb]( https://github.com/regione-piemonte/rebus/tree/main/rebus-rebusdb ) : 				script DDL/DML per la creazione ed il popolamento iniziale del DB (Postgresql);
- [rebuscruswcl]( https://github.com/regione-piemonte/rebus/tree/main/rebus-rebuscruswcl ) : 	Client Web (Angular5), front-end applicativo del modulo ParcoBus;
- [rebuscrus]( https://github.com/regione-piemonte/rebus/tree/main/rebus-rebuscrus ) : 			Componente SPA con servizi REST per rebuscruswcl;
- [anagraficawcl]( https://github.com/regione-piemonte/rebus/tree/main/rebus-anagraficawcl ) :	Client Web (Angular7), front-end applicativo del modulo Contratti e Soggetti Giuridici TPL;
- [anagraficasrv]( https://github.com/regione-piemonte/rebus/tree/main/rebus-anagraficasrv ) : 	Componente SPA con servizi REST per anagraficawcl.


A ciascuna componente del prodotto elencata sopra corisponde una sotto-directory denominata REBUS-<nome_componente>.

In ciascuna di queste cartelle di componente si trovano ulteriori informazioni specifiche, incluso il BOM della componente di prodotto.

Nella directory [csi-lib]( https://github.com/regione-piemonte/REBUS/tree/main/csi-lib ) si trovano le librerie sviluppate da CSI-Piemonte con licenza OSS, come indicato nei BOM delle singole componenti, ed usate trasversalmente nel prodotto.
	

# Prerequisiti di sistema

Una istanza DBMS Postgresql (v. 9.6 o superiore) con utenza avente privilegi per la creazione DB ed altri oggetti correlati (tramite le istruzioni DDL messe a disposizione nella componente rebusdb).

Una istanza di application server J2EE, consigliato JBoss 6.4 (oppure WildFly 17 - https://www.wildfly.org/downloads/ ).\
Una istanza di web server, consigliato apache web server ( https://httpd.apache.org/ ).\
Per il build è previsto l'uso di Apache ANT & IVY ( https://ant.apache.org/ ).\
Per la compilazione/build delle componenti Java sono rese disponibili nella directory "csi-lib" una serie di librerie predisposte da CSI Piemonte per un uso trasversale nei prodotti realizzati, o per uso specifico in altri prodotti con cui REBUS si interfaccia. Indicazioni più specifiche sono disponibili nella documentazione di ciascuna componente.

Infine, per quanto concerne l'autenticazione e la profilazione degli utenti del sistema, REBUS è integrato con servizi trasversali del sistema informativo regionale ("Shibboleth", "IRIDE"), di conseguenza per un utilizzo in un altro contesto occorre avere a disposizione servizi analoghi o integrare moduli opportuni che svolgano analoghe funzionalità.
 

# Installazione

Creare il DB ed i euoli e gli schema necessari, tramite gli script della componente REBUSDB. Effettuare tutte le operazioni indicate nella stessa directory.
 
Configurare il datasource nell'application server, utilizzato in rebuscrus ed anagraficasrv.

# Deployment

Dopo aver seguito le indicazioni del paragrafo relativo all'installazione, si può procedere al build dei pacchetti ed al deploy su application server (JBoss).


# Versioning
Per la gestione del codice sorgente viene utilizzato Git, ma non vi sono vincoli per l'utilizzo di altri strumenti analoghi.\
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).


# Copyrights
© Copyright Regione Piemonte – 2022


# License

SPDX-License-Identifier: EUPL-1.2-or-later .\
Questo software è distribuito con licenza EUPL-1.2 .\
Consultare il file [EUPL v1_2 IT-LICENSE.txt](https://github.com/regione-piemonte/rebus/blob/main/EUPL%20v1_2%20IT-LICENSE.txt) per i dettagli sulla licenza.
