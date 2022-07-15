# Prodotto - Componente

Prodotto REBUS, componente ANAGRAFICAWCL

# Descrizione della componente

Questa componente è la controparte Angular della web application ANAGRAFICASRV, e di fatto costituisce il front-end del prodotto REBUS per il modulo Anagrafica dei Soggetti TPL.

Il progetto è stato generato con [Angular CLI](https://github.com/angular/angular-cli) versione 7.3.9 e con la versione di Angular 7.2.18 .

# Configurazioni iniziali

Per configurare il prorpio ambiente di sviluppo eseguire `npm start`.

La URL di default dell'ambiente di sviluppo è `http://localhost:4200/`.

In tale ambiente, la "web application" effettuerà il reload automatico per qualsiasi modifica dei sorgenti.

Tutte le configurazioni devono essere impostate nel file `buildfiles/<env>.properties.json` :

- buildfiles/<env>.properties.json : contiene i parametri in base all’ambiente selezionato;

- buildfiles/webpack.<env>.js : contiene la configurazione in base all’ambiente selezionato.

Per ogni nuovo "ambiente" che si crea, deve essere aggiunta una corrispondente "entry" nel file `angular-cli.json`, per referenziare il nuovo ambiente.

Queste sono le impostazioni da configurare:
- production = indica se la configurazione ed il file corrispondente è relativa ad un ambiente di produzione o di pre-produzione
- ambiente = il nome del environment
- publicPath = la URL a cui risponde l'applicazione
- backendServer = il prefisso usato per comporre il "BackEnd service URL" (nel caso sia utile tenerlo separato)
- beService = la "Base URL" del servizio di BackEnd
- shibbolethSSOLogoutURL = la URL di logout dal SSO (Single Sign On)
- parcoBusPath = path che permette lo switch tra i moduli Anagrafiche e ParcoBus.

# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy.

Prima di effettuare il build occorre eseguire il comando "npm install".

Per il build usare il comando `npm build`.

Gli "artefatti" risultanti si troveranno in `dist/`.

Per la versione di esercizio usare il flag `--prod`.

il progetto usa gli script standard di "Angular CLI" (non è necessario installare la "CLI" su tutti gli ambienti, in quanto viene referenziata come una "development dependency").

Per semplificare il "lifecycle" della applicazione sono disponibili alcuni "goal":\
- package-* = scripts that execute the Angular CLI build with a predefied configuration;
- extract-i18n = script that extracts the internationalization strings and adds them to JSON files (non utilizzata).

# Prerequisiti di sistema
Questa componente necessita di ANAGRAFICASRV per funzionare.

Inoltre prevede la configurazione di un servizio di autenticazione (Shibboleth).

# Installazione

La componente Angular viene solitamente inclusa nello stesso pacchetto di installazione della componente ANAGRAFICASRV.

# Esecuzione dei test 

Questa compoente è predisposta per "unit test" e test "end-to-end", ma non configurati. Nel caso sarebbe possibile:

- Eseguire `ng test` per effettuare gli "unit test" tramite [Karma](https://karma-runner.github.io).

- Eseguire `ng e2e` per effettuare i test "end-to-end" tramite [Protractor](http://www.protractortest.org/).

Questa componente è stata oggetto di test di vulnerabilità prima del rilascio.

# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Copyrights

© Copyright Regione Piemonte – 2022

Questo stesso elenco dei titolari del software è anche riportato in Copyrights.txt 

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.\
SPDX-License-Identifier: EUPL-1.2-or-later\
Vedere il file [EUPL v1_2 IT-LICENSE.txt](https://github.com/regione-piemonte/rebus/blob/main/EUPL%20v1_2%20IT-LICENSE.txt) per i dettagli.

# Community site
Per avere ulteriori informazioni sull'utilizzo di Angular CLI si può usare il comando `ng help` o consultare il [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
