/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

public class ProcedimentiDTO {

	private Long idVa;
	private String numProc;
	private String tipoProc;
	private String statoProc;
	private String dataStato;
	private String contratto;
	private String enteComm;
	private String esecTit;
	private String richiesta;
	private String autorizzazione;
	private Long idProc;
	private Long idTipoProc;

	public Long getIdVa() {
		return idVa;
	}

	public void setIdVa(Long idVa) {
		this.idVa = idVa;
	}

	public String getNumProc() {
		return numProc;
	}

	public void setNumProc(String numProc) {
		this.numProc = numProc;
	}

	public String getTipoProc() {
		return tipoProc;
	}

	public void setTipoProc(String tipoProc) {
		this.tipoProc = tipoProc;
	}

	public String getStatoProc() {
		return statoProc;
	}

	public void setStatoProc(String statoProc) {
		this.statoProc = statoProc;
	}

	public String getAutorizzazione() {
		return autorizzazione;
	}

	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}

	public Long getIdProc() {
		return idProc;
	}

	public void setIdProc(Long idProc) {
		this.idProc = idProc;
	}

	public String getDataStato() {
		return dataStato;
	}

	public void setDataStato(String dataStato) {
		this.dataStato = dataStato;
	}

	public String getContratto() {
		return contratto;
	}

	public void setContratto(String contratto) {
		this.contratto = contratto;
	}

	public String getEnteComm() {
		return enteComm;
	}

	public void setEnteComm(String enteComm) {
		this.enteComm = enteComm;
	}

	public String getEsecTit() {
		return esecTit;
	}

	public void setEsecTit(String esecTit) {
		this.esecTit = esecTit;
	}

	public String getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}

	public Long getIdTipoProc() {
		return idTipoProc;
	}

	public void setIdTipoProc(Long idTipoProc) {
		this.idTipoProc = idTipoProc;
	}

}
