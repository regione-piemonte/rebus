/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Injectable } from '@angular/core';


/**
 * Servizio di configurazione
 */
@Injectable()
export class ConfigService {

    /**
     * server del backend nel formato http://server:port
     */
    getBEServer(): string {
        return ENV_PROPERTIES.beServer;
        /*return process.env.ENV_PARAMS.beServer*/;
    }

    /**
     * Url di logout da SSO
     */
    getSSOLogoutURL(): string {
        return ENV_PROPERTIES.shibbolethSSOLogoutURL;
    }


    getTimeout(): number {
        return ENV_PROPERTIES.timeout;
    }

    getSrcEnviroment(): string {
        return ENV_PROPERTIES.srcEnviroment;
    }

    getRebusPath(): string {
        return ENV_PROPERTIES.rebusPath;
    }
}