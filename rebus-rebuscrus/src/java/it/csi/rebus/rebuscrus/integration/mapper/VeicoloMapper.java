/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoloDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;

@Component
public class VeicoloMapper extends ParentMapper implements EntityMapper<VeicoloDTO, VeicoloVO> {

	@Override
	public VeicoloVO mapDTOtoVO(VeicoloDTO dto) {
		VeicoloVO vo = new VeicoloVO();
		vo.setIdVariazAutobus(dto.getIdVariazAutobus());
		vo.setPrimoTelaio(dto.getPrimoTelaio());
		vo.setnTarga(dto.getnTarga());
		vo.setDescClasseAmbEuro(dto.getDescClasseAmbEuro());
		vo.setDescTipoAllestimento(dto.getDescTipoAllestimento());
		vo.setLunghezza(dto.getLunghezza());
		vo.setMarca(dto.getMarca());
		vo.setModello(dto.getModello());
		vo.setDataPrimaImmatricolazione(dto.getDataPrimaImmatricolazione());
		vo.setDataUltimaImmatricolazione(dto.getDataUltimaImmatricolazione());
		vo.setSelected(false);
		return vo;
	}

	@Override
	public VeicoloDTO mapVOtoDTO(VeicoloVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
