/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { BacinoVO, TipoAffidamentoVO, ModalitaAffidamentoVO, TipologiaServizioVO, AmbitoServizioVO, TipoDocumentoVO, AziendaMandatariaVO, TipoSostituzioneVO } from '../vo/drop-down-menu-vo';
import { InserisciContrattoVO } from '../vo/inserisci-contratto-vo';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { ContrattoVO } from '../vo/contratto-vo';
import { AmbTipServizioVO } from '../vo/amb-tip-servizio-vo';
import { ContrAmbTipSerVO } from '../vo/contr-amb-tip-serv-vo';
import { map } from 'rxjs/operators';
import { SoggettoSubentroVO } from '../vo/soggetto-subentro-vo';
import { SoggettoCoinvoltoVO } from "../vo/soggetto-coinvolto-vo";



@Injectable()
export class ContrattoService {

    urlGlobal: string = "/anagrafichetplweb/restfacade/contratto";

    constructor(
        private http: HttpClient,
        private router: Router,
    ) { }

    //------------------------- GET ------------------------------------
    getBacini(): Observable<Array<BacinoVO>> {
        let url: string = this.urlGlobal + '/bacini';
        return this.http.get<Array<BacinoVO>>(url);
    }
    getTipiAffidamento(): Observable<Array<TipoAffidamentoVO>> {
        let url: string = this.urlGlobal + '/tipiAffidamento';
        return this.http.get<Array<TipoAffidamentoVO>>(url);
    }
    getModalitaAffidamento(): Observable<Array<ModalitaAffidamentoVO>> {
        let url: string = this.urlGlobal + '/modalitaAffidamento';
        return this.http.get<Array<ModalitaAffidamentoVO>>(url);
    }
    getTipologieServizio(): Observable<Array<TipologiaServizioVO>> {
        let url: string = this.urlGlobal + '/tipologieServizio';
        return this.http.get<Array<TipologiaServizioVO>>(url);
    }

    getAmbitoTipoServizio(): Observable<Array<AmbTipServizioVO>> {
        let url: string = this.urlGlobal + '/ambitoTipoServizio';
        return this.http.get<Array<AmbTipServizioVO>>(url);
    }
    getContrattiAmbitoTipSerVO(id: number): Observable<Array<ContrAmbTipSerVO>> {
        let url: string = this.urlGlobal + '/contrattiAmbitiTipologiaServizio';
        let params = new HttpParams().set('idContratto', id.toString());
        return this.http.get<Array<ContrAmbTipSerVO>>(url, { params: params });
    }

    getAmbitiServizioByIdTipologiaServizio(id: number): Observable<Array<AmbitoServizioVO>> {
        let url: string = this.urlGlobal + '/ambitiServizioByIdTipologiaServizio';
        let params = new HttpParams().set('idTipologiaServizio', id.toString());
        return this.http.get<Array<AmbitoServizioVO>>(url, { params: params });
    }

    getDescrizioneAmbTipServiziobyId(id: number): Observable<string> {
        let url: string = this.urlGlobal + '/descrizioneAmbTipServiziobyId';
        let params = new HttpParams().set('idAmbTipServizio', id.toString());
        return this.http.get<string>(url, { params: params, responseType: 'text' as 'json' });
    }
    getTipiDocumento(): Observable<Array<TipoDocumentoVO>> {
        let url: string = this.urlGlobal + '/tipiDocumento';
        return this.http.get<Array<TipoDocumentoVO>>(url);
    }

    getAziendaMandataria(id: number): Observable<Array<AziendaMandatariaVO>> {
        let url: string = this.urlGlobal + '/aziendaMandataria';
        if (id) {
            let params = new HttpParams().set('idContratto', id.toString());
            return this.http.get<Array<AziendaMandatariaVO>>(url, { params: params });
        }
        return this.http.get<Array<AziendaMandatariaVO>>(url);
    }

    getTipiSostituzione(): Observable<Array<TipoSostituzioneVO>> {
        let url: string = this.urlGlobal + '/tipiSostituzione';
        return this.http.get<Array<TipoSostituzioneVO>>(url);
    }

    getSoggettiSubentro(idContratto: number, idTipoSoggContraente: number): Observable<Array<SoggettoSubentroVO>> {
        let url: string = this.urlGlobal + '/soggettiSubentro';
        let params = new HttpParams().set('idContratto', idContratto.toString()).set('idTipoSoggContraente', idTipoSoggContraente.toString());
        return this.http.get<Array<SoggettoSubentroVO>>(url, { params: params });
    }

    getContenutoDocumentoById(idContratto: number, idDocumento: number) {
        let params = new HttpParams().set('idContratto', idContratto.toString()).set("idDocumento", idDocumento.toString());
        let url: string = this.urlGlobal + '/getContenutoDocumentoById';
        return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            }
        ));
    }


    //------------------------- INSERT ------------------------------------ 

    inserisciContratto(contratto: InserisciContrattoVO) {
        let url: string = this.urlGlobal + '/inserisciContratto';
        var form: FormData = new FormData();
        if (contratto.files.length > 0) {
            var i = 0;
            for (var file of contratto.files) {
                form.append("file" + i, file.file);
                i++;
            }
        }
        form.append("contratto", JSON.stringify(contratto));

        return this.http.post<number>(url, form);
    }

    creaRinnovoContratto(idContratto: number) {
        let url: string = this.urlGlobal + '/creaRinnovoContratto';
        return this.http.post<number>(url, idContratto);
    }


    //------------------------- SELECT ------------------------------------

    //Dettaglio contratto
    private dettaglioContrattoSubject = new Subject<ContrattoVO>();
    dettaglioContratto$: Observable<ContrattoVO> = this.dettaglioContrattoSubject.asObservable();

    dettaglioContratto(id: number, actionFlag: string) {
        let url: string = this.urlGlobal + '/dettaglioContratto/' + id.toString()
        //var url: string = '/anagrafichetplweb/restfacade/contratto/'.concat(id.toString());
        let params = new HttpParams().set('action', actionFlag);
        this.http.get<ContrattoVO>(url, { params }).subscribe(
            (data) => {
                this.dettaglioContrattoSubject.next(data);
                return data;
            },
            (err) => {
                CommonsHandleException.authorizationError(err, this.router);
            });
    }

    //------------------------- UPDATE ------------------------------------
    modificaContratto(contratto: ContrattoVO) {
        let url: string = this.urlGlobal + '/modificaContratto';
        var form: FormData = new FormData();
        if (contratto.allegati.length > 0) {
            var i = 0;
            for (var file of contratto.allegati) {
                form.append("file" + i, file.file);
                i++;
            }
        }
        form.append("modificaContratto", JSON.stringify(contratto));
        return this.http.post<number>(url, form);
    }



    //------------------------- FILTER  ------------------------------------
    filtraSoggettiCoinvolti(idContratto: number, dataFiltro: Date): Observable<Array<SoggettoCoinvoltoVO>> {
        let url: string = this.urlGlobal + '/filtraSoggettiCoinvolti';
        let var1 = dataFiltro.toLocaleDateString();
        let params = new HttpParams().set('idContratto', idContratto.toString()).set('dataFiltro', var1.toString());
        return this.http.get<Array<SoggettoCoinvoltoVO>>(url, { params: params });
    }

}
