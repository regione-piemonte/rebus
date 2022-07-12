/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.common.Messages;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.excel.Validator.Combo;
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
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipologiaDimensDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRAziendaAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ExcelDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDCategoriaVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDCategoriaVeicoloSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseAmbEuroDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseAmbEuroSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseVeicoloSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDDispositiviPrevenzDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDDispositiviPrevenzSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDDotazioneDisabiliDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDDotazioneDisabiliSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDImpiantiAudioDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDImpiantiAudioSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDImpiantiVisiviDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDImpiantiVisiviSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDProprietaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDProprietaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoAlimentazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoAlimentazioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoAllestimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoAllestimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoImmatricolDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoImmatricolSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.AutobusDto;
import it.csi.rebus.rebuscrus.integration.dto.custom.ResponseExcelDto;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.StringUtil;

@Component
public class ExcelHelper {
	private HashMap<Combo, HashMap<String, Long>> mapDecodifiche = new HashMap<Combo, HashMap<String, Long>>();
	private HashMap<Long, List> mapTipolDim = null;

	@Autowired
	private RebusDTipoAlimentazioneDAO rebusDTipoAlimentazioneDAO;
	@Autowired
	private RebusDTipoAllestimentoDAO rebusDTipoAllestimentoDAO;
	@Autowired
	private RebusDClasseAmbEuroDAO rebusDClasseAmbEuroDAO;
	@Autowired
	private RebusDDotazioneDisabiliDAO rebusDDotazioneDisabiliDAO;
	@Autowired
	private RebusDImpiantiAudioDAO rebusDImpiantiAudioDAO;
	@Autowired
	private RebusDImpiantiVisiviDAO rebusDImpiantiVisiviDAO;
	@Autowired
	private RebusDTipoImmatricolDAO rebusDTipoImmatricolDAO;
	@Autowired
	private RebusDClasseVeicoloDAO rebusDClasseVeicoloDAO;
	@Autowired
	private RebusDDispositiviPrevenzDAO rebusDDispositiviPrevenzDAO;
	@Autowired
	private RebusDProprietaDAO rebusDProprietaDAO;
	@Autowired
	private ExcelDAO excelDAO;
	@Autowired
	private RebusTAutobusDAO rebusTAutobusDAO;
	@Autowired
	private RebusRAziendaAutobusDAO rebusRAziendaAutobusDAO;
	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;
	@Autowired
	private RebusRVarautobusDpDAO rebusRVarautobusDpDAO;
	@Autowired
	private RebusDTipologiaDimensDAO rebusDTipologiaDimensDAO;
	@Autowired
	private RebusDCategoriaVeicoloDAO rebusDCategoriaVeicoloDAO;

	private Logger logger = Logger.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME);

	/*
	 * Carico tutti i dati delle tabelle di decodifica, per poterli usare per
	 * validare i dati del file excel
	 */
	public void loadTable() {

		// capire se svuotare la variabile o modificare il metodo
		mapDecodifiche = new HashMap<Combo, HashMap<String, Long>>();

		synchronized (this) {
			// TIPO ALIMENTAZIONE
			try {
				RebusDTipoAlimentazioneSelector example = new RebusDTipoAlimentazioneSelector();
				example.setOrderByClause("id_tipo_alimentazione");
				List<RebusDTipoAlimentazioneDTO> rebusDTipoAlimentazioneDTOList = rebusDTipoAlimentazioneDAO
						.selectByExample(example);
				if (rebusDTipoAlimentazioneDTOList != null && !rebusDTipoAlimentazioneDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoAlimentazione = new LinkedHashMap<String, Long>();
					for (RebusDTipoAlimentazioneDTO dto : rebusDTipoAlimentazioneDTOList) {
						mapTipoAlimentazione.put(dto.getDescrizione().toUpperCase(), dto.getIdTipoAlimentazione());
					}
					mapDecodifiche.put(Combo.TIPO_FLAG, mapTipoAlimentazione);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_ALIMENTAZIONE));
				e.printStackTrace();
			}

			// PROPIETA' LEASING
			try {
				RebusDProprietaSelector example = new RebusDProprietaSelector();
				example.setOrderByClause("id_proprieta");
				List<RebusDProprietaDTO> rebusDProprietaDTOList = rebusDProprietaDAO.selectByExample(example);
				if (rebusDProprietaDTOList != null && !rebusDProprietaDTOList.isEmpty()) {
					HashMap<String, Long> mapProprieta = new LinkedHashMap<String, Long>();
					for (RebusDProprietaDTO dto : rebusDProprietaDTOList) {
						mapProprieta.put(dto.getDescrizione().toUpperCase(), dto.getIdProprieta());
					}
					mapDecodifiche.put(Combo.PROPRIETA_LEASING, mapProprieta);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_PROPIETA_LEASING));
				e.printStackTrace();
			}

			// TIPI DISPOSITIVI PREVENZIONE
			try {
				RebusDDispositiviPrevenzSelector example = new RebusDDispositiviPrevenzSelector();
				example.setOrderByClause("id_dispositivo");
				List<RebusDDispositiviPrevenzDTO> rebusDDispositiviPrevenzDTOList = rebusDDispositiviPrevenzDAO
						.selectByExample(example);
				if (rebusDDispositiviPrevenzDTOList != null && !rebusDDispositiviPrevenzDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoDispPrevenz = new LinkedHashMap<String, Long>();
					for (RebusDDispositiviPrevenzDTO dto : rebusDDispositiviPrevenzDTOList) {
						mapTipoDispPrevenz.put(dto.getDescrizione().toUpperCase(), dto.getIdDispositivo());
					}
					mapDecodifiche.put(Combo.TIPO_DISPOSITIVI_PREVENZIONE, mapTipoDispPrevenz);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_TIPO_DISPOSITIVI_PREVENZIONE));
				e.printStackTrace();
			}

			// CLASSE VEICOLO
			try {
				RebusDClasseVeicoloSelector example = new RebusDClasseVeicoloSelector();
				example.setOrderByClause("id_classe_veicolo");
				List<RebusDClasseVeicoloDTO> rebusDClasseVeicoloDTOList = rebusDClasseVeicoloDAO
						.selectByExample(example);
				if (rebusDClasseVeicoloDTOList != null && !rebusDClasseVeicoloDTOList.isEmpty()) {
					HashMap<String, Long> mapClasseVeicolo = new LinkedHashMap<String, Long>();
					for (RebusDClasseVeicoloDTO dto : rebusDClasseVeicoloDTOList) {
						mapClasseVeicolo.put(dto.getDescrizione().toUpperCase(), dto.getIdClasseVeicolo());
					}
					mapDecodifiche.put(Combo.CLASSE_VEICOLO, mapClasseVeicolo);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_CLASSE_VEICOLO));
				e.printStackTrace();
			}

			// TIPO IMMATRICOLAZIONE
			try {
				RebusDTipoImmatricolSelector example = new RebusDTipoImmatricolSelector();
				example.setOrderByClause("id_tipo_immatricolazione");
				List<RebusDTipoImmatricolDTO> rebusDTipoImmatricolDTOList = rebusDTipoImmatricolDAO
						.selectByExample(example);
				if (rebusDTipoImmatricolDTOList != null && !rebusDTipoImmatricolDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoImmatricolazione = new LinkedHashMap<String, Long>();
					for (RebusDTipoImmatricolDTO dto : rebusDTipoImmatricolDTOList) {
						mapTipoImmatricolazione.put(dto.getDescrizione().toUpperCase(),
								dto.getIdTipoImmatricolazione());
					}
					mapDecodifiche.put(Combo.TIPO_IMMATRICOLAZIONE, mapTipoImmatricolazione);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_IMMATRICOLAZIONE));
				e.printStackTrace();
			}

			// TIPO VEIDEO
			try {
				RebusDImpiantiVisiviSelector example = new RebusDImpiantiVisiviSelector();
				example.setOrderByClause("id_impianti_visivi");
				List<RebusDImpiantiVisiviDTO> rebusDImpiantiVisiviDTOList = rebusDImpiantiVisiviDAO
						.selectByExample(example);
				if (rebusDImpiantiVisiviDTOList != null && !rebusDImpiantiVisiviDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoVideo = new LinkedHashMap<String, Long>();
					for (RebusDImpiantiVisiviDTO dto : rebusDImpiantiVisiviDTOList) {
						mapTipoVideo.put(dto.getDescrizione().toUpperCase(), dto.getIdImpiantiVisivi());
					}
					mapDecodifiche.put(Combo.TIPO_VIDEO, mapTipoVideo);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_VIDEO));
				e.printStackTrace();
			}

			//TIPO AUDIO
			try {
				RebusDImpiantiAudioSelector example = new RebusDImpiantiAudioSelector();
				example.setOrderByClause("id_impianti_audio");
				List<RebusDImpiantiAudioDTO> rebusDImpiantiAudioDTOList = rebusDImpiantiAudioDAO
						.selectByExample(example);
				if (rebusDImpiantiAudioDTOList != null && !rebusDImpiantiAudioDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoAudio = new LinkedHashMap<String, Long>();
					for (RebusDImpiantiAudioDTO dto : rebusDImpiantiAudioDTOList) {
						mapTipoAudio.put(dto.getDescrizione().toUpperCase(), dto.getIdImpiantiAudio());
					}
					mapDecodifiche.put(Combo.TIPO_AUDIO, mapTipoAudio);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_AUDIO));
				e.printStackTrace();
			}

			// TIPO FACILITAZIONI
			try {
				RebusDDotazioneDisabiliSelector example = new RebusDDotazioneDisabiliSelector();
				example.setOrderByClause("id_dotazione_disabili");
				List<RebusDDotazioneDisabiliDTO> rebusDDotazioneDisabiliDTOList = rebusDDotazioneDisabiliDAO
						.selectByExample(example);
				if (rebusDDotazioneDisabiliDTOList != null && !rebusDDotazioneDisabiliDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoFacilitazioni = new LinkedHashMap<String, Long>();
					for (RebusDDotazioneDisabiliDTO dto : rebusDDotazioneDisabiliDTOList) {
						mapTipoFacilitazioni.put(dto.getDescrizione().toUpperCase(), dto.getIdDotazioneDisabili());
					}
					mapDecodifiche.put(Combo.TIPO_FACILITAZIONI, mapTipoFacilitazioni);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_FACILITAZIONI));
				e.printStackTrace();
			}

			// TIPO EURO
			try {
				RebusDClasseAmbEuroSelector example = new RebusDClasseAmbEuroSelector();
				example.setOrderByClause("id_classe_ambientale");
				List<RebusDClasseAmbEuroDTO> rebusDClasseAmbEuroList = rebusDClasseAmbEuroDAO.selectByExample(example);
				if (rebusDClasseAmbEuroList != null && !rebusDClasseAmbEuroList.isEmpty()) {
					HashMap<String, Long> mapTipoEuro = new LinkedHashMap<String, Long>();
					for (RebusDClasseAmbEuroDTO dto : rebusDClasseAmbEuroList) {
						mapTipoEuro.put(dto.getDescrizione().toUpperCase(), dto.getIdClasseAmbientale());
					}
					mapDecodifiche.put(Combo.TIPO_EURO, mapTipoEuro);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_EURO));
				e.printStackTrace();
			}

			// TIPO ALLESTIMENTO
			try {
				RebusDTipoAllestimentoSelector example = new RebusDTipoAllestimentoSelector();
				example.setOrderByClause("id_tipo_allestimento");
				List<RebusDTipoAllestimentoDTO> rebusDTipoAllestimentoDTOList = rebusDTipoAllestimentoDAO
						.selectByExample(example);
				if (rebusDTipoAllestimentoDTOList != null && !rebusDTipoAllestimentoDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoAllestimento = new LinkedHashMap<String, Long>();
					for (RebusDTipoAllestimentoDTO dto : rebusDTipoAllestimentoDTOList) {
						mapTipoAllestimento.put(dto.getDescrizione().toUpperCase(), dto.getIdTipoAllestimento());
					}
					mapDecodifiche.put(Combo.TIPO_ALLESTIMENTO, mapTipoAllestimento);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_ALLESTIMENTO));
				e.printStackTrace();
			}

			// CATEGORIA VEICOLO
			try {
				RebusDCategoriaVeicoloSelector example = new RebusDCategoriaVeicoloSelector();
				example.setOrderByClause("cod_categoria_veicolo");
				List<RebusDCategoriaVeicoloDTO> rebusDCategoriaVeicoloDTOList = rebusDCategoriaVeicoloDAO
						.selectByExample(example);
				if (rebusDCategoriaVeicoloDTOList != null && !rebusDCategoriaVeicoloDTOList.isEmpty()) {
					HashMap<String, Long> mapCategoriaVeicolo = new LinkedHashMap<String, Long>();
					for (RebusDCategoriaVeicoloDTO dto : rebusDCategoriaVeicoloDTOList) {
						mapCategoriaVeicolo.put(dto.getCategoriaVeicolo().toUpperCase(), dto.getIdCategoriaVeicolo());
					}
					mapDecodifiche.put(Combo.CATEGORIA_VEICOLO, mapCategoriaVeicolo);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_CATEGORIA_VEICOLO));
				e.printStackTrace();
			}

			// TIPO ALIMENTAZIONE
			try {
				RebusDTipoAlimentazioneSelector example = new RebusDTipoAlimentazioneSelector();
				example.setOrderByClause("id_tipo_alimentazione");
				List<RebusDTipoAlimentazioneDTO> rebusDTipoAlimentazioneDTOList = rebusDTipoAlimentazioneDAO
						.selectByExample(example);
				if (rebusDTipoAlimentazioneDTOList != null && !rebusDTipoAlimentazioneDTOList.isEmpty()) {
					HashMap<String, Long> mapTipoAlimentazione = new LinkedHashMap<String, Long>();
					for (RebusDTipoAlimentazioneDTO dto : rebusDTipoAlimentazioneDTOList) {
						mapTipoAlimentazione.put(dto.getDescrizione().toUpperCase(), dto.getIdTipoAlimentazione());
					}
					mapDecodifiche.put(Combo.TIPO_ALIMENTAZIONE, mapTipoAlimentazione);
				}
			} catch (Exception e) {
				logger.error(new ErroreGestitoException(Messages.ERRORE_CARICAMENTO_TIPO_ALIMENTAZIONE));
				e.printStackTrace();
			}

		}

		/*
		 * Carico una mappa con arraylist per ogni allestimento, per evitare di andare a
		 * leggere ogni volta che carico gli excel, i dati dal db per ogni riga
		 */
		if (mapTipolDim == null) {
			synchronized (this) {
				mapTipolDim = new HashMap<Long, List>();
				List<RebusDTipologiaDimensDTO> listTipolDim = rebusDTipologiaDimensDAO.selectByExample(null);
				if (listTipolDim != null) {
					for (RebusDTipologiaDimensDTO tipolDim : listTipolDim) {
						List<RebusDTipologiaDimensDTO> tipolDimByAllestimento = (List<RebusDTipologiaDimensDTO>) mapTipolDim
								.get(tipolDim.getIdTipoAllestimento());
						if (tipolDimByAllestimento == null) {
							tipolDimByAllestimento = new ArrayList<RebusDTipologiaDimensDTO>();
							mapTipolDim.put(tipolDim.getIdTipoAllestimento(), tipolDimByAllestimento);
						}
						tipolDimByAllestimento.add(tipolDim);
					}
				}
			}
		}
	}

	/*
	 * Controlli incrociati file - db
	 */
	public boolean controlliIncrociatiFileDbExcel(ResponseExcelDto response, Long idAzienda, List<AutobusDto> mezzi) {
		boolean result = false;
		ArrayList<String> listTarghe = new ArrayList<String>();
		ArrayList<String> listPrimoTelaio = new ArrayList<String>();

		if (mezzi == null)
			mezzi = response.getMezzi();

		for (AutobusDto autobus : mezzi) {
			listTarghe.add(autobus.getnTarga());
			listPrimoTelaio.add(autobus.getPrimoTelaio());
		}
		List<String> msgErroreCongruenzaCampiExcel = new ArrayList<String>();
		/*
		 * Controlli incrociati file - db In fase di upload il sistema dovra' verificare
		 * che il numero primo telaio non sia gia' presente nella base dati: - se non e'
		 * presente, aggiunge il record nel db e carica il nuovo mezzo - se e' gia'
		 * presente verifica l'id azienda cui e' associato, se diverso da quello
		 * dell'utente segnala con un messaggio che quel mezzo e' gia' stato caricato
		 * dall'azienda 'xxx' e non ne consente il caricamento, se uguale a quello
		 * dell'utente verifica sulla tabella t_variaz_autobus il campo
		 * flg_verificato_azienda e: se e' valorizzato a S restituisce all'utente un
		 * messaggio che indica che l'autobus non puo' essere caricato in quanto e' gia'
		 * nella base dati. Per effettuare le modifiche deve utilizzare il data entry se
		 * e' valorizzato a N restituisce all'utente un messaggio che indica che
		 * l'autobus e' gia' presente nella base dati e che e' possibile ricaricarlo da
		 * file solo se prima viene eliminato da applicativo.
		 */
		List<RebusTVariazAutobusDTO> telai = null;
		if (listPrimoTelaio.size() > 0) {
			telai = excelDAO.getPrimoTelaioByPrimoTelaio(listPrimoTelaio);
		}
		if (telai != null && !telai.isEmpty()) {
			result = true;
			for (RebusTVariazAutobusDTO autobus : telai) {
				if (idAzienda.compareTo(autobus.getFkAzienda()) == 0) {
					if (Constants.FLAG_SI.equalsIgnoreCase(autobus.getFlgVerificatoAzienda())) {
						msgErroreCongruenzaCampiExcel.add("L'autobus con primo telaio " + autobus.getPrimoTelaio()
								+ " e' gia' presente nel sistema: per effettuare modifiche utilizzare il data entry");
					} else {
						msgErroreCongruenzaCampiExcel.add("L'autobus con primo telaio " + autobus.getPrimoTelaio()
								+ " e' gia' presente nel sistema, per ricaricarlo e' necessario prima eliminarlo da applicativo");
					}
				} else {
					msgErroreCongruenzaCampiExcel.add("L'autobus con primo telaio " + autobus.getPrimoTelaio()
							+ " e' gia' presente nel sistema ma risulta collegato ad un'altra azienda");
				}
			}
		}
		/*
		 * Inoltre sempre in fase di upload il sistema dovra' verificare che il valore
		 * del campo 'targa' non sia gia' presente nella base dati: - se e' presente il
		 * sistema deve verificare anche l'id azienda cui il bus con quella targa e'
		 * associato, se l'id azienda e' diverso da quello di appartenenza dell'utente,
		 * restituisce un messaggio che segnala che la targa 'XXX' e' gia' presente nel
		 * db collegata ad un autobus dell'azienda 'xxx', se e' presente associata ad un
		 * mezzo il cui id azienda e' uguale a quello dell'utente il sistema restituisce
		 * il messaggio che la targa 'XXX' e' gia' presente nel sistema e non procede
		 * con il caricamento; - se non e' presente il sistema carica il record
		 *
		 */
		List<RebusTVariazAutobusDTO> targhe = null;
		if (listTarghe.size() > 0) {
			targhe = excelDAO.getTargheByTarghe(listTarghe);
		}
		if (targhe != null && !targhe.isEmpty()) {
			result = true;
			for (RebusTVariazAutobusDTO autobus : targhe) {
				if (idAzienda.compareTo(autobus.getFkAzienda()) == 0) {
					msgErroreCongruenzaCampiExcel.add("L'autobus con targa " + autobus.getnTarga()
							+ " e' gia' presente nel sistema e quindi non puo' essere caricato");
				} else {
					msgErroreCongruenzaCampiExcel.add("L'autobus con targa " + autobus.getnTarga()
							+ " e' gia' presente nel sistema ma risulta collegato ad un'altra azienda");
				}
			}
		}

		if (result)
			response.setMsgErroreCongruenzaCampiExcel(msgErroreCongruenzaCampiExcel);

		return result;

	}

	public void insertAutobus(AutobusDto mezzo) {
		Date now = new Date();
		String defaultDate = "1900-01-01";
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(defaultDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Per prima cosa aggiungo un reco dulla tabella dell'autobus: rebus_t_autobus
		 */
		RebusTAutobusDTO autobus = new RebusTAutobusDTO();
		autobus.setPrimoTelaio(mezzo.getPrimoTelaio());
		autobus.setDataPrimaImmatricolazione(
				mezzo.getDtPrimaImmatricolazione() != null ? mezzo.getDtPrimaImmatricolazione() : date);
		autobus.setMarca(mezzo.getMarca());
		autobus.setModello(mezzo.getModello());
		this.rebusTAutobusDAO.insertSelective(autobus);

		/*
		 * Poi aggiungo un record sulla tabella che lega l'azienda all'autobus
		 * rebus_r_azienda_autobus
		 */
		RebusRAziendaAutobusDTO aziendaAutobus = new RebusRAziendaAutobusDTO();
		aziendaAutobus.setFkAzienda(mezzo.getFkAzienda());
		aziendaAutobus.setDataAggiornamento(now);
		aziendaAutobus.setPrimoTelaio(mezzo.getPrimoTelaio());
		aziendaAutobus.setDataAlienazione(mezzo.getDataAlienazione());

		this.rebusRAziendaAutobusDAO.insertSelective(aziendaAutobus);

		/*
		 * Aggiungo un record sulal tabella dove ci sono i dati dell'autobus
		 * storicizzati: rebus_t_variaz_autobus
		 */
		mezzo.setnTelaio(mezzo.getnTelaio() == null ? mezzo.getPrimoTelaio() : mezzo.getnTelaio());
		mezzo.setEnteAutorizzPrimaImm(mezzo.getEnteAutorizzPrimaImm() == null ? "ND" : mezzo.getEnteAutorizzPrimaImm());
		mezzo.setDataAggiornamento(now);
		mezzo.setDataInserimento(now);
		this.rebusTVariazAutobusDAO.insertSelective(mezzo);

		/*
		 * Ed infine aggiungo una tabella dove ci sono i dati relativi ai dispositivi di
		 * prevenzione rebus_r_varautobus_dp
		 */
		if (mezzo.getFkDispositiviPrevenzInc() != null) {
			RebusRVarautobusDpKey dispPrev = new RebusRVarautobusDpKey();

			dispPrev.setIdDispositivo(mezzo.getFkDispositiviPrevenzInc());
			dispPrev.setIdVariazAutobus(mezzo.getIdVariazAutobus());

			rebusRVarautobusDpDAO.insertSelective(dispPrev);
		}
	}

	public void inizializzazionePuliziaDatiAutobus(ResponseExcelDto response) {

		it.csi.rebus.rebuscrus.security.UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		/*
		 * Per i campi Primo telaio, telaio e targa occorre in fase di caricamento su db
		 * eliminare gli eventuali spazi inseriti dall'utente in fase di compilazione
		 * del xls. Per ciascun autobus nuovo caricato tramite upload, il sistema dovra'
		 * valorizzare in automatico a N sia il campo flg_verificato_azienda sia il
		 * campo flg_verificato_AMP.
		 */
		for (AutobusDto autobus : response.getMezzi()) {
			// Devo Eliminare gli spazzi dalla targa e dal telaio
			autobus.setPrimoTelaio(autobus.getPrimoTelaio().replaceAll(" ", ""));
			autobus.setnTelaio(autobus.getnTelaio() != null ? autobus.getnTelaio().replaceAll(" ", "") : null);
			autobus.setnTarga(autobus.getnTarga() != null ? autobus.getnTarga().replaceAll(" ", "") : null);

			// nMatricolaAziendale se mettono un numero mi viene restituito con
			// .0 finale
			autobus.setnMatricolaAziendale(StringUtil.removedot0(autobus.getnMatricolaAziendale()));

			autobus.setFlgVerificatoAzienda(Constants.FLAG_NO);
			autobus.setFlgVerificatoAmp(Constants.FLAG_NO);

			Boolean isPrimoImpianto = SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);// rebus
																														// 16
																														// //no
																														// id
																														// Azienda
			Boolean isUploadExcel = SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS);// rebus
																									// 4
																									// //SI
																									// IDaZIENDA
																									// NEL
																									// PROFILO

			if (isPrimoImpianto && isUploadExcel && userInfo.getIdAzienda() != null)
				autobus.setFkAzienda(userInfo.getIdAzienda());
			if (isUploadExcel && !isPrimoImpianto)
				autobus.setFkAzienda(userInfo.getIdAzienda());

			autobus.setFkUtente(userInfo.getIdUtente());

			/*
			 * colonna progr_tipo_dimens della tabella rebus_t_variaz_autobus: fa
			 * riferimento alla tabella d_tipologia_dimens. Questa tabella, incrociando i
			 * valori di diversi campi, consente di determinare il valore del campo
			 * tipologia dimensionale. Quindi per id_tipo allestimento (urbano, suburbano,
			 * extraurbano), occorre vedere sulla tab.d_tipologia_dimens il range di
			 * lunghezza in cui cade quel bus e determinare il prog tipo dimens da inserire
			 * nella tabella var_autobus sulla base della coppia id_alestimento e
			 * prog_tipo_dimens il sistema dovra' in visualizzazione mostrare il valore del
			 * campo tipologia_dimens es bus urbano lungo 9 metri avra' prog_tipo_dimens =3.
			 * La tipologia dimensionale del bus risultante e' medio
			 */
			List<RebusDTipologiaDimensDTO> listTipolDim = mapTipolDim.get(autobus.getFkTipoAllestimento());

			autobus.setProgrTipoDimens(getProgTipoDimens(listTipolDim, autobus.getLunghezza()));
		}
	}

	private Long getProgTipoDimens(List<RebusDTipologiaDimensDTO> listTipolDim, double lunghezza) {
		if (listTipolDim != null) {
			for (RebusDTipologiaDimensDTO estremi : listTipolDim) {
				if (lunghezza >= estremi.getLunghezzaMin() && lunghezza <= estremi.getLunghezzaMax())
					return estremi.getProgrTipoDimens();
			}
		}
		return 0l; // se non ho trovato nulla inseriamo 0 che corrisponde a ND
	}

	/**
	 * Resituisce -1 se non trova nulla, per poter trasmettere un errore, null se
	 * non c'era una stringa in input
	 *
	 * @param desc
	 * @param combo
	 * @return
	 */
	public Long getIdFromDescription(String desc, Combo combo) {
		if (StringUtil.isEmpty(desc))
			return null;
		HashMap<String, Long> mapTipo = mapDecodifiche.get(combo);
		Long result = mapTipo.get(desc.toUpperCase());
		if (result == null)
			result = -1l;
		return result;
	}

	public HashMap<Combo, HashMap<String, Long>> getMapDecodifiche() {
		return mapDecodifiche;
	}

}
