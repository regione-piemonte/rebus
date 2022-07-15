/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { NativeDateAdapter } from '@angular/material';
import { MatDateFormats } from '@angular/material/core';
import { registerLocaleData } from '@angular/common';


export class DateFormat extends NativeDateAdapter {
    format(date: Date, displayFormat: Object): string {
        if (displayFormat === 'input') {
          let day: string = date.getDate().toString();
          day = +day < 10 ? '0' + day : day;
          let month: string = (date.getMonth() + 1).toString();
          month = +month < 10 ? '0' + month : month;
          let year = date.getFullYear();
          return `${day}-${month}-${year}`;
        }
        return date.toDateString();
      }
}
export const APP_DATE_FORMATS: MatDateFormats = {
    parse: {
      dateInput: { day: 'numeric', month: 'numeric', year: 'numeric' },
    },
    display: {
      dateInput: 'input',
      monthYearLabel: { year: 'numeric', month: 'numeric' },
      dateA11yLabel: { day: 'numeric' , month: 'long', year: 'numeric'
      },
      monthYearA11yLabel: { year: 'numeric', month: 'long' },
    }
  };