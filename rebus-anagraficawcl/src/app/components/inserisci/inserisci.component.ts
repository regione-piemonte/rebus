/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { InserisciSoggettoGiuridicoVO } from '../../vo/inserisci-soggetto-giuridico-vo';
import { TipoSoggettoGiuridicoVO } from '../../vo/tipo-soggetto-giuridico-vo';
import { NaturaGiuridicaVO } from '../../vo/natura-giuridica-vo';
import { TipoEnteVO } from '../../vo/tipo-ente-vo';
import { UbicazioneVO } from '../../vo/ubicazione-vo';
import { ComuneVO } from '../../vo/comune-vo';
import { DepositoVO } from '../../vo/deposito-vo';
import { NgForm, FormControl, FormGroup } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Moment } from 'moment';
import { DatiBancariVO } from '../../vo/dati-bancari-vo';
import { SoggettoService } from '../../services/soggetto.service';
import { LuoghiService } from '../../services/luoghi.service';
import { EnteService } from '../../services/ente.service';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { MatSnackBar, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { ProvinciaVO } from 'src/app/vo/provincia-vo';
import { DialogComponentConfirm } from '../dialog-confirm/dialog-confirm.component';
import { ImageDialogComponent } from '../image-dialog/image-dialog.component';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-inserisci',
  templateUrl: './inserisci.component.html',
  styleUrls: ['./inserisci.component.scss']
})
export class InserisciComponent implements OnInit {

  loadedTipoSog: boolean = false;
  loadedNat: boolean = false;
  loadedProv: boolean = false;
  loadedTipoEnte: boolean = false;
  loadedSave: boolean = true;
  isNotSelectedDepositoPrevalente: Boolean = null;
  isNotSelectedIBAN: Boolean = null;
  errorLength: Boolean = false;

  isRegione: boolean;
  isAmp: boolean;

  dataIn: Moment;
  dataCes: Moment;
  dataToday: Date = new Date();
  soggetto: InserisciSoggettoGiuridicoVO;
  tipiSoggettoGiuridico: Array<TipoSoggettoGiuridicoVO>;
  natureGiuridiche: Array<NaturaGiuridicaVO>;
  tipiEnte: Array<TipoEnteVO>;
  ubicazioneSede: UbicazioneVO = new UbicazioneVO();
  province: Array<ProvinciaVO>;
  comuniSede: Array<ComuneVO> = new Array<ComuneVO>();
  comuniDep: Array<ComuneVO> = new Array<ComuneVO>();
  arrayComuniDep: Array<Array<ComuneVO>> = new Array<Array<ComuneVO>>();
  nomeAllegatoTmp: string;
  dep: DepositoVO = new DepositoVO();
  dato: DatiBancariVO = new DatiBancariVO();
  isLogoUploaded: boolean = false;

  tipoSoggettoGiuridicoSelected: any;
  filteredOptionsTipo: Observable<TipoSoggettoGiuridicoVO[]>;
  tipoSoggGroup: FormGroup = new FormGroup({ tipoSogg: new FormControl() });

  naturaGiuridicaSelected = new FormControl();
  filteredOptionsNatura: Observable<NaturaGiuridicaVO[]>;
  naturaGroup: FormGroup = new FormGroup({ nat: new FormControl() });

  tipoEnteSelected = new FormControl();
  filteredOptionsTipoEnte: Observable<TipoEnteVO[]>;
  tipoEnteGroup: FormGroup = new FormGroup({ tipoEnte: new FormControl() });

  provinciaSedeSelected = new FormControl();
  filteredOptionsProvinciaSede: Observable<ProvinciaVO[]>;
  provinciaSedeGroup: FormGroup = new FormGroup({ provSede: new FormControl() });

  comuneSedeSelected = new FormControl();
  filteredOptionsComuneSede: Observable<ComuneVO[]>;
  comuneSedeGroup: FormGroup = new FormGroup({ comSede: new FormControl() });

  provinciaDepSelected = new FormControl();
  filteredOptionsProvinciaDep: Observable<ProvinciaVO[]>;
  provinciaDepGroup: FormGroup = new FormGroup({ provDep: new FormControl() });

  comuneDepSelected = new FormControl();
  filteredOptionsComuneDep: Observable<ComuneVO[]>;
  comuneDepGroup: FormGroup = new FormGroup({ comDeposito: new FormControl() });

  provinceDepSelected = new Array<FormControl>();
  filteredOptionsProvinceDep = new Array<Observable<ProvinciaVO[]>>();
  provinceDepGroup: Array<FormGroup> = new Array<FormGroup>();

  comuniDepSelected = new Array<FormControl>();
  filteredOptionsComuniDep = new Array<Observable<ComuneVO[]>>();
  comuniDepGroup: Array<FormGroup> = new Array<FormGroup>();

  constructor(
    private dateAdapter: DateAdapter<Date>,
    private router: Router,
    private soggettoService: SoggettoService,
    private luoghiService: LuoghiService,
    private enteService: EnteService,
    private dialog: MatDialog,
    public snackBar: MatSnackBar,
    private cdRef: ChangeDetectorRef,
    private sanitizer: DomSanitizer,
  ) {
    dateAdapter.setLocale('it-IT');
  }

  ngOnInit() {
    window.scroll(0, 0);
    this.soggetto = new InserisciSoggettoGiuridicoVO();
    this.soggetto.aziendaAttiva = false;
    this.comuneSedeGroup.disable();
    this.comuneDepGroup.disable();
    this.arrayComuniDep.push(new Array<ComuneVO>());
    this.loadChoices();
  }

  click(event: any, s: string) {
    if (s == 'T') {
      this.tipoSoggettoGiuridicoSelected = event.option.value;
      if (this.soggetto.codOsservatorioNaz && this.soggetto.codOsservatorioNaz.length > 0) {
        this.soggetto.codOsservatorioNaz = "";
      }
    }
    else if (s == 'N')
      this.naturaGiuridicaSelected = event.option.value;
    else if (s == 'TE')
      this.tipoEnteSelected = event.option.value;
    else if (s == 'PS') {
      if (event.option.value.id != this.ubicazioneSede.idProvincia) {
        this.comuneSedeSelected = new FormControl();
        this.comuneSedeGroup.controls['comSede'].setValue(null);
      }
      this.provinciaSedeSelected = event.option.value;
      this.ubicazioneSede.idProvincia = event.option.value.id;
      this.luoghiService.getComuniByIdProvincia(event.option.value.id).subscribe(data => {
        this.comuniSede = data;
        this.filteredOptionsComuneSede = this.comuneSedeGroup.controls['comSede'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterComuneSede(name) : this.comuniSede.slice())
          );
        this.comuneSedeGroup.enable();
      });
    }
    else if (s == 'CS') {
      this.comuneSedeSelected = event.option.value;
      this.ubicazioneSede.idComune = event.option.value.id;
    }
    else if (s == 'PD') {
      if (event.option.value.id != this.dep.ubicazione.idProvincia) {
        this.comuneDepSelected = new FormControl();
        this.comuneDepGroup.controls['comDeposito'].setValue(null);
      }
      this.provinciaDepSelected = event.option.value;
      this.dep.ubicazione.idProvincia = event.option.value.id;
      this.luoghiService.getComuniByIdProvincia(event.option.value.id).subscribe(data => {
        this.comuniDep = data;
        this.filteredOptionsComuneDep = this.comuneDepGroup.controls['comDeposito'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterComuneDep(name) : this.comuniDep.slice())
          );
        this.comuneDepGroup.enable();
      });
    }
    else if (s == 'CD') {
      this.comuneDepSelected = event.option.value;
      this.dep.ubicazione.idComune = event.option.value.id;
    }
  }

  check(s: string) {
    setTimeout(() => {
      if (s == 'T') {
        if (!this.tipoSoggettoGiuridicoSelected || (this.tipoSoggGroup.controls['tipoSogg'] && this.tipoSoggettoGiuridicoSelected !== this.tipoSoggGroup.controls['tipoSogg'].value)) {
          this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
          this.tipoSoggettoGiuridicoSelected = new FormControl();
        }
      }
      else if (s == 'N') {
        if (!this.naturaGiuridicaSelected || (this.naturaGroup.controls['nat'] && this.naturaGiuridicaSelected !== this.naturaGroup.controls['nat'].value)) {
          this.naturaGroup.controls['nat'].setValue(null);
          this.naturaGiuridicaSelected = new FormControl();
        }
      }
      else if (s == 'TE') {
        if (!this.tipoEnteSelected || (this.tipoEnteGroup.controls['tipoEnte'] && this.tipoEnteSelected !== this.tipoEnteGroup.controls['tipoEnte'].value)) {
          this.tipoEnteGroup.controls['tipoEnte'].setValue(null);
          this.tipoEnteSelected = new FormControl();
        }
      }
      else if (s == 'PS') {
        if (!this.provinciaSedeSelected ||
          (this.provinciaSedeGroup.controls['provSede']
            && this.provinciaSedeSelected !== this.provinciaSedeGroup.controls['provSede'].value
            && this.provinciaSedeGroup.controls['provSede'].value !== this.province.find(a => a.id == this.ubicazioneSede.idProvincia))) {
          this.provinciaSedeGroup.controls['provSede'].setValue(null);
          this.provinciaSedeSelected = new FormControl();
          this.comuneSedeGroup.controls['comSede'].setValue(null);
          this.comuneSedeSelected = new FormControl();
          this.comuneSedeGroup.disable();
        }
      }
      else if (s == 'CS') {
        if (!this.comuneSedeSelected ||
          (this.comuneSedeGroup.controls['comSede']
            && this.comuneSedeSelected !== this.comuneSedeGroup.controls['comSede'].value
            && this.comuneSedeGroup.controls['comSede'].value !== this.comuniSede.find(a => a.id == this.ubicazioneSede.idComune))) {
          this.comuneSedeGroup.controls['comSede'].setValue(null);
          this.comuneSedeSelected = new FormControl();
        }
      }
      else if (s == 'PD') {
        if (!this.provinciaDepSelected || (this.provinciaDepGroup.controls['provDep'] && this.provinciaDepSelected !== this.provinciaDepGroup.controls['provDep'].value)) {
          this.provinciaDepGroup.controls['provDep'].setValue(null);
          this.provinciaDepSelected = new FormControl();
          if (this.comuneDepGroup.controls['comDeposito'] != null)
            this.comuneDepGroup.controls['comDeposito'].setValue(null);
          this.comuneDepSelected = new FormControl();
          this.dep.ubicazione.idComune = null;
          this.dep.ubicazione.idProvincia = null;
          this.comuneDepGroup.disable();
        }
      }
      else if (s == 'CD') {
        if (!this.comuneDepSelected || (this.comuneDepGroup.controls['comDeposito'] && this.comuneDepSelected !== this.comuneDepGroup.controls['comDeposito'].value)) {
          this.comuneDepGroup.controls['comDeposito'].setValue(null);
          this.comuneDepSelected = new FormControl();
          this.dep.ubicazione.idComune = null;
        }
      }
    }, 200);
  }

  clickDep(event: any, index: number, s: string) {
    if (s == "P") {
      if (event.option.value.id != this.soggetto.depositi[index].ubicazione.idProvincia) {
        this.comuniDepSelected[index] = new FormControl();
        this.comuniDepGroup[index].controls['comDep'].setValue(null);
      }
      this.provinceDepSelected[index] = event.option.value;
      this.soggetto.depositi[index].ubicazione.idProvincia = event.option.value.id;
      this.luoghiService.getComuniByIdProvincia(event.option.value.id).subscribe(data => {
        this.arrayComuniDep[index] = data;
        this.comuniDepGroup[index].enable();
      });
    }
    else if (s == "C") {
      this.comuniDepSelected[index] = event.option.value;
      this.soggetto.depositi[index].ubicazione.idComune = event.option.value.id;
    }
  }
  checkDep(index: number, s: string) {
    setTimeout(() => {
      if (s == "P") {
        if (!this.provinceDepSelected[index] ||
          (this.provinceDepGroup[index].controls['provDeposito']
            && this.provinceDepSelected[index] !== this.provinceDepGroup[index].controls['provDeposito'].value
            && this.provinceDepGroup[index].controls['provDeposito'].value !== this.province.find(a => a.id == this.soggetto.depositi[index].ubicazione.idProvincia))) {
          this.provinceDepGroup[index].controls['provDeposito'].setValue(null);
          this.provinceDepSelected[index] = new FormControl();
          this.comuniDepGroup[index].controls['comDep'].setValue(null);
          this.comuniDepSelected[index] = new FormControl();
          this.soggetto.depositi[index].ubicazione.idProvincia = null;
          this.soggetto.depositi[index].ubicazione.idComune = null;
          this.comuniDepGroup[index].disable();
        }
      }
      else if (s == "C") {
        if (!this.comuniDepSelected[index] ||
          (this.comuniDepGroup[index].controls['comDep']
            && this.comuniDepSelected[index] !== this.comuniDepGroup[index].controls['comDep'].value
            && this.comuniDepGroup[index].controls['comDep'].value.id !== this.soggetto.depositi[index].ubicazione.idComune)) {
          this.comuniDepGroup[index].controls['comDep'].setValue(null);
          this.comuniDepSelected[index] = new FormControl();
          this.soggetto.depositi[index].ubicazione.idComune = null;
        }
      }
    }, 200);
  }

  private _filterTipo(descrizione: string): TipoSoggettoGiuridicoVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipiSoggettoGiuridico.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
  }

  private _filterNatura(descrizione: string): NaturaGiuridicaVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.natureGiuridiche.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
  }

  private _filterTipoEnte(descrizione: string): TipoEnteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.tipiEnte.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
  }

  private _filterProvincia(denominazione: string): ProvinciaVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.province.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  private _filterComuneSede(denominazione: string): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.comuniSede.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  private _filterComuneDep(denominazione: string): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.comuniDep.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  private _filterComuneArrayDep(denominazione: string, index: number): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.arrayComuniDep[index].filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  displayFn(a?: any): string | undefined { //per tipo soggetto giuridico, natura giuridica e tipo ente
    return a ? a.descrizione : undefined;
  }

  displayFnc(a?: any): string | undefined { //per comune
    return a ? a.denominazione : undefined;
  }

  private loadChoices() {
    this.loadedTipoSog = false;
    this.loadedNat = false;
    this.loadedProv = false;
    this.loadedTipoEnte = false;
    this.soggettoService.getTipiSoggettoGiuridico(true).subscribe(data => {
      if (data) {
        this.tipiSoggettoGiuridico = data;
        this.filteredOptionsTipo = this.tipoSoggGroup.controls['tipoSogg'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipo(name) : this.tipiSoggettoGiuridico.slice())
          );
      }
      this.loadedTipoSog = true;

    });
    this.soggettoService.getNatureGiuridiche().subscribe(data => {
      if (data) {
        this.natureGiuridiche = data;
        this.filteredOptionsNatura = this.naturaGroup.controls['nat'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterNatura(name) : this.natureGiuridiche.slice())
          );
      }
      this.loadedNat = true;
    });
    this.enteService.getTipiEnte().subscribe(data => {
      if (data) {
        this.tipiEnte = data;
        this.filteredOptionsTipoEnte = this.tipoEnteGroup.controls['tipoEnte'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.descrizione),
            map(name => name ? this._filterTipoEnte(name) : this.tipiEnte.slice())
          );
      }
      this.loadedTipoEnte = true;
    });
    this.luoghiService.getProvince().subscribe(data => {
      if (data) {
        this.province = data;
        this.filteredOptionsProvinciaSede = this.provinciaSedeGroup.controls['provSede'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterProvincia(name) : this.province.slice())
          );
        this.filteredOptionsProvinciaDep = this.provinciaDepGroup.controls['provDep'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterProvincia(name) : this.province.slice())
          );
      }
      this.loadedProv = true;
    });

    this.ubicazioneSede = new UbicazioneVO();

  }

  checkIsIBANinList() {
    for (let dato of this.soggetto.datiBancari) {
      if (dato.doatpl) {
        return true;
      }
    }
    return false;
  }

  IBANChange() {
    if (this.dato.iban != null && this.dato.doatpl) {
      this.isNotSelectedIBAN = false;
    } else if (this.dato.iban == null && this.dato.doatpl && !this.checkIsIBANinList()) {
      this.isNotSelectedIBAN = true
    }
  }
  denominazioneDepChange() {
    if (this.dep.denominazione != null && this.dep.depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = false;
    } else if (this.dep.denominazione == null && this.dep.depositoPrevalenteFlg && !this.checkIsDepositoPrevalenteinList()) {
      this.isNotSelectedDepositoPrevalente = true
    }
  }

  depositoPrevalenteOnChange() {
    if (this.dep.depositoPrevalenteFlg) {
      if (this.dep.denominazione != null) {
        this.isNotSelectedDepositoPrevalente = false;
      }
      for (let deposito of this.soggetto.depositi) {
        if (deposito.depositoPrevalenteFlg) {
          let dialogRef = this.dialog.open(DialogComponentConfirm, {
            width: '600px',
            data: { type: "Attenzione", message: "Esiste già un deposito principale; si vuole rendere tale il deposito corrente?" }
          });

          dialogRef.afterClosed().subscribe(result => {

            if (result) {
              deposito.depositoPrevalenteFlg = !result;

            } else {
              this.dep.depositoPrevalenteFlg = false;
            }
          });
        }
      }
    }
  }

  doatplCheck() {
    if (this.dato.doatpl) {
      if (this.dato.doatpl != null) {
        this.isNotSelectedIBAN = false;
      }
      for (let banca of this.soggetto.datiBancari) {
        if (banca.doatpl) {
          let dialogRef = this.dialog.open(DialogComponentConfirm, {
            width: '600px',
            data: { type: "Attenzione", message: "Esiste già un IBAN per contributi Dotazione Organica; si vuole rendere tale l'IBAN corrente?" }
          });

          dialogRef.afterClosed().subscribe(result => {

            if (result) {
              banca.doatpl = !result;
            } else {
              this.dato.doatpl = false;
            }
          });
        }
      }
    }
  }


  listaDepositoPrevalenteOnChange(index) {
    for (let deposito of this.soggetto.depositi) {
      if (deposito.depositoPrevalenteFlg && deposito != this.soggetto.depositi[index]) {
        let dialogRef = this.dialog.open(DialogComponentConfirm, {
          width: '600px',
          data: { type: "Attenzione", message: "Esiste già un deposito principale; si vuole rendere tale il deposito corrente?" }
        });

        dialogRef.afterClosed().subscribe(result => {

          if (result) {
            deposito.depositoPrevalenteFlg = !result;

          } else {
            this.soggetto.depositi[index].depositoPrevalenteFlg = false;
            this.dep.depositoPrevalenteFlg = false;
          }
        });
        this.isNotSelectedDepositoPrevalente = false;
      }
    }

    if (this.dep.depositoPrevalenteFlg) {
      let dialog = this.dialog.open(DialogComponentConfirm, {
        width: '600px',
        data: { type: "Attenzione", message: "Esiste già un deposito principale; si vuole rendere tale il deposito corrente?" }
      });

      dialog.afterClosed().subscribe(result => {

        if (result) {
          this.dep.depositoPrevalenteFlg = !result;

        } else {
          this.soggetto.depositi[index].depositoPrevalenteFlg = false;
        }
      });
    }

    if (this.soggetto.depositi[index].depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = false;
    } else {
      this.isNotSelectedDepositoPrevalente = true;
    }
  }


  // Gestione allegati
  files: File;
  handleFileInput(files: FileList) {
    this.files = files[0];
    this.isLogoUploaded = true;
  }

  addDeposito() {
    if (this.dep.depositoPrevalenteFlg === null) {
      this.dep.depositoPrevalenteFlg = false;
    }
    this.soggetto.depositi.push(this.dep);
    let comuneDep = new FormControl();
    comuneDep.setValue(this.comuniDep.find(a => a.id == this.dep.ubicazione.idComune));
    this.comuniDepSelected.push(comuneDep);
    this.comuniDepGroup.push(new FormGroup({ comDep: comuneDep }));
    this.comuniDepGroup[this.comuniDepGroup.length - 1].disable();
    this.filteredOptionsComuniDep.push(this.comuniDepGroup[this.comuniDepGroup.length - 1].controls['comDep'].valueChanges
      .pipe(
        startWith(''),
        map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
        map(name => name ? this._filterComuneArrayDep(name, this.comuniDepGroup.length - 1) : this.arrayComuniDep[this.comuniDepGroup.length - 1].slice())
      ));

    let provinciaDep = new FormControl();
    if (this.provinciaDepGroup.controls['provDep'] && this.provinciaDepGroup.controls['provDep'].value) {
      provinciaDep.setValue(this.province.find(b => b.id == this.provinciaDepGroup.controls['provDep'].value.id))
    }
    this.provinceDepSelected.push(provinciaDep);
    this.provinceDepGroup.push(new FormGroup({ provDeposito: provinciaDep }));
    this.provinceDepGroup[this.provinceDepGroup.length - 1].disable();
    this.filteredOptionsProvinceDep.push(this.provinceDepGroup[this.provinceDepGroup.length - 1].controls['provDeposito'].valueChanges
      .pipe(
        startWith(''),
        map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
        map(name => name ? this._filterProvincia(name) : this.province.slice())
      ));

    if (this.dep.depositoPrevalenteFlg || this.checkIsDepositoPrevalenteinList()) {
      this.isNotSelectedDepositoPrevalente = false;
    } else {
      this.isNotSelectedDepositoPrevalente = true;
    }

    this.dep = new DepositoVO();
    this.dep.depositoPrevalenteFlg = false;
    this.arrayComuniDep.push(new Array<ComuneVO>());
    this.comuneDepGroup = new FormGroup({ comDeposito: new FormControl() });
    this.comuneDepSelected = new FormControl();
    this.provinciaDepGroup = new FormGroup({ provDep: new FormControl() });
    this.provinciaDepSelected = new FormControl();
    this.comuneDepGroup.disable();
    this.filteredOptionsProvinciaDep = this.provinciaDepGroup.controls['provDep'].valueChanges
      .pipe(
        startWith(''),
        map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
        map(name => name ? this._filterProvincia(name) : this.province.slice())
      );
  }

  isAddDepDisabled() {
    if (this.dep.denominazione == null || this.dep.denominazione.length == 0)
      return true;
    else return false;
  }

  checkIsDepositoPrevalenteinList() {
    for (let deposito of this.soggetto.depositi) {
      if (deposito.depositoPrevalenteFlg) {
        return true;
      }
    }
    return false;
  }

  removeDeposito(index: number) {
    if (this.soggetto.depositi[index].depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = true;
    }
    this.soggetto.depositi.splice(index, 1);
    this.comuniDepSelected.splice(index, 1);
    this.filteredOptionsComuniDep.splice(index, 1);
    this.comuniDepGroup.splice(index, 1);
    this.provinceDepSelected.splice(index, 1);
    this.filteredOptionsProvinceDep.splice(index, 1);
    this.provinceDepGroup.splice(index, 1);

    if (this.soggetto.depositi.length == 0) {
      this.isNotSelectedDepositoPrevalente = null;
    }
  }


  addDatiBancari() {
    this.soggetto.datiBancari.push(this.dato);
    this.dato = new DatiBancariVO();

    if (this.dato.doatpl || this.checkIsIBANinList()) {
      this.isNotSelectedIBAN = false;
    } else {
      this.isNotSelectedIBAN = true;
    }

  }

  isAddDatiBancariDisabled() {
    /*  if (this.dato.iban == null || this.dato.iban.length == 0 || this.dato.iban.length < 27) */
    if (this.dato.iban == null || this.dato.iban.length == 0)
      return true;
    else return false;
  }

  removeDatiBancari(index: number) {
    if (this.soggetto.datiBancari[index].doatpl) {
      this.isNotSelectedIBAN = true;
    }
    this.soggetto.datiBancari.splice(index, 1);

    if (this.soggetto.datiBancari.length == 0) {
      this.isNotSelectedIBAN = null;
    }
  }

  private image: any;
  viewLogo() {
    let objectURL = URL.createObjectURL(this.files);
    this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    const dialogRef = this.dialog.open(ImageDialogComponent, {
      width: '600px',
      data: { image: this.image }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  downloadDoc() {
    saveAs(this.files, "logo.jpg");
  }

  eliminaDoc() {

    this.isLogoUploaded = false;
    this.files = undefined;
  }

  @ViewChild('soggettoForm') formGroup: NgForm;
  isValidCampiRequired() {
    if (this.formGroup && this.formGroup.form) {
      if (this.tipoSoggGroup.controls['tipoSogg'] && this.tipoSoggGroup.controls['tipoSogg'].hasError('required')) return true;
      if (this.tipoSoggGroup.controls['tipoSogg'] && this.tipoSoggGroup.controls['tipoSogg'].value.id == 2) {
        if (this.naturaGroup.controls['nat'] && this.naturaGroup.controls['nat'].hasError('required')) return true;
        if (this.formGroup.controls['denomAAEP'] && this.formGroup.controls['denomAAEP'].hasError('required')) return true;
        if (this.formGroup.controls['nomeRappresentanteLegale'] && this.formGroup.controls['nomeRappresentanteLegale'].hasError('required')) return true;
        if (this.formGroup.controls['cognomeRappresentanteLegale'] && this.formGroup.controls['cognomeRappresentanteLegale'].hasError('required')) return true;
        if (this.formGroup.controls['dataInizio'] && this.formGroup.controls['dataInizio'].hasError('required')) return true;
        if (this.formGroup.controls['denomDep'] && this.formGroup.controls['denomDep'].hasError('required')) return true;
        for (let i = 0; i < this.soggetto.depositi.length; i++) {
          if (this.hasDenomDepError(i))
            return true;
        }
        if (this.formGroup.controls['iban'] && this.formGroup.controls['iban'].hasError('required')) return true;
        for (let i = 0; i < this.soggetto.datiBancari.length; i++) {
          if (this.hasIbanError(i))
            return true;
        }
      }
      else {
        if (this.tipoEnteGroup.controls['tipoEnte'] && this.tipoEnteGroup.controls['tipoEnte'].hasError('required')) return true;
        if (this.formGroup.controls['denominazione'] && this.formGroup.form.controls['denominazione'].hasError('required')) return true;
      }
      if (this.formGroup.controls['denomBreve'] && this.formGroup.controls['denomBreve'].hasError('required')) return true;
      if (this.formGroup.controls['denomOsservatorioNaz'] && this.formGroup.controls['denomOsservatorioNaz'].hasError('required')) return true;
      if (this.formGroup.controls['codOsservatorioNaz'] && (this.formGroup.controls['codOsservatorioNaz'].hasError('required') || this.formGroup.controls['codOsservatorioNaz'].invalid)) return true;
      if (this.formGroup.controls['partitaIva'] && this.formGroup.controls['partitaIva'].hasError('required')) return true;
      if (this.formGroup.controls['codiceFiscale'] && this.formGroup.controls['codiceFiscale'].hasError('required')) return true;
    }
    return false;
  }

  isCampiInvalid() {

    if (this.formGroup.controls['iban'] && this.formGroup.controls['iban'].errors) return true;

    if (this.formGroup.controls['partitaIva'] && this.formGroup.controls['partitaIva'].hasError('minlength') && this.formGroup.controls['partitaIva'].untouched) {
      this.formGroup.controls['partitaIva'].markAsTouched();
    }
    if (this.formGroup.controls['codiceFiscale'] && this.formGroup.controls['codiceFiscale'].hasError('pattern') && this.formGroup.controls['codiceFiscale'].untouched) {
      this.formGroup.controls['codiceFiscale'].markAsTouched();
    }
    if (this.formGroup.controls['codCsrBip'] && (this.formGroup.controls['codCsrBip'].hasError('integer') || this.formGroup.controls['codCsrBip'].errors)) return true;
    if (this.formGroup.controls['codOsservatorioNaz'] && this.formGroup.controls['codOsservatorioNaz'].errors && !this.formGroup.controls['codOsservatorioNaz'].hasError('pattern')) return true;
    if (this.formGroup.controls['dataCessazione'] && this.formGroup.controls['dataCessazione'].invalid) return true;
    if (this.formGroup.controls['partitaIva'] && (this.formGroup.controls['partitaIva'].hasError('minlength') || this.formGroup.controls['partitaIva'].errors)) return true;
    if (this.formGroup.controls['codiceFiscale'] && (this.formGroup.controls['codiceFiscale'].hasError('pattern') || this.formGroup.controls['codiceFiscale'].errors)) return true;
    if ((this.isNotSelectedDepositoPrevalente != null && this.isNotSelectedDepositoPrevalente == true) && this.soggetto.depositi.length > 0) return true;
    return false;
  }

  isDenomDepRequired() {
    let ubi = this.dep.ubicazione;
    if ((this.dep.telefono != null && this.dep.telefono.length > 0)
      || ubi.idComune != null
      || (ubi.top != null && ubi.top.length > 0)
      || (ubi.indirizzo != null && ubi.indirizzo.length > 0)
      || (ubi.civico != null && ubi.civico.length > 0)
      || (ubi.cap != null && ubi.cap.length > 0)
      || (this.dep.depositoPrevalenteFlg)) {
      return true;
    }
    return false;
  }

  hasDenomDepError(index: number) {
    let s: string = "denomDeposito_" + index;

    if (this.formGroup.controls[s] && this.formGroup.controls[s].hasError('required'))
      return true;
    return false;
  }


  isIbanRequired() {
    if ((this.dato.note != null && this.dato.note.length > 0)
      || (this.dato.doatpl)) {
      return true;
    }
    return false;
  }

  hasIbanError(index: number) {
    let s: string = "iban_" + index;

    if (this.formGroup.controls[s] && this.formGroup.controls[s].hasError('required'))
      return true;
    return false;
  }

  reset() {
    this.files = null;
    this.feedback = "";
    this.soggetto = new InserisciSoggettoGiuridicoVO();
    this.soggetto.aziendaAttiva = false;
    this.dato = new DatiBancariVO();
    this.soggetto.datiBancari = new Array<DatiBancariVO>();
    this.tipoSoggGroup.controls['tipoSogg'].setValue(null);
    this.tipoSoggettoGiuridicoSelected = new FormControl();
    this.naturaGroup.controls['nat'].setValue(null);
    this.naturaGiuridicaSelected = new FormControl();
    this.tipoEnteGroup.controls['tipoEnte'].setValue(null);
    this.tipoEnteSelected = new FormControl();
    this.provinciaSedeGroup.controls['provSede'].setValue(null);
    this.provinciaSedeSelected = new FormControl();
    this.comuneSedeGroup.controls['comSede'].setValue(null);
    this.comuneSedeSelected = new FormControl();
    this.comuneDepGroup.controls['comDeposito'].setValue(null);
    this.comuneDepSelected = new FormControl();
    this.provinciaDepGroup.controls['provDep'].setValue(null);
    this.provinciaDepSelected = new FormControl();
    this.comuniDepSelected = new Array<FormControl>();
    this.comuniDepGroup = new Array<FormGroup>();
    this.provinceDepSelected = new Array<FormControl>();
    this.provinceDepGroup = new Array<FormGroup>();
    this.dep = new DepositoVO();
    this.dataIn = null;
    this.dataCes = null;
    this.loadChoices();
    window.scrollTo(0, 0);
  }

  feedback: string;
  save() {
    this.loadedSave = false;
    this.soggetto.idTipoSoggettoGiuridico = this.tipoSoggGroup.controls['tipoSogg'].value.id;
    if (this.tipoSoggGroup.controls['tipoSogg'].value.idRuolo == 2) {
      this.soggetto.idNaturaGiuridica = this.naturaGroup.controls['nat'].value.id;
      this.soggetto.dataInizio = this.dataIn.toDate();
      if (this.dataCes != null)
        this.soggetto.dataCessazione = this.dataCes.toDate();
      this.soggetto.descrizione = null;
      if (this.dep.denominazione != null && this.dep.denominazione.length > 0)
        this.addDeposito();
      if (this.dato.iban != null && this.dato.iban.length > 0)
        this.addDatiBancari();
    }
    else {
      this.soggetto.idTipoEnte = this.tipoEnteGroup.controls['tipoEnte'].value.id;
      this.soggetto.denomAAEP = null;
      this.soggetto.nomeRappresentanteLegale = null;
      this.soggetto.cognomeRappresentanteLegale = null;
    }
    if (this.provinciaSedeGroup.controls['provSede'] != null && this.provinciaSedeGroup.controls['provSede'].value != null)
      this.ubicazioneSede.idProvincia = this.provinciaSedeGroup.controls['provSede'].value.id;
    this.soggetto.ubicazioneSede = this.ubicazioneSede;

    this.soggettoService.inserisciSoggetto(this.soggetto, this.files).subscribe(data => {
      this.loadedSave = true;

      this.feedback = "";
      let id: number = data;
      window.scrollTo(0, 0);
      this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
        duration: 2000,
      });

      setTimeout(() => { this.router.navigate(['/modificaSoggetto/' + id, { action: 'inserisci' }]) }, 2000);
    }, err => {
      this.loadedSave = true;
      CommonsHandleException.authorizationError(err, this.router);

      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      if (errorRest.type == TypeErrorRest.OK) {
        this.feedback = errorRest.message;
      }
      else {
        this.feedback = "Si è verificato un errore in fase di salvataggio";
      }

      console.error("this.feedback = " + this.feedback);
      window.scrollTo(0, 0);
      if (errorRest.codice == 'TFND') {
        setTimeout(() => { this.formGroup.controls['codOsservatorioNaz'].setErrors({ duplicato: true }) }, 200);
      }
      //CSR-BIP duplicato
      if (errorRest.codice == 'CBD') {
        setTimeout(() => { this.formGroup.controls['codCsrBip'].setErrors({ duplicato: true }) }, 200);
      }
      if (errorRest.codice == 'PID') {
        setTimeout(() => { this.formGroup.controls['partitaIva'].setErrors({ duplicato: true }) }, 200);

      }
      if (errorRest.codice == 'CFD') {
        setTimeout(() => { this.formGroup.controls['codiceFiscale'].setErrors({ duplicato: true }) }, 200);
      }
    });
  }

  isLoading() {
    if (!this.loadedSave) return true;
    if (!this.loadedProv) return true;
    if (this.loadedTipoSog && this.loadedNat) return false;
    if (this.loadedTipoEnte) return false;
    return true;
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }


  getStyle() {
    if (this.isValidCampiRequired() || this.isCampiInvalid() || this.erroreIban()) {
      return 'rgb(99, 97, 97);';
    }

    return 'white';
  }

  getBackground() {
    if (this.isValidCampiRequired() || this.isCampiInvalid() || this.erroreIban()) {
      return 'rgb(192, 192, 192)';
    }
    return 'rgb(214,87,42)';
  }

  erroreIban() {
    //se esite iban
    if (this.dato.iban) {
      if (this.dato.doatpl != true && !this.checkIsIBANinList()) {
        return true;
      }
    }
    //se iban non è inserito nel campo attuale
    if (this.dato.doatpl != true && !this.checkIsIBANinList() && this.isNotSelectedIBAN != null) {
      return true;
    }
    return false;
  }


  //pulisce input data se passo un parametro di tipo stringa
  checkData(data: Date, name: string) {
    if (data == null)
      this.formGroup.form.get(name).setValue(null);
  }

  listaDoatplCheck(index) {
    for (let banca of this.soggetto.datiBancari) {
      if (banca.doatpl && banca != this.soggetto.datiBancari[index]) {
        let dialogRef = this.dialog.open(DialogComponentConfirm, {
          width: '600px',
          data: { type: "Attenzione", message: "Esiste già un IBAN per contributi Dotazione Organica; si vuole rendere tale l'IBAN corrente?" }
        });

        dialogRef.afterClosed().subscribe(result => {

          if (result) {
            banca.doatpl = !result;

          } else {
            this.soggetto.datiBancari[index].doatpl = false;
            this.dato.doatpl = false;
          }
        });
        this.isNotSelectedIBAN = false;
      }
    }

    if (this.dato.doatpl) {
      let dialog = this.dialog.open(DialogComponentConfirm, {
        width: '600px',
        data: { type: "Attenzione", message: "Esiste già un IBAN per contributi Dotazione Organica; si vuole rendere tale l'IBAN corrente?" }
      });

      dialog.afterClosed().subscribe(result => {

        if (result) {
          this.dato.doatpl = !result;

        } else {
          this.soggetto.datiBancari[index].doatpl = false;
        }
      });
    }

    if (this.soggetto.datiBancari[index].doatpl) {
      this.isNotSelectedIBAN = false;
    } else {
      this.isNotSelectedIBAN = true;
    }

  }

  //controllo lunghezza IBAN
  erroreLength(length: number) {
    if (length > 0 && length < 27)
      this.formGroup.controls['iban'].setErrors({ lunghezza: true })
  }


}
