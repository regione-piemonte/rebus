/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from "@angular/forms";
import { HttpClient } from '@angular/common/http';
import { UploadEvent } from 'ngx-file-drop';
import { Router } from '@angular/router';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { ExcelService } from '../../services/excel.service';
import { CommonsHandleException } from '../../commons/commons-handle-exception';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent {
  form: FormGroup;
  loading: boolean = false;
  msgEsitoUpload: String;
  msgEsitoUploadOk: String;


  @ViewChild('fileInput') fileInput: ElementRef;
  context: string;


  constructor(
    private router: Router,
    private fb: FormBuilder,
    private http: HttpClient,
    private navbarFilterContext: NavbarFilterContext,
    private excelService: ExcelService,
  ) {
    this.createForm();
  }

  public dropped(event: UploadEvent) {
    var form = new FormData();
    if (event.files[0].fileEntry.file) {
      var self = this;
      event.files[0].fileEntry.file(function (file) {
        let reader = new FileReader();
        reader.readAsArrayBuffer(file);
        reader.onloadend = function () {
          var data: any = reader.result;
          var bytes = new Uint8Array(data);
          var blob = new Blob([bytes], { type: 'application/vnd.ms-excel' });
          form.append('uploadedFile', blob);
          self.inserisciExcel(form);
        };
      });
    }
  }

  createForm() {
    if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
      var intervalContesti = setInterval(() => {
        this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
          this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        clearInterval(intervalContesti);
      }, 200);
    } else {
      this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
      if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
        this.context = this.navbarFilterContext.InfoFiltro.cod;
      }
    }
    this.form = this.fb.group({
      uploadedFile: null
    });
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      this.form.get('uploadedFile').setValue(file);
    }
  }

  private prepareSave(): any {
    let input = new FormData();
    input.append('uploadedFile', this.form.get('uploadedFile').value);
    return input;
  }

  public inserisciExcel(formModel: FormData) {
    this.loading = true;
    this.msgEsitoUpload = null;
    this.msgEsitoUploadOk = null;
    if (this.form.get('uploadedFile') != null) {
      this.http.get('assets/modulo.xls', { responseType: 'blob' }).subscribe(res => {
        formModel.append('uploadTemplate', res)
        this.excelService.inserisciExcel(formModel).subscribe(res => {

          if (res != null && res.size > 0)
            window.open(window.URL.createObjectURL(res))
          let input = new FormData();
          input.append('txt', "txt");
          //# chiamo un secondo servizio per avere il dettaglio dell'errore da visualizzare
          this.excelService.dettagliErroriExcel(input).subscribe(msgErrore => {
            if (msgErrore != null && msgErrore.indexOf('correttamente') >= 0) {
              this.msgEsitoUploadOk = msgErrore;
            } else {
              this.msgEsitoUpload = msgErrore;
            }
            this.loading = false;
          },
            error => {
              this.msgEsitoUpload = 'Si è verificato un errore durante il caricamento del file. Si prega di riprovare.';
              this.loading = false;
              console.error(error);
            });

        },
          error => {
            CommonsHandleException.authorizationError(error,this.router);
            this.msgEsitoUpload = 'Si è verificato un errore durante il caricamento del file. Si prega di riprovare.';
            this.loading = false;
          });
      });
    }
  }

  onSubmit() {
    const formModel = this.prepareSave();
    this.inserisciExcel(formModel);
  }

  clearFile() {
    this.form.get('uploadedFile').setValue(null);
    this.fileInput.nativeElement.value = '';
  }

}
