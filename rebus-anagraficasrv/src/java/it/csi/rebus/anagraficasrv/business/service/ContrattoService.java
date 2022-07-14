/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.Date;
import java.util.List;

import it.csi.rebus.anagraficasrv.vo.AmbTipServizioVO;
import it.csi.rebus.anagraficasrv.vo.AmbitoServizioVO;
import it.csi.rebus.anagraficasrv.vo.AziendaMandatariaVO;
import it.csi.rebus.anagraficasrv.vo.BacinoVO;
import it.csi.rebus.anagraficasrv.vo.ContrAmbTipServVO;
import it.csi.rebus.anagraficasrv.vo.ContrattoVO;
import it.csi.rebus.anagraficasrv.vo.InserisciContrattoVO;
import it.csi.rebus.anagraficasrv.vo.ModalitaAffidamentoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoCoinvoltoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoSubentroVO;
import it.csi.rebus.anagraficasrv.vo.TipoAffidamentoVO;
import it.csi.rebus.anagraficasrv.vo.TipoDocumentoVO;
import it.csi.rebus.anagraficasrv.vo.TipoSostituzioneVO;
import it.csi.rebus.anagraficasrv.vo.TipologiaServizioVO;

public interface ContrattoService {

	List<BacinoVO> getBacini();

	List<TipoAffidamentoVO> getTipiAffidamento();

	List<ModalitaAffidamentoVO> getModalitaAffidamento();

	List<TipologiaServizioVO> getTipologieServizio();

	List<AmbitoServizioVO> getAmbitiServizioByIdTipologiaServizio(Long idTipologiaServizio);

	List<TipoDocumentoVO> getTipiDocumento();

	List<AziendaMandatariaVO> getAziendaMandataria(Long idContratto);

	List<AmbTipServizioVO> getAmbTipServizio();

	List<ContrAmbTipServVO> getContrattiAmbitiTipologiaServizio(Long idContratto);

	Long inserisciContratto(InserisciContrattoVO contrattoRequest);

	String getDescrizioneAmbTipServiziobyId(Long id);

	ContrattoVO dettaglioContratto(long id, String action);

	Long modificaContratto(ContrattoVO contrattoRequest);

	byte[] getContenutoDocumentoByIdContrattoAndTipo(Long idContratto, Long idDocumento);

	List<TipoSostituzioneVO> getTipiSostituzione();

	List<SoggettoSubentroVO> getSoggettiSubentro(Long idContratto, Long idTipoSoggContraente);
	
	List<SoggettoCoinvoltoVO> filtraSoggettiCoinvolti(Long id,Date filtro);

}
