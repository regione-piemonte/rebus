/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.service.LuoghiService;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.custom.SoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.UbicazioneVO;

@Component
public class SoggettoGiuridicoMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, SoggettoGiuridicoVO> {

	@Autowired
	private LuoghiService luoghiService;

	@Override
	public SoggettoGiuridicoVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		SoggettoGiuridicoVO vo = new SoggettoGiuridicoVO();
		vo.setId(dto.getIdSoggettoGiuridico());
		vo.setIdTipoSoggettoGiuridico(dto.getIdTipoSogGiuridico());
		if (vo.getIdTipoSoggettoGiuridico().equals(Constants.ID_TIPO_SOG_GIURIDICO_ENTE_LONG)) {
			vo.setIdTipoEnte(dto.getIdTipoEnte());
			vo.setDescrizione(dto.getDescrizione());
			//vo.setCodRegionale("SGE"+ String.format("%04d", dto.getIdSoggettoGiuridico()));
		} else {
			vo.setIdNaturaGiuridica(dto.getIdNaturaGiuridica());
			vo.setDenomAAEP(dto.getDenominazioneAaep());
			vo.setNomeRappresentanteLegale(dto.getNomeRapprLegale());
			vo.setCognomeRappresentanteLegale(dto.getCognomeRapprLegale());
			vo.setDataInizio(dto.getDataInizioAttivita());
			vo.setDataCessazione(dto.getDataCessazione());
			//vo.setCodRegionale(vo.getIdTipoSoggettoGiuridico().equals(Constants.ID_TIPO_SOG_GIURIDICO_AZIENDA_LONG) ?  ("SGA"+ String.format("%04d", dto.getIdSoggettoGiuridico())) : ("SGD"+ String.format("%04d", dto.getIdSoggettoGiuridico())));
		}
		vo.setCodRegionale(dto.getCodIdRegionale());
		vo.setCodCsrBip(dto.getCodCsrBip());
		vo.setDenomBreve(dto.getDenominazioneBreve());
		vo.setDenominazioneRicerca(dto.getDenominazioneRicerca());
		vo.setDenomOsservatorioNaz(dto.getDenomSoggettoGiuridico());
		vo.setAziendaCessata((vo.getDataCessazione() != null && vo.getDataCessazione().before(new Date())) ? true : false);
		vo.setCodOsservatorioNaz(dto.getCodOsservatorioNaz());
		vo.setPartitaIva(dto.getPartitaIva());
		vo.setCodiceFiscale(dto.getCodiceFiscale());
		vo.setNote(dto.getNote());
		vo.setNote(dto.getNote());
		vo.setLogo(dto.getLogo());
		if (dto.getLogo() != null)
			vo.setIsLogoUploaded(true);
		else
			vo.setIsLogoUploaded(false);
		UbicazioneVO ubicazioneSede = new UbicazioneVO();
		ubicazioneSede.setTop(dto.getToponimoSedeLegale());
		ubicazioneSede.setIndirizzo(dto.getIndirizzoSedeLegale());
		ubicazioneSede.setCivico(dto.getNumCivicoSedeLegale());
		ubicazioneSede.setIdComune(dto.getIdComuneSedeLegale());

		if (dto instanceof SoggettoGiuridicoDTO) {
			ubicazioneSede.setIdProvincia(((SoggettoGiuridicoDTO) dto).getIdProvinciaSedeLegale());
		} else if (dto.getIdComuneSedeLegale() != null) {
			ubicazioneSede.setIdProvincia(luoghiService.getProvinciaByIdComune(dto.getIdComuneSedeLegale()).getId());
		}
		ubicazioneSede.setCap(dto.getCapSedeLegale());
		vo.setUbicazioneSede(ubicazioneSede);
		vo.setTelefonoSede(dto.getTelefonoSedeLegale());
		vo.setFax(dto.getFaxSedeLegale());
		vo.setEmail(dto.getEmailSedeLegale());
		vo.setPec(dto.getPecSedeLegale());
		vo.setIndirizzoWeb(dto.getIndirizzoWeb());
		vo.setNumeroVerde(dto.getNumeroVerde());
		vo.setDataAggiornamento(dto.getDataAggiornamento());
		return vo;
	}

	@Override
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(SoggettoGiuridicoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
