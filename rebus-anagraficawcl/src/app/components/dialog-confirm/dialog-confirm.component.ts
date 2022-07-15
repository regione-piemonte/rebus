/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogData } from '../dialog/dialog.component';


@Component({
    selector: 'app-dialog-confirm',
    templateUrl: './dialog-confirm.component.html'
  })
export class DialogComponentConfirm {
    constructor(
        public dialogRef: MatDialogRef<DialogComponentConfirm>,
        @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

        onNoClick(): void {
            this.dialogRef.close();
          }
}