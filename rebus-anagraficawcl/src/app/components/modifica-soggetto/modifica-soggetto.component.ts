/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { SoggettoService } from '../../services/soggetto.service';
import { SoggettoGiuridicoVO } from '../../vo/soggetto-giuridico-vo';
import { ActivatedRoute, Router } from '@angular/router';
import { UbicazioneVO } from '../../vo/ubicazione-vo';
import { NgForm, FormControl, FormGroup } from '@angular/forms';
import { TipoSoggettoGiuridicoVO } from '../../vo/tipo-soggetto-giuridico-vo';
import { NaturaGiuridicaVO } from '../../vo/natura-giuridica-vo';
import { TipoEnteVO } from '../../vo/tipo-ente-vo';
import { EnteService } from '../../services/ente.service';
import { Moment } from 'moment';
import { ComuneVO } from '../../vo/comune-vo';
import { Observable } from 'rxjs';
import { LuoghiService } from '../../services/luoghi.service';
import { startWith, map } from 'rxjs/operators';
import { DatiBancariVO } from '../../vo/dati-bancari-vo';
import { DateAdapter, MatSnackBar, MatDialog } from '@angular/material';
import { DepositoVO } from '../../vo/deposito-vo';
import { saveAs } from "file-saver";
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { ProvinciaVO } from '../../vo/provincia-vo';
import { ImageDialogComponent } from '../image-dialog/image-dialog.component';
import { DomSanitizer } from '@angular/platform-browser';
import { UserService } from '../../services/user.service';
import { CodeRoles } from '../../class/code-roles';
import { DialogComponentConfirm } from '../dialog-confirm/dialog-confirm.component';;

var moment = require('moment');

@Component({
  selector: 'app-modifica-soggetto',
  templateUrl: './modifica-soggetto.component.html',
  styleUrls: ['./modifica-soggetto.component.scss']
})
export class ModificaSoggettoComponent implements OnInit {

  ruoloUtente: string;
  isRegione: boolean;
  isAmp: boolean;
  isAzienda: boolean;
  loadComplete: boolean;
  id: number;
  dettaglioSoggetto: SoggettoGiuridicoVO = new SoggettoGiuridicoVO;
  ubicazioneSede: UbicazioneVO = new UbicazioneVO();
  tipoSoggettoGiuridico: TipoSoggettoGiuridicoVO;
  naturaGiuridica: NaturaGiuridicaVO;
  tipoEnte: TipoEnteVO;
  dataIn: Moment;
  dataCes: Moment;
  dataToday: Date = new Date();
  files: File;
  province: Array<ProvinciaVO>;
  comuniSede: Array<ComuneVO> = new Array<ComuneVO>();
  comuniDep: Array<ComuneVO> = new Array<ComuneVO>();
  mapComuniDep: Map<number, Array<ComuneVO>> = new Map<number, Array<ComuneVO>>();
  dep: DepositoVO = new DepositoVO();
  dato: DatiBancariVO = new DatiBancariVO();
  isLogoUploadedButNonSaved: boolean = false;

  loadedSoggetto: boolean = false;
  loadedProv: boolean = false;
  loadedSave: boolean = true;
  loadedRuoloUtente: boolean = false;
  isNotSelectedDepositoPrevalente: Boolean = null;
  isNotSelectedIBAN: Boolean = null;

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
  isServizio: boolean;

  csrBipGroup: Array<FormGroup> = new Array<FormGroup>();

  constructor(private soggettoService: SoggettoService,
    private enteService: EnteService,
    private luoghiService: LuoghiService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    public snackBar: MatSnackBar,
    private cdRef: ChangeDetectorRef,
    private dateAdapter: DateAdapter<Date>,
    private dialog: MatDialog,
    private sanitizer: DomSanitizer,) {
    dateAdapter.setLocale('it-IT');
  }

  ngOnInit() {
    window.scroll(0, 0);
    this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.loadedRuoloUtente = false;
      this.userService.getCodiceRuoloUtente().subscribe(data => {
        this.ruoloUtente = data;
        console.log(this.ruoloUtente);
        
        if (this.ruoloUtente === CodeRoles.RUOLO_AZIENDA) {
          this.isAzienda = true;
        } else if (this.ruoloUtente === CodeRoles.RUOLO_REGIONE) {
          this.isRegione = true;
        } else if (this.ruoloUtente === CodeRoles.RUOLO_AMP) {
          this.isAmp = true;
        } else if (this.ruoloUtente === CodeRoles.RUOLO_SERVIZIO) {
          this.isServizio = true;
        }
        this.loadedRuoloUtente = true;
      });
      this.comuneSedeGroup.disable();
      this.comuneDepGroup.disable();

      this.loadDettaglioSoggettoAndChoices();
    });
  }

  loadDettaglioSoggettoAndChoices() {
    this.loadComplete = false;
    this.loadedSoggetto = false;
    this.soggettoService.dettaglioSoggetto(this.id, Action.EDIT);

    this.soggettoService.dettaglioSoggetto$.subscribe(data => {
      if (data) {
        this.dettaglioSoggetto = data;
        this.ubicazioneSede = this.dettaglioSoggetto.ubicazioneSede;
        this.loadComplete = true;

        for (let d of this.dettaglioSoggetto.depositi) {
          if (d.depositoPrevalenteFlg) {
            this.isNotSelectedDepositoPrevalente = false;
          }
          this.comuniDepSelected.push(new FormControl());
          this.comuniDepGroup.push(new FormGroup({ comDep: new FormControl() }));
          this.provinceDepSelected.push(new FormControl());
          this.provinceDepGroup.push(new FormGroup({ provDeposito: new FormControl() }));
        }
        if (this.dettaglioSoggetto.depositi.length && this.isNotSelectedDepositoPrevalente == null) {
          this.isNotSelectedDepositoPrevalente = true;
        }

        for (let d of this.dettaglioSoggetto.datiBancari) {
          if (d.doatpl) {
            this.isNotSelectedIBAN = false;
          }
        }
        if (this.dettaglioSoggetto.datiBancari.length && this.isNotSelectedIBAN == null) {
          this.isNotSelectedIBAN = true;
        }
        this.loadedSoggetto = true;
        this.loadChoices();
      }
    });

  }

  loadChoices() {
    this.soggettoService.getTipiSoggettoGiuridico(true).subscribe(data1 => {
      this.tipoSoggettoGiuridico = data1.find(a => a.id == this.dettaglioSoggetto.idTipoSoggettoGiuridico);
      this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico = this.tipoSoggettoGiuridico.idRuolo;
      if (this.tipoSoggettoGiuridico.idRuolo == 2) {
        this.soggettoService.getNatureGiuridiche().subscribe(data2 => {
          this.naturaGiuridica = data2.find(b => b.id == this.dettaglioSoggetto.idNaturaGiuridica);
        });
        this.dataIn = moment((new Date(this.dettaglioSoggetto.dataInizio)).toString());
        if (this.dettaglioSoggetto.dataCessazione != null)
          this.dataCes = moment((new Date(this.dettaglioSoggetto.dataCessazione)).toString());
      }
      else {
        this.enteService.getTipiEnte().subscribe(data3 => {
          this.tipoEnte = data3.find(c => c.id == this.dettaglioSoggetto.idTipoEnte);
        });
      }
    });

    this.loadedProv = false;
    this.luoghiService.getProvince().subscribe(data => {
      if (data) {
        this.province = data;
        let count = 0;
        for (let p of this.province) {
          this.luoghiService.getComuniByIdProvincia(p.id).subscribe(data2 => {
            this.mapComuniDep.set(p.id, data2);
            count++;
            if (count == this.province.length)
              this.loadComuni();
          });
        }
        this.filteredOptionsProvinciaSede = this.provinciaSedeGroup.controls['provSede'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterProvincia(name) : this.province.slice())
          );
        if (this.dettaglioSoggetto.ubicazioneSede.idComune != null) {
          this.provinciaSedeSelected.setValue(this.province.find(a => a.id == this.dettaglioSoggetto.ubicazioneSede.idProvincia));
          this.provinciaSedeGroup.controls['provSede'].setValue(this.province.find(a => a.id == this.dettaglioSoggetto.ubicazioneSede.idProvincia));
          this.luoghiService.getComuniByIdProvincia(this.dettaglioSoggetto.ubicazioneSede.idProvincia).subscribe(data2 => {
            this.comuniSede = data2;
            this.filteredOptionsComuneSede = this.comuneSedeGroup.controls['comSede'].valueChanges
              .pipe(
                startWith(''),
                map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
                map(name => name ? this._filterComuneSede(name) : this.comuniSede.slice())
              );
            this.comuneSedeSelected.setValue(this.comuniSede.find(a => a.id == this.dettaglioSoggetto.ubicazioneSede.idComune));
            this.comuneSedeGroup.controls['comSede'].setValue(this.comuniSede.find(a => a.id == this.dettaglioSoggetto.ubicazioneSede.idComune));
            if (!this.isRegione && !this.isAmp) {
              this.comuneSedeGroup.enable();
            }
          });

        }

      }
      if (this.isRegione || this.isAmp) {
        this.provinciaSedeGroup.disable();
        this.comuneSedeGroup.disable();



      }
      this.loadedProv = true;
    });
  }

  loadComuni() {
    if (this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico == 2) {
      this.filteredOptionsProvinciaDep = this.provinciaDepGroup.controls['provDep'].valueChanges
        .pipe(
          startWith(''),
          map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
          map(name => name ? this._filterProvincia(name) : this.province.slice())
        );
      if (this.isRegione || this.isAmp) {
        this.provinciaDepGroup.disable();
        this.comuneDepGroup.disable();
      }
      let i = 0;
      for (let d of this.dettaglioSoggetto.depositi) {
        this.filteredOptionsProvinceDep.push(this.provinceDepGroup[i].controls['provDeposito'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterProvincia(name) : this.province.slice())
          ));
        this.provinceDepSelected[i].setValue(this.province.find(a => a.id == d.ubicazione.idProvincia));
        this.provinceDepGroup[i].controls['provDeposito'].setValue(this.province.find(a => a.id == d.ubicazione.idProvincia));
        if (d.ubicazione.idProvincia != null) {
          this.comuniDepSelected[i].setValue(this.mapComuniDep.get(d.ubicazione.idProvincia).find(a => a.id == d.ubicazione.idComune));
          this.comuniDepGroup[i].controls['comDep'].setValue(this.mapComuniDep.get(d.ubicazione.idProvincia).find(a => a.id == d.ubicazione.idComune));
        }
        this.filteredOptionsComuniDep.push(this.comuniDepGroup[i].controls['comDep'].valueChanges
          .pipe(
            startWith(''),
            map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
            map(name => name ? this._filterComuneArrayDep(name, d.ubicazione.idProvincia) : this.mapComuniDep.get(d.ubicazione.idProvincia))
          ));
        this.provinceDepGroup[i].disable();
        this.comuniDepGroup[i].disable();
        i++;
      }
    }
  }

  _filterProvincia(denominazione: string): ProvinciaVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.province.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  _filterComuneSede(denominazione: string): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.comuniSede.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  _filterComuneDep(denominazione: string): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.comuniDep.filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  _filterComuneArrayDep(denominazione: string, index: number): ComuneVO[] {
    const filterValue = denominazione.toLowerCase();
    return this.mapComuniDep.get(index).filter(option => option.denominazione.toLowerCase().startsWith(filterValue));
  }

  click(event: any, s: string) {
    if (s == 'PS') {
      if (event.option.value.id != this.ubicazioneSede.idProvincia) {
        this.comuneSedeSelected = new FormControl();
        this.comuneSedeGroup.controls['comSede'].setValue(null);
      }
      this.ubicazioneSede.idProvincia = event.option.value.id;
      this.provinciaSedeSelected = event.option.value;

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
      this.dep.ubicazione.idProvincia = this.provinciaDepGroup.controls['provDep'].value.id;
    }
  }

  check(s: string) {
    setTimeout(() => {
      if (s == 'PS') {
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
      if (event.option.value.id != this.dettaglioSoggetto.depositi[index].ubicazione.idProvincia) {
        this.comuniDepSelected[index] = new FormControl();
        this.comuniDepGroup[index].controls['comDep'].setValue(null);
      }
      this.provinceDepSelected[index] = event.option.value;
      this.dettaglioSoggetto.depositi[index].ubicazione.idProvincia = event.option.value.id;
      this.filteredOptionsComuniDep[index] = this.comuniDepGroup[index].controls['comDep'].valueChanges
        .pipe(
          startWith(''),
          map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
          map(name => name ? this._filterComuneArrayDep(name, event.option.value.id) : this.mapComuniDep.get(event.option.value.id).slice())
        );
      this.comuniDepGroup[index].enable();

    }
    else if (s == "C") {
      this.comuniDepSelected[index] = event.option.value;
      this.dettaglioSoggetto.depositi[index].ubicazione.idComune = event.option.value.id;
    }
  }
  checkDep(index: number, s: string) {
    setTimeout(() => {
      if (s == "P") {
        if (!this.provinceDepSelected[index] ||
          (this.provinceDepGroup[index].controls['provDeposito']
            && this.provinceDepSelected[index] !== this.provinceDepGroup[index].controls['provDeposito'].value
            && this.provinceDepGroup[index].controls['provDeposito'].value !== this.province.find(a => a.id == this.dettaglioSoggetto.depositi[index].ubicazione.idProvincia))) {
          this.provinceDepGroup[index].controls['provDeposito'].setValue(null);
          this.provinceDepSelected[index] = new FormControl();
          this.comuniDepGroup[index].controls['comDep'].setValue(null);
          this.comuniDepSelected[index] = new FormControl();
          this.dettaglioSoggetto.depositi[index].ubicazione.idProvincia = null;
          this.dettaglioSoggetto.depositi[index].ubicazione.idComune = null;
          this.comuniDepGroup[index].disable();
        }
      }
      else if (s == "C") {
        if (!this.comuniDepSelected[index] ||
          (this.comuniDepGroup[index].controls['comDep']
            && this.comuniDepSelected[index] !== this.comuniDepGroup[index].controls['comDep'].value
            && this.comuniDepGroup[index].controls['comDep'].value !== this.mapComuniDep.get(this.dettaglioSoggetto.depositi[index].ubicazione.idProvincia).find(a => a.id == this.dettaglioSoggetto.depositi[index].ubicazione.idComune))) {
          this.comuniDepGroup[index].controls['comDep'].setValue(null);
          this.comuniDepSelected[index] = new FormControl();
          this.dettaglioSoggetto.depositi[index].ubicazione.idComune = null;
        }
      }
    }, 200);
  }

  displayFnc(a?: any): string | undefined { //per comune
    return a ? a.denominazione : undefined;
  }

  // Gestione allegati
  handleFileInput(files: FileList) {
    this.files = files[0];
    this.isLogoUploadedButNonSaved = true;
  }

  addDeposito() {
    this.dettaglioSoggetto.depositi.push(this.dep);
    let comuneDep = new FormControl();
    comuneDep.setValue(this.comuniDep.find(a => a.id == this.dep.ubicazione.idComune));
    this.comuniDepSelected.push(comuneDep);
    this.comuniDepGroup.push(new FormGroup({ comDep: comuneDep }));
    this.comuniDepGroup[this.comuniDepGroup.length - 1].disable();
    this.filteredOptionsComuniDep.push(this.comuniDepGroup[this.comuniDepGroup.length - 1].controls['comDep'].valueChanges
      .pipe(
        startWith(''),
        map(value => (typeof value === 'string' || value == null) ? value : value.denominazione),
        map(name => name ? this._filterComuneArrayDep(name, this.dep.ubicazione.idProvincia) : this.mapComuniDep.get(this.dep.ubicazione.idProvincia))
      ));

    let provinciaDep = new FormControl();
    if (this.provinciaDepGroup.controls['provDep'] && this.provinciaDepGroup.controls['provDep'].value) {
      provinciaDep.setValue(this.province.find(b => b.id == this.provinciaDepGroup.controls['provDep'].value.id));
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
    if (this.dep.denominazione == null || this.dep.denominazione.length == 0 || this.isRegione || this.isAmp)
      return true;
    else return false;
  }

  checkIsDepositoPrevalenteinList() {
    for (let deposito of this.dettaglioSoggetto.depositi) {
      if (deposito.depositoPrevalenteFlg) {
        return true;
      }
    }
    return false;
  }

  checkIsIBANinList() {
    for (let dato of this.dettaglioSoggetto.datiBancari) {
      if (dato.doatpl) {
        return true;
      }
    }
    return false;
  }

  removeDeposito(index: number) {
    if (this.dettaglioSoggetto.depositi[index].depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = true;
    }
    this.dettaglioSoggetto.depositi.splice(index, 1);
    this.comuniDepSelected.splice(index, 1);
    this.filteredOptionsComuniDep.splice(index, 1);
    this.comuniDepGroup.splice(index, 1);
    this.provinceDepSelected.splice(index, 1);
    this.filteredOptionsProvinceDep.splice(index, 1);
    this.provinceDepGroup.splice(index, 1);

    if (this.dettaglioSoggetto.depositi.length == 0) {
      this.isNotSelectedDepositoPrevalente = null;
    }
  }
  addDatiBancari() {
    this.dettaglioSoggetto.datiBancari.push(this.dato);
    this.dato = new DatiBancariVO();

    if (this.dato.doatpl || this.checkIsIBANinList()) {
      this.isNotSelectedIBAN = false;
    } else {
      this.isNotSelectedIBAN = true;
    }

  }

  isAddDatiBancariDisabled() {
    if (this.dato.iban == null || this.dato.iban.length == 0 || this.isRegione || this.isAmp)
      return true;
    else return false;
  }

  removeDatiBancari(index: number) {
    if (this.dettaglioSoggetto.datiBancari[index].doatpl) {
      this.isNotSelectedIBAN = true;
    }
    this.dettaglioSoggetto.datiBancari.splice(index, 1);

    if (this.dettaglioSoggetto.datiBancari.length == 0) {
      this.isNotSelectedIBAN = null;
    }
  }


  denominazioneDepChange() {
    if (this.dep.denominazione != null && this.dep.depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = false;
    } else if (this.dep.denominazione == null && this.dep.depositoPrevalenteFlg && !this.checkIsDepositoPrevalenteinList()) {
      this.isNotSelectedDepositoPrevalente = true
    }
  }

  IBANChange() {
    if (this.dato.iban != null && this.dato.doatpl) {
      this.isNotSelectedIBAN = false;
    } else if (this.dato.iban == null && this.dato.doatpl && !this.checkIsIBANinList()) {
      this.isNotSelectedIBAN = true
    }
  }

  depositoPrevalenteOnChange() {
    if (this.dep.depositoPrevalenteFlg) {
      if (this.dep.denominazione != null) {
        this.isNotSelectedDepositoPrevalente = false;
      }
      for (let deposito of this.dettaglioSoggetto.depositi) {
        if (deposito.depositoPrevalenteFlg) {
          let dialogRef = this.dialog.open(DialogComponentConfirm, {
            width: '600px',
            data: { type: "Attenzione", message: "Esiste già un deposito principale; si vuole rendere tale il deposito corrente?" }
          });

          dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
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
      for (let banca of this.dettaglioSoggetto.datiBancari) {
        if (banca.doatpl) {
          let dialogRef = this.dialog.open(DialogComponentConfirm, {
            width: '600px',
            data: { type: "Attenzione", message: "Esiste già un IBAN per contributi Dotazione Organica; si vuole rendere tale l'IBAN corrente?" }
          });

          dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
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


  listaDoatplCheck(index) {
    for (let banca of this.dettaglioSoggetto.datiBancari) {
      if (banca.doatpl && banca != this.dettaglioSoggetto.datiBancari[index]) {
        let dialogRef = this.dialog.open(DialogComponentConfirm, {
          width: '600px',
          data: { type: "Attenzione", message: "Esiste già un IBAN per contributi Dotazione Organica; si vuole rendere tale l'IBAN corrente?" }
        });

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
          if (result) {
            banca.doatpl = !result;

          } else {
            this.dettaglioSoggetto.datiBancari[index].doatpl = false;
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
        console.log('The dialog was closed');
        if (result) {
          this.dato.doatpl = !result;

        } else {
          this.dettaglioSoggetto.datiBancari[index].doatpl = false;
        }
      });
    }

    if (this.dettaglioSoggetto.datiBancari[index].doatpl) {
      this.isNotSelectedIBAN = false;
    } else {
      this.isNotSelectedIBAN = true;
    }

  }

  listaDepositoPrevalenteOnChange(index) {
    for (let deposito of this.dettaglioSoggetto.depositi) {
      if (deposito.depositoPrevalenteFlg && deposito != this.dettaglioSoggetto.depositi[index]) {
        let dialogRef = this.dialog.open(DialogComponentConfirm, {
          width: '600px',
          data: { type: "Attenzione", message: "Esiste già un deposito principale; si vuole rendere tale il deposito corrente?" }
        });

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
          if (result) {
            deposito.depositoPrevalenteFlg = !result;

          } else {
            this.dettaglioSoggetto.depositi[index].depositoPrevalenteFlg = false;
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
        console.log('The dialog was closed');
        if (result) {
          this.dep.depositoPrevalenteFlg = !result;

        } else {
          this.dettaglioSoggetto.depositi[index].depositoPrevalenteFlg = false;
        }
      });
    }

    if (this.dettaglioSoggetto.depositi[index].depositoPrevalenteFlg) {
      this.isNotSelectedDepositoPrevalente = false;
    } else {
      this.isNotSelectedDepositoPrevalente = true;
    }
  }

  @ViewChild('soggettoForm') formGroup: NgForm;
  isValidCampiRequired() {
    if (this.formGroup && this.formGroup.form) {
      if (this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico == 2) {
        if (this.formGroup.form.get('denomAAEP') && this.formGroup.form.get('denomAAEP').hasError('required')) return true;
        if (this.formGroup.form.get('nomeRappresentanteLegale') && this.formGroup.form.get('nomeRappresentanteLegale').hasError('required')) return true;
        if (this.formGroup.form.get('cognomeRappresentanteLegale') && this.formGroup.form.get('cognomeRappresentanteLegale').hasError('required')) return true;
        if (this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('required')) return true;
        if (this.formGroup.controls['denomDep'] && this.formGroup.controls['denomDep'].hasError('required')) return true;
        for (let i = 0; i < this.dettaglioSoggetto.depositi.length; i++) {
          if (this.hasDenomDepError(i))
            return true;
        }
        if (this.formGroup.controls['iban'] && this.formGroup.controls['iban'].hasError('required')) return true;
        for (let i = 0; i < this.dettaglioSoggetto.datiBancari.length; i++) {
          if (this.hasIbanError(i))
            return true;
        }
      }
      if (this.formGroup.form.get('denomBreve') && this.formGroup.form.get('denomBreve').hasError('required')) return true;
      if (this.formGroup.form.get('denomOsservatorioNaz') && this.formGroup.form.get('denomOsservatorioNaz').hasError('required')) return true;
      if (this.formGroup.controls['codOsservatorioNaz'] && (this.formGroup.controls['codOsservatorioNaz'].hasError('required') || this.formGroup.controls['codOsservatorioNaz'].invalid)) return true;
      if (this.formGroup.form.get('partitaIva') && this.formGroup.form.get('partitaIva').hasError('required')) return true;
      if (this.formGroup.form.get('codiceFiscale') && this.formGroup.form.get('codiceFiscale').hasError('required')) return true;
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
    if (this.formGroup.form.get('dataCessazione') && this.formGroup.form.get('dataCessazione').invalid) return true;
    if (this.formGroup.controls['codCsrBip'] && (this.formGroup.controls['codCsrBip'].hasError('integer') || this.formGroup.controls['codCsrBip'].errors)) return true;
    if (this.formGroup.controls['partitaIva'] && (this.formGroup.controls['partitaIva'].hasError('minlength') || this.formGroup.controls['partitaIva'].errors)) return true;
    if (this.formGroup.controls['codiceFiscale'] && (this.formGroup.controls['codiceFiscale'].hasError('pattern') || this.formGroup.controls['codiceFiscale'].errors)) return true;
    if (this.formGroup.controls['codOsservatorioNaz'] && this.formGroup.controls['codOsservatorioNaz'].errors && !this.formGroup.controls['codOsservatorioNaz'].hasError('pattern')) return true;
    if ((this.isNotSelectedDepositoPrevalente != null && this.isNotSelectedDepositoPrevalente == true) && this.dettaglioSoggetto.depositi.length > 0) return true;
    if ((this.isNotSelectedIBAN != null && this.isNotSelectedIBAN == true) && this.dettaglioSoggetto.datiBancari.length > 0) return true;

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

  private image: any;
  viewLogo() {
    this.soggettoService.getLogoByIdSoggetto(this.id).subscribe(res => {
      let objectURL = URL.createObjectURL(res);
      this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      const dialogRef = this.dialog.open(ImageDialogComponent, {
        width: '600px',
        data: { image: this.image }
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }, err => {
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      console.error(errorRest.message);
    });
  }

  viewLogoNotDb() {
    let objectURL = URL.createObjectURL(this.files);
    this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    const dialogRef = this.dialog.open(ImageDialogComponent, {
      width: '600px',
      data: { image: this.image }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  downloadDocNotDb() {
    saveAs(this.files, "logo.jpg");
  }

  downloadDoc() {
    this.soggettoService.getLogoByIdSoggetto(this.id).subscribe(res => {
      saveAs(res, "logo.jpg");
    }, err => {
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      console.error(errorRest.message);
    });
  }

  eliminaDoc() {
    this.dettaglioSoggetto.isLogoUploaded = false;
    this.isLogoUploadedButNonSaved = false;
    this.files = undefined;
  }

  eliminaDocDb() {
    this.soggettoService.eliminaLogoDb(this.id).subscribe(
      res => {
        this.snackBar.open("Logo eliminato con successo!", "Chiudi", {
          duration: 2000,
        });
      },
      err => {
        let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
        console.error(errorRest.message);
      }
    );;
    this.dettaglioSoggetto.isLogoUploaded = false;
    this.files = undefined;
  }

  indietro() {
    this.loadedSoggetto = false;
    if (this.route.snapshot.paramMap.get('action') == 'inserisci')
      this.router.navigate(['/ricercaSoggetto']);
    else if (this.route.snapshot.paramMap.get('action') == 'ricerca')
      window.history.back();
  }

  feedback: string;
  save() {
    this.loadComplete = false;
    this.loadedSave = false;
    if (this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico == 2) {
      this.dettaglioSoggetto.dataInizio = this.dataIn.toDate();
      if (this.dataCes != null)
        this.dettaglioSoggetto.dataCessazione = this.dataCes.toDate();
      if (this.dep.denominazione != null && this.dep.denominazione.length > 0)
        this.addDeposito();
      if (this.dato.iban != null && this.dato.iban.length > 0)
        this.addDatiBancari();
    }
    this.dettaglioSoggetto.ubicazioneSede = this.ubicazioneSede;

    this.soggettoService.modificaSoggetto(this.dettaglioSoggetto, this.files).subscribe(
      data => {
        this.loadComplete = true;
        this.loadedSave = true;
        if (this.files != null)
          this.dettaglioSoggetto.isLogoUploaded = true;
        this.isLogoUploadedButNonSaved = false;
        window.scrollTo(0, 0);
        this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
          duration: 2000,
        });
        this.feedback = "";
        return data;
      }, err => {
        CommonsHandleException.authorizationError(err, this.router);
        let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
        if (errorRest.type == TypeErrorRest.OK) {
          this.feedback = errorRest.message;
        }
        else {
          this.feedback = "Si è verificato un errore in fase di salvataggio";
        }
        //this.loadDettaglioSoggettoAndChoices();
        console.error("this.feedback =" + this.feedback);
        window.scrollTo(0, 0);
        this.loadComplete = true;
        this.loadedSave = true;
        if (this.dato.iban.length < 27) { }


        if (errorRest.codice == 'TFND') {
          setTimeout(() => { this.formGroup.controls['codOsservatorioNaz'].setErrors({ duplicato: true }) }, 200);
        }
        if (errorRest.codice == 'PID') {
          setTimeout(() => { this.formGroup.controls['partitaIva'].setErrors({ duplicato: true }) }, 2000);
        }
        if (errorRest.codice == 'CFD') {
          setTimeout(() => { this.formGroup.controls['codiceFiscale'].setErrors({ duplicato: true }) }, 2000);
        }
        if (errorRest.codice == 'CBD') {
          setTimeout(() => { this.formGroup.controls['codCsrBip'].setErrors({ duplicato: true }) }, 200);
        }
      });
  }

  isLoading() {
    if (!this.loadedSave || !this.loadedSoggetto || !this.loadedRuoloUtente) return true;
    if (this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico == 1)
      return false;
    if (!this.loadedProv) return true;
    return false;
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


  //controllo lunghezza IBAN
  erroreLength(length: number) {
    if (length > 0 && length < 27)
      this.formGroup.controls['iban'].setErrors({ lunghezza: true })
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

}

export enum Action {
  EDIT = "E",
  VIEW = "V",
}

