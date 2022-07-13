/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-02 11:09:35  
 */
import { Router } from '@angular/router';
import { MessageRestError, ErrorRest, TypeErrorRest } from '../class/error-rest';
import * as config from '../globalparameter';


/**
 * SE UNAUTHORIZED
 * estraggo messaggio e lo mostro
 * SE ALTRO ERRORE
 * controllo se presente lo stato e lo mostro . Altrimenti messaggio generico 
 */

export class CommonsHandleException {

    //HOMEPAGE
    public static handleBlockingError(err: any, router: Router): void {
        //UNAUTHORIZED
        if (err.status == 401) {
            let object = null;
            try {
                if (typeof (err.error) === 'string' || err.error instanceof String)
                    object = JSON.parse(err.error);
                else
                    object = err.error;
            } catch (e) {
                console.log('eccezione in parse json:' + e);
                router.navigate(['/error'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
            }
            let exception = object['exception'];
            let mes = MessageRestError.UNAUTHORIZED
            if (exception.hasOwnProperty('message') && exception.message != null) {
                mes = object['message'];
            }
            console.log(`Backend returned code ${err.status}, message was: ${err.message}`);
            router.navigate(['/error'], { queryParams: { message: mes }, skipLocationChange: true });

        }
        else {
            if (this.isTimeOutError(err.name))
                router.navigate(['/error'], { queryParams: { message: MessageRestError.TIMEOUT }, skipLocationChange: true });
            if (this.isClientSideErrorOrNetworkError(err))
                router.navigate(['/error'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
            else {
                let object = null;
                try {
                    if (typeof (err.error) === 'string' || err.error instanceof String)
                        object = JSON.parse(err.error);
                    else
                        object = err.error;
                } catch (e) {
                    console.log('eccezione in parse json:' + e);
                    router.navigate(['/error'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
                }

                console.log(`Backend returned code ${err.status}, body was: ${err.message}`);
                let exception = null;
                try {
                    exception = object['exception'];
                } catch (e) {
                    console.log("NON SI TRATTA DI UN ECCEZIONE BE GESTITA");
                }
                let codice = null;
                let mes = MessageRestError.GENERIC
                if (exception != null && exception.hasOwnProperty('codice') && exception.codice != null) {
                    codice = exception['codice'];
                    mes = object['message'];
                }
                router.navigate(['/error'], { queryParams: { message: mes }, skipLocationChange: true });
            }
        }

    }

    //ESTRAE MESSAGGIO BE E LO MOSTRA SOLO SE POSSIEDE CODICE
    public static handleNotBlockingError(err: any): ErrorRest {
        if (err.status == 401) {
            let object = null;
            try {
                if (typeof (err.error) === 'string' || err.error instanceof String)
                    object = JSON.parse(err.error);
                else
                    object = err.error;
            } catch (e) {
                console.log('eccezione in parse json:' + e);
                return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
            }
            let exception = object['exception'];
            let mes = MessageRestError.UNAUTHORIZED
            if (exception.hasOwnProperty('message') && exception.message != null) {
                mes = object['message'];
            }
            console.log(`Backend returned code ${err.status}, message was: ${err.message}`);
            return new ErrorRest(TypeErrorRest.UNAUTHORIZED, mes);
        }
        else {
            if (this.isTimeOutError(err.name))
                return new ErrorRest(TypeErrorRest.TIMEOUT, MessageRestError.TIMEOUT);
            else if (this.isClientSideErrorOrNetworkError(err))
                return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
            else {
                //BE ERROR 
                let object = null;
                try {
                    if (typeof (err.error) === 'string' || err.error instanceof String)
                        object = JSON.parse(err.error);
                    else
                        object = err.error;
                } catch (e) {
                    console.log('eccezione in parse json:' + e);
                    return new ErrorRest(TypeErrorRest.SCONOSCIUTO, MessageRestError.GENERIC);
                }
                console.log(`Backend returned code ${err.status}, body was: ${err.message}`);
                let exception = null;
                try {
                    exception = object['exception'];
                } catch (e) {
                    console.log("NON SI TRATTA DI UN ECCEZIONE BE GESTITA");
                }
                let codice = null;
                let message = MessageRestError.GENERIC
                if (exception != null && exception.hasOwnProperty('codice') && exception.codice != null) {
                    codice = exception['codice'];
                    message = object['message'];
                }

                return new ErrorRest(TypeErrorRest.OK, message, codice);
            }
        }

    }


    private static isClientSideErrorOrNetworkError(err: any) {
        if (err.error instanceof Error) {
            console.log('An error occurred:', err.error.message);
            return true;
        }
        else false;
    }

    private static isTimeOutError(err: any) {
        if (err == "TimeoutError") {
            console.log('TIMEOUT ERROR WITH MESSAGE:', err);
            return true;
        }
        else false;
    }

    public static authorizationError(error: any, router: Router, path?: string, id?: number) {
        let messageError;
        let messageCode

        if (!config.isNullOrVoid(error.error.exception)) {
            messageError = error.error.exception.message;
            messageCode = +error.error.exception.codice
        } else {
            messageError = error.statusText;
            messageCode = +error.status;
        }

        if (error.status === 401) {// Utente non autorizzato
            router.navigate(['/error'], { queryParams: { message: error.error.message }, skipLocationChange: true });
            // schermata di errore
            return;
        } else if (messageCode === 404) {// Dato non trovato
            router.navigate(['/error'], { queryParams: { message: messageError }, skipLocationChange: true });
            // schermata di errore
            return;
        }else if (messageCode === 422) {
            // redirect al dettaglio perche' non abilitato alla modifica 
            // commentato lato BE ancora non funzionante
            if (!config.isNullOrVoid(path)) {
                // se vengono passati sia il path che l'id effettua la redirect 
                if (!config.isNullOrVoid(id)) {
                    router.navigate([path + id]);
                } else {
                    router.navigate([path]);
                }
                return;
                // redirect
            } else {
                this.handleNotBlockingError(error);
                return;
            }
        } else {
            router.navigate(['/error'], { queryParams: { message: messageError }, skipLocationChange: true });
            // schermata di errore
            console.error(error);
            return;
        }

    }

}