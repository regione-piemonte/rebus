/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class DepositoVO {
    id: number;
    denominazione: string;
    isPrevalente: boolean;
    telefono: string;
    indirizzo: string;

    constructor(depositoVO: DepositoVO) {
        this.id = depositoVO.id;
        this.denominazione = depositoVO.denominazione;
        this.isPrevalente = depositoVO.isPrevalente;
        this.telefono = depositoVO.telefono;
        this.indirizzo = depositoVO.indirizzo;
    }
}