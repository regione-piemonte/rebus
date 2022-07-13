/***************************************************
Copyright Regione Piemonte - 2022
 *SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { ConfigService } from "./config.service";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Router } from "@angular/router";
import { DocumentoVO } from "../vo/documento-vo";
import { CommonsHandleException } from "../commons/commons-handle-exception";
import { Injectable } from "@angular/core";
import { ReplaySubject } from "rxjs";
import { Observable } from "rxjs/Observable";


@Injectable()
export class DocumentService {

    constructor(
        private http: HttpClient,
        private config: ConfigService,
        private router: Router
    ) { }


    //variabile elenco documento
    private elencoDocumentoSubject = new ReplaySubject<any>();
    elencoDocumento$: Observable<Array<any>> = this.elencoDocumentoSubject.asObservable();
    private elencoDocumentoContribuzioneSubject = new ReplaySubject<any>();
    elencoDocumentoContribuzione$: Observable<Array<any>> = this.elencoDocumentoContribuzioneSubject.asObservable();

    elencoDocumento(idContesto: number) {
        var url: string = '/parcobustplweb/restfacade/document/elencoDocumento';
        let params = new HttpParams().set("idContesto", idContesto.toString());
        //Timeout 16 minuti
        return this.http.get<Array<DocumentoVO>>(url, { params: params }).timeout(1000000).subscribe(
            (data) => {
                if (data.length != null) {
                    if (idContesto === 1) {
                        this.elencoDocumentoSubject.next(data);
                    }
                    if (idContesto === 5) {
                        this.elencoDocumentoContribuzioneSubject.next(data);
                    }
                }
                return data;
            },
            (err) => {
                console.error(err);
                CommonsHandleException.handleBlockingError(err, this.router);
            });
    }

    getContenutoDocumentoById(idVarAutobus: number, idTipoDocumento: number) {
        let params = new HttpParams().set('idVarAutobus', idVarAutobus.toString()).set("idTipoDocumento", idTipoDocumento.toString());
        var url: string = "/parcobustplweb/restfacade/document/getContenutoDocumentoById";
        return this.http.get(url, { params: params, responseType: 'blob' }).map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            });
    }

    getContenutoDocumentoByIdProcedimento(idProcedimento: number, idTipoDocumento: number) {
        let params = new HttpParams().set('idProcedimento', idProcedimento.toString()).set("idTipoDocumento", idTipoDocumento.toString());
        var url: string = "/parcobustplweb/restfacade/document/getContenutoDocumentoByIdProcedimento";
        return this.http.get(url, { params: params, responseType: 'blob' }).map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            });
    }

    eliminaAllegatoProcedimentoDb(idProcedimento: number, idTipoDocumento: number): Observable<string> {
        let params = new HttpParams().set('idProcedimento', idProcedimento.toString()).set("idTipoDocumento", idTipoDocumento.toString());
        var url: string = '/parcobustplweb/restfacade/document/eliminaAllegatoProcedimentoDb';
        return this.http.delete<string>(url, { params });
    }

    getContenutoAnteprimaPdf(idProcedimento: number, idStatoProc: number, idTipoProc: number) {
        let params = new HttpParams().set('idProcedimento', idProcedimento.toString()).set("idStatoProc", idStatoProc.toString()).set("idTipoProc", idTipoProc.toString());
        var url: string = "/parcobustplweb/restfacade/document/getContenutoAnteprimaPdf";
        return this.http.get(url, { params: params, responseType: 'blob' }).map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            });
    }
}
