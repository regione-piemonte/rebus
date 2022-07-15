/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, PageEvent } from '@angular/material';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { Router } from '@angular/router';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { MatDialog } from '@angular/material';
import { UserInfo } from '../../vo/funzionario-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { ElencoSoggettoService } from '../../services/elenco-soggetto.service';
import { UserService } from '../../services/user.service';
import { SoggettoService } from '../../services/soggetto.service';
import { SoggettoRicercaVO } from '../../vo/soggetto-ricerca-vo';
import { FiltroSoggettoVO } from '../../vo/filtro-soggetto';
import { SoggettoGiuridicoVO } from '../../vo/soggetto-giuridico-vo';
import { CommonsHandleException } from '../../commons/commons-handle-exception';

@Component({
    selector: 'app-elenco-soggetto',
    templateUrl: './elenco-soggetto.component.html',
    styleUrls: ['./elenco-soggetto.component.scss']
})

@DestroySubscribers()
export class ElencoSoggettoComponent implements OnInit {
    //columns
    staticColumnsAzienda: Array<String> = ['tipologia', 'codRegionale', 'codOsservatorioNaz', 'partitaIva', 'denominazioneENaturaGiuridica', 'dataAggiornamento', 'azioni'];

    isUtenteAzienda: boolean;
    isModificaAbilitata: boolean;
    isCancellaAbilitato: boolean;
    isConsultaAbilitato: boolean;
    funzionario: UserInfo;
    displayedColumns: Array<String>;
    soggettiRicerca: Array<SoggettoRicercaVO> = new Array<SoggettoRicercaVO>();
    //DATASOURCE
    dataSource: SoggettoDatasource;


    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    defaultPageSize: number;
    pageSizeOption: Array<Number>
    pageEvent: PageEvent;

    @Output() isLoadingEvent = new EventEmitter<boolean>();


    public subscribers: any = {};
    constructor(private elencoSoggettoService: ElencoSoggettoService,
        private userService: UserService,
        private soggettoService: SoggettoService,
        private router: Router,
        public dialog: MatDialog,
    ) { }

    ngOnInit() {

        this.pageSizeOption = [200, 1000, 2500, 5000];
        this.defaultPageSize = 200;this.dataSource = null;
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_ANAGRAFICA_SOGGETTO);
            this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_SOGGETTO);
        });
    }

    aggiorna(filtro: FiltroSoggettoVO) {
        this.isLoadingEvent.emit(true);
        if (filtro) {
            this.elencoSoggettoService.filtraElencoSoggetto(filtro).subscribe(data => {
                this.setElencoSoggetti(data);
            }, err => {
                console.error(err);
                CommonsHandleException.authorizationError(err, this.router);
            });
        } else {
            this.elencoSoggettoService.elencoSoggetto().subscribe(data => {
                this.setElencoSoggetti(data);
            }, err => {
                console.error(err);
                CommonsHandleException.authorizationError(err, this.router);
            });
        }
    }

    setElencoSoggetti(data: SoggettoGiuridicoVO[]) {
        this.displayedColumns = this.staticColumnsAzienda;
        this.soggettiRicerca = new Array<SoggettoRicercaVO>();
        //estrazione e settaggio colonne
        this.soggettoService.getNatureGiuridiche().subscribe(data2 => {
            this.soggettoService.getTipiSoggettoGiuridico(true).subscribe(data3 => {
                for (let d of data) {
                    let s = new SoggettoRicercaVO();
                    if (d.denominazioneRicerca == null || d.denominazioneRicerca == "")
                        s.denominazione = d.denomOsservatorioNaz;
                    else s.denominazione = d.denominazioneRicerca;
                    s.tipologia = data3.find(t => t.id == d.idTipoSoggettoGiuridico);
                    s.codOsservatorioNaz = d.codOsservatorioNaz;
                    s.codRegionale = d.codRegionale;
                    s.partitaIva = d.partitaIva;
                    s.aziendaNonAttiva = !d.aziendaAttiva;
                    s.aziendaCessata = d.aziendaCessata;
                    s.id = d.id;
                    s.dataAggiornamento = d.dataAggiornamento;
                    s.naturaGiuridica = data2.find(a => a.id == d.idNaturaGiuridica);
                    this.soggettiRicerca.push(s);
                }
                this.dataSource = new SoggettoDatasource(this.soggettiRicerca);
                this.soggettiRicerca = new Array<SoggettoRicercaVO>();
                this.dataSource.paginator = this.paginator;
                this.dataSource.sortingDataAccessor = (item, property) => {
                    switch (property) {
                        case 'denominazioneENaturaGiuridica':
                            return item.denominazione;
                        case 'naturaGiuridica':
                            return item.naturaGiuridica != null ? item.naturaGiuridica.descrizione : null;
                        case 'tipologia':
                            return item.tipologia != null ? item.tipologia.descrizione : null;
                        default:
                            return item[property];
                    }
                };
                this.dataSource.sort = this.sort;
                this.isLoadingEvent.emit(false);
            });
        });
    }


    dettaglioSoggetto(value: number) {
        this.router.navigate(['/dettaglioSoggetto', value],
            {
                queryParams: {
                },
            });
    }

    modificaSoggetto(value: number) {
        this.router.navigate(['/modificaSoggetto/' + value, { action: 'ricerca' }]);
    }

    replacePoint(str) {
        return (str && str.toString()) ? str.toString().replace(".", ",") : "";
    }

    troncaCaratteri(str) {
        let varStr: String;
        if (str && str.toString()) {
            varStr = str.toString();
            if (varStr.length > 70) {
                let varSubStr: String = varStr.substr(0, 67);
                varStr = varSubStr.trim() + "...";
            }
        }

        return varStr != null ? varStr.toString() : null;
    }

    ngOnDestroy() {

    }
}

export class SoggettoDatasource extends MatTableDataSource<SoggettoRicercaVO> {
    constructor(data: SoggettoRicercaVO[]) {
        super(data);

    }

}

