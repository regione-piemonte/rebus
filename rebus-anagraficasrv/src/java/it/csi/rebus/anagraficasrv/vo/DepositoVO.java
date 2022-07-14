/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class DepositoVO extends ParentVO {
	private Long id;
	private String denominazione;
	private UbicazioneVO ubicazione;
	private String telefono;
	private Boolean depositoPrevalenteFlg;

	public DepositoVO() {
		this.ubicazione = new UbicazioneVO();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public UbicazioneVO getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(UbicazioneVO ubicazione) {
		this.ubicazione = ubicazione;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getDepositoPrevalenteFlg() {
		return depositoPrevalenteFlg;
	}

	public void setDepositoPrevalenteFlg(Boolean depositoPrevalenteFlg) {
		this.depositoPrevalenteFlg = depositoPrevalenteFlg;
	}
}
