/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserInfo } from '../../vo/funzionario-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { UserService } from '../../services/user.service';
import { TipoProcedimentoVO } from '../../vo/extend-vo';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { ContestoVO } from '../../vo/contesto-vo';
import { MessaggiService } from '../../services/messaggi.service';
import { ProcedimentoService } from '../../services/procedimento.service';

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    @Input() labelSelected: string;
    @Input() filtroNavbar: number;
    funzionario: UserInfo;
    public subscribers: any = {};
    indexMenu: Array<Menu>;
    index: number;
    isAutobus: boolean;
    isProcedimento: boolean;
    isContribuzione: boolean;
    nuovoProcedimento1: TipoProcedimentoVO;
    nuovoProcedimento4: TipoProcedimentoVO;
    loadingTipologie: boolean;

    contestoAutobus: ContestoVO;
    contestoProcedimenti: ContestoVO;
    contestoContribuzione: ContestoVO;

    links: Array<TipoProcedimentoVO>;
    param: number = 1;

    constructor(private router: Router,
        private userService: UserService,
        private procedimentoService: ProcedimentoService,
        private navbarFilterContext: NavbarFilterContext,
        private route: ActivatedRoute,
        private messaggiService: MessaggiService) {
    }


    ngOnInit() {
        let url: string = window.location.href;

        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                if (this.navbarFilterContext.ElencoContesti != null) {
                    this.contestoAutobus = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
                    this.contestoProcedimenti = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
                    this.contestoContribuzione = this.navbarFilterContext.ElencoContesti.find(c => c.id === 5);
                    this.subscribers.verificaFunzionarioData = this.userService.funzionarioVo$.subscribe(data => {
                        this.funzionario = data;
                        if (this.navbarFilterContext.InfoFiltro) {
                            this.filtroNavbar = this.navbarFilterContext.InfoFiltro.id;
                        }
                        this.indexMenu = this.createIndexMenuForUser(data);
                    });
                    clearInterval(intervalContesti);
                }
            }, 200);
        } else {
            this.contestoAutobus = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
            this.contestoProcedimenti = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
            this.contestoContribuzione = this.navbarFilterContext.ElencoContesti.find(c => c.id === 5);
            this.subscribers.verificaFunzionarioData = this.userService.funzionarioVo$.subscribe(data => {
                this.funzionario = data;
                if (this.navbarFilterContext.InfoFiltro) {
                    this.filtroNavbar = this.navbarFilterContext.InfoFiltro.id;
                }
                this.indexMenu = this.createIndexMenuForUser(data);
            });
        }

    }

    ngOnChanges() {
        if (this.filtroNavbar && this.contestoAutobus && this.contestoProcedimenti) {
            this.indexMenu = this.createIndexMenuForUser(this.funzionario);
        }
    }


    private createIndexMenuForUser(funzionario): Array<Menu> {
        let menu: Array<Menu> = new Array<Menu>();
        let url: string = window.location.href;
        if (url.includes("messaggi/0"))
            this.param = 0;
        this.isAutobus = false;
        this.isProcedimento = false;
        if (url.toUpperCase().includes("RICHIEST") || url.toUpperCase().includes("PROCEDIMENTI") || url.toUpperCase().includes("RIFERIMENTI") || this.filtroNavbar == 2) {  //this.route.snapshot.paramMap.get('filter')
            this.isProcedimento = true;
            this.isAutobus = false;
            this.isContribuzione = false;
            this.messaggiService.idContestoNavbar = 2;
        } else if (this.filtroNavbar == 5 || url.toUpperCase().includes("CONTRIBUZIONE")) {
            this.isProcedimento = false;
            this.isAutobus = false;
            this.isContribuzione = true;
            this.messaggiService.idContestoNavbar = 5;
        } else {
            this.isAutobus = true;
            this.isProcedimento = false;
            this.isContribuzione = false;
            this.messaggiService.idContestoNavbar = 1;
        }
        if (url.toUpperCase().includes("DETTAGLIOMESSAGGIO")) {
            let idContesto = +this.route.snapshot.paramMap.get('idContesto');
            this.messaggiService.idContestoNavbar = idContesto;
            this.isAutobus = idContesto == 1 ? true : false;
            this.isProcedimento = idContesto == 2 ? true : false;
            this.isContribuzione = idContesto == 5 ? true : false;
            this.filtroNavbar = idContesto;
        }

        if (this.isProcedimento) {
            this.nuovoProcedimento1 = new TipoProcedimentoVO();
            this.nuovoProcedimento4 = new TipoProcedimentoVO();
            this.loadingTipologie = true;
            this.procedimentoService.getElencoTipologia().subscribe(data => {
                if (data) {
                    this.links = data;
                    this.links = this.links.filter(a => a.id != 7); //rimozione rendicontazione
                    let descrizione = this.links.find(a => a.id == 3).descrizione
                    this.nuovoProcedimento1.id = 31;
                    this.nuovoProcedimento1.descrizione = descrizione + " e " + this.links.find(a => a.id == 1).descrizione;
                    this.nuovoProcedimento4.id = 34;
                    this.nuovoProcedimento4.descrizione = descrizione + " e " + this.links.find(a => a.id == 4).descrizione;
                }
                this.loadingTipologie = false;
            });
        }
        if (this.isProcedimento) {
            menu.push({ label: this.contestoProcedimenti.descrizione.toUpperCase(), routing: '', indexName: 'PROCEDIMENTI', disabled: true });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_PROCEDIMENTI))
                menu.push({ label: 'RICERCA', routing: '/ricercaProcedimenti', indexName: 'RICERCA_PROCEDIMENTI', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_PROCEDIMENTO))
                menu.push({ label: 'INSERISCI', routing: '#', indexName: 'INSERIMENTO_RICHIESTA', disabled: false })
            if (this.funzionario.authority.includes(AuthorizationRoles.SCARICA_RIFERIMENTI) || this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_RIFERIMENTI))
                menu.push({ label: 'RIFERIMENTI NORMATIVI', routing: '/riferimentiNormativi', indexName: 'RIFERIMENTI', disabled: false })
            if (this.funzionario.authority.includes(AuthorizationRoles.MESSAGGISTICA))
                menu.push({ label: 'MESSAGGI', routing: '/messaggi/' + this.param, indexName: 'MESSAGGISTICA', disabled: false })
        }
        if (this.isAutobus) {
            menu.push({ label: this.contestoAutobus.descrizione.toUpperCase(), routing: '', indexName: 'PARCO BUS', disabled: true });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS) && this.funzionario.idAzienda != null)
                menu.push({ label: 'RICERCA', routing: '/ricercabusazienda', indexName: 'RICERCA_ANAGRAFICA_BUS', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS) && this.funzionario.idEnte != null)
                menu.push({ label: 'RICERCA', routing: '/ricercabusazienda', indexName: 'RICERCA_ANAGRAFICA_BUS', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS))
                menu.push({ label: 'INSERISCI', routing: '/inserisci', indexName: 'INSERIMENTO_ANAGRAFICA_BUS', disabled: false })
            if (this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS) || this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO))
                menu.push({ label: 'CARICA DATI DA MODULO', routing: '/upload', indexName: 'UPLOAD_FILE_XLS', disabled: false });
            if (funzionario.authority.includes(AuthorizationRoles.DOWNLOAD_FILE_XLS))
                menu.push({ label: 'SCARICA MODULO', routing: '/download', indexName: 'DOWNLOAD_FILE_XLS', disabled: false })
            if (this.funzionario.authority.includes(AuthorizationRoles.MESSAGGISTICA))
                menu.push({ label: 'MESSAGGI', routing: '/messaggi/' + this.param, indexName: 'MESSAGGISTICA', disabled: false })
        }
        if (this.isContribuzione) {
            menu.push({ label: this.contestoContribuzione.descrizione.toUpperCase(), routing: '', indexName: 'CONTRIBUZIONE', disabled: true });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_CONTRIBUZIONE))
                menu.push({ label: 'RICERCA', routing: '/ricercaContribuzione', indexName: 'RICERCA_CONTRIBUZIONE', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE))
                menu.push({ label: 'INSERISCI', routing: '/inserisciContribuzione/7', indexName: 'INSERIMENTO_CONTRIBUZIONE', disabled: false })
            if (this.funzionario.authority.includes(AuthorizationRoles.MESSAGGISTICA))
                menu.push({ label: 'MESSAGGI', routing: '/messaggi/' + this.param, indexName: 'MESSAGGISTICA', disabled: false })
        }

        for (let i = 0; i < menu.length; i++) {
            if (menu[i].indexName == this.labelSelected) {
                this.index = i;
                break;
            }
        }
        return menu;
    }

    public selectedTab(e) {
        if (this.isAutobus || e.index != 2 || this.isContribuzione) { //e.index = 2 corrisponde a "INSERISCI" --> deve aprire un menù
            this.router.navigateByUrl(this.indexMenu[e.index].routing);
        }
    }
}


export interface Menu {
    label: string;
    routing: string;
    indexName: string;
    disabled: boolean;
}