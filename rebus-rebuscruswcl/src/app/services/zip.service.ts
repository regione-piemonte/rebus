/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class ZipService {

    constructor(
        private http: HttpClient
    ) { }



    zipRicercaContribuzione(body: any) {
        var url: string = "/parcobustplweb/restfacade/zip/zipRicercaContribuzione";
        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/zip' })
            }
        ));
    }
}