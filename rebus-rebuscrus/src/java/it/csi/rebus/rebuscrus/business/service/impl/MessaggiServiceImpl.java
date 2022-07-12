/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.CrudAutobusService;
import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.business.service.ProcedimentiService;
import it.csi.rebus.rebuscrus.integration.dao.RebusDCategoriaVeicoloDAO;
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
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoMessaggioSistemaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipologiaDimensDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRAziendaAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRStoriaVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRUtenteAzEnteDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAziendeDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTMessaggiDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTStoriaVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTipoProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.RicercaDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoMessaggioDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoMessaggioSistemaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRStoriaVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRStoriaVarautobusDpSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTMessaggiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTMessaggiSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTProcedimentoDTO;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.DateUtils;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.StringUtil;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.DepositoVO;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;
import it.csi.rebus.rebuscrus.vo.UtenteAzEnteVO;

@Component
public class MessaggiServiceImpl implements MessaggiService {

	@Autowired
	private RebusTMessaggiDAO rebusTMessaggiDAO;

	@Autowired
	private RebusDTipoMessaggioDAO rebusDTipoMessaggioDAO;

	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;
	
	@Autowired
	private RebusDTipologiaDimensDAO rebusDTipologiaDimensDAO;

	@Autowired
	private RebusTStoriaVariazAutobusDAO rebusTStoriaVariazAutobusDAO;

	@Autowired
	private RebusTAziendeDAO rebusTaziendaDAO;

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
	private RebusDTipoAlimentazioneDAO rebusDTipoAlimentazioneDAO;

	@Autowired
	private RebusDClasseAmbEuroDAO rebusDClasseAmbEuroDAO;

	@Autowired
	private RebusDProprietaDAO rebusDProprietaDAO;

	@Autowired
	private RebusDDotazioneDisabiliDAO rebusDDotazioneDisabiliDAO;

	@Autowired
	private RebusRStoriaVarautobusDpDAO rebusRStoriaVarautobusDpDAO;

	@Autowired
	private RebusDDispositiviPrevenzDAO rebusDDispositiviPrevenzDAO;

	@Autowired
	private RebusRVarautobusDpDAO rebusRVarautobusDpDAO;

	@Autowired
	private RicercaDAO ricercaDAO;

	@Autowired
	private RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO;

	@Autowired
	private RebuspDTipoProcedimentoDAO rebuspDTipoProcedimentoDAO;

	@Autowired
	private RebusDTipoMessaggioSistemaDAO rebusDTipoMessaggioSistemaDAO;

	@Autowired
	private RebusRAziendaAutobusDAO rebusRAziendaAutobusDAO;

	@Autowired
	private ProcedimentiService procedimentiService;

	@Autowired
	private CrudAutobusService crudAutobusService;

	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	@Autowired
	private RebusDCategoriaVeicoloDAO rebusDCategoriaVeicoloDAO;

	@Override
	public Long calcolaNumMessaggi() {

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		if (SecurityUtils.isUtenteServizioOGestoreDati()) {
			RebusTMessaggiSelector selector = new RebusTMessaggiSelector();
			selector.createCriteria().andFlgLettoEqualTo("N");
			return (long) rebusTMessaggiDAO.countByExample(selector);
		}

		return ricercaDAO.calcolaNumMessaggi(userInfo.getIdEnte(), userInfo.getIdAzienda());

	}

	@Override
	public List<RebusTMessaggiDTO> elencoMessaggi(Long idFilter) {

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		List<RebusTMessaggiDTO> messaggi = new ArrayList<>();
		// RebusTMessaggiSelector selector = new RebusTMessaggiSelector();
		// se sono utente servizi o gestore dati ritorno tutti i messaggi senza
		// filtri

		if (SecurityUtils.isUtenteServizioOGestoreDati()) {
			// selector.setOrderByClause("data_creazione desc");
			return ricercaDAO.elencoMessaggi(null, null, null, idFilter);
		}
		if (userInfo.getIdAzienda() != null) {
			messaggi = ricercaDAO.elencoMessaggi(null, userInfo.getIdAzienda(), userInfo.getIdUtente(), idFilter);
		} else if (userInfo.getIdEnte() != null) {
			messaggi = ricercaDAO.elencoMessaggi(userInfo.getIdEnte(), null, userInfo.getIdUtente(), idFilter);

		}

		return messaggi.stream().filter(new Predicate<RebusTMessaggiDTO>() {
			public boolean test(RebusTMessaggiDTO messaggio) {
				return messaggio.getFlgArchiviato() != null
						&& messaggio.getFlgArchiviato().equalsIgnoreCase(Constants.FLAG_NO);
			}
		}).collect(Collectors.toList());
	}

	@Override
	public void ripristinaNonLetto(Long idMessaggio) {

		if (idMessaggio != null) {
			RebusTMessaggiDTO messaggio = rebusTMessaggiDAO.selectByPrimaryKey(idMessaggio);
			messaggio.setFlgLetto(Constants.FLAG_NO);
			messaggio.setDataLettura(null);
			rebusTMessaggiDAO.updateByPrimaryKey(messaggio);

		}
	}

	@Override
	public void segnaComeLetto(Long idMessaggio) {

		if (idMessaggio != null) {

			RebusTMessaggiDTO messaggio = new RebusTMessaggiDTO();
			messaggio.setIdMessaggio(idMessaggio);
			messaggio.setFlgLetto(Constants.FLAG_SI);
			messaggio.setDataLettura(DateUtils.getDataSistema());

			rebusTMessaggiDAO.updateByPrimaryKeySelective(messaggio);

		}
	}

	@Override
	public MessaggioVo dettaglioMessaggio(Long idMessaggio) {

		MessaggioVo messaggio = new MessaggioVo();

		RebusTMessaggiDTO recordDto = rebusTMessaggiDAO.selectByPrimaryKey(idMessaggio);

		if (recordDto != null) {

			messaggio.setIdMessaggio(idMessaggio);
			messaggio.setDataCreazione(recordDto.getDataCreazione());
			messaggio.setDataLettura(recordDto.getDataLettura());
			messaggio.setFkStoriaVariazAutobus(recordDto.getFkStoriaVariazAutobus());
			messaggio.setFkTipoMessaggio(recordDto.getFkTipoMessaggio());
			messaggio.setFkUtenteMittente(recordDto.getFkUtenteMittente());
			messaggio.setFkVariazAutobus(recordDto.getFkVariazAutobus());
			messaggio.setFkStoriaVariazAutobusSucc(recordDto.getFkStoriaVariazAutobusSucc());
			messaggio.setFkProcedimento(recordDto.getFkProcedimento());
			messaggio.setFlgLetto(recordDto.getFlgLetto());
			messaggio.setMessaggio(recordDto.getMessaggio());
			messaggio.setFkUtenteDestinatario(recordDto.getFkUtenteDestinatario());
			messaggio.setFkTipoMessaggioSistema(recordDto.getFkTipoMessaggioSistema());
			RebusDTipoMessaggioDTO tipoMess = rebusDTipoMessaggioDAO.selectByPrimaryKey(recordDto.getFkTipoMessaggio());
			String descTipoMess = (tipoMess != null ? tipoMess.getDescrizione() : null);
			Long idContesto = tipoMess.getIdContesto();

			List<Long> tipiMessaggio = procedimentiService.getTipiMessaggio(idContesto);
			if (tipiMessaggio != null && tipiMessaggio.contains(recordDto.getFkTipoMessaggio())) {
				if (recordDto.getFkProcedimento() != null) {
					RebuspTProcedimentoDTO procedimento = procedimentiService
							.getProcedimentoByKey(recordDto.getFkProcedimento());
					String descProcedimento = rebuspDTipoProcedimentoDAO
							.selectByPrimaryKey(procedimento.getIdTipoProcedimento()).getDescTipoProcedimento();
					if (descProcedimento != null) {
						descTipoMess = (descTipoMess != null ? descTipoMess.replace("$$TIPO_PROC", descProcedimento)
								: null);
						// se e' un contesto 5(rendicontazione) faccio la replace del telaio
						if (idContesto.equals(new Long(5))) {
							String telaio = contribuzioneDAO.getTelaio(procedimento.getIdProcedimento());
							// inserire replace telaio
							descTipoMess = (descTipoMess != null ? descTipoMess.replace("$$TELAIO", telaio) : null);
						}
					}

					String noteMessaggio = procedimentiService.getNoteMessaggio(recordDto.getFkProcedimento(),
							recordDto.getFkTipoMessaggio(), recordDto.getDataCreazione());
					messaggio.setNote(noteMessaggio != null ? noteMessaggio : null);
				}
			}
			messaggio.setDescTipoMessaggio(descTipoMess);

			/* Se MESSAGGIO DI SISTEMA -- setto il testo del messaggio */
			if (messaggio.getFkTipoMessaggio().compareTo(Constants.TIPO_MESSAGGIO_4) == 0
					|| messaggio.getFkTipoMessaggio().compareTo(Constants.TIPO_MESSAGGIO_40) == 0
					|| messaggio.getFkTipoMessaggio().compareTo(Constants.TIPO_MESSAGGIO_41) == 0) {
				RebusDTipoMessaggioSistemaDTO rebusDTipoMessaggioSistemaDTO = rebusDTipoMessaggioSistemaDAO
						.selectByPrimaryKey(recordDto.getFkTipoMessaggioSistema());
				messaggio.setTestoMessaggioSistema(rebusDTipoMessaggioSistemaDTO.getTesto());
			}

			/* recupero targa */
			RebusTVariazAutobusDTO varAutobusDto = rebusTVariazAutobusDAO
					.selectByPrimaryKey(recordDto.getFkVariazAutobus());
			if (varAutobusDto != null) {
				messaggio.setTarga(varAutobusDto.getnTarga());

				if (recordDto.getFkTipoMessaggio() == 3) {
					if (varAutobusDto.getMotivazione() != null) {
						messaggio.setMessaggio(varAutobusDto.getMotivazione());
					} else {
						messaggio.setMessaggio("");
					}
				}

				Long fkAzienda = getFkAzienda(varAutobusDto.getPrimoTelaio());

				/* recupero azienda */
				if (fkAzienda != null) {
					RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
					rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(fkAzienda);
					List<RebusTAziendeDTO> aziendeDTO = rebusTaziendaDAO.selectByExample(rebusTAziendeSelector);
					if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
						messaggio.setAzienda(aziendeDTO.get(0).getDenominazione());
					}
				}

				/* Se FK_TIPO_MESSAGGIO = 1 -- setto le variazioni */
				if (messaggio.getFkTipoMessaggio().compareTo(Constants.TIPO_MESSAGGIO_1) == 0) {
					if (recordDto.getFkStoriaVariazAutobusSucc() != null) {
						RebusTStoriaVariazAutobusDTO varStoriaAutobusDto = rebusTStoriaVariazAutobusDAO
								.selectByPrimaryKey(recordDto.getFkStoriaVariazAutobus());
						RebusTStoriaVariazAutobusDTO varStoriaAutobusSuccDto = rebusTStoriaVariazAutobusDAO
								.selectByPrimaryKey(recordDto.getFkStoriaVariazAutobusSucc());
						messaggio.setVariazioneAutobus(getStoricoVariazioneAutobus(varStoriaAutobusSuccDto,
								getFkAzienda(varStoriaAutobusSuccDto.getPrimoTelaio())));
						messaggio.setVariazioneStoricoAutobus(getStoricoVariazioneAutobus(varStoriaAutobusDto,
								getFkAzienda(varStoriaAutobusDto.getPrimoTelaio())));
					} else {

						messaggio.setVariazioneAutobus(
								getVariazioneAutobus(varAutobusDto, getFkAzienda(varAutobusDto.getPrimoTelaio())));

						RebusTStoriaVariazAutobusDTO varStoriaAutobusDto = rebusTStoriaVariazAutobusDAO
								.selectByPrimaryKey(recordDto.getFkStoriaVariazAutobus());
						messaggio.setVariazioneStoricoAutobus(getStoricoVariazioneAutobus(varStoriaAutobusDto,
								getFkAzienda(varStoriaAutobusDto.getPrimoTelaio())));
					}
				}
			} else if (messaggio.getFkTipoMessaggio().compareTo(Constants.TIPO_MESSAGGIO_23) == 0) {

			}
		} else {
			/* nessun messaggio presente */
			return null;
		}

		return messaggio;
	}

	@Override
	public Long inserisciMessaggio(MessaggioVo input) {

		if (input == null) {
			throw new InvalidParameterException("messaggio non valorizzato");
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		/*
		 * TODO - VERIFICARE SE I DATI DI TARGA, AZIENDA E ORA MI ARRIVANO GIA' NEL
		 * MESSAGGIO O SE DEVO RECUPERARLI CON L'ID DELLA VARIAZIONE AUTOBUS
		 */
		RebusTMessaggiDTO record = new RebusTMessaggiDTO();

		record.setFkUtenteMittente(userInfo.getIdUtente());

		if (SecurityUtils.isAutorizzato(AuthorizationRoles.MODIFICA_BUS_AMP) || input.getFkProcedimento() != null) {
			record.setFkUtenteDestinatario(input.getFkUtenteDestinatario());
		} else {
			record.setFkUtenteDestinatario(Constants.ID_UTENTE_DESTINATARIO_MESSAGGIO_12);
		}

		record.setFkTipoMessaggio(input.getFkTipoMessaggio());
		record.setFkVariazAutobus(input.getFkVariazAutobus());
		record.setFkStoriaVariazAutobus(input.getFkStoriaVariazAutobus());
		record.setMessaggio(input.getMessaggio());
		record.setDataCreazione(DateUtils.getDataSistema());
		record.setFlgLetto(Constants.FLAG_NO);
		record.setFlgArchiviato(Constants.FLAG_NO);

		/* jira Rebus- del 14/01 */
		if (input.getFkTipoMessaggio().equals(Constants.TIPO_MESSAGGIO_1) && input.getFkStoriaVariazAutobus() != null) {
			RebusTMessaggiSelector sel = new RebusTMessaggiSelector();
			sel.createCriteria().andFkTipoMessaggioEqualTo(input.getFkTipoMessaggio())
					.andFkVariazAutobusEqualTo(input.getFkVariazAutobus());
			sel.setOrderByClause("data_creazione DESC");
			List<RebusTMessaggiDTO> messaggi = rebusTMessaggiDAO.selectByExample(sel);
			if (!messaggi.isEmpty()) {
				RebusTMessaggiDTO messaggio = messaggi.get(0);
				messaggio.setFkStoriaVariazAutobusSucc(input.getFkStoriaVariazAutobus());
				rebusTMessaggiDAO.updateByPrimaryKey(messaggio);
			}
		}
		record.setFkProcedimento(input.getFkProcedimento());

		rebusTMessaggiDAO.insert(record);

		return record.getIdMessaggio();

	}

	@Override
	public UtenteAzEnteVO dettaglioUtenteAzEnte(Long idUtente) {
		// TODO Auto-generated method stub
		UtenteAzEnteVO utenteAzEnte = new UtenteAzEnteVO();
		RebusRUtenteAzEnteSelector rebusUtenteSelector = new RebusRUtenteAzEnteSelector();
		rebusUtenteSelector.createCriteria().andFkUtenteEqualTo(idUtente);
		rebusUtenteSelector.setOrderByClause("data_appartenenza desc");
		List<RebusRUtenteAzEnteDTO> rebusUtenti = rebusRUtenteAzEnteDAO.selectByExample(rebusUtenteSelector);

		if (rebusUtenti.size() > 0) {
			RebusRUtenteAzEnteDTO rebusUtente = rebusUtenti.get(0);
			utenteAzEnte.setFkAzienda(rebusUtente.getFkAzienda());
			utenteAzEnte.setFkEnte(rebusUtente.getFkEnte());
			utenteAzEnte.setFkUtente(rebusUtente.getFkUtente());
			utenteAzEnte.setIdRUtenteAzEnte(rebusUtente.getIdRUtenteAzEnte());

			return utenteAzEnte;
		}

		return null;
	}

	private AutobusVO getStoricoVariazioneAutobus(RebusTStoriaVariazAutobusDTO varAutobusDto, Long idAzienda) {
		AutobusVO variazione = new AutobusVO();
		variazione.setAltraAlimentazione(varAutobusDto.getAltraAlimentazione());
		variazione.setAltriDispositiviPrevenzInc(varAutobusDto.getAltriDispositiviPrevenzInc());
		variazione.setAnnoSostProg(varAutobusDto.getAnnoSostProg() != null ? varAutobusDto.getAnnoSostProg() : null);

		variazione.setCaratteristicheParticolari(varAutobusDto.getCaratteristicheParticolari());
		variazione.setContributoPubblicoAcquisto(varAutobusDto.getContributoPubblicoAcquisto());
		variazione.setDataUltimaImmatricolazione(varAutobusDto.getDataUltimaImmatricolazione());
		variazione.setDataUltimaRevisione(varAutobusDto.getDataUltimaRevisione());
		variazione.setEnteAutorizzPrimaImm(varAutobusDto.getEnteAutorizzPrimaImm());

		variazione.setIdAzienda(idAzienda);
		if (variazione.getIdAzienda() != null) {
			RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
			rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(variazione.getIdAzienda());
			List<RebusTAziendeDTO> aziendeDTO = rebusTaziendaDAO.selectByExample(rebusTAziendeSelector);
			if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
				variazione.setAzienda(aziendeDTO.get(0).getDenominazione() + " "
						+ StringUtils.trimToEmpty(aziendeDTO.get(0).getNaturaGiuridica()));
			}
		}

		variazione.setIdTipoAllestimento(varAutobusDto.getFkTipoAllestimento());
		if (variazione.getIdTipoAllestimento() != null) {
			variazione.setTipoAllestimento(
					rebusDTipoAllestimentoDAO.selectByPrimaryKey(variazione.getIdTipoAllestimento()).getDescrizione());
		}

		variazione.setIdImpiantiAudio(varAutobusDto.getFkImpiantiAudio());
		if (variazione.getIdImpiantiAudio() != null) {
			variazione.setImpiantiAudio(
					rebusDImpiantiAudioDAO.selectByPrimaryKey(variazione.getIdImpiantiAudio()).getDescrizione());
		}

		variazione.setIdImpiantiVisivi(varAutobusDto.getFkImpiantiVisivi());
		if (variazione.getIdImpiantiVisivi() != null) {
			variazione.setImpiantiVisivi(
					rebusDImpiantiVisiviDAO.selectByPrimaryKey(variazione.getIdImpiantiVisivi()).getDescrizione());
		}

		variazione.setIdClasseVeicolo(varAutobusDto.getFkClasseVeicolo());
		if (variazione.getIdClasseVeicolo() != null) {
			variazione.setClasseVeicolo(
					rebusDClasseVeicoloDAO.selectByPrimaryKey(variazione.getIdClasseVeicolo()).getDescrizione());
		}

		variazione.setIdTipoImmatricolazione(varAutobusDto.getFkTipoImmatricolazione());
		if (variazione.getIdTipoImmatricolazione() != null) {
			variazione.setTipoImmatricolazione(rebusDTipoImmatricolDAO
					.selectByPrimaryKey(variazione.getIdTipoImmatricolazione()).getDescrizione());
		}

		variazione.setIdTipoAlimentazione(varAutobusDto.getFkTipoAlimentazione());
		if (variazione.getIdTipoAlimentazione() != null) {
			variazione.setTipoAlimentazione(rebusDTipoAlimentazioneDAO
					.selectByPrimaryKey(variazione.getIdTipoAlimentazione()).getDescrizione());
		}

		variazione.setIdClasseAmbientale(varAutobusDto.getFkClasseAmbientaleEuro());
		if (variazione.getIdClasseAmbientale() != null) {
			variazione.setClasseAmbientale(
					rebusDClasseAmbEuroDAO.selectByPrimaryKey(variazione.getIdClasseAmbientale()).getDescrizione());
		}

		variazione.setIdProprietaLeasing(varAutobusDto.getFkProprietaLeasing());
		if (variazione.getIdProprietaLeasing() != null) {
			variazione.setProprietaLeasing(
					rebusDProprietaDAO.selectByPrimaryKey(variazione.getIdProprietaLeasing()).getDescrizione());
		}

		variazione.setIdDeposito(varAutobusDto.getFkDeposito());
		if (varAutobusDto.getFkDeposito() != null) {
			DepositoVO deposito = crudAutobusService.getDepositoById(varAutobusDto.getFkDeposito());
			variazione.setDepositoStr(
					deposito.getDenominazione() + " - " + deposito.getIndirizzo() != null ? deposito.getIndirizzo()
							: "");
		}

		variazione.setIdDotazioneDisabili(varAutobusDto.getFkDotazioneDisabili());
		if (variazione.getIdDotazioneDisabili() != null) {
			variazione.setDotazioneDisabili(rebusDDotazioneDisabiliDAO
					.selectByPrimaryKey(variazione.getIdDotazioneDisabili()).getDescrizione());
		}
		variazione.setIdTipoAllestimento(varAutobusDto.getFkTipoAllestimento());
		variazione.setProgrTipoDimens(
				varAutobusDto.getProgrTipoDimens() != null ? varAutobusDto.getProgrTipoDimens().toString() : null);
		if (variazione.getIdTipoAllestimento() != null && variazione.getProgrTipoDimens() != null) {
			RebusDTipologiaDimensSelector sel = new RebusDTipologiaDimensSelector();
			RebusDTipologiaDimensSelector.Criteria criteria = sel.createCriteria();
			criteria.andIdTipoAllestimentoEqualTo(variazione.getIdTipoAllestimento());
			criteria.andProgrTipoDimensEqualTo(Long.parseLong(variazione.getProgrTipoDimens()));
			variazione.setTipologiaDimensionale(
					rebusDTipologiaDimensDAO.selectByExample(sel).get(0).getTipologiaDimens());
		}

		RebusRStoriaVarautobusDpSelector seldp = new RebusRStoriaVarautobusDpSelector();
		seldp.createCriteria().andIdStoriaVariazAutobusEqualTo(varAutobusDto.getIdStoriaVariazAutobus());

		List<RebusRStoriaVarautobusDpKey> res = rebusRStoriaVarautobusDpDAO.selectByExample(seldp);
		if (res != null) {
			String dispositivi = "";
			ArrayList<Long> ids = new ArrayList<>();
			for (RebusRStoriaVarautobusDpKey item : res) {
				dispositivi += rebusDDispositiviPrevenzDAO.selectByPrimaryKey(item.getIdDispositivo()).getDescrizione()
						+ ", ";
				ids.add(item.getIdDispositivo());
			}

			if (!dispositivi.equals("")) {
				dispositivi = dispositivi.substring(0, dispositivi.length() - 2);
			}
			variazione.setDispositiviPrevIncidenti(ids);
			variazione.setDispositiviPrevenzInc(dispositivi);
		}

		variazione.setFlagAlienato(varAutobusDto.getFlgAlienato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgAlienato(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgAvm(varAutobusDto.getFlgAvm() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgAvm(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgCabinaGuidaIsolata(varAutobusDto.getFlgCabinaGuidaIsolata() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgCabinaGuidaIsolata(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);

		variazione.setFlgContapasseggeri(varAutobusDto.getFlgContapasseggeri() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgContapasseggeri(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgConteggiatoMiv(varAutobusDto.getFlgConteggiatoMiv() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgConteggiatoMiv(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgDuePiani(varAutobusDto.getFlgDuePiani() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgDuePiani(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgFiltroFap(varAutobusDto.getFlgFiltroFap() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgFiltroFap(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione
				.setFlgImpiantoCondizionamento(varAutobusDto.getFlgImpiantoCondizionamento() == null ? Constants.FLAG_NO
						: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgImpiantoCondizionamento(), Constants.FLAG_NO)
								? Constants.FLAG_NO
								: Constants.FLAG_SI);
		variazione.setFlgOtx(varAutobusDto.getFlgOtx() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgOtx(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgRichiestaContr(varAutobusDto.getFlgRichiestaContr() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgRichiestaContr(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgRilevatoreBip(varAutobusDto.getFlgRilevatoreBip() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgRilevatoreBip(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgSnodato(varAutobusDto.getFlgSnodato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgSnodato(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgVeicoloAssicurato(varAutobusDto.getFlgVeicoloAssicurato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgVeicoloAssicurato(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);

		variazione.setLunghezza(varAutobusDto.getLunghezza());
		variazione.setMatricola(varAutobusDto.getnMatricolaAziendale());
		variazione.setnPostiInPiedi(
				varAutobusDto.getnPostiInPiedi() != null ? varAutobusDto.getnPostiInPiedi().toString() : null);
		variazione.setnPostiRiservati(
				varAutobusDto.getnPostiRiservati() != null ? varAutobusDto.getnPostiRiservati().toString() : null);
		variazione.setnPostiSedere(
				varAutobusDto.getnPostiSedere() != null ? varAutobusDto.getnPostiSedere().toString() : null);
		variazione.setTarga(varAutobusDto.getnTarga());
		variazione.setTelaio(varAutobusDto.getnTelaio());
		variazione.setNumeroPorte(varAutobusDto.getNumeroPorte() != null ? varAutobusDto.getNumeroPorte() : null);
		variazione.setOmologazioneDirettivaEuropea(varAutobusDto.getOmologazioneDirettivaEuropea());
		variazione.setPostiCarrozzina(
				varAutobusDto.getPostiCarrozzina() != null ? varAutobusDto.getPostiCarrozzina().toString() : null);
		variazione.setPrezzoTotAcquisto(varAutobusDto.getPrezzoTotAcquisto());
		variazione.setPrimoTelaio(varAutobusDto.getPrimoTelaio());

		return variazione;
	}

	private AutobusVO getVariazioneAutobus(RebusTVariazAutobusDTO varAutobusDto, Long idAzienda) {
		AutobusVO variazione = new AutobusVO();
		variazione.setAltraAlimentazione(varAutobusDto.getAltraAlimentazione());
		variazione.setAltriDispositiviPrevenzInc(varAutobusDto.getAltriDispositiviPrevenzInc());
		variazione.setAnnoSostProg(varAutobusDto.getAnnoSostProg() != null ? varAutobusDto.getAnnoSostProg() : null);

		variazione.setCaratteristicheParticolari(varAutobusDto.getCaratteristicheParticolari());
		variazione.setContributoPubblicoAcquisto(varAutobusDto.getContributoPubblicoAcquisto());
		variazione.setDataUltimaImmatricolazione(varAutobusDto.getDataUltimaImmatricolazione());
		variazione.setDataUltimaRevisione(varAutobusDto.getDataUltimaRevisione());
		variazione.setEnteAutorizzPrimaImm(varAutobusDto.getEnteAutorizzPrimaImm());

		variazione.setIdAzienda(idAzienda);
		if (variazione.getIdAzienda() != null) {
			RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
			rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(variazione.getIdAzienda());
			List<RebusTAziendeDTO> aziendeDTO = rebusTaziendaDAO.selectByExample(rebusTAziendeSelector);
			if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
				variazione.setAzienda(aziendeDTO.get(0).getDenominazione() + " "
						+ StringUtils.trimToEmpty(aziendeDTO.get(0).getNaturaGiuridica()));
			}
		}

		variazione.setIdTipoAllestimento(varAutobusDto.getFkTipoAllestimento());
		if (variazione.getIdTipoAllestimento() != null) {
			variazione.setTipoAllestimento(
					rebusDTipoAllestimentoDAO.selectByPrimaryKey(variazione.getIdTipoAllestimento()).getDescrizione());
		}

		variazione.setIdImpiantiAudio(varAutobusDto.getFkImpiantiAudio());
		if (variazione.getIdImpiantiAudio() != null) {
			variazione.setImpiantiAudio(
					rebusDImpiantiAudioDAO.selectByPrimaryKey(variazione.getIdImpiantiAudio()).getDescrizione());
		}

		variazione.setIdCategoriaVeicolo(varAutobusDto.getFkCategoriaVeicolo());
		if (variazione.getIdCategoriaVeicolo() != null) {
			variazione.setCategoriaVeicolo(rebusDCategoriaVeicoloDAO
					.selectByPrimaryKey(variazione.getIdCategoriaVeicolo()).getCategoriaVeicolo());
		}

		variazione.setIdImpiantiVisivi(varAutobusDto.getFkImpiantiVisivi());
		if (variazione.getIdImpiantiVisivi() != null) {
			variazione.setImpiantiVisivi(
					rebusDImpiantiVisiviDAO.selectByPrimaryKey(variazione.getIdImpiantiVisivi()).getDescrizione());
		}

		variazione.setIdClasseVeicolo(varAutobusDto.getFkClasseVeicolo());
		if (variazione.getIdClasseVeicolo() != null) {
			variazione.setClasseVeicolo(
					rebusDClasseVeicoloDAO.selectByPrimaryKey(variazione.getIdClasseVeicolo()).getDescrizione());
		}

		variazione.setIdTipoImmatricolazione(varAutobusDto.getFkTipoImmatricolazione());
		if (variazione.getIdTipoImmatricolazione() != null) {
			variazione.setTipoImmatricolazione(rebusDTipoImmatricolDAO
					.selectByPrimaryKey(variazione.getIdTipoImmatricolazione()).getDescrizione());
		}

		variazione.setIdTipoAlimentazione(varAutobusDto.getFkTipoAlimentazione());
		if (variazione.getIdTipoAlimentazione() != null) {
			variazione.setTipoAlimentazione(rebusDTipoAlimentazioneDAO
					.selectByPrimaryKey(variazione.getIdTipoAlimentazione()).getDescrizione());
		}

		variazione.setIdClasseAmbientale(varAutobusDto.getFkClasseAmbientaleEuro());
		if (variazione.getIdClasseAmbientale() != null) {
			variazione.setClasseAmbientale(
					rebusDClasseAmbEuroDAO.selectByPrimaryKey(variazione.getIdClasseAmbientale()).getDescrizione());
		}

		variazione.setIdProprietaLeasing(varAutobusDto.getFkProprietaLeasing());
		if (variazione.getIdProprietaLeasing() != null) {
			variazione.setProprietaLeasing(
					rebusDProprietaDAO.selectByPrimaryKey(variazione.getIdProprietaLeasing()).getDescrizione());
		}

		variazione.setIdDeposito(varAutobusDto.getFkDeposito());
		if (varAutobusDto.getFkDeposito() != null) {
			DepositoVO deposito = crudAutobusService.getDepositoById(varAutobusDto.getFkDeposito());
			variazione.setDepositoStr(
					deposito.getDenominazione() + " - " + deposito.getIndirizzo() != null ? deposito.getIndirizzo()
							: "");
		}

		variazione.setIdDotazioneDisabili(varAutobusDto.getFkDotazioneDisabili());
		if (variazione.getIdDotazioneDisabili() != null) {
			variazione.setDotazioneDisabili(rebusDDotazioneDisabiliDAO
					.selectByPrimaryKey(variazione.getIdDotazioneDisabili()).getDescrizione());
		}

		variazione.setIdTipoAllestimento(varAutobusDto.getFkTipoAllestimento());
		variazione.setProgrTipoDimens(
				varAutobusDto.getProgrTipoDimens() != null ? varAutobusDto.getProgrTipoDimens().toString() : null);
		if (variazione.getIdTipoAllestimento() != null && variazione.getProgrTipoDimens() != null) {
			RebusDTipologiaDimensSelector sel = new RebusDTipologiaDimensSelector();
			RebusDTipologiaDimensSelector.Criteria criteria = sel.createCriteria();
			criteria.andIdTipoAllestimentoEqualTo(variazione.getIdTipoAllestimento());
			criteria.andProgrTipoDimensEqualTo(Long.parseLong(variazione.getProgrTipoDimens()));
			variazione.setTipologiaDimensionale(
					rebusDTipologiaDimensDAO.selectByExample(sel).get(0).getTipologiaDimens());
		}
		RebusRVarautobusDpSelector seldp = new RebusRVarautobusDpSelector();
		seldp.createCriteria().andIdVariazAutobusEqualTo(varAutobusDto.getIdVariazAutobus());

		List<RebusRVarautobusDpKey> res = rebusRVarautobusDpDAO.selectByExample(seldp);
		if (res != null) {
			String dispositivi = "";
			ArrayList<Long> ids = new ArrayList<>();
			for (RebusRVarautobusDpKey item : res) {
				dispositivi += rebusDDispositiviPrevenzDAO.selectByPrimaryKey(item.getIdDispositivo()).getDescrizione()
						+ ", ";
				ids.add(item.getIdDispositivo());
			}

			if (!dispositivi.equals("")) {
				dispositivi = dispositivi.substring(0, dispositivi.length() - 2);
			}
			variazione.setDispositiviPrevIncidenti(ids);
			variazione.setDispositiviPrevenzInc(dispositivi);
		}

		variazione.setFlagAlienato(varAutobusDto.getFlgAlienato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgAlienato(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgAvm(varAutobusDto.getFlgAvm() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgAvm(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgCabinaGuidaIsolata(varAutobusDto.getFlgCabinaGuidaIsolata() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgCabinaGuidaIsolata(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);

		variazione.setFlgContapasseggeri(varAutobusDto.getFlgContapasseggeri() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgContapasseggeri(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgConteggiatoMiv(varAutobusDto.getFlgConteggiatoMiv() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgConteggiatoMiv(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgDuePiani(varAutobusDto.getFlgDuePiani() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgDuePiani(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgFiltroFap(varAutobusDto.getFlgFiltroFap() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgFiltroFap(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione
				.setFlgImpiantoCondizionamento(varAutobusDto.getFlgImpiantoCondizionamento() == null ? Constants.FLAG_NO
						: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgImpiantoCondizionamento(), Constants.FLAG_NO)
								? Constants.FLAG_NO
								: Constants.FLAG_SI);
		variazione.setFlgOtx(varAutobusDto.getFlgOtx() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgOtx(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgRichiestaContr(varAutobusDto.getFlgRichiestaContr() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgRichiestaContr(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgRilevatoreBip(varAutobusDto.getFlgRilevatoreBip() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgRilevatoreBip(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgSnodato(varAutobusDto.getFlgSnodato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgSnodato(), Constants.FLAG_NO) ? Constants.FLAG_NO
						: Constants.FLAG_SI);
		variazione.setFlgVeicoloAssicurato(varAutobusDto.getFlgVeicoloAssicurato() == null ? Constants.FLAG_NO
				: StringUtil.equalsIgnoreCase(varAutobusDto.getFlgVeicoloAssicurato(), Constants.FLAG_NO)
						? Constants.FLAG_NO
						: Constants.FLAG_SI);

		variazione.setLunghezza(varAutobusDto.getLunghezza());
		variazione.setMatricola(varAutobusDto.getnMatricolaAziendale());
		variazione.setnPostiInPiedi(
				varAutobusDto.getnPostiInPiedi() != null ? varAutobusDto.getnPostiInPiedi().toString() : null);
		variazione.setnPostiRiservati(
				varAutobusDto.getnPostiRiservati() != null ? varAutobusDto.getnPostiRiservati().toString() : null);
		variazione.setnPostiSedere(
				varAutobusDto.getnPostiSedere() != null ? varAutobusDto.getnPostiSedere().toString() : null);
		variazione.setTarga(varAutobusDto.getnTarga());
		variazione.setTelaio(varAutobusDto.getnTelaio());
		variazione.setNumeroPorte(varAutobusDto.getNumeroPorte() != null ? varAutobusDto.getNumeroPorte() : null);
		variazione.setOmologazioneDirettivaEuropea(varAutobusDto.getOmologazioneDirettivaEuropea());
		variazione.setPostiCarrozzina(
				varAutobusDto.getPostiCarrozzina() != null ? varAutobusDto.getPostiCarrozzina().toString() : null);
		variazione.setPrezzoTotAcquisto(varAutobusDto.getPrezzoTotAcquisto());
		variazione.setPrimoTelaio(varAutobusDto.getPrimoTelaio());

		return variazione;
	}

	@Override
	public Boolean checkMessaggiNonLetti(Long idFilter) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		if (SecurityUtils.isUtenteServizioOGestoreDati()) {
			return ricercaDAO.checkMessaggiNonLetti(null, null, null, idFilter);
		}

		if (userInfo.getIdAzienda() != null) {
			return ricercaDAO.checkMessaggiNonLetti(null, userInfo.getIdAzienda(), userInfo.getIdUtente(), idFilter);
		} else if (userInfo.getIdEnte() != null) {
			return ricercaDAO.checkMessaggiNonLetti(userInfo.getIdEnte(), null, userInfo.getIdUtente(), idFilter);

		}

		return false;
	}

	private Long getFkAzienda(String primoTelaio) {
		Date now = new Date();
		RebusRAziendaAutobusSelector rebusRAziendaAutobusSel = new RebusRAziendaAutobusSelector();
		rebusRAziendaAutobusSel = new RebusRAziendaAutobusSelector();
		rebusRAziendaAutobusSel.createCriteria().andPrimoTelaioEqualTo(primoTelaio)
				.andDataAlienazioneGreaterThanOrEqualTo(now);
		rebusRAziendaAutobusSel.or().andPrimoTelaioEqualTo(primoTelaio).andDataAlienazioneIsNull();
		rebusRAziendaAutobusSel.setOrderByClause("data_aggiornamento DESC");
		List<RebusRAziendaAutobusDTO> aziendaAutobus = rebusRAziendaAutobusDAO.selectByExample(rebusRAziendaAutobusSel);
		if (aziendaAutobus != null && aziendaAutobus.size() > 0) {
			return aziendaAutobus.get(0).getFkAzienda();
		} else {
			return null;
		}
	}

}
