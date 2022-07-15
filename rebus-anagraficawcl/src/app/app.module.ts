/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
/*Angular module */
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatGridListModule } from '@angular/material/grid-list';

import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
/* module */
import { MaterialModule } from './module/material.module';
/*Angular Component*/
import { AppComponent } from './app.component';
import { UserService } from './services/user.service';
import { ConfigService } from './services/config.service';
import { TimeoutInterceptor } from './services/httpRequestInterceptor';
import { XsrfInterceptor } from './services/httpXSRFInterceptor';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { InserisciComponent } from './components/inserisci/inserisci.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { InserisciContrattoComponent } from './components/inserisci-contratto/inserisci-contratto.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { SoggettoService } from './services/soggetto.service';
import { LuoghiService } from './services/luoghi.service';
import { EnteService } from './services/ente.service';
import { ContrattoService } from './services/contratto.service';
import { DialogComponent } from './components/dialog/dialog.component';
import { ModificaSoggettoComponent } from './components/modifica-soggetto/modifica-soggetto.component';
import { DettaglioSoggettoComponent } from './components/dettaglio-soggetto/dettaglio-soggetto.component';
import { RicercaSoggettoComponent } from './components/ricerca-soggetto/ricerca-soggetto.component';
import { ElencoSoggettoComponent } from './components/elenco-soggetto/elenco-soggetto.component';
import { ElencoSoggettoService } from './services/elenco-soggetto.service';
import { ElencoContrattoService } from './services/elenco-contratto.service';
import { RicercaContrattoComponent } from './components/ricerca-contratto/ricerca-contratto.component'; 
import { ModificaContrattoComponent } from './components/modifica-contratto/modifica-contratto.component';
import { ElencoContrattoComponent } from './components/elenco-contratto/elenco-contratto.component';
import { DettaglioContrattoComponent } from './components/dettaglio-contratto/dettaglio-contratto.component';
import { ContrattoFilterService } from './services/contrattoFilter.service';
import { SoggettoFilterService } from './services/soggettoFilter.service';
import { LogoutComponent } from './components/logout/logout.component';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { ErrorComponent } from './components/error/error.component';
import { ImageDialogComponent } from './components/image-dialog/image-dialog.component';
import { DialogComponentConfirm } from './components/dialog-confirm/dialog-confirm.component';
import { CancellaDialogComponent } from './components/cancelladialog/cancelladialog.component';
import { ExcelContrattoService } from './services/excel-contratto.service';
import { ExcelSoggettoService } from './services/excel-soggetto.service';


// the second parameter 'it' is optional
registerLocaleData(localeIt, 'it');
@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    InserisciComponent,
    NavbarComponent,
    InserisciContrattoComponent,
    DialogComponent,
    ModificaSoggettoComponent,
    DettaglioSoggettoComponent,
    RicercaSoggettoComponent,
    ElencoSoggettoComponent,
    RicercaContrattoComponent,
    ElencoContrattoComponent,
    ModificaContrattoComponent,
    DettaglioContrattoComponent,
    LogoutComponent,
    ErrorComponent,
    ImageDialogComponent,
    DialogComponentConfirm,
    CancellaDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    HttpClientModule,
    MatAutocompleteModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatGridListModule,
  ],
  providers: [ConfigService,
    UserService,
    SoggettoService,
    LuoghiService,
    EnteService,
    ContrattoService,
    ElencoSoggettoService,
    ElencoContrattoService,
    ContrattoFilterService,
    ExcelContrattoService,
    ExcelSoggettoService,
    SoggettoFilterService,
    { provide: HTTP_INTERCEPTORS, useClass: TimeoutInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true },
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
  ],
  bootstrap: [AppComponent],
  entryComponents: [DialogComponent, ImageDialogComponent, DialogComponentConfirm, CancellaDialogComponent],
})
export class AppModule { }
