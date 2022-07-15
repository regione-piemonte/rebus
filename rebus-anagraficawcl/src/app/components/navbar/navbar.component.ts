/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { UserInfo } from '../../vo/funzionario-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { UserService } from '../../services/user.service';

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

    @Input() labelSelected: string;
    funzionario: UserInfo;
    public subscribers: any = {};
    indexMenu: Array<Menu>;
    index: number;

    constructor(
        private router: Router,
        private userService: UserService,
    ) {
    }


    ngOnInit() {
        this.subscribers.verificaFunzionarioData = this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.indexMenu = this.createIndexMenuForUser(data);
        });
    }

    private createIndexMenuForUser(funzionario): Array<Menu> {
        let menu: Array<Menu> = new Array<Menu>();
        let url: string = window.location.href;
        let isSoggetto: boolean = false;
        let isContratto: boolean = false;
        if (url.toUpperCase().includes("SOGGETTO")) {
            isSoggetto = true;
            isContratto = false;
        }
        if (url.toUpperCase().includes("CONTRATTO")) {
            isContratto = true;
            isSoggetto = false;
        }
        if (isSoggetto) {
            menu.push({ label: 'SOGGETTI', routing: '', indexName: 'SOGGETTO', disabled: true });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI))
                menu.push({ label: 'RICERCA', routing: '/ricercaSoggetto', indexName: 'RICERCA_SOGGETTO', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_SOGGETTO))
                menu.push({ label: 'INSERISCI', routing: '/inserisciSoggetto', indexName: 'INSERIMENTO_SOGGETTO', disabled: false })
        }
        if (isContratto) {
            menu.push({ label: 'CONTRATTI', routing: '', indexName: 'CONTRATTO', disabled: true });
            if (this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_CONTRATTI))
                menu.push({ label: 'RICERCA', routing: '/ricercaContratto', indexName: 'RICERCA_CONTRATTO', disabled: false });
            if (this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_CONTRATTO))
                menu.push({ label: 'INSERISCI', routing: '/inserisciContratto', indexName: 'INSERIMENTO_CONTRATTO', disabled: false })
        }

        //TODO AGGIUNGERE NUOVE OPZIONI AL MAT TAB
        for (let i = 0; i < menu.length; i++) {
            if (menu[i].indexName == this.labelSelected) {
                this.index = i;
                break;
            }

        }
        return menu;
    }

    public selectedTab(e) {
        this.router.navigateByUrl(this.indexMenu[e.index].routing);
    }
    public routingTab() {
        this.router.navigateByUrl(this.indexMenu[this.index].routing);
    }


}


export interface Menu {
    label: string;
    routing: string;
    indexName: string;
    disabled: boolean;
}