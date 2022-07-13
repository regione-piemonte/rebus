/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { MatPaginator } from "@angular/material";
import { Router } from "@angular/router";
import { AppComponent } from "../../app.component";
import { AuthorizationRoles } from "../../class/authorization-roles";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import * as config from "../../globalparameter";
import { HeaderMessaggi, MessaggiData, MessaggiDatasource } from "../../globalparameter";
import { MessaggiService } from "../../services/messaggi.service";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";
import { ProcedimentoService } from "../../services/procedimento.service";
import { UserService } from "../../services/user.service";
import { UtilityService } from "../../services/utility.service";
import { ContestoVO } from "../../vo/contesto-vo";
import { UserInfo } from "../../vo/funzionario-vo";

@Component({
    selector: 'app-elenco-messaggi',
    templateUrl: './elenco-messaggi.component.html',
    styleUrls: ['./elenco-messaggi.component.scss']
})


export class ElencoMessaggiComponent implements OnInit, OnDestroy {
    staticColumnsMessaggi: Array<String> = ['tipoMessaggio', 'messaggio', 'azioni'];

    context: number;
    funzionario: UserInfo;
    selected: FormControl;

    isUtenteAzienda: boolean;
    isModificaAbilitata: boolean;
    isCancellaAbilitato: boolean;
    isConsultaAbilitato: boolean;
    isAutorizzatoMessaggi: boolean;
    isAutorizzatoMessaggiRendicontazione: boolean;
    isAutorizzatoMessaggiRichieste: boolean;
    loadComplete: boolean = true;
    loadHeaderComplete: boolean = true;
    idContesto: number;
    elementSplice: any;

    contestoAutobus: ContestoVO;
    contestoProcedimenti: ContestoVO;
    contestoContribuzione: ContestoVO;

    ELEMENT_DATA: MessaggiDatasource[] = [];
    dataSource: MessaggiData;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    displayedColumns: String[];

    headers: HeaderMessaggi[] = [];

    tipiMessaggi: Number[] = [];

    isMessaggioFumetto: boolean = false;

    isExtendedRow = (index, item) => item.dataRow;
    //NavbarFilterContext chiamata per ottenere il contesto di home(info filtro che indica la tab contente
    // l'ultimo messaggio ricevuto e quindi la tab da mostrare quando si accede da fumetto) e tutti i contesti
    constructor(private userService: UserService, private navbarFilterContext: NavbarFilterContext, private messaggiService: MessaggiService,
        private procedimentoService: ProcedimentoService, private utilityService: UtilityService, private router: Router, private appComponent: AppComponent,) {
    }
    ngOnDestroy(): void {
        this.messaggiService.idContestoNavbar = undefined;
    }

    async ngOnInit() {
        // mi riprendo l'url e faccio lo split per / così da ottenere come ultimo elemento dell'array 0 o 1
        // 0 corrisponde ai messaggi da fumetto quindi setto isMessaggioFumetto a true
        // 1 corrisponde a messaggi da menu quindi setto isMessaggioFumetto a false
        let routeParam = this.router.url.split('/');
        // isMessaggioFumetto lo utilizzo per differenziare le chiamate da menu e da fumetto
        this.isMessaggioFumetto = +routeParam[routeParam.length - 1] == 0 ? true : false;
        this.selected = new FormControl();
        // se i dati all'interno di navbarFilterContext faccio le chiamate per ottenere i contesti e il contesto dell'ultimo messaggio
        if (this.navbarFilterContext.isEmptyFilterNavbarContex()) {
            await this.getContesti();
            await this.getContestoHome();
            //chiamata per info filtro e elenco contesti
        }
        this.loadFunzionario();
        this.contestoAutobus = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
        this.contestoProcedimenti = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
        this.contestoContribuzione = this.navbarFilterContext.ElencoContesti.find(c => c.id === 5);

        // Setto gli header da visualizzare in base all'utente
        if (this.isAutorizzatoMessaggi) {
            this.headers.push(new HeaderMessaggi(this.contestoAutobus.descrizione, this.contestoAutobus.id, false));
        }
        if (this.isAutorizzatoMessaggiRichieste) {
            this.headers.push(new HeaderMessaggi(this.contestoProcedimenti.descrizione, this.contestoProcedimenti.id, false));
        }
        if (this.isAutorizzatoMessaggiRendicontazione) {
            this.headers.push(new HeaderMessaggi(this.contestoContribuzione.descrizione, this.contestoContribuzione.id, false));
        }

        //Se infoFiltro ha i parametri null setto di default parcobus id=1
        if (config.isNullOrVoid(this.navbarFilterContext.InfoFiltro.id)) {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === this.headers[0].idContestoMess);
        }
        // Setto l'idContesto
        if (this.isMessaggioFumetto) {
            if (config.isNullOrVoid(this.messaggiService.idContestoNavbar)) {
                this.idContesto = this.navbarFilterContext.InfoFiltro.id;
            } else {
                this.idContesto = this.messaggiService.idContestoNavbar;
            }
        } else {
            if (config.isNullOrVoid(this.messaggiService.idContestoNavbar)) {
                this.idContesto = 1;
            } else {
                this.idContesto = this.messaggiService.idContestoNavbar;
            }
        }

        let context: HeaderMessaggi = this.headers.find(c => c.idContestoMess == this.idContesto);
        let index = this.headers.findIndex(c => c.idContestoMess == this.idContesto);
        // Setto la tab da mostrare
        if (index == -1) {
            //Mostro di default loa prima tab dell'array
            this.selected.setValue(0); //selezione
            this.loadMessaggi(this.headers[0].idContestoMess); //posizione e contesto
        }
        else {
            //Mostro la tab dell l'ultimo messaggio ricevuto
            this.selected.setValue(index); //selezione
            this.loadMessaggi(context.idContestoMess); //posizione e contesto
        }
    }



    async loadMessaggi(idContestoMessaggio: number) {
        //Svuoto ELEMENT_DATA
        this.loadComplete = false;
        this.ELEMENT_DATA = [];
        this.displayedColumns = this.staticColumnsMessaggi;
        this.idContesto = idContestoMessaggio;

        try {
            // Chiamata al BE per ottenere i messaggi in base al contersto
            let elencoMessaggi = await this.messaggiService.elencoMessaggi(this.idContesto).toPromise();
            if (this.isMessaggioFumetto) {
                await this.mapMessaggiFumetto(elencoMessaggi, idContestoMessaggio);
            } else {
                await this.mapMessaggiMenu(elencoMessaggi, idContestoMessaggio);
            }
            // setto a context l'id del contesto, questo permette il cambio della navbar in base al contesto
            this.context = idContestoMessaggio;
            await this.loadTipiMessaggi();
            await this.checkMessaggiNonLetti();
        } catch (error) {
            CommonsHandleException.authorizationError(error, this.router);
            console.error("Errore nel caricamento dei messaggi!")
            console.error(error);
        }
    }

    async mapMessaggiFumetto(elencoMessaggi: any, idContestoMessaggio: number) {
        elencoMessaggi.forEach(element => {
            let indiceSplit = element.messaggio.search(/\S\s/ || "$$TIMESTAMP");
            let mess: any = {
                messaggio: element.messaggio.substring(indiceSplit + 1).replace(/\s/g, '\u00A0'),
                idMessaggio: element.idMessaggio,
                fkTipoMessaggio: element.fkTipoMessaggio,
                dataCreazione: new Date(element.dataCreazione),
                flgLetto: element.flgLetto,
                fkTipoMessaggioSistema: element.fkTipoMessaggioSistema,
                idContestoMessaggio: idContestoMessaggio,
            };

            this.ELEMENT_DATA.push(mess);
        });

        this.ELEMENT_DATA.sort((a, b) => this.sortDataSource(a, b));

        this.dataSource = new MessaggiData([...this.ELEMENT_DATA]);
        this.loadComplete = true;
        this.dataSource.paginator = this.paginator;
        //setto la prima pagina del paginator
        this.dataSource.paginator.firstPage();
    }

    mapMessaggiMenu(elencoMessaggi: any, idContestoMessaggio: number) {
        this.elementSplice = new Map();
        elencoMessaggi.forEach(element => {
            let indiceSplit = element.messaggio.search(/\S\s/ || "$$TIMESTAMP");
            let mess: any = {
                messaggio: element.messaggio.substring(indiceSplit + 1).replace(/\s/g, '\u00A0'),
                idMessaggio: element.idMessaggio,
                fkTipoMessaggio: element.fkTipoMessaggio,
                dataCreazione: new Date(element.dataCreazione),
                flgLetto: element.flgLetto,
                fkTipoMessaggioSistema: element.fkTipoMessaggioSistema,
                idContestoMessaggio: idContestoMessaggio,
            };

            this.ELEMENT_DATA.push(mess);
        });

        this.ELEMENT_DATA.sort((a, b) => (a.dataCreazione < b.dataCreazione) ? 1 : ((b.dataCreazione < a.dataCreazione) ? -1 : 0));

        this.ELEMENT_DATA.splice(0, 0, { dataCreazione: new Date(this.ELEMENT_DATA[0].dataCreazione), dataRow: this.ELEMENT_DATA[0].dataCreazione });
        for (var j = 1; j < this.ELEMENT_DATA.length; j++) {
            if ((this.ELEMENT_DATA[j].dataCreazione != null && this.ELEMENT_DATA[j - 1].dataCreazione != null) && this.ELEMENT_DATA[j].dataCreazione.toDateString() != this.ELEMENT_DATA[j - 1].dataCreazione.toDateString()) {
                //creo un array che contiene tutti gli Header dei giorni per i messaggi
                this.elementSplice.set(j, this.ELEMENT_DATA[j].dataCreazione);
            };
        };

        let incrementoRighe = 0;
        this.elementSplice.forEach((value, key) => {
            this.ELEMENT_DATA.splice(key + incrementoRighe++, 0, { dataRow: value });
        });

        this.dataSource = new MessaggiData([...this.ELEMENT_DATA]);
        this.loadComplete = true;
        this.dataSource.paginator = this.paginator;
        //setto la prima pagina del paginator
        this.dataSource.paginator.firstPage();
    }

    // Carico i tipi messaggio in base al contesto
    async loadTipiMessaggi() {
        //Carico i tipi messaggi in base al contesto per mostrare l'icona nell'elenco
        try {
            this.tipiMessaggi = await this.procedimentoService.getTipiMessaggio(this.idContesto).toPromise();
        } catch (error) {
            console.error(error);
        }
    }

    // get contesto relativo all'ultimo messaggio ricevuto
    async getContestoHome() {
        try {
            this.navbarFilterContext.InfoFiltro = await this.utilityService.getContestoHome().toPromise();
        } catch (error) {
            console.error(error);
        }
    }

    // get di tutti i contesti
    async getContesti() {
        try {
            this.navbarFilterContext.ElencoContesti = await this.utilityService.getContesti().toPromise();
        } catch (error) {
            console.error(error);
        }
    }

    // Chiamata per settare blod i messaggi non letti
    async checkMessaggiNonLetti() {
        try {
            let check = await this.messaggiService.checkMessaggiNonLetti(this.idContesto).toPromise();
            if (!config.isNullOrVoid(check)) {
                let index = this.headers.findIndex(c => c.idContestoMess == this.idContesto);
                this.headers[index].bold = check;
                this.loadHeaderComplete = true;
            }
        } catch (error) {
            console.error(error);
        }
    }

    // metodo utilizzato per mostarre le icone per i messaggi dei procedimenti
    tipiMessaggiProcedimento(tipoMessaggio: Number) {
        if (this.tipiMessaggi != null && this.tipiMessaggi.includes(tipoMessaggio) && tipoMessaggio != 40 && this.idContesto == 2) {
            return true;
        }
        return false;
    }

    loadFunzionario() {
        // Carico i dati dell'utente e le autorizzazioni
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS) || this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
            this.isCancellaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA);
            this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS);

            this.isAutorizzatoMessaggi = this.funzionario.authority.includes(AuthorizationRoles.MESSAGGI);
            this.isAutorizzatoMessaggiRichieste = this.funzionario.authority.includes(AuthorizationRoles.MESSAGGI_RICHIESTE);
            this.isAutorizzatoMessaggiRendicontazione = this.funzionario.authority.includes(AuthorizationRoles.MESSAGGI_RENDICONTRAZIONE);
        });
    }

    sortDataSource(a, b): number {
        if (a.flgLetto != b.flgLetto) {
            return a.flgLetto == 'S' ? 1 : -1;
        }
        return a.dataCreazione < b.dataCreazione ? 1 : (b.dataCreazione < a.dataCreazione ? -1 : 0);
    }

    dettaglioMessaggio(value: number) {
        let action;
        if (this.isMessaggioFumetto) {
            action = '0';
        } else {
            action = '1';
        }
        this.router.navigate(['/dettaglioMessaggio/' + value, { action: action, idContesto: this.idContesto }]);
    }

    ripristinaNonLetto(value: number) {
        this.messaggiService.ripristinaNonLetto(value);
        this.ELEMENT_DATA.forEach(el => {
            if (el.idMessaggio == value) {
                el.flgLetto = 'N';
            }
        });

        this.messaggiService.ripristinaNonLetto$.subscribe(data => {
            this.appComponent.aggiornaMessaggi();
        });
        this.dataSource = new MessaggiData([...this.ELEMENT_DATA]);
    }
}

