/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { InserisciContrattoVO } from '../../vo/inserisci-contratto-vo';
import { BacinoVO, TipoAffidamentoVO, ModalitaAffidamentoVO, TipologiaServizioVO, AmbitoServizioVO, TipoRaggruppamentoVO, ComposizioneRaggruppamentoVO, TipoDocumentoVO, AziendaMandatariaVO } from '../../vo/drop-down-menu-vo';
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
import { EnteVO } from 'src/app/vo/ente-vo';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { AllegatoVO } from '../../vo/allegato-vo';
import { Router } from '@angular/router';
import { AmbTipServizioVO } from '../../vo/amb-tip-servizio-vo';
import { of } from 'rxjs';

@Component({
  selector: 'app-inserisci-contratto',
  templateUrl: './inserisci-contratto.component.html',
  styleUrls: ['./inserisci-contratto.component.scss'],
})
export class InserisciContrattoComponent implements OnInit {
  tipoSoggettoEsecutore: any;
  @ViewChild('fileInput') fileInput: ElementRef;

  constructor(
    private dateAdapter: DateAdapter<Date>,
    private contrattoService: ContrattoService,
    private enteService: EnteService,
    private soggettoService: SoggettoService,
    public snackBar: MatSnackBar,
    private router: Router,
    public dialog: MatDialog) {
    dateAdapter.setLocale('it-IT');
  }

  loadedSave: boolean = true;

  contratto: InserisciContrattoVO;
  bacino: Array<BacinoVO>;
  tipoAffidamento: Array<TipoAffidamentoVO>;
  modalitaAffidamento: Array<ModalitaAffidamentoVO>;
  tipologiaServizio: Array<TipologiaServizioVO>;
  ambitoTipoServizio: Array<AmbTipServizioVO>;
  ambitoServizio: Array<AmbitoServizioVO>;
  enteCommittente: Array<EnteVO>;
  soggettoEsecutore: Array<SoggettoEsecutoreVO>;
  tipoRaggruppamento: Array<TipoRaggruppamentoVO>;
  aziendaMandataria: Array<AziendaMandatariaVO>;
  composizioneRaggruppamento: Array<ComposizioneRaggruppamentoVO>;
  tipiSoggettoGiuridico: Array<TipoSoggettoGiuridicoVO>;
  nomeAllegatoTmp: string;
  tipiDocumento: Array<TipoDocumentoVO>;
  tipiDocCaricati = new Array<TipoDocumentoVO>();
  descrEstesaTipoDocSelected: string;
  allegati = new Array<AllegatoVO>();
  arrayAllegatiFiltrati: Array<number>;
  noteFile: string = "";

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

  ambitotipoServizioSelected = new Array<AmbTipServizioVO>();
  filteredOptionsAmbitoTipoServizio: Observable<AmbTipServizioVO[]>;
  ambTipoServizioGroup: FormGroup = new FormGroup({ ambTipoServizioForm: new FormControl() });

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


  dataSt: Moment;
  dataIn: Moment;
  dataFin: Moment;
  dataToday: Date = new Date();

  ngOnInit() {
    window.scroll(0, 0);
    this.contratto = new InserisciContrattoVO();
    this.ambitoServizioGroup.disable();
    this.tipoRaggruppamentoGroup.disable();
    this.aziendaMandatariaGroup.disable();
    this.composizioneRaggGroup.disable();
    this.loadChoices();
  }

  loadedBacini = false;
  loadedTipiAff = false;
  loadedModAff = false;
  loadedTipoServ = false;
  loadedEnti = false;
  loadedEsec = false;
  loadedTipiSogg = false;

  loadChoices() {
    this.contrattoService.getBacini().subscribe(data => {
      if (data) {
        this.bacino = data;
        this.filteredOptionsBacino = this.bacinoGroup.controls['bacinoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterBacino(name) : this.bacino.slice())
          );
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
      }
      this.loadedTipiAff = true;
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
      }
      this.loadedModAff = true;
    });
    this.contrattoService.getAmbitoTipoServizio().subscribe(data => {
      if (data) {
        this.ambitoTipoServizio = data;
        this.filteredOptionsAmbitoTipoServizio = this.ambTipoServizioGroup.controls['ambTipoServizioForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterAmbitoTipoServizio(name) : this.ambitoTipoServizio.slice())
          );
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
      }
      this.loadedEnti = true;
    });
    this.soggettoService.getSoggettiEsecutoriTitolari(true, null).subscribe(data => {
      if (data) {
        this.soggettoEsecutore = data;
        this.filteredOptionsSoggettoEsecutore = this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterSoggettoEsecutore(name) : this.soggettoEsecutore.slice())
          );
      }
      this.loadedEsec = true;
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
      }
      this.loadedTipiSogg = true;
    });

    this.contrattoService.getTipiDocumento().subscribe(data => {
      if (data) {
        this.tipiDocumento = data;
        this.filteredOptionsTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipoDocumento(name) : this.tipiDocumento.slice())
          );
      }

    });
    this.arrayAllegatiFiltrati = new Array<number>();
    this.tipoDocumentoGroup.controls['tipoDocumentoForm'];
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

  _filterTipologiaServizio(descrizione: string): TipologiaServizioVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipologiaServizio.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  _filterAmbitoTipoServizio(descrizione: string): AmbTipServizioVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.ambitoTipoServizio.filter(option => option.tipologiaServizio.descrizione.toLowerCase().includes(filterValue));
  }

  _filterAmbitoServizio(descrizione: string): AmbitoServizioVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.ambitoServizio.filter(option => option.descrizione.toLowerCase().includes(filterValue));
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
    return this.tipiDocumento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  click(event: any, s: string) {
    if (s == 'Ba')
      this.bacinoSelected = event.option.value;
    else if (s == 'Ta')
      this.tipoAffidamentoSelected = event.option.value;
    else if (s == 'Ma')
      this.modalitaAffidamentoSelected = event.option.value;
    else if (s == 'Ts') {
      this.tipologiaServizioSelected = event.option.value;
    }
    else if (s == 'As')
      this.ambitoServizioSelected = event.option.value;
    else if (s == 'Ec')
      this.enteCommittenteSelected = event.option.value;
    else if (s == 'Se') {
      this.soggettoEsecutoreSelected.setValue(event.option.value);
      this.contratto.idSoggettoEsecutore = event.option.value.id;
      this.tipoSoggettoEsecutore = event.option.value.idRuolo;
      if (this.tipoSoggettoEsecutore == 2) {
        this.filteredOptionsTipo = of(this.tipiSoggettoGiuridico.filter(a => a.id != 1));
        this.tipoSoggGroup.enable();
        if (this.tipoSoggGroup.controls['tipoSogg'].value != null && this.tipoSoggGroup.controls['tipoSogg'].value.id == 1) {
          this.tipoSoggettoGiuridicoSelected = new FormControl();
          this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
        }
        if (this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value != null && this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value.id == 1) { //consorzio
          this.aziendaMandatariaSelected.setValue(this.aziendaMandataria.find(a => a.id == this.contratto.idSoggettoEsecutore));
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(this.aziendaMandataria.find(a => a.id == this.contratto.idSoggettoEsecutore));
          this.aziendaMandatariaGroup.disable();
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
          this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.contratto.idSoggettoEsecutore);
          this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
            );

          this.composizioneRaggGroup.enable();
        }
        if (this.composizioneRaggruppamento) {
          this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.contratto.idSoggettoEsecutore);
        }
      }

    }
    else if (s == 'T') {
      this.tipoSoggettoGiuridicoSelected = event.option.value;
      if (event.option.value.id == 3) { //ID_TIPO_SOG=RAGGRUPPAMENTO
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

        this.contrattoService.getAziendaMandataria(null).subscribe(data => {
          if (data) {
            this.aziendaMandataria = data;
            this.filteredOptionsAziendaMandataria = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].valueChanges
              .pipe(
                startWith(''),
                map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
                map(name => name ? this._filterAziendaMandataria(name) : this.aziendaMandataria.slice())
              );
            this.aziendaMandatariaGroup.enable();
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
      }
    }
    else if (s == 'Tr') {
      this.tipoRaggruppamentoSelected = event.option.value;
      this.contratto.idTipoRaggruppamento = event.option.value.id;
      //se seleziono "consorzio" come tipo raggruppamento devo prevalorizzare aziedna mandataria
      if (event.option.value.id == 1) {
        if (this.contratto.idSoggettoEsecutore != null) {
          this.composizioneRaggGroup.disable();
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
          this.aziendaMandatariaSelected = new FormControl();
          this.aziendaMandatariaSelected.setValue(this.aziendaMandataria.find(a => a.id == this.contratto.idSoggettoEsecutore));
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(this.aziendaMandataria.find(a => a.id == this.contratto.idSoggettoEsecutore));
          this.composizioneRaggruppamento = new Array<ComposizioneRaggruppamentoVO>();
          for (let a of this.aziendaMandataria) {
            let c = new ComposizioneRaggruppamentoVO();
            c.id = a.id;
            c.descrizione = a.descrizione;
            c.selected = false;
            this.composizioneRaggruppamento.push(c);
          }
          this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.aziendaMandataria.find(a => a.id == this.contratto.idSoggettoEsecutore).id);
          this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
            .pipe(
              startWith(''),
              map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
              map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
            );
          this.composizioneRaggGroup.enable();
        }
        else if (this.contratto.idAziendaMandataria != null) {
          this.soggettoEsecutoreSelected.setValue(this.soggettoEsecutore.find(a => a.id == this.contratto.idAziendaMandataria));
          this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(this.soggettoEsecutore.find(a => a.id == this.contratto.idAziendaMandataria));
        }
        this.aziendaMandatariaGroup.disable();
      }
      else this.aziendaMandatariaGroup.enable();


    }
    else if (s == 'Am') {
      this.composizioneRaggGroup.disable();
      this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
      this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
      this.aziendaMandatariaSelected = event.option.value;
      this.contratto.idAziendaMandataria = event.option.value.id;
      if (this.contratto.idTipoRaggruppamento == 1) {
        if (this.contratto.idSoggettoEsecutore == null) {
          this.soggettoEsecutoreSelected.setValue(this.soggettoEsecutore.find(a => a.id == this.contratto.idAziendaMandataria));
          this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(this.soggettoEsecutore.find(a => a.id == this.contratto.idAziendaMandataria));
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
      this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.contratto.idAziendaMandataria);
      if (this.contratto.idAziendaMandataria && this.contratto.idSoggettoEsecutore && this.contratto.idAziendaMandataria != this.contratto.idSoggettoEsecutore) {
        this.composizioneRaggruppamento = this.composizioneRaggruppamento.filter(a => a.id != this.contratto.idSoggettoEsecutore);
      }
      this.filteredOptionsComposizioneRagg = this.composizioneRaggGroup.controls['composizioneRaggForm'].valueChanges
        .pipe(
          startWith(''),
          map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
          map(name => name ? this._filterComposizioneRagg(name) : this.composizioneRaggruppamento.slice())
        );
      this.composizioneRaggGroup.enable();
    }
    else if (s == 'Td') {
      this.tipoDocumentoSelected = event.option.value;
      this.descrEstesaTipoDocSelected = event.option.value.descrizioneEstesa;
    }
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

    if (this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value != null && comp.id == this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id && comp.selected) {
      this.openDialog('Errore!', 'Azienda già selezionata come Mandataria');
      this.composizioneRaggSelected.pop();
    }

  }

  optionClickedAmbTipoServizio(event: Event, comp: AmbTipServizioVO) {
    event.stopPropagation();
    this.toggleSelectionAmbTipServizio(comp);
  }

  toggleSelectionAmbTipServizio(comp: AmbTipServizioVO) {
    comp.selected = !comp.selected;
    if (comp.selected) {
      this.ambitotipoServizioSelected.push(comp);
    } else {
      const i = this.ambitotipoServizioSelected.findIndex(value => value.idAmbTipServizio === comp.idAmbTipServizio);
      this.ambitotipoServizioSelected.splice(i, 1);
    }

    this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(this.ambitotipoServizioSelected);

  }

  check(s: string) {
    setTimeout(() => {
      if (s == 'Ba') {
        if (!this.bacinoSelected || (this.bacinoGroup.controls['bacinoForm'] && this.bacinoSelected !== this.bacinoGroup.controls['bacinoForm'].value)) {
          this.bacinoGroup.controls['bacinoForm'].setValue(null);
          this.bacinoSelected = new FormControl();
        }
      }
      else if (s == 'Ta') {
        if (!this.tipoAffidamentoSelected || (this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'] && this.tipoAffidamentoSelected !== this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value)) {
          this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].setValue(null);
          this.tipoAffidamentoSelected = new FormControl();
        }
      }
      else if (s == 'Ma') {
        if (!this.modalitaAffidamentoSelected || (this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'] && this.modalitaAffidamentoSelected !== this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value)) {
          this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].setValue(null);
          this.modalitaAffidamentoSelected = new FormControl();
        }
      }
      else if (s == 'Ts') {
        if (!this.tipologiaServizioSelected || (this.tipologiaServizioGroup.controls['tipologiaServizioForm'] && this.tipologiaServizioSelected !== this.tipologiaServizioGroup.controls['tipologiaServizioForm'].value)) {
          this.tipologiaServizioGroup.controls['tipologiaServizioForm'].setValue(null);
          this.tipologiaServizioSelected = new FormControl();
          this.ambitoServizioGroup.disable();
        }
      }
      else if (s == 'AmbTip') {
        if (!this.ambitotipoServizioSelected || this.ambitotipoServizioSelected.length == 0) {
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(null);
        }
        else if (this.ambTipoServizioGroup.controls['ambTipoServizioForm'] && this.ambTipoServizioGroup.controls['ambTipoServizioForm'].value !== this.displayFnats(this.ambitotipoServizioSelected)) {
          this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(this.ambitotipoServizioSelected);
        }
      }
      else if (s == 'As') {
        if (!this.ambitoServizioSelected || (this.ambitoServizioGroup.controls['ambitoServizioForm'] && this.ambitoServizioSelected !== this.ambitoServizioGroup.controls['ambitoServizioForm'].value)) {
          this.ambitoServizioGroup.controls['ambitoServizioForm'].setValue(null);
          this.ambitoServizioSelected = new FormControl();
        }
      }
      else if (s == 'Ec') {
        if (!this.enteCommittenteSelected || (this.enteCommittenteGroup.controls['enteCommittenteForm'] && this.enteCommittenteSelected !== this.enteCommittenteGroup.controls['enteCommittenteForm'].value)) {
          this.enteCommittenteGroup.controls['enteCommittenteForm'].setValue(null);
          this.enteCommittenteSelected = new FormControl();
        }
      }
      else if (s == 'Se') {
        if (!this.soggettoEsecutoreSelected || (this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'] && this.soggettoEsecutoreSelected.value !== this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].value)) {
          this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
          this.tipoSoggettoGiuridicoSelected = new FormControl();
          this.tipoSoggGroup.enable();
          this.filteredOptionsTipo = of(this.tipiSoggettoGiuridico);
          this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].setValue(null);
          this.soggettoEsecutoreSelected = new FormControl();
          this.contratto.idSoggettoEsecutore = null;

          this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
          this.tipoRaggruppamentoSelected = new FormControl();
          this.tipoRaggruppamentoGroup.disable();
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
          this.aziendaMandatariaSelected = new FormControl();
          this.aziendaMandatariaGroup.disable();
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
          this.composizioneRaggGroup.disable();
        }
      }
      else if (s == 'T') {
        if (!this.tipoSoggettoGiuridicoSelected || (this.tipoSoggGroup.controls['tipoSogg'] && this.tipoSoggettoGiuridicoSelected !== this.tipoSoggGroup.controls['tipoSogg'].value)) {
          this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
          this.tipoSoggettoGiuridicoSelected = new FormControl();
          this.tipoRaggruppamentoGroup.disable();
          this.aziendaMandatariaGroup.disable();
          this.composizioneRaggGroup.disable();
        }
      }
      else if (s == 'Tr') {
        if (!this.tipoRaggruppamentoSelected || (this.tipoRaggruppamentoGroup.controls['tipoRaggForm'] && this.tipoRaggruppamentoSelected !== this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value)) {
          this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].setValue(null);
          this.tipoRaggruppamentoSelected = new FormControl();
          this.contratto.idTipoRaggruppamento = null;
          this.aziendaMandatariaGroup.enable();
        }
      }
      else if (s == 'Am') {
        if (!this.aziendaMandatariaSelected || (this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'] && this.aziendaMandatariaSelected !== this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value)) {
          this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].setValue(null);
          this.aziendaMandatariaSelected = new FormControl();
          this.contratto.idAziendaMandataria = null;
          this.composizioneRaggGroup.disable();
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
          this.composizioneRaggSelected = new Array<ComposizioneRaggruppamentoVO>();
        }
      }
      else if (s == 'Cr') {
        if (!this.composizioneRaggSelected || this.composizioneRaggSelected.length == 0) {
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(null);
        }
        else if (this.composizioneRaggGroup.controls['composizioneRaggForm'] && this.composizioneRaggGroup.controls['composizioneRaggForm'].value !== this.displayFncr(this.composizioneRaggSelected)) {
          this.composizioneRaggGroup.controls['composizioneRaggForm'].setValue(this.composizioneRaggSelected);
        }
      }
      else if (s == 'Td') {
        if (!this.tipoDocumentoSelected || (this.tipoDocumentoGroup.controls['tipoDocumentoForm'] && this.tipoDocumentoSelected !== this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value)) {
          this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
          this.tipoDocumentoSelected = new FormControl();
          this.descrEstesaTipoDocSelected = null;
        }
      }
    }, 200);
  }

  displayFn(a?: any): string | undefined {
    return a ? a.descrizione : undefined;
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

  displayFnats(a?: any): string | undefined { //per ambito tipologia servizi
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
  openDialog(type: string, message: string): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: { type: type, message: message }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  @ViewChild('contrattoForm') formGroup: NgForm;
  isValidCampiRequired() {
    if (this.formGroup && this.formGroup.form) {
      if (this.formGroup.form.get('descrizione') && this.formGroup.form.get('descrizione').hasError('required')) return true;
      if (this.formGroup.form.get('NumRepertorio') && this.formGroup.form.get('NumRepertorio').hasError('required')) return true;
      if (this.bacinoGroup.get('bacinoForm') && this.bacinoGroup.get('bacinoForm').hasError('required')) return true;
      if (this.formGroup.form.get('dataStipula') && this.formGroup.form.get('dataStipula').hasError('required')) return true;
      if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('required')) return true;
      if (this.formGroup.form.get('dataFine') && this.formGroup.form.get('dataFine').hasError('required')) return true;
      if (this.ambTipoServizioGroup.get('ambTipoServizioForm') && this.ambTipoServizioGroup.get('ambTipoServizioForm').hasError('required')) return true;
      if (this.enteCommittenteGroup.get('enteCommittenteForm') && this.enteCommittenteGroup.get('enteCommittenteForm').hasError('required')) return true;
      if (this.soggettoEsecutoreGroup.get('soggettoEsecutoreForm') && this.soggettoEsecutoreGroup.get('soggettoEsecutoreForm').hasError('required')) return true;
      if (this.tipoSoggGroup.get('tipoSogg') && this.tipoSoggGroup.get('tipoSogg').hasError('required')) return true;
      if (this.tipoSoggGroup.get('tipoSogg').value.id == 3) {
        if (this.tipoRaggruppamentoGroup.get('tipoRaggForm') && this.tipoRaggruppamentoGroup.get('tipoRaggForm').hasError('required')) return true;
        if (this.aziendaMandatariaGroup.get('aziendaMandatariaForm') && this.aziendaMandatariaGroup.get('aziendaMandatariaForm').hasError('required')) return true;
        if (this.composizioneRaggGroup.get('composizioneRaggForm') && this.composizioneRaggGroup.get('composizioneRaggForm').hasError('required')) return true;
      }
    }

    if (this.formGroup.form.get('dataFine') && this.formGroup.form.get('dataFine').hasError('matDatepickerMin')) return true;
    if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('matDatepickerMin')) return true;

    return false;
  }
  isCampiInvalid() {
    return false;
  }
  dataOggi: string = "";
  handleFileInput(files: Array<File>) {
    for (let f of files) {
      let allegato = new AllegatoVO();
      allegato.idTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value.id;
      allegato.noteFile = this.noteFile;
      allegato.file = f;
      allegato.nomeFile = f.name;
      allegato.descrizioneTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value.descrizione;
      allegato.dataCaricamento = new Date();
      this.dataOggi = this.dataToday.getDate() + "/" + (this.dataToday.getMonth() + 1).toString() + "/" + this.dataToday.getFullYear();
      this.contratto.files.unshift(allegato); //l'ultimo allegato inserito deve essere il primo delll'array
    }

    this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
    this.tipoDocumentoSelected = new FormControl();
    this.descrEstesaTipoDocSelected = null;
    this.noteFile = '';
    this.fileInput.nativeElement.value = "";
  }


  reset() {
    this.contratto = new InserisciContrattoVO();
    this.bacinoGroup.controls['bacinoForm'].setValue(null);
    this.bacinoSelected = new FormControl();
    this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].setValue(null);
    this.tipoAffidamentoSelected = new FormControl();
    this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].setValue(null);
    this.modalitaAffidamentoSelected = new FormControl();
    this.ambTipoServizioGroup.controls['ambTipoServizioForm'].setValue(null);
    this.ambitotipoServizioSelected = new Array<AmbTipServizioVO>();
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
    this.dataFin = null;
    this.dataIn = null;
    this.dataSt = null;
    this.dataToday = new Date();



    this.loadChoices();
    window.scrollTo(0, 0);
  }
  feedback: string;
  save() {
    this.loadedSave = false;
    this.contratto.idBacino = (this.bacinoGroup.controls['bacinoForm'].value != null ? this.bacinoGroup.controls['bacinoForm'].value.id : null);
    this.contratto.idTipoAffidamento = (this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value != null ? this.tipoAffidamentoGroup.controls['tipoAffidamentoForm'].value.id : null);
    this.contratto.idModalitaAffidamento = (this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value != null ? this.modalitaAffidamentoGroup.controls['modalitaAffidamentoForm'].value.id : null);
    if (this.dataSt != null) {
      this.contratto.dataStipula = this.dataSt.toDate();
    }
    this.contratto.dataInizioValidita = this.dataIn.toDate();
    if (this.dataFin != null) {
      this.contratto.dataFineValidita = this.dataFin.toDate();
    }
    this.contratto.idEnteCommittente = this.enteCommittenteGroup.controls['enteCommittenteForm'].value.id;
    this.contratto.idSoggettoEsecutore = this.soggettoEsecutoreGroup.controls['soggettoEsecutoreForm'].value.id;
    this.contratto.idTipoSoggettoEsecutore = this.tipoSoggGroup.controls['tipoSogg'].value.id;
    for (let v of this.ambitotipoServizioSelected) {
      this.contratto.idAmbTipoServizi.push(v.idAmbTipServizio);
    }
    if (this.contratto.idTipoSoggettoEsecutore == 3) {
      this.contratto.idTipoRaggruppamento = this.tipoRaggruppamentoGroup.controls['tipoRaggForm'].value.id;
      this.contratto.idAziendaMandataria = this.aziendaMandatariaGroup.controls['aziendaMandatariaForm'].value.id;
      for (let c of this.composizioneRaggSelected) {
        this.contratto.idComposizioneRaggruppamento.push(c.id);
      }
    }

    this.contrattoService.inserisciContratto(this.contratto).subscribe(data => {
      this.loadedSave = true;

      this.feedback = "";
      let id: number = data;
      window.scrollTo(0, 0);
      this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
        duration: 2000,
      });

      setTimeout(() => { this.router.navigate(['/modificaContratto/' + id, { action: 'inserisci' }]) }, 2000);
    }, err => {
      CommonsHandleException.authorizationError(err, this.router);
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      this.contratto.idAmbTipoServizi = new Array<number>();
      this.contratto.idComposizioneRaggruppamento = new Array<number>();
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

  isLoading() {
    if (!this.loadedSave) return true;
    if (!this.loadedBacini || !this.loadedEnti || !this.loadedEsec || !this.loadedModAff || !this.loadedTipiAff || !this.loadedTipiSogg || !this.loadedTipoServ) return true;

    return false;
  }

  download(idTipoDocumento: number) {
    let file = this.contratto.files.find(f => f.idTipoDocumento == idTipoDocumento);
    saveAs(file.file, file.nomeFile);
  }

  elimina(idTipoDocumento: number) {
    this.contratto.files.splice(this.contratto.files.findIndex(a => a.idTipoDocumento == idTipoDocumento), 1);
    this.arrayAllegatiFiltrati.splice(this.arrayAllegatiFiltrati.findIndex(a => a == idTipoDocumento), 1);
    this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
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

  //pulisce input data se passo un parametro di tipo stringa
  checkData(data: Date, name: string) {
    if (data == null)
      this.formGroup.form.get(name).setValue(null);

  }
}
