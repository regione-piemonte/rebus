/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-03 11:12:18  
 */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';
import { ConfigService } from '../../services/config.service';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  intervalId = 0;
  seconds: number = 10;
  message: string;


  constructor(private router: Router,
    private userService: UserService, private configService: ConfigService,
    @Inject(DOCUMENT) private document: any) { }


  ngOnInit() {
    let param = this.router.parseUrl(this.router.url);
    this.message = param.queryParams.message;
  }

}
