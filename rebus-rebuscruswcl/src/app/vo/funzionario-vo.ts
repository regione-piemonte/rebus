/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { AziendaVO } from "./azienda-vo";
import { Ruolo } from "./ruolo";

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-15 11:22:42  */
export class UserInfo {

    constructor(
        public nome: string,
        public cognome: string,
        public codFisc: string,
        public ente: string,
        public aziendaDesc: string,
        public idIride: string,
        public idFunzionario: number,
        public idEnte: number,
        public idAzienda: number,
        public idUtente: number,
        public ruolo: Ruolo,
        public authority: Array<String>,
        public aziende: Array<AziendaVO>,
        public ruoli: Array<Ruolo>
    ) {
    }
}

