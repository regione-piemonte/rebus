/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.util.ArrayList;
import java.util.List;

public class SirtplcRAmbTipServizioSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public SirtplcRAmbTipServizioSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
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

		public Criteria andIdAmbTipServizioIsNull() {
			addCriterion("id_amb_tip_servizio is null");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioIsNotNull() {
			addCriterion("id_amb_tip_servizio is not null");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioEqualTo(Long value) {
			addCriterion("id_amb_tip_servizio =", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioNotEqualTo(Long value) {
			addCriterion("id_amb_tip_servizio <>", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioGreaterThan(Long value) {
			addCriterion("id_amb_tip_servizio >", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioGreaterThanOrEqualTo(Long value) {
			addCriterion("id_amb_tip_servizio >=", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioLessThan(Long value) {
			addCriterion("id_amb_tip_servizio <", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioLessThanOrEqualTo(Long value) {
			addCriterion("id_amb_tip_servizio <=", value, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioIn(List<Long> values) {
			addCriterion("id_amb_tip_servizio in", values, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioNotIn(List<Long> values) {
			addCriterion("id_amb_tip_servizio not in", values, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioBetween(Long value1, Long value2) {
			addCriterion("id_amb_tip_servizio between", value1, value2, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbTipServizioNotBetween(Long value1, Long value2) {
			addCriterion("id_amb_tip_servizio not between", value1, value2, "idAmbTipServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioIsNull() {
			addCriterion("id_ambito_servizio is null");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioIsNotNull() {
			addCriterion("id_ambito_servizio is not null");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioEqualTo(Long value) {
			addCriterion("id_ambito_servizio =", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioNotEqualTo(Long value) {
			addCriterion("id_ambito_servizio <>", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioGreaterThan(Long value) {
			addCriterion("id_ambito_servizio >", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioGreaterThanOrEqualTo(Long value) {
			addCriterion("id_ambito_servizio >=", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioLessThan(Long value) {
			addCriterion("id_ambito_servizio <", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioLessThanOrEqualTo(Long value) {
			addCriterion("id_ambito_servizio <=", value, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioIn(List<Long> values) {
			addCriterion("id_ambito_servizio in", values, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioNotIn(List<Long> values) {
			addCriterion("id_ambito_servizio not in", values, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioBetween(Long value1, Long value2) {
			addCriterion("id_ambito_servizio between", value1, value2, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdAmbitoServizioNotBetween(Long value1, Long value2) {
			addCriterion("id_ambito_servizio not between", value1, value2, "idAmbitoServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioIsNull() {
			addCriterion("id_tipologia_servizio is null");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioIsNotNull() {
			addCriterion("id_tipologia_servizio is not null");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioEqualTo(Long value) {
			addCriterion("id_tipologia_servizio =", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioNotEqualTo(Long value) {
			addCriterion("id_tipologia_servizio <>", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioGreaterThan(Long value) {
			addCriterion("id_tipologia_servizio >", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioGreaterThanOrEqualTo(Long value) {
			addCriterion("id_tipologia_servizio >=", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioLessThan(Long value) {
			addCriterion("id_tipologia_servizio <", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioLessThanOrEqualTo(Long value) {
			addCriterion("id_tipologia_servizio <=", value, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioIn(List<Long> values) {
			addCriterion("id_tipologia_servizio in", values, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioNotIn(List<Long> values) {
			addCriterion("id_tipologia_servizio not in", values, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioBetween(Long value1, Long value2) {
			addCriterion("id_tipologia_servizio between", value1, value2, "idTipologiaServizio");
			return (Criteria) this;
		}

		public Criteria andIdTipologiaServizioNotBetween(Long value1, Long value2) {
			addCriterion("id_tipologia_servizio not between", value1, value2, "idTipologiaServizio");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtplc_r_amb_tip_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
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
     * This class corresponds to the database table sirtplc_r_amb_tip_servizio
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 21 14:09:21 CEST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}