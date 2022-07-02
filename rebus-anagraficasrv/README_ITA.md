# Prodotto
REBUS componente ANAGRAFICASRV

# Descrizione della componente 

Questa componente è una web application che segue il paradigma "Single Page Application (SPA)", espone servizi REST alla componente ANAGRAFICAWCL (Angular7) e si connette al DB (REBUSDB) per le operazioni CRUD.
Si collega al servizio di profilazione utenti trasversale (IRIDE).
Questa componente si connette al DB (DBMS POSTGRESQL) utilizzando il DataSource definito a livello del suo "container" (JBoss).

# Configurazioni iniziali
Da un punto di vista generale, nella fase iniziale occorre adattare i file di properties nella directory buildfiles alla propria configurazione.
Una delle cose principali da configurare è il datasource con i riferimenti del DB che si intende utilizzare (JNDI name).\
Per quanto riguarda le properties da configurare, sono definite in....

# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), occorre procedere con la modifica dei file di configurazione in base al proprio ambiente di deploy e quindi procedere al build.

In generale, ogni integrazione con moduli di terze parti deve corrispondere ad un modulo separato in `/integ-<name>`.

Per ogni nuova integrazione, ci deve essere una corrispondenza dei moduli nelle directory `/ear` e `/tar`.

Questa la struttura di riferimento per i vari moduli:

- `/lib` contiene le librerie, usate trasversalmente.
- `/web` contiene i moduli relativi ai servizi REST  che costituiscono gli "entrypoints" dellae applicazione.
- `/ejb` contiene la business logic.
- `/integ-<name>` contiene le logiche per integrare "third-party" software.
- `/ear` contiene le indicazioni di "packaging" per costruire gli EAR.
- `/tar` contiene le indicazioni di "packaging" per la predisposizone dei pacchetti in formato "TAR archive", per i sistemi di "automated distribution".

# Prerequisiti di sistema
Il primo passo è la predisposizione dei DB Schema utilizzati da questa componente, secondo quanto definito in REBUSDB, ed inserire i dati iniziali necessari.
Nella directory "csi-lib" sono disponibili le librerie sviluppate da CSI e rese disponibili con le licenze indicate nel BOM.csv .

Occorre inoltre prevedere le opportune sostituzioni dei servizi esterni richiamati (IRIDE).

Per il "build" è preimpostato Apache ANT...\
Per la compilazione: "...".\
I file di `properties` corrispondenti ai vari ambienti devono essere aggiunti nella directory `/profiles`.


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

