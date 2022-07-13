/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-01 11:15:01  
*/
import { NgModule } from '@angular/core';
import { } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatGridListModule,
    MatCardModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatTabsModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    MatSortModule,
    MatPaginatorModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatProgressBarModule
} from '@angular/material';
import {CdkTableModule} from '@angular/cdk/table';

@NgModule({
    imports: [
        BrowserAnimationsModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatButtonModule,
        MatGridListModule,
        MatCardModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        MatTabsModule,
        MatListModule,
        MatTableModule,
        MatCheckboxModule,
        MatSortModule,
        MatPaginatorModule,
        MatRadioModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatProgressSpinnerModule,
        CdkTableModule,
        MatExpansionModule,
        MatProgressBarModule
    ],
    exports: [
        BrowserAnimationsModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatButtonModule,
        MatGridListModule,
        MatCardModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        MatTabsModule,
        MatListModule,
        MatTableModule,
        MatCheckboxModule,
        MatSortModule,
        MatPaginatorModule,
        MatRadioModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatProgressSpinnerModule,
        CdkTableModule,
        MatExpansionModule,
        MatProgressBarModule
    ],
})
export class MaterialModule { }