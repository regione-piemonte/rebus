/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.HashMap;
import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaContribuzioneDTO;
import it.csi.rebus.rebuscrus.vo.AssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.AttoAssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.ContribuzioneCompletaVO;
import it.csi.rebus.rebuscrus.vo.ContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.CostoFornituraVO;
import it.csi.rebus.rebuscrus.vo.DatoBonificoVO;
import it.csi.rebus.rebuscrus.vo.DatoFatturaVO;
import it.csi.rebus.rebuscrus.vo.DatoMessaServizioVO;
import it.csi.rebus.rebuscrus.vo.DatoSpesaVO;
import it.csi.rebus.rebuscrus.vo.DocContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.DocDatoMessaServizioVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.FonteFinanziamentoVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;
import it.csi.rebus.rebuscrus.vo.OrdineAcquistoVO;
import it.csi.rebus.rebuscrus.vo.ResponseStringVO;
import it.csi.rebus.rebuscrus.vo.TipoDocumentoQuietanzaVO;
import it.csi.rebus.rebuscrus.vo.TipoSostituzioneVO;
import it.csi.rebus.rebuscrus.vo.VoceCostoFornituraVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoContribuzioneVO;

public interface ContribuzioneService {

	List<VoceDiCostoContribuzioneVO> getAllVoceCostoContribuzione();

	List<AttoAssegnazioneRisorseVO> getAllAttoAssegnazioneRisorse();

	List<TipoDocumentoQuietanzaVO> getAllTipoDocumentoQuietanza();

	List<TipoSostituzioneVO> getAllTipoSostituzione();

	List<FonteFinanziamentoVO> getAllFonteFinanziamentoByIdAttoAssegnazione(Long idAttoAssegnazione);

	List<FonteFinanziamentoVO> getAllFonteFinanziamento();

	// Sezione: Assegnazione delle risorse - Contributi erogati
	Long insertAssegnazioneRisorse(AssegnazioneRisorseVO assegnazioneRisorse);

	// Sezione: Ordine di acquisto (GARA)
	Long insertOrdineAcquisto(OrdineAcquistoVO ordineAcquisto);

	// Sezione: Voci di costo delle forniture
	HashMap<Long, Long> insertVoceCostoFornitura(List<VoceCostoFornituraVO> voceCostoFornitura, Long idCostoFornitura);

	Long insertCostoFornitura(CostoFornituraVO costoFornitura, Long idDocCostoFornitura);

	// Sezione: Contributi erogabili e documentazione di spesa e di pagamento

	Long insertDatoSpesa(DatoSpesaVO datoSpesa);

	Long insertDatoFattura(DatoFatturaVO datoFattura, Long idDoc);

	void insertOggettoFattura(Long idDatoFattura, Long idVoceCostoFornitura);

	// Sezione: Messa in servizio, rispetto dei vincoli e all assolvimento delle
	// azioni di pubblicita

	Long insertDatoMessaServizio(DatoMessaServizioVO datoMessaServizio);

	void insertDocDatoMessaServizio(Long idDatoMessaServizio, Long idDocumento);

	// Sezione: Stato della rendicontazione
	Long insertContribuzione(ContribuzioneCompletaVO contribuzioneCompleta, InserisciRichiestaVO richiestaRequest);

	Long insertDocContribuzione(DocContribuzioneVO datiDocumento);

	DocContribuzioneVO getDocumentoById(Long id);

	List<VExportRicercaContribuzioneDTO> filtraElencoContribuzione(FiltroContribuzioneVO filtro);

	List<VExportRicercaContribuzioneDTO> elencoContribuzione();

	// GET CONTRIBUZIONE
	ContribuzioneVO getContribuzioneByIdProcedimento(Long idProcedimento);

	ContribuzioneVO getContribuzioneById(Long idContribuzione);

	// Sezione: Assegnazione delle risorse - Contributi erogati
	AssegnazioneRisorseVO getAssegnazioneRisorseById(Long idAassegnazioneRisorse);

	// Sezione: Ordine di acquisto (GARA)
	OrdineAcquistoVO getOrdineAcquistoById(Long idOrdineAcquisto);

	// Sezione: Voci di costo delle forniture
	CostoFornituraVO getCostoFornituraById(Long idCostoFornitura);

	List<VoceCostoFornituraVO> getVoceCostoFornituraByIdCostoFornitura(Long idCostoFornitura);

	// RESTITUISCE LE INFO DEL DOCUMENTO SENZA IL FILE
	DocContribuzioneVO getInfoDocContribuzione(Long idDocumento);

	// RESTITUISCE IL DOUMENTO PER IL DOWNLOAD (CHIAMATA SUL BOTTONE DI DOWNLOAD)
	byte[] getDocContribuzione(Long idDocumento);

	// Sezione: Contributi erogabili e documentazione di spesa e di pagamento

	DatoSpesaVO getDatoSpesaById(Long idDatoSpesa);

	List<DatoFatturaVO> getDatoFatturaByIdDatoSpesa(Long idDatoSpesa);

	List<Long> getOggettoFatturaByIdDatoFattura(Long idDatoFattura);

	// Sezione: Messa in servizio, rispetto dei vincoli e all assolvimento delle
	// azioni di pubblicita

	DatoMessaServizioVO getDatoMessaServizioById(Long idDatoMessaServizio);

	List<DocDatoMessaServizioVO> getDocDatoMessaServizioByIdDatoMessaServizio(Long idDatoMessaServizio);

	ContribuzioneCompletaVO getContribuzioneCompletaByIdProcedimento(Long idProcedimento);

	ContribuzioneCompletaVO getContribuzioneCompletaById(Long idContribuzione);

	// Sezione: Assegnazione delle risorse - Contributi erogati
	Long updateAssegnazioneRisorse(AssegnazioneRisorseVO assegnazioneRisorse);

	// Sezione: Ordine di acquisto (GARA)
	Long updateOrdineAcquisto(OrdineAcquistoVO ordineAcquisto);

	Long updateCostoFornitura(CostoFornituraVO costoFornitura);

	Long updateContribuzione(ContribuzioneCompletaVO contribuzione, List<Long> listaVociCostoDaEliminare,
			List<Long> listaDatiFatturaDaEliminare, List<Long> listaDatiBonificoDaEliminare);

	// Sezione: Contributi erogabili e documentazione di spesa e di pagamento

	Long updateDatoSpesa(DatoSpesaVO datoSpesa);

	// Sezione: Messa in servizio, rispetto dei vincoli e all assolvimento delle
	// azioni di pubblicita

	Long updateDatoMessaServizio(DatoMessaServizioVO datoMessaServizio);
	
	Long updateDocContribuzione(DocContribuzioneVO datiDocumento);

	void updateDocDatoMessaServizio(Long oldDocumentId, DocContribuzioneVO newDocument, Long IdDatoMessaServizio);

	ResponseStringVO getTelaioByIdProcedimento(Long IdProcediemnto);
	
	boolean checkFinalStateIter(Long idStato);
	
	void insertDatoBonificoAndFatturaBonifico(List<DatoBonificoVO> listBonifici, Long idDatoFattura);
	
	List<String> getTelaiVeicoloDaSostituire();
}
