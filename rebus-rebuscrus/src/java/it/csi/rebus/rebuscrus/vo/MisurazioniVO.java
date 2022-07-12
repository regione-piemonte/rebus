/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class MisurazioniVO extends ParentVO implements Comparable<MisurazioniVO> {
	private Long idMisurazioni;
	private String primoTelaio;
	private Date dataInizio;
	private Date dataFine;
	private Date dataAggiornamento;
	private Long idUtenteAggiornamento;
	private Long idDocMisurazione;

	private Long idCampagna;
	private String descrizione;
	private Long idTipoMonitoraggio;
	private String codTipoMonitoraggio;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private Date dataInizioRestituzione;
	private Date dataFineRestituzione;
	
	private List<PortabiciVO> bici;
	private List<EmissioniVO> emissioni;

	public Long getIdMisurazioni() {
		return idMisurazioni;
	}

	public void setIdMisurazioni(Long idMisurazioni) {
		this.idMisurazioni = idMisurazioni;
	}
	
	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	@Override
	public int compareTo(MisurazioniVO o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}


	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	public Long getIdDocMisurazione() {
		return idDocMisurazione;
	}

	public void setIdDocMisurazione(Long idDocMisurazione) {
		this.idDocMisurazione = idDocMisurazione;
	}

	public Long getIdCampagna() {
		return idCampagna;
	}

	public void setIdCampagna(Long idCampagna) {
		this.idCampagna = idCampagna;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	public String getCodTipoMonitoraggio() {
		return codTipoMonitoraggio;
	}

	public void setCodTipoMonitoraggio(String codTipoMonitoraggio) {
		this.codTipoMonitoraggio = codTipoMonitoraggio;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Date getDataFineRestituzione() {
		return dataFineRestituzione;
	}

	public void setDataFineRestituzione(Date dataFineRestituzione) {
		this.dataFineRestituzione = dataFineRestituzione;
	}

	public Date getDataInizioRestituzione() {
		return dataInizioRestituzione;
	}

	public void setDataInizioRestituzione(Date dataInizioRestituzione) {
		this.dataInizioRestituzione = dataInizioRestituzione;
	}

	public List<PortabiciVO> getBici() {
		return bici;
	}

	public void setBici(List<PortabiciVO> bici) {
		this.bici = bici;
	}

	public List<EmissioniVO> getEmissioni() {
		return emissioni;
	}

	public void setEmissioni(List<EmissioniVO> emissioni) {
		this.emissioni = emissioni;
	}




	


	

	
}
