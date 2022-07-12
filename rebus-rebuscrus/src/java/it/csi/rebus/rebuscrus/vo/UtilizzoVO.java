/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class UtilizzoVO extends ParentVO {
	private	ContrattoProcVO contratto;
	private TipoContrattoVO tipoContratto;
	
	public ContrattoProcVO getContratto() {
		return contratto;
	}
	public void setContratto(ContrattoProcVO contratto) {
		this.contratto = contratto;
	}
	public TipoContrattoVO getTipoContratto() {
		return tipoContratto;
	}
	public void setTipoContratto(TipoContrattoVO tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	
}
