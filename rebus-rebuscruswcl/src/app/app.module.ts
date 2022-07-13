/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*Angular module */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http'
import { HttpModule } from "@angular/http";
import { MatIconModule, DateAdapter, MatPaginatorModule, MatSortModule } from '@angular/material';
import { MatDialogModule } from '@angular/material';
/*routing module*/
import { RouterModule, Routes, ExtraOptions } from '@angular/router';
/* module */
import { MaterialModule } from './module/material.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule, MatCheckboxModule, MatTableModule } from '@angular/material';
/*Angular service */
import { ConfigService } from './services/config.service';
/*Angular component  */
import { AppComponent } from './app.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { ScaricaComponent } from './components/scarica/scarica.component';
import { LogoutComponent } from './components/logout/logout.component';
import { CancellaDialogComponent } from './components/cancelladialog/cancelladialog.component';
import { ElencoComponent } from './components/elenco/elenco.component';
import { DettaglioComponent } from './components/dettaglio/dettaglio.component';
import { ModificaComponent } from './components/modifica/modifica.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RicercaAutobusAziendaComponent } from './components/ricercaautobus-azienda/ricercaautobus.component';
import { ErrorComponent } from './components/error/error.component';
import { TimeoutInterceptor } from './services/httpRequestInterceptor';
import { XsrfInterceptor } from './services/httpXSRFInterceptor';
import { UploadComponent } from './components/upload/upload.component';
import { FileDropModule } from 'ngx-file-drop';
import { InserisciComponent } from './components/inserisci/inserisci.component';
import { DateFormat } from './dateformat/dateFormat';
import { DatetoStringpiperebus } from './pipe/date-pipe-rebus';

import { EntitaContributoPubblicoANDPrezzoTotaleAcquisto, EntitaContributoPubblicoANDPrezzoTotaleAcquistoModifica } from './directive/entitaContributoPubblicoPrezzoTotraleAcquisto.directive';
import { UserService } from './services/user.service';
import { UtilityService } from './services/utility.service';
import { FilterUtilsService } from './services/filter.service';
import { AutobusService } from './services/autobus.service';
import { DocumentService } from './services/document.service';
import { MessaggiService } from './services/messaggi.service';
import { DettaglioMessaggiComponent } from './components/dettaglioMessaggi/dettaglioMessaggi.component';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { TitleCasePipe } from './pipe/case-conversion-pipes';
import { ElencoRichiestaComponent } from './components/elenco-richiesta/elenco-richiesta.component';
import { RicercaRichiestaComponent } from './components/ricerca-richiesta/ricerca-richiesta.component';
import { InserisciRichiestaComponent } from './components/inserisci-richiesta/inserisci-richiesta.component';
import { ProcedimentoService } from './services/procedimento.service';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { RichiestaFilterService } from './services/contrattoFilter.service';
import { ModificaRichiestaComponent } from './components/modifica-richiesta/modifica-richiesta.component';
import { DettaglioRichiestaComponent } from './components/dettaglio-richiesta/dettaglio-richiesta.component';
import { LunghezzaAutobusModifica } from './directive/lunghezzaAutobus.directive';
import { InserisciRichiestaSostituzioneComponent } from './components/inserisci-richiesta-sostituzione/inserisci-richiesta-sostituzione.component';
import { ModificaRichiestaSostituzioneComponent } from './components/modifica-richiesta-sostituzione/modifica-richiesta-sostituzione.component';
import { DettaglioRichiestaSostituzioneComponent } from './components/dettaglio-richiesta-sostituzione/dettaglio-richiesta-sostituzione.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { RiferimentiNormativiComponent } from './components/riferimenti-normativi/riferimenti-normativi.component';
import { RiferimentiNormativiService } from './services/riferimenti-normativi.service';
import { ElencoRiferimentiComponent } from './components/elenco-riferimenti/elenco-riferimenti.component';
import { InserisciRichiestaUsoInLineaComponent } from './components/inserisci-richiesta-uso-in-linea/inserisci-richiesta-uso-in-linea.component';
import { ModificaRichiestaUsoInLineaComponent } from './components/modifica-richiesta-uso-in-linea/modifica-richiesta-uso-in-linea.component';
import { DettaglioRichiestaUsoInLineaComponent } from './components/dettaglio-richiesta-uso-in-linea/dettaglio-richiesta-uso-in-linea.component';
import { NavbarFilterContext } from './services/navbarFilterContext.service';
import { ContribuzioneService } from './services/contribuzione.service';
import {MatGridListModule} from '@angular/material/grid-list';
import { InserisciContribuzioneComponent } from './components/inserisci-contribuzione/inserisci-contribuzione.component';
import { RicercaContribuzioneComponent } from './components/ricerca-contribuzione/ricerca-contribuzione.component';
import { ElencoContribuzioneComponent } from './components/elenco-contribuzione/elenco-contribuzione.component';
import { RendicontazioneFilterService } from './services/rendicontazioneFilter.service';
import { ElencoMessaggiComponent } from './components/elenco-messaggi/elenco-messaggi.component';
import { ZipService } from './services/zip.service';
import { ExcelService } from './services/excel.service';

const appRoutes: Routes = [
  { path: 'home', component: WelcomeComponent},
  { path: 'upload', component: UploadComponent },
  { path: 'download', component: ScaricaComponent },
  { path: 'dettaglioBus/:id', component: DettaglioComponent },
  { path: 'modificaBus/:id', component: ModificaComponent },
  { path: 'ricercabusazienda', component: RicercaAutobusAziendaComponent },
  { path: 'inserisci', component: InserisciComponent },
  { path: 'messaggi/1', component: ElencoMessaggiComponent },
  { path: 'messaggi/0', component: ElencoMessaggiComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'dettaglioMessaggio/:id', component: DettaglioMessaggiComponent },
  { path: 'inserisciRichiesta/:id', component: InserisciRichiestaComponent },
  { path: 'inserisciRichiestaUsoInLinea/:id', component: InserisciRichiestaUsoInLineaComponent},
  { path: 'inserisciRichiestaSostituzione/:id', component: InserisciRichiestaSostituzioneComponent},
  { path: 'modificaRichiesta/:id', component: ModificaRichiestaComponent },
  { path: 'modificaRichiestaUsoInLinea/:id', component: ModificaRichiestaUsoInLineaComponent},
  { path: 'modificaRichiestaSostituzione/:id', component: ModificaRichiestaSostituzioneComponent},
  { path: 'dettaglioRichiesta/:id', component: DettaglioRichiestaComponent },
  { path: 'dettaglioRichiestaUsoInLinea/:id', component: DettaglioRichiestaUsoInLineaComponent },
  { path: 'dettaglioRichiestaSostituzione/:id', component: DettaglioRichiestaSostituzioneComponent },
  { path: 'ricercaProcedimenti', component: RicercaRichiestaComponent }, 
  { path: 'riferimentiNormativi', component: RiferimentiNormativiComponent }, 
  { path: 'inserisciContribuzione/:id', component: InserisciContribuzioneComponent }, 
  { path: 'ricercaContribuzione', component: RicercaContribuzioneComponent }, 
  { path: '**', redirectTo: 'home' }
];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

registerLocaleData(localeIt, 'it');

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ScaricaComponent,
    UploadComponent,
    LogoutComponent,
    CancellaDialogComponent,
    ElencoComponent,
    DettaglioComponent,
    ModificaComponent,
    NavbarComponent,
    RicercaAutobusAziendaComponent,
    ErrorComponent,
    InserisciComponent,
    DatetoStringpiperebus,
    TitleCasePipe,
    EntitaContributoPubblicoANDPrezzoTotaleAcquisto,
    EntitaContributoPubblicoANDPrezzoTotaleAcquistoModifica,
    LunghezzaAutobusModifica,
    ElencoMessaggiComponent,
    DettaglioMessaggiComponent,
    ElencoRichiestaComponent,
    RicercaRichiestaComponent,
    InserisciRichiestaComponent,
    ModificaRichiestaComponent,
    DettaglioRichiestaComponent,
    InserisciRichiestaUsoInLineaComponent,
    ModificaRichiestaUsoInLineaComponent,
    DettaglioRichiestaUsoInLineaComponent,
    InserisciRichiestaSostituzioneComponent,
    ModificaRichiestaSostituzioneComponent,
    DettaglioRichiestaSostituzioneComponent,
    DialogComponent,
    RiferimentiNormativiComponent,
    ElencoRiferimentiComponent,
    InserisciContribuzioneComponent,
    RicercaContribuzioneComponent,
    ElencoContribuzioneComponent
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    HttpModule,
    MatIconModule,
    MatAutocompleteModule,
    MatDialogModule,
    ReactiveFormsModule,
    MaterialModule,
    MatSnackBarModule,
    MatGridListModule,
    MatButtonModule, MatCheckboxModule, MatTableModule, MatPaginatorModule, MatSortModule,
    TextMaskModule,
    RouterModule.forRoot(
      appRoutes,
      configRouter
    ),
    FileDropModule,
  ],
  exports: [
    MatPaginatorModule,
    MatSortModule,
    MatTableModule],
  providers: [ConfigService,
    UserService,
    UtilityService,
    FilterUtilsService,
    NavbarFilterContext,
    AutobusService,
    DocumentService,
    MessaggiService,
    ProcedimentoService,
    RichiestaFilterService,
    RendicontazioneFilterService,
    RiferimentiNormativiService,
    ContribuzioneService,
    ZipService,
    ExcelService,
    
    { provide: HTTP_INTERCEPTORS, useClass: TimeoutInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true },
    //for formatter date
    { provide: DateAdapter, useClass: DateFormat },
    { provide: LOCALE_ID, useValue: 'it-IT' }
  ],
  bootstrap: [AppComponent],
  entryComponents: [CancellaDialogComponent, DialogComponent]
})
export class AppModule { }
