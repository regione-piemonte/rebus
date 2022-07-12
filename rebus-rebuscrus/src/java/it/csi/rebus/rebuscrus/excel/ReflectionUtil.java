/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;

import it.csi.rebus.rebuscrus.common.exception.RebusException;
import it.csi.rebus.rebuscrus.excel.Validator.Combo;
import it.csi.rebus.rebuscrus.integration.dto.custom.AutobusDto;
import it.csi.rebus.rebuscrus.util.UtilRebus;

/*
 * L'utente ha fatto l'upload di un file excel
 * Questa classe serve per leggere, in modo dinamico i dati che si trovano nel file excel e popolare un dto
 * 
 */
public class ReflectionUtil {

	//Logger appliativo
	private static final Logger LOGGER = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".ReflectionUtil");

	/*
	 * Setta un attributo dell'autobus
	 */
	public void setParam(AutobusDto autobus, Validator validator, Cella cella) {
		try {
			// Recupero la classe su cui devo valorizzare gli attributi
			Class cls = Class.forName("it.csi.rebus.rebuscrus.integration.dto.custom.AutobusDto");
			/*
			 * Ciclo su tutti i metodi della classe, per capire quello legato al
			 * contenuto della celal excel
			 */
			for (Method method : cls.getMethods()) {
				// trovo solo i metodi che iniziano con set
				if (method.getName().equalsIgnoreCase("set" + validator.getItemName())) {
					//verifico con cosa fare il set del metodo
					checkMethod(method, autobus, validator, cella);
					//posso interrompere il ciclo
					break;
				}
			}
		} catch (ClassNotFoundException c) {
			// Errore di ClassNotFoundException
			LOGGER.error("ReflectionUtil::setParam ClassNotFoundException: " + c.getMessage());
			//Rilancio una RebusException
			throw new RebusException(c);
		}
	}

	// Cerca il metodo per cui fare la set
	private void checkMethod(Method method, AutobusDto autobus, Validator validator, Cella cella) {
		// Caso Stringa
		checkString(method, autobus, validator, cella);
		// Caso numero intero
		checkLong(method, autobus, validator, cella);
		// Caso numero reale
		checkDouble(method, autobus, validator, cella);
		// Caso data
		checkDate(method, autobus, validator, cella);
	}

	// Verifica se e' una stringa
	private void checkString(Method method, AutobusDto autobus, Validator validator, Cella cella) {
		try {
			if (validator instanceof StringValidator) {
				// Le selezioni multiple afferiscono alla stringa,
				// quindi devo veficare se e' una seleziona multipla o
				// una stringa
				if (validator.getCombo() == Combo.NO_COMBO) {
					// Siamo nel caso di stringa vera e propria
					if (cella.getStrValue() != null) {
						method.invoke(autobus, new String(cella.getStrValue()));
					}
				} else {
					// Caso di seleziona multipla
					if (cella.getLongValue() != null) {
						method.invoke(autobus, new Long(cella.getLongValue()));
					}
				}
			}
		} catch (IllegalAccessException eAcc) {
			// Errore di IllegalAccessException
			LOGGER.error("ReflectionUtil::checkString IllegalAccessException: " + eAcc.getMessage());
			//Rilancio una RebusException
			throw new RebusException(eAcc);
		} catch (IllegalArgumentException eArg) {
			// Errore di IllegalArgumentException
			LOGGER.error("ReflectionUtil::checkString IllegalArgumentException: " + eArg.getMessage());
			//Rilancio una RebusException
			throw new RebusException(eArg);
		} catch (InvocationTargetException eTar) {
			// Errore InvocationTargetException
			LOGGER.error("ReflectionUtil::checkString InvocationTargetException: " + eTar.getMessage());
			//Rilancio una RebusException
			throw new RebusException(eTar);
		}
	}

	// Verifica se e' un Long
	private void checkLong(Method method, AutobusDto autobus, Validator validator, Cella cella) {
		try {
			if (validator instanceof LongValidator && cella.getLongValue() != null) {
				method.invoke(autobus, new Long(cella.getLongValue()));
			}
		} catch (IllegalAccessException eAcc) {
			// Errore di IllegalAccessException
			LOGGER.error("ReflectionUtil::checkLong IllegalAccessException: " + eAcc.getMessage()+" - "+UtilRebus.getStackTrace(eAcc));
			//Rilancio una RebusException
			throw new RebusException(eAcc);
		} catch (IllegalArgumentException eArg) {
			// Errore di IllegalArgumentException
			LOGGER.error("ReflectionUtil::checkLong IllegalArgumentException: " + eArg.getMessage()+" - "+UtilRebus.getStackTrace(eArg));
			//Rilancio una RebusException
			throw new RebusException(eArg);
		} catch (InvocationTargetException eTar) {
			// Errore InvocationTargetException
			LOGGER.error("ReflectionUtil::checkLong InvocationTargetException: " + eTar.getMessage()+" - "+UtilRebus.getStackTrace(eTar));
			//Rilancio una RebusException
			throw new RebusException(eTar);
		}
	}

	// Verifica se e' un Double
	private void checkDouble(Method method, AutobusDto autobus, Validator validator, Cella cella) {
		try {
			if (validator instanceof DoubleValidator && cella.getDoubleValue() != null) {
				method.invoke(autobus, new Double(cella.getDoubleValue()));
			}
		} catch (IllegalAccessException eAcc) {
			// Errore di IllegalAccessException
			LOGGER.error("ReflectionUtil::checkDouble IllegalAccessException: " + eAcc.getMessage()+" - "+UtilRebus.getStackTrace(eAcc));
			//Rilancio una RebusException
			throw new RebusException(eAcc);
		} catch (IllegalArgumentException eArg) {
			// Errore di IllegalArgumentException
			LOGGER.error("ReflectionUtil::checkDouble IllegalArgumentException: " + eArg.getMessage()+" - "+UtilRebus.getStackTrace(eArg));
			//Rilancio una RebusException
			throw new RebusException(eArg);
		} catch (InvocationTargetException eTar) {
			// Errore InvocationTargetException
			LOGGER.error("ReflectionUtil::checkDouble InvocationTargetException: " + eTar.getMessage()+" - "+UtilRebus.getStackTrace(eTar));
			//Rilancio una RebusException
			throw new RebusException(eTar);
		}
	}

	// Verifica se e' una Date
	private void checkDate(Method method, AutobusDto autobus, Validator validator, Cella cella) {
		try {
			if (validator instanceof DateValidator && cella.getDateValue() != null) {
				method.invoke(autobus, new Date(cella.getDateValue().getTime()));
			}
		} catch (IllegalAccessException eAcc) {
			// Errore di IllegalAccessException
			LOGGER.error("ReflectionUtil::checkDate IllegalAccessException: " + eAcc.getMessage()+" - "+UtilRebus.getStackTrace(eAcc));
			//Rilancio una RebusException
			throw new RebusException(eAcc);
		} catch (IllegalArgumentException eArg) {
			// Errore di IllegalArgumentException
			LOGGER.error("ReflectionUtil::checkDate IllegalArgumentException: " + eArg.getMessage()+" - "+UtilRebus.getStackTrace(eArg));
			//Rilancio una RebusException
			throw new RebusException(eArg);
		} catch (InvocationTargetException eTar) {
			// Errore InvocationTargetException
			LOGGER.error("ReflectionUtil::checkDate InvocationTargetException: " + eTar.getMessage()+" - "+UtilRebus.getStackTrace(eTar));
			//Rilancio una RebusException
			throw new RebusException(eTar);
		}
	}
}
