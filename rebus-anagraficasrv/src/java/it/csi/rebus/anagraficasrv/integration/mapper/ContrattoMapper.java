/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.vo.ContrattoVO;

@Component
public class ContrattoMapper extends ParentMapper implements EntityMapper<SirtplcTContrattoDTO, ContrattoVO> {

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;

	@Override
	public ContrattoVO mapDTOtoVO(SirtplcTContrattoDTO dto) {
		ContrattoVO vo = new ContrattoVO();
		vo.setIdContratto(dto.getIdContratto());
		vo.setIdContrattoPadre(dto.getIdContrattoPadre());
		vo.setCodIdNazionale(dto.getCodIdNazionale());
		vo.setNumRepertorio(dto.getNumRepertorio());
		vo.setCodRegionale(dto.getCodIdRegionale());
		//vo.setCodRegionale("CDS"+ String.format("%04d", dto.getIdContratto()));
		vo.setIdSogGiuridCommittente(dto.getIdSogGiuridCommittente());
		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(dto.getIdSogGiuridCommittente());
		if (sirtplaTSoggettoGiuridicoDTO != null) {
			vo.setIdNaturaGiuridicaCommittente(sirtplaTSoggettoGiuridicoDTO.getIdNaturaGiuridica());
		}
		vo.setIdSogGiuridEsecutore(dto.getIdSogGiuridEsecutore());
		vo.setIdTipoSogGiuridEsec(dto.getIdTipoSogGiuridEsec());
		sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(dto.getIdSogGiuridEsecutore());
		if (sirtplaTSoggettoGiuridicoDTO != null) {
			vo.setIdNaturaGiuridicaEsec(sirtplaTSoggettoGiuridicoDTO.getIdNaturaGiuridica());
		}
		vo.setIdTipoRaggrSogGiuridEsec(dto.getIdTipoRaggrSogGiuridEsec());
		vo.setIdTipoAffidamento(dto.getIdTipoAffidamento());
		vo.setIdModalitaAffidamento(dto.getIdModalitaAffidamento());
		vo.setAccordoProgramma(dto.getAccordoProgramma());
		vo.setGrossCost(dto.getGrossCost());
		vo.setCIG(dto.getCig());
		vo.setDataStipula(dto.getDataStipula());
		vo.setDescContratto(dto.getDescContratto());
		vo.setIdBacino(dto.getIdBacino());
		vo.setDataInizioValidita(dto.getDataInizioValidita());
		vo.setDataFineValidita(dto.getDataFineValidita());
		vo.setDataAggiornamento(dto.getDataAggiornamento());
		return vo;
	}

	@Override
	public SirtplcTContrattoDTO mapVOtoDTO(ContrattoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
