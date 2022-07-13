/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable, ReplaySubject } from "rxjs";
import { RiferimentiNormativiVO } from "../vo/riferimenti-normativi-vo";
import { CommonsHandleException } from "../commons/commons-handle-exception";

@Injectable()
export class RiferimentiNormativiService {

  private urlRiferimenti: string = "/parcobustplweb/restfacade/riferimentiNormativi";

  //variable elenco 
  private elencoRiferimentoSubject = new ReplaySubject<RiferimentiNormativiVO[]>();
  elencoRiferimento$: Observable<Array<RiferimentiNormativiVO>> = this.elencoRiferimentoSubject.asObservable();


  constructor(
    private http: HttpClient,
    private router: Router,

  ) { }

  inserisciRiferimento(riferimento: FormData) {
    return this.http.post<number>(this.urlRiferimenti + '/inserisciRiferimento', riferimento);
  }

  elencoRiferimenti() {
    let params = new HttpParams();
    return this.http.get<Array<RiferimentiNormativiVO>>(this.urlRiferimenti + '/elencoriferimentiNormativi', { params: params }).subscribe(
      (data) => {

        if (data.length != null) {
          this.elencoRiferimentoSubject.next(data);
        }
        return data;
      },
      (err) => {

        console.error(err);
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }


  fileRemover(name, val) {
    // convert RegExp to string
    if (val && val.constructor === RegExp) {
      return val.toString();
    } else if (name === 'file') {
      return undefined; // remove from result
    } else {
      return val; // return as is
    }
  };

  private eliminaRiferimentoSubject = new ReplaySubject<string>();
  eliminaRiferimento$: Observable<string> = this.eliminaRiferimentoSubject.asObservable();

  eliminaRiferimentoNormativo(idDocumento: number) {
    let params = new HttpParams()
      .set('idDocumento', JSON.stringify(idDocumento));
    this.http.get<string>(this.urlRiferimenti + '/eliminaRiferimentoNormativo', { params: params }).subscribe(
      (data) => {
        this.eliminaRiferimentoSubject.next(data);
        this.elencoRiferimenti();
        return data;
      },
      (error) => {
        CommonsHandleException.authorizationError(error, this.router);
      });
  }

  getDocumentoByIdDocumento(idDocumento: number) {
    let params = new HttpParams().set('idDocumento', idDocumento.toString());
    return this.http.get(this.urlRiferimenti + '/getDocumentoByIdDocumento', { params: params, responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application' })
      });
  }



}