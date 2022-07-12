/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
public interface ValidationService {

	void verificaTargaTelaioAndFileExtention(AutobusVO autobus, String fileName, byte[] file);

	void verificaTargaTelaio(AutobusVO autobus);

	boolean existsTargaAndPrimoTelaio(AutobusDettagliatoVO autobus);
	
	boolean correctYear(AutobusDettagliatoVO autobus);
	
	
	boolean year(AutobusVO autobus);
	

}
