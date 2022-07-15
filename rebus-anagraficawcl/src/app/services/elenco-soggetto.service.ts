/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SoggettoGiuridicoVO } from '../vo/soggetto-giuridico-vo';
import { FiltroSoggettoVO } from '../vo/filtro-soggetto';




@Injectable()
export class ElencoSoggettoService {


  urlGlobal: string = "/anagrafichetplweb/restfacade/elencoS";

  constructor(
    private http: HttpClient
  ) { }


  elencoSoggetto() {
    let url: string = this.urlGlobal + '/elencoSoggetto';
    let params = new HttpParams();
    return this.http.get<Array<SoggettoGiuridicoVO>>(url, { params: params });
  }



  filtraElencoSoggetto(filtro: FiltroSoggettoVO) {
    let url: string = this.urlGlobal + '/filtraElencoSoggetto';
    const body = filtro;
    return this.http.post<Array<SoggettoGiuridicoVO>>(url, body);
  }

}
