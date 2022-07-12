/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusSmallDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.CronologiaDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcVO;

@Component
public class AutobusMapper extends ParentMapper implements EntityMapper<VAutobusSmallDTO, AutobusVO> {

	@Override
	public AutobusVO mapDTOtoVO(VAutobusSmallDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusVO.class);
	}

	@Override
	public VAutobusSmallDTO mapVOtoDTO(AutobusVO vo) {
		if (vo == null)
			return null;
		return getDozerMapper().map(vo, VAutobusSmallDTO.class);
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusDettagliatoVO.class);
	}
	
	
	/*
	 * public static AutobusVO mapDTOtoVO(CronologiaDTO dto) { AutobusVO vo = new
	 * AutobusVO(); vo.setDataAlienazione(dto.getDataAlienazione());
	 * vo.setDenominazioneBreve(dto.getDenominazioneBreve());
	 * vo.setPrimoTelaio(dto.getPrimoTelaio()); return vo; }
	 */

}
