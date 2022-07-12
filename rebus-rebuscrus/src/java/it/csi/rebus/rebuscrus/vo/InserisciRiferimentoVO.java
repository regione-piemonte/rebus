/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class InserisciRiferimentoVO extends ParentVO {
	private Long idRiferimento;
	private String descrizione;
	private byte[] file;
	private String nomeFile;
	private Date dataCaricamento;

	

	public Long getIdRiferimento() {
		return idRiferimento;
	}

	public void setIdRiferimento(Long idRiferimento) {
		this.idRiferimento = idRiferimento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
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

}
