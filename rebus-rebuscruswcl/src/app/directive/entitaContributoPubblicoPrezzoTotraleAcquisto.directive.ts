/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Directive, Input } from "@angular/core";
import { Validator, AbstractControl, NG_VALIDATORS, ValidatorFn } from "@angular/forms";
import { InserisciAutobusVO } from "../vo/inserisci-autobus-vo";
import { AutobusVO } from "../vo/autobus-vo";


@Directive({
    selector: '[contributoPubblicoAcquisto]',
    providers: [{ provide: NG_VALIDATORS, useExisting: EntitaContributoPubblicoANDPrezzoTotaleAcquisto, multi: true }]
})

export class EntitaContributoPubblicoANDPrezzoTotaleAcquisto implements Validator {
    @Input('autobus') autobus: InserisciAutobusVO;
    @Input('type') type: string;


    validate(control: AbstractControl): { [key: string]: any } {
        if (this.type == 'MIN') {
            let prezzo: number = control.value;
            return (prezzo) ?
                this.verificaPubblicoPrezzoTotaleAcquistoMINEntitaContributo(this.autobus)(control)
                : null;
        }

        else if (this.type == "MAX") {
            let prezzo: number = control.value;
            return (prezzo) ?
                this.verificaEntitaContributoMAXPubblicoPrezzoTotaleAcquisto(this.autobus)(control)
                : null;
        }
        return null;
    }


    verificaPubblicoPrezzoTotaleAcquistoMINEntitaContributo(autobus: InserisciAutobusVO): ValidatorFn {
        var prezzoInt =  Number(this.autobus.prezzoTotAcquisto);
        if (isNaN(prezzoInt)){
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoisString': { value: control.value } };
            }; 
        }

        if (this.autobus.contributoPubblicoAcquisto != null && ~~this.autobus.prezzoTotAcquisto < ~~this.autobus.contributoPubblicoAcquisto) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoMINcontributoPubblicoAcquisto': { value: control.value } };
            };
        }
        else return (control: AbstractControl): { [key: string]: any } => {
            return null;
        };
    }

    verificaEntitaContributoMAXPubblicoPrezzoTotaleAcquisto(autobus: InserisciAutobusVO): ValidatorFn {
        var prezzoInt =  Number(this.autobus.contributoPubblicoAcquisto);
        if (isNaN(prezzoInt)){
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoisString': { value: control.value } };
            }; 
        }
        if (this.autobus.prezzoTotAcquisto != null && ~~this.autobus.contributoPubblicoAcquisto > ~~this.autobus.prezzoTotAcquisto) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'contributoPubblicoAcquistoMAXPrezzoTotAcquisto': { value: control.value } };
            };
        }
        else return (control: AbstractControl): { [key: string]: any } => {
            return null;
        };
    }
}


// ------------ MODIFICA ------------

@Directive({
    selector: '[contributoPubblicoAcquistoModifica]',
    providers: [{ provide: NG_VALIDATORS, useExisting: EntitaContributoPubblicoANDPrezzoTotaleAcquistoModifica, multi: true }]
})
export class EntitaContributoPubblicoANDPrezzoTotaleAcquistoModifica implements Validator {
    @Input('autobus') autobus: AutobusVO;
    @Input('type') type: string;


    validate(control: AbstractControl): { [key: string]: any } {
        if (this.type == 'MIN') {
            let prezzo: string = control.value;
            return (prezzo) ?
                this.verificaPubblicoPrezzoTotaleAcquistoMINEntitaContributoModifica(this.autobus)(control)
                : null;
        }

        else if (this.type == "MAX") {
            let prezzo: string = control.value;
            return (prezzo) ?
                this.verificaEntitaContributoMAXPubblicoPrezzoTotaleAcquistoModifica(this.autobus)(control)
                : null;
        }
        return null;
    }


    verificaPubblicoPrezzoTotaleAcquistoMINEntitaContributoModifica(autobus: AutobusVO): ValidatorFn {
        var prezzoInt =  Number(this.autobus.prezzoTotAcquisto);
        if (isNaN(prezzoInt)){
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoisString': { value: control.value } };
            }; 
        }
        let contrPubblicoAcqu: Number = this.autobus.contributoPubblicoAcquisto != null ? new Number(new String(this.autobus.contributoPubblicoAcquisto).replace(",", ".")) : null;
        let prezzoTotAcquisto: Number = this.autobus.prezzoTotAcquisto != null ? new Number(new String(this.autobus.prezzoTotAcquisto).replace(",", ".")) : null;
        if (contrPubblicoAcqu != null && prezzoTotAcquisto < contrPubblicoAcqu) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoMINContributoPubblicoAcquistoModifica': { value: control.value } };
            };
        }
        else return (control: AbstractControl): { [key: string]: any } => {
            return null;
        };
    }

    verificaEntitaContributoMAXPubblicoPrezzoTotaleAcquistoModifica(autobus: AutobusVO): ValidatorFn {
        var prezzoInt =  Number(this.autobus.contributoPubblicoAcquisto);
        if (isNaN(prezzoInt)){
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'prezzoTotAcquistoisString': { value: control.value } };
            }; 
        }
        let contrPubblicoAcqu: Number = this.autobus.contributoPubblicoAcquisto != null ? new Number(new String(this.autobus.contributoPubblicoAcquisto).replace(",", ".")) : null;
        let prezzoTotAcquisto: Number = this.autobus.prezzoTotAcquisto != null ? new Number(new String(this.autobus.prezzoTotAcquisto).replace(",", ".")) : null;

        if (prezzoTotAcquisto != null && contrPubblicoAcqu > prezzoTotAcquisto) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'contributoPubblicoAcquistoMAXPrezzoTotAcquistoModifica': { value: control.value } };
            };
        }
        else return (control: AbstractControl): { [key: string]: any } => {
            return null;
        };
    }
}



