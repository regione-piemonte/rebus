/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map } from "rxjs/operators";

@Injectable()
export class ExcelSoggettoService {

    urlGlobal: string = "/anagrafichetplweb/restfacade/excelSoggetto";

    constructor(
        private http: HttpClient,
    ) { }

    excelSoggettoRicerca(body: any) {
        let url: string = this.urlGlobal + '/excelSoggettoRicerca';
        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/xls' })
            }
        ));
    }

}