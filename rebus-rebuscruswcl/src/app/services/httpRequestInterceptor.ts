/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-15 08:45:30  */
import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ConfigService } from './config.service';

@Injectable()
export class TimeoutInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).timeout(this.config.getTimeout()*1000);
  }

  constructor(private config: ConfigService) { }
}