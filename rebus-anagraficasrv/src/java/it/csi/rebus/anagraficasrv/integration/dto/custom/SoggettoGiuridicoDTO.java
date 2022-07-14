/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto.custom;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;

@SuppressWarnings("serial")
public class SoggettoGiuridicoDTO extends SirtplaTSoggettoGiuridicoDTO {
	private Long idProvinciaSedeLegale;

	/**
	 * @return the idProvinciaSedeLegale
	 */
	public Long getIdProvinciaSedeLegale() {
		return idProvinciaSedeLegale;
	}

	/**
	 * @param idProvinciaSedeLegale
	 *            the idProvinciaSedeLegale to set
	 */
	public void setIdProvinciaSedeLegale(Long idProvinciaSedeLegale) {
		this.idProvinciaSedeLegale = idProvinciaSedeLegale;
	}
}
