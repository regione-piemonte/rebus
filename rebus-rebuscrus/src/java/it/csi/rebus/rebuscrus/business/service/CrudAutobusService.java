/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.DepositoVO;
import it.csi.rebus.rebuscrus.vo.DocVariazAutobusVO;
import it.csi.rebus.rebuscrus.vo.MisurazioniVO;
import it.csi.rebus.rebuscrus.vo.PortabiciAutobusVO;
import it.csi.rebus.rebuscrus.vo.SistemaLocalizzazioneVO;
import it.csi.rebus.rebuscrus.vo.SistemaVideosorveglianzaVO;
import it.csi.rebus.rebuscrus.vo.TipologiaDimensioneVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
public interface CrudAutobusService {

	void eliminaAutobus(String[] idsVariazAutobus);

	void updateAutobus(AutobusVO autobus, Boolean isUploadB);

	AutobusVO dettaglioAutobus(Long id, String action);

	Long inserisciAutobus(AutobusDettagliatoVO autobus);

	List<VeicoloVO> getVeicoliInserisci(Long idTipoProcedimento);

	List<VeicoloVO> getVeicoliModifica(Long idProcedimento, Long idTipoProcedimento);

	TipologiaDimensioneVO tipologiaDimensione(Long idTipoAllestimento) throws Exception;

	List<DepositoVO> getDepositi(Long idAzienda);

	DepositoVO getDepositoById(Long idDeposito);
	
	List<MisurazioniVO> getMisurazioni(String primoTelaio);
	
	List<String> getAutobusForContribuzioneAzienda();
	
	List<PortabiciAutobusVO> getAllPortabiciForAutobus();
	
	List<SistemaLocalizzazioneVO> getAllSistemaLocalizzazioneForAutobus();
	
	List<SistemaVideosorveglianzaVO> getAllSistemaVideosorveglianzaForAutobus();
	
	List<DocVariazAutobusVO> getDocVariazAutobusForInfo(Long idVa);

}
