/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Directive, Input, Output, EventEmitter } from "@angular/core";
import { Validator, AbstractControl, NG_VALIDATORS, ValidatorFn } from "@angular/forms";
import { AutobusVO } from "../vo/autobus-vo";
import { TipologiaDimensioneVO } from "../vo/tiplogia-dimensione-vo";



// ------------ MODIFICA ------------

@Directive({
    selector: '[lunghezzaAutobusSelectorModifica]',
    providers: [{ provide: NG_VALIDATORS, useExisting: LunghezzaAutobusModifica, multi: true }]
})
export class LunghezzaAutobusModifica implements Validator {
    @Input('autobus') autobus: AutobusVO;
    @Input('tipologiaDimensione') tipologiaDimensione: TipologiaDimensioneVO;
    @Input('tipologiaLoaded') tipologiaLoaded: boolean;

    @Output('ngInit') initEvent: EventEmitter<any> = new EventEmitter();

    ngOnInit() {
        var intervalId = setInterval(() => {
            if (this.tipologiaLoaded) {
                clearInterval(intervalId);
                this.initEvent.emit()
            }
        }, 100);

    }

    validate(control: AbstractControl): { [key: string]: any } {
        if (this.tipologiaDimensione != null) {
            if (this.tipologiaDimensione.idTipoAllestimento != 0) {

                let lunghezza: string = control.value;
                return (lunghezza) ?
                    this.verificaLunghezzaAutobusModifica(this.autobus, this.tipologiaDimensione)(control)
                    : null;
            }

        }

        return null;
    }


    verificaLunghezzaAutobusModifica(autobus: AutobusVO, allestimento: TipologiaDimensioneVO): ValidatorFn {
        var lunghezzaNumb = Number(this.autobus.lunghezza);
        if (isNaN(lunghezzaNumb)) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'lunghezzaisString': { value: control.value } };
            };
        }
        if (lunghezzaNumb != null && (lunghezzaNumb < allestimento.lunghezzaMin || lunghezzaNumb > allestimento.lunghezzaMax) && (lunghezzaNumb != 999)) {
            return (control: AbstractControl): { [key: string]: any } => {
                return { 'tooLong': { value: control.value } };
            };
        }
        else return (control: AbstractControl): { [key: string]: any } => {
            return null;
        };
    }


}



