/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject } from 'rxjs';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { UserInfo } from '../vo/funzionario-vo';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { MessageRestError } from '../class/error-rest';
import { Injectable } from '@angular/core';
import { Ruolo } from '../vo/ruolo';
import { AziendaVO } from '../vo/azienda-vo';


@Injectable()
export class UserService {

  private funzionarioVo = new ReplaySubject<UserInfo>();
  funzionarioVo$: Observable<UserInfo> = this.funzionarioVo.asObservable();

  urlGlobal: string = "/anagrafichetplweb/restfacade";



  constructor(
    private http: HttpClient,
    private router: Router) {
  }

  verificaUtente() {
    let url: string = this.urlGlobal + '/user/verificaUtente';
    return this.http.get<UserInfo>(url).subscribe(data => {
      if (data) {
        this.funzionarioVo.next(data);
      } else {
        console.error('utente non abilitato');
        this.router.navigate(['/error'], { queryParams: { message: MessageRestError.UNAUTHORIZED }, skipLocationChange: true });

      }
    }, (err) => {
      console.error('ERRORE IN VERIFICA UTENTE: ' + err);
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }

  logOut() {
    let url: string = this.urlGlobal + '/user/localLogout';
    return this.http.get(url, { responseType: 'text' }).subscribe(data => {
      return data;
    }, err => {
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }

  //------------------------- SET ------------------------------------
  setRuoloUtente(ruolo: Ruolo) {
    let url: string = this.urlGlobal + '/user/setRuoloUtente';
    const body = ruolo;
    return this.http.post<UserInfo>(url, body);
  }

  setIdAziendaUtente(id: number) {
    let url: string = this.urlGlobal + '/user/setIdAziendaUtente';
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<UserInfo>(url, { params: params });
  }

  //------------------------- GET ------------------------------------
  getFunzionarioAzienda() {
    return this.funzionarioVo;
  }

  getAziendeFunzionario() {
    let url: string = this.urlGlobal + '/logout/aziendeFunzionario';
    return this.http.get<Array<AziendaVO>>(url);
  }





  getCodiceRuoloUtente() {
    let url: string = this.urlGlobal + '/user/getCodiceRuoloUtente';
    return this.http.get<string>(url, { responseType: 'text' as 'json' });
  }

}