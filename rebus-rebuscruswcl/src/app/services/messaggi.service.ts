/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from "@angular/router";
import { MessaggioVO } from "../vo/messaggio-vo";
import { Observable, Subject } from "rxjs";

import { CommonsHandleException } from "../commons/commons-handle-exception";
import { UtenteAzEnteVO } from "../vo/utenteAzEnte-vo";


@Injectable()
export class MessaggiService {

  idContestoNavbar: number;


  constructor(
    private http: HttpClient,
    private router: Router,


  ) { }


  elencoMessaggi(filter: number): Observable<Array<MessaggioVO>> {
    var url: string = '/parcobustplweb/restfacade/messaggi/elencoMessaggi';
    let params = new HttpParams().set('filter', filter.toString());
    //Timeout 16 minuti
    return this.http.get<Array<MessaggioVO>>(url, { params }).timeout(1000000);
  }

  checkMessaggiNonLetti(filter: number): Observable<Boolean> {
    var url: string = '/parcobustplweb/restfacade/messaggi/checkMessaggiNonLetti';
    let params = new HttpParams().set('filter', filter.toString());
    //Timeout 16 minuti
    return this.http.get<Boolean>(url, { params }).timeout(1000000);
  }


  numMessDaLeggere(): Observable<number> {
    var url: string = '/parcobustplweb/restfacade/messaggi/calcolaNumMessaggi';
    //Timeout 16 minuti
    return this.http.get<number>(url).timeout(1000000);
  }

  //Dettaglio messaggio 
  private dettaglioSubject = new Subject<MessaggioVO>();
  dettaglioMessaggio$: Observable<MessaggioVO> = this.dettaglioSubject.asObservable();
  private subscribedDettaglioMessaggio;
  dettaglioMessaggio(idMessaggio: number) {
    var url: string = '/parcobustplweb/restfacade/messaggi/dettaglioMessaggio/';
    let params = new HttpParams().set('idMessaggio', idMessaggio.toString());
    this.subscribedDettaglioMessaggio = this.http.get<MessaggioVO>(url, { params }).subscribe(
      (data) => {
        this.dettaglioSubject.next(data);
      },
      (err) => {
        CommonsHandleException.authorizationError(err, this.router);
      });
  }

  //dettaglioUtenteAzEnte
  private dettaglioUtenteAzEnteSubject = new Subject<UtenteAzEnteVO>();
  dettaglioUtenteAzEnte$: Observable<UtenteAzEnteVO> = this.dettaglioUtenteAzEnteSubject.asObservable();
  private subscribedDettaglioUtenteAzEnte;
  dettaglioUtenteAzEnte(idUtente: number) {
    var url: string = '/parcobustplweb/restfacade/messaggi/dettaglioUtenteAzEnte/';
    let params = new HttpParams().set('idUtente', idUtente.toString());
    this.subscribedDettaglioUtenteAzEnte = this.http.get<UtenteAzEnteVO>(url, { params }).subscribe(
      (data) => {
        this.dettaglioUtenteAzEnteSubject.next(data);
      },
      (err) => {
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }

  resetMessaggi() {
    this.subscribedDettaglioMessaggio.unsubscribe();
  }


  //ripristinaNonLetto  
  private ripristinaNonLettoSubject = new Subject<String>();
  ripristinaNonLetto$: Observable<String> = this.ripristinaNonLettoSubject.asObservable();

  ripristinaNonLetto(idMessaggio: number) {
    var url: string = '/parcobustplweb/restfacade/messaggi/ripristinaNonLetto/';
    let params = new HttpParams().set('idMessaggio', idMessaggio.toString());
    this.http.get<String>(url, { params }).subscribe(
      (data) => {
        this.ripristinaNonLettoSubject.next(data);
        return data;
      },
      (err) => {
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }

  //ripristinaNonLetto  
  private segnaComeLettoSubject = new Subject<String>();
  segnaComeLetto$: Observable<String> = this.segnaComeLettoSubject.asObservable();

  segnaComeLetto(idMessaggio: number) {
    var url: string = '/parcobustplweb/restfacade/messaggi/segnaComeLetto/';
    let params = new HttpParams().set('idMessaggio', idMessaggio.toString());
    this.http.get<String>(url, { params }).subscribe(
      (data) => {
        this.segnaComeLettoSubject.next(data);
        return data;
      },
      (err) => {
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }

  inserisciMessaggio(messaggio: MessaggioVO) {
    let url = '/parcobustplweb/restfacade/messaggi/inserisciMessaggio';
    var form = new FormData();

    form.append("messaggio", JSON.stringify(messaggio));

    return this.http.post<number>(url, form);
  }


}