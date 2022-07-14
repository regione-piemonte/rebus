/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class AmbTipServizioVO extends ParentVO {
	private Long idAmbTipServizio;
	private AmbitoServizioVO ambitoServizio;
	private TipologiaServizioVO tipologiaServizio;

	public Long getIdAmbTipServizio() {
		return idAmbTipServizio;
	}

	public void setIdAmbTipServizio(Long idAmbTipServizio) {
		this.idAmbTipServizio = idAmbTipServizio;
	}

	public AmbitoServizioVO getAmbitoServizio() {
		return ambitoServizio;
	}

	public void setAmbitoServizio(AmbitoServizioVO ambitoServizio) {
		this.ambitoServizio = ambitoServizio;
	}

	public TipologiaServizioVO getTipologiaServizio() {
		return tipologiaServizio;
	}

	public void setTipologiaServizio(TipologiaServizioVO tipologiaServizio) {
		this.tipologiaServizio = tipologiaServizio;
	}

	
}
