/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-12 11:14:41  
*/
import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { ConfigService } from '../../services/config.service';
import { DOCUMENT } from '@angular/platform-browser';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { UserInfo } from '../../vo/funzionario-vo';
import { AziendaVO } from '../../vo/azienda-vo';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  @Output() isLogout = new EventEmitter<boolean>();

  funzionario: UserInfo;
  idAziendaSelected: number;
  aziende: Array<AziendaVO>;
  isLoading: boolean;


  constructor(private userService: UserService,
    private configService: ConfigService,
    private router: Router,
    @Inject(DOCUMENT) private document: any) { }

  ngOnInit() {
    this.isLoading = true;
    this.userService.getAziendeFunzionario().subscribe(data => {
      this.aziende = data;
      this.aziende = this.aziende.filter((a, i, arr) => arr.findIndex(t => t.id === a.id) === i); // filtro duplicati
      this.userService.getFunzionarioAzienda().subscribe(data2 => {
        this.funzionario = data2;
        if (this.aziende && this.aziende.length > 0 && this.funzionario.idAzienda) {
          this.aziende.splice(this.aziende.findIndex(a => a.id === this.funzionario.idAzienda), 1);
        }
        this.isLoading = false;
      });
    });
  }

  returnHome() {
    this.isLogout.emit(false);
    this.router.navigateByUrl('/home');
  }

  logOut() {
    this.userService.logOut();
    this.document.location.href = this.configService.getSSOLogoutURL();
  }

  conferma() {
    if (this.idAziendaSelected == 0) {
      this.logOut();
    } else {
      this.cambioAziendaLogin();
    }
  }

  cambioAziendaLogin() {
    this.userService.setIdAziendaUtente(this.idAziendaSelected).subscribe(data => {
      this.funzionario.idAzienda = this.idAziendaSelected;
      this.funzionario.aziendaDesc = this.aziende.find(a => a.id === this.idAziendaSelected).descrizione;
      this.returnHome();
    });
  }

}
