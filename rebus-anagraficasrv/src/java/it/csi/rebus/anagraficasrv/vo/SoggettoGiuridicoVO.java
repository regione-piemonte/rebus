/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class SoggettoGiuridicoVO extends ParentVO {
	private Long id;
	// DATI IDENTIFICATIVI
	private Long idTipoSoggettoGiuridico;
	private Long idRuoloTipoSoggettoGiuridico;
	private String denomBreve;
	private String denomAAEP;
	private String denominazioneRicerca;
	private String denomOsservatorioNaz;
	private String codOsservatorioNaz;
	private String codRegionale;
	private Long idNaturaGiuridica;
	private String nomeRappresentanteLegale;
	private String cognomeRappresentanteLegale;
	private String partitaIva;
	private String codiceFiscale;
	private Date dataInizio;
	private Date dataCessazione;
	private Boolean aziendaAttiva;
	private Boolean aziendaCessata;
	private String note;
	private Long codCsrBip;

	private Long idTipoEnte;
	private String descrizione;
	private byte[] logo;
	private Boolean isLogoUploaded;

	// SEDE LEGALE E CONTATTI
	private UbicazioneVO ubicazioneSede;
	private String telefonoSede;
	private String fax;
	private String email;
	private String pec;
	private String indirizzoWeb;
	private String numeroVerde;

	private List<DepositoVO> depositi;

	private List<DatiBancariVO> datiBancari;

	private Date dataAggiornamento;
	
	private List<ContrattoSoggettoVO> contrattiSoggetti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipoSoggettoGiuridico() {
		return idTipoSoggettoGiuridico;
	}

	public void setIdTipoSoggettoGiuridico(Long idTipoSoggettoGiuridico) {
		this.idTipoSoggettoGiuridico = idTipoSoggettoGiuridico;
	}

	public String getDenomBreve() {
		return denomBreve;
	}

	public void setDenomBreve(String denomBreve) {
		this.denomBreve = denomBreve;
	}
	public String getDenominazioneRicerca() {
		return denominazioneRicerca;
	}

	public void setDenominazioneRicerca(String denominazioneRicerca) {
		this.denominazioneRicerca = denominazioneRicerca;
	}
	public String getDenomAAEP() {
		return denomAAEP;
	}

	public void setDenomAAEP(String denomAAEP) {
		this.denomAAEP = denomAAEP;
	}

	public String getDenomOsservatorioNaz() {
		return denomOsservatorioNaz;
	}

	public void setDenomOsservatorioNaz(String denomOsservatorioNaz) {
		this.denomOsservatorioNaz = denomOsservatorioNaz;
	}

	public String getCodOsservatorioNaz() {
		return codOsservatorioNaz;
	}

	public void setCodOsservatorioNaz(String codOsservatorioNaz) {
		this.codOsservatorioNaz = codOsservatorioNaz;
	}

	public String getCodRegionale() {
		return codRegionale;
	}

	public void setCodRegionale(String codRegionale) {
		this.codRegionale = codRegionale;
	}

	
	  public Long getIdNaturaGiuridica() { return idNaturaGiuridica; }
	  
	  public void setIdNaturaGiuridica(Long idNaturaGiuridica) {
	  this.idNaturaGiuridica = idNaturaGiuridica; }
	 

	public String getNomeRappresentanteLegale() {
		return nomeRappresentanteLegale;
	}

	public void setNomeRappresentanteLegale(String nomeRappresentanteLegale) {
		this.nomeRappresentanteLegale = nomeRappresentanteLegale;
	}

	public String getCognomeRappresentanteLegale() {
		return cognomeRappresentanteLegale;
	}

	public void setCognomeRappresentanteLegale(String cognomeRappresentanteLegale) {
		this.cognomeRappresentanteLegale = cognomeRappresentanteLegale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Boolean getAziendaAttiva() {
		return aziendaAttiva;
	}

	public void setAziendaAttiva(Boolean aziendaAttiva) {
		this.aziendaAttiva = aziendaAttiva;
	}

	public Boolean getAziendaCessata() {
		return aziendaCessata;
	}

	public void setAziendaCessata(Boolean aziendaCessata) {
		this.aziendaCessata = aziendaCessata;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdTipoEnte() {
		return idTipoEnte;
	}

	public void setIdTipoEnte(Long idTipoEnte) {
		this.idTipoEnte = idTipoEnte;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public Boolean getIsLogoUploaded() {
		return isLogoUploaded;
	}

	public void setIsLogoUploaded(Boolean isLogoUploaded) {
		this.isLogoUploaded = isLogoUploaded;
	}

	public UbicazioneVO getUbicazioneSede() {
		return ubicazioneSede;
	}

	public void setUbicazioneSede(UbicazioneVO ubicazioneSede) {
		this.ubicazioneSede = ubicazioneSede;
	}

	public String getTelefonoSede() {
		return telefonoSede;
	}

	public void setTelefonoSede(String telefonoSede) {
		this.telefonoSede = telefonoSede;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getIndirizzoWeb() {
		return indirizzoWeb;
	}

	public void setIndirizzoWeb(String indirizzoWeb) {
		this.indirizzoWeb = indirizzoWeb;
	}

	public String getNumeroVerde() {
		return numeroVerde;
	}

	public void setNumeroVerde(String numeroVerde) {
		this.numeroVerde = numeroVerde;
	}

	public List<DepositoVO> getDepositi() {
		return depositi;
	}

	public void setDepositi(List<DepositoVO> depositi) {
		this.depositi = depositi;
	}

	public List<DatiBancariVO> getDatiBancari() {
		return datiBancari;
	}

	public void setDatiBancari(List<DatiBancariVO> datiBancari) {
		this.datiBancari = datiBancari;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public List<ContrattoSoggettoVO> getContrattiSoggetti() {
		return contrattiSoggetti;
	}

	public void setContrattiSoggetti(List<ContrattoSoggettoVO> contrattiSoggetti) {
		this.contrattiSoggetti = contrattiSoggetti;
	}

	public Long getIdRuoloTipoSoggettoGiuridico() {
		return idRuoloTipoSoggettoGiuridico;
	}

	public void setIdRuoloTipoSoggettoGiuridico(Long idRuoloTipoSoggettoGiuridico) {
		this.idRuoloTipoSoggettoGiuridico = idRuoloTipoSoggettoGiuridico;
	}
	
	public Long getCodCsrBip() {
		return codCsrBip;
	}

	public void setCodCsrBip(Long codCsrBip) {
		this.codCsrBip = codCsrBip;
		
	}
	

}
