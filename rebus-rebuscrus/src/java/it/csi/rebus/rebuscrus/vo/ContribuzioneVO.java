/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class ContribuzioneVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idContribuzione;
	private Long idAssegnazioneRisorse;
	private Long idOrdineAcquisto;
	private Long idCostoFornitura;
	private Long idDatoSpesa;
	private Long idDatoMessaServizio;
	private Long idProcedimento;
	private String noteAzienda;
	private String noteRpAmp;
	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;

	public Long getIdContribuzione() {
		return idContribuzione;
	}

	public void setIdContribuzione(Long idContribuzione) {
		this.idContribuzione = idContribuzione;
	}

	public Long getIdAssegnazioneRisorse() {
		return idAssegnazioneRisorse;
	}

	public void setIdAssegnazioneRisorse(Long idAssegnazioneRisorse) {
		this.idAssegnazioneRisorse = idAssegnazioneRisorse;
	}

	public Long getIdOrdineAcquisto() {
		return idOrdineAcquisto;
	}

	public void setIdOrdineAcquisto(Long idOrdineAcquisto) {
		this.idOrdineAcquisto = idOrdineAcquisto;
	}

	public Long getIdCostoFornitura() {
		return idCostoFornitura;
	}

	public void setIdCostoFornitura(Long idCostoFornitura) {
		this.idCostoFornitura = idCostoFornitura;
	}

	public Long getIdDatoSpesa() {
		return idDatoSpesa;
	}

	public void setIdDatoSpesa(Long idDatoSpesa) {
		this.idDatoSpesa = idDatoSpesa;
	}

	public Long getIdDatoMessaServizio() {
		return idDatoMessaServizio;
	}

	public void setIdDatoMessaServizio(Long idDatoMessaServizio) {
		this.idDatoMessaServizio = idDatoMessaServizio;
	}

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	
	public String getNoteAzienda() {
		return noteAzienda;
	}

	public void setNoteAzienda(String noteAzienda) {
		this.noteAzienda = noteAzienda;
	}

	public String getNoteRpAmp() {
		return noteRpAmp;
	}

	public void setNoteRpAmp(String noteRpAmp) {
		this.noteRpAmp = noteRpAmp;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

}
