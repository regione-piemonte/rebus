/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class InserisciContrattoVO extends ParentVO {

	private String descrizione;
	private String codiceIdentificativoNazionale;
	private String numeroRepertorio;
	private Long idBacino;
	private Long idTipoAffidamento;
	private Long idModalitaAffidamento;
	private List<Long> idAmbTipoServizi;
	private String accordoDiProgramma;
	private boolean grossCost;
	private String cig;
	private Date dataStipula;
	private Date dataInizioValidita;
	private Date dataFineValidita;

	// Dati Contraenti

	private Long idEnteCommittente;
	private Long idSoggettoEsecutore;
	private Long idTipoSoggettoEsecutore;
	private Long idTipoRaggruppamento;
	private Long idAziendaMandataria;
	private List<Long> idComposizioneRaggruppamento;

	// Allegato
	private List<AllegatoVO> files;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceIdentificativoNazionale() {
		return codiceIdentificativoNazionale;
	}

	public void setCodiceIdentificativoNazionale(String codiceIdentificativoNazionale) {
		this.codiceIdentificativoNazionale = codiceIdentificativoNazionale;
	}

	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}

	public Long getIdBacino() {
		return idBacino;
	}

	public void setIdBacino(Long idBacino) {
		this.idBacino = idBacino;
	}

	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public Long getIdModalitaAffidamento() {
		return idModalitaAffidamento;
	}

	public void setIdModalitaAffidamento(Long idModalitaAffidamento) {
		this.idModalitaAffidamento = idModalitaAffidamento;
	}

	public List<Long> getIdAmbTipoServizi() {
		return idAmbTipoServizi;
	}

	public void setIdAmbTipoServizi(List<Long> idAmbTipoServizi) {
		this.idAmbTipoServizi = idAmbTipoServizi;
	}

	public String getAccordoDiProgramma() {
		return accordoDiProgramma;
	}

	public void setAccordoDiProgramma(String accordoDiProgramma) {
		this.accordoDiProgramma = accordoDiProgramma;
	}

	public boolean isGrossCost() {
		return grossCost;
	}

	public void setGrossCost(boolean grossCost) {
		this.grossCost = grossCost;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Long getIdEnteCommittente() {
		return idEnteCommittente;
	}

	public void setIdEnteCommittente(Long idEnteCommittente) {
		this.idEnteCommittente = idEnteCommittente;
	}

	public Long getIdSoggettoEsecutore() {
		return idSoggettoEsecutore;
	}

	public void setIdSoggettoEsecutore(Long idSoggettoEsecutore) {
		this.idSoggettoEsecutore = idSoggettoEsecutore;
	}

	public Long getIdTipoSoggettoEsecutore() {
		return idTipoSoggettoEsecutore;
	}

	public void setIdTipoSoggettoEsecutore(Long idTipoSoggettoEsecutore) {
		this.idTipoSoggettoEsecutore = idTipoSoggettoEsecutore;
	}

	public Long getIdTipoRaggruppamento() {
		return idTipoRaggruppamento;
	}

	public void setIdTipoRaggruppamento(Long idTipoRaggruppamento) {
		this.idTipoRaggruppamento = idTipoRaggruppamento;
	}

	public Long getIdAziendaMandataria() {
		return idAziendaMandataria;
	}

	public void setIdAziendaMandataria(Long idAziendaMandataria) {
		this.idAziendaMandataria = idAziendaMandataria;
	}

	public List<Long> getIdComposizioneRaggruppamento() {
		return idComposizioneRaggruppamento;
	}

	public void setIdComposizioneRaggruppamento(List<Long> idComposizioneRaggruppamento) {
		this.idComposizioneRaggruppamento = idComposizioneRaggruppamento;
	}

	public List<AllegatoVO> getFiles() {
		return files;
	}

	public void setFiles(List<AllegatoVO> files) {
		this.files = files;
	}

}
