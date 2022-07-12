/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.Date;
import java.util.List;

import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTProcedimentoDTO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcDatiVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcVO;
import it.csi.rebus.rebuscrus.vo.DettaglioRichiestaVO;
import it.csi.rebus.rebuscrus.vo.FiltroProcedimentiVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaUsoInLineaVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;
import it.csi.rebus.rebuscrus.vo.MotivazioneVO;
import it.csi.rebus.rebuscrus.vo.MotorizzazioneVO;
import it.csi.rebus.rebuscrus.vo.ProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.StatoProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.TipoContrattoVO;
import it.csi.rebus.rebuscrus.vo.TipoDocumentoVO;
import it.csi.rebus.rebuscrus.vo.TipoProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.TransizioneAutomaVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoVO;

public interface ProcedimentiService {

	List<ProcedimentoVO> elencoProcedimenti();

	List<ProcedimentoVO> filtraElencoProcedimenti(FiltroProcedimentiVO filtro);

	TipoProcedimentoVO getTipoProcedimento(Long id);

	TipoProcedimentoVO getTipoProcedimentoByIdProc(Long id);

	List<MotorizzazioneVO> getMotorizzazioni();

	List<MotivazioneVO> getMotivazioni(Long idTipoProcedimento, boolean hasDataFilter);

	List<ContrattoProcVO> getContrattiInserisci();

	List<ContrattoProcVO> getContrattiModifica(Long idProcedimento);

	List<TipoDocumentoVO> getTipiDocumento(Long idTipoProcedimento);

	Long inserisciRichiesta(InserisciRichiestaVO richiestaRequest) throws Exception;
	
	Long inserisciRichiestaUsoInLinea(InserisciRichiestaUsoInLineaVO richiestaRequest) throws Exception;

	DettaglioRichiestaVO dettaglioRichiesta(long id, String action) throws Exception;
	

	List<StatoProcedimentoVO> elencoStato();

	Long modificaRichiesta(DettaglioRichiestaVO richiestaRequest);

	List<TipoProcedimentoVO> elencoTipologia();

	void eliminaProcedimento(Long idProcedimento);

	List<TipoContrattoVO> getTipiContratto();

	List<TransizioneAutomaVO> getTransizioniAutoma(Long idProcedimento, Long idStatoIterRichiesta, String parte) throws Exception;

	Long avanzaIterRichiesta(DettaglioRichiestaVO richiestaRequest, TransizioneAutomaVO transizioneRequest, String notaTransizione) throws ErroreGestitoException;

	List<Long> getTipiMessaggio(Long idContesto);

	String descrizioneAziendaMessaggioProc(Long idProcedimento);

	String getNoteMessaggio(Long idProcedimento, Long idTipoMessaggio, Date dataCreazione);

	RebuspTProcedimentoDTO getProcedimentoByKey(Long key);

	Long inserisciRichiestaSostituzione(List<InserisciRichiestaVO> richiestaRequests);

	Long modificaRichiestaSostituzione(List<DettaglioRichiestaVO> richiestaRequests);

	DettaglioRichiestaVO getFirmaProcedimento();

	List<VoceDiCostoVO> getVociDiCosto();

	Long getNumProcedimento(Long idTipoProcedimento) throws Exception;

	ContrattoProcDatiVO getDatiContratto(Long idContratto, Long idProcedimento);

	Long getGestoreContratto(Long idTipoProcedimento, Long idContratto, Long idProcedimento);

	

}