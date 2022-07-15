/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class AllegatoVO {
    public idAllegato: number;
    public idTipoDocumento: number;
    public descrizioneTipoDocumento: string;
    public nomeFile: string;
    public noteFile: string;
    public file: File;
    public dataCaricamento : Date;
    public idAllegatoTmp: number;

}