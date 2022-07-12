/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class DatoSpesaVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDatoSpesa;
	private Double contributoErogabileReg;
	private Double contributoRegionaleFf;

	private String nrAttoLiquidazioneRpAmpAnt;
	private Date dataAttoLiquidazioneRpAmpAnt;
	private String nrDeterminaRpAmpAnt;
	private Date dataDeterminaRpAmpAnt;
	private String nrAttoLiquidazioneAmpAzAnt;
	private Date dataAttoLiquidazioneAmpAzAnt;
	private String nrDeterminaAmpAzAnt;
	private Date dataDeterminaAmpAzAnt;

	private String nrAttoLiquidazioneRpAmpSal;
	private Date dataAttoLiquidazioneRpAmpSal;
	private String nrDeterminaRpAmpSal;
	private Date dataDeterminaRpAmpSal;
	private String nrAttoLiquidazioneAmpAzSal;
	private Date dataAttoLiquidazioneAmpAzSal;
	private String nrDeterminaAmpAzSal;
	private Date dataDeterminaAmpAzSal;

	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;
	private Double importoAnticipo;
	private Double importoSaldo;

	public Long getIdDatoSpesa() {
		return idDatoSpesa;
	}

	public void setIdDatoSpesa(Long idDatoSpesa) {
		this.idDatoSpesa = idDatoSpesa;
	}

	public Double getContributoErogabileReg() {
		return contributoErogabileReg;
	}

	public void setContributoErogabileReg(Double contributoErogabileReg) {
		this.contributoErogabileReg = contributoErogabileReg;
	}

	public Double getContributoRegionaleFf() {
		return contributoRegionaleFf;
	}

	public void setContributoRegionaleFf(Double contributoRegionaleFf) {
		this.contributoRegionaleFf = contributoRegionaleFf;
	}

	public String getNrAttoLiquidazioneRpAmpAnt() {
		return nrAttoLiquidazioneRpAmpAnt;
	}

	public void setNrAttoLiquidazioneRpAmpAnt(String nrAttoLiquidazioneRpAmpAnt) {
		this.nrAttoLiquidazioneRpAmpAnt = nrAttoLiquidazioneRpAmpAnt;
	}

	public Date getDataAttoLiquidazioneRpAmpAnt() {
		return dataAttoLiquidazioneRpAmpAnt;
	}

	public void setDataAttoLiquidazioneRpAmpAnt(Date dataAttoLiquidazioneRpAmpAnt) {
		this.dataAttoLiquidazioneRpAmpAnt = dataAttoLiquidazioneRpAmpAnt;
	}

	public String getNrDeterminaRpAmpAnt() {
		return nrDeterminaRpAmpAnt;
	}

	public void setNrDeterminaRpAmpAnt(String nrDeterminaRpAmpAnt) {
		this.nrDeterminaRpAmpAnt = nrDeterminaRpAmpAnt;
	}

	public Date getDataDeterminaRpAmpAnt() {
		return dataDeterminaRpAmpAnt;
	}

	public void setDataDeterminaRpAmpAnt(Date dataDeterminaRpAmpAnt) {
		this.dataDeterminaRpAmpAnt = dataDeterminaRpAmpAnt;
	}

	public String getNrAttoLiquidazioneAmpAzAnt() {
		return nrAttoLiquidazioneAmpAzAnt;
	}

	public void setNrAttoLiquidazioneAmpAzAnt(String nrAttoLiquidazioneAmpAzAnt) {
		this.nrAttoLiquidazioneAmpAzAnt = nrAttoLiquidazioneAmpAzAnt;
	}

	public Date getDataAttoLiquidazioneAmpAzAnt() {
		return dataAttoLiquidazioneAmpAzAnt;
	}

	public void setDataAttoLiquidazioneAmpAzAnt(Date dataAttoLiquidazioneAmpAzAnt) {
		this.dataAttoLiquidazioneAmpAzAnt = dataAttoLiquidazioneAmpAzAnt;
	}

	public String getNrDeterminaAmpAzAnt() {
		return nrDeterminaAmpAzAnt;
	}

	public void setNrDeterminaAmpAzAnt(String nrDeterminaAmpAzAnt) {
		this.nrDeterminaAmpAzAnt = nrDeterminaAmpAzAnt;
	}

	public Date getDataDeterminaAmpAzAnt() {
		return dataDeterminaAmpAzAnt;
	}

	public void setDataDeterminaAmpAzAnt(Date dataDeterminaAmpAzAnt) {
		this.dataDeterminaAmpAzAnt = dataDeterminaAmpAzAnt;
	}

	public String getNrAttoLiquidazioneRpAmpSal() {
		return nrAttoLiquidazioneRpAmpSal;
	}

	public void setNrAttoLiquidazioneRpAmpSal(String nrAttoLiquidazioneRpAmpSal) {
		this.nrAttoLiquidazioneRpAmpSal = nrAttoLiquidazioneRpAmpSal;
	}

	public Date getDataAttoLiquidazioneRpAmpSal() {
		return dataAttoLiquidazioneRpAmpSal;
	}

	public void setDataAttoLiquidazioneRpAmpSal(Date dataAttoLiquidazioneRpAmpSal) {
		this.dataAttoLiquidazioneRpAmpSal = dataAttoLiquidazioneRpAmpSal;
	}

	public String getNrDeterminaRpAmpSal() {
		return nrDeterminaRpAmpSal;
	}

	public void setNrDeterminaRpAmpSal(String nrDeterminaRpAmpSal) {
		this.nrDeterminaRpAmpSal = nrDeterminaRpAmpSal;
	}

	public Date getDataDeterminaRpAmpSal() {
		return dataDeterminaRpAmpSal;
	}

	public void setDataDeterminaRpAmpSal(Date dataDeterminaRpAmpSal) {
		this.dataDeterminaRpAmpSal = dataDeterminaRpAmpSal;
	}

	public String getNrAttoLiquidazioneAmpAzSal() {
		return nrAttoLiquidazioneAmpAzSal;
	}

	public void setNrAttoLiquidazioneAmpAzSal(String nrAttoLiquidazioneAmpAzSal) {
		this.nrAttoLiquidazioneAmpAzSal = nrAttoLiquidazioneAmpAzSal;
	}

	public Date getDataAttoLiquidazioneAmpAzSal() {
		return dataAttoLiquidazioneAmpAzSal;
	}

	public void setDataAttoLiquidazioneAmpAzSal(Date dataAttoLiquidazioneAmpAzSal) {
		this.dataAttoLiquidazioneAmpAzSal = dataAttoLiquidazioneAmpAzSal;
	}

	public String getNrDeterminaAmpAzSal() {
		return nrDeterminaAmpAzSal;
	}

	public void setNrDeterminaAmpAzSal(String nrDeterminaAmpAzSal) {
		this.nrDeterminaAmpAzSal = nrDeterminaAmpAzSal;
	}

	public Date getDataDeterminaAmpAzSal() {
		return dataDeterminaAmpAzSal;
	}

	public void setDataDeterminaAmpAzSal(Date dataDeterminaAmpAzSal) {
		this.dataDeterminaAmpAzSal = dataDeterminaAmpAzSal;
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

	public Double getImportoAnticipo() {
		return importoAnticipo;
	}

	public void setImportoAnticipo(Double importoAnticipo) {
		this.importoAnticipo = importoAnticipo;
	}

	public Double getImportoSaldo() {
		return importoSaldo;
	}

	public void setImportoSaldo(Double importoSaldo) {
		this.importoSaldo = importoSaldo;
	}

}
