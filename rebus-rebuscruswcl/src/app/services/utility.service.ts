/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ReplaySubject } from 'rxjs';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { ComboVO } from '../vo/combo-vo';
import { ConfigService } from './config.service';
import { CommonsHandleException } from '../commons/commons-handle-exception';
import { ContestoVO } from '../vo/contesto-vo';
import { AutobusVO } from '../vo/autobus-vo';





@Injectable()
export class UtilityService {

  private urlUtility: string = "/parcobustplweb/restfacade/utilita";

  private comboVO = new ReplaySubject<ComboVO[]>();
  private tipologiaDimensionale = new ReplaySubject<String>();

  comboVO$: Observable<Array<ComboVO>> = this.comboVO.asObservable();
  tipologiaDimesionale$: Observable<String> = this.tipologiaDimensionale.asObservable();

  localmap: Map<any, any>;

  //variabile elenco aziende
  private elencoAziendeSubject = new ReplaySubject<any>();
  elencoAziende$: Observable<Array<any>> = this.elencoAziendeSubject.asObservable();


  constructor(private http: HttpClient, private config: ConfigService, private router: Router) {
  }


  elencoDecodifiche() {
    return this.http.get<Array<ComboVO>>(this.urlUtility + '/elencoDecodifiche').subscribe(data => {
      if (data) {
        this.comboVO.next(data);
      }
    }, (err) => {
      CommonsHandleException.handleBlockingError(err, this.router);
    });
  }

  getContesti(): Observable<Array<ContestoVO>> {
    return this.http.get<Array<ContestoVO>>(this.urlUtility + '/getContesto').timeout(1000000);
  }

  getContestoHome(): Observable<ContestoVO> {
    return this.http.get<ContestoVO>(this.urlUtility + '/getContestoHome');
  }

  progressivoTipoDimensione(lunghezza: number, idTipoAllestimento: number) {
    let body = {
      lunghezza: lunghezza,
      idTipoAllestimento: idTipoAllestimento,
    };

    return this.http.post<any>(this.urlUtility + '/progressivoTipoDimensione', body).subscribe(
      (data) => {
        this.tipologiaDimensionale.next(data.descrizione);
      },
      (err) => {
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }

  // ************************* ELENCO AZIENDE *************************//

  elencoAziende() {
    let params = new HttpParams();
    //Timeout 16 minuti
    return this.http.get<Array<AutobusVO>>(this.urlUtility + '/elencoAziende', { params: params }).timeout(1000000).subscribe(
      (data) => {
        if (data.length != null) {
          this.elencoAziendeSubject.next(data);
        }
        return data;
      },
      (err) => {
        console.error(err);
        CommonsHandleException.handleBlockingError(err, this.router);
      });
  }

  troncaTreDigitali(n: string, autobus: any) {
    if (n.includes("-")) {
      autobus.lunghezza = 0;
    } else if (n.includes(".")) {
      var nSplit = n.split(".");
      var int = nSplit[0];
      var dec = nSplit[1];
      var dec3 = dec.substr(0, 3);
      var dec3 = dec.substring(0, 3);
      var nOK = int + "." + dec3
      autobus.lunghezza = nOK;
    } else if (n.includes(",")) {
      var nSplit = n.split(",");
      var int = nSplit[0];
      var dec = nSplit[1];
      var dec3 = dec.substr(0, 3);
      var dec3 = dec.substring(0, 3);
      var nOK = int + "," + dec3
      autobus.lunghezza = nOK;
    }
    else {
      autobus.lunghezza = n;
    }
  }

  troncaDueDigitali(n: string, numToTrunc: string, autobus: any) {
    switch (numToTrunc) {
      case "entita": {
        if (n.includes("-")) {
          autobus.contributoPubblicoAcquisto = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var dec = nSplit[1];
          var dec3 = dec.substr(0, 2);
          var dec3 = dec.substring(0, 2);
          var nOK = int + "." + dec3
          autobus.contributoPubblicoAcquisto = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var dec = nSplit[1];
          var dec3 = dec.substr(0, 2);
          var dec3 = dec.substring(0, 2);
          var nOK = int + "," + dec3
          autobus.contributoPubblicoAcquisto = nOK;
        }
        else {
          autobus.contributoPubblicoAcquisto = n;
        }
        break;
      }
      case "prezzo": {
        if (n.includes("-")) {
          autobus.prezzoTotAcquisto = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var dec = nSplit[1];
          var dec3 = dec.substr(0, 2);
          var dec3 = dec.substring(0, 2);
          var nOK = int + "." + dec3
          autobus.prezzoTotAcquisto = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var dec = nSplit[1];
          var dec3 = dec.substr(0, 2);
          var dec3 = dec.substring(0, 2);
          var nOK = int + "," + dec3
          autobus.prezzoTotAcquisto = nOK;
        }
        else {
          autobus.prezzoTotAcquisto = n;
        }
        break;
      }
    }
  }


  troncaDecimali(n: string, numToTrunc: string, autobus: any) {
    switch (numToTrunc) {
      case "porte": {
        if (n.includes("-")) {
          autobus.numeroPorte = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.numeroPorte = nOK;
        }
        else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.numeroPorte = nOK;
        }
        else {
          autobus.numeroPorte = n;
        }
        break;
      }
      case "postiInPiedi": {
        if (n.includes("-")) {
          autobus.nPostiInPiedi = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiInPiedi = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiInPiedi = nOK;
        }
        else {
          autobus.nPostiInPiedi = n;
        }
        break;
      }
      case "postiASedere": {
        if (n.includes("-")) {
          autobus.nPostiSedere = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiSedere = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiSedere = nOK;
        }
        else {
          autobus.nPostiSedere = n;
        }
        break;
      }
      case "postiRiservati": {
        if (n.includes("-")) {
          autobus.nPostiRiservati = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiRiservati = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.nPostiRiservati = nOK;
        }
        else {
          autobus.nPostiRiservati = n;
        }
        break;
      }
      case "postiInCarrozzina": {
        if (n.includes("-")) {
          autobus.postiCarrozzina = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.postiCarrozzina = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.postiCarrozzina = nOK;
        }
        else {
          autobus.postiCarrozzina = n;
        }
        break;
      }
      case "annoSostProg": {
        if (n.includes("-")) {
          autobus.annoSostProg = 0;
        } else if (n.includes(".")) {
          var nSplit = n.split(".");
          var int = nSplit[0];
          var nOK = int
          autobus.annoSostProg = nOK;
        } else if (n.includes(",")) {
          var nSplit = n.split(",");
          var int = nSplit[0];
          var nOK = int
          autobus.annoSostProg = nOK;
        }
        else {
          autobus.annoSostProg = n;
        }
        break;
      }

    }
  }

  jsonToMap(obj) {
    this.localmap = new Map();
    for (let k of Object.keys(obj)) {
      this.localmap.set(k, obj[k]);
    }

    return this.localmap;

  }

}

