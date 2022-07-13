/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: 'datetostringpiperebus' })
export class DatetoStringpiperebus implements PipeTransform {
    transform(value) {
        if (value){
            let  date:Date= new Date(value);
            let month = String(date.getMonth() + 1);
            let day = String(date.getDate());
            const year = String(date.getFullYear());

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return `${day}/${month}/${year}`;
        }
    }

}