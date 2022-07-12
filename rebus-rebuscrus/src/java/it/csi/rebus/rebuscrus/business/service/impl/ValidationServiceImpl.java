/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.ValidationService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusRAziendaAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRAziendaAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusSelector;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
@Component
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	RebusTVariazAutobusDAO rebusTVariazAutobusDAO;

	@Autowired
	private RebusRAziendaAutobusDAO rebusRAziendaAutobusDAO;



	@Override
	public void verificaTargaTelaioAndFileExtention(AutobusVO autobus, String fileName, byte[] file) {

		String mess = "";
		String targa = autobus.getTarga();
		String primoTelaio = autobus.getTelaio();
		Long idAutobus = autobus.getId();
		Long idAzienda = autobus.getIdAzienda();

		RebusTVariazAutobusSelector selVa = new RebusTVariazAutobusSelector();
		List<RebusTVariazAutobusDTO> listDto = new ArrayList<>();

		if (StringUtils.isNotEmpty(primoTelaio) && StringUtils.isNotEmpty(fileName) && !fileName.contains(".pdf")
				&& file != null) {

			selVa.createCriteria().andPrimoTelaioEqualTo(primoTelaio).andIdVariazAutobusNotEqualTo(idAutobus);
			listDto = rebusTVariazAutobusDAO.selectByExample(selVa);

			if (!listDto.isEmpty()) {
				for (RebusTVariazAutobusDTO dto : listDto) {
					Long fkAzienda = getFkAzienda(dto.getPrimoTelaio());
					if (fkAzienda != null && idAzienda.compareTo(fkAzienda) == 0) {
						if (Constants.FLAG_SI.equalsIgnoreCase(dto.getFlgVerificatoAzienda())) {
							mess = "Primo Telaio gia presente in archivio! \n Formato file del documento allegato non consentito!";
							throw new ErroreGestitoException(mess, "TELFLAGSIPS");
						} else {
							mess = "Primo Telaio gia presente in archivio! \n Formato file del documento allegato non consentito! ";
							throw new ErroreGestitoException(mess, "TELFLAGNOPS");
						}
					} else {
						mess = "Primo Telaio gia presente in archivio! \n Formato file del documento allegato non consentito! ";
						throw new ErroreGestitoException(mess, "TELFLAGNOPSA");
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(targa)) {

			selVa = new RebusTVariazAutobusSelector();
			selVa.createCriteria().andNTargaEqualTo(targa).andIdVariazAutobusNotEqualTo(idAutobus);
			listDto = rebusTVariazAutobusDAO.selectByExample(selVa);

			if (!listDto.isEmpty()) {
				for (RebusTVariazAutobusDTO dto : listDto) {
					Long fkAzienda = getFkAzienda(dto.getPrimoTelaio());
					if (fkAzienda != null && idAzienda.compareTo(fkAzienda) == 0) {
						mess = "Targa o Primo Telaio gia presente in archivio! \n Formato file del documento allegato non consentito!";
						throw new ErroreGestitoException(mess, "TARGAPSA");
					} else {
						mess = "Targa o Primo Telaio gia presente in archivio! \n Formato file del documento allegato non consentito! ";
						throw new ErroreGestitoException(mess, "TARGAPSNA");
					}
				}
			}
		}
		if (StringUtils.isEmpty(primoTelaio))
			throw new InvalidParameterException("telaio uguale a null");
	}

	/*
	 * False: verifica non superata True: verifica superata
	 */
	@Override
	public void verificaTargaTelaio(AutobusVO autobus) {

		String mess = "";
		String targa = autobus.getTarga();
		String primoTelaio = autobus.getTelaio();
		Long idAutobus = autobus.getId();
		Long idAzienda = autobus.getIdAzienda();

		RebusTVariazAutobusSelector selVa = new RebusTVariazAutobusSelector();
		List<RebusTVariazAutobusDTO> listDto = new ArrayList<>();

		if (StringUtils.isNotEmpty(primoTelaio)) {

			selVa.createCriteria().andPrimoTelaioEqualTo(primoTelaio).andIdVariazAutobusNotEqualTo(idAutobus);
			listDto = rebusTVariazAutobusDAO.selectByExample(selVa);

			if (!listDto.isEmpty()) {
				for (RebusTVariazAutobusDTO dto : listDto) {
					Long fkAzienda = getFkAzienda(dto.getPrimoTelaio());
					if (fkAzienda != null && idAzienda.compareTo(fkAzienda) == 0) {
						if (Constants.FLAG_SI.equalsIgnoreCase(dto.getFlgVerificatoAzienda())) {
							mess = "Primo Telaio gia presente in archivio! ";
							throw new ErroreGestitoException(mess, "TELFLAGSIPS");
						} else {
							mess = "Primo Telaio gia presente in archivio! ";
							throw new ErroreGestitoException(mess, "TELFLAGNOPS");
						}
					} else {
						mess = "Primo Telaio gia presente in archivio! ";
						throw new ErroreGestitoException(mess, "TELFLAGNOPSA");
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(targa)) {
			selVa = new RebusTVariazAutobusSelector();
			selVa.createCriteria().andNTargaEqualTo(targa).andIdVariazAutobusNotEqualTo(idAutobus);
			listDto = rebusTVariazAutobusDAO.selectByExample(selVa);

			if (!listDto.isEmpty()) {
				for (RebusTVariazAutobusDTO dto : listDto) {
					Long fkAzienda = getFkAzienda(dto.getPrimoTelaio());
					if (fkAzienda != null && idAzienda.compareTo(fkAzienda) == 0) {
						mess = "Targa gia presente in archivio! ";
						throw new ErroreGestitoException(mess, "TARGAPSA");
					} else {
						mess = "Targa gia presente in archivio! ";
						throw new ErroreGestitoException(mess, "TARGAPSNA");
					}
				}
			}
		}
		if (StringUtils.isEmpty(primoTelaio)) {
			throw new InvalidParameterException("telaio uguale a null");
		}
	}

	@Override
	public boolean existsTargaAndPrimoTelaio(AutobusDettagliatoVO autobus) {
		RebusTVariazAutobusSelector example = new RebusTVariazAutobusSelector();
		RebusTVariazAutobusSelector example2 = new RebusTVariazAutobusSelector();
		if (autobus.getTarga() != null) {
			example.createCriteria().andNTargaEqualTo(autobus.getTarga());
			example.or(example2.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio()));
		} else {
			example.createCriteria().andPrimoTelaioEqualTo(autobus.getPrimoTelaio());
		}

		List<RebusTVariazAutobusDTO> listRebusTVar = rebusTVariazAutobusDAO.selectByExample(example);

		return !listRebusTVar.isEmpty();
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

	public boolean correctYear(AutobusDettagliatoVO autobus) {
		Date imm = autobus.getDataPrimaImmatricolazione();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(imm);
		int annoImm = calendar.get(Calendar.YEAR);
		int anno = Integer.parseInt(autobus.getAnnoSostProg());
		if (anno >= annoImm) {
			return false;
		} else {
			return true;
		}

	}

	public boolean year(AutobusVO autobus) {
		Date imm = autobus.getDataPrimaImmatricolazione();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(imm);
		long annoImm = calendar.get(Calendar.YEAR);
		long anno = autobus.getAnnoSostProg();
		if (anno >= annoImm) {
			return false;
		} else {
			return true;
		}

	}
}
