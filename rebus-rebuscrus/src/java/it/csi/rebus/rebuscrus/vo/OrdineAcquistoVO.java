/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class OrdineAcquistoVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cig;
	private String cup;
	private String cupMaster;
	private Date dataAggiornamento;
	private Date dataAggiudicazione;
	private Date dataOrdine;
	private Date dataStipula;
	private String fornitore;
	private Long idOrdineAcquisto;
	private Long idUtenteAggiornamento;
	private String numeroOrdine;


	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCupMaster() {
		return cupMaster;
	}

	public void setCupMaster(String cupMaster) {
		this.cupMaster = cupMaster;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Date getDataAggiudicazione() {
		return dataAggiudicazione;
	}

	public void setDataAggiudicazione(Date dataAggiudicazione) {
		this.dataAggiudicazione = dataAggiudicazione;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public String getFornitore() {
		return fornitore;
	}

	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}

	public Long getIdOrdineAcquisto() {
		return idOrdineAcquisto;
	}

	public void setIdOrdineAcquisto(Long idOrdineAcquisto) {
		this.idOrdineAcquisto = idOrdineAcquisto;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}
}
