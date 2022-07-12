/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTMessaggiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoloDTO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.ContestoVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

public interface RicercaDAO {
	public List<AutobusVO> getElencoAutobus(FiltroAutobusVO filtro);

	public Long calcolaNumMessaggi(@Param("idEnte") Long idEnte, @Param("idAzienda") Long idAzienda);

	public RebusTStoriaVariazAutobusDTO trovaDestinatario(RebusTVariazAutobusDTO variazioneAutobus);

	public List<VeicoloDTO> getVeicoli(@Param("idAzienda") Long idAzienda, @Param("idTipoProcedimento") Long idTipoProcedimento, @Param("idVeicoliSelezionati") List<Long> idVeicoliSelezionati);

	public RebusDTipologiaDimensDTO getTipologiaDimensione(@Param("idTipoAllestimento") Long idTipoAllestimento);
	
	public List<RebusTMessaggiDTO> elencoMessaggi(@Param("idEnte") Long idEnte, @Param("idAzienda") Long idAzienda, @Param("idUtente") Long idUtente,@Param("idContesto") Long idContesto);

	public ContestoVO getContestoHome(@Param("idEnte") Long idEnte, @Param("idAzienda") Long idAzienda, @Param("idUtente") Long idUtente);
	
	public Boolean checkMessaggiNonLetti(@Param("idEnte") Long idEnte, @Param("idAzienda") Long idAzienda, @Param("idUtente") Long idUtente,@Param("idContesto") Long idContesto);

}