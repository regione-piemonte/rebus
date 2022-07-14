/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.vo.InserisciSoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.NaturaGiuridicaVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoEsecutoreVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.TipoSoggettoGiuridicoVO;

public interface SoggettoService {

	List<TipoSoggettoGiuridicoVO> getTipiSoggettoGiuridico(Boolean bol);

	List<NaturaGiuridicaVO> getNatureGiuridiche();

	Long inserisciSoggetto(InserisciSoggettoGiuridicoVO soggetto, byte[] file, String filename);

	List<SoggettoEsecutoreVO> getSoggettiEsecutoriTitolari(Boolean bol, Long idSoggettoGiuridico);

	SoggettoGiuridicoVO dettaglioSoggetto(long id, String action);

	Long modificaSoggetto(SoggettoGiuridicoVO soggettoRequest, byte[] fileByte, String fileName);

	byte[] getLogoByIdSoggetto(Long id);
	
	void eliminaLogoOnDb(Long idLogo);

}
