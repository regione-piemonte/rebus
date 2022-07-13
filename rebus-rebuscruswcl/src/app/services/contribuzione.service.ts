/***************************************************
Copyright Regione Piemonte - 2022
 *SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/Observable";
import { AttoAssegnazioneRisorseVo } from "../vo/contribuzione/atto-assegnazione-risorse-vo";
import { ContribuzioneCompletaVO } from "../vo/contribuzione/contribuzione-completa-vo";
import { FonteFinanziamentoVO } from "../vo/contribuzione/fonte-finanziamento-vo";
import { TipoDocumentoQuietanzaVO } from "../vo/contribuzione/tipo-documento-quietanza-vo";
import { TipoSostituzioneVO } from "../vo/contribuzione/tipo-sostituzione-vo";
import { VoceDiCostoContribuzioneVO } from "../vo/contribuzione/voce-costo-contribuzione-vo";
import { FiltroContribuzioneVO } from "../vo/filtro-contribuzione-vo";
import { InserisciRichiestaVO } from "../vo/inserisci-richiesta.vo";

@Injectable()
export class ContribuzioneService {

    private urlContribuzione: string = "/parcobustplweb/restfacade/contribuzione";

    primoTelaio: string;
    idProcedimento: number;
    idVariazAutobus: number;

    constructor(private http: HttpClient) {

    }

    //************************* UTILITY *************************//

    resetFields() {
        this.primoTelaio = undefined;
        this.idProcedimento = undefined;
        this.idVariazAutobus = undefined;
    }

    checkFinalStateIter(idStato: number): Observable<any> {
        let params = new HttpParams().set('idStato', JSON.stringify(idStato))
        return this.http.get<any>(this.urlContribuzione + "/checkFinalStateIter", { params: params });
    }

    //************************* GET *************************//

    //Get DropDown Voci costo contribuazione
    getAllVoceCostoContribuzione(): Observable<VoceDiCostoContribuzioneVO[]> {
        return this.http.get<VoceDiCostoContribuzioneVO[]>(this.urlContribuzione + "/getAllVoceCostoContribuzione");
    }

    //Get DropDown Atto assegnazione
    getAllAttoAssegnazioneRisorse(): Observable<AttoAssegnazioneRisorseVo[]> {
        return this.http.get<AttoAssegnazioneRisorseVo[]>(this.urlContribuzione + "/getAllAttoAssegnazioneRisorse");
    }

    //Get DropDown tipo documento quietanza
    getAllTipoDocumentoQuietanza(): Observable<TipoDocumentoQuietanzaVO[]> {
        return this.http.get<TipoDocumentoQuietanzaVO[]>(this.urlContribuzione + "/getAllTipoDocumentoQuietanza");
    }

    //Get DropDown tipo sostituzione
    getAllTipoSostituzione(): Observable<TipoSostituzioneVO[]> {
        return this.http.get<TipoSostituzioneVO[]>(this.urlContribuzione + "/getAllTipoSostituzione");
    }

    //Get DropDown tipo sostituzione
    getAllFonteFinanziamentoByIdAttoAssegnazione(idAttoAssegnazione: number): Observable<FonteFinanziamentoVO[]> {
        let params = new HttpParams().set('idAttoAssegnazione', JSON.stringify(idAttoAssegnazione))
        return this.http.get<FonteFinanziamentoVO[]>(this.urlContribuzione + "/getAllFonteFinanziamentoByIdAttoAssegnazione", { params: params });
    }

    //Get DropDown fonte di finanziamento
    getAllFonteFinanziamento(): Observable<FonteFinanziamentoVO[]> {
        return this.http.get<FonteFinanziamentoVO[]>(this.urlContribuzione + "/getAllFonteFinanziamento");
    }
    getContribuzioneCompletaByIdProcedimento(idProcedimento: number): Observable<ContribuzioneCompletaVO> {
        let params = new HttpParams().set('idProcedimento', JSON.stringify(idProcedimento))
        return this.http.get<ContribuzioneCompletaVO>(this.urlContribuzione + "/getContribuzioneCompletaByIdProcedimento", { params: params });
    }

    getContribuzioneCompletaById(idContribuzione: number): Observable<ContribuzioneCompletaVO> {
        let params = new HttpParams().set('idContribuzione', JSON.stringify(idContribuzione))
        return this.http.get<ContribuzioneCompletaVO>(this.urlContribuzione + "/getContribuzioneCompletaById", { params: params });
    }

    getDocContribuzione(idDoc: number) {
        let params = new HttpParams().set('idDoc', JSON.stringify(idDoc))
        return this.http.get(this.urlContribuzione + "/getDocContribuzione", { params: params, responseType: 'blob' }).map(
            (res) => {
                return new Blob([res], { type: 'application/pdf' })
            });
    }

    getTelaioByIdProcedimento(idProcedimento: number): Observable<any> {
        let params = new HttpParams().set('idProcedimento', JSON.stringify(idProcedimento))
        return this.http.get<any>(this.urlContribuzione + "/getTelaioByIdProcedimento", { params: params });
    }

    getTelaiVeicoloDaSostituire(): Observable<any> {
        return this.http.get<any>(this.urlContribuzione + "/getTelaiVeicoloDaSostituire");
    }

    //************************* INSERT *************************//

    insertContribuzione(contribuzione: ContribuzioneCompletaVO, richiesta: InserisciRichiestaVO): Observable<number> {
        var form = new FormData();
        form.append('contribuzione', JSON.stringify(contribuzione))
        form.append('richiesta', JSON.stringify(richiesta));
        return this.http.post<number>(this.urlContribuzione + "/inserisciContribuzione", form);
    }

    //************************* UPDATE *************************//

    updateContribuzione(contribuzione: ContribuzioneCompletaVO, listOfDeletedIdVociCosto: number[], listOfDeletedIdDatiFattura: number[], listOfDeletedIdDatiBonifico: number[]) {
        var form = new FormData();
        form.append('contribuzione', JSON.stringify(contribuzione))
        form.append('listaVociCostoDaEliminare', JSON.stringify(listOfDeletedIdVociCosto));
        form.append('listaDatiFatturaDaEliminare', JSON.stringify(listOfDeletedIdDatiFattura));
        form.append('listaDatiBonificoDaEliminare', JSON.stringify(listOfDeletedIdDatiBonifico));
        return this.http.post<number>(this.urlContribuzione + "/updateContribuzione", form);
    }

    //************************* UPDATE *************************//

    elencoContribuzione() {
        let params = new HttpParams();
        return this.http.get<Array<FiltroContribuzioneVO>>(this.urlContribuzione + "/elencoContribuzione", { params: params });
    }



    filtraElencoContribuzione(filtro: FiltroContribuzioneVO) {
        const body = filtro;
        return this.http.post<Array<FiltroContribuzioneVO>>(this.urlContribuzione + "/filtraElencoContribuzione", body);
    }

}