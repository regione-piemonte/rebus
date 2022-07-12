/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class FiltroContribuzioneVO extends ParentVO {

	private Long idEnte;
	private Long idStatoIter;
	private String isAMP;
	private String isRegionePiemonte;
	private String isServizio;
	private String flagStatoCorrente;

	private String primoTelaio;
	private Long idFonteFinanziamento; // filtro
	private String fonteFinanziamento;
	private Long idTipoAlimentazione;
	private String tipoAlimentazione;
	private Long idTipoAllestimento; // filtro
	private String tipoAllestimento;
	private String flgVerificatoAzienda;
	private String flgVerificatoAmp;
	private Long idProcedimento;

	private String flgRendicontazione;
	private String targa; // filtro

	private Long idAzienda; // filtro
	private String azienda;
	
	private String aziendaRicerca;

	private String trasmessaA;
	private String trasmessaB;
	private String validitaA;
	private String validitaB;

	public FiltroContribuzioneVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	// controllo cambio in Long
	public FiltroContribuzioneVO(String primoTelaio, Long idFonteFinanziamento, Long idTipoAlimentazione,
			Long idAzienda, String targa) {
		super();

		this.primoTelaio = (primoTelaio != null && primoTelaio.trim().length() > 0) ? primoTelaio.trim() : null;
		this.idFonteFinanziamento = idFonteFinanziamento;
		this.idTipoAlimentazione = idTipoAlimentazione;
		this.targa = (targa != null && targa.trim().length() > 0) ? targa.trim() : null;
		this.azienda = (azienda != null && azienda.trim().length() > 0) ? azienda.trim() : null;
		
	}

	public void prepareSQL() {
		if (this.targa != null && this.targa.trim().length() > 0) {
			this.targa = "%" + this.targa.trim() + "%";
		}
		if (this.primoTelaio != null && this.primoTelaio.trim().length() > 0) {
			this.primoTelaio = "%" + this.primoTelaio.trim() + "%";
		}
		if (this.azienda != null && this.azienda.trim().length() > 0) {
			this.azienda = "%" + this.azienda.trim() + "%";
		}

	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = (primoTelaio != null && primoTelaio.trim().length() > 0) ? primoTelaio : null;

	}

	public Long getIdFonteFinanziamento() {
		return idFonteFinanziamento;
	}

	public void setIdFonteFinanziamento(Long idFonteFinanziamento) {
		this.idFonteFinanziamento = idFonteFinanziamento;
	}

	public String getFonteFinanziamento() {
		return fonteFinanziamento;
	}

	public void setFonteFinanziamento(String fonteFinanziamento) {
		this.fonteFinanziamento = fonteFinanziamento;
	}

	public Long getIdTipoAlimentazione() {
		return idTipoAlimentazione;
	}

	public void setIdTipoAlimentazione(Long idTipoAlimentazione) {
		this.idTipoAlimentazione = idTipoAlimentazione;
	}

	public String getTipoAlimentazione() {
		return tipoAlimentazione;
	}

	public void setTipoAlimentazione(String tipoAlimentazione) {
		this.tipoAlimentazione = tipoAlimentazione;
	}

	public Long getIdTipoAllestimento() {
		return idTipoAllestimento;
	}

	public void setIdTipoAllestimento(Long idTipoAllestimento) {
		this.idTipoAllestimento = idTipoAllestimento;
	}

	public String getTipoAllestimento() {
		return tipoAllestimento;
	}

	public void setTipoAllestimento(String tipoAllestimento) {
		this.tipoAllestimento = tipoAllestimento;
	}

	public String getFlgVerificatoAzienda() {
		return flgVerificatoAzienda;
	}

	public void setFlgVerificatoAzienda(String flgVerificatoAzienda) {
		this.flgVerificatoAzienda = flgVerificatoAzienda;
	}

	public String getFlgVerificatoAmp() {
		return flgVerificatoAmp;
	}

	public void setFlgVerificatoAmp(String flgVerificatoAmp) {
		this.flgVerificatoAmp = flgVerificatoAmp;
	}

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public String getFlgRendicontazione() {
		return flgRendicontazione;
	}

	public void setFlgRendicontazione(String flgRendicontazione) {
		this.flgRendicontazione = flgRendicontazione;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getFlagStatoCorrente() {
		return flagStatoCorrente;
	}

	public void setFlagStatoCorrente(String flagStatoCorrente) {
		this.flagStatoCorrente = flagStatoCorrente;
	}

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public Long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}

	public Long getIdStatoIter() {
		return idStatoIter;
	}

	public void setIdStatoIter(Long idStatoIter) {
		this.idStatoIter = idStatoIter;
	}

	public String getIsAMP() {
		return isAMP;
	}

	public void setIsAMP(String isAMP) {
		this.isAMP = isAMP;
	}

	public String getIsRegionePiemonte() {
		return isRegionePiemonte;
	}

	public void setIsRegionePiemonte(String isRegionePiemonte) {
		this.isRegionePiemonte = isRegionePiemonte;
	}

	public String getIsServizio() {
		return isServizio;
	}

	public void setIsServizio(String isServizio) {
		this.isServizio = isServizio;
	}



	public String getValiditaA() {
		return validitaA;
	}

	public void setValiditaA(String validitaA) {
		this.validitaA = validitaA;
	}

	public String getValiditaB() {
		return validitaB;
	}

	public void setValiditaB(String validitaB) {
		this.validitaB = validitaB;
	}

	public String getAzienda() {
		return azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}

	public String getAziendaRicerca() {
		return aziendaRicerca;
	}

	public void setAziendaRicerca(String aziendaRicerca) {
		this.aziendaRicerca = (aziendaRicerca != null && aziendaRicerca.trim().length() > 0) ? aziendaRicerca : null;

		
	}

	public String getTrasmessaA() {
		return trasmessaA;
	}

	public void setTrasmessaA(String trasmessaA) {
		this.trasmessaA = trasmessaA;
	}

	public String getTrasmessaB() {
		return trasmessaB;
	}

	public void setTrasmessaB(String trasmessaB) {
		this.trasmessaB = trasmessaB;
	}

}
