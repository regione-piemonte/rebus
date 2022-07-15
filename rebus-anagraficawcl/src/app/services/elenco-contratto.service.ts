/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ContrattoVO } from '../vo/contratto-vo';
import { FiltroContrattoVO } from '../vo/filtro-contratto-vo';



@Injectable()
export class ElencoContrattoService {

  urlGlobal: string = "/anagrafichetplweb/restfacade/elencoC";
  
  constructor(
    private http: HttpClient
  ) { }

  elencoContratto() {
    let url: string = this.urlGlobal + '/elencoContratto';
    let params = new HttpParams();
    return this.http.get<Array<ContrattoVO>>(url, { params: params });
  }



  filtraElencoContratto(filtro: FiltroContrattoVO) {
    let url: string = this.urlGlobal + '/filtraElencoContratto';
    const body = filtro;
    return this.http.post<Array<ContrattoVO>>(url, body);
  }
}
