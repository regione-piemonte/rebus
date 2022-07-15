/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConfigService } from '../../services/config.service';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  intervalId = 0;
  seconds: number = 10;
  message: string;


  constructor(private router: Router
  ) { }


  ngOnInit() {
    let param = this.router.parseUrl(this.router.url);
    this.message = param.queryParams.message;
  }

}
