/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class EmissioniVo {

    public idTipoAllestimento?: number;
    public idMisurazione?: number;
    public kmPercorsi?: number;

    public emissioniCo2?: number;
    public emissioniNox?: number;
    public emissioniPm10Numero?: number;
    public emissioniPm10Peso?: number;

    public descrizione?: string;

    constructor() {
    }
} 