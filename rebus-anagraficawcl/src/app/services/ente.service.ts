/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TipoEnteVO } from '../vo/tipo-ente-vo';
import { TipoRaggruppamentoVO } from '../vo/drop-down-menu-vo';
import { EnteVO } from '../vo/ente-vo';

@Injectable()
export class EnteService {


    urlGlobal: string = "/anagrafichetplweb/restfacade/ente";

    constructor(
        private http: HttpClient,
    ) { }

    //------------------------- GET ------------------------------------
    getTipiEnte(): Observable<Array<TipoEnteVO>> {
        let url: string = this.urlGlobal + '/tipiEnte';
        return this.http.get<Array<TipoEnteVO>>(url);
    }

    getEnti(): Observable<Array<EnteVO>> {
        let url: string = this.urlGlobal + '/enti';
        return this.http.get<Array<EnteVO>>(url);
    }

    getTipiRaggruppamento(): Observable<Array<TipoRaggruppamentoVO>> {
        let url: string = this.urlGlobal + '/tipiRaggruppamento';
        return this.http.get<Array<TipoRaggruppamentoVO>>(url);
    }

}