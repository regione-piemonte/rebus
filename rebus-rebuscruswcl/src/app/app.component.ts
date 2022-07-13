/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfigService } from './services/config.service';
import { DestroySubscribers } from './decorator/destroy-subscribers';
import { AuthorizationRoles } from './class/authorization-roles';
import { UserInfo } from './vo/funzionario-vo';
import { UserService } from './services/user.service';
import { MessaggiService } from './services/messaggi.service';
import { AziendaVO } from './vo/azienda-vo';
import { TipoProcedimentoVO } from './vo/extend-vo';
import { Ruolo } from './vo/ruolo';
import { NavbarFilterContext } from './services/navbarFilterContext.service';
import { UtilityService } from './services/utility.service';
import { ProcedimentoService } from './services/procedimento.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@DestroySubscribers()
export class AppComponent implements OnInit {

  funzionario: UserInfo;
  loadingFunzionario: boolean;
  loadingTipologie: boolean;
  aziendaSelected: AziendaVO;
  ruoloSelected: Ruolo;
  public subscribers: any = {};
  public src: string;
  public anagrafichePath: string;
  private timestampLastCall: number;
  private flagAggiornaMessaggi: boolean;
  isLogout: boolean;

  numMessDaLeggere: number;
  isAutorizzatoMessaggistica: boolean;
  isAutorizzatoRendicontazione: boolean;
  links: Array<TipoProcedimentoVO>;
  nuovoProcedimento1: TipoProcedimentoVO;
  nuovoProcedimento4: TipoProcedimentoVO;
  isAutorizzatoRiferimenti: boolean;
  ruoloConfirmed: boolean = false;
  disabledButtonConfermaRuolo: boolean = false;
  filterNavbar: number;



  constructor(private fb: FormBuilder,
    private userService: UserService,
    private procedimentoService: ProcedimentoService,
    private router: Router,
    private configService: ConfigService,
    private utilityService: UtilityService,
    private navbarFilterMessaggi: NavbarFilterContext,
    private messaggiService: MessaggiService) {
    this.flagAggiornaMessaggi = false;
  }

  ngOnInit(): void {
    this.loadingFunzionario = true;
    this.loadingTipologie = true;
    window.setInterval(() => {
      this.loadingFunzionario = false;
      this.loadingTipologie = false;
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
      this.isAutorizzatoRendicontazione = this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE)
      this.isAutorizzatoMessaggistica = this.funzionario.authority.includes(AuthorizationRoles.MESSAGGISTICA);
      this.isAutorizzatoRiferimenti = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_RIFERIMENTI) || this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_RIFERIMENTI)
      this.loadingFunzionario = false;
    });
    this.procedimentoService.getElencoTipologia().subscribe(data => {
      this.nuovoProcedimento1 = new TipoProcedimentoVO();
      this.nuovoProcedimento4 = new TipoProcedimentoVO();
      if (data) {
        this.links = data;
        this.links = this.links.filter(a => a.id != 7);
        let descrizione = this.links.find(a => a.id == 3).descrizione
        this.nuovoProcedimento1.id = 31;
        this.nuovoProcedimento1.descrizione = descrizione + " e " + this.links.find(a => a.id == 1).descrizione;
        this.nuovoProcedimento4.id = 34;
        this.nuovoProcedimento4.descrizione = descrizione + " e " + this.links.find(a => a.id == 4).descrizione;
      }
      this.loadingTipologie = false;
    });
    this.subscribers.verificaFunzionario = this.userService.verificaUtente();
    this.src = this.configService.getSrcEnviroment();
    this.anagrafichePath = this.configService.getAnagrafichePath();
    this.messaggiService.numMessDaLeggere().subscribe(dati => {
      this.numMessDaLeggere = dati;
      this.timestampLastCall = Date.now();
    });
    this.utilityService.getContesti().subscribe(dati => {
      if (dati) {
        this.navbarFilterMessaggi.ElencoContesti = dati;
      }
    });


  }

  aggiornaMessaggi() {
    this.flagAggiornaMessaggi = true;
    this.messaggiService.numMessDaLeggere().subscribe(dati => {
      this.numMessDaLeggere = ~~dati;
      this.timestampLastCall = Date.now();
    });
  }

  assertMenu(menuItemName: string) {
    if (this.funzionario == null) {
      return null;
    }

    switch (menuItemName) {
      case "ricercabusazienda":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS) && this.funzionario.idAzienda != null;
      case "ricercabusente":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS) && this.funzionario.idEnte != null;
      case "upload":
        return (this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS)
          || this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO));
      case "download":
        return this.funzionario.authority.includes(AuthorizationRoles.DOWNLOAD_FILE_XLS);
      case "anagraficaAzienda":
        return this.funzionario.authority.includes(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA);
      case "inserimentoBus":
        return this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS);
      case "modificaBus":
        return this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS);
      case "anagraficaAziende":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_AL_SISTEMA_ANAGRAFICHE) && this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_SOGGETTI);
      case "anagraficaContratti":
        return this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_AL_SISTEMA_ANAGRAFICHE) && this.funzionario.authority.includes(AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_CONTRATTI);
      case "ricercaProcedimenti":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_PROCEDIMENTI);
      case "nuovoProcedimenti":
        return this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_PROCEDIMENTO);
      case "ricercaContribuzione":
        return this.funzionario.authority.includes(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
      case "nuovaContribuzione":
        return this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE);
      default:
        return false;
    }

  }

  goToMessaggi(param: number) {
    //controllo
    this.filterNavbar = 1;
    if (this.navbarFilterMessaggi.InfoFiltro && this.filterNavbar != this.navbarFilterMessaggi.InfoFiltro.id) {
      this.filterNavbar = this.navbarFilterMessaggi.InfoFiltro.id;
    }
    this.router.navigate(['/messaggi/' + param]);
  }
  ngAfterViewChecked() {

    if ((Date.now()) - this.timestampLastCall > 10000) {
      this.timestampLastCall = Date.now();
      this.messaggiService.numMessDaLeggere().subscribe(dati => {
        this.numMessDaLeggere = dati;
        this.timestampLastCall = Date.now();
      });
    }
    if (this.flagAggiornaMessaggi) {
      this.flagAggiornaMessaggi = false;
    }
  }

  confermaLogin() {
    this.userService.setIdAziendaUtente(this.aziendaSelected.id).subscribe(data => {
      this.funzionario.idAzienda = this.aziendaSelected.id;
      this.funzionario.aziendaDesc = this.aziendaSelected.descrizione;
      this.funzionario.aziende = null;
      this.timestampLastCall = Date.now();
      this.messaggiService.numMessDaLeggere().subscribe(dati => {
        this.numMessDaLeggere = dati;
        this.timestampLastCall = Date.now();
      });
      if (this.flagAggiornaMessaggi) {
        this.flagAggiornaMessaggi = false;
      }
    });
  }

  logout() {
    this.isLogout = true;
    this.router.navigateByUrl('/logout');
  }

  changeIsLogout(event: boolean) {
    this.isLogout = event;
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