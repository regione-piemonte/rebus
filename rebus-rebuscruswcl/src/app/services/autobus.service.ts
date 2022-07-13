/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject, Subject } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { AutobusVO } from '../vo/autobus-vo';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { FiltroAutobusVO } from '../vo/filtro-autobus-vo';
import { InserisciAutobusVO } from '../vo/inserisci-autobus-vo';
import { VeicoloVO } from '../vo/veicolo-vo';
import { TipologiaDimensioneVO } from '../vo/tiplogia-dimensione-vo';
import { DepositoVO } from '../vo/deposito-vo';
import { PortabiciAutobusVO } from '../vo/autobus/portabici-autobus-VO';
import { SistemaLocalizzazioneVO } from '../vo/autobus/sistema-localizzazione-VO';
import { SistemaVideosorveglianzaVO } from '../vo/autobus/sistema-videosorveglianza-VO';
import { DocVariazAutobusVO } from '../vo/doc-variaz-autobus-vo';
import { MisurazioniVO } from '../vo/misurazioni-vo';




@Injectable()
export class AutobusService {

  private urlAutobus: string = "/parcobustplweb/restfacade/autobus";

  //Dettaglio bus 
  private dettaglioSubject = new Subject<AutobusVO>();
  dettaglioAutobus$: Observable<AutobusVO> = this.dettaglioSubject.asObservable();

  //Elimina bus
  private eliminaSubject = new ReplaySubject<string>();
  eliminaAutobus$: Observable<string> = this.eliminaSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,

  ) { }





  //************************* GET *************************//

  //GET TIPOLOGIA DIMENSIONE
  private tipologiaDimensioneSubject = new ReplaySubject<string>();
  tipologiaDimensione$: Observable<string> = this.tipologiaDimensioneSubject.asObservable();

  getTipolgiaDimensione(idTipoAllestimento: number): Observable<TipologiaDimensioneVO> {
    let params = new HttpParams()
      .set('idTipoAllestimento', JSON.stringify(idTipoAllestimento));
    return this.http.get<TipologiaDimensioneVO>(this.urlAutobus + '/tipologiaDimensione', { params });
  }

  getVeicoliInserisci(idTipoProcedimento: number) {
    let params = new HttpParams().set('idTipoProcedimento', JSON.stringify(idTipoProcedimento));
    return this.http.get<Array<VeicoloVO>>(this.urlAutobus + '/veicoliInserisci', { params });
  }

  getVeicoliModifica(idProcedimento: number, idTipoProcedimento: number) {
    let params = new HttpParams().set('idProcedimento', JSON.stringify(idProcedimento)).set('idTipoProcedimento', JSON.stringify(idTipoProcedimento));
    return this.http.get<Array<VeicoloVO>>(this.urlAutobus + '/veicoliModifica', { params });
  }

  getDepositi(idAzienda: number) {
    let params = new HttpParams().set('idAzienda', JSON.stringify(idAzienda));
    return this.http.get<Array<DepositoVO>>(this.urlAutobus + '/depositi', { params });
  }

  getMisurazioni(primoTelaio: string) {
    let params = new HttpParams().set('primoTelaio', primoTelaio);
    return this.http.get<Array<MisurazioniVO>>(this.urlAutobus + '/misurazioni', { params });
  }

  getDepositoById(idDeposito: number) {
    let params = new HttpParams().set('idDeposito', JSON.stringify(idDeposito));

    return this.http.get<DepositoVO>(this.urlAutobus + '/depositoById', { params });
  }

  getAutobusForContribuzioneAzienda() {
    return this.http.get<any>(this.urlAutobus + '/getAutobusForContribuzioneAzienda');
  }

  getAllPortabiciForAutobus() {
    return this.http.get<PortabiciAutobusVO[]>(this.urlAutobus + '/getAllPortabiciForAutobus');
  }

  getAllSistemaLocalizzazioneForAutobus() {
    return this.http.get<SistemaLocalizzazioneVO[]>(this.urlAutobus + '/getAllSistemaLocalizzazioneForAutobus');
  }

  getAllSistemaVideosorveglianzaForAutobus() {
    return this.http.get<SistemaVideosorveglianzaVO[]>(this.urlAutobus + '/getAllSistemaVideosorveglianzaForAutobus');
  }

  getDocVariazAutobusForInfo(idVa: number) {
    let params = new HttpParams().set('idVa', JSON.stringify(idVa));
    return this.http.get<DocVariazAutobusVO[]>(this.urlAutobus + '/getDocVariazAutobusForInfo', { params });
  }

  //************************* UPDATE *************************//

  //Modifica Bus
  modificaBus(autobus: AutobusVO, isUpload: boolean) {
    var form = new FormData();
    form.append("isUpload", isUpload.toString());

    form.append("autobus", JSON.stringify(JSON.parse(JSON.stringify(autobus, this.replacer))));

    return this.http.post(this.urlAutobus + '/modificaAutobus', form, { responseType: 'text' });
  }

  //************************* INSERT *************************//

  inserisciBus(autobus: InserisciAutobusVO) {
    var form = new FormData();
    form.append("autobus", JSON.stringify(autobus));
    return this.http.post<number>(this.urlAutobus + '/inserisciAutobus', form);
  }

  //************************* DETTAGLIO *************************//

  dettaglioAutobus2(idBus: number, actionFlag: string) {
    let params = new HttpParams().set('action', actionFlag);
    return this.http.get<AutobusVO>(this.urlAutobus + '/dettaglioAutobus/' + idBus.toString(), { params });
  }

  //************************* DELETE *************************//

  eliminaAutobus(idBus: number[]) {
    let params = new HttpParams()
      .set('idBus', JSON.stringify(idBus));
    this.http.get<string>(this.urlAutobus + '/eliminaAutobus', { params: params }).subscribe(
      (data) => {
        this.eliminaSubject.next(data);
        return data;
      },
      (error) => {
        CommonsHandleException.authorizationError(error, this.router);
      });
  }

  // ************************* ELENCO *************************//

  elencoAutobus() {
    let params = new HttpParams();
    //Timeout 16 minuti
    return this.http.get<Array<FiltroAutobusVO>>(this.urlAutobus + '/elencoAutobus', { params: params }).timeout(1000000);
  }

  filtraElencoAutobus(filtro: FiltroAutobusVO) {
    const body = filtro;
    //Timeout 16 minuti
    return this.http.post<Array<FiltroAutobusVO>>(this.urlAutobus + '/filtraElencoAutobus', body);
  }

  //************************* UTILITY *************************//

  replacer(key, value) {
    if (value === null) return undefined
    return value
  }

}

export enum Action {
  EDIT = "E",
  VIEW = "V",
}
