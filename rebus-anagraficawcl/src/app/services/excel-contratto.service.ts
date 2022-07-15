/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map } from "rxjs/operators";

@Injectable()
export class ExcelContrattoService {

    urlGlobal: string = "/anagrafichetplweb/restfacade/excel/";

    constructor(
        private http: HttpClient,
    ) { }

    excelRicercaContratti(body: any) {
        let url: string = this.urlGlobal + '/excelRicercaContratti';
        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/xls' })
            }
        ));
    }

}