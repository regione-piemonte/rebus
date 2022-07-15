/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export interface DialogData {
    type: string;
    message: string;
    image: any;
}

@Component({
    selector: 'app-dialog',
    templateUrl: './dialog.component.html'
  })
export class DialogComponent {
    constructor(
        public dialogRef: MatDialogRef<DialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

        close(): void {
            this.dialogRef.close();
        }
}