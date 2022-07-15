/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfigService } from './services/config.service';
import { DestroySubscribers } from './decorator/destroy-subscribers';
import { AuthorizationRoles } from './class/authorization-roles';
import { UserInfo } from './vo/funzionario-vo';
import { UserService } from './services/user.service';
import { AziendaVO } from './vo/azienda-vo';
import { Ruolo } from './vo/ruolo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@DestroySubscribers()
export class AppComponent implements OnInit {

  funzionario: UserInfo;
  aziendaSelected: AziendaVO;
  ruoloSelected: Ruolo;
  loading: boolean;
  public subscribers: any = {};
  public src: string;
  public rebusPath: string;
  private timestampLastCall: number;
  ruoloConfirmed: boolean = false;
  disabledButtonConfermaRuolo: boolean = false;

  constructor(private fb: FormBuilder,
    private userService: UserService,
    private router: Router, private configService: ConfigService) { }

  ngOnInit(): void {
    this.loading = true;
    window.setInterval(() => {
      this.loading = false;
    }, 4000);
    this.subscribers.verificaFunzionarioData = this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      if (this.funzionario.ruolo != null) {
        this.ruoloConfirmed = true;
      }
      if (this.funzionario.aziende != null && this.funzionario.aziende.length > 1) {
        this.funzionario.aziende = this.funzionario.aziende.filter((a, i, arr) => arr.findIndex(t => t.id === a.id) === i); // filtro duplicati
        if (this.funzionario.idAzienda != null) {
          this.aziendaSelected = this.funzionario.aziende.find(a => a.id == this.funzionario.idAzienda);
          this.confermaLogin();
        }
      }
      this.loading = false;
    });
    this.subscribers.verificaFunzionario = this.userService.verificaUtente();
    this.src = this.configService.getSrcEnviroment();
    this.rebusPath = this.configService.getRebusPath();

  }

  assertMenu(menuItemName: string) {
    if (this.funzionario == null) {
      return null;
    }

    switch (menuItemName) {
      case "anagraficaSoggetti":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_SOGGETTI);
      case "anagraficaContratti":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_CONTRATTI);
      case "altriApplicativi":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS);
      case "ricercaSoggetto":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI);
      case "inserimentoSoggetto":
        return this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_SOGGETTO);
      case "ricercaContratto":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_CONTRATTI);
      case "inserimentoContratto":
        return this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_CONTRATTO);
      case "anagraficaBus":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS);
      default:
        return false;
    }

  }

  confermaLogin() {
    this.userService.setIdAziendaUtente(this.aziendaSelected.id).subscribe(data => {
      this.funzionario.idAzienda = this.aziendaSelected.id;
      this.funzionario.aziendaDesc = this.aziendaSelected.descrizione;
      this.funzionario.aziende = null;
      this.timestampLastCall = Date.now();
    });
  }

  confermaRuolo() {
    this.disabledButtonConfermaRuolo = true;
    this.userService.setRuoloUtente(this.ruoloSelected).subscribe(data => {
      if (data) {
        this.funzionario = data;
        this.funzionario.ruolo = this.ruoloSelected;
        this.ruoloConfirmed = true;
        this.disabledButtonConfermaRuolo = false;
        this.userService.verificaUtente();
      }
    });
  }

}