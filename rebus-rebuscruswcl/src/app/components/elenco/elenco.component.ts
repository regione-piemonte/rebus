/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Massimo.durando
 * @Date: 2018-01-12 11:21:44  */
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource, PageEvent } from '@angular/material';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { Router } from '@angular/router';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { OnDestroy } from '@angular/core/src/metadata/lifecycle_hooks';
import { CancellaDialogComponent } from '../cancelladialog/cancelladialog.component';
import { MatDialog } from '@angular/material';
import { UserInfo } from '../../vo/funzionario-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { UserService } from '../../services/user.service';
import { AutobusService } from '../../services/autobus.service';
import { FiltroAutobusVO } from '../../vo/filtro-autobus-vo';
import { CommonsHandleException } from '../../commons/commons-handle-exception';



@Component({
    selector: 'app-elenco',
    templateUrl: './elenco.component.html',
    styleUrls: ['./elenco.component.scss']
})

@DestroySubscribers()
export class ElencoComponent implements OnInit, OnDestroy {
    //columns

    staticColumnsAzienda: Array<String> = ['presenzaAllegati', 'oggettoRichieste', 'presenzaRendicontazione', 'nTarga', 'primoTelaio', 'nMatricolaAziendale', 'dataPrimaImmatricolazione',
        'tipoAllestimento', 'lunghezza', 'alimentazione', 'omologazioneClasse', 'contribuito', 'flgVerificatoAzienda',
        'flgVerificatoAmp', 'azioni'];

    staticColumnsEnte: Array<String> = ['presenzaAllegati', 'oggettoRichieste', 'presenzaRendicontazione', 'denominazione', 'nTarga', 'primoTelaio', 'nMatricolaAziendale', 'dataPrimaImmatricolazione',
        'tipoAllestimento', 'lunghezza', 'alimentazione', 'omologazioneClasse', 'contribuito', 'flgVerificatoAzienda',
        'flgVerificatoAmp', 'azioni'];

    isUtenteAzienda: boolean;
    isModificaAbilitata: boolean;
    isCancellaAbilitato: boolean;
    isConsultaAbilitato: boolean;
    funzionario: UserInfo;
    displayedColumns: Array<String>;
    //DATASOURCE
    dataSource: AutobusDatasource;



    @ViewChild(MatSort) sort: MatSort;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    defaultPageSize: number;
    pageSizeOption: Array<Number>
    pageEvent: PageEvent;

    @Output() isLoadingEvent = new EventEmitter<boolean>();

    @ViewChild(CancellaDialogComponent)



    public subscribers: any = {};
    constructor(
        private userService: UserService,
        private router: Router,
        public dialog: MatDialog,
        private autobusService: AutobusService
    ) { }

    ngOnInit() {

        this.pageSizeOption = [100, 1000, 2500, 5000];
        this.defaultPageSize = 100;

        ///SOTTOSCRIZIONE AL SERVICE RICHIAMATO NELLA INBOX
        this.dataSource = null;
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS) || this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
            this.isCancellaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA);
            this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS)
        });

    }

    aggiorna(filtro: FiltroAutobusVO) {
        this.isLoadingEvent.emit(true);
        if (filtro) {
            this.autobusService.filtraElencoAutobus(filtro).subscribe(data => {
                this.setElencoAutobus(data);
            }, error => {
                CommonsHandleException.authorizationError(error, this.router);
            });
        } else {
            this.autobusService.elencoAutobus().subscribe(data => {
                this.setElencoAutobus(data);
            }, error => {
                CommonsHandleException.authorizationError(error, this.router);
            });
        }
    }

    resetElencoAutobus() {
        this.setElencoAutobus(new Array<FiltroAutobusVO>());
    }

    setElencoAutobus(data: FiltroAutobusVO[]) {

        if (this.isUtenteAzienda) {
            this.displayedColumns = this.staticColumnsAzienda;
        } else {
            this.displayedColumns = this.staticColumnsEnte;
        }
        //estrazione e settaggio colonne
        this.dataSource = new AutobusDatasource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.isLoadingEvent.emit(false);
    }


    dettagliobus(value: number) {
        //RECUPERA BANDO SEL E SETTORE SEL
        this.router.navigate(['/dettaglioBus', value],
            {
                queryParams: {
                },
            });
    }

    modificabus(value: number) {
        //RECUPERA BANDO SEL E SETTORE SEL
        //("il valore dell id è : " + value);
        this.router.navigate(['/modificaBus/' + value, { action: 'ricerca' }]);
    }

    eliminabuspopup(idBus: number, flagVerificatoAzienda: string) {
        if (flagVerificatoAzienda == 'N') {
            let dialogRef = this.dialog.open(CancellaDialogComponent, {
                height: '200px',
                width: '400px',
                data: { msg: 'Sei sicuro di voler eliminare gli autobus selezionati?' }
            });

            dialogRef.afterClosed().subscribe(result => {
                if (result == 'OK') {
                    this.autobusService.eliminaAutobus([idBus]);

                }
            });
        }
        else {
            let dialogRef = this.dialog.open(CancellaDialogComponent, {
                height: '300px',
                width: '450px',
                data: { msg: 'Impossibile procedere con l\'eliminazione dei bus selezionati in quanto alcuni di essi non sono in bozza. ' }
            });
        }
    }


    replacePoint(str) {
        return (str && str.toString()) ? str.toString().replace(".", ",") : "";
    }

    troncaCaratteri(str) {
        let varStr: String;
        if (str && str.toString()) {
            varStr = str.toString();
            if (varStr.length > 25) {
                let varSubStr: String = varStr.substr(0, 22);
                varStr = varSubStr.trim() + "...";
            }
        }
        return varStr.toString();
    }

    ngOnDestroy() {
    }
}

export class AutobusDatasource extends MatTableDataSource<FiltroAutobusVO> {

    constructor(data: FiltroAutobusVO[]) {
        super(data);
    }

}
