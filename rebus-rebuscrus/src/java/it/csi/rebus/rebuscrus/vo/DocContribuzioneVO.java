/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class DocContribuzioneVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDocContribuzione;
	private Long idTipoDocumento;
	private String nomeFile;
	private Date dataCaricamento;
	private Long idUtenteAggiornamento;
	private String documento;
	private String label;
	private String descEstesa;

	public Long getIdDocContribuzione() {
		return idDocContribuzione;
	}

	public void setIdDocContribuzione(Long idDocContribuzione) {
		this.idDocContribuzione = idDocContribuzione;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescEstesa() {
		return descEstesa;
	}

	public void setDescEstesa(String descEstesa) {
		this.descEstesa = descEstesa;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

}
