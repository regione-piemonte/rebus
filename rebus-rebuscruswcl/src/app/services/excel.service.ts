/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class ExcelService {

    constructor(
        private http: HttpClient
    ) { }



    excelRicercaContribuzione(body: any) {
        var url: string = "/parcobustplweb/restfacade/excel/excelRicercaContribuzione";
        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/xls' })
            }
        ));
    }

    exportRicercaAutobus(body: any) {
        var url: string = "/parcobustplweb/restfacade/excel/excelRicerca";
        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/xls' })
            }
        ));
    }

    inserisciExcel(formModel: any) {
        var url: string = "/parcobustplweb/restfacade/excel/inserisciExcel";
        return this.http.post(url, formModel, { responseType: 'blob' }).pipe(map(
            (res) => {
                return res
            }
        ));

    }

    dettagliErroriExcel(input: any) {
        var url: string = "/parcobustplweb/restfacade/excel/dettaglioErroriExcel";
        return this.http.post(url, input, { responseType: 'text' }).pipe(map(
            (res) => {
                return res
            }
        ));

    }
}