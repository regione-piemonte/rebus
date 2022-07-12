/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaContribuzioneDTO;
import it.csi.rebus.rebuscrus.vo.AttoAssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.DatoBonificoVO;
import it.csi.rebus.rebuscrus.vo.DocContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.FonteFinanziamentoVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoContribuzioneVO;

public interface ContribuzioneDAO {

	// METODO CREATO MANUALMENTE
	List<FonteFinanziamentoVO> selectByIdAttoAssegnazione(@Param("idAttoAssegnazione") Long idAttoAssegnazione);
	
	List<AttoAssegnazioneRisorseVO> getAllAttoAssegnazione();
	
	List<VoceDiCostoContribuzioneVO> getAllVociCosto();
	// RESTITUISCE LE INFO DEL DOCUMENTO SENZA IL FILE
	DocContribuzioneVO selectDocContribuzioneForInfo(Long idDocumento);

	
	//gestione dei filtro in ricerca contribuzione
	public List<VExportRicercaContribuzioneDTO> getElencoContribuzione(FiltroContribuzioneVO filtro);
	
	Long getIdProcedimento(@Param("primoTelaio") String primoTelaio,@Param("idTipoProcedimento") int idTipoProcedimento);

	Long getDestinatarioMessaggioContribuzione(@Param("idProcedimento") Long idProcedimento);
	
	String getTelaio(@Param("idProcedimento") Long idProcedimento);
	
	Long finalStateIter(@Param("idStato") Long idStato);
	
	List<DatoBonificoVO> getDatoBonficoByIdDatoFattura(@Param("idDatoFattura") Long idDatoFattura);
	
	List<String> getTelaiVeicoloDaSostituire(@Param("idAzienda") Long idAzienda);
	
}