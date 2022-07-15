/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ElementRef, ViewChild, ChangeDetectorRef } from '@angular/core';
import { BacinoVO, TipoAffidamentoVO, ModalitaAffidamentoVO, TipologiaServizioVO, AmbitoServizioVO, TipoRaggruppamentoVO, ComposizioneRaggruppamentoVO, TipoDocumentoVO, AziendaMandatariaVO, TipoSostituzioneVO } from '../../vo/drop-down-menu-vo';
import { Observable } from 'rxjs';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { ContrattoService } from '../../services/contratto.service';
import { DateAdapter, MatDialog, MatSnackBar } from '@angular/material';
import { EnteService } from '../../services/ente.service';
import { Moment } from 'moment';
import { SoggettoService } from '../../services/soggetto.service';
import { TipoSoggettoGiuridicoVO } from '../../vo/tipo-soggetto-giuridico-vo';
import { DialogComponent } from '../dialog/dialog.component';
import { SoggettoEsecutoreVO } from '../../vo/soggetto-esecutore-vo';
import { EnteVO } from '../../vo/ente-vo';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { AllegatoVO } from '../../vo/allegato-vo';
import { Router, ActivatedRoute } from '@angular/router';
import { ContrattoVO } from '../../vo/contratto-vo';
import { ContrAmbTipSerVO } from '../../vo/contr-amb-tip-serv-vo';
import { ProrogaVO } from '../../vo/proroga-vo';
import { of } from 'rxjs';
import { AmbTipServizioVO } from '../../vo/amb-tip-servizio-vo';
import { SoggettoSubentroVO } from '../../vo/soggetto-subentro-vo';
import { SubentroSubaffidamentoVO } from '../../vo/subentro-subaffidamento-vo';
import { UserService } from '../../services/user.service';
import { UserInfo } from '../../vo/funzionario-vo';
import { ContrattoRaggruppamentoVO } from '../../vo/contratto-raggruppamento-vo';
import { CancellaDialogComponent } from '../cancelladialog/cancelladialog.component';
import * as config from '../../globalparameter';

var moment = require('moment');

interface SoggettoSub {
  id: number;
  denominazione: string;
  idTipoSoggetto: number;
  idRuolo: number;
  group: string;
}

interface SoggettiSubGroup {
  disabled?: boolean;
  name: string;
  soggetti: SoggettoSub[];
}

@Component({
  selector: 'app-modifica-contratto',
  templateUrl: './modifica-contratto.component.html',
  styleUrls: ['./modifica-contratto.component.scss']
})
export class ModificaContrattoComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef;
  loadedSave: boolean = true;
  loadedBacini: boolean;
  loadedTipiAffidamento: boolean;
  loadedModalitaAffidamento: boolean;
  //loadedTipologiaServizio: boolean;
  loadedEnti: boolean;
  loadedSoggettiEsecutori: boolean;
  loadedTipiSoggettoGiuridico: boolean;
  loadedTipiDocumento: boolean;
  loadedAziendaMandataria: boolean;
  loadedtipoRaggr: boolean;
  loadedTipoServ: boolean; //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
  loadedTipiSostituzione: boolean;
  contrattoAmbtipoServizioArray: string[];
  contrattoAmbtipoServizioArrayVO: ContrAmbTipSerVO[];
  descTipologiaServizio: string;
  descAmbitoServizio: string;
  disableRadioSubentriSubaff: boolean = false;
  disableDescrizioneCodNazionale: boolean = true;
  isUtenteRegioneoServizi: boolean;
  funzionario: UserInfo;
  dataFineSubentroDisabled: boolean = false;
  checkedAltraData: boolean = false;
  eliminaShow: boolean = false;
  maxDataProroga: number;
  composizioneRaggruppamentoString: string; // usato per fare la join e mostrale l'elenco nella text area


  constructor(
    private dateAdapter: DateAdapter<Date>,
    private contrattoService: ContrattoService,
    private userService: UserService,
    private enteService: EnteService,
    private soggettoService: SoggettoService,
    private router: Router,
    private route: ActivatedRoute,
    private cdRef: ChangeDetectorRef,
    public snackBar: MatSnackBar,
    public dialog: MatDialog) {
    dateAdapter.setLocale('it-IT');
  }

  id: number;
  loadComplete: boolean;
  dettaglioContratto: ContrattoVO;
  contrattoCessato: boolean;
  bacino: Array<BacinoVO>;
  tipoAffidamento: Array<TipoAffidamentoVO>;
  modalitaAffidamento: Array<ModalitaAffidamentoVO>;
  tipologiaServizio: Array<TipologiaServizioVO>;
  ambitoServizio: Array<AmbitoServizioVO>;
  enteCommittente: Array<EnteVO>;
  soggettoEsecutore: Array<SoggettoEsecutoreVO>;
  tipoRaggruppamento: Array<TipoRaggruppamentoVO>;
  aziendaMandataria: Array<AziendaMandatariaVO>;
  composizioneRaggruppamento: Array<ComposizioneRaggruppamentoVO>;
  tipiSoggettoGiuridico: Array<TipoSoggettoGiuridicoVO>;
  tipiSostituzione: Array<TipoSostituzioneVO>;
  tipoSostituzioneSelected: TipoSostituzioneVO;
  soggettiSubentri: Array<SoggettoSubentroVO>;
  attoSubentro: string;
  dataSubentro: Moment;
  dataFineSubentro: Moment;
  nomeAllegatoTmp: string;
  tipiDocumento: Array<TipoDocumentoVO>;
  documenti: Array<TipoDocumentoVO>;
  tipiDocCaricati = new Array<TipoDocumentoVO>();
  descrEstesaTipoDocSelected: string;
  allegati = new Array<AllegatoVO>();
  noteFile: string = "";
  composizione: string = "";
  proroga: ProrogaVO = new ProrogaVO();
  ambTipServ: string = "";
  arrayAllegatiFiltrati: Array<number>;
  numRepertorioTmp: string = "";
  tipoSoggettoEsecutore: any;
  isAmp: boolean;
  isFirstTime: boolean;

  bacinoSelected = new FormControl();
  filteredOptionsBacino: Observable<BacinoVO[]>;
  bacinoGroup: FormGroup = new FormGroup({ bacinoForm: new FormControl() });

  tipoAffidamentoSelected = new FormControl();
  filteredOptionsTipoAffidamento: Observable<TipoAffidamentoVO[]>;
  tipoAffidamentoGroup: FormGroup = new FormGroup({ tipoAffidamentoForm: new FormControl() });

  modalitaAffidamentoSelected = new FormControl();
  filteredOptionsModalitaAffidamento: Observable<ModalitaAffidamentoVO[]>;
  modalitaAffidamentoGroup: FormGroup = new FormGroup({ modalitaAffidamentoForm: new FormControl() });

  tipologiaServizioSelected = new FormControl();
  filteredOptionsTipologiaServizio: Observable<TipologiaServizioVO[]>;
  tipologiaServizioGroup: FormGroup = new FormGroup({ tipologiaServizioForm: new FormControl() });

  //ambitotipoServizioSelected = new Array<AmbTipServizioVO>();
  //filteredOptionsAmbitoTipoServizio: Observable<AmbTipServizioVO[]>;
  //ambTipoServizioGroup: FormGroup = new FormGroup({ ambTipoServizioForm: new FormControl() });

  ambitoServizioSelected = new FormControl();
  filteredOptionsAmbitoServizio: Observable<AmbitoServizioVO[]>;
  ambitoServizioGroup: FormGroup = new FormGroup({ ambitoServizioForm: new FormControl() });

  enteCommittenteSelected = new FormControl();
  filteredOptionsEnteCommittente: Observable<EnteVO[]>;
  enteCommittenteGroup: FormGroup = new FormGroup({ enteCommittenteForm: new FormControl() });

  soggettoEsecutoreSelected = new FormControl();
  filteredOptionsSoggettoEsecutore: Observable<SoggettoEsecutoreVO[]>;
  soggettoEsecutoreGroup: FormGroup = new FormGroup({ soggettoEsecutoreForm: new FormControl() });

  tipoSoggettoGiuridicoSelected = new FormControl();
  filteredOptionsTipo: Observable<TipoSoggettoGiuridicoVO[]>;
  tipoSoggGroup: FormGroup = new FormGroup({ tipoSogg: new FormControl() });

  tipoRaggruppamentoSelected = new FormControl();
  filteredOptionsTipoRaggruppamento: Observable<TipoSoggettoGiuridicoVO[]>;
  tipoRaggruppamentoGroup: FormGroup = new FormGroup({ tipoRaggForm: new FormControl() });

  aziendaMandatariaSelected = new FormControl();
  filteredOptionsAziendaMandataria: Observable<AziendaMandatariaVO[]>;
  aziendaMandatariaGroup: FormGroup = new FormGroup({ aziendaMandatariaForm: new FormControl() });

  composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
  filteredOptionsComposizioneRagg: Observable<ComposizioneRaggruppamentoVO[]>;
  composizioneRaggGroup: FormGroup = new FormGroup({ composizioneRaggForm: new FormControl() });

  tipoDocumentoSelected = new FormControl();
  filteredOptionsTipoDocumento: Observable<TipoDocumentoVO[]>;
  tipoDocumentoGroup: FormGroup = new FormGroup({ tipoDocumentoForm: new FormControl() });

  ambitotipoServizioSelected = new Array<AmbTipServizioVO>();         // DA CANCELLARE QUANDO SI RIPRISTINA LA VERSIONE CORRETTA
  filteredOptionsAmbitoTipoServizio: Observable<AmbTipServizioVO[]>;  // DA CANCELLARE QUANDO SI RIPRISTINA LA VERSIONE CORRETTA
  ambTipoServizioGroup: FormGroup = new FormGroup({ ambTipoServizioForm: new FormControl() });  // DA CANCELLARE QUANDO SI RIPRISTINA LA VERSIONE CORRETTA
  ambitoTipoServizio: Array<AmbTipServizioVO>; // DA CANCELLARE QUANDO SI RIPRISTINA LA VERSIONE CORRETTA
  ambitoTipoServizioCheck: Array<AmbTipServizioVO> = new Array<AmbTipServizioVO>(); // DA CANCELLARE QUANDO SI RIPRISTINA LA VERSIONE CORRETTA

  soggettoContraenteFormGroup: FormGroup;
  contraenteSubSelected: SoggettoSub;
  contraentiSubGroups: SoggettiSubGroup[];
  contraentiSubaffidamentoGroups: SoggettiSubGroup[];

  contraentiSubGroupsFiltered: SoggettiSubGroup[];
  contraentiSubGroupsChange: SoggettiSubGroup[];
  hasRaggruppamento: boolean = true;

  subentroSelected = new FormControl();
  filteredOptionsSubentro: Observable<SoggettoSubentroVO[]>;
  subFormGroup2: FormGroup = new FormGroup({ subGroup2: new FormControl() });

  dataSt: Moment = null;
  dataIn: Moment = null;
  dataFin: Moment = null;
  dataPr: Moment;
  dataToday: Date = new Date();
  dataFineContratto: Moment = null;


  // filteredOptions: Observable<dropDownMenuVO[]>;
  // myControl = new FormControl();

  ngOnInit() {
    window.scroll(0, 0);
    this.dettaglioContratto = new ContrattoVO;
    //this.ambitoServizioGroup.disable();
    //this.tipoRaggruppamentoGroup.disable();
    //this.aziendaMandatariaGroup.disable();
    //this.composizioneRaggGroup.disable();
    this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number

      this.loadChoices();
    });


    /* this.filteredOptions = this.myControl.valueChanges
     .pipe(
       startWith(''),
       map(value => this._filter(value))
     );*/
  }

  /* displayFn(bacino?: dropDownMenuVO): string | undefined {
     return bacino ? bacino.descrizione : undefined;
   }*/

  loadChoices() {
    this.loadComplete = false;
    this.contrattoService.dettaglioContratto(this.id, Action.EDIT);

    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      if (this.funzionario.ruolo === null) {
        this.userService.funzionarioVo$.takeLast(1).subscribe(data => {
          this.funzionario = data;
          this.isUtenteRegioneoServizi = this.funzionario.ruolo.codiceRuolo.includes("REGIONE_REBUS") || this.funzionario.ruolo.codiceRuolo.includes("SERVIZIO_REBUS");
          this.isAmp = this.funzionario.ruolo.codiceRuolo.includes("AMP");

        })
      }
      else {
        this.isUtenteRegioneoServizi = this.funzionario.ruolo.codiceRuolo.includes("REGIONE_REBUS") || this.funzionario.ruolo.codiceRuolo.includes("SERVIZIO_REBUS");
        this.isAmp = this.funzionario.ruolo.codiceRuolo.includes("AMP");
      }
    });

    if (!this.isUtenteRegioneoServizi && !this.isAmp) {
      this.disableForm();
    }



    this.contrattoService.dettaglioContratto$.subscribe(data => {
      if (data) {
        this.dettaglioContratto = data;
        if (!this.dettaglioContratto.subentriSubaffidamenti) {
          this.dettaglioContratto.subentriSubaffidamenti = new Array<SubentroSubaffidamentoVO>();
        }
        if (data.codIdNazionale == null || data.codIdNazionale == '') {
          this.disableDescrizioneCodNazionale = false;
        } else {
          this.disableDescrizioneCodNazionale = true;
        }
        this.numRepertorioTmp = this.dettaglioContratto.numRepertorio;
        // this.soggettoService.getSoggettiEsecutoriTitolari(null).subscribe(data => {
        //   this.denomSoggTitolare = data.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore) != null ? data.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore).denominazione : null;
        //   this.soggetto = this.denomSoggTitolare;
        // });

        this.loadComplete = true;
        this.loadData();
        this.subFormGroup2.controls['subGroup2'].disable();
      }
      this.isFirstTime = true;
    });

    this.soggettoContraenteFormGroup = new FormGroup({ subGroup1: new FormControl() });
    this.contraentiSubGroups = new Array<SoggettiSubGroup>();
    this.contraentiSubGroupsFiltered = new Array<SoggettiSubGroup>();
    this.contraentiSubGroupsChange = new Array<SoggettiSubGroup>();
  }

  loadData() {
    this.loadedBacini = false;
    this.loadedTipiAffidamento = false;
    this.loadedModalitaAffidamento = false;
    //this.loadedTipologiaServizio = false;
    this.loadedEnti = false;
    this.loadedSoggettiEsecutori = false;
    this.loadedTipiSoggettoGiuridico = false;
    this.loadedTipiDocumento = false;
    this.loadedAziendaMandataria = false;
    this.loadedtipoRaggr = false;
    if (this.dettaglioContratto.dataStipula != null) {
      this.dataSt = moment((new Date(this.dettaglioContratto.dataStipula)).toString());
    }
    if (this.dettaglioContratto.dataInizioValidita != null) {
      this.dataIn = moment((new Date(this.dettaglioContratto.dataInizioValidita)).toString());
    }
    if (this.dettaglioContratto.dataFineValidita != null) {
      this.dataFin = moment((new Date(this.dettaglioContratto.dataFineValidita)).toString());
    }
    if (this.dettaglioContratto.proroghe != null && this.dettaglioContratto.proroghe.length > 0) {
      this.dataFineContratto = moment(this.dettaglioContratto.proroghe[0].dataFineProroga);

    } else {
      if (this.dettaglioContratto.dataFineValidita != null) {
        this.dataFineContratto = moment(this.dettaglioContratto.dataFineValidita);
      }
    }
    for (let i in this.dettaglioContratto.subentriSubaffidamenti) {
      if (this.dettaglioContratto.subentriSubaffidamenti[i].dataFine == null) {
        this.dettaglioContratto.subentriSubaffidamenti[i].dataFine = this.dataFineContratto.toDate();
      }
    }

    if (this.dettaglioContratto.proroghe[0] != undefined) {
      this.maxDataProroga = this.dettaglioContratto.proroghe[0].idProroga;
    }


    this.dataSubentro = this.dataIn;
    this.dataFineSubentro = this.dataFineContratto;
    this.dataFineSubentroDisabled = true;
    this.isContrattoCessato();
    this.contrattoService.getBacini().subscribe(data => {
      if (data) {
        this.bacino = data;
        this.filteredOptionsBacino = this.bacinoGroup.controls['bacinoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterBacino(name) : this.bacino.slice())
          );
        this.bacinoSelected = new FormControl();
        this.bacinoSelected.setValue(this.bacino.find(a => a.id == this.dettaglioContratto.idBacino) != null ? (this.bacino.find(a => a.id == this.dettaglioContratto.idBacino)) : null);
        this.bacinoGroup.controls['bacinoForm'].setValue(this.bacinoSelected.value);
        if (this.bacinoSelected.value === null) {
          this.bacinoGroup.controls['bacinoForm'].markAsTouched();
        }
      }
      this.loadedBacini = true;
    });
    this.contrattoService.getTipiAffidamento().subscribe(data => {
      if (data) {
        this.tipoAffidamento = data;
        this.filteredOptionsTipoAffidamento = this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipoAffidamento(name) : this.tipoAffidamento.slice())
          );
        this.tipoAffidamentoSelected = new FormControl();
        this.tipoAffidamentoSelected.setValue(this.tipoAffidamento.find(a => a.id == this.dettaglioContratto.idTipoAffidamento)) != null ? (this.tipoAffidamento.find(a => a.id == this.dettaglioContratto.idTipoAffidamento)) : new TipoAffidamentoVO();
        this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].setValue(this.tipoAffidamentoSelected.value);
      }
      this.loadedTipiAffidamento = true;
    });
    this.contrattoService.getModalitaAffidamento().subscribe(data => {
      if (data) {
        this.modalitaAffidamento = data;
        this.filteredOptionsModalitaAffidamento = this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterModalitaAffidamento(name) : this.modalitaAffidamento.slice())
          );
        this.modalitaAffidamentoSelected = new FormControl();
        this.modalitaAffidamentoSelected.setValue(this.modalitaAffidamento.find(a => a.id == this.dettaglioContratto.idModalitaAffidamento)) != null ? (this.modalitaAffidamento.find(a => a.id == this.dettaglioContratto.idModalitaAffidamento)) : new ModalitaAffidamentoVO();
        this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].setValue(this.modalitaAffidamentoSelected.value);
      }
      this.loadedModalitaAffidamento = true;
    });

    /* for (let ambTip of this.dettaglioContratto.ambTipServizio) {    //DA SCOMMENTARE PER RIPRISTINARE LA VERSIONE CORRETTA
       this.ambTipServ += ambTip.tipologiaServizio.descrizione + " - " + ambTip.ambitoServizio.descrizione + ", ";
     }
     this.ambTipServ = this.ambTipServ.slice(0, this.ambTipServ.length - 2); */

    this.loadedTipoServ = false;
    this.contrattoService.getAmbitoTipoServizio().subscribe(data => {  //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
      this.ambitotipoServizioSelected = new Array<AmbTipServizioVO>();
      this.ambitoTipoServizioCheck = new Array<AmbTipServizioVO>();
      if (data) {
        this.ambitoTipoServizio = data;
        for (let d of data) {
          this.ambitoTipoServizioCheck.push(new AmbTipServizioVO(d.idAmbTipServizio, d.ambitoServizio, d.tipologiaServizio, d.selected));
        }

        for (let ats of this.dettaglioContratto.ambTipServizio) {
          ats.selected = true;
          this.ambitoTipoServizio.find(a => a.idAmbTipServizio == ats.idAmbTipServizio).selected = true;
          this.ambitoTipoServizioCheck.find(a => a.idAmbTipServizio == ats.idAmbTipServizio).selected = true;

          this.ambitotipoServizioSelected.push(ats);
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(this.ambitotipoServizioSelected);
        }
        this.filteredOptionsAmbitoTipoServizio = this.ambTipoServizioGroup.controls['ambTipoServizioForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterAmbitoTipoServizio(name) : this.ambitoTipoServizio.slice())
          );
        if (this.contrattoCessato) {
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].disable();
          if (this.isUtenteRegioneoServizi || this.isAmp) {
            this.ambTipoServizioGroup.controls['ambTipoServizioForm'].enable();
          }
        }
      }
      this.loadedTipoServ = true;
    });

    this.enteService.getEnti().subscribe(data => {
      if (data) {
        this.enteCommittente = data;
        this.filteredOptionsEnteCommittente = this.enteCommittenteGroup.controls['enteCommittenteForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterEnteCommittente(name) : this.enteCommittente.slice())
          );
        this.enteCommittenteSelected = new FormControl();
        this.enteCommittenteSelected.setValue(this.enteCommittente.find(a => a.id == this.dettaglioContratto.idSogGiuridCommittente)) != null ? (this.enteCommittente.find(a => a.id == this.dettaglioContratto.idSogGiuridCommittente)) : new EnteVO();
        this.enteCommittenteGroup.controls['enteCommittenteForm'].setValue(this.enteCommittenteSelected.value);
      }
      this.loadedEnti = true;
    });
    this.soggettoService.getSoggettiEsecutoriTitolari(true, this.dettaglioContratto.idSogGiuridEsecutore).subscribe(data => {
      if (data) {
        this.soggettoEsecutore = data;

        this.filteredOptionsSoggettoEsecutore = this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterSoggettoEsecutore(name) : this.soggettoEsecutore.slice()),
          );

        this.soggettoEsecutoreSelected = new FormControl();
        this.soggettoEsecutoreSelected.setValue(this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore) != null ? (this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore)) : new SoggettoEsecutoreVO());
        this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(this.soggettoEsecutoreSelected.value);
        this.tipoSoggettoEsecutore = this.soggettoEsecutoreSelected.value.idTipoSoggettoEsecutore;
        // if (this.soggettoEsecutoreSelected.value.denominazione === undefined) {
        //   this.soggettoEsecutoreSelected.value.denominazione = this.denomSoggTitolare;
        // }
      }
      this.loadedSoggettiEsecutori = true;
    });

    this.soggettoService.getTipiSoggettoGiuridico(null).subscribe(data => {
      if (data) {
        this.tipiSoggettoGiuridico = data.sort((a, b) => a.id - b.id);
        this.tipiSoggettoGiuridico.splice(this.tipiSoggettoGiuridico.findIndex(t => t.idRuolo === 1), 1);
        this.filteredOptionsTipo = this.tipoSoggGroup.controls['tipoSogg'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipo(name) : this.tipiSoggettoGiuridico.slice())
          );
        this.tipoSoggettoGiuridicoSelected = new FormControl();
        this.tipoSoggettoGiuridicoSelected.setValue(this.tipiSoggettoGiuridico.find(a => a.id == this.dettaglioContratto.idTipoSogGiuridEsec)) != null ? (this.tipiSoggettoGiuridico.find(a => a.id == this.dettaglioContratto.idTipoSogGiuridEsec)) : new TipoSoggettoGiuridicoVO();
        this.tipoSoggGroup.controls['tipoSogg'].setValue(this.tipoSoggettoGiuridicoSelected.value);
      }
      this.loadedTipiSoggettoGiuridico = true;
    });
    this.arrayAllegatiFiltrati = new Array<number>();
    this.contrattoService.getTipiDocumento().subscribe(data => {
      if (data) {
        this.documenti = data;
        this.dettaglioContratto.allegati.forEach(a => this.arrayAllegatiFiltrati.push(a.idTipoDocumento));

        this.tipiDocumento = data.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id));
        /*
                this.filteredOptionsTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].valueChanges
                  .pipe(
                    startWith(''),
                    map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
                    map(name => name ? this._filterTipoDocumento(name) : this.tipiDocumento.slice())
                  );
          */
        this.filteredOptionsTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipoDocumento(name) : data.slice())
          );


        if (this.contrattoCessato && (!this.isUtenteRegioneoServizi && !this.isAmp)) {
          this.tipoDocumentoGroup.controls['tipoDocumentoForm'].disable();
        }
      }
      this.loadedTipiDocumento = true;
      // this.tipoDocumentoSelected.setValue(this.tipiDocumento.find(a => a.id == this.dettaglioContratto.));
    });

    this.enteService.getTipiRaggruppamento().subscribe(data => {
      if (data) {
        this.tipoRaggruppamento = data;
        this.filteredOptionsTipoRaggruppamento = this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipoRagg(name) : this.tipoRaggruppamento.slice())
          );
        this.tipoRaggruppamentoSelected = new FormControl();
        this.tipoRaggruppamentoSelected.setValue(this.tipoRaggruppamento.find(a => a.id == this.dettaglioContratto.idTipoRaggrSogGiuridEsec) != null ? this.tipoRaggruppamento.find(a => a.id == this.dettaglioContratto.idTipoRaggrSogGiuridEsec) : new TipoRaggruppamentoVO());
        this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(this.tipoRaggruppamentoSelected.value);
        if (this.tipoRaggruppamentoSelected.value.id == 1) {
          this.aziendaMandatariaGroup.disable();
        }
      }
      this.loadedtipoRaggr = true;
    });


    this.contrattoService.getAziendaMandataria(this.dettaglioContratto.idContratto).subscribe(data => {
      if (data) {
        this.aziendaMandataria = data;
        this.filteredOptionsAziendaMandataria = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterAziendaMandataria(name) : this.aziendaMandataria.slice())
          );
        let cr = this.dettaglioContratto.contrattoRaggruppamentoVOs.find(a => a.capofila == true);
        this.aziendaMandatariaSelected = new FormControl();
        if (cr != null) {
          this.aziendaMandatariaSelected.setValue(this.aziendaMandataria.find(b => b.id == cr.idSoggettoGiuridico) != null ? this.aziendaMandataria.find(b => b.id == cr.idSoggettoGiuridico) : new AziendaMandatariaVO());
          this.dettaglioContratto.idAziendaMandataria = this.aziendaMandatariaSelected.value.id;
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(this.aziendaMandatariaSelected.value);
        }
        this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
        for (let a of this.aziendaMandataria) {
          let c = new ComposizioneRaggruppamentoVO();
          c.id = a.id;
          c.descrizione = a.descrizione;
          c.idRuolo = a.idRuolo;
          c.selected = false;
          this.composizioneRaggruppamento.push(c);
        }

        if (this.dettaglioContratto.contrattoRaggruppamentoVOs != null && this.dettaglioContratto.contrattoRaggruppamentoVOs.length > 0) {
          this.hasRaggruppamento = true;
          let comp = this.dettaglioContratto.contrattoRaggruppamentoVOs.filter(c => c.capofila == false);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
          for (let contrattoRaggr of comp) {
            let c = this.composizioneRaggruppamento.findIndex(d => d.id == contrattoRaggr.idSoggettoGiuridico);
            if (c == -1) {
              console.error("id soggetto giuridico non trovato" + contrattoRaggr.idSoggettoGiuridico);
            }
            if (c && c !== -1) {
              let tmp = this.composizioneRaggruppamento[c];
              tmp.selected = true;
              tmp.idContrattoRaggruppamento = contrattoRaggr.idContrattoRaggruppamento;
              this.composizioneRaggSelected.push(tmp);
              this.composizioneRaggruppamento[c].selected = true;
              /*this.composizione += this.composizioneRaggruppamento.find(d => d.id == comp[i].idSoggettoGiuridico).descrizione;
              if (i != comp.length - 1) {
                this.composizione += ", "
              }*/
            }
          }

          if (!config.isNullOrVoid(this.composizioneRaggSelected) && this.composizioneRaggSelected.length > 0) {
            this.composizioneRaggruppamentoString = this.composizioneRaggSelected.map(c => c.descrizione).join(', ');
          }
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(this.composizioneRaggSelected);
          this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
            );

        } else {
          this.hasRaggruppamento = false;
        }
      }
      this.loadedAziendaMandataria = true;
    });
    this.loadedTipiSostituzione = false;
    this.contrattoService.getTipiSostituzione().subscribe(data => {
      if (data) {
        this.tipiSostituzione = data;
        this.tipoSostituzioneSelected = this.tipiSostituzione[0];
      }
      this.loadedTipiSostituzione = true;
    });
  }

  disableForm() {
    this.bacinoGroup.disable();
    this.tipoAffidamentoGroup.disable();
    this.modalitaAffidamentoGroup.disable();
    this.enteCommittenteGroup.disable();
    this.soggettoEsecutoreGroup.disable();
    this.tipoSoggGroup.disable();
    this.tipoRaggruppamentoGroup.disable();
    this.aziendaMandatariaGroup.disable();
    this.composizioneRaggGroup.disable();
  }

  isContrattoCessato() {
    if (this.dettaglioContratto.dataFineValidita != null) {
      if (this.dettaglioContratto.dataFineValidita <= new Date()) {
        this.contrattoCessato = true;
      }
      if (this.dettaglioContratto.proroghe != null && this.dettaglioContratto.proroghe.length > 0) {
        if (this.dettaglioContratto.proroghe[0].dataFineProroga > new Date()) {
          this.contrattoCessato = false;
        }
      }
    }
    else {
      this.contrattoCessato = false;
    }
  }

  hasFineValidita() {
    return this.dataFin != null;
  }

  calcolaMinDataProroga(): Date {
    let d: Date;
    if (this.dataFin != null) {

      if (this.dettaglioContratto.proroghe == null || this.dettaglioContratto.proroghe.length == 0) {
        d = new Date(this.dataFin.toDate());
      }
      else {
        d = new Date(this.dettaglioContratto.proroghe[0].dataFineProroga);
      }
      d.setDate(d.getDate() + 1);
    }
    return d;
  }


  changeAltraData(event) {
    this.checkedAltraData = event.checked;
    if (this.checkedAltraData) {
      this.dataFineSubentroDisabled = false;
      this.dataFineSubentro = null;
    } else {
      this.dataFineSubentroDisabled = true;
      this.dataFineSubentro = this.dataFineContratto;
    }
  }

  changeDataFineProroga() {
    if (this.dataPr) {
      var isValid = this.dataPr.isValid();
      if (isValid && this.calcolaMinDataProroga().getTime() < this.dataPr.toDate().getTime()) {
        this.dataFineSubentro = this.dataPr;
      } else {
        this.dataFineSubentro = this.dataFineContratto;
      }
    } else {
      this.dataFineSubentro = this.dataFineContratto;
    }

  }

  changeDataInizioValidita() {
    if (this.dataIn) {
      var isValid = this.dataIn.isValid();
      if (isValid) {
        this.dataSubentro = this.dataIn;
      }
    }
  }

  isAttoProrogaRequired() {
    if (this.dataPr != null)
      return true;
    return false;
  }

  isDataFineProrogaRequired() {
    if (this.proroga.attoProroga != null && this.proroga.attoProroga.length > 0)
      return true;
    return false;
  }

  isProrogaRemovable(index: number) {
    if (0 == index)
      return this.eliminaShow = true;
    // return this.dettaglioContratto.proroghe[index].idProroga == null;
  }

  addProroghe() {
    this.proroga.dataFineProroga = this.dataPr.toDate();
    this.dettaglioContratto.proroghe.unshift(this.proroga);
    this.dataPr = null;
    this.proroga = new ProrogaVO();
    for (let i = 0; i < this.dettaglioContratto.proroghe.length; i++) {
      this.autogrow(i);
    }
    this.isContrattoCessato();
    if (!this.contrattoCessato) {
      this.tipoDocumentoGroup.controls['tipoDocumentoForm'].enable();
      this.ambTipoServizioGroup.controls['ambTipoServizioForm'].enable();

    }
  }

  isAddProrogheDisabled() {
    if (this.proroga.attoProroga == null || this.proroga.attoProroga.length == 0 || this.dataPr == null)
      return true;
    else return false;
  }

  removeProroga(index: number) {
    let dialogRef = this.dialog.open(CancellaDialogComponent, {
      height: '200px',
      width: '500px',
      data: { msg: 'Eventuali allegati relativi alla proroga appena eliminata dovranno essere rimossi manualmente' }
    });


    this.dettaglioContratto.proroghe.splice(index, 1);


    // this.isContrattoCessato();
    // if (this.contrattoCessato) {
    //   this.tipoDocumentoGroup.controls['tipoDocumentoForm'].disable();
    //   this.ambTipoServizioGroup.controls['ambTipoServizioForm'].disable();
    // }



  }


  tipoSostituzioneChange(event: any) {
    if (event.value.id == 1) { //SUBAFFIDAMENTO
      this.contraentiSubGroupsChange = this.contraentiSubaffidamentoGroups;
    } else if (event.value.id == 2) { //SUBENTRO
      this.contraentiSubGroupsChange = this.contraentiSubGroupsFiltered;
    } else { // SOMMINISTRAZIONE DI PERSONALE
      this.contraentiSubGroupsChange = this.contraentiSubGroups;
    }

    this.contraenteSubSelected = null;
    this.soggettoContraenteFormGroup.controls['subGroup1'].markAsUntouched();
    this.subFormGroup2.controls['subGroup2'].setValue(null);
    this.subFormGroup2.controls['subGroup2'].markAsUntouched();
    this.subFormGroup2.controls['subGroup2'].disable();
    //this.formGroup.form.controls['dataSub'].setErrors(null);
    if (this.formGroup.form.get('dataFineSub')) {
      this.formGroup.form.controls['dataFineSub'].setErrors(null);
      //this.formGroup.form.controls['dataFineSub'].setValue(this.dataFineSubentro);
    }
    if (this.formGroup.form.get('attoSub')) {
      this.formGroup.form.controls['attoSub'].setValue(null);
    }
    /*     if (this.formGroup.form.get('dataSub')) {
          this.formGroup.form.controls['dataSub'].setValue(null);
        } */

    if (this.formGroup.form.get('dataSub')) {
      this.formGroup.form.controls['dataSub'].setErrors(null);

    }
    if (this.formGroup.form.get('altraData')) {
      this.dataFineSubentro = this.dataFineContratto;
      this.dataSubentro = this.dataIn;
      this.dataFineSubentroDisabled = true;
      this.checkedAltraData = false;
    }
    this.subentroSelected = new FormControl();
  }

  isSubentroRemovable(index: number) {
    if (this.isUtenteRegioneoServizi || this.isAmp)
      return true;

    if (this.contrattoCessato) {
      return false;
    }


    return this.dettaglioContratto.subentriSubaffidamenti[index].id == null;
  }

  addSubentri() {
    let sub = new SubentroSubaffidamentoVO();
    sub.soggettoContraente = new SoggettoSubentroVO();
    sub.soggettoContraente.id = this.contraenteSubSelected.id;
    sub.soggettoContraente.idTipoSoggGiurid = this.contraenteSubSelected.idTipoSoggetto;
    sub.soggettoContraente.denomSoggGiurid = this.contraenteSubSelected.denominazione;
    sub.contraenteGroup = this.contraenteSubSelected.group;
    sub.soggettoSubentrante = this.subFormGroup2.controls['subGroup2'].value;
    sub.tipoSostituzione = this.tipoSostituzioneSelected;
    sub.atto = this.attoSubentro;
    sub.data = this.dataSubentro.toDate();
    if (this.dataFineSubentro != null) {
      sub.dataFine = this.dataFineSubentro.toDate();
    }

    this.dettaglioContratto.subentriSubaffidamenti.push(sub);
    this.dettaglioContratto.subentriSubaffidamenti.sort((a, b) => {
      if (!b.data) return -1;
      if (!a.data) return 1;
      if (b.data > a.data) return 1;
      return -1;
    });
    if (sub.tipoSostituzione.id === 2) {//SUBENTRO
      this.removeSoggettoFromContrentiSub(sub);
      this.contraentiSubGroupsChange = this.contraentiSubGroupsFiltered;
    }
    this.dataSubentro = null;
    if (this.dataPr) {
      var isValid = this.dataPr.isValid();
      if (isValid && this.calcolaMinDataProroga().getTime() < this.dataPr.toDate().getTime()) {
        this.dataFineSubentro = this.dataPr;
      } else {
        this.dataFineSubentro = this.dataFineContratto;
      }
    } else {
      this.dataFineSubentro = this.dataFineContratto;
    }
    this.attoSubentro = null;
    this.contraenteSubSelected = null;
    this.soggettoContraenteFormGroup.controls['subGroup1'].markAsUntouched();
    this.subFormGroup2.controls['subGroup2'].setValue(null);
    this.subFormGroup2.controls['subGroup2'].markAsUntouched();
    this.subFormGroup2.controls['subGroup2'].disable();
    this.formGroup.form.controls['dataSub'].markAsUntouched();
    if (this.formGroup.form.get('dataFineSub')) {
      this.formGroup.form.controls['dataFineSub'].markAsUntouched();
    }
    this.subentroSelected = new FormControl();
    this.tipoSostituzioneSelected = this.tipiSostituzione[0];
  }

  removeSoggettoFromContrentiSub(sub: SubentroSubaffidamentoVO) {
    if (sub.contraenteGroup === 'EC') {
      this.contraentiSubGroupsFiltered.splice(this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Ente Committente'), 1);
    } else if (sub.contraenteGroup === 'ET') {
      this.contraentiSubGroupsFiltered.splice(this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Esecutore Titolare'), 1);
    }
    else if (sub.contraenteGroup === 'R') {
      let soggetti = this.contraentiSubGroupsFiltered[this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Raggruppamento')].soggetti;
      soggetti.splice(soggetti.findIndex(a => a.id === sub.soggettoContraente.id), 1);
    }
  }

  isAddSubentriDisabled() {
    if (this.contraenteSubSelected == null || this.contraenteSubSelected.id == null || !this.subFormGroup2.controls['subGroup2'].value || (this.formGroup.form.get('dataSub') && this.formGroup.form.get('dataSub').errors))
      return true;
    if (this.tipoSostituzioneSelected && this.tipoSostituzioneSelected.id != 2) {
      if ((this.formGroup.form.get('dataFineSub') && this.formGroup.form.get('dataFineSub').errors))
        return true;
    }
    else return false;
  }

  removeSubentro(index: number) {
    this.dettaglioContratto.subentriSubaffidamenti.splice(index, 1);
    this.contraentiSubGroupsFiltered = new Array<SoggettiSubGroup>();
    this.contraentiSubGroupsFiltered.push(
      {
        name: 'Ente Committente',
        soggetti: [
          { id: this.enteCommittenteSelected.value.id, idRuolo: this.enteCommittenteSelected.value.idRuolo, denominazione: this.enteCommittenteSelected.value.denominazione, idTipoSoggetto: 1, group: "EC" }
        ]
      });
    this.contraentiSubGroupsFiltered.push(
      {
        name: 'Esecutore Titolare',
        soggetti: [
          { id: this.soggettoEsecutoreSelected.value.id, idRuolo: this.enteCommittenteSelected.value.idRuolo, denominazione: this.soggettoEsecutoreSelected.value.denominazione, idTipoSoggetto: this.soggettoEsecutoreSelected.value.idTipoSoggettoEsecutore, group: "ET" }
        ]
      });
    if (this.hasRaggruppamento) {
      let soggSubFiltered = new Array<SoggettoSub>();
      if (this.aziendaMandatariaSelected.value.id != this.soggettoEsecutoreSelected.value.id) {
        soggSubFiltered.push({
          id: this.aziendaMandatariaSelected.value.id,
          idRuolo: this.aziendaMandatariaSelected.value.idRuolo,
          denominazione: this.aziendaMandatariaSelected.value.descrizione,
          idTipoSoggetto: 2,
          group: "R"
        });
      }
      for (let c of this.composizioneRaggSelected) {
        soggSubFiltered.push({
          id: c.id,
          idRuolo: c.idRuolo,
          denominazione: c.descrizione,
          idTipoSoggetto: 2,
          group: "R"
        });
      }
      this.contraentiSubGroupsFiltered.push(
        {
          name: 'Raggruppamento',
          soggetti: soggSubFiltered
        });
    }
    for (let sub of this.dettaglioContratto.subentriSubaffidamenti) {
      if (sub.tipoSostituzione.id === 2) { //SUBENTRO
        this.removeSoggettoFromContrentiSub(sub);
        // rimuovo il raggruppamento per il subaffidamento
      }
    }
    if (this.tipoSostituzioneSelected.id === 1) {
      // rimuovo il raggruppamento per il subaffidamento
      this.contraentiSubaffidamentoGroups = JSON.parse(JSON.stringify(this.contraentiSubGroups));
      this.contraentiSubaffidamentoGroups = this.contraentiSubaffidamentoGroups.filter(a => a.name == 'Esecutore Titolare');
      this.contraentiSubGroupsChange = this.contraentiSubaffidamentoGroups;
    } else if (this.tipoSostituzioneSelected.id === 2) {
      this.contraentiSubGroupsChange = this.contraentiSubGroupsFiltered;
    } else {
      this.contraentiSubGroupsChange = this.contraentiSubGroups;
    }
  }

  indietro() {
    this.loadComplete = false;
    if (this.route.snapshot.paramMap.get('action') == 'inserisci')
      this.router.navigate(['/ricercaContratto']);
    else if (this.route.snapshot.paramMap.get('action') == 'ricerca')
      window.history.back();
  }
  _filterBacino(descrizione: string): BacinoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.bacino.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterTipoAffidamento(descrizione: string): TipoAffidamentoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipoAffidamento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterModalitaAffidamento(descrizione: string): ModalitaAffidamentoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.modalitaAffidamento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }


  _filterAmbitoTipoServizio(descrizione: string): AmbTipServizioVO[] { //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
    const filterValue = descrizione.toLowerCase();
    let array = this.ambitoTipoServizio.filter(option => option.tipologiaServizio.descrizione.toLowerCase().includes(filterValue));
    return array;
  }

  _filterEnteCommittente(denominazione: string): EnteVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.enteCommittente.filter(option => option.denominazione.toLowerCase().includes(filterValue));
  }

  _filterSoggettoEsecutore(denominazione: string): SoggettoEsecutoreVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.soggettoEsecutore.filter(option => option.denominazione.toLowerCase().includes(filterValue));
  }

  _filterTipo(descrizione: string): TipoSoggettoGiuridicoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipiSoggettoGiuridico.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterTipoRagg(descrizione: string): TipoRaggruppamentoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipoRaggruppamento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterAziendaMandataria(descrizione: string): AziendaMandatariaVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.aziendaMandataria.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterComposizioneRagg(descrizione: string): ComposizioneRaggruppamentoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.composizioneRaggruppamento.filter(option => option.descrizione.toLowerCase().includes(filterValue) && option.id != this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id);
  }

  _filterTipoDocumento(descrizione: string): TipoDocumentoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.documenti.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
  }

  _filterSoggettiSubentri(descrizione: string): SoggettoSubentroVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.soggettiSubentri.filter(option => option.denomSoggGiurid ? (option.denomSoggGiurid.toLowerCase().indexOf(filterValue) >= 0 ? true : false) : false);
  }

  click(event: any, s: string) {
    if (s == 'Ba')
      this.bacinoSelected.setValue(event.option.value);
    else if (s == 'Ta')
      this.tipoAffidamentoSelected.setValue(event.option.value);
    else if (s == 'Ma')
      this.modalitaAffidamentoSelected.setValue(event.option.value);
    else if (s == 'Ts')
      this.tipologiaServizioSelected.setValue(event.option.value);
    else if (s == 'As')
      this.ambitoServizioSelected.setValue(event.option.value);
    else if (s == 'Ec')
      this.enteCommittenteSelected.setValue(event.option.value);
    else if (s == 'Se') {
      this.soggettoEsecutoreSelected.setValue(event.option.value);
      this.dettaglioContratto.idSogGiuridEsecutore = event.option.value.id;
      this.tipoSoggettoEsecutore = event.option.value.idTipoSoggettoEsecutore;
      this.checkSoggettoEsecutore();

    }
    else if (s == 'T') {
      this.tipoSoggettoGiuridicoSelected.setValue(event.option.value);
      this.checkTipoSoggettoGiuridico();

    }
    else if (s == 'Tr') {
      this.tipoRaggruppamentoSelected.setValue(event.option.value);
      this.dettaglioContratto.idTipoRaggrSogGiuridEsec = event.option.value.id;
      this.checkTipoRaggruppamento();
    }
    else if (s == 'Am') {
      this.aziendaMandatariaSelected.setValue(event.option.value);
      this.dettaglioContratto.idAziendaMandataria = event.option.value.id;
      this.checkAziendaMandataria();
    }
    else if (s == 'Td') {
      this.tipoDocumentoSelected.setValue(event.option.value);
      this.descrEstesaTipoDocSelected = event.option.value.descrizioneEstesa;
    }
    else if (s == 'Sub') {
      this.subentroSelected.setValue(event.option.value);
    }
  }

  checkSoggettoEsecutore() {
    //se il tipo soggetto esecutore è 1 Ente seleziono il tipo sogg giuridico con il tipo 1 e disabilito il campo
    /*if (this.tipoSoggettoEsecutore == 1) {
      this.tipoSoggettoGiuridicoSelected = new FormControl();
      this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
      this.tipoSoggettoGiuridicoSelected.setValue(this.tipiSoggettoGiuridico.find(a => a.id == 1));
      this.tipoSoggGroup.controls['tipoSogg'].setValue(this.tipiSoggettoGiuridico.find(a => a.id == 1));
      this.tipoSoggGroup.disable();
      this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
      this.tipoRaggruppamentoSelected = new FormControl();
      this.tipoRaggruppamentoGroup.disable();
      this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
      this.aziendaMandatariaSelected = new FormControl();
      this.aziendaMandatariaGroup.disable();
      this.composizioneRaggGroup.disable();
      this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
      this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
    } else*/ if (this.tipoSoggettoEsecutore == 2) {
      this.filteredOptionsTipo = of(this.tipiSoggettoGiuridico.filter(a => a.id != 1));
      this.tipoSoggGroup.enable();
      if (this.tipoSoggGroup.controls['tipoSogg'].value != null && this.tipoSoggGroup.controls['tipoSogg'].value.id == 1) {
        this.tipoSoggettoGiuridicoSelected = new FormControl();
        this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
      }
      if (this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value != null && this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value.id == 1) { //consorzio
        this.aziendaMandatariaSelected.setValue(this.aziendaMandataria.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore));
        this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(this.aziendaMandataria.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore));
        this.aziendaMandatariaGroup.disable();
        if (this.composizioneRaggSelected == null || this.composizioneRaggSelected.length == 0) {
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
          this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
          for (let a of this.aziendaMandataria) {
            let c = new ComposizioneRaggruppamentoVO();
            c.id = a.id;
            c.descrizione = a.descrizione;
            c.selected = false;
            this.composizioneRaggruppamento.push(c);
          }
          this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.dettaglioContratto.idSogGiuridEsecutore);
          this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
            );
          this.composizioneRaggGroup.enable();
        }
      }
    }
    if (this.composizioneRaggruppamento) {
      this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.dettaglioContratto.idSogGiuridEsecutore);
    }
  }

  checkTipoSoggettoGiuridico() {
    if (this.tipoSoggettoGiuridicoSelected.value.id == 3) { //ID_TIPO_SOG=RAGGRUPPAMENTO
      this.enteService.getTipiRaggruppamento().subscribe(data => {
        if (data) {
          this.tipoRaggruppamento = data;
          this.filteredOptionsTipoRaggruppamento = this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterTipoRagg(name) : this.tipoRaggruppamento.slice())
            );
          this.tipoRaggruppamentoGroup.enable();
        }
      });

      this.contrattoService.getAziendaMandataria(this.dettaglioContratto.idSogGiuridEsecutore).subscribe(data => {
        if (data) {
          this.aziendaMandataria = data;
          this.filteredOptionsAziendaMandataria = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterAziendaMandataria(name) : this.aziendaMandataria.slice())
            );
          if (this.tipoRaggruppamentoSelected != null && this.tipoRaggruppamentoSelected.value != null && this.tipoRaggruppamentoSelected.value.id != 1) {
            this.aziendaMandatariaGroup.enable();
          }
        }
      });

    }
    else {
      this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
      this.tipoRaggruppamentoSelected = new FormControl();
      this.tipoRaggruppamentoGroup.disable();
      this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
      this.aziendaMandatariaSelected = new FormControl();
      this.aziendaMandatariaGroup.disable();
      this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
      this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
      this.composizioneRaggGroup.disable();
      this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
      for (let a of this.aziendaMandataria) {
        let c = new ComposizioneRaggruppamentoVO();
        c.id = a.id;
        c.descrizione = a.descrizione;
        c.selected = false;
        this.composizioneRaggruppamento.push(c);
      }
    }
  }

  checkTipoRaggruppamento() {
    //se seleziono "consorzio" come tipo raggruppamento devo prevalorizzare aziedna mandataria
    if (this.tipoRaggruppamentoSelected.value.id == 1) {
      if (this.dettaglioContratto.idSogGiuridEsecutore != null) {
        this.composizioneRaggGroup.disable();
        //this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
        //this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
        this.aziendaMandatariaSelected = new FormControl();
        this.aziendaMandatariaSelected.setValue(this.aziendaMandataria.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore));
        this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(this.aziendaMandataria.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore));
        /*        this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
                for (let a of this.aziendaMandataria) {
                  let c = new ComposizioneRaggruppamentoVO();
                  c.id = a.id;
                  c.descrizione = a.descrizione;
                  c.selected = false;
                  this.composizioneRaggruppamento.push(c);
                }
          */
        this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.aziendaMandataria.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore).id);
        this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
          );
        this.composizioneRaggGroup.enable();
      }
      else if (this.dettaglioContratto != null) {
        this.soggettoEsecutoreSelected.setValue(this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idAziendaMandataria));
        this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idAziendaMandataria));
      }
      this.aziendaMandatariaGroup.disable();
    }
    else this.aziendaMandatariaGroup.enable();
  }

  checkAziendaMandataria() {
    this.composizioneRaggGroup.disable();
    this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
    this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
    if (this.dettaglioContratto.idTipoRaggrSogGiuridEsec == 1) {
      if (this.dettaglioContratto.idSogGiuridEsecutore == null) {
        this.soggettoEsecutoreSelected.setValue(this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idAziendaMandataria));
        this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(this.soggettoEsecutore.find(a => a.id == this.dettaglioContratto.idAziendaMandataria));
      }
    }
    this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
    for (let a of this.aziendaMandataria) {
      let c = new ComposizioneRaggruppamentoVO();
      c.id = a.id;
      c.descrizione = a.descrizione;
      c.selected = false;
      this.composizioneRaggruppamento.push(c);
    }
    this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.dettaglioContratto.idAziendaMandataria);
    if (this.dettaglioContratto.idAziendaMandataria && this.dettaglioContratto.idSogGiuridEsecutore && this.dettaglioContratto.idAziendaMandataria != this.dettaglioContratto.idSogGiuridEsecutore) {
      this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.dettaglioContratto.idSogGiuridEsecutore);
    }
    this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
      .pipe(
        startWith(''),
        map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
        map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
      );
    this.composizioneRaggGroup.enable();
  }

  optionClicked(event: Event, comp: ComposizioneRaggruppamentoVO) {
    event.stopPropagation();
    this.toggleSelection(comp);
  }

  toggleSelection(comp: ComposizioneRaggruppamentoVO) {
    comp.selected = !comp.selected;
    if (comp.selected) {
      this.composizioneRaggSelected.push(comp);
    } else {
      const i = this.composizioneRaggSelected.findIndex(value => value.descrizione === comp.descrizione);
      this.composizioneRaggSelected.splice(i, 1);
    }

    this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(this.composizioneRaggSelected);

    if (comp.id == this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id && comp.selected) {
      this.openDialog('Errore!', 'Azienda già selezionata come Mandataria');
    }

  }

  optionClickedAmbTipoServizio(event: Event, comp: AmbTipServizioVO) {
    event.stopPropagation();
    this.toggleSelectionAmbTipServizio(comp);
  }

  toggleSelectionAmbTipServizio(comp: AmbTipServizioVO) { //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
    //comp.selected = !comp.selected;
    let at = this.ambitoTipoServizioCheck.find(a => a.idAmbTipServizio == comp.idAmbTipServizio);
    at.selected = !at.selected;
    comp.selected = at.selected;
    if (comp.selected) {
      this.ambitotipoServizioSelected.push(comp);
    } else {
      const i = this.ambitotipoServizioSelected.findIndex(value => value.idAmbTipServizio === comp.idAmbTipServizio);
      this.ambitotipoServizioSelected.splice(i, 1);
    }

    this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(this.ambitotipoServizioSelected);
  }

  displayFnats(a?: any): string | undefined { //per ambito tipologia servizi  DA CANCELLARE PER RIRPRISTINARE LA VERSIONE CORRETTA
    let displayValue: string;
    if (Array.isArray(a)) {
      a.forEach((c, index) => {
        if (index === 0) {
          displayValue = c.tipologiaServizio.descrizione + " - " + c.ambitoServizio.descrizione;
        } else {
          displayValue += ', ' + c.tipologiaServizio.descrizione + " - " + c.ambitoServizio.descrizione;
        }
      });
    } else {
      displayValue = a;
    }
    return displayValue;
  }

  check(s: string) {
    setTimeout(() => {
      if (s == 'Ba') {
        if (!this.bacinoSelected || (this.bacinoGroup.controls['bacinoForm'] && this.bacinoSelected.value !== this.bacinoGroup.controls['bacinoForm'].value)) {
          this.bacinoGroup.controls['bacinoForm'].setValue(null);
          this.bacinoSelected = new FormControl();
        }
      }
      else if (s == 'Ta') {
        if (!this.tipoAffidamentoSelected || (this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'] && this.tipoAffidamentoSelected.value !== this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value)) {
          this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].setValue(null);
          this.tipoAffidamentoSelected = new FormControl();
        }
      }
      else if (s == 'Ma') {
        if (!this.modalitaAffidamentoSelected || (this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'] && this.modalitaAffidamentoSelected.value !== this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value)) {
          this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].setValue(null);
          this.modalitaAffidamentoSelected = new FormControl();
        }
      }
      else if (s == 'Ts') {
        if (!this.tipologiaServizioSelected || (this.tipologiaServizioGroup.controls['tipologiaServizioForm'] && this.tipologiaServizioSelected.value !== this.tipologiaServizioGroup.controls['tipologiaServizioForm'].value)) {
          this.tipologiaServizioGroup.controls['tipologiaServizioForm'].setValue(null);
          this.tipologiaServizioSelected = new FormControl();
          this.ambitoServizioGroup.disable();
        }
      }
      else if (s == 'As') {
        if (!this.ambitoServizioSelected || (this.ambitoServizioGroup.controls['ambitoServizioForm'] && this.ambitoServizioSelected.value !== this.ambitoServizioGroup.controls['ambitoServizioForm'].value)) {
          this.ambitoServizioGroup.controls['ambitoServizioForm'].setValue(null);
          this.ambitoServizioSelected = new FormControl();
        }
      }
      else if (s == 'Ec') {
        if (!this.enteCommittenteSelected || (this.enteCommittenteGroup.controls['enteCommittenteForm'] && this.enteCommittenteSelected.value !== this.enteCommittenteGroup.controls['enteCommittenteForm'].value)) {
          this.enteCommittenteGroup.controls['enteCommittenteForm'].setValue(null);
          this.enteCommittenteSelected = new FormControl();
        }
      }
      else if (s == 'Se') {
        if (!this.soggettoEsecutoreSelected || (this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'] && this.soggettoEsecutoreSelected.value !== this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].value)) {
          this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(null);
          this.soggettoEsecutoreSelected = new FormControl();
        }
      }
      else if (s == 'T') {
        if (!this.tipoSoggettoGiuridicoSelected || (this.tipoSoggGroup.controls['tipoSogg'] && this.tipoSoggettoGiuridicoSelected.value !== this.tipoSoggGroup.controls['tipoSogg'].value)) {
          this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
          this.tipoSoggettoGiuridicoSelected = new FormControl();
          this.tipoRaggruppamentoGroup.disable();
          this.aziendaMandatariaGroup.disable();
          this.composizioneRaggGroup.disable();
        }
      }
      else if (s == 'Tr') {
        if (!this.tipoRaggruppamentoSelected || (this.tipoRaggruppamentoGroup.controls['tipoRaggForm'] && this.tipoRaggruppamentoSelected.value !== this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value)) {
          this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
          this.tipoRaggruppamentoSelected = new FormControl();
        }
      }
      else if (s == 'Am') {
        if (!this.aziendaMandatariaSelected || (this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'] && this.aziendaMandatariaSelected.value !== this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value)) {
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
          this.aziendaMandatariaSelected = new FormControl();
        }
      }
      else if (s == 'Cr') {
        if (!this.composizioneRaggSelected || (this.composizioneRaggGroup.controls['composizioneRaggForm'] && this.composizioneRaggSelected !== this.composizioneRaggGroup.controls['composizioneRaggForm'].value)) {
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
        }
      }
      if (s == 'Td') {
        if (!this.tipoDocumentoSelected || (this.tipoDocumentoGroup.controls['tipoDocumentoForm'] && this.tipoDocumentoSelected.value !== this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value)) {
          this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
          this.tipoDocumentoSelected = new FormControl();
          this.descrEstesaTipoDocSelected = null;
        }
      }
      else if (s == 'AmbTip') { //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
        if (!this.ambitotipoServizioSelected || this.ambitotipoServizioSelected.length == 0) {
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(null);
        }
        else if (this.ambTipoServizioGroup.controls['ambTipoServizioForm'] && this.ambTipoServizioGroup.controls['ambTipoServizioForm'].value !== this.displayFnats(this.ambitotipoServizioSelected)) {
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(this.ambitotipoServizioSelected);
        }
      } else if (s == 'Sub') {
        if (!this.subentroSelected || (this.subFormGroup2.controls['subGroup2'] && this.subentroSelected.value !== this.subFormGroup2.controls['subGroup2'].value)) {
          this.subFormGroup2.controls['subGroup2'].setValue(null);
          this.subentroSelected = new FormControl();
        }
      }
    }, 200);
  }

  displayFn(a?: any): string | undefined {
    return a ? a.descrizione : undefined;
  }

  displayFnSub(a?: any): string | undefined {
    return a ? a.denomSoggGiurid : undefined;
  }

  displayFne(a?: any): string | undefined { //per ente committente e soggetto esecutore
    return a ? a.denominazione : undefined;
  }

  displayFncr(a?: any): string | undefined { //per composizione raggruppamento
    let displayValue: string;
    if (Array.isArray(a)) {
      a.forEach((c, index) => {
        if (index === 0) {
          displayValue = c.descrizione;
        } else {
          displayValue += ', ' + c.descrizione;
        }
      });
    } else {
      displayValue = a;
    }
    return displayValue;
  }

  openDialog(type: string, message: string): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: { type: type, message: message }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  autogrow(index: number) {
    let textarea = document.getElementById("textarea_" + index);
    if (textarea != null) {
      textarea.style.overflow = 'hidden';
      textarea.style.height = '0px';
      textarea.style.height = textarea.scrollHeight + 'px';
    }
  }

  loadSoggettiSubentro(var1) {
    if (var1.value != null && var1.value.group == "EC") {
      this.tipoSostituzioneSelected = this.tipiSostituzione[1];
      this.disableRadioSubentriSubaff = true;
    } else {
      this.disableRadioSubentriSubaff = false;
    }
    this.subFormGroup2.controls['subGroup2'].disable();
    this.contrattoService.getSoggettiSubentro(this.id, this.contraenteSubSelected.idRuolo).subscribe(data => {
      if (data) {
        this.soggettiSubentri = data;
        this.filteredOptionsSubentro = this.subFormGroup2.controls['subGroup2'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denomSoggGiurid),
            map(name => name ? this._filterSoggettiSubentri(name) : this.soggettiSubentri.slice())
          );
        this.subFormGroup2.controls['subGroup2'].enable();
      }
    });
  }


  @ViewChild('contrattoForm') formGroup: NgForm;

  isValidCampiRequired() {
    if (this.formGroup && this.formGroup.form) {
      if (this.formGroup.form.get('descrizione') && this.formGroup.form.get('descrizione').hasError('required')) return true;
      if (this.formGroup.form.get('NumRepertorio') && this.formGroup.form.get('NumRepertorio').hasError('required')) return true;
      if (this.bacinoGroup.get('bacinoForm') && this.bacinoGroup.get('bacinoForm').hasError('required')) return true;
      //if (this.formGroup.form.get('cig') && this.formGroup.form.get('cig').hasError('required')) return true;
      if (this.formGroup.form.get('dataStipula') && this.formGroup.form.get('dataStipula').hasError('required')) return true;

      if (this.enteCommittenteGroup.get('enteCommittenteForm') && this.enteCommittenteGroup.get('enteCommittenteForm').hasError('required')) return true;
      if (this.soggettoEsecutoreGroup.get('soggettoEsecutoreForm') && this.soggettoEsecutoreGroup.get('soggettoEsecutoreForm').hasError('required')) return true;
      if (this.tipoSoggGroup.get('tipoSogg') && this.tipoSoggGroup.get('tipoSogg').hasError('required')) return true;
      if (this.tipoSoggGroup.get('tipoSogg').value && this.tipoSoggGroup.get('tipoSogg').value.id == 3) {
        if (this.tipoRaggruppamentoGroup.get('tipoRaggForm') && this.tipoRaggruppamentoGroup.get('tipoRaggForm').hasError('required')) return true;
        if (this.aziendaMandatariaGroup.get('aziendaMandatariaForm') && this.aziendaMandatariaGroup.get('aziendaMandatariaForm').hasError('required')) return true;
        if (this.composizioneRaggGroup.get('composizioneRaggForm') && this.composizioneRaggGroup.get('composizioneRaggForm').hasError('required')) return true;
      }
      if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('required')) return true;
      if (this.formGroup.form.get('dataFine') && this.formGroup.form.get('dataFine').hasError('required')) return true;
      if (this.formGroup.form.get('attoProroga') && this.formGroup.form.get('attoProroga').hasError('required')) return true;
      if (this.formGroup.form.get('dataFineProroga') && this.formGroup.form.get('dataFineProroga').hasError('required')) return true;
      // if(this.tipoDocumentoGroup.get('tipoDocumentoForm') && this.tipoDocumentoGroup.get('tipoDocumentoForm').hasError('required')) return true;
    }
    //if(this.formGroup.form.get('dataStipula') && this.formGroup.form.get('dataStipula').hasError('required')) return true;


    //errorDate
    if (this.formGroup.form.get('dataFineSub') && this.formGroup.form.get('dataFineSub').hasError('matDatepickerMin')) return true;
    if (this.formGroup.form.get('dataFineSub') && this.formGroup.form.get('dataFineSub').hasError('matDatepickerMax')) return true;
    if (this.formGroup.form.get('dataSub') && this.formGroup.form.get('dataSub').hasError('matDatepickerMin')) return true;
    if (this.formGroup.form.get('dataSub') && this.formGroup.form.get('dataSub').hasError('matDatepickerMax')) return true;
    if (this.formGroup.form.get('dataFineProroga') && this.formGroup.form.get('dataFineProroga').hasError('matDatepickerMin')) return true;
    if (this.formGroup.form.get('dataFine') && this.formGroup.form.get('dataFine').hasError('matDatepickerMin')) return true;
    if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('matDatepickerMin')) return true;
    // if (this.soggettoContraenteFormGroup.get('subGroup1') && this.soggettoContraenteFormGroup.get('subGroup1').hasError('required')) return true;
    return false;
  }

  refreshDirective() {
    if (this.formGroup.form.get('dataStipula')) this.formGroup.form.get('dataStipula').markAsTouched();
    if (this.formGroup.form.get('dataInizio')) this.formGroup.form.get('dataInizio').markAsTouched();
    if (this.formGroup.form.get('dataFine')) this.formGroup.form.get('dataFine').markAsTouched();
  }

  isCampiInvalid() {
    if (this.formGroup.form.get('dataFine') && this.formGroup.form.get('dataFine').invalid) return true;

    //if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('matDatepickerMax')) return true;
    return false;
  }
  dataOggi: string = "";
  allegatoTmpCounter: number = 0;
  handleFileInput(files: Array<File>) {
    if (!config.isNullOrVoid(this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value) && !config.isNullOrVoid(files[0].name)) {

    }
    for (let f of files) {
      let allegato = new AllegatoVO();
      allegato.idTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value.id;
      //this.arrayAllegatiFiltrati.push(allegato.idTipoDocumento);
      allegato.noteFile = this.noteFile;
      allegato.file = f;
      allegato.nomeFile = f.name;
      allegato.descrizioneTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value.descrizione;
      allegato.dataCaricamento = new Date();
      this.allegatoTmpCounter += 1;
      allegato.idAllegatoTmp = this.allegatoTmpCounter;
      this.dataOggi = this.dataToday.getDate() + "/" + (this.dataToday.getMonth() + 1).toString() + "/" + this.dataToday.getFullYear();
      this.dettaglioContratto.allegati.unshift(allegato); //l'ultimo allegato inserito deve essere il primo dell'array
      //this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
    }

    this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
    this.tipoDocumentoSelected = new FormControl();
    this.descrEstesaTipoDocSelected = null;
    this.noteFile = '';
    this.fileInput.nativeElement.value = "";

  }



  reset() {
    this.dettaglioContratto = new ContrattoVO();
    this.bacinoGroup.controls['bacinoForm'].setValue(null);
    this.bacinoSelected = new FormControl();
    this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].setValue(null);
    this.tipoAffidamentoSelected = new FormControl();
    this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].setValue(null);
    this.modalitaAffidamentoSelected = new FormControl();
    this.tipologiaServizioSelected = new FormControl();
    //this.ambitoServizioSelected = new AmbitoServizioVO(); // DA SCOMMENTARE PER RIPRISTINARE LA VERSIONE CORRETTA
    this.ambitotipoServizioSelected = new Array<AmbTipServizioVO>(); //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
    this.enteCommittenteGroup.controls['enteCommittenteForm'].setValue(null);
    this.enteCommittenteSelected = new FormControl();
    this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(null);
    this.soggettoEsecutoreSelected = new FormControl();
    this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
    this.tipoSoggettoGiuridicoSelected = new FormControl();
    this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
    this.tipoRaggruppamentoSelected = new FormControl();
    this.tipoRaggruppamentoGroup.disable();
    this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
    this.aziendaMandatariaSelected = new FormControl();
    this.aziendaMandatariaGroup.disable();
    this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
    this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
    this.composizioneRaggGroup.disable();
    this.tipoDocumentoSelected = new FormControl();
    this.dataFin = null;
    this.dataIn = null;
    this.dataSt = null;
    this.dataToday = new Date();
    this.loadChoices();
    window.scrollTo(0, 0);
  }

  // Gestione allegati

  /*in: Date;
  fin:Date;

  before(): boolean{
    if(this.dataIn && this.dataFin){
      this.in = this.dataIn.toDate();
      this.fin = this.dataFin.toDate();
      if(this.fin<this.in)
        return true;
      return false;
    }
    return false;
  }*/
  feedback: string;
  save() {
    this.loadedSave = false;

    this.dettaglioContratto.idBacino = (this.bacinoGroup.controls['bacinoForm'].value != null ? this.bacinoGroup.controls['bacinoForm'].value.id : null);
    this.dettaglioContratto.idTipoAffidamento = (this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value != null ? this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value.id : null);
    this.dettaglioContratto.idModalitaAffidamento = (this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value != null ? this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value.id : null);
    /*this.dettaglioContratto.idTipologiaServizio = (this.tipologiaServizioGroup.controls['tipologiaServizioForm'].value != null ? this.tipologiaServizioGroup.controls['tipologiaServizioForm'].value.id : null);
    if(this.dettaglioContratto.idTipologiaServizio){
      this.dettaglioContratto.idAmbitoServizio = ( this.ambitoServizioGroup.controls['ambitoServizioForm'].value!= null ? this.ambitoServizioGroup.controls['ambitoServizioForm'].value.id : null);
    }*/
    this.dettaglioContratto.idSogGiuridCommittente = this.enteCommittenteGroup.controls['enteCommittenteForm'].value.id;
    this.dettaglioContratto.idSogGiuridEsecutore = this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].value.id;
    this.dettaglioContratto.idTipoSogGiuridEsec = this.tipoSoggGroup.controls['tipoSogg'].value.id;
    if (this.dettaglioContratto.idTipoSogGiuridEsec == 3) {
      this.dettaglioContratto.idTipoRaggrSogGiuridEsec = this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value.id;
      this.dettaglioContratto.idAziendaMandataria = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id;
      if (this.dettaglioContratto.contrattoRaggruppamentoVOs === null) {
        this.dettaglioContratto.contrattoRaggruppamentoVOs = new Array<ContrattoRaggruppamentoVO>();
      }
      for (let c of this.composizioneRaggSelected) {
        if (!this.dettaglioContratto.contrattoRaggruppamentoVOs.find(r => r.idSoggettoGiuridico == c.id)) {
          let contr = new ContrattoRaggruppamentoVO();
          contr.idSoggettoGiuridico = c.id;
          contr.capofila = false;
          this.dettaglioContratto.contrattoRaggruppamentoVOs.push(contr);
        }
      }
      /* for(let c of this.dettaglioContratto.contrattoRaggruppamentoVOs){
         if(!this.composizioneRaggSelected.find(comp=>comp.id==c.idSoggettoGiuridico && !c.capofila)){
           this.dettaglioContratto.contrattoRaggruppamentoVOs.splice(this.dettaglioContratto.contrattoRaggruppamentoVOs.findIndex(x=>x.idSoggettoGiuridico==c.idSoggettoGiuridico),1);
         }

       }*/
      /* if(!this.dettaglioContratto.contrattoRaggruppamentoVOs.find
         (comp=>comp.idSoggettoGiuridico==this.dettaglioContratto.idAziendaMandataria && comp.capofila)){*/
      // this.dettaglioContratto.contrattoRaggruppamentoVOs.splice(this.dettaglioContratto.contrattoRaggruppamentoVOs.findIndex(x=>x.capofila),1);
      let contr = new ContrattoRaggruppamentoVO();
      if (!this.dettaglioContratto.contrattoRaggruppamentoVOs.find(r => r.idSoggettoGiuridico == this.dettaglioContratto.idAziendaMandataria && r.capofila == true)) {
        this.dettaglioContratto.contrattoRaggruppamentoVOs.splice(this.dettaglioContratto.contrattoRaggruppamentoVOs.findIndex(x => x.capofila === true), 1);
        contr.idSoggettoGiuridico = this.dettaglioContratto.idAziendaMandataria;
        contr.capofila = true;
        this.dettaglioContratto.contrattoRaggruppamentoVOs.push(contr);
      }
      for (let c of this.dettaglioContratto.contrattoRaggruppamentoVOs) {
        if (!this.composizioneRaggSelected.find(r => r.id == c.idSoggettoGiuridico)) {
          if (c.capofila === false) {
            this.dettaglioContratto.contrattoRaggruppamentoVOs.splice(this.dettaglioContratto.contrattoRaggruppamentoVOs.findIndex(contr => contr.idSoggettoGiuridico === c.idSoggettoGiuridico), 1);
          }
        }
      }
      // }

    } else {
      this.dettaglioContratto.idTipoRaggrSogGiuridEsec = null;
      this.dettaglioContratto.idAziendaMandataria = null;
      this.dettaglioContratto.contrattoRaggruppamentoVOs = new Array<ContrattoRaggruppamentoVO>();;
    }
    if (this.numRepertorioTmp != null && this.numRepertorioTmp != "") {
      this.dettaglioContratto.numRepertorio = this.numRepertorioTmp;
    }
    if (this.dataSt) {
      this.dettaglioContratto.dataStipula = this.dataSt.toDate();
    }
    this.dettaglioContratto.dataInizioValidita = this.dataIn.toDate();
    if (this.dataFin != null) {
      this.dettaglioContratto.dataFineValidita = this.dataFin.toDate();
    }

    this.dettaglioContratto.ambTipServizio = new Array<AmbTipServizioVO>();
    for (let v of this.ambitotipoServizioSelected) { //DA CANCELLARE PER RIPRISTINARE LA VERSIONE CORRETTA
      this.dettaglioContratto.ambTipServizio.push(v);
    }
    /*
        this.dettaglioContratto.idSogGiuridCommittente = this.enteCommittenteGroup.controls['enteCommittenteForm'].value.id;
        this.dettaglioContratto.idSogGiuridEsecutore = this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].value.id;
        this.dettaglioContratto.idTipoSogGiuridEsec = this.tipoSoggGroup.controls['tipoSogg'].value.id;
        if(this.dettaglioContratto.idTipoSogGiuridEsec == 3){
          this.dettaglioContratto.idTipoRaggrSogGiuridEsec = this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value.id;
          this.dettaglioContratto. = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id;
          this.dettaglioContratto.idComposizioneRaggruppamento = this.composizioneRaggGroup.controls['composizioneRaggForm'].value.id;
        }*/
    if (this.proroga.attoProroga != null && this.proroga.attoProroga.length > 0 && this.dataPr != null) {
      this.proroga.dataFineProroga = this.dataPr.toDate();
      this.addProroghe();
    }
    if (!this.isAddSubentriDisabled()) {
      this.addSubentri();
    }

    for (let i in this.dettaglioContratto.subentriSubaffidamenti) {
      if (this.dataPr && this.dataPr.isValid()) {
        if (this.dettaglioContratto.subentriSubaffidamenti[i].dataFine != null && this.dettaglioContratto.subentriSubaffidamenti[i].dataFine.getTime() == this.dataPr.toDate().getTime()) {
          this.dettaglioContratto.subentriSubaffidamenti[i].dataFine = null;
        }
      } else {
        const data1 = new Date(this.dettaglioContratto.subentriSubaffidamenti[i].dataFine);
        const data2 = new Date(this.dataFineContratto.toDate());
        if (data1.getTime() === data2.getTime()) {
          this.dettaglioContratto.subentriSubaffidamenti[i].dataFine = null;
        }
      }
    }
    //this.dettaglioContratto.allegati = this.dettaglioContratto.allegati.filter(a => !a.idAllegato);

    this.contrattoService.modificaContratto(this.dettaglioContratto).subscribe(data => {
      this.loadedSave = true;
      this.loadChoices();
      this.contraentiSubGroupsChange = this.contraentiSubGroups;
      this.feedback = "";
      window.scrollTo(0, 0);
      this.soggettoContraenteFormGroup.controls['subGroup1'].reset();
      setTimeout(() => {
        this.formGroup.form.controls['dataSub'].setErrors(null);
        if (this.formGroup.form.get('dataFineSub')) {
          this.formGroup.form.controls['dataFineSub'].setErrors(null);
        };
      }, 200)
      //this.formGroup.form.clearAsyncValidators();
      //this.formGroup.form.clearValidators();
      this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
        duration: 2000,
      });
    }, err => {
      CommonsHandleException.authorizationError(err, this.router);
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      this.dettaglioContratto.ambTipServizio = new Array<AmbTipServizioVO>();
      if (errorRest.type == TypeErrorRest.OK) {
        this.feedback = errorRest.message;
      }
      else {
        this.feedback = "Si è verificato un errore in fase di salvataggio";
      }
      console.error("this.feedback =" + this.feedback);
      window.scrollTo(0, 0);
      this.loadedSave = true;
      if (errorRest.codice == 'TFND') {
        setTimeout(() => { this.formGroup.controls['CodIdentNazionale'].setErrors({ duplicato: true }) }, 200);
      }
    });
  }

  elimina(idAllegato: number) {
    this.dettaglioContratto.allegati.splice(this.dettaglioContratto.allegati.findIndex(a => a.idAllegato == idAllegato), 1);
    //this.arrayAllegatiFiltrati.splice(this.arrayAllegatiFiltrati.findIndex(a => a == idTipoDocumento), 1);
    //this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
  }

  download(idDocumento: number) {
    this.contrattoService.getContenutoDocumentoById(this.dettaglioContratto.idContratto, idDocumento)
      .subscribe(
        res => {
          saveAs(res, this.dettaglioContratto.allegati.find(a => a.idAllegato == idDocumento).nomeFile);
        },
        err => {
          let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
          console.error(errorRest.message);
        }
      );
  }

  downloadIns(idAllegatoTmp: number) {
    let file = this.dettaglioContratto.allegati.find(f => f.idAllegatoTmp == idAllegatoTmp);
    saveAs(file.file, file.nomeFile);
  }


  rinnovaContratto() {
    this.contrattoService.creaRinnovoContratto(this.id).subscribe(data => {
      this.loadedSave = true;
      this.feedback = "";
      window.scrollTo(0, 0);
      this.snackBar.open("Rinnovo Completato!", "Chiudi", {
        duration: 2000,
      });

      setTimeout(() => { this.router.navigate(['/modificaContratto/' + data, { action: 'inserisci' }]) }, 2000);
    }, err => {
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      this.dettaglioContratto.ambTipServizio = new Array<AmbTipServizioVO>();
      if (errorRest.type == TypeErrorRest.OK) {
        this.feedback = errorRest.message;
      }
      else {
        this.feedback = "Si è verificato un errore in fase di salvataggio";
      }
      console.error("this.feedback =" + this.feedback);
      window.scrollTo(0, 0);
      this.loadedSave = true;
    });

  }

  isLoading() {
    if (!this.loadedSave) return true;
    if (!this.loadComplete || !this.loadedBacini || !this.loadedTipiAffidamento || !this.loadedModalitaAffidamento || !this.loadedTipoServ
      || !this.loadedEnti || !this.loadedSoggettiEsecutori || !this.loadedTipiSoggettoGiuridico || !this.loadedTipiDocumento || !this.loadedAziendaMandataria
      || !this.loadedtipoRaggr)
      return true;
    return false;
  }

  getStyle() {
    if (this.isValidCampiRequired()) {
      return 'rgb(99, 97, 97);';
    }

    return 'white';
  }

  getBackground() {
    if (this.isValidCampiRequired()) {
      return 'rgb(192, 192, 192)';
    }
    return 'rgb(214,87,42)';
  }

  /*ngAfterViewInit(){
   let interval = setInterval(function() {
      clearInterval(interval);
    },1000);

   if(this.ambTipoServizioGroup){
    this.ambTipoServizioGroup.controls['ambTipoServizioForm'].disable();
   }
  }*/
  ngAfterContentChecked() {
    this.cdRef.detectChanges();
    if (this.dettaglioContratto.proroghe != null) {
      for (let i = 0; i < this.dettaglioContratto.proroghe.length; i++) {
        this.autogrow(i);
      }
    }
    if (this.loadedEnti && this.loadedSoggettiEsecutori && this.loadedAziendaMandataria && this.loadedTipiSoggettoGiuridico && this.loadedtipoRaggr && this.isFirstTime) {
      this.isFirstTime = false;
      this.refreshDirective();
      /*this.contraentiSubGroups.push(
        {
          name: 'Ente Committente',
          soggetti: [
            { id: this.enteCommittenteSelected.value.id, denominazione: this.enteCommittenteSelected.value.denominazione, idTipoSoggetto: 1, group: "EC" }
          ]
        });*/
      this.contraentiSubGroupsFiltered.push(
        {
          name: 'Ente Committente',
          soggetti: [
            { id: this.enteCommittenteSelected.value.id, idRuolo: this.enteCommittenteSelected.value.idRuolo, denominazione: this.enteCommittenteSelected.value.denominazione, idTipoSoggetto: 1, group: "EC" }
          ]
        });
      this.contraentiSubGroups.push(
        {
          name: 'Esecutore Titolare',
          soggetti: [
            { id: this.soggettoEsecutoreSelected.value.id, idRuolo: this.soggettoEsecutoreSelected.value.idRuolo, denominazione: this.soggettoEsecutoreSelected.value.denominazione, idTipoSoggetto: this.soggettoEsecutoreSelected.value.idTipoSoggettoEsecutore, group: "ET" }
          ]
        });
      this.contraentiSubGroupsFiltered.push(
        {
          name: 'Esecutore Titolare',
          soggetti: [
            { id: this.soggettoEsecutoreSelected.value.id, idRuolo: this.soggettoEsecutoreSelected.value.idRuolo, denominazione: this.soggettoEsecutoreSelected.value.denominazione, idTipoSoggetto: this.soggettoEsecutoreSelected.value.idTipoSoggettoEsecutore, group: "ET" }
          ]
        });
      if (this.hasRaggruppamento) {
        let soggSub = new Array<SoggettoSub>();
        let soggSubFiltered = new Array<SoggettoSub>();
        if (this.aziendaMandatariaSelected.value.id != this.soggettoEsecutoreSelected.value.id) {
          soggSub.push({
            id: this.aziendaMandatariaSelected.value.id,
            idRuolo: this.aziendaMandatariaSelected.value.idRuolo,
            denominazione: this.aziendaMandatariaSelected.value.descrizione,
            idTipoSoggetto: 2,
            group: "R"
          });
          soggSubFiltered.push({
            id: this.aziendaMandatariaSelected.value.id,
            idRuolo: this.aziendaMandatariaSelected.value.idRuolo,
            denominazione: this.aziendaMandatariaSelected.value.descrizione,
            idTipoSoggetto: 2,
            group: "R"
          });
        }
        for (let c of this.composizioneRaggSelected) {
          soggSub.push({
            id: c.id,
            idRuolo: c.idRuolo,
            denominazione: c.descrizione,
            idTipoSoggetto: 2,
            group: "R"
          });
          soggSubFiltered.push({
            id: c.id,
            idRuolo: c.idRuolo,
            denominazione: c.descrizione,
            idTipoSoggetto: 2,
            group: "R"
          });
        }
        this.contraentiSubGroups.push(
          {
            name: 'Raggruppamento',
            soggetti: soggSub
          });

        this.contraentiSubGroupsFiltered.push(
          {
            name: 'Raggruppamento',
            soggetti: soggSubFiltered
          });
      }
      // rimuovo il raggruppamento per il subaffidamento
      this.contraentiSubaffidamentoGroups = JSON.parse(JSON.stringify(this.contraentiSubGroups));
      this.contraentiSubaffidamentoGroups = this.contraentiSubaffidamentoGroups.filter(a => a.name == 'Esecutore Titolare');
      this.contraentiSubGroupsChange = this.contraentiSubaffidamentoGroups;
      for (let s of this.dettaglioContratto.subentriSubaffidamenti) {
        if (s.tipoSostituzione.id === 2) { //SUBENTRO
          if (s.contraenteGroup === 'EC') {
            this.contraentiSubGroupsFiltered.splice(this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Ente Committente'), 1);
          } else if (s.contraenteGroup === 'ET') {
            this.contraentiSubGroupsFiltered.splice(this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Esecutore Titolare'), 1);
          }
          else if (s.contraenteGroup === 'R') {
            let soggettiSubentro = this.contraentiSubGroupsFiltered[this.contraentiSubGroupsFiltered.findIndex(a => a.name === 'Raggruppamento')].soggetti;
            let soggettiSomministrazione = this.contraentiSubGroups[this.contraentiSubGroups.findIndex(a => a.name === 'Raggruppamento')].soggetti;
            // Rimuove i soggetti contraenti presenti in subentri precedenti
            // Combo dei subentri
            soggettiSubentro.splice(soggettiSubentro.findIndex(a => a.id === s.soggettoContraente.id), 1);
            // Combo delle somministrazioni di personale
            soggettiSomministrazione.splice(soggettiSomministrazione.findIndex(a => a.id === s.soggettoContraente.id), 1);
            // soggetti.splice(soggetti.findIndex(a => a.id === s.id), 1);
          }
        }
      }

      this.checkSoggettoEsecutore();
      if (this.tipoSoggettoGiuridicoSelected.value != null) {
        this.checkTipoSoggettoGiuridico();
        if (this.tipoSoggettoGiuridicoSelected.value.id == 3) {
          this.checkTipoRaggruppamento();
          // this.checkAziendaMandataria();
        }
      }


    }
  }

  //pulisce input data se passo un parametro di tipo stringa
  checkData(data: Date, name: string) {
    if (data == null)
      this.formGroup.form.get(name).setValue(null);
  }



}


export enum Action {
  EDIT = "E",
  VIEW = "V",
}
