/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.CrudAutobusService;
import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.business.service.StoricizzazioneService;
import it.csi.rebus.rebuscrus.business.service.UtilityService;
import it.csi.rebus.rebuscrus.business.service.ValidationService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusDClasseAmbEuroDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDClasseVeicoloDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDDispositiviPrevenzDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDDotazioneDisabiliDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDImpiantiAudioDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDImpiantiVisiviDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDProprietaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoAlimentazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoAllestimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoImmatricolDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoMessaggioDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipologiaDimensDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRAziendaAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRDocVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRUtenteAzEnteDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAziendeDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTStoriaVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDPortabiciDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDSistemaLocalizzazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDSistemaVideosorveglianzaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusmTMisEmissioniDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusmTMisPortabiciDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcVeicoloDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTIterProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplaRSogGiuridDepositoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplaTDepositoDAO;
import it.csi.rebus.rebuscrus.integration.dao.VAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.VAutobusSmallDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.AutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.CronologiaDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.MisurazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ProcedimentiDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.RicercaDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoAllestimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDPortabiciDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDPortabiciSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaLocalizzazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaLocalizzazioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaVideosorveglianzaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaVideosorveglianzaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusmTMisEmissioniDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusmTMisEmissioniSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusmTMisPortabiciDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusmTMisPortabiciSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaRSogGiuridDepositoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaRSogGiuridDepositoSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTDepositoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusSmallDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusSmallSelector;
import it.csi.rebus.rebuscrus.integration.dto.custom.MisurazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoloDTO;
import it.csi.rebus.rebuscrus.integration.mapper.AllegatoVeicoloMapper;
import it.csi.rebus.rebuscrus.integration.mapper.DepositoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.DettaglioAutobusMapper;
import it.csi.rebus.rebuscrus.integration.mapper.VeicoloMapper;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.DateUtils;
import it.csi.rebus.rebuscrus.util.NumberUtil;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.StringUtil;
import it.csi.rebus.rebuscrus.vo.AllegatoVeicoloVO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.CampagnaVO;
import it.csi.rebus.rebuscrus.vo.DepositoVO;
import it.csi.rebus.rebuscrus.vo.DocVariazAutobusVO;
import it.csi.rebus.rebuscrus.vo.DocumentoVO;
import it.csi.rebus.rebuscrus.vo.EmissioniVO;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;
import it.csi.rebus.rebuscrus.vo.MisurazioniVO;
import it.csi.rebus.rebuscrus.vo.PortabiciAutobusVO;
import it.csi.rebus.rebuscrus.vo.PortabiciVO;
import it.csi.rebus.rebuscrus.vo.SistemaLocalizzazioneVO;
import it.csi.rebus.rebuscrus.vo.SistemaVideosorveglianzaVO;
import it.csi.rebus.rebuscrus.vo.TipologiaDimensioneVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
@Component
public class CrudAutobusServiceImpl implements CrudAutobusService {

	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;
	@Autowired
	private RebusRAziendaAutobusDAO rebusRAziendaAutobusDAO;
	@Autowired
	private RebusRVarautobusDpDAO rebusRVarautobusDpDAO;
	@Autowired
	private RebusTAutobusDAO rebusTAutobusDAO;
	@Autowired
	private RebusRDocVariazAutobusDAO rebusRDocVariazAutobusDAO;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private StoricizzazioneService storicizzazioneService;
	@Autowired
	private UtilityService utilityService;
	@Autowired
	private DettaglioAutobusMapper dettaglioMapper;
	@Autowired
	private VAutobusDAO vAutobusDAO;
	@Autowired
	private VAutobusSmallDAO vAutobusSmallDAO;
	@Autowired
	private RebusTAziendeDAO rebusTaziendaDAO;
	@Autowired
	private RebusDTipoAlimentazioneDAO rebusDTipoAlimentazioneDAO;
	@Autowired
	private RebusDTipoAllestimentoDAO rebusDTipoAllestimentoDAO;
	@Autowired
	private RebusDImpiantiAudioDAO rebusDImpiantiAudioDAO;
	@Autowired
	private RebusDImpiantiVisiviDAO rebusDImpiantiVisiviDAO;
	@Autowired
	private RebusDClasseVeicoloDAO rebusDClasseVeicoloDAO;
	@Autowired
	private RebusDTipoImmatricolDAO rebusDTipoImmatricolDAO;
	@Autowired
	private RebusDClasseAmbEuroDAO rebusDClasseAmbEuroDAO;
	@Autowired
	private RebusDProprietaDAO rebusDProprietaDAO;
	@Autowired
	private RebusDDotazioneDisabiliDAO rebusDDotazioneDisabiliDAO;
	@Autowired
	private RebusDTipologiaDimensDAO rebusDTipologiaDimensDAO;
	@Autowired
	private RebusDDispositiviPrevenzDAO rebusDDispositiviPrevenzDAO;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private RebusDTipoMessaggioDAO rebusDTipoMessaggioDAO;
	@Autowired
	private MessaggiService messaggiService;
	@Autowired
	private RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO;
	@Autowired
	private RebusTStoriaVariazAutobusDAO rebusTStoriaVariazAutobusDAO;
	@Autowired
	private RicercaDAO ricercaDAO;
	@Autowired
	private RebuspTIterProcedimentoDAO rebuspTIterProcedimentoDAO;
	@Autowired
	private SirtplaRSogGiuridDepositoDAO sirtplaRSogGiuridDepositoDAO;
	@Autowired
	private SirtplaTDepositoDAO sirtplaTDepositoDAO;
	@Autowired
	private RebuspRProcVeicoloDAO rebuspRProcVeicoloDAO;
	@Autowired
	private VeicoloMapper veicoloMapper;
	@Autowired
	private AllegatoVeicoloMapper allegatoVeicoloMapper;
	@Autowired
	private DepositoMapper depositoMapper;
	@Autowired
	private CronologiaDAO cronologiaDAO;
	@Autowired
	private ProcedimentiDAO procedimentiDAO;
	@Autowired
	private MisurazioneDAO misurazioneDAO;
	@Autowired
	private RebusmTMisEmissioniDAO rebusmTMisEmissioniDAO;
	@Autowired
	private RebusmTMisPortabiciDAO rebusmTMisPortabiciDAO;
	@Autowired
	private AutobusDAO autobusDAO;
	@Autowired
	private RebuscDPortabiciDAO rebuscDPortabiciDAO;
	@Autowired
	private RebuscDSistemaLocalizzazioneDAO rebuscDSistemaLocalizzazioneDAO;
	@Autowired
	private RebuscDSistemaVideosorveglianzaDAO rebuscDSistemaVideosorveglianzaDAO;

	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	@Autowired
	private Mapper dozerMapper;

	@Override
	public void eliminaAutobus(String[] idsVariazAutobus) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA);
		if (idsVariazAutobus != null) {
			for (String id : idsVariazAutobus) {
				id = id.replace("[", "").replace("]", "");
				RebuspRProcVeicoloSelector procedimentoVeicoloSelector = new RebuspRProcVeicoloSelector();
				procedimentoVeicoloSelector.createCriteria().andPrimoTelaioEqualTo(id);
				List<RebuspRProcVeicoloDTO> procedimentoVeicolo = rebuspRProcVeicoloDAO
						.selectByExample(procedimentoVeicoloSelector);
				if (procedimentoVeicolo != null && procedimentoVeicolo.size() > 0) {
					throw new ErroreGestitoException(
							"ATTENZIONE !!! Il veicolo non puo' essere eliminato in quanto presente in almeno una richiesta",
							"VD");
				}
				// recupero primo telaio
				String primoTelaio = rebusTVariazAutobusDAO.selectByPrimaryKey(Long.parseLong(id)).getPrimoTelaio();
				RebusRAziendaAutobusSelector selAz = new RebusRAziendaAutobusSelector();
				selAz.createCriteria().andPrimoTelaioEqualTo(primoTelaio);
				rebusRAziendaAutobusDAO.deleteByExample(selAz);

				RebusRVarautobusDpSelector seldp = new RebusRVarautobusDpSelector();
				seldp.createCriteria().andIdVariazAutobusEqualTo(Long.parseLong(id));
				rebusRVarautobusDpDAO.deleteByExample(seldp);

				rebusTVariazAutobusDAO.deleteByPrimaryKey(Long.parseLong(id));
				rebusTAutobusDAO.deleteByPrimaryKey(primoTelaio);

				RebusRDocVariazAutobusSelector rebusRDocVarAutobusSelector = new RebusRDocVariazAutobusSelector();
				rebusRDocVarAutobusSelector.createCriteria().andIdVariazAutobusEqualTo(Long.parseLong(id));
				rebusRDocVariazAutobusDAO.deleteByExample(rebusRDocVarAutobusSelector);

			}
		}
	}

	@Override
	public void updateAutobus(AutobusVO autobus, Boolean isUpload) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_BUS);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (autobus != null) {

			// VERIFICO CHE IL TELAIO E TARGA NON ESISTANO GIA
			validationService.verificaTargaTelaio(autobus);

			for (DocVariazAutobusVO doc : autobus.getDocumentiAutobus()) {
				if (!doc.getNomeFile().contains(".pdf") && doc != null) {
					throw new ErroreGestitoException(" Formato file del documento allegato non consentito! ", "TFNC");
				}
			}

			if (autobus.getAnnoSostProg() != null) {
				if (validationService.year(autobus)) {
					throw new ErroreGestitoException(
							"Data Prima Immatricolazione e Anno sostituzione programmata non congrui!", "CY");
				}
			}

			// AGGIORNO REBUS_T_AUTOBUS
			RebusTAutobusSelector sel = new RebusTAutobusSelector();
			sel.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio());

			RebusTAutobusDTO record = rebusTAutobusDAO.selectByExample(sel).get(0);
			record.setPrimoTelaio(autobus.getPrimoTelaio().toUpperCase());
			record.setMarca(autobus.getMarca());
			record.setModello(autobus.getModello());
			record.setDataPrimaImmatricolazione(autobus.getDataPrimaImmatricolazione());

			rebusTAutobusDAO.updateByExample(record, sel);

			// AGGIORNO REBUS_T_VARIAZ_AUTOBUS
			RebusTVariazAutobusSelector selVa = new RebusTVariazAutobusSelector();
			selVa.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio());

			RebusTVariazAutobusDTO dto = rebusTVariazAutobusDAO.selectByExample(selVa).get(0);
			Date now = new Date();
			RebusRAziendaAutobusSelector rebusRAziendaAutobusSel = new RebusRAziendaAutobusSelector();
			rebusRAziendaAutobusSel.createCriteria().andPrimoTelaioEqualTo(dto.getPrimoTelaio())
					.andDataAlienazioneGreaterThanOrEqualTo(now);
			rebusRAziendaAutobusSel.or().andPrimoTelaioEqualTo(dto.getPrimoTelaio()).andDataAlienazioneIsNull();
			rebusRAziendaAutobusSel.setOrderByClause("data_aggiornamento DESC");
			List<RebusRAziendaAutobusDTO> aziendaAutobus = rebusRAziendaAutobusDAO
					.selectByExample(rebusRAziendaAutobusSel);

			Long fkStoriaVariazAutobus = null;

			if (Constants.FLAG_SI.equalsIgnoreCase(dto.getFlgVerificatoAzienda())
					&& Constants.FLAG_SI.equalsIgnoreCase(autobus.getFlagVerificaAzienda())) {
				fkStoriaVariazAutobus = storicizzazioneService.storicizzaAutobus(dto, userInfo.getIdUtente(), now);
			}

			long utenteDestinatario = -1;
			// Recupero il destinatario del messaggio quando e diretto a AMP

			if (SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP)) {
				RebusRUtenteAzEnteSelector rebusUtenteSelector = new RebusRUtenteAzEnteSelector();
				rebusUtenteSelector.createCriteria().andFkUtenteEqualTo(dto.getFkUtente());
				rebusUtenteSelector.setOrderByClause("data_appartenenza desc");
				List<RebusRUtenteAzEnteDTO> rebusUtenti = rebusRUtenteAzEnteDAO.selectByExample(rebusUtenteSelector);
				RebusRUtenteAzEnteDTO rebusUtente = new RebusRUtenteAzEnteDTO();
				if (rebusUtenti.size() > 0) {
					rebusUtente = rebusUtenti.get(0);
				}

				if (rebusUtente.getIdRUtenteAzEnte() != null && aziendaAutobus != null
						&& aziendaAutobus.get(0).getFkAzienda().equals(rebusUtente.getFkAzienda())) {
					utenteDestinatario = dto.getFkUtente();
				} else {
					RebusTStoriaVariazAutobusDTO varStoriaAutobusDto = ricercaDAO.trovaDestinatario(dto);
					if (varStoriaAutobusDto != null) {
						utenteDestinatario = varStoriaAutobusDto.getFkUtente();
					}
				}
			}

			boolean isModificato = isModificatoAutobus(autobus, dto);

			String ora = DateUtils.timeToString(now);
			dto.setFkUtente(userInfo.getIdUtente());
			dto.setAltraAlimentazione(autobus.getAltraAlimentazione());
			dto.setCaratteristicheParticolari(autobus.getCaratteristicheParticolari());
			dto.setAltriDispositiviPrevenzInc(autobus.getAltriDispositiviPrevenzInc());
			dto.setAnnoSostProg(autobus.getAnnoSostProg());
			dto.setContributoPubblicoAcquisto(autobus.getContributoPubblicoAcquisto());
			dto.setDataAggiornamento(now);
			dto.setDataUltimaImmatricolazione(autobus.getDataUltimaImmatricolazione());
			dto.setEnteAutorizzPrimaImm(autobus.getEnteAutorizzPrimaImm());
			dto.setFkClasseAmbientaleEuro(autobus.getIdClasseAmbientale());
			dto.setFlgAvm(autobus.getFlgAvm());
			dto.setFlgVeicoloAssicurato(
					autobus.getFlgVeicoloAssicurato() == null ? Constants.FLAG_NO : autobus.getFlgVeicoloAssicurato());

			dto.setFlgAlienato(autobus.getFlgAlienato() == null ? null : autobus.getFlgAlienato());
			dto.setFkClasseVeicolo(autobus.getIdClasseVeicolo());
			// FkDepositoPrevalente non è più il deposito prevalmente ma quello
			// scelto dalla combo
			dto.setFkDeposito(autobus.getIdDeposito());
			dto.setFkDotazioneDisabili(autobus.getIdDotazioneDisabili());
			dto.setFkImpiantiAudio(autobus.getIdImpiantiAudio());
			dto.setFkImpiantiVisivi(autobus.getIdImpiantiVisivi());
			dto.setFkProprietaLeasing(autobus.getIdProprietaLeasing());
			dto.setFkTipoAlimentazione(autobus.getIdTipoAlimentazione());
			dto.setFkTipoAllestimento(autobus.getIdTipoAllestimento());
			dto.setFkTipoImmatricolazione(autobus.getIdTipoImmatricolazione());
			dto.setFlgCabinaGuidaIsolata(autobus.getFlgCabinaGuidaIsolata() == null ? Constants.FLAG_NO
					: autobus.getFlgCabinaGuidaIsolata());
			dto.setFlgContapasseggeri(autobus.getFlgContapasseggeri());
			dto.setFlgConteggiatoMiv(
					autobus.getFlgConteggiatoMiv() == null ? Constants.FLAG_UNDEFINED : autobus.getFlgConteggiatoMiv());
			dto.setFlgDuePiani(autobus.getFlgDuePiani());
			dto.setFlgImpiantoCondizionamento(autobus.getFlgImpiantoCondizionamento());
			dto.setFlgOtx(autobus.getFlgOtx());
			dto.setFlgRichiestaContr(
					autobus.getFlgRichiestaContr() == null ? Constants.FLAG_NO : autobus.getFlgRichiestaContr());
			dto.setFlgRilevatoreBip(
					autobus.getFlgRilevatoreBip() == null ? Constants.FLAG_NO : autobus.getFlgRilevatoreBip());
			dto.setFlgSnodato(autobus.getFlgSnodato());
			dto.setFlgFiltroFap(autobus.getFlgFiltroFap() == null ? Constants.FLAG_NO : autobus.getFlgFiltroFap());
			dto.setMotivazione(autobus.getMotivazione());

			dto.setFkPortabici(autobus.getFkPortabici());
			dto.setFkSistemaVideosorveglianza(autobus.getFkSistemaVideosorveglianza());
			dto.setFkSistemaLocalizzazione(autobus.getFkSistemaLocalizzazione());
			dto.setFlgBipCablato(autobus.getFlgBipCablato());
			dto.setFlgContapasseggeriIntegrato(autobus.getFlgContapasseggeriIntegrato());
			dto.setFlgSistemiProtezioneAutista(autobus.getFlgSistemiProtezioneAutista());
			dto.setAltriAllestimenti(autobus.getAltriAllestimenti());
			dto.setFlgContribuzione(autobus.getFlgContribuzione());

			dto.setFkCategoriaVeicolo(autobus.getIdCategoriaVeicolo());

			/*
			 * invii il messaggio di tipo 1 quando il record che stai modificando e'
			 * FLG_VERIFICATO_AMP = 'S' mandi il msg...e giri quel flag ad N
			 */

			boolean msgTipo2 = false;
			if (dto.getFlgVerificatoAzienda().equals("N") && autobus.getFlagVerificaAzienda().equals("S")) {
				if (userInfo.getIdAzienda() != null && this.getDepositi(userInfo.getIdAzienda()).size() == 0) {
					throw new ErroreGestitoException(
							"Impossibile verificare un autobus senza l'aggiunta di un deposito. Aggiungere un deposito all'autobus corrente");
				}
				msgTipo2 = true;
			}
			dto.setFlgVerificatoAmp(
					autobus.getFlagVerificaAmp() == null ? Constants.FLAG_UNDEFINED : autobus.getFlagVerificaAmp());
			if (Constants.FLAG_SI.equalsIgnoreCase(autobus.getFlagVerificaAmp())
					&& !StringUtils.isEmpty(dto.getMotivazione())) {
				dto.setMotivazione(null);
			}

			dto.setFlgVerificatoAzienda(autobus.getFlagVerificaAzienda());
			dto.setLunghezza(autobus.getLunghezza());
			dto.setnMatricolaAziendale(autobus.getMatricola());
			dto.setNotaRiservataAmp(autobus.getNotaRiservataAmp());
			dto.setNotaRiservataAzienda(autobus.getNotaRiservataAzienda());
			dto.setNote(autobus.getNote());
			dto.setnPostiInPiedi(NumberUtil.parseLong(autobus.getnPostiInPiedi()));
			dto.setnPostiRiservati(NumberUtil.parseLong(autobus.getnPostiRiservati()));
			dto.setnPostiSedere(NumberUtil.parseLong(autobus.getnPostiSedere()));
			dto.setnTarga(autobus.getTarga() != null ? autobus.getTarga().toUpperCase().replaceAll("\\s+", "") : null);
			dto.setnTelaio(autobus.getTelaio().toUpperCase());
			dto.setNumeroPorte(autobus.getNumeroPorte());
			dto.setPostiCarrozzina(NumberUtil.parseLong(autobus.getPostiCarrozzina()));
			dto.setPrezzoTotAcquisto(autobus.getPrezzoTotAcquisto());
			dto.setOmologazioneDirettivaEuropea(autobus.getOmologazioneDirettivaEuropea());

			RebusDTipologiaDimensDTO tipologiaDimensionale = utilityService
					.getTipologiaDimensionale(autobus.getIdTipoAllestimento(), autobus.getLunghezza());
			Long progrTipologiaDimensionale = (tipologiaDimensionale != null
					&& tipologiaDimensionale.getProgrTipoDimens() != null) ? tipologiaDimensionale.getProgrTipoDimens()
							: 0L;
			dto.setProgrTipoDimens(progrTipologiaDimensionale);
			dto.setDataUltimaRevisione(autobus.getDataUltimaRevisione());
			/*
			 * PREV INC Long idAltriDispPrevInc
			 * = autobus.getIdAltriDispositiviPrevInc(); if (idAltriDispPrevInc != null)
			 * dto.setAltriDispositiviPrevenzInc(idAltriDispPrevInc.toString());
			 */

			// ELIMINO TUTTI QUELLI GIA PRESENTI
			RebusRVarautobusDpSelector example = new RebusRVarautobusDpSelector();
			example.createCriteria().andIdVariazAutobusEqualTo(autobus.getId());
			rebusRVarautobusDpDAO.deleteByExample(example);
			// INSERISCO QUELLI NUOVI
			if (autobus.getDispositiviPrevIncidenti() != null && !autobus.getDispositiviPrevIncidenti().isEmpty()) {
				for (Long id : autobus.getDispositiviPrevIncidenti()) {
					RebusRVarautobusDpKey record1 = new RebusRVarautobusDpKey();
					record1.setIdDispositivo(id);
					record1.setIdVariazAutobus(autobus.getId());
					rebusRVarautobusDpDAO.insert(record1);
				}
			}
			// AGGIORNO DATA ALIENAZIONE
			RebusRAziendaAutobusSelector selBus = new RebusRAziendaAutobusSelector();
			selBus.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio())
					.andFkAziendaEqualTo(autobus.getIdAzienda());
			RebusRAziendaAutobusDTO rebusRAziendaAutobusDTO = rebusRAziendaAutobusDAO.selectByExample(selBus).get(0);
			rebusRAziendaAutobusDTO.setDataAlienazione(autobus.getDataAlienazione());
			rebusRAziendaAutobusDAO.updateByPrimaryKey(rebusRAziendaAutobusDTO);

			RebusRDocVariazAutobusKey key = new RebusRDocVariazAutobusKey();
			List<DocumentoVO> elencoDocumento = documentoService.elencoDocumento(new Long(1));
			if (!autobus.getDocumentiAutobus().isEmpty()) {
				List<RebusRDocVariazAutobusDTO> documentList = new ArrayList<RebusRDocVariazAutobusDTO>();
				for (DocVariazAutobusVO doc : autobus.getDocumentiAutobus()) {
					// E' STATO INSERITO UN NUOVO DOCUMENTO OOPURE E' STATO SOSTITUITO
					if (doc.getIdVariazAutobus() == null && doc.getDocumento() != null) {
						// ELIMINO IL VECCHIO DOCUMENTO
						try {
							ObjectMapper objectMapper = new ObjectMapper();
							key.setIdTipoDocumento(doc.getIdTipoDocumento());
							key.setIdVariazAutobus(autobus.getId());
							rebusRDocVariazAutobusDAO.deleteByPrimaryKey(key);
							RebusRDocVariazAutobusDTO rebusRDocVarAutobus = new RebusRDocVariazAutobusDTO();
							rebusRDocVarAutobus.setIdVariazAutobus(autobus.getId());
							rebusRDocVarAutobus.setIdTipoDocumento(doc.getIdTipoDocumento());
							rebusRDocVarAutobus.setDocumento(objectMapper.readValue(doc.getDocumento(), byte[].class));
							rebusRDocVarAutobus.setNomeFile(doc.getNomeFile());
							rebusRDocVarAutobus.setDataCaricamento(new Date());
							rebusRDocVarAutobus.setNote(doc.getNote());
							rebusRDocVarAutobus.setFkUtente(userInfo.getIdUtente());
							documentList.add(rebusRDocVarAutobus);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (!documentList.isEmpty()) {
					try {
						// INSERT DELLA LISTA DEI DOCUMENTI
						autobusDAO.insertDocVariazAutobus(documentList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (DocumentoVO documentoVO : elencoDocumento) {
					// ELIMINO I DOCUMENTI CHE NON SONO PRESENTI NELLA LISTA
					// TRAMITE L'ID TIPO DOCUMENTO
					Long check = 0L;
					check = documentoVO.getIdTipoDocumento();
					for (DocVariazAutobusVO doc : autobus.getDocumentiAutobus()) {
						if (documentoVO.getIdTipoDocumento().equals(doc.getIdTipoDocumento())) {
							check = 0L;
							break;
						}
					}
					if (!check.equals(0L)) {
						key.setIdTipoDocumento(check);
						key.setIdVariazAutobus(autobus.getId());
						rebusRDocVariazAutobusDAO.deleteByPrimaryKey(key);
					}
				}
			} else {
				RebusRDocVariazAutobusSelector rebusRDocVarAutobusSelector = new RebusRDocVariazAutobusSelector();
				rebusRDocVarAutobusSelector.createCriteria().andIdVariazAutobusEqualTo(autobus.getId());
				rebusRDocVariazAutobusDAO.deleteByExample(rebusRDocVarAutobusSelector);
			}

			boolean containRecordVerificatoAMP = false;
			RebusTStoriaVariazAutobusSelector storiaVarAutobusSel = new RebusTStoriaVariazAutobusSelector();
			storiaVarAutobusSel.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio());
			List<RebusTStoriaVariazAutobusDTO> storiaVarAutobusList = rebusTStoriaVariazAutobusDAO
					.selectByExample(storiaVarAutobusSel);
			for (RebusTStoriaVariazAutobusDTO el : storiaVarAutobusList) {
				if (Constants.FLAG_SI.equalsIgnoreCase(el.getFlgVerificatoAmp())) {
					containRecordVerificatoAMP = true;
					break;
				}
			}

			// se ho un ruolo azienda
			if (!SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP)) {
				/*
				 * mandi il msg di tipo 2 quando stai salvando un record con
				 * FLG_VERIFICATO_AZIENDA = 'S', ed il record originale era
				 * FLG_VERIFICATO_AZIENDA = 'N'
				 */
				if (msgTipo2) {
					inviaMessaggio(autobus, dto.getIdVariazAutobus(), null, Constants.TIPO_MESSAGGIO_2, ora,
							utenteDestinatario);
				} else {

					// se verificato Azienda e a S e verificato AMP != null
					// invio messaggio tipo 1
					if ((dto.getFlgVerificatoAzienda().equals("S") && isModificato
							&& (Constants.FLAG_SI.equalsIgnoreCase(dto.getFlgVerificatoAmp())
									|| Constants.FLAG_NO.equalsIgnoreCase(dto.getFlgVerificatoAmp())))
							|| (isModificato && containRecordVerificatoAMP
									&& StringUtils.isEmpty(dto.getFlgVerificatoAmp()))) {

						inviaMessaggio(autobus, dto.getIdVariazAutobus(), fkStoriaVariazAutobus,
								Constants.TIPO_MESSAGGIO_1, ora, utenteDestinatario);
						if (Constants.FLAG_SI.equalsIgnoreCase(dto.getFlgVerificatoAmp())) {
							// se l'azienda ha modificato e verificato i dati e
							// il flg verifica AMP era a S resetto il flag
							// verifica AMP
							dto.setFlgVerificatoAmp(Constants.FLAG_UNDEFINED);
						}
					}

				}
			} else {
				// se ho ruolo AMP
				if (SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP)) {
					// invio messaggio tipo 2 verfica AMP
					if (Constants.FLAG_SI.equalsIgnoreCase(autobus.getFlagVerificaAmp())) {
						inviaMessaggio(autobus, dto.getIdVariazAutobus(), null, Constants.TIPO_MESSAGGIO_2, ora,
								utenteDestinatario);
					}
					// invio messaggio tipo 3 verifica riufituata AMP
					else if (Constants.FLAG_NO.equalsIgnoreCase(autobus.getFlagVerificaAmp())) {
						inviaMessaggio(autobus, dto.getIdVariazAutobus(), null, Constants.TIPO_MESSAGGIO_3, ora,
								utenteDestinatario);
					}

				}
			}

			rebusTVariazAutobusDAO.updateByExample(dto, selVa);
		}
	}

	private boolean isModificatoAutobus(AutobusVO autobus, RebusTVariazAutobusDTO dto) {

		RebusDTipologiaDimensDTO tipologiaDimensionale = utilityService
				.getTipologiaDimensionale(autobus.getIdTipoAllestimento(), autobus.getLunghezza());
		Long progrTipologiaDimensionale = (tipologiaDimensionale != null
				&& tipologiaDimensionale.getProgrTipoDimens() != null) ? tipologiaDimensionale.getProgrTipoDimens()
						: 0L;

		return !(StringUtil.equalsIgnoreCase(dto.getAltraAlimentazione(), autobus.getAltraAlimentazione())
				&& StringUtil.equalsIgnoreCase(dto.getCaratteristicheParticolari(),
						autobus.getCaratteristicheParticolari())
				&& StringUtil.equalsIgnoreCase(dto.getAltriDispositiviPrevenzInc(),
						autobus.getAltriDispositiviPrevenzInc())
				&& NumberUtil.equals(dto.getAnnoSostProg(), autobus.getAnnoSostProg())
				&& NumberUtil.equals(dto.getContributoPubblicoAcquisto(), autobus.getContributoPubblicoAcquisto())
				&& DateUtils.equals(dto.getDataUltimaImmatricolazione(), autobus.getDataUltimaImmatricolazione())
				&& NumberUtil.equals(dto.getFkClasseAmbientaleEuro(), autobus.getIdClasseAmbientale())
				&& StringUtil.equalsIgnoreCase(dto.getFlgAvm(), autobus.getFlgAvm())
				&& StringUtil.equalsIgnoreCase(dto.getFlgVeicoloAssicurato(), autobus.getFlgVeicoloAssicurato())
				&& StringUtil.equalsIgnoreCase(dto.getFlgAlienato(), autobus.getFlagAlienato())
				&& NumberUtil.equals(dto.getFkClasseVeicolo(), autobus.getIdClasseVeicolo())
				&& NumberUtil.equals(dto.getFkDeposito(), autobus.getIdDeposito())
				&& NumberUtil.equals(dto.getFkDotazioneDisabili(), autobus.getIdDotazioneDisabili())
				&& NumberUtil.equals(dto.getFkImpiantiAudio(), autobus.getIdImpiantiAudio())
				&& NumberUtil.equals(dto.getFkClasseVeicolo(), autobus.getIdCategoriaVeicolo())
				&& NumberUtil.equals(dto.getFkImpiantiVisivi(), autobus.getIdImpiantiVisivi())
				&& NumberUtil.equals(dto.getFkProprietaLeasing(), autobus.getIdProprietaLeasing())
				&& NumberUtil.equals(dto.getFkTipoAlimentazione(), autobus.getIdTipoAlimentazione())
				&& NumberUtil.equals(dto.getFkTipoAllestimento(), autobus.getIdTipoAllestimento())
				&& NumberUtil.equals(dto.getFkTipoImmatricolazione(), autobus.getIdTipoImmatricolazione())
				&& StringUtil.equalsIgnoreCase(dto.getFlgCabinaGuidaIsolata(), autobus.getFlgCabinaGuidaIsolata())
				&& StringUtil.equalsIgnoreCase(dto.getFlgContapasseggeri(), autobus.getFlgContapasseggeri())
				&& StringUtil.equalsIgnoreCase(dto.getFlgConteggiatoMiv(), autobus.getFlgConteggiatoMiv())
				&& StringUtil.equalsIgnoreCase(dto.getFlgDuePiani(), autobus.getFlgDuePiani())
				&& StringUtil.equalsIgnoreCase(dto.getFlgImpiantoCondizionamento(),
						autobus.getFlgImpiantoCondizionamento())
				&& StringUtil.equalsIgnoreCase(dto.getFlgOtx(), autobus.getFlgOtx())
				&& StringUtil.equalsIgnoreCase(dto.getFlgRichiestaContr(), autobus.getFlgRichiestaContr())
				&& StringUtil.equalsIgnoreCase(dto.getFlgRilevatoreBip(), autobus.getFlgRilevatoreBip())
				&& StringUtil.equalsIgnoreCase(dto.getFlgSnodato(), autobus.getFlgSnodato())
				&& StringUtil.equalsIgnoreCase(dto.getFlgFiltroFap(), autobus.getFlgFiltroFap())
				&& StringUtil.equalsIgnoreCase(dto.getMotivazione(), autobus.getMotivazione())
				&& StringUtil.equalsIgnoreCase(dto.getFlgVerificatoAzienda(), autobus.getFlagVerificaAzienda())
				&& NumberUtil.equals(dto.getLunghezza(), autobus.getLunghezza())
				&& StringUtil.equalsIgnoreCase(dto.getNotaRiservataAmp(), autobus.getNotaRiservataAmp())
				&& NumberUtil.equals(dto.getnPostiInPiedi(), NumberUtil.parseLong(autobus.getnPostiInPiedi()))
				&& NumberUtil.equals(dto.getnPostiRiservati(), NumberUtil.parseLong(autobus.getnPostiRiservati()))
				&& NumberUtil.equals(dto.getnPostiSedere(), NumberUtil.parseLong(autobus.getnPostiSedere()))
				&& StringUtil.equalsIgnoreCase(dto.getnTarga(), autobus.getTarga())
				&& StringUtil.equalsIgnoreCase(dto.getnTelaio(), autobus.getTelaio())
				&& StringUtil.equalsIgnoreCase(dto.getnMatricolaAziendale(), autobus.getMatricola())
				&& NumberUtil.equals(dto.getNumeroPorte(), autobus.getNumeroPorte())
				&& NumberUtil.equals(dto.getPostiCarrozzina(), NumberUtil.parseLong(autobus.getPostiCarrozzina()))
				&& NumberUtil.equals(dto.getPrezzoTotAcquisto(), autobus.getPrezzoTotAcquisto())
				&& StringUtil.equalsIgnoreCase(dto.getOmologazioneDirettivaEuropea(),
						autobus.getOmologazioneDirettivaEuropea())
				&& NumberUtil.equals(dto.getProgrTipoDimens(), progrTipologiaDimensionale)
				&& DateUtils.equals(dto.getDataUltimaRevisione(), autobus.getDataUltimaRevisione()));

	}

	private void inviaMessaggio(AutobusVO autobus, Long fkVariazAutobus, Long fkStoriaVariazAutobus, Long tipoMessaggio,
			String ora, Long utenteDestinatario) {
		MessaggioVo input = new MessaggioVo();

		/*
		 * Stringa composta da: $$TIMESTAMP [spacer] [tipo_messaggio] (15 blanks)
		 * "targa" (15 blanks) [targa] (15 blanks) "di" (15 blanks) [azienda] dove i
		 * dati variabili, indicati tra parentesi quadre, sono: $$TIMESTAMP Segnaposto
		 * che verra valorizzato dall applicativo a seconda del tipo di messaggio
		 * [spacer] (28 blanks) (Var. 1.a) o (20 blanks) (Var. 1.b targa Campo N_TARGA
		 * del record corrente di REBUS_T_VARIAZ_AUTOBUS oggetto di modifica (Var. 1.a)
		 * o di verifica (Var. 1.b)
		 * 
		 * azienda Campo DENOMINAZIONE di REBUS_T_AZIENDE legato da FK_AZIENDA al record
		 * corrente di REBUS_T_VARIAZ_AUTOBUS oggetto di modifica (Var. 1.a) o di
		 * verifica (Var. 1.b) $$timestamo + 10 blanks + <descMessaggio> +
		 * 35-lunghezza(<descMessaggio>) blanks + "targa" + 10 blanks + <targa> + 10
		 * blanks + "di/da parte di" + 15/6 blanks + <alias> "di/da parte di" + 15/6
		 * blanks significa che verranno utilizzati 15 blanks quando viene scritto "di"
		 * (cioe nei tipi messaggio con ID IN (1,2), 6 quando viene scritto
		 * "da parte di" (ID msg = 3)
		 * 
		 */
		final String blanks10 = "          ";// 10 blanks
		final String blanks5 = "     ";// 5 blanks
		StringBuilder messaggio = new StringBuilder();

		messaggio.append("$$TIMESTAMP");
		messaggio.append(blanks10);
		String descrizione = rebusDTipoMessaggioDAO.selectByPrimaryKey(tipoMessaggio).getDescrizione();
		messaggio.append(descrizione);

		if (tipoMessaggio == 1 || tipoMessaggio == 2 || tipoMessaggio == 3) {
			for (int i = 0; i < 35 - descrizione.trim().length(); i++) {
				messaggio.append(" ");
			}
			messaggio.append("targa");
			messaggio.append(blanks10);
			messaggio.append(autobus.getTarga());
			messaggio.append(blanks10);
			if (SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP)) {
				messaggio.append("da parte di");
				messaggio.append(blanks5 + " ");// blanks6
				messaggio.append("AMP");
			} else {
				messaggio.append("di");
				messaggio.append(blanks10 + blanks5);
				if (autobus.getIdAzienda() != null) {
					RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
					rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(autobus.getIdAzienda());
					List<RebusTAziendeDTO> aziendeDTO = rebusTaziendaDAO.selectByExample(rebusTAziendeSelector);
					if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
						messaggio.append(aziendeDTO.get(0).getDenominazione());
					}
				}
			}
		}
		input.setMessaggio(messaggio.toString());

		input.setFkTipoMessaggio(tipoMessaggio);
		input.setFkVariazAutobus(fkVariazAutobus);
		input.setFkStoriaVariazAutobus(fkStoriaVariazAutobus);
		input.setFkUtenteDestinatario(utenteDestinatario);

		messaggiService.inserisciMessaggio(input);
	}

	@Override
	public AutobusVO dettaglioAutobus(Long id, String action) {
		if (StringUtils.isEmpty(action) || action.equals(Action.VIEW))
			SecurityUtils.assertAutorizzazioni(AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS);
		else if (action.equals(Action.EDIT.value))
			SecurityUtils.assertMultipleAutorizzazioni(AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.MODIFICA_BUS);

		VAutobusSelector selector = new VAutobusSelector();
		selector.createCriteria().andIdVariazAutobusEqualTo(id);
		List<VAutobusDTO> dettagli = vAutobusDAO.selectByExample(selector);

		if (dettagli != null && !dettagli.isEmpty()) {
			AutobusVO autobus = dettaglioMapper.mapDTOtoVO(dettagli.get(0));
			if (autobus.getIdAzienda() != null) {
				RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
				rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(autobus.getIdAzienda());
				List<RebusTAziendeDTO> aziendeDTO = rebusTaziendaDAO.selectByExample(rebusTAziendeSelector);
				if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
					autobus.setAzienda(aziendeDTO.get(0).getDenominazione() + " "
							+ StringUtils.trimToEmpty(aziendeDTO.get(0).getNaturaGiuridica()));
				}
			}

			List<MisurazioneDTO> mis = misurazioneDAO.selectByTelaio(autobus.getPrimoTelaio());

			List<CampagnaVO> campagna = new ArrayList<>();
			for (MisurazioneDTO c : mis) {
				campagna.add(dozerMapper.map(c, CampagnaVO.class));
			}
			autobus.setCampagna(campagna);
			List<PortabiciVO> portabici = new ArrayList<>();
			List<EmissioniVO> emissioni = new ArrayList<>();
			if (mis != null && mis.size() > 0) {
				for (MisurazioneDTO m : mis) {

					if (m.getIdMisurazione() != null) {

						if (m.getIdTipoMonitoraggio() == 1) {

							RebusmTMisPortabiciSelector portaSelector = new RebusmTMisPortabiciSelector();
							portaSelector.createCriteria().andIdMisurazioneEqualTo(m.getIdMisurazione());

							List<RebusmTMisPortabiciDTO> rebusmTMisPortabiciDTOs = rebusmTMisPortabiciDAO
									.selectByExample(portaSelector);
							if (rebusmTMisPortabiciDTOs != null && rebusmTMisPortabiciDTOs.size() > 0) {
								for (RebusmTMisPortabiciDTO sd : rebusmTMisPortabiciDTOs) {
									portabici.add(dozerMapper.map(sd, PortabiciVO.class));
								}
							}
							autobus.setPortabiciList(portabici);
						}

						if (m.getIdTipoMonitoraggio() == 2) {

							RebusmTMisEmissioniSelector emissSelector = new RebusmTMisEmissioniSelector();
							emissSelector.createCriteria().andIdMisurazioneEqualTo(m.getIdMisurazione());

							List<RebusmTMisEmissioniDTO> rebusmTMisEmissioniDTOs = rebusmTMisEmissioniDAO
									.selectByExample(emissSelector);
							if (rebusmTMisEmissioniDTOs != null && rebusmTMisEmissioniDTOs.size() > 0) {
								for (RebusmTMisEmissioniDTO sd : rebusmTMisEmissioniDTOs) {
									RebusDTipoAllestimentoDTO descEmissione = rebusDTipoAllestimentoDAO
											.selectByPrimaryKey(sd.getIdTipoAllestimento());
									emissioni.add(dozerMapper.map(sd, EmissioniVO.class));
								}
							}
							autobus.setEmissioni(emissioni);

						}

					}
				}
			}

			autobus.setDaImmatricolare(autobus.getTarga() == null ? true : false);

			if (autobus.getIdTipoAllestimento() != null) {
				autobus.setTipoAllestimento(
						rebusDTipoAllestimentoDAO.selectByPrimaryKey(autobus.getIdTipoAllestimento()).getDescrizione());
			}
			if (autobus.getIdImpiantiAudio() != null) {
				autobus.setImpiantiAudio(
						rebusDImpiantiAudioDAO.selectByPrimaryKey(autobus.getIdImpiantiAudio()).getDescrizione());
			}
			if (autobus.getIdImpiantiVisivi() != null) {
				autobus.setImpiantiVisivi(
						rebusDImpiantiVisiviDAO.selectByPrimaryKey(autobus.getIdImpiantiVisivi()).getDescrizione());
			}
			if (autobus.getIdClasseVeicolo() != null) {
				autobus.setClasseVeicolo(
						rebusDClasseVeicoloDAO.selectByPrimaryKey(autobus.getIdClasseVeicolo()).getDescrizione());
			}
			if (autobus.getIdTipoImmatricolazione() != null) {
				autobus.setTipoImmatricolazione(rebusDTipoImmatricolDAO
						.selectByPrimaryKey(autobus.getIdTipoImmatricolazione()).getDescrizione());
			}
			if (autobus.getIdTipoAlimentazione() != null) {
				autobus.setTipoAlimentazione(rebusDTipoAlimentazioneDAO
						.selectByPrimaryKey(autobus.getIdTipoAlimentazione()).getDescrizione());
			}
			if (autobus.getIdClasseAmbientale() != null) {
				autobus.setClasseAmbientale(
						rebusDClasseAmbEuroDAO.selectByPrimaryKey(autobus.getIdClasseAmbientale()).getDescrizione());
			}
			if (autobus.getIdProprietaLeasing() != null) {
				autobus.setProprietaLeasing(
						rebusDProprietaDAO.selectByPrimaryKey(autobus.getIdProprietaLeasing()).getDescrizione());
			}
			if (autobus.getIdDotazioneDisabili() != null) {
				autobus.setDotazioneDisabili(rebusDDotazioneDisabiliDAO
						.selectByPrimaryKey(autobus.getIdDotazioneDisabili()).getDescrizione());
			}

			if (autobus.getIdTipoAllestimento() != null && autobus.getProgrTipoDimens() != null) {
				RebusDTipologiaDimensSelector sel = new RebusDTipologiaDimensSelector();
				RebusDTipologiaDimensSelector.Criteria criteria = sel.createCriteria();
				criteria.andIdTipoAllestimentoEqualTo(autobus.getIdTipoAllestimento());
				criteria.andProgrTipoDimensEqualTo(Long.parseLong(autobus.getProgrTipoDimens()));
				autobus.setTipologiaDimensionale(
						rebusDTipologiaDimensDAO.selectByExample(sel).get(0).getTipologiaDimens());
			}

			RebusTVariazAutobusDTO dtoBus = rebusTVariazAutobusDAO.selectByPrimaryKey(autobus.getId());
			autobus.setNotaRiservataAmp(dtoBus.getNotaRiservataAmp());
			autobus.setNotaRiservataAzienda(dtoBus.getNotaRiservataAzienda());
			autobus.setNotaRiservataRp(dtoBus.getNotaRiservataRp());
			autobus.setFlagVerificaAzienda(dtoBus.getFlgVerificatoAzienda());
			autobus.setFlagVerificaAmp(dtoBus.getFlgVerificatoAmp());

			autobus.setFkPortabici(dtoBus.getFkPortabici());
			autobus.setFkSistemaVideosorveglianza(dtoBus.getFkSistemaVideosorveglianza());
			autobus.setFkSistemaLocalizzazione(dtoBus.getFkSistemaLocalizzazione());

			autobus.setFlgBipCablato(dtoBus.getFlgBipCablato());
			autobus.setFlgContapasseggeriIntegrato(dtoBus.getFlgContapasseggeriIntegrato());
			autobus.setFlgSistemiProtezioneAutista(dtoBus.getFlgSistemiProtezioneAutista());
			autobus.setAltriAllestimenti(dtoBus.getAltriAllestimenti());
			autobus.setFlgContribuzione(dtoBus.getFlgContribuzione());

			autobus.setMotivazione(dtoBus.getMotivazione());

			autobus.setAnnoSostProg(dtoBus.getAnnoSostProg() != null ? dtoBus.getAnnoSostProg() : null);

			VAutobusSmallSelector selV = new VAutobusSmallSelector();
			selV.createCriteria().andIdVariazAutobusEqualTo(autobus.getId());
			VAutobusSmallDTO dtoV = vAutobusSmallDAO.selectByExample(selV).get(0);
			autobus.setContributo(dtoV.getContribuito());

			RebusRVarautobusDpSelector seldp = new RebusRVarautobusDpSelector();
			seldp.createCriteria().andIdVariazAutobusEqualTo(autobus.getId());

			List<RebusRVarautobusDpKey> res = rebusRVarautobusDpDAO.selectByExample(seldp);
			if (res != null) {
				String dispositivi = "";
				ArrayList<Long> ids = new ArrayList<>();
				for (RebusRVarautobusDpKey item : res) {
					dispositivi += rebusDDispositiviPrevenzDAO.selectByPrimaryKey(item.getIdDispositivo())
							.getDescrizione() + ", ";
					ids.add(item.getIdDispositivo());
				}

				if (!dispositivi.equals("")) {
					dispositivi = dispositivi.substring(0, dispositivi.length() - 2);
				}
				autobus.setDispositiviPrevIncidenti(ids);
				autobus.setDispositiviPrevenzInc(dispositivi);
			}

			RebusRAziendaAutobusSelector selBus = new RebusRAziendaAutobusSelector();
			selBus.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio());
			selBus.createCriteria().andFkAziendaEqualTo(autobus.getIdAzienda());
			Date dataAlienazione = autobus.getDataAlienazione();
			if (dataAlienazione != null)
				autobus.setDataAlienazione(dataAlienazione);

			if ("S".equals(dtoBus.getFlgAlienato()) || dataAlienazione != null) {
				autobus.setFlagAlienato("S");
			} else {
				autobus.setFlagAlienato("N");
			}
			if ("S".equals(dtoBus.getFlgRichiestaContr())) {
				autobus.setFlgRichiestaContr("S");
			} else {
				autobus.setFlgRichiestaContr("N");
			}

			// cronologia proprieta
			String primoTelaio = rebusRAziendaAutobusDAO.selectByExample(selBus).get(0).getPrimoTelaio();
			autobus.setCronologia(cronologiaDAO.getCronologia(primoTelaio));
			autobus.setProcedimenti(procedimentiDAO.getProcByIdVa(id));
			String contributo = dtoBus.getContributoPubblicoAcquisto() != null
					&& !dtoBus.getContributoPubblicoAcquisto().equals(0.0) ? "S" : "N";
			autobus.setContributo(contributo);

			RebusRDocVariazAutobusSelector selDocBus = new RebusRDocVariazAutobusSelector();
			selDocBus.createCriteria().andIdVariazAutobusEqualTo(autobus.getId());
			List<DocVariazAutobusVO> listDoc = autobusDAO.getDocVariazAutobusForInfo(id);
			if (listDoc == null || listDoc.isEmpty()) {
				autobus.setIsFileUpload("noFile");
				// In maniera brutalmente sporca, setto a mano l'idDocumento
				// poiche ad oggi nel db c'e solo un idDocumento.
				autobus.setIdDocumento(new Long(1));
			} else {
				autobus.setDocumentiAutobus(listDoc);
				autobus.setIsFileUpload("isFile");
			}

			primoTelaio = autobus.getPrimoTelaio();
			int idTipoProcedimento = 7; // contribuzione
			Long idProcedimento = contribuzioneDAO.getIdProcedimento(primoTelaio, idTipoProcedimento);
			autobus.setIdProcedimento(idProcedimento);
			return autobus;
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long inserisciAutobus(AutobusDettagliatoVO autobus) {
		if (autobus == null) {
			throw new InvalidParameterException("Autobus non valorizzato");
		}

		if (validationService.existsTargaAndPrimoTelaio(autobus)) {
			throw new ErroreGestitoException("Targa o Primo Telaio gia presente in archivio!", "TGP");
		}

		if (autobus.getAnnoSostProg() != null) {
			if (validationService.correctYear(autobus)) {
				throw new ErroreGestitoException(
						"Data Prima Immatricolazione e Anno sostituzione programmata non congrui!", "CY");
			}
		}
		for (DocVariazAutobusVO doc : autobus.getDocumentiAutobus()) {
			if (!doc.getNomeFile().contains(".pdf") && doc != null) {
				throw new ErroreGestitoException(" Formato file del documento allegato non consentito! ", "TFNC");
			}
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		RebusDTipologiaDimensDTO tipologiaDimensionale = utilityService
				.getTipologiaDimensionale(autobus.getIdTipoAllestimento(), autobus.getLunghezza());
		Long progrTipologiaDimensionale = (tipologiaDimensionale != null
				&& tipologiaDimensionale.getProgrTipoDimens() != null) ? tipologiaDimensionale.getProgrTipoDimens()
						: 0L;
		Long anno = (StringUtils.isNotEmpty(autobus.getAnnoSostProg())) ? Long.parseLong(autobus.getAnnoSostProg())
				: null;

		RebusTAutobusDTO record = new RebusTAutobusDTO();
		record.setPrimoTelaio(autobus.getPrimoTelaio().toUpperCase());
		record.setFkBando(null);
		record.setDataPrimaImmatricolazione(autobus.getDataPrimaImmatricolazione());
		record.setMarca(autobus.getMarca());
		record.setModello(autobus.getModello());
		rebusTAutobusDAO.insert(record);

		RebusRAziendaAutobusDTO rebusRAzienda = new RebusRAziendaAutobusDTO();
		rebusRAzienda.setFkAzienda(autobus.getIdAzienda());
		rebusRAzienda.setPrimoTelaio(autobus.getPrimoTelaio().toUpperCase());
		rebusRAzienda.setDataAggiornamento(new Date());
		rebusRAzienda.setDataAlienazione(autobus.getDataAlienazione());
		rebusRAziendaAutobusDAO.insert(rebusRAzienda);

		RebusTVariazAutobusDTO rebusTVarAutobus = new RebusTVariazAutobusDTO();
		rebusTVarAutobus.setPrimoTelaio(autobus.getPrimoTelaio().toUpperCase());
		rebusTVarAutobus.setnTelaio(autobus.getTelaio().toUpperCase());
		rebusTVarAutobus
				.setnTarga(autobus.getTarga() != null ? autobus.getTarga().toUpperCase().replaceAll("\\s+", "") : null);
		rebusTVarAutobus.setDataUltimaImmatricolazione(autobus.getDataUltimaImmatricolazione());
		rebusTVarAutobus.setDataInserimento(new Date());
		rebusTVarAutobus.setnMatricolaAziendale(autobus.getMatricola());
		rebusTVarAutobus.setFkTipoImmatricolazione(autobus.getIdTipoImmatricolazione());
		rebusTVarAutobus.setEnteAutorizzPrimaImm(autobus.getEnteAutorizzPrimaImm());
		rebusTVarAutobus.setOmologazioneDirettivaEuropea(autobus.getOmologazioneDirettivaEuropea());
		rebusTVarAutobus.setFkClasseAmbientaleEuro(autobus.getIdClasseAmbientale());
		rebusTVarAutobus.setFkTipoAllestimento(autobus.getIdTipoAllestimento());
		rebusTVarAutobus.setFkTipoAlimentazione(autobus.getIdTipoAlimentazione());
		rebusTVarAutobus.setAltraAlimentazione(autobus.getAltraAlimentazione());
		rebusTVarAutobus.setCaratteristicheParticolari(autobus.getCaratteristicheParticolari());
		rebusTVarAutobus.setFkClasseVeicolo(autobus.getIdClasseVeicolo());
		rebusTVarAutobus.setLunghezza(autobus.getLunghezza());

		rebusTVarAutobus.setFlgDuePiani(this.flagToString(autobus.getFlgDuePiani()));
		rebusTVarAutobus.setFlgSnodato(this.flagToString(autobus.getFlgSnodato()));
		rebusTVarAutobus.setFlgCabinaGuidaIsolata(autobus.getFlgCabinaGuidaIsolata() == null ? Constants.FLAG_NO
				: this.flagToString(autobus.getFlgCabinaGuidaIsolata()));

		rebusTVarAutobus.setNumeroPorte(autobus.getNumeroPorte());
		rebusTVarAutobus.setnPostiInPiedi(autobus.getnPostiInPiedi());
		rebusTVarAutobus.setnPostiSedere(autobus.getnPostiSedere());
		rebusTVarAutobus.setnPostiRiservati(autobus.getnPostiRiservati());
		rebusTVarAutobus.setPostiCarrozzina(autobus.getPostiCarrozzina());
		rebusTVarAutobus.setFkDotazioneDisabili(autobus.getIdDotazioneDisabili());
		rebusTVarAutobus.setFkImpiantiAudio(autobus.getIdImpiantiAudio());
		rebusTVarAutobus.setFkImpiantiVisivi(autobus.getIdImpiantiVisivi());
		rebusTVarAutobus.setAltriDispositiviPrevenzInc(autobus.getAltriDispositiviPrevenzInc());

		rebusTVarAutobus.setFlgImpiantoCondizionamento(this.flagToString(autobus.getFlgImpiantoCondizionamento()));
		rebusTVarAutobus.setFlgRilevatoreBip(autobus.getFlgRilevatoreBip() == null ? Constants.FLAG_NO
				: this.flagToString(autobus.getFlgRilevatoreBip()));
		rebusTVarAutobus.setFlgContapasseggeri(this.flagToString(autobus.getFlgContapasseggeri()));
		rebusTVarAutobus.setFlgOtx(this.flagToString(autobus.getFlgOtx()));
		rebusTVarAutobus.setFlgAvm(this.flagToString(autobus.getFlgAvm()));
		rebusTVarAutobus.setFlgFiltroFap(
				autobus.getFlgFiltroFap() == null ? Constants.FLAG_NO : this.flagToString(autobus.getFlgFiltroFap()));

		// FkDepositoPrevalent non è più il deposito prevalente ma quello scelto
		// dalla combo
		rebusTVarAutobus.setFkDeposito(autobus.getIdDeposito());
		rebusTVarAutobus.setFkProprietaLeasing(autobus.getIdProprietaLeasing());
		rebusTVarAutobus.setDataUltimaRevisione(autobus.getDataUltimaRevisione());
		rebusTVarAutobus.setPrezzoTotAcquisto(autobus.getPrezzoTotAcquisto());
		rebusTVarAutobus.setContributoPubblicoAcquisto(autobus.getContributoPubblicoAcquisto());
		rebusTVarAutobus.setProgrTipoDimens(progrTipologiaDimensionale);

		rebusTVarAutobus.setFlgVeicoloAssicurato(autobus.getFlgVeicoloAssicurato() == null ? Constants.FLAG_NO
				: this.flagToString(autobus.getFlgVeicoloAssicurato()));
		rebusTVarAutobus.setFlgConteggiatoMiv(autobus.getFlgConteggiatoMiv() == null ? Constants.FLAG_UNDEFINED
				: this.flagToString(autobus.getFlgConteggiatoMiv()));
		rebusTVarAutobus.setFlgRichiestaContr(autobus.getFlgRichiestaContr() == null ? Constants.FLAG_NO
				: this.flagToString(autobus.getFlgRichiestaContr()));
		rebusTVarAutobus.setFlgAlienato(autobus.getFlgAlienato() == null ? null : autobus.getFlgAlienato());
		rebusTVarAutobus.setAnnoSostProg(anno);
		rebusTVarAutobus.setFlgVerificatoAzienda(this.flagToString(autobus.getFlagVerificaAzienda()));
		rebusTVarAutobus.setFlgVerificatoAmp(autobus.getFlagVerificaAmp() == null ? Constants.FLAG_UNDEFINED
				: this.flagToString(autobus.getFlagVerificaAmp()));

		rebusTVarAutobus.setNote(autobus.getNote());
		rebusTVarAutobus.setNotaRiservataAzienda(autobus.getNotaRiservataAzienda());
		rebusTVarAutobus.setNotaRiservataAmp(autobus.getNotaRiservataAmp());
		rebusTVarAutobus.setDataAggiornamento(new Date());
		rebusTVarAutobus.setFkUtente(userInfo.getIdUtente());

		rebusTVarAutobus.setFkPortabici(autobus.getFkPortabici());
		rebusTVarAutobus.setFkSistemaVideosorveglianza(autobus.getFkSistemaVideosorveglianza());
		rebusTVarAutobus.setFkSistemaLocalizzazione(autobus.getFkSistemaLocalizzazione());
		rebusTVarAutobus.setFlgBipCablato(autobus.getFlgBipCablato());
		rebusTVarAutobus.setFlgContapasseggeriIntegrato(autobus.getFlgContapasseggeriIntegrato());
		rebusTVarAutobus.setFlgSistemiProtezioneAutista(autobus.getFlgSistemiProtezioneAutista());
		rebusTVarAutobus.setAltriAllestimenti(autobus.getAltriAllestimenti());
		rebusTVarAutobus.setFlgContribuzione(autobus.getFlgContribuzione());
		rebusTVarAutobus.setFkCategoriaVeicolo(autobus.getIdCategoriaVeicolo());
		rebusTVariazAutobusDAO.insert(rebusTVarAutobus);

		// se ho un ruolo azienda e flag verificato azienda e a S mando
		// messaggio di tipo 2 ad AMP
		if (!SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP)
				&& Constants.FLAG_SI.equals(rebusTVarAutobus.getFlgVerificatoAzienda())) {
			String ora = DateUtils.timeToString(new Date());

			AutobusVO autobusVO = new AutobusVO();
			autobusVO.setTarga(autobus.getTarga());
			autobusVO.setIdAzienda(autobus.getIdAzienda());

			inviaMessaggio(autobusVO, rebusTVarAutobus.getIdVariazAutobus(), null, Constants.TIPO_MESSAGGIO_2, ora,
					Constants.ID_UTENTE_DESTINATARIO_MESSAGGIO_12);
		}

		RebusRVarautobusDpKey rebusRVarAu = new RebusRVarautobusDpKey();
		rebusRVarAu.setIdVariazAutobus(rebusTVarAutobus.getIdVariazAutobus());
		List<Long> listaDispositivi = autobus.getIdAltriDispositiviPrevInc();
		for (Long idDispositivo : listaDispositivi) {
			rebusRVarAu.setIdDispositivo(idDispositivo);
			rebusRVarautobusDpDAO.insert(rebusRVarAu);
		}
		// Appoggio per recuperare idAutobus da passare al newDettaglioAutobus()
		Long idAutubus = rebusTVarAutobus.getIdVariazAutobus();

		RebusRDocVariazAutobusDTO rebusRDocVarAutobus = new RebusRDocVariazAutobusDTO();

		for (DocVariazAutobusVO doc : autobus.getDocumentiAutobus()) {
			if (doc != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				rebusRDocVarAutobus.setIdVariazAutobus(idAutubus);
				rebusRDocVarAutobus.setIdTipoDocumento(doc.getIdTipoDocumento());
				try {
					rebusRDocVarAutobus.setDocumento(objectMapper.readValue(doc.getDocumento(), byte[].class));
				} catch (IOException e) {
					e.printStackTrace();
				}
				rebusRDocVarAutobus.setNomeFile(doc.getNomeFile());
				rebusRDocVarAutobus.setDataCaricamento(new Date());
				rebusRDocVarAutobus.setNote(doc.getNote());
				rebusRDocVarAutobus.setFkUtente(userInfo.getIdUtente());
				rebusRDocVariazAutobusDAO.insert(rebusRDocVarAutobus);
			}
		}

		return rebusTVarAutobus.getIdVariazAutobus();
	}

	private String flagToString(Boolean flag) {
		return (flag == null) ? "U" : (flag) ? "S" : "N";
	}

	enum Action {
		EDIT("E"), VIEW("V");

		private String value;

		private Action(String value) {
			this.value = value;
		}

	}

	@Override
	public List<VeicoloVO> getVeicoliInserisci(Long idTipoProcedimento) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		return ricercaVeicoli(userInfo.getIdAzienda(), idTipoProcedimento, null);
	}

	@Override
	public List<VeicoloVO> getVeicoliModifica(Long idProcedimento, Long idTipoProcedimento) {
		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento)
				.andIdStatoIterEqualTo(new Long(10)); // BOZZA
		rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		RebuspTIterProcedimentoDTO iterDto = null;
		if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
			iterDto = rebuspTIterProcedimentoDTOs.get(0);
		}

		RebusRUtenteAzEnteSelector rebusRUtenteAzEnteSelector = new RebusRUtenteAzEnteSelector();
		rebusRUtenteAzEnteSelector.createCriteria().andIdRUtenteAzEnteEqualTo(iterDto.getIdUtenteSogGiurid());
		List<RebusRUtenteAzEnteDTO> rebusRUtenteAzEnteDTOs = rebusRUtenteAzEnteDAO
				.selectByExample(rebusRUtenteAzEnteSelector);
		Long idAzienda = null;
		if (rebusRUtenteAzEnteDTOs != null && rebusRUtenteAzEnteDTOs.size() > 0) {
			idAzienda = rebusRUtenteAzEnteDTOs.get(0).getFkAzienda() != null
					? rebusRUtenteAzEnteDTOs.get(0).getFkAzienda()
					: rebusRUtenteAzEnteDTOs.get(0).getFkEnte();
		}

		RebuspRProcVeicoloSelector rebuspRProcVeicoloSelector = new RebuspRProcVeicoloSelector();
		rebuspRProcVeicoloSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
		List<RebuspRProcVeicoloDTO> rebuspRProcVeicoloDTOs = rebuspRProcVeicoloDAO
				.selectByExample(rebuspRProcVeicoloSelector);
		List<Long> idVeicoliSelezionati = new ArrayList<>();
		for (RebuspRProcVeicoloDTO dto : rebuspRProcVeicoloDTOs) {
			RebusTVariazAutobusSelector rebusTVariazAutobusSelector = new RebusTVariazAutobusSelector();
			rebusTVariazAutobusSelector.createCriteria().andPrimoTelaioEqualTo(dto.getPrimoTelaio());
			List<RebusTVariazAutobusDTO> rebusTVariazAutobusDTOs = rebusTVariazAutobusDAO
					.selectByExample(rebusTVariazAutobusSelector);
			if (rebusTVariazAutobusDTOs != null && rebusTVariazAutobusDTOs.size() == 1) {
				idVeicoliSelezionati.add(rebusTVariazAutobusDTOs.get(0).getIdVariazAutobus());
			}
		}
		if (idVeicoliSelezionati.size() == 0) {
			idVeicoliSelezionati = null;
		}

		return ricercaVeicoli(idAzienda, idTipoProcedimento, idVeicoliSelezionati);
	}

	private List<VeicoloVO> ricercaVeicoli(Long idAzienda, Long idTipoProcedimento, List<Long> idVeicoliSelezionati) {
		List<VeicoloDTO> veicoloDTOs = ricercaDAO.getVeicoli(idAzienda, idTipoProcedimento, idVeicoliSelezionati);
		List<VeicoloVO> veicoloVOs = new ArrayList<>();
		for (VeicoloDTO dto : veicoloDTOs) {
			RebusRDocVariazAutobusSelector rebusRDocVariazAutobusSelector = new RebusRDocVariazAutobusSelector();
			rebusRDocVariazAutobusSelector.createCriteria().andIdVariazAutobusEqualTo(dto.getIdVariazAutobus());
			List<RebusRDocVariazAutobusDTO> rebusRDocVariazAutobusDTOs = rebusRDocVariazAutobusDAO
					.selectByExample(rebusRDocVariazAutobusSelector);
			List<AllegatoVeicoloVO> allegati = new ArrayList<>();
			for (RebusRDocVariazAutobusDTO dto2 : rebusRDocVariazAutobusDTOs) {
				allegati.add(allegatoVeicoloMapper.mapDTOtoVO(dto2));
			}
			VeicoloVO vo = veicoloMapper.mapDTOtoVO(dto);
			vo.setAllegati(allegati);
			veicoloVOs.add(vo);
		}
		return veicoloVOs;
	}

	@Override
	public TipologiaDimensioneVO tipologiaDimensione(Long idTipoAllestimento) {
		RebusDTipologiaDimensDTO tipologiaDimensioneDTO = ricercaDAO.getTipologiaDimensione(idTipoAllestimento);
		if (tipologiaDimensioneDTO == null) {
			throw new InvalidParameterException("id allestimento non valido");
		}
		TipologiaDimensioneVO tipologiaDimensioneVO = new TipologiaDimensioneVO();
		tipologiaDimensioneVO.setIdTipoAllestimento(idTipoAllestimento);
		tipologiaDimensioneVO.setLunghezzaMax(tipologiaDimensioneDTO.getLunghezzaMax());
		tipologiaDimensioneVO.setLunghezzaMin(tipologiaDimensioneDTO.getLunghezzaMin());
		return tipologiaDimensioneVO;
	}

	@Override
	public List<DepositoVO> getDepositi(Long idAzienda) {
		SirtplaRSogGiuridDepositoSelector sirtplaRSogGiuridDepositoSelector = new SirtplaRSogGiuridDepositoSelector();
		sirtplaRSogGiuridDepositoSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(idAzienda);
		List<SirtplaRSogGiuridDepositoDTO> sirtplaRSogGiuridDepositoDTOs = sirtplaRSogGiuridDepositoDAO
				.selectByExample(sirtplaRSogGiuridDepositoSelector);
		List<DepositoVO> depositi = new ArrayList<>();
		if (sirtplaRSogGiuridDepositoDTOs != null && sirtplaRSogGiuridDepositoDTOs.size() > 0) {
			for (SirtplaRSogGiuridDepositoDTO sd : sirtplaRSogGiuridDepositoDTOs) {
				depositi.add(depositoMapper.mapDTOtoVO(sirtplaTDepositoDAO.selectByPrimaryKey(sd.getIdDeposito())));
			}
		}
		Collections.sort(depositi);
		return depositi;
	}

	@Override
	public DepositoVO getDepositoById(Long idDeposito) {
		SirtplaTDepositoDTO deposito = null;

		if (idDeposito != null) {
			deposito = sirtplaTDepositoDAO.selectByPrimaryKey(idDeposito);
		}

		if (deposito != null) {
			return depositoMapper.mapDTOtoVO(deposito);
		}
		return new DepositoVO();
	}

	// prova
	@Override
	public List<MisurazioniVO> getMisurazioni(String primoTelaio) {

		List<MisurazioniVO> richiesta = new ArrayList<>();

		List<PortabiciVO> portabici = new ArrayList<>();
		List<EmissioniVO> emissioni = new ArrayList<>();

		List<MisurazioneDTO> mis = misurazioneDAO.selectByTelaio(primoTelaio);

		int i = 0;
		if (mis != null && mis.size() > 0) {
			for (MisurazioneDTO campagnaMapper : mis) {
				richiesta.add(dozerMapper.map(campagnaMapper, MisurazioniVO.class));

				if (campagnaMapper.getIdTipoMonitoraggio() == 1) {
					RebusmTMisPortabiciSelector portaSelector = new RebusmTMisPortabiciSelector();
					portaSelector.createCriteria().andIdMisurazioneEqualTo(campagnaMapper.getIdMisurazione());
					// REBUSM_T_MISS_PORTABICI
					List<RebusmTMisPortabiciDTO> rebusmTMisPortabiciDTOs = rebusmTMisPortabiciDAO
							.selectByExample(portaSelector);
					if (rebusmTMisPortabiciDTOs != null && rebusmTMisPortabiciDTOs.size() > 0) {
						for (RebusmTMisPortabiciDTO bici : rebusmTMisPortabiciDTOs) {
							portabici.add(dozerMapper.map(bici, PortabiciVO.class));
						}
					}
					richiesta.get(i).setBici(portabici);
				}

				if (campagnaMapper.getIdTipoMonitoraggio() == 2) {
					RebusmTMisEmissioniSelector emissioniSelector = new RebusmTMisEmissioniSelector();
					emissioniSelector.createCriteria().andIdMisurazioneEqualTo(campagnaMapper.getIdMisurazione());
					// REBUSM_T_MISS_EMISSIONI
					List<RebusmTMisEmissioniDTO> rebusmTMisEmissioniDTOs = rebusmTMisEmissioniDAO
							.selectByExample(emissioniSelector);
					if (rebusmTMisEmissioniDTOs != null && rebusmTMisEmissioniDTOs.size() > 0) {
						for (RebusmTMisEmissioniDTO emi : rebusmTMisEmissioniDTOs) {
							emissioni.add(dozerMapper.map(emi, EmissioniVO.class));
						}
					}
					richiesta.get(i).setEmissioni(emissioni);
				}

			}
			i++;

		}
		return richiesta;
	}

	@Override
	// flg_contribuzione settato a true nell'autobusDAO.xml
	public List<String> getAutobusForContribuzioneAzienda() {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		List<String> result = autobusDAO.getAutobusForContribuzioneAzienda(userInfo.getIdAzienda());
		return result;
	}

	@Override
	public List<PortabiciAutobusVO> getAllPortabiciForAutobus() {
		List<PortabiciAutobusVO> result = new ArrayList<PortabiciAutobusVO>();
		RebuscDPortabiciSelector sel = new RebuscDPortabiciSelector();
		List<RebuscDPortabiciDTO> portabiciAutobus = rebuscDPortabiciDAO.selectByExample(sel);
		for (RebuscDPortabiciDTO rebuscDPortabiciDTO : portabiciAutobus) {
			result.add(dozerMapper.map(rebuscDPortabiciDTO, PortabiciAutobusVO.class));
		}
		return result;
	}

	@Override
	public List<SistemaLocalizzazioneVO> getAllSistemaLocalizzazioneForAutobus() {
		List<SistemaLocalizzazioneVO> result = new ArrayList<SistemaLocalizzazioneVO>();
		RebuscDSistemaLocalizzazioneSelector sel = new RebuscDSistemaLocalizzazioneSelector();
		List<RebuscDSistemaLocalizzazioneDTO> sistemaLocalizzazione = rebuscDSistemaLocalizzazioneDAO
				.selectByExample(sel);
		for (RebuscDSistemaLocalizzazioneDTO rebuscDSistemaLocalizzazioneDTO : sistemaLocalizzazione) {
			result.add(dozerMapper.map(rebuscDSistemaLocalizzazioneDTO, SistemaLocalizzazioneVO.class));
		}
		return result;
	}

	@Override
	public List<SistemaVideosorveglianzaVO> getAllSistemaVideosorveglianzaForAutobus() {
		List<SistemaVideosorveglianzaVO> result = new ArrayList<SistemaVideosorveglianzaVO>();
		RebuscDSistemaVideosorveglianzaSelector sel = new RebuscDSistemaVideosorveglianzaSelector();
		List<RebuscDSistemaVideosorveglianzaDTO> sistemaVideosorveglianzaDTO = rebuscDSistemaVideosorveglianzaDAO
				.selectByExample(sel);
		for (RebuscDSistemaVideosorveglianzaDTO rebuscDSistemaVideosorveglianzaDTO : sistemaVideosorveglianzaDTO) {
			result.add(dozerMapper.map(rebuscDSistemaVideosorveglianzaDTO, SistemaVideosorveglianzaVO.class));
		}
		return result;
	}

	public String convertBooleanToString(Boolean value) {
		if (value == null) {
			return null;
		}
		String nuovoValore = null;
		if (value == true) {
			nuovoValore = "Si";
		} else if (value == false)
			nuovoValore = "No";
		return nuovoValore;
	}

	public Boolean convertStringToBoolean(String value) {
		if (value == null) {
			return null;
		}
		Boolean nuovoValore = null;
		if (value.equalsIgnoreCase("Si")) {
			nuovoValore = true;
		} else if (value.equalsIgnoreCase("No"))
			nuovoValore = false;
		return nuovoValore;
	}

	@Override
	public List<DocVariazAutobusVO> getDocVariazAutobusForInfo(Long idVa) {
		List<DocVariazAutobusVO> result = autobusDAO.getDocVariazAutobusForInfo(idVa);
		return result;
	}

}
