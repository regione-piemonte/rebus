/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class PDFService {

    constructor(
        private http: HttpClient
    ) { }


    downloadPDF(idProcedimento: number, idTipoStampa: number) {
        let params = new HttpParams().set('idProcedimento', idProcedimento.toString()).set('idTipoStampa', idTipoStampa.toString());
        var url: string = "/parcobustplweb/restfacade/pdf/downloadPDF";
        return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            }
        ));
    }
}