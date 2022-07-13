/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class DocContribuzioneVO {
    idDocContribuzione: number;
    idTipoDocumento: number;
    nomeFile: string;
    dataCaricamento: Date;
    idUtenteAggiornamento: number;
    documento: any;
    label: string; // label usata nell'HTML come nome del campo
    descEstesa: string; // descrizione usata nell'HTML come descrizione del documento

    constructor() {

    }
}