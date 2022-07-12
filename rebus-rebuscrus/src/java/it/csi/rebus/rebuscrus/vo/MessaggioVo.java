/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class MessaggioVo extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1258360408460915953L;

	private Long idMessaggio;

	private Long fkTipoMessaggio;

	private Long fkUtenteMittente;

	private Long fkUtenteDestinatario;

	private Long fkVariazAutobus;

	private Long fkStoriaVariazAutobus;

	private Long fkStoriaVariazAutobusSucc;

	private String messaggio;

	private Date dataCreazione;

	private String flgLetto;

	private Date dataLettura;

	private String flgArchiviato;

	private Date dataArchiviazione;

	private String targa;

	private String azienda;

	private String descTipoMessaggio;

	private AutobusVO variazioneAutobus;

	private AutobusVO variazioneStoricoAutobus;

	private Long fkProcedimento;

	private String note;
	
	private Long fkTipoMessaggioSistema;

	private String testoMessaggioSistema;

	public Long getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(Long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public Long getFkTipoMessaggio() {
		return fkTipoMessaggio;
	}

	public void setFkTipoMessaggio(Long fkTipoMessaggio) {
		this.fkTipoMessaggio = fkTipoMessaggio;
	}

	public Long getFkUtenteMittente() {
		return fkUtenteMittente;
	}

	public void setFkUtenteMittente(Long fkUtenteMittente) {
		this.fkUtenteMittente = fkUtenteMittente;
	}

	public Long getFkUtenteDestinatario() {
		return fkUtenteDestinatario;
	}

	public void setFkUtenteDestinatario(Long fkUtenteDestinatario) {
		this.fkUtenteDestinatario = fkUtenteDestinatario;
	}

	public Long getFkVariazAutobus() {
		return fkVariazAutobus;
	}

	public void setFkVariazAutobus(Long fkVariazAutobus) {
		this.fkVariazAutobus = fkVariazAutobus;
	}

	public Long getFkStoriaVariazAutobus() {
		return fkStoriaVariazAutobus;
	}

	public void setFkStoriaVariazAutobus(Long fkStoriaVariazAutobus) {
		this.fkStoriaVariazAutobus = fkStoriaVariazAutobus;
	}

	public Long getFkStoriaVariazAutobusSucc() {
		return fkStoriaVariazAutobusSucc;
	}

	public void setFkStoriaVariazAutobusSucc(Long fkStoriaVariazAutobusSucc) {
		this.fkStoriaVariazAutobusSucc = fkStoriaVariazAutobusSucc;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getFlgLetto() {
		return flgLetto;
	}

	public void setFlgLetto(String flgLetto) {
		this.flgLetto = flgLetto;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public String getFlgArchiviato() {
		return flgArchiviato;
	}

	public void setFlgArchiviato(String flgArchiviato) {
		this.flgArchiviato = flgArchiviato;
	}

	public Date getDataArchiviazione() {
		return dataArchiviazione;
	}

	public void setDataArchiviazione(Date dataArchiviazione) {
		this.dataArchiviazione = dataArchiviazione;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getAzienda() {
		return azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}

	public String getDescTipoMessaggio() {
		return descTipoMessaggio;
	}

	public void setDescTipoMessaggio(String descTipoMessaggio) {
		this.descTipoMessaggio = descTipoMessaggio;
	}

	public AutobusVO getVariazioneAutobus() {
		return variazioneAutobus;
	}

	public void setVariazioneAutobus(AutobusVO variazioneAutobus) {
		this.variazioneAutobus = variazioneAutobus;
	}

	public AutobusVO getVariazioneStoricoAutobus() {
		return variazioneStoricoAutobus;
	}

	public void setVariazioneStoricoAutobus(AutobusVO variazioneStoricoAutobus) {
		this.variazioneStoricoAutobus = variazioneStoricoAutobus;
	}

	public Long getFkProcedimento() {
		return fkProcedimento;
	}

	public void setFkProcedimento(Long fkProcedimento) {
		this.fkProcedimento = fkProcedimento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTestoMessaggioSistema() {
		return testoMessaggioSistema;
	}

	public void setTestoMessaggioSistema(String testoMessaggioSistema) {
		this.testoMessaggioSistema = testoMessaggioSistema;
	}

	public Long getFkTipoMessaggioSistema() {
		return fkTipoMessaggioSistema;
	}

	public void setFkTipoMessaggioSistema(Long fkTipoMessaggioSistema) {
		this.fkTipoMessaggioSistema = fkTipoMessaggioSistema;
	}

}
