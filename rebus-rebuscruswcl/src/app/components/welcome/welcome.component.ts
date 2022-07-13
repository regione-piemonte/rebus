/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit } from '@angular/core';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { UtilityService } from '../../services/utility.service';
import { ContestoVO } from '../../vo/contesto-vo';

@Component({
    selector: 'app-welcome',
    templateUrl: 'welcome.component.html'
})

export class WelcomeComponent implements OnInit {

    constructor(private navbarFilterMessaggi: NavbarFilterContext, private utilityService: UtilityService,) { }
    ngOnInit(): void {
        this.utilityService.getContestoHome().subscribe(dati => {
            if (dati) {
                this.navbarFilterMessaggi.InfoFiltro = dati;
            } else {
                this.navbarFilterMessaggi.InfoFiltro = new ContestoVO();
            }
        });
    }
}