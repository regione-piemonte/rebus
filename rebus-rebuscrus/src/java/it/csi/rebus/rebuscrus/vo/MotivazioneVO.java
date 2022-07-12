/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class MotivazioneVO extends ExtendVO {
	private Boolean flgMotivAltro;

	/**
	 * @return the flgMotivAltro
	 */
	public Boolean getFlgMotivAltro() {
		return flgMotivAltro;
	}

	/**
	 * @param flgMotivAltro
	 *            the flgMotivAltro to set
	 */
	public void setFlgMotivAltro(Boolean flgMotivAltro) {
		this.flgMotivAltro = flgMotivAltro;
	}
}
