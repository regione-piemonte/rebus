/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';
import * as config from '../../globalparameter';

@Component({
    selector: 'app-cancelladialog',
    templateUrl: './cancelladialog.component.html',
    styleUrls: ['./cancelladialog.component.css']
  })

  export class CancellaDialogComponent implements OnInit, OnDestroy {
  
    constructor(
      public dialogRef: MatDialogRef<CancellaDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any) { }
  
    onNoClick(): void {
      this.dialogRef.close('KO');
    }

    onConfirmClick(): void {
      this.dialogRef.close('OK');
    }

    ngOnInit() {
      if (config.isNullOrVoid(this.data.showConferma)) {
        this.data.showConferma = true;
      }
    }

    ngOnDestroy() {} 
  
  }