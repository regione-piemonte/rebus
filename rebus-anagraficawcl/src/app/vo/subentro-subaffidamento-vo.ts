/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { SoggettoSubentroVO } from './soggetto-subentro-vo';
import { TipoSostituzioneVO } from './drop-down-menu-vo';

export class SubentroSubaffidamentoVO {
    id: number;
    soggettoContraente: SoggettoSubentroVO;
    contraenteGroup: string;
    soggettoSubentrante: SoggettoSubentroVO;
    tipoSostituzione: TipoSostituzioneVO;
    atto: string;
    data: Date;
    dataFine: Date;
}