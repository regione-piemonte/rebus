/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { UserInfo } from '../vo/funzionario-vo';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { MessageRestError } from '../class/error-rest';
import { ConfigService } from './config.service';
import { Ruolo } from '../vo/ruolo';
import { AziendaVO } from '../vo/azienda-vo';


@Injectable()
export class UserService {

  private funzionarioVo = new ReplaySubject<UserInfo>();
  funzionarioVo$: Observable<UserInfo> = this.funzionarioVo.asObservable();


  verificaUtente() {
    var url: string = '/parcobustplweb/restfacade/user/verificaUtente';
    return this.http.get<UserInfo>(url).subscribe(data => {
      if (data) {
        this.funzionarioVo.next(data);
      } else {
        this.router.navigate(['/error'], { queryParams: { message: MessageRestError.UNAUTHORIZED }, skipLocationChange: true });
      }
    }, (err) => {
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }

  setIdAziendaUtente(id: number) {
    let url = '/parcobustplweb/restfacade/user/setIdAziendaUtente';
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<UserInfo>(url, { params: params });
  }

  getFunzionarioAzienda() {
    return this.funzionarioVo;
  }

  setRuoloUtente(ruolo: Ruolo) {
    let url = '/parcobustplweb/restfacade/user/setRuoloUtente';
    const body = ruolo;
    return this.http.post<UserInfo>(url, body);
  }

  getAziendeFunzionario() {
    var url: string = '/parcobustplweb/restfacade/logout/aziendeFunzionario';
    return this.http.get<Array<AziendaVO>>(url);
  }

  logOut() {
    var url: string = '/parcobustplweb/restfacade/user/localLogout';
    return this.http.get(url, { responseType: 'text' }).subscribe(data => {
      return data;
    }, err => {
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }



  constructor(private http: HttpClient, private config: ConfigService, private router: Router) {

  }


}
