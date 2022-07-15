/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { NgModule } from '@angular/core';
import { Routes, RouterModule, ExtraOptions } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { InserisciComponent } from './components/inserisci/inserisci.component';
import { InserisciContrattoComponent } from './components/inserisci-contratto/inserisci-contratto.component';
import { ModificaSoggettoComponent } from './components/modifica-soggetto/modifica-soggetto.component';
import { DettaglioSoggettoComponent } from './components/dettaglio-soggetto/dettaglio-soggetto.component';
import { RicercaSoggettoComponent } from './components/ricerca-soggetto/ricerca-soggetto.component';
import { RicercaContrattoComponent } from './components/ricerca-contratto/ricerca-contratto.component';
import { DettaglioContrattoComponent } from './components/dettaglio-contratto/dettaglio-contratto.component';
import { ModificaContrattoComponent } from './components/modifica-contratto/modifica-contratto.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ErrorComponent } from './components/error/error.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: WelcomeComponent },
  { path: 'inserisciSoggetto', component: InserisciComponent },
  { path: 'inserisciContratto', component: InserisciContrattoComponent },
  { path: 'modificaSoggetto/:id', component: ModificaSoggettoComponent },
  { path: 'modificaContratto/:id', component: ModificaContrattoComponent },
  { path: 'dettaglioSoggetto/:id', component: DettaglioSoggettoComponent },
  { path: 'dettaglioContratto/:id', component: DettaglioContrattoComponent },
  { path: 'ricercaSoggetto', component: RicercaSoggettoComponent },
  { path: 'ricercaContratto', component: RicercaContrattoComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: 'home' },
];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

@NgModule({
  imports: [RouterModule.forRoot(
    routes,
    configRouter
  ),],
  exports: [RouterModule]
})
export class AppRoutingModule { }
