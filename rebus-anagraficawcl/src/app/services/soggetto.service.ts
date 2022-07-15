/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { InserisciSoggettoGiuridicoVO } from '../vo/inserisci-soggetto-giuridico-vo';
import { Observable, Subject } from 'rxjs';
import { TipoSoggettoGiuridicoVO } from '../vo/tipo-soggetto-giuridico-vo';
import { NaturaGiuridicaVO } from '../vo/natura-giuridica-vo';
import { SoggettoGiuridicoVO } from '../vo/soggetto-giuridico-vo';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { SoggettoEsecutoreVO } from '../vo/soggetto-esecutore-vo';
import { map } from 'rxjs/operators';

@Injectable()
export class SoggettoService {

  urlGlobal: string = "/anagrafichetplweb/restfacade/soggetto";

  constructor(
    private http: HttpClient,
    private router: Router,
  ) { }

  //------------------------- GET ------------------------------------
  getTipiSoggettoGiuridico(id: boolean): Observable<Array<TipoSoggettoGiuridicoVO>> {
    let url = this.urlGlobal + '/tipiSoggettoGiuridico';
    let params = new HttpParams();
    if (id) {
      url += '?idTipiSoggettoGiuridico=true';
    }
    return this.http.get<Array<TipoSoggettoGiuridicoVO>>(url, { params: params });
  }

  getNatureGiuridiche(): Observable<Array<NaturaGiuridicaVO>> {
    let url = this.urlGlobal + '/natureGiuridiche';
    return this.http.get<Array<NaturaGiuridicaVO>>(url);
  }


  //se id e true ritorna i sogetti esecutori attivi altrimenti li ritorna tutti
  getSoggettiEsecutoriTitolari(id: boolean, idSoggettoGiuridico: number): Observable<Array<SoggettoEsecutoreVO>> {
    let url = this.urlGlobal + '/soggettiEsecutoriTitolari';
    if (id) {
      url += '?idSoggettiEsecutoriTitolari=true';
    }
    if (idSoggettoGiuridico) {
      let params = new HttpParams().set('idSoggettoGiuridico', idSoggettoGiuridico.toString());
      return this.http.get<Array<SoggettoEsecutoreVO>>(url, { params });
    }
    else return this.http.get<Array<SoggettoEsecutoreVO>>(url);
  }
  //------------------------- INSERT ------------------------------------
  inserisciSoggetto(soggetto: InserisciSoggettoGiuridicoVO, file: File) {
    let url = this.urlGlobal + '/inserisciSoggetto';
    var form = new FormData();
    if (file)
      form.append("file", file);
    form.append("soggetto", JSON.stringify(soggetto));
    return this.http.post<number>(url, form);
  }

  //------------------------- SELECT ------------------------------------

  //Dettaglio soggetto
  private dettaglioSubject = new Subject<SoggettoGiuridicoVO>();
  dettaglioSoggetto$: Observable<SoggettoGiuridicoVO> = this.dettaglioSubject.asObservable();

  dettaglioSoggetto(idSog: number, actionFlag: string) {
    let url = this.urlGlobal + '/dettaglioSoggetto/' + idSog.toString();
    let params = new HttpParams().set('action', actionFlag);
    this.http.get<SoggettoGiuridicoVO>(url, { params }).subscribe(
      (data) => {
        this.dettaglioSubject.next(data);
        return data;
      },
      (err) => {
        CommonsHandleException.authorizationError(err, this.router);
      });
  }

  //------------------------- UPDATE ------------------------------------
  modificaSoggetto(soggetto: SoggettoGiuridicoVO, file: File) {
    let url = this.urlGlobal + '/modificaSoggetto';
    var form = new FormData();
    if (file)
      form.append("file", file);
    form.append("soggetto", JSON.stringify(soggetto));

    return this.http.post<number>(url, form);
  }



  //------------------------- LOGO ------------------------------------
  getLogoByIdSoggetto(id: number) {
    let params = new HttpParams().set('id', id.toString());
    let url = this.urlGlobal + '/logoByIdSoggetto';
    return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
      (res) => {
        return new Blob([res], { type: 'jpg' })
      }
    ));
  }

  eliminaLogoDb(idLogo: number): Observable<string> {
    let params = new HttpParams().set('idLogo', idLogo.toString());
    let url = this.urlGlobal + '/eliminaLogoByIdSoggetto';
    return this.http.delete<string>(url, { params });
  }

}
