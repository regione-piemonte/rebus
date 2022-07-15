/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { MatTableDataSource, MatSort, MatPaginator, PageEvent, MatDialog, DateAdapter } from '@angular/material';
import { ContrattoRicercaVO } from '../../vo/contratto-ricerca-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { ContrattoService } from '../../services/contratto.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { ElencoContrattoService } from '../../services/elenco-contratto.service';
import { SoggettoService } from '../../services/soggetto.service';
import { EnteVO } from '../../vo/ente-vo';
import { SoggettoEsecutoreVO } from '../../vo/soggetto-esecutore-vo';
import { EnteService } from '../../services/ente.service';
import { FiltroContrattoVO } from '../../vo/filtro-contratto-vo';
import { ContrattoVO } from '../../vo/contratto-vo';
import { CommonsHandleException } from '../../commons/commons-handle-exception';


@Component({
  selector: 'app-elenco-contratto',
  templateUrl: './elenco-contratto.component.html',
  styleUrls: ['./elenco-contratto.component.scss']
})

@DestroySubscribers()
export class ElencoContrattoComponent implements OnInit {
  //columns
  staticColumnsContratto: Array<String> = ['codRegionale', 'codIdentificativo', 'numRepertorio', 'enteCommittenteENaturaGiuridica', 'soggEsecutoreENaturaGiuridica', 'descrizione', 'dataScadenza', 'dataFineUltimaProroga', 'dataAggiornamento', 'azioni'];

  isUtenteAzienda: boolean;
  isModificaAbilitata: boolean;
  isCancellaAbilitato: boolean;
  isConsultaAbilitato: boolean;
  funzionario: UserInfo;
  // ridotto: boolean;
  displayedColumns: Array<String>;
  contrattiRicerca: Array<ContrattoRicercaVO> = new Array<ContrattoRicercaVO>();

  //DATASOURCE
  dataSource: ContrattoDatasource;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  defaultPageSize: number;
  pageSizeOption: Array<Number>
  pageEvent: PageEvent;

  @Output() isLoadingEvent = new EventEmitter<boolean>();


  public subscribers: any = {};
  constructor(private elencoContrattoService: ElencoContrattoService,
    private soggettoService: SoggettoService,
    private enteService: EnteService,
    private userService: UserService,
    private router: Router,
    public dialog: MatDialog,
    private dateAdapter: DateAdapter<Date>
  ) {
    dateAdapter.setLocale('it-IT');
  }

  ngOnInit() {
    this.pageSizeOption = [200, 1000, 2500, 5000];
    this.defaultPageSize = 200;

    ///SOTTOSCRIZIONE AL SERVICE RICHIAMATO NELLA INBOX

    this.dataSource = null;
    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
      this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_ANAGRAFICA_CONTRATTO);
      this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_CONTRATTO);
    });
  }

  aggiorna(filtro: FiltroContrattoVO) {
    this.isLoadingEvent.emit(true);
    if (filtro) {
      this.elencoContrattoService.filtraElencoContratto(filtro).subscribe(data => {
        this.setElencoContratto(data);
      }, err => {
        console.error(err);
        CommonsHandleException.authorizationError(err, this.router);
      });
    } else {

      this.elencoContrattoService.elencoContratto().subscribe(data => {
        this.setElencoContratto(data);
      }, err => {
        console.error(err);
        CommonsHandleException.authorizationError(err, this.router);
      });
    }
  }

  setElencoContratto(data: ContrattoVO[]) {
    this.displayedColumns = this.staticColumnsContratto;
    //estrazione e settaggio colonne
    let enti: Array<EnteVO>;
    let soggEsecutori: Array<SoggettoEsecutoreVO>;
    this.enteService.getEnti().subscribe(data2 => {
      enti = data2;
      this.soggettoService.getSoggettiEsecutoriTitolari(null, null).subscribe(data3 => {
        soggEsecutori = data3;
        this.soggettoService.getNatureGiuridiche().subscribe(data4 => {
          this.contrattiRicerca = new Array<ContrattoRicercaVO>();
          for (let d of data) {
            let s = new ContrattoRicercaVO();
            s.codIdentificativo = d.codIdNazionale;
            s.codRegionale = d.codRegionale;
            s.id = d.idContratto;
            s.enteCommittente = enti.find(a => a.id == d.idSogGiuridCommittente);

            s.soggEsecutore = soggEsecutori.find(a => a.id == d.idSogGiuridEsecutore);
            let now = new Date();
            now.setHours(0, 0, 0, 0);
            if (d.dataFineValidita != null) {
              s.dataScadenza = new Date(d.dataFineValidita);
              if (s.dataScadenza.getTime() < now.getTime()) {
                s.contrattoCessato = true;
              }
              if (d.proroghe != null && d.proroghe.length > 0) {
                let dataFineProroga = new Date(d.proroghe[0].dataFineProroga);
                if (dataFineProroga.getTime() > now.getTime()) {
                  s.prorogato = true;
                  s.contrattoCessato = false;
                  s.dataFineUltimaProroga = d.proroghe[0].dataFineProroga;
                }
                else {
                  s.dataFineUltimaProroga = dataFineProroga;
                }
              }
            }
            else {

              s.contrattoCessato = false;
            }
            s.dataAggiornamento = d.dataAggiornamento;
            s.numRepertorio = d.numRepertorio;
            s.descContratto = d.descContratto;
            s.naturaGiuridicaEnte = data4.find(a => a.id == d.idNaturaGiuridicaCommittente);
            s.naturaGiuridicaEsec = data4.find(a => a.id == d.idNaturaGiuridicaEsec);
            this.contrattiRicerca.push(s);
          }
          this.dataSource = new ContrattoDatasource(this.contrattiRicerca);

          this.dataSource.paginator = this.paginator;
          this.dataSource.sortingDataAccessor = (item, property) => {
            switch (property) {
              case 'enteCommittenteENaturaGiuridica':
                return item.enteCommittente ? item.enteCommittente.denomBreve + (item.naturaGiuridicaEnte ? (item.naturaGiuridicaEnte.descrizioneBreve ? item.naturaGiuridicaEnte.descrizioneBreve : "") : "") : "";
              case 'soggEsecutoreENaturaGiuridica':
                return item.soggEsecutore ? item.soggEsecutore.denomBreve + (item.naturaGiuridicaEsec ? (item.naturaGiuridicaEsec.descrizioneBreve ? item.naturaGiuridicaEsec.descrizioneBreve : "") : "") : "";
              default:
                return item[property];
            }
          };

          this.dataSource.sort = this.sort;
          this.isLoadingEvent.emit(false);
        });
      });
    }, err => {
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }


  dettaglioContratto(value: number) {
    this.router.navigate(['/dettaglioContratto/' + value]);
  }

  modificaContratto(value: number) {
    this.router.navigate(['/modificaContratto/' + value, { action: 'ricerca' }]);
  }

  replacePoint(str) {
    return (str && str.toString()) ? str.toString().replace(".", ",") : "";
  }

  troncaCaratteri(str) {
    let varStr: String;
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 31) {
        let varSubStr: String = varStr.substr(0, 28);
        varStr = varSubStr.trim() + "...";
      }
    }

    return varStr.toString();
  }


  troncaDescr(str) {
    let varStr: String;
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 37) {
        let varSubStr: String = varStr.substr(0, 40);
        varStr = varSubStr.trim() + "...";
      }
    }

    return varStr.toString();
  }

  ngOnDestroy() {

  }
}

export class ContrattoDatasource extends MatTableDataSource<ContrattoRicercaVO> {
  constructor(data: ContrattoRicercaVO[]) {
    super(data);

  }

}
