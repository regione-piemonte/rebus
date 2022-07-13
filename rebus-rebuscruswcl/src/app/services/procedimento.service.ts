/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { MotorizzazioneVO, TipoProcedimentoVO, MotivazioneVO, TipoDocumentoVO, TipoContrattoVO, VoceDiCostoVO, StatoProcedimentoVO } from "../vo/extend-vo";
import { Observable, ReplaySubject } from "rxjs";
import { ContrattoProcVO, ContrattoProcDatiVO } from "../vo/contratto-proc-vo";
import { InserisciRichiestaVO } from "../vo/inserisci-richiesta.vo";
import { DettaglioRichiestaVO } from "../vo/dettaglio-richiesta-vo";
import { TransizioneAutomaVO } from "../vo/transizione-automa-vo";
import { InserisciRichiestaUsoInLineaVO } from "../vo/inserisci-richiesta-uso-in-linea.vo";
import * as config from '../globalparameter';
import { FiltroProcedimentiVO } from "../vo/filtro-procedimenti-vo";
import { ProcedimentiVO } from "../vo/procedimenti-vo";
import { CommonsHandleException } from "../commons/commons-handle-exception";
import { Router } from "@angular/router";

@Injectable()
export class ProcedimentoService {

  private urlProcedimenti: string = "/parcobustplweb/restfacade/procedimenti";

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  //************************* GET *************************//

  getTipoProcedimento(id: number): Observable<TipoProcedimentoVO> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<TipoProcedimentoVO>(this.urlProcedimenti + '/tipoProcedimento', { params });
  }

  getTipoProcedimentoByIdProc(id: number): Observable<TipoProcedimentoVO> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<TipoProcedimentoVO>(this.urlProcedimenti + '/tipoProcedimentoByIdProc', { params });
  }

  getMotorizzazioni(): Observable<Array<MotorizzazioneVO>> {
    return this.http.get<Array<MotorizzazioneVO>>(this.urlProcedimenti + '/motorizzazioni');
  }

  getMotivazioni(idTipoProcedimento: number, hasDataFilter: boolean): Observable<Array<MotivazioneVO>> {
    let params = new HttpParams().set('idTipoProcedimento', idTipoProcedimento.toString()).set('hasDataFilter', hasDataFilter.toString());
    return this.http.get<Array<MotivazioneVO>>(this.urlProcedimenti + '/motivazioni', { params });
  }

  getContrattiInserisci(): Observable<Array<ContrattoProcVO>> {
    return this.http.get<Array<ContrattoProcVO>>(this.urlProcedimenti + '/contrattiInserisci');
  }

  getContrattiModifica(idProcedimento: number): Observable<Array<ContrattoProcVO>> {
    let params = new HttpParams().set('idProcedimento', idProcedimento.toString());
    return this.http.get<Array<ContrattoProcVO>>(this.urlProcedimenti + '/contrattiModifica', { params });
  }

  getDatiContratto(idContratto: number, idProcedimento: number): Observable<ContrattoProcDatiVO> {
    let params = new HttpParams().set('idContratto', idContratto.toString()).set('idProcedimento', idProcedimento ? idProcedimento.toString() : "");
    return this.http.get<ContrattoProcDatiVO>(this.urlProcedimenti + '/datiContratto', { params });
  }

  getTipiContratto(): Observable<Array<TipoContrattoVO>> {
    return this.http.get<Array<TipoContrattoVO>>(this.urlProcedimenti + '/tipiContratto');
  }

  getTipiDocumento(idTipoProcedimento: number): Observable<Array<TipoDocumentoVO>> {
    let params = new HttpParams().set('idTipoProcedimento', idTipoProcedimento.toString());
    return this.http.get<Array<TipoDocumentoVO>>(this.urlProcedimenti + '/tipiDocumento', { params });
  }

  getTipiMessaggio(idContesto: number): Observable<Array<Number>> {
    let params = new HttpParams().set('idContesto', idContesto.toString());
    return this.http.get<Array<Number>>(this.urlProcedimenti + '/tipiMessaggio', { params });
  }

  getDescrizioneAziendaMessaggioProc(id: Number): Observable<String> {
    let params = new HttpParams().set('idProcedimento', id.toString());
    return this.http.get<String>(this.urlProcedimenti + '/descrizioneAziendaMessaggioProc', { params: params, responseType: 'text' as 'json' });
  }

  getFirmaProcedimento(): Observable<DettaglioRichiestaVO> {
    return this.http.get<DettaglioRichiestaVO>(this.urlProcedimenti + '/firmaProcedimenti');
  }

  getVociDiCosto(): Observable<Array<VoceDiCostoVO>> {
    return this.http.get<Array<VoceDiCostoVO>>(this.urlProcedimenti + '/vociDiCosto');
  }

  getNumProcedimento(idTipoProcedimento: number): Observable<number> {
    let params = new HttpParams().set('idTipoProcedimento', idTipoProcedimento.toString());
    return this.http.get<number>(this.urlProcedimenti + '/numProcedimento', { params });
  }

  getDocumento(idTipoProcedimento: number, idTipoDocumento: number): Observable<Array<TipoDocumentoVO>> {
    let params = new HttpParams().set('idTipoProcedimento', idTipoProcedimento.toString()).append('idTipoDocumento', idTipoDocumento.toString());
    return this.http.get<Array<TipoDocumentoVO>>(this.urlProcedimenti + '/documenti', { params });
  }

  getElencoTipologia(): Observable<Array<TipoProcedimentoVO>> {
    return this.http.get<Array<TipoProcedimentoVO>>(this.urlProcedimenti + '/elencoTipologia');
  }

  getElencoStato(): Observable<Array<StatoProcedimentoVO>> {
    return this.http.get<Array<StatoProcedimentoVO>>(this.urlProcedimenti + '/elencoStato');
  }

  

  //************************* INSERT *************************//

  inserisciRichiesta(richiesta: InserisciRichiestaVO) {
    var form: FormData = new FormData();
    if (richiesta.files.length > 0) {
      var i = 0;
      for (var file of richiesta.files) {
        form.append("file" + i, file.file);
        i++;
      }
    }
    form.append("richiesta", JSON.stringify(richiesta, this.fileRemover));

    return this.http.post<number>(this.urlProcedimenti + '/inserisciRichiesta', form);
  }

  inserisciRichiestaSostituzione(richieste: Array<InserisciRichiestaVO>) {
    var form: FormData = new FormData();
    var j = 0;
    for (var richiesta of richieste) {
      if (richiesta.files != null && richiesta.files.length > 0) {
        var i = 0;
        for (var file of richiesta.files) {
          form.append("file" + richiesta.tipoProcedimento.id + i, file.file);
          i++;
        }
      }
      form.append("richiesta" + j, JSON.stringify(richiesta, this.fileRemover));
      j++
    }
    return this.http.post<number>(this.urlProcedimenti + '/inserisciRichiestaSostituzione', form);
  }

  inserisciRichiestaUsoInLinea(richiesta: InserisciRichiestaUsoInLineaVO) {
    var form: FormData = new FormData();

    if (richiesta.files.length > 0) {
      var i = 0;
      for (var file of richiesta.files) {
        form.append("file" + i, file.file);
        i++;
      }
    }
    form.append("richiesta", JSON.stringify(richiesta, this.fileRemover));

    return this.http.post<number>(this.urlProcedimenti + '/inserisciRichiestaUsoInLinea', form);
  }



  //************************* DETTAGLIO *************************//

  dettaglioRichiesta(id: number, actionFlag: string) {
    let params = new HttpParams().set('action', actionFlag);
    return this.http.get<DettaglioRichiestaVO>(this.urlProcedimenti + '/dettaglioRichiesta/' + id.toString(), { params });
  }

  //************************* UPDATE *************************//

  modificaRichiesta(richiesta: DettaglioRichiestaVO) {
    var form: FormData = new FormData();

    if (richiesta.files != null && richiesta.files.length > 0) {
      var i = 0;
      for (var file of richiesta.files) {
        if (file.file != null) {
          form.append("file" + i, file.file);
        }
        i++;
      }
    }
    form.append("modificaRichiesta", JSON.stringify(richiesta, this.fileRemover));
    return this.http.post<number>(this.urlProcedimenti + '/modificaRichiesta', form);
  }

  modificaRichiestaSostituzione(richieste: Array<DettaglioRichiestaVO>) {
    var form: FormData = new FormData();
    var j = 0;
    for (var richiesta of richieste) {
      if (richiesta.files != null && richiesta.files.length > 0) {
        var i = 0;
        for (var file of richiesta.files) {
          if (file.file != null) {
            form.append("file" + richiesta.idTipoProcedimento + i, file.file);
            i++;
          }
        }
      }
      form.append("richiesta" + j, JSON.stringify(richiesta, this.fileRemover));
      j++
    }
    return this.http.post<number>(this.urlProcedimenti + '/modificaRichiestaSostituzione', form);
  }

  //************************* TRANSAZIONE AUTOMA *************************//

  getTransizioniAutoma(idProcedimento: number, idStatoIterRichiesta: number, parte?: string): Observable<Array<TransizioneAutomaVO>> {
    let params: HttpParams;
    if (config.isNullOrVoid(parte)) {
      params = new HttpParams().set('idProcedimento', idProcedimento.toString()).append('idStatoIterRichiesta', idStatoIterRichiesta.toString());
    } else {
      params = new HttpParams().set('idProcedimento', idProcedimento.toString()).append('idStatoIterRichiesta', idStatoIterRichiesta.toString()).append('parte', parte.toString());
    }
    return this.http.get<Array<TransizioneAutomaVO>>(this.urlProcedimenti + '/transizioniAutoma', { params });
  }

  avanzaIterRichiesta(dettaglioRichiesta: DettaglioRichiestaVO, transizioneAutoma: TransizioneAutomaVO, notaTransizione: string) {
    var form: FormData = new FormData();
    form.append("dettaglioRichiesta", JSON.stringify(dettaglioRichiesta, this.fileRemover));
    form.append("transizioneAutoma", JSON.stringify(transizioneAutoma));
    form.append("notaTransizione", JSON.stringify(notaTransizione));
    return this.http.post<number>(this.urlProcedimenti + '/avanzaIterRichiesta', form);
  }

  //************************* ELENCO PROCEDIMENTI *************************//

  elencoRichiesta() {
    let params = new HttpParams();
    return this.http.get<Array<ProcedimentiVO>>(this.urlProcedimenti + '/elencoProcedimenti', { params: params });
  }

  filtraElencoRichiesta(filtro: FiltroProcedimentiVO) {
    const body = filtro;
    return this.http.post<Array<ProcedimentiVO>>(this.urlProcedimenti + '/filtraElencoProcedimenti', body);
  }


  //************************* DELETE *************************//

  private eliminaProcedimentoSubject = new ReplaySubject<string>();
  eliminaProcedimento$: Observable<string> = this.eliminaProcedimentoSubject.asObservable();

  eliminaProcedimento(idProcedimento: number) {
    let params = new HttpParams()
      .set('idProcedimento', JSON.stringify(idProcedimento));
    this.http.get<string>(this.urlProcedimenti + '/eliminaProcedimento', { params: params }).subscribe(
      (data) => {
        this.eliminaProcedimentoSubject.next(data);
        return data;
      },
      (error) => {
        CommonsHandleException.authorizationError(error, this.router);
      });
  }

  //************************* UTILITY *************************//

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


}