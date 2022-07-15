/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { MatTableDataSource } from "@angular/material";

//controllo se la variabile è null, undefined e stringa vuota
export function isNullOrVoid(value: any) {
    return value == null || value === "" || value == undefined;
}

// COstanti utilizzate nell'iter in contribuzione
export const PARTE_A = "A";
export const PARTE_B = "B";

// classi/interfaccia utilizzate per i messaggi
export class MessaggiData extends MatTableDataSource<MessaggiDatasource> {
    constructor(data: MessaggiDatasource[]) {
        super(data);
    }
}

export interface MessaggiDatasource {
    idMessaggio?: number;
    fkTipoMessaggio?: number;
    ora?: String;
    messaggio?: String;
    dataCreazione?: Date;
    dataRow?: Date;
    flgLetto?: string;
    fkTipoMessaggioSistema?: number;
    idContestoMess?: number,
}

export class HeaderMessaggi {
    descMess: string;
    idContestoMess: number;
    bold: Boolean
    constructor(descMess: string, idContestoMess: number, bold: Boolean) {
        this.descMess = descMess;
        this.idContestoMess = idContestoMess;
    }
}