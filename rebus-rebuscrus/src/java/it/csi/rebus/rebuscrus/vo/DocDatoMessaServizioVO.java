/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

public class DocDatoMessaServizioVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDatoMessaServizio;
	private Long idDocContribuzione;

	public Long getIdDatoMessaServizio() {
		return idDatoMessaServizio;
	}

	public void setIdDatoMessaServizio(Long idDatoMessaServizio) {
		this.idDatoMessaServizio = idDatoMessaServizio;
	}

	public Long getIdDocContribuzione() {
		return idDocContribuzione;
	}

	public void setIdDocContribuzione(Long idDocContribuzione) {
		this.idDocContribuzione = idDocContribuzione;
	}

}
