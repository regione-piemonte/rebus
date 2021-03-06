/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RebuscTCostoFornituraSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public RebuscTCostoFornituraSelector() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdCostoFornituraIsNull() {
			addCriterion("id_costo_fornitura is null");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraIsNotNull() {
			addCriterion("id_costo_fornitura is not null");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraEqualTo(Long value) {
			addCriterion("id_costo_fornitura =", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraNotEqualTo(Long value) {
			addCriterion("id_costo_fornitura <>", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraGreaterThan(Long value) {
			addCriterion("id_costo_fornitura >", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraGreaterThanOrEqualTo(Long value) {
			addCriterion("id_costo_fornitura >=", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraLessThan(Long value) {
			addCriterion("id_costo_fornitura <", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraLessThanOrEqualTo(Long value) {
			addCriterion("id_costo_fornitura <=", value, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraIn(List<Long> values) {
			addCriterion("id_costo_fornitura in", values, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraNotIn(List<Long> values) {
			addCriterion("id_costo_fornitura not in", values, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraBetween(Long value1, Long value2) {
			addCriterion("id_costo_fornitura between", value1, value2, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdCostoFornituraNotBetween(Long value1, Long value2) {
			addCriterion("id_costo_fornitura not between", value1, value2, "idCostoFornitura");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneIsNull() {
			addCriterion("id_doc_contribuzione is null");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneIsNotNull() {
			addCriterion("id_doc_contribuzione is not null");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneEqualTo(Long value) {
			addCriterion("id_doc_contribuzione =", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneNotEqualTo(Long value) {
			addCriterion("id_doc_contribuzione <>", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneGreaterThan(Long value) {
			addCriterion("id_doc_contribuzione >", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneGreaterThanOrEqualTo(Long value) {
			addCriterion("id_doc_contribuzione >=", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneLessThan(Long value) {
			addCriterion("id_doc_contribuzione <", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneLessThanOrEqualTo(Long value) {
			addCriterion("id_doc_contribuzione <=", value, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneIn(List<Long> values) {
			addCriterion("id_doc_contribuzione in", values, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneNotIn(List<Long> values) {
			addCriterion("id_doc_contribuzione not in", values, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneBetween(Long value1, Long value2) {
			addCriterion("id_doc_contribuzione between", value1, value2, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andIdDocContribuzioneNotBetween(Long value1, Long value2) {
			addCriterion("id_doc_contribuzione not between", value1, value2, "idDocContribuzione");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegIsNull() {
			addCriterion("costo_ammissibile_reg is null");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegIsNotNull() {
			addCriterion("costo_ammissibile_reg is not null");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegEqualTo(Double value) {
			addCriterion("costo_ammissibile_reg =", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegNotEqualTo(Double value) {
			addCriterion("costo_ammissibile_reg <>", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegGreaterThan(Double value) {
			addCriterion("costo_ammissibile_reg >", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegGreaterThanOrEqualTo(Double value) {
			addCriterion("costo_ammissibile_reg >=", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegLessThan(Double value) {
			addCriterion("costo_ammissibile_reg <", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegLessThanOrEqualTo(Double value) {
			addCriterion("costo_ammissibile_reg <=", value, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegIn(List<Double> values) {
			addCriterion("costo_ammissibile_reg in", values, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegNotIn(List<Double> values) {
			addCriterion("costo_ammissibile_reg not in", values, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegBetween(Double value1, Double value2) {
			addCriterion("costo_ammissibile_reg between", value1, value2, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileRegNotBetween(Double value1, Double value2) {
			addCriterion("costo_ammissibile_reg not between", value1, value2, "costoAmmissibileReg");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfIsNull() {
			addCriterion("costo_ammissibile_ff is null");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfIsNotNull() {
			addCriterion("costo_ammissibile_ff is not null");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfEqualTo(Double value) {
			addCriterion("costo_ammissibile_ff =", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfNotEqualTo(Double value) {
			addCriterion("costo_ammissibile_ff <>", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfGreaterThan(Double value) {
			addCriterion("costo_ammissibile_ff >", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfGreaterThanOrEqualTo(Double value) {
			addCriterion("costo_ammissibile_ff >=", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfLessThan(Double value) {
			addCriterion("costo_ammissibile_ff <", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfLessThanOrEqualTo(Double value) {
			addCriterion("costo_ammissibile_ff <=", value, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfIn(List<Double> values) {
			addCriterion("costo_ammissibile_ff in", values, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfNotIn(List<Double> values) {
			addCriterion("costo_ammissibile_ff not in", values, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfBetween(Double value1, Double value2) {
			addCriterion("costo_ammissibile_ff between", value1, value2, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andCostoAmmissibileFfNotBetween(Double value1, Double value2) {
			addCriterion("costo_ammissibile_ff not between", value1, value2, "costoAmmissibileFf");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoIsNull() {
			addCriterion("id_utente_aggiornamento is null");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoIsNotNull() {
			addCriterion("id_utente_aggiornamento is not null");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoEqualTo(Long value) {
			addCriterion("id_utente_aggiornamento =", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoNotEqualTo(Long value) {
			addCriterion("id_utente_aggiornamento <>", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoGreaterThan(Long value) {
			addCriterion("id_utente_aggiornamento >", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoGreaterThanOrEqualTo(Long value) {
			addCriterion("id_utente_aggiornamento >=", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoLessThan(Long value) {
			addCriterion("id_utente_aggiornamento <", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoLessThanOrEqualTo(Long value) {
			addCriterion("id_utente_aggiornamento <=", value, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoIn(List<Long> values) {
			addCriterion("id_utente_aggiornamento in", values, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoNotIn(List<Long> values) {
			addCriterion("id_utente_aggiornamento not in", values, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoBetween(Long value1, Long value2) {
			addCriterion("id_utente_aggiornamento between", value1, value2, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andIdUtenteAggiornamentoNotBetween(Long value1, Long value2) {
			addCriterion("id_utente_aggiornamento not between", value1, value2, "idUtenteAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoIsNull() {
			addCriterion("data_aggiornamento is null");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoIsNotNull() {
			addCriterion("data_aggiornamento is not null");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoEqualTo(Date value) {
			addCriterion("data_aggiornamento =", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoNotEqualTo(Date value) {
			addCriterion("data_aggiornamento <>", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoGreaterThan(Date value) {
			addCriterion("data_aggiornamento >", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoGreaterThanOrEqualTo(Date value) {
			addCriterion("data_aggiornamento >=", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoLessThan(Date value) {
			addCriterion("data_aggiornamento <", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoLessThanOrEqualTo(Date value) {
			addCriterion("data_aggiornamento <=", value, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoIn(List<Date> values) {
			addCriterion("data_aggiornamento in", values, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoNotIn(List<Date> values) {
			addCriterion("data_aggiornamento not in", values, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoBetween(Date value1, Date value2) {
			addCriterion("data_aggiornamento between", value1, value2, "dataAggiornamento");
			return (Criteria) this;
		}

		public Criteria andDataAggiornamentoNotBetween(Date value1, Date value2) {
			addCriterion("data_aggiornamento not between", value1, value2, "dataAggiornamento");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusc_t_costo_fornitura
     *
     * @mbg.generated do_not_delete_during_merge Tue Nov 23 11:58:17 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}