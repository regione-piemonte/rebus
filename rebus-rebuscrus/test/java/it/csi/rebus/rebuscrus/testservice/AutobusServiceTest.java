/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.testservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.CrudAutobusService;
import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.testbase.RebuscrusJunitClassRunner;
import it.csi.rebus.rebuscrus.testbase.TestBaseService;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.DocumentoVO;

/**
 * @author 
 * @date 11 mar 2018
 *
 *       note: disabilitare nel service eventuale accesso a RequestContextHolder
 */
@RunWith(RebuscrusJunitClassRunner.class)
public class AutobusServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(AutobusServiceTest.class);

	@Autowired
	private RicercaAutobusService autobusService;
	@Autowired
	private DocumentoService documentoService;

	@Autowired
	CrudAutobusService crudAutobusService;

	// @Test
	// public void getDettaglioAutobus() {
	// logger.info("START");
	// AutobusVO aut = null;
	// try {
	// aut = autobusService.dettaglioAutobus(new Long(43429));
	// } catch (ErroreGestitoException e) {
	// logger.error("Eccezione gestita", e);
	// }
	//
	// assertNotNull(aut);
	// }

	@Test
	public void elencoDocumentoTest() {

		List<DocumentoVO> documenti = new ArrayList<>();

		try {
			documenti = documentoService.elencoDocumento();
		} catch (ErroreGestitoException e) {
			logger.error("Eccezione gestita", e);
			throw e;
		} catch (Exception e) {
			logger.error("Eccezione generica: ", e);
		}

		if (documenti.size() != 0) {
			System.out.println("Lista piena");
			System.out.println(documenti.get(0).toString());
		} else {
			System.out.println("lista vuota");
		}
	}

	@Test
	public void modificaAutobus() {
		logger.info("START");

		AutobusVO aut = crudAutobusService.dettaglioAutobus(new Long(44000), "Action.VIEW");

		aut.setFlagVerificaAmp("S");

		aut.setNumeroPorte("10");

		crudAutobusService.updateAutobus(aut, null, null, false);

		logger.info("STOP");
	}

	/*
	 * @Test public void inserimentoAutobus() { logger.info("START");
	 *
	 * AutobusDettagliatoVO autobus = this.getAutobusMock();
	 *
	 * try { autobusService.inserisciAutobus(autobus); } catch
	 * (ErroreGestitoException e) { logger.error("Eccezione gestita", e); throw
	 * e; } catch (Exception e) { logger.error("Eccezione generica: ", e); } }
	 */

	@Test
	public void eliminiaDocumentoTest() {
		AutobusVO autobus = new AutobusVO();
		autobus.setIdDocumento(new Long(1));
		autobus.setId(new Long(44044));

		try {
			documentoService.eliminaDocumento(autobus);
		} catch (ErroreGestitoException e) {
			logger.error("Eccezione gestita", e);
			throw e;
		} catch (Exception e) {
			logger.error("Eccezione generica: ", e);
		}

	}

	private AutobusDettagliatoVO getAutobusMock() {
		AutobusDettagliatoVO autobus = new AutobusDettagliatoVO();

		// Dati identificativi e d'immatricolazione
		autobus.idAzienda = 1L;
		autobus.telaio = "LUCA_VACCA";
		autobus.idTipoImmatricolazione = 0L;
		autobus.primoTelaio = "LUCA_VACCA";
		autobus.dataPrimaImmatricolazione = new Date(10 / 3 / 2018);
		autobus.targa = "LUCA_VACCA";
		autobus.enteAutorizzPrimaImm = "GTT";
		autobus.matricola = "IJ00";
		autobus.dataUltimaImmatricolazione = new Date(10 / 3 / 18);

		// Caratteristica Fisica e Tecnologiche
		autobus.marca = "IVECO";
		autobus.idTipoAllestimento = 0L;
		autobus.modello = "VAN";
		autobus.idTipoAlimentazione = 0L;
		autobus.omologazioneDirettivaEuropea = "Omologazione Direttiva EU";
		autobus.caratteristicheParticolari = "Graffi";
		autobus.idClasseAmbientale = 0L;
		autobus.idClasseVeicolo = 0L;
		autobus.lunghezza = 999.0;
		autobus.numeroPorte = 5L;
		autobus.nPostiInPiedi = 999L;
		autobus.nPostiSedere = 999L;
		autobus.nPostiRiservati = 999L;
		autobus.postiCarrozzina = 999L;

		// Dotazioni Specifiche
		autobus.idDotazioneDisabili = 0L;
		autobus.idAltriDispositiviPrevInc = new ArrayList<>();
		autobus.idAltriDispositiviPrevInc.add(5L);
		autobus.idAltriDispositiviPrevInc.add(6L);
		autobus.idImpiantiAudio = 0L;
		autobus.idImpiantiVisivi = 0L;
		autobus.altriDispositiviPrevenzInc = "estintore";

		// Dati Amministrativi ed Economici
		autobus.prezzoTotAcquisto = 10.0;
		autobus.idProprietaLeasing = 0L;
		autobus.contributoPubblicoAcquisto = 10.0;
		autobus.dataUltimaRevisione = new Date(1 / 3 / 18);
		autobus.tipologiaDimensionale = "100";

		autobus.dataUltimaRevisione = new Date(1 / 3 / 18);
		autobus.annoSostProg = "2019";

		// Verifica e Note
		autobus.notaRiservataAzienda = "nota azienda";
		autobus.notaRiservataAmp = "note amp";
		autobus.note = "note";

		// flags obbligatori
		autobus.flgCabinaGuidaIsolata = false;
		autobus.flgConteggiatoMiv = false;
		autobus.flagVerificaAmp = false;
		autobus.flagVerificaAzienda = false;
		autobus.flgFiltroFap = false;
		autobus.flgRilevatoreBip = false;
		autobus.flgVeicoloAssicurato = false;
		autobus.flagAlienato = false;
		autobus.flgRichiestaContr = false;

		return autobus;
	}

}
