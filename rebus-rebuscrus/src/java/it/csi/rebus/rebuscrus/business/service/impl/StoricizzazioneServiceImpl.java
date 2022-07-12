/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.rebuscrus.business.service.StoricizzazioneService;
import it.csi.rebus.rebuscrus.integration.dao.RebusRStoriaVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRVarautobusDpDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTStoriaVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRStoriaVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusRVarautobusDpSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.mapper.StoricizzaMapper;

/**
 * @author riccardo.bova
 * @date 02 mar 2018
 */
@Service
public class StoricizzazioneServiceImpl implements StoricizzazioneService {

	@Autowired
	private RebusTStoriaVariazAutobusDAO rebusTStoriaVariazAutobusDAO;

	@Autowired
	private StoricizzaMapper storicizzazioneMapper;

	@Autowired
	RebusTVariazAutobusDAO rebusTVariazAutobusDAO;

	@Autowired
	RebusRVarautobusDpDAO rebusRVarautobusDpDAO;

	@Autowired
	RebusRStoriaVarautobusDpDAO rebusRStoriaVarautobusDpDAO;

	@Override
	public long storicizzaAutobus(RebusTVariazAutobusDTO autobus, Long idUtente, Date now) {

		if (autobus == null)
			throw new InvalidParameterException("AUTOBUS VO UGUALE A NULL");
		if (idUtente == null)
			throw new InvalidParameterException("ID UTENTE UGUALE A NULL");

		RebusTStoriaVariazAutobusDTO record = storicizzazioneMapper.mapTVariazToTStoriaVar(autobus);

		record.setFkUtenteStoria(idUtente);
		record.setDataAggiornamentoStoria(now);

		rebusTStoriaVariazAutobusDAO.insert(record);
		this.storicizzaDispositiviPrevenzione(autobus, record);
		return record.getIdStoriaVariazAutobus();
	}

	private void storicizzaDispositiviPrevenzione(RebusTVariazAutobusDTO autobus, RebusTStoriaVariazAutobusDTO storia) {
		RebusRVarautobusDpSelector example = new RebusRVarautobusDpSelector();
		example.createCriteria().andIdVariazAutobusEqualTo(autobus.getIdVariazAutobus());

		RebusRStoriaVarautobusDpKey storiaDispositivo;

		for (RebusRVarautobusDpKey elem : rebusRVarautobusDpDAO.selectByExample(example)) {
			storiaDispositivo = new RebusRStoriaVarautobusDpKey();
			storiaDispositivo.setIdStoriaVariazAutobus(storia.getIdStoriaVariazAutobus());
			storiaDispositivo.setIdDispositivo(elem.getIdDispositivo());

			rebusRStoriaVarautobusDpDAO.insert(storiaDispositivo);
		}
	}

}
