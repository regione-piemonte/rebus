/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebusTMessaggiDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.id_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long idMessaggio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_tipo_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkTipoMessaggio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_utente_mittente
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkUtenteMittente;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_utente_destinatario
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkUtenteDestinatario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkVariazAutobus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_storia_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkStoriaVariazAutobus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private String messaggio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.data_creazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Date dataCreazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.flg_letto
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private String flgLetto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.data_lettura
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Date dataLettura;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.flg_archiviato
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private String flgArchiviato;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.data_archiviazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Date dataArchiviazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_storia_variaz_autobus_succ
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkStoriaVariazAutobusSucc;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_procedimento
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkProcedimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_t_messaggi.fk_tipo_messaggio_sistema
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private Long fkTipoMessaggioSistema;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_t_messaggi
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.id_messaggio
	 * @return  the value of rebus_t_messaggi.id_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getIdMessaggio() {
		return idMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.id_messaggio
	 * @param idMessaggio  the value for rebus_t_messaggi.id_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setIdMessaggio(Long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_tipo_messaggio
	 * @return  the value of rebus_t_messaggi.fk_tipo_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkTipoMessaggio() {
		return fkTipoMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_tipo_messaggio
	 * @param fkTipoMessaggio  the value for rebus_t_messaggi.fk_tipo_messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkTipoMessaggio(Long fkTipoMessaggio) {
		this.fkTipoMessaggio = fkTipoMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_utente_mittente
	 * @return  the value of rebus_t_messaggi.fk_utente_mittente
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkUtenteMittente() {
		return fkUtenteMittente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_utente_mittente
	 * @param fkUtenteMittente  the value for rebus_t_messaggi.fk_utente_mittente
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkUtenteMittente(Long fkUtenteMittente) {
		this.fkUtenteMittente = fkUtenteMittente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_utente_destinatario
	 * @return  the value of rebus_t_messaggi.fk_utente_destinatario
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkUtenteDestinatario() {
		return fkUtenteDestinatario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_utente_destinatario
	 * @param fkUtenteDestinatario  the value for rebus_t_messaggi.fk_utente_destinatario
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkUtenteDestinatario(Long fkUtenteDestinatario) {
		this.fkUtenteDestinatario = fkUtenteDestinatario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_variaz_autobus
	 * @return  the value of rebus_t_messaggi.fk_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkVariazAutobus() {
		return fkVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_variaz_autobus
	 * @param fkVariazAutobus  the value for rebus_t_messaggi.fk_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkVariazAutobus(Long fkVariazAutobus) {
		this.fkVariazAutobus = fkVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_storia_variaz_autobus
	 * @return  the value of rebus_t_messaggi.fk_storia_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkStoriaVariazAutobus() {
		return fkStoriaVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_storia_variaz_autobus
	 * @param fkStoriaVariazAutobus  the value for rebus_t_messaggi.fk_storia_variaz_autobus
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkStoriaVariazAutobus(Long fkStoriaVariazAutobus) {
		this.fkStoriaVariazAutobus = fkStoriaVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.messaggio
	 * @return  the value of rebus_t_messaggi.messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public String getMessaggio() {
		return messaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.messaggio
	 * @param messaggio  the value for rebus_t_messaggi.messaggio
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio == null ? null : messaggio.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.data_creazione
	 * @return  the value of rebus_t_messaggi.data_creazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Date getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.data_creazione
	 * @param dataCreazione  the value for rebus_t_messaggi.data_creazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.flg_letto
	 * @return  the value of rebus_t_messaggi.flg_letto
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public String getFlgLetto() {
		return flgLetto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.flg_letto
	 * @param flgLetto  the value for rebus_t_messaggi.flg_letto
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFlgLetto(String flgLetto) {
		this.flgLetto = flgLetto == null ? null : flgLetto.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.data_lettura
	 * @return  the value of rebus_t_messaggi.data_lettura
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Date getDataLettura() {
		return dataLettura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.data_lettura
	 * @param dataLettura  the value for rebus_t_messaggi.data_lettura
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.flg_archiviato
	 * @return  the value of rebus_t_messaggi.flg_archiviato
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public String getFlgArchiviato() {
		return flgArchiviato;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.flg_archiviato
	 * @param flgArchiviato  the value for rebus_t_messaggi.flg_archiviato
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFlgArchiviato(String flgArchiviato) {
		this.flgArchiviato = flgArchiviato == null ? null : flgArchiviato.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.data_archiviazione
	 * @return  the value of rebus_t_messaggi.data_archiviazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Date getDataArchiviazione() {
		return dataArchiviazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.data_archiviazione
	 * @param dataArchiviazione  the value for rebus_t_messaggi.data_archiviazione
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setDataArchiviazione(Date dataArchiviazione) {
		this.dataArchiviazione = dataArchiviazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_storia_variaz_autobus_succ
	 * @return  the value of rebus_t_messaggi.fk_storia_variaz_autobus_succ
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkStoriaVariazAutobusSucc() {
		return fkStoriaVariazAutobusSucc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_storia_variaz_autobus_succ
	 * @param fkStoriaVariazAutobusSucc  the value for rebus_t_messaggi.fk_storia_variaz_autobus_succ
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkStoriaVariazAutobusSucc(Long fkStoriaVariazAutobusSucc) {
		this.fkStoriaVariazAutobusSucc = fkStoriaVariazAutobusSucc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_procedimento
	 * @return  the value of rebus_t_messaggi.fk_procedimento
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkProcedimento() {
		return fkProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_procedimento
	 * @param fkProcedimento  the value for rebus_t_messaggi.fk_procedimento
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkProcedimento(Long fkProcedimento) {
		this.fkProcedimento = fkProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_t_messaggi.fk_tipo_messaggio_sistema
	 * @return  the value of rebus_t_messaggi.fk_tipo_messaggio_sistema
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public Long getFkTipoMessaggioSistema() {
		return fkTipoMessaggioSistema;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_t_messaggi.fk_tipo_messaggio_sistema
	 * @param fkTipoMessaggioSistema  the value for rebus_t_messaggi.fk_tipo_messaggio_sistema
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	public void setFkTipoMessaggioSistema(Long fkTipoMessaggioSistema) {
		this.fkTipoMessaggioSistema = fkTipoMessaggioSistema;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_messaggi
	 * @mbg.generated  Thu Nov 05 12:40:05 CET 2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idMessaggio=").append(idMessaggio);
		sb.append(", fkTipoMessaggio=").append(fkTipoMessaggio);
		sb.append(", fkUtenteMittente=").append(fkUtenteMittente);
		sb.append(", fkUtenteDestinatario=").append(fkUtenteDestinatario);
		sb.append(", fkVariazAutobus=").append(fkVariazAutobus);
		sb.append(", fkStoriaVariazAutobus=").append(fkStoriaVariazAutobus);
		sb.append(", messaggio=").append(messaggio);
		sb.append(", dataCreazione=").append(dataCreazione);
		sb.append(", flgLetto=").append(flgLetto);
		sb.append(", dataLettura=").append(dataLettura);
		sb.append(", flgArchiviato=").append(flgArchiviato);
		sb.append(", dataArchiviazione=").append(dataArchiviazione);
		sb.append(", fkStoriaVariazAutobusSucc=").append(fkStoriaVariazAutobusSucc);
		sb.append(", fkProcedimento=").append(fkProcedimento);
		sb.append(", fkTipoMessaggioSistema=").append(fkTipoMessaggioSistema);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}