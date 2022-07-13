/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class PortabiciVo {

    public idPortabici?: number;
    public idMisurazione?: number;
    public flagSensoristicaPb?: Boolean;

    public mesiUtilizzoPrevalente?: String;
    public giorniUtilizzoPrevalente?: String;
    public numBiciTrasportabili?: number;
    public totBiciTrasportate?: number;
    public isAllegatoupload?: string;

    constructor() {
    }
} 