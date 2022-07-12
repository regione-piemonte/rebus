/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

public class MisurazioneDTO {

	private Long idMisurazione;
	private String primoTelaio;
	private Long idDocMisurazione;
	private Date dataInizio;
	private Date dataFine;
	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;
	private Long idCampagna;
	private String descrizione;
	private Long idTipoMonitoraggio;
	private String codTipoMonitoraggio;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private Date dataInizioRestituzione;
	private Date dataFineRestituzione;

	public Long getIdMisurazione() {
		return idMisurazione;
	}

	public void setIdMisurazione(Long idMisurazione) {
		this.idMisurazione = idMisurazione;
	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	public Long getIdDocMisurazione() {
		return idDocMisurazione;
	}

	public void setIdDocMisurazione(Long idDocMisurazione) {
		this.idDocMisurazione = idDocMisurazione;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	public Long getIdCampagna() {
		return idCampagna;
	}

	public void setIdCampagna(Long idCampagna) {
		this.idCampagna = idCampagna;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getCodTipoMonitoraggio() {
		return codTipoMonitoraggio;
	}

	public void setCodTipoMonitoraggio(String codTipoMonitoraggio) {
		this.codTipoMonitoraggio = codTipoMonitoraggio;
	}

	public Long getIdTipoMonitoraggio() {
		return idTipoMonitoraggio;
	}

	public void setIdTipoMonitoraggio(Long idTipoMonitoraggio) {
		this.idTipoMonitoraggio = idTipoMonitoraggio;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataInizioRestituzione() {
		return dataInizioRestituzione;
	}

	public void setDataInizioRestituzione(Date dataInizioRestituzione) {
		this.dataInizioRestituzione = dataInizioRestituzione;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Date getDataFineRestituzione() {
		return dataFineRestituzione;
	}

	public void setDataFineRestituzione(Date dataFineRestituzione) {
		this.dataFineRestituzione = dataFineRestituzione;
	}

}
