/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.custom.SoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.AziendaMandatariaVO;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoEsecutoreVO;

public interface RicercaDAO {
	public List<SoggettoGiuridicoDTO> getElencoSoggetto(FiltroSoggettoVO filtro);

	public List<SirtplcTContrattoDTO> getElencoContratto(FiltroContrattoVO filtro);

	public Long isAziendaAttiva(@Param("idSoggetto") Long idSoggetto);
	
	List<AziendaMandatariaVO> getAziendaMandataria( @Param("idContratto") Long idContratto);
	
	List<SoggettoEsecutoreVO> getSoggettoEsecutore(@Param("bool") Boolean bool,@Param("idSoggettoGiuridico") Long idSoggettoGiuridico );
}
