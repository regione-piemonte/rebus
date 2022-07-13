/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit } from '@angular/core';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';


@Component({
    selector: 'app-scarica',
    templateUrl: './scarica.component.html',
    styleUrls: ['./scarica.component.css']
})

export class ScaricaComponent implements OnInit {
    context: string;
    constructor(
        private navbarFilterContext: NavbarFilterContext) { }

    ngOnInit() {
        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
                if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                    this.context = this.navbarFilterContext.InfoFiltro.cod;
                }
                clearInterval(intervalContesti);
            }, 200);
        } else {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
            if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }
    }

}