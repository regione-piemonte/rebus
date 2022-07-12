/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.sql.Date;

public class FiltroAutobusVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Long idAzienda;
	private String denominazioneAzienda;
	private String targa;
	private String matricola;
	private String primoTelaio;
	private String codiceRichiesta;
	private Long tipoAlimentazione;
	private Date dataPrimaImmatricolazioneDa;
	private Date dataPrimaImmatricolazioneA;

	private Date situazioneAl;
	private String flagIncludiVariazioniPre;

	/*
	 * filtro avanzate
	 */
	private String autobus;
	private String autovetture;
	private String attivo;
	private String ritirato;
	private String alienatoAzienda;
	private String alienatoNoAzienda;
	private String includiPropPrec;
	private String daImmatricolare;
	private String verificatoAziende;
	private String verificatoAMP;
	private String bozza;
	private String richieste;
	private String rendicontazione;

	public FiltroAutobusVO() {
	}

	public FiltroAutobusVO(String primoTelaio, String targa, String matricola, String denominazioneAzienda,
			Long tipoAlimentazione, Date dataPrimaImmatricolazioneDa, Date dataPrimaImmatricolazioneA,
			Date situazioneAl, String flagIncludiVariazioniPre, String codiceRichiesta, String autobus,
			String autovetture, String attivo, String ritirato, String includiPropPrec, String daImmatricolare,
			String verificatoAziende, String verificatoAMP, String bozza, String richieste, String rendicontazione,
			String alienatoAzienda, String alienatoNoAzienda) {
		super();
		this.primoTelaio = (primoTelaio != null && primoTelaio.trim().length() > 0) ? primoTelaio.trim() : null;
		this.targa = (targa != null && targa.trim().length() > 0) ? targa.trim() : null;
		this.matricola = (matricola != null && matricola.trim().length() > 0) ? matricola.trim() : null;
		this.denominazioneAzienda = (denominazioneAzienda != null && denominazioneAzienda.trim().length() > 0)
				? denominazioneAzienda.trim()
				: null;
		this.tipoAlimentazione = tipoAlimentazione;
		this.dataPrimaImmatricolazioneDa = dataPrimaImmatricolazioneDa;
		this.dataPrimaImmatricolazioneA = dataPrimaImmatricolazioneA;
		this.situazioneAl = situazioneAl;
		this.flagIncludiVariazioniPre = flagIncludiVariazioniPre;
		this.codiceRichiesta = (codiceRichiesta != null && codiceRichiesta.trim().length() > 0) ? codiceRichiesta.trim()
				: null;
		this.autovetture = autovetture;
		this.autobus = autobus;
		this.attivo = attivo;
		this.ritirato = ritirato;

		this.alienatoAzienda = alienatoAzienda;
		this.alienatoNoAzienda = alienatoNoAzienda;
		this.includiPropPrec = includiPropPrec;
		this.daImmatricolare = daImmatricolare;
		this.verificatoAziende = verificatoAziende;
		this.verificatoAMP = verificatoAMP;
		this.bozza = bozza;
		this.richieste = richieste;
		this.rendicontazione = rendicontazione;

	}

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = (targa != null && targa.trim().length() > 0) ? targa.trim() : null;
	}
	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = (matricola != null && matricola.trim().length() > 0) ? matricola : null;
	}

	public String getDenominazioneAzienda() {
		return denominazioneAzienda;
	}

	public void setDenominazioneAzienda(String denominazioneAzienda) {
		this.denominazioneAzienda = (denominazioneAzienda != null && denominazioneAzienda.trim().length() > 0)
				? denominazioneAzienda.trim()
				: null;
	}
	public void prepareSQL() {

		if (this.primoTelaio != null && this.primoTelaio.trim().length() > 0) {
			this.primoTelaio = "%" + this.primoTelaio.trim() + "%";
		}
		;
		if (this.targa != null && this.targa.trim().length() > 0) {
			this.targa = "%" + this.targa.trim() + "%";
		}
		;
		if (this.matricola != null && this.matricola.trim().length() > 0) {
			this.matricola = "%" + this.matricola.trim() + "%";
		}
		;
		if (this.denominazioneAzienda != null && this.denominazioneAzienda.trim().length() > 0) {
			this.denominazioneAzienda = "%" + this.denominazioneAzienda.trim() + "%";
		}
		if (this.codiceRichiesta != null && this.codiceRichiesta.trim().length() > 0) {
			this.codiceRichiesta = "%" + this.codiceRichiesta.trim() + "%";
		}
		;
	}

	public Long getTipoAlimentazione() {
		return tipoAlimentazione;
	}

	public void setTipoAlimentazione(Long tipoAlimentazione) {
		this.tipoAlimentazione = tipoAlimentazione;
	}

	public Date getDataPrimaImmatricolazioneDa() {
		return dataPrimaImmatricolazioneDa;
	}

	public void setDataPrimaImmatricolazioneDa(Date dataPrimaImmatricolazioneDa) {
		this.dataPrimaImmatricolazioneDa = dataPrimaImmatricolazioneDa;
	}

	public Date getDataPrimaImmatricolazioneA() {
		return dataPrimaImmatricolazioneA;
	}

	public void setDataPrimaImmatricolazioneA(Date dataPrimaImmatricolazioneA) {
		this.dataPrimaImmatricolazioneA = dataPrimaImmatricolazioneA;
	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = (primoTelaio != null && primoTelaio.trim().length() > 0) ? primoTelaio : null;

	}

	public Date getSituazioneAl() {
		return situazioneAl;
	}

	public void setSituazioneAl(Date situazioneAl) {
		this.situazioneAl = situazioneAl;
	}

	public String getFlagIncludiVariazioniPre() {
		return flagIncludiVariazioniPre;
	}

	public void setFlagIncludiVariazioniPre(String flagIncludiVariazioniPre) {
		this.flagIncludiVariazioniPre = flagIncludiVariazioniPre;
	}

	public String getAutobus() {
		return autobus;
	}

	public void setAutobus(String autobus) {
		this.autobus = autobus;
	}

	public String getAutovetture() {
		return autovetture;
	}

	public void setAutovetture(String autovetture) {
		this.autovetture = autovetture;
	}

	public String getAttivo() {
		return attivo;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}

	public String getRitirato() {
		return ritirato;
	}

	public void setRitirato(String ritirato) {
		this.ritirato = ritirato;
	}

	public String getIncludiPropPrec() {
		return includiPropPrec;
	}

	public void setIncludiPropPrec(String includiPropPrec) {
		this.includiPropPrec = includiPropPrec;
	}

	public String getDaImmatricolare() {
		return daImmatricolare;
	}

	public void setDaImmatricolare(String daImmatricolare) {
		this.daImmatricolare = daImmatricolare;
	}

	public String getVerificatoAziende() {
		return verificatoAziende;
	}

	public void setVerificatoAziende(String verificatoAziende) {
		this.verificatoAziende = verificatoAziende;
	}

	public String getVerificatoAMP() {
		return verificatoAMP;
	}

	public void setVerificatoAMP(String verificatoAMP) {
		this.verificatoAMP = verificatoAMP;
	}

	public String getBozza() {
		return bozza;
	}

	public void setBozza(String bozza) {
		this.bozza = bozza;
	}

	public String getRendicontazione() {
		return rendicontazione;
	}

	public void setRendicontazione(String rendicontazione) {
		this.rendicontazione = rendicontazione;
	}

	public String getRichieste() {
		return richieste;
	}

	public void setRichieste(String richieste) {
		this.richieste = richieste;
	}

	public String getAlienatoAzienda() {
		return alienatoAzienda;
	}

	public void setAlienatoAzienda(String alienatoAzienda) {
		this.alienatoAzienda = alienatoAzienda;
	}

	public String getAlienatoNoAzienda() {
		return alienatoNoAzienda;
	}

	public void setAlienatoNoAzienda(String alienatoNoAzienda) {
		this.alienatoNoAzienda = alienatoNoAzienda;
	}

	public String getCodiceRichiesta() {
		return codiceRichiesta;
	}

	public void setCodiceRichiesta(String codiceRichiesta) {
		this.codiceRichiesta = (codiceRichiesta != null && codiceRichiesta.trim().length() > 0) ? codiceRichiesta
				: null;

	}

}
