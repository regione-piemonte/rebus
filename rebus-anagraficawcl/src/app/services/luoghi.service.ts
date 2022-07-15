/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComuneVO } from '../vo/comune-vo';
import { ProvinciaVO } from '../vo/provincia-vo';

@Injectable()
export class LuoghiService {

    urlGlobal: string = "/anagrafichetplweb/restfacade/luoghi";

    constructor(
        private http: HttpClient,
    ) { }

    //------------------------- GET ------------------------------------
    getProvince(): Observable<Array<ProvinciaVO>> {
        let url: string = this.urlGlobal + '/province';
        return this.http.get<Array<ProvinciaVO>>(url);
    }

    getComuniByIdProvincia(id: number): Observable<Array<ComuneVO>> {
        let url: string = this.urlGlobal + '/comuniByIdProvincia';
        let params = new HttpParams().set('idProvincia', id.toString());
        return this.http.get<Array<ComuneVO>>(url, { params: params });
    }

    getComuni(): Observable<Array<ComuneVO>> {
        let url: string = this.urlGlobal + '/comuni';
        return this.http.get<Array<ComuneVO>>(url);
    }

    getProvinciaByIdComune(id: number): Observable<ProvinciaVO> {
        let url: string = this.urlGlobal + '/provinciaByIdComune';
        let params = new HttpParams().set('idComune', id.toString());
        return this.http.get<ProvinciaVO>(url, { params: params });
    }

}