/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';
import { Observable } from './../../../node_modules/rxjs-compat/Observable';
import { ConfigService } from './config.service';
import { timeout } from 'rxjs/operators';

@Injectable()
export class TimeoutInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(timeout(this.config.getTimeout()*1000));
  }

  constructor(private config: ConfigService) { }
}