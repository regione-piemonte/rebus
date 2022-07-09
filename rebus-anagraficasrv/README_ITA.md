# Prodotto
REBUS componente ANAGRAFICASRV

# Descrizione della componente 

Questa componente è una web application che segue il paradigma "Single Page Application (SPA)", espone servizi REST alla componente ANAGRAFICAWCL (Angular7) e si connette al DB (REBUSDB) per le operazioni CRUD.
Si collega al servizio di profilazione utenti trasversale (IRIDE).
Questa componente si connette al DB (DBMS POSTGRESQL) utilizzando il DataSource definito a livello del suo "container" (JBoss).

# Configurazioni iniziali
Il prodotto è stato sviluppato con il JDK 1.8.0_201 .
Da un punto di vista generale, nella fase iniziale occorre adattare i file di properties nella directory buildfiles alla propria configurazione, per poter poi effettuare la "build" della componente.
Una delle cose principali da configurare è il datasource con i riferimenti del DB che si intende utilizzare (JNDI name). Questa configurazione deve essere coerente a livello di "container" e di codice.\
Per quanto riguarda le configurazioni, le principali sono definite in:
- anagraficasrv/src/java/it/csi/rebus/anagraficasrv/integration/generator/generatorConfig.xml
- anagraficasrv/src/java/it/csi/rebus/anagraficasrv/common/YserMock.java
- anagraficasrv/conf/ear/application.xml
- anagraficasrv/conf/tar/anagraficasrv-ds.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/applicationContext.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/dao-beans.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/daoContext.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/jboss-web.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/mybatis-config.xml
- anagraficasrv/conf/web/rebus/rest/WEB-INF/web.xml

# Getting Started

Una volta prelevata e portata in locale dal repository la componente ("git clone"), occorre procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy e quindi procedere al build.

Per ogni nuova integrazione, ci deve essere una corrispondenza dei moduli nelle directory `conf/ear` e `conf/tar`.

Questa la struttura di riferimento per i vari moduli nella directory 'conf':
- `/web` contiene le configurazioni dei servizi REST ("entrypoints" della componente), degli oggetti DAO e del framework di mapping "object-relational".
- `/ear` contiene le indicazioni di "packaging" per costruire gli EAR.
- `/tar` contiene le indicazioni di "packaging" per la predisposizone dei pacchetti in formato "TAR archive", per i sistemi di "automated distribution".

La struttura dei folder del prodotto è la seguente:
- manager: gestione Excel
- service - service/Impl: business logic
- excel: contiene le classi specifiche della generazione degli Excel
- integration: gestione dei DAO e DTO. Contiene il plugin generator di MyBatis. Contiene le classi di mapper DTO-VO. 
- security: gestione ruoli e autorizzazioni utenti
- util: classi di utility
- vo: classi VO 
- web: contiene i moduli relativi ai servizi REST  che costituiscono gli "entrypoints" dell'applicazione.


# Prerequisiti di sistema
Il primo passo è la predisposizione dei DB Schema utilizzati da questa componente, secondo quanto definito in REBUSDB, ed inserire i dati iniziali necessari.
A livello applicativo, per la persistenza dei dati viene utilizzato il framework MyBatis, e di conseguenza viene adottato anche in questa componente.

Nella directory "csi-lib" sono disponibili le librerie sviluppate da CSI e rese disponibili con le licenze indicate nel BOM.csv .

Occorre inoltre prevedere le opportune sostituzioni dei servizi esterni richiamati (IRIDE).

Per il "build" è preimpostato Apache ANT 1.8.\
Per la compilazione:
- creare il pacchetto con Ant facendo attenzione a specificare il target opportuno;\
- lanciare il comando ant -Dtarget <env> per generare l'ear dell'ambiente di sviluppo necessario.\

I file di `properties` corrispondenti ai vari ambienti devono essere aggiunti nella directory `/buildfiles`.


# Installazione - Deployment
Installare il file "ear" generato con il build sul proprio ambiente JBoss.

# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Copyrights

© Copyright Regione Piemonte – 2022\
Questo stesso elenco dei titolari del software è anche riportato in Copyrights.txt .

# License
Il prodotto software è sottoposto alla licenza EUPL-1.2 o versioni successive.\
SPDX-License-Identifier: EUPL-1.2-or-later.

