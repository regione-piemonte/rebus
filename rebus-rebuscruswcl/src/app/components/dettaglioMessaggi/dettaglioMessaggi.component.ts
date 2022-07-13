/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit, OnDestroy } from '@angular/core';
import { MessaggiService } from "../../services/messaggi.service";
import { Router, ActivatedRoute } from "@angular/router";
import { UserService } from "../../services/user.service";
import { ChangeDetectorRef } from '@angular/core';
import { MessaggioVO } from '../../vo/messaggio-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { UtilityService } from '../../services/utility.service'
import { AppComponent } from '../../app.component';
import { Subscription } from 'rxjs';
import { ModificaLabel } from '../../commons/labels/modifica';
import { UtenteAzEnteVO } from '../../vo/utenteAzEnte-vo';
import { ProcedimentoService } from '../../services/procedimento.service';
import { ContribuzioneService } from "../../services/contribuzione.service";
import { DettaglioRichiestaVO } from '../../vo/dettaglio-richiesta-vo';
import { CommonsHandleException } from '../../commons/commons-handle-exception';

@Component({
   selector: 'app-dettaglioMess',
   templateUrl: './dettaglioMessaggi.component.html',
   styleUrls: ['./dettaglioMessaggi.component.scss']
})

@DestroySubscribers()
export class DettaglioMessaggiComponent implements OnInit, OnDestroy {

   idMessaggio: number;
   azione: string;
   dettaglioMessaggio: MessaggioVO;
   dettaglioUtenteAzEnte: UtenteAzEnteVO;
   isUtenteAzienda: boolean;
   loadComplete: boolean;
   funzionario: UserInfo;
   isUtenteServizio: boolean;
   isUtenteAmp: boolean;
   private subscribers: Subscription;
   private subscriberDettaglioMessaggio: Subscription;
   dataCreazione: Date;
   mapVariazioneAutobus;
   mapVariazioneStoricoAutobus;
   staticColumnsMessaggi: Array<String> = ['Caratteristica', 'Vecchio valore', 'Nuovo Valore'];
   isModificaAbilitata: boolean;
   flagConfrontoArray: boolean;
   subscriberUtenteAzEnte: Subscription;
   tipiMessaggi: Number[];
   aziendaMessaggio: String;
   messaggioTipoProcedimento: boolean;
   loadTipiMessaggio: boolean;
   loadUtenteAzEnte: boolean;
   loadDettaglio: boolean;
   idContesto: number;
   dettaglioRichiesta: DettaglioRichiestaVO = new DettaglioRichiestaVO();
   isFinalStateRendicontazione: boolean = false;
   context: number;


   constructor(private messaggiService: MessaggiService,
      private procedimentoService: ProcedimentoService,
      private router: Router,
      private userService: UserService,
      private route: ActivatedRoute,
      private cdRef: ChangeDetectorRef,
      private utilityService: UtilityService,
      private appComponent: AppComponent,
      private contribuzioneService: ContribuzioneService,
   ) { }

   ngOnInit() {
      this.userService.funzionarioVo$.subscribe(data => {
         this.funzionario = data;
         this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
         this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS) || this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
         this.isUtenteServizio = this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);
         this.isUtenteAmp = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
      });


      this.subscribeDettaglioMessaggio();
      this.subscribeUtenteAzEnte();
      this.azione = this.route.snapshot.paramMap.get('action');
      this.idContesto = +this.route.snapshot.paramMap.get('idContesto');
      this.context = this.idContesto;

      this.subscribers = this.route.params.subscribe(params => {
         this.idMessaggio = +params['id']; // (+) converts string 'id' to a number

         this.callDettaglioMessaggio(+params['id']);
         this.loadComplete = true;
      });


   }

   subscribeDettaglioMessaggio() {
      this.subscriberDettaglioMessaggio = this.messaggiService.dettaglioMessaggio$.subscribe(data1 => {
         this.dettaglioMessaggio = data1;
         this.loadTipiMessaggio = false;
         this.procedimentoService.getTipiMessaggio(this.idContesto).subscribe(data => {
            if (data) {
               this.tipiMessaggi = data;
            }
            this.loadTipiMessaggio = true;
            if (this.dettaglioMessaggio != null && this.tipiMessaggi != null) {
               if (this.tipiMessaggiProcedimento((this.dettaglioMessaggio.fkTipoMessaggio))) {
                  this.messaggioTipoProcedimento = true;
               }
               else {
                  this.messaggioTipoProcedimento = false;
               }
            }
         });


         this.messaggiService.segnaComeLetto(this.idMessaggio);
         if (this.dettaglioMessaggio.fkProcedimento != null) {
            this.procedimentoService.getDescrizioneAziendaMessaggioProc(this.dettaglioMessaggio.fkProcedimento).subscribe(data => {
               if (data) {
                  this.aziendaMessaggio = data;
               }
            });
         }

         this.messaggiService.segnaComeLetto$.subscribe(data => {
            this.appComponent.aggiornaMessaggi();
         });

         this.dataCreazione = new Date(this.dettaglioMessaggio.dataCreazione);

         //Costruzione di mappe per confronto dei json di vecchio e nuovo valore
         if (this.dettaglioMessaggio.fkTipoMessaggio == 1) {
            let variazDisposPrevInc = data1.variazioneAutobus.dispositiviPrevenzInc.split(',').map(Function.prototype.call, String.prototype.trim);
            let variazStoricoDisposPrevInc = data1.variazioneStoricoAutobus.dispositiviPrevenzInc.split(',').map(Function.prototype.call, String.prototype.trim);
            this.flagConfrontoArray = this.confrontaDispositiviPrevInc(variazDisposPrevInc, variazStoricoDisposPrevInc);
            this.mapVariazioneAutobus = this.utilityService.jsonToMap(this.dettaglioMessaggio.variazioneAutobus);
            this.mapVariazioneStoricoAutobus = this.utilityService.jsonToMap(this.dettaglioMessaggio.variazioneStoricoAutobus);
            this.mapVariazioneAutobus.forEach((value, key) => {
               //non confronto i campi che iniziano con id
               if (this.isId(key)) {
                  this.mapVariazioneAutobus.delete(key);
                  this.mapVariazioneStoricoAutobus.delete(key);
               }
               //se il campo è di tipo data aggiorno il formato
               else if (this.isData(key)) {
                  let data: Date;
                  if (value == null) {
                     this.mapVariazioneAutobus.set(key, "");
                  }
                  else {
                     data = new Date(value);
                     this.mapVariazioneAutobus.set(key, data.toDateString());
                  }
                  let oldValue = this.mapVariazioneStoricoAutobus.get(key);
                  if (oldValue == null) {
                     this.mapVariazioneStoricoAutobus.set(key, "");
                  }
                  else {
                     data = new Date(oldValue);
                     this.mapVariazioneStoricoAutobus.set(key, data.toDateString());
                  }

               }
               else if (this.isStrField(key)) {
                  this.mapVariazioneAutobus.delete(key.substring(0, key.length - 3));
                  this.mapVariazioneStoricoAutobus.delete(key.substring(0, key.length - 3));
               }
            });
         }
         //se fkTipoMessaggio = 2 il servizio mi restituisce vuoti i campi variazioneAutobus e storico
         else {
            this.mapVariazioneAutobus = undefined;
            this.mapVariazioneStoricoAutobus = "";
         }

         this.callUtenteAzEnte(+this.dettaglioMessaggio.fkUtenteMittente);

         if (this.idContesto == 5 && this.dettaglioMessaggio.fkTipoMessaggioSistema == null) {
            this.procedimentoService.dettaglioRichiesta(this.dettaglioMessaggio.fkProcedimento, Action.EDIT).subscribe(data => {
               if (data) {
                  this.dettaglioRichiesta = data;
                  this.loadDettaglio = true
               }
            }, (error) => {
               CommonsHandleException.authorizationError(error, this.router);
            });
         } else {
            this.loadDettaglio = true
         }
      });
      this.context = this.idContesto;
   }

   subscribeUtenteAzEnte() {
      this.subscriberUtenteAzEnte = this.messaggiService.dettaglioUtenteAzEnte$.subscribe(data => {
         this.dettaglioUtenteAzEnte = data;
         this.loadUtenteAzEnte = true;
      });
   }

   tipiMessaggiProcedimento(tipoMessaggio: Number) {
      if (this.tipiMessaggi != null && this.tipiMessaggi.includes(tipoMessaggio) && tipoMessaggio != 40) {
         return true;
      }
      return false;
   }



   callDettaglioMessaggio(idMessaggioParam) {
      this.loadDettaglio = false;
      this.dettaglioMessaggio = null;

      this.messaggiService.dettaglioMessaggio(idMessaggioParam);
   }

   callUtenteAzEnte(idUtente) {
      this.loadUtenteAzEnte = false;
      this.dettaglioUtenteAzEnte = null;
      this.messaggiService.dettaglioUtenteAzEnte(idUtente);
   }

   isLoading() {
      if (!this.loadComplete || !this.loadDettaglio || !this.loadTipiMessaggio || !this.loadUtenteAzEnte) return true;
      return false;
   }

   ngOnDestroy(): void {
      this.subscriberDettaglioMessaggio.unsubscribe();
      this.subscribers.unsubscribe();
   }


   goBack() {
      this.messaggiService.idContestoNavbar = this.idContesto;
      this.router.navigate(['/messaggi', this.azione]);

   }

   getKeys(map: Map<string, any>) {
      return Array.from(map.keys());
   }

   isArray(obj) {
      return !Array.isArray(obj);
   }

   isData(obj) {
      return obj.startsWith("data");
   }

   isId(obj) {
      return obj.startsWith("id");
   }

   isStrField(obj) {
      return obj.endsWith("Str");
   }


   modificabus(value: number) {
      //RECUPERA BANDO SEL E SETTORE SEL
      let idMessaggio: string = this.route.snapshot.params.id;
      let azione = this.route.snapshot.paramMap.get('action');
      this.router.navigate(['/modificaBus', value, { PagMess: idMessaggio, idContesto: this.idContesto, action: azione }],
         {
            queryParams: {
            },
         });
   }

   modificaProcedimento(value: number) {
      //RECUPERA BANDO SEL E SETTORE SEL
      this.procedimentoService.getTipoProcedimentoByIdProc(value).subscribe(data => {
         let idContesto = this.route.snapshot.paramMap.get('idContesto');
         let azione: string = "messaggio_" + this.idMessaggio + "_" + this.azione + "_" + idContesto;
         if (data.id == 2) { // SOSTITUZIONE
            this.router.navigate(['/modificaRichiestaSostituzione/' + value, { action: azione }]);
         } else {
            this.router.navigate(['/modificaRichiesta/' + value, { action: azione }]);
         }
      });
   }


   modificaRendicontazione(value: number) {
      let lastState = this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null);
      this.contribuzioneService.checkFinalStateIter(lastState.idStato).subscribe(data => {
         this.isFinalStateRendicontazione = data;
         this.contribuzioneService.idProcedimento = value;
         let idMessaggio: string = this.route.snapshot.params.id;
         let azione = this.route.snapshot.paramMap.get('action');
         if (this.isFinalStateRendicontazione) {
            this.router.navigate(['/dettaglioBus', this.dettaglioMessaggio.fkVariazAutobus, { PagMess: idMessaggio, idContesto: this.idContesto, action: azione }],
               {
                  fragment: 'rendicontazione',
                  queryParams: {
                  },
               });
         } else {
            //controllare la pagina inserisci contribuzione. non ha torna indietro
            this.router.navigate(['/inserisciContribuzione/7'],
               {
                  fragment: 'dettaglioMessaggio',
                  queryParams: {
                  },
               });
         }
      });
   }

   confrontaDispositiviPrevInc(newA: Array<string>, oldA: Array<string>) {
      let status = true;
      newA.forEach(el => {
         if (!oldA.includes(el)) {
            status = false;
         }
      });
      oldA.forEach(el => {
         if (!newA.includes(el)) {
            status = false;
         }
      })
      if (status) {
         return true;
      }
      return false;
   }

   confrontaArray(a1: Number[], a2: Number[]) {
      if (a1.length != a2.length) {
         return false;
      }
      else {
         for (var i = 0, len = a1.length; i < len; i++) {
            if (a1[i] != a2[i]) {
               return false;
            }
         }
      }
      return true;
   }

   //Mapper per conversione di campi che arrivano dal DB
   convertiLabel(str: string) {
      if (ModificaLabel[str]) {
         return (ModificaLabel[str]);
      }
      switch (str) {
         case "N": {
            return "No";
         }
         case "S": {
            return "Si";
         }
         default: {
            return str;
         }
      }
   }
}

export enum Action {
   EDIT = "E",
   VIEW = "V",
}