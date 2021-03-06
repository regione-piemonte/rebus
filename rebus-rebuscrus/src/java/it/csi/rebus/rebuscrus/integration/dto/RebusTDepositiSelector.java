/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.List;

public class RebusTDepositiSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public RebusTDepositiSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
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

		public Criteria andIdDepositoIsNull() {
			addCriterion("id_deposito is null");
			return (Criteria) this;
		}

		public Criteria andIdDepositoIsNotNull() {
			addCriterion("id_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andIdDepositoEqualTo(Long value) {
			addCriterion("id_deposito =", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoNotEqualTo(Long value) {
			addCriterion("id_deposito <>", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoGreaterThan(Long value) {
			addCriterion("id_deposito >", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoGreaterThanOrEqualTo(Long value) {
			addCriterion("id_deposito >=", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoLessThan(Long value) {
			addCriterion("id_deposito <", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoLessThanOrEqualTo(Long value) {
			addCriterion("id_deposito <=", value, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoIn(List<Long> values) {
			addCriterion("id_deposito in", values, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoNotIn(List<Long> values) {
			addCriterion("id_deposito not in", values, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoBetween(Long value1, Long value2) {
			addCriterion("id_deposito between", value1, value2, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIdDepositoNotBetween(Long value1, Long value2) {
			addCriterion("id_deposito not between", value1, value2, "idDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepIsNull() {
			addCriterion("indirizzo_dep is null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepIsNotNull() {
			addCriterion("indirizzo_dep is not null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepEqualTo(String value) {
			addCriterion("indirizzo_dep =", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepNotEqualTo(String value) {
			addCriterion("indirizzo_dep <>", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepGreaterThan(String value) {
			addCriterion("indirizzo_dep >", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepGreaterThanOrEqualTo(String value) {
			addCriterion("indirizzo_dep >=", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepLessThan(String value) {
			addCriterion("indirizzo_dep <", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepLessThanOrEqualTo(String value) {
			addCriterion("indirizzo_dep <=", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepLike(String value) {
			addCriterion("indirizzo_dep like", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepNotLike(String value) {
			addCriterion("indirizzo_dep not like", value, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepIn(List<String> values) {
			addCriterion("indirizzo_dep in", values, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepNotIn(List<String> values) {
			addCriterion("indirizzo_dep not in", values, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepBetween(String value1, String value2) {
			addCriterion("indirizzo_dep between", value1, value2, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepNotBetween(String value1, String value2) {
			addCriterion("indirizzo_dep not between", value1, value2, "indirizzoDep");
			return (Criteria) this;
		}

		public Criteria andFkComuneIsNull() {
			addCriterion("fk_comune is null");
			return (Criteria) this;
		}

		public Criteria andFkComuneIsNotNull() {
			addCriterion("fk_comune is not null");
			return (Criteria) this;
		}

		public Criteria andFkComuneEqualTo(Long value) {
			addCriterion("fk_comune =", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneNotEqualTo(Long value) {
			addCriterion("fk_comune <>", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneGreaterThan(Long value) {
			addCriterion("fk_comune >", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneGreaterThanOrEqualTo(Long value) {
			addCriterion("fk_comune >=", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneLessThan(Long value) {
			addCriterion("fk_comune <", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneLessThanOrEqualTo(Long value) {
			addCriterion("fk_comune <=", value, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneIn(List<Long> values) {
			addCriterion("fk_comune in", values, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneNotIn(List<Long> values) {
			addCriterion("fk_comune not in", values, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneBetween(Long value1, Long value2) {
			addCriterion("fk_comune between", value1, value2, "fkComune");
			return (Criteria) this;
		}

		public Criteria andFkComuneNotBetween(Long value1, Long value2) {
			addCriterion("fk_comune not between", value1, value2, "fkComune");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepLikeInsensitive(String value) {
			addCriterion("upper(indirizzo_dep) like", value.toUpperCase(), "indirizzoDep");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebus_t_depositi
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
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
     * This class corresponds to the database table rebus_t_depositi
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 21 17:22:07 CEST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}