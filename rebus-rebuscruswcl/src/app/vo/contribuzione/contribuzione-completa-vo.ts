/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { AssegnazioneRisorseVO } from "./assegnazione-risorse-vo";
import { ContribuzioneVO } from "./contribuzione-vo";
import { CostoFornituraVO } from "./costo-fornitura-vo";
import { DatoFatturaVO } from "./dato-fattura-vo";
import { DatoMessaServizioVO } from "./dato-messa-servizio-vo";
import { DatoSpesaVO } from "./dato-spesa-vo";
import { DocContribuzioneVO } from "./doc-contribuzione-vo";
import { OrdineAcquistoVO } from "./ordine-acquisto-vo";
import { VoceCostoFornituraVO } from "./voce-costo-fornitura-vo";

export class ContribuzioneCompletaVO {
    assegnazioneRisorse: AssegnazioneRisorseVO;
    ordineAcquisto: OrdineAcquistoVO;
    costoFornitura: CostoFornituraVO;
    vociCosto: VoceCostoFornituraVO[];
    datoSpesa: DatoSpesaVO;
    datoMessaServizio: DatoMessaServizioVO;
    contribuzione: ContribuzioneVO;
    datiFattura: DatoFatturaVO[];

    // DOCUMENTI
    documentoContribuzione: DocContribuzioneVO;
    documentoCartaCircolazione: DocContribuzioneVO;
    documentoAttoObbligo: DocContribuzioneVO;
    documentoGaranzia: DocContribuzioneVO;
    documentoAlienazione: DocContribuzioneVO;
    documentoMisureEmissioni: DocContribuzioneVO;

    constructor(){
        this.documentoAttoObbligo = new DocContribuzioneVO();
        this.documentoGaranzia = new DocContribuzioneVO();
    }
}