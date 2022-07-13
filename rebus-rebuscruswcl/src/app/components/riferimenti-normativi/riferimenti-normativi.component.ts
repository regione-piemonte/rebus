/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm } from "@angular/forms";
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { RiferimentiNormativiService } from '../../services/riferimenti-normativi.service';
import { MatSnackBar } from '@angular/material';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { UserService } from '../../services/user.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { UserInfo } from '../../vo/funzionario-vo';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';

@Component({
  selector: 'app-riferimenti-normativi',
  templateUrl: './riferimenti-normativi.component.html',
  styleUrls: ['./riferimenti-normativi.component.scss']
})
export class RiferimentiNormativiComponent {
  form: FormGroup;
  loading: boolean = false;
  descrizione: string;
  msgEsitoUpload: String;
  msgEsitoUploadOk: String;


  @ViewChild('fileInput') fileInput: ElementRef;
  @ViewChild('riferimentiform') riferimentiForm: NgForm;
  funzionario: UserInfo;
  isModificaAbilita: any;
  isDownloadAblita: any;
  context: string;


  constructor(private router: Router,
    private riferimentiNormativiService: RiferimentiNormativiService,
    private userService: UserService,
    public snackBar: MatSnackBar,
    private navbarFilterContext: NavbarFilterContext,
    private route: ActivatedRoute, private fb: FormBuilder, private http: HttpClient) {
    this.createForm();
  }

  ngOnInit() {
    if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
      var intervalContesti = setInterval(() => {
        this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
          this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        clearInterval(intervalContesti);
      }, 200);
    } else {
      this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
      if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
        this.context = this.navbarFilterContext.InfoFiltro.cod;
      }
    }

    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      this.isModificaAbilita = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_RIFERIMENTI);
      this.isDownloadAblita = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_RIFERIMENTI);
    });

  }

  createForm() {
    this.form = this.fb.group({
      uploadedFile: null,
      descrizione: null
    });
  }

  handleFileInput(file) {
    if (file.length > 0) {
      let f = file[0];
      this.form.get('uploadedFile').setValue(f);
    }
  }

  private prepareSave(): any {
    let input = new FormData();
    input.append('descrizione', this.descrizione);
    input.append('uploadedFile', this.form.get('uploadedFile').value);
    return input;
  }

  public inserisciFile(formModel: FormData) {
    this.loading = true;
    this.msgEsitoUpload = null;
    this.msgEsitoUploadOk = null;
    if (this.form.get('uploadedFile') != null) {
      this.riferimentiNormativiService.inserisciRiferimento(formModel).subscribe(data => {
        this.loading = false;
        let id: number = data;
        window.scrollTo(0, 0);
        this.riferimentiNormativiService.elencoRiferimenti();
        this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
          duration: 2000,
        });
        setTimeout(() => { this.router.navigate(['/riferimentiNormativi']) }, 2000);
      }, error => {
        CommonsHandleException.authorizationError(error, this.router);
        this.msgEsitoUpload = error.error.message;
        window.scrollTo(0, 0);
        this.loading = false;
      });
    }
    this.clearFile();
  }

  isValidCampiRequired() {
    if (this.riferimentiForm && this.riferimentiForm.form) {
      if (this.riferimentiForm.form.get('descrizioneF') && this.riferimentiForm.form.get('descrizioneF').hasError('required')) return true;
      if (this.form.get('uploadedFile').value == null) return true;
    }
    return false;
  }

  onSubmit() {
    const formModel = this.prepareSave();
    this.inserisciFile(formModel);
  }

  clearFile() {

    this.form.get('uploadedFile').setValue(null);
    this.fileInput.nativeElement.value = '';
    this.riferimentiForm.form.get('descrizioneF').setValue(null);
    this.form.get('descrizione').setValue(null);
    this.riferimentiForm.form.get('descrizioneF').markAsUntouched();
    this.riferimentiForm.resetForm();
  }

}
