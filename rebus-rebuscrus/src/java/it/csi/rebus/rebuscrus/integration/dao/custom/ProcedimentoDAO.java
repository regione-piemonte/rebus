/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDStatoIterDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcVO;
import it.csi.rebus.rebuscrus.vo.FiltroProcedimentiVO;
import it.csi.rebus.rebuscrus.vo.ProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.RichiestaPdfVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;

public interface ProcedimentoDAO {

	public List<ProcedimentoVO> getProcedimenti(FiltroProcedimentiVO filtro);

	public List<ContrattoProcVO> getContratti(@Param("idAzienda") Long idAzienda,
			@Param("idProcedimento") Long idProcedimento);

	public List<RebusDTipoDocumentoDTO> getTipiDocumento(@Param("idTipoProcedimento") Long idTipoProcedimento);

	public String getDescAziendaFromProcedimento(@Param("idProcedimento") Long idProcedimento);

	public Long getDestinatarioMessaggioProcedimentoEnteCommittente(@Param("idContratto") Long idContratto);

	public Long getDestinatarioMessaggioProcedimentoConsorzio(@Param("idContratto") Long idContratto,
			@Param("idProcedimento") Long idProcedimento);

	public Long getDestinatarioMessaggioProcedimentoSostituzioneConsorzio(@Param("idContratto") Long idContratto,
			@Param("idProcedimento") Long idProcedimento);

	public Long getDestinatarioMessaggioProcedimento(@Param("idSoggettoGiuridico") Long idSoggettoGiuridico);

	public SirtplaTSoggettoGiuridicoDTO getGestoreContrattoProcedimento(@Param("idContratto") Long idContratto,
			@Param("idProcedimento") Long idProcedimento);

	public SirtplaTSoggettoGiuridicoDTO getGestoreContrattoProcedimentoSostituzione(
			@Param("idContratto") Long idContratto, @Param("idProcedimento") Long idProcedimento);

	public String getNoteMessaggio(@Param("idProcedimento") Long idProcedimento,
			@Param("idTipoMessaggio") Long idTipoMessaggio, @Param("dataCreazione") Date dataCreazione);

	public List<Long> getSoggettoAbilitatoModifica(@Param("idProcedimento") Long idProcedimento);

	public Long getEnteAbilitatoModifica(@Param("idProcedimento") Long idProcedimento);

	public Long getSoggettoAbilitatoScaricaPDF(@Param("idProcedimento") Long idProcedimento);

	public Long getEnteAbilitatoScaricaPDF(@Param("idProcedimento") Long idProcedimento);

	public RebuspTProcedimentoDTO getProcedimentoForFirma(@Param("idAzienda") Long idAzienda);

	public RebuspTProcedimentoDTO getProcedimentoForFirmaEnte(@Param("idEnte") Long idEnte);

	public RebuspTProcedimentoDTO getProcedimentoForPremessePrescrizioni(@Param("idEnte") Long idEnte,
			@Param("idtipoProcedimento") Long idtipoProcedimento);

	public Long countForNumProcedimento(@Param("dataInizio") Date dataInizio, @Param("dataFine") Date dataFine,
			@Param("idTipoProcedimento") Long idTipoProcedimento);

	public int getVeicoliTitolariContratto(@Param("primo_telaio") String primo_telaio,
			@Param("idProcedimento") Long idProcedimento);

	public List<RebuspRProcVeicoloDTO> checkVeicoliContrattiUsoInLinea(@Param("idTipoContratto") Long idTipoContratto,
			@Param("veicoloVO") List<VeicoloVO> veicoloVO);

	public List<RebuspDStatoIterDTO> getElencoStatiRichieste();

	//subaffidataria consorziata
	public List<RichiestaPdfVO> selectSogContr(@Param("idContratto") Long idContratto,
			@Param("idTipoSostituzione") Long idTipoSostituzione,
			@Param("idSoggettoRichiedente") Long idSoggettoRichiedente);

	
}