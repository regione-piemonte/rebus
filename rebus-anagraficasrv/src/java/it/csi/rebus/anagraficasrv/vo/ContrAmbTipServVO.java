/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class ContrAmbTipServVO extends ParentVO {
	private Long idContratto;
	private Long idAmbTipServizio;
	
	public Long getIdContratto() {
		return idContratto;
	}
	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}
	public Long getIdAmbTipServizio() {
		return idAmbTipServizio;
	}
	public void setIdAmbTipServizio(Long idAmbTipServizio) {
		this.idAmbTipServizio = idAmbTipServizio;
	}
}
