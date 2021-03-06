/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RebuscTAttoAssegnazioneSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public RebuscTAttoAssegnazioneSelector() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
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

		protected void addCriterionForJDBCDate(String condition, Date value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()), property);
		}

		protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property + " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
		}

		public Criteria andIdAttoAssegnazioneIsNull() {
			addCriterion("id_atto_assegnazione is null");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneIsNotNull() {
			addCriterion("id_atto_assegnazione is not null");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneEqualTo(Long value) {
			addCriterion("id_atto_assegnazione =", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneNotEqualTo(Long value) {
			addCriterion("id_atto_assegnazione <>", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneGreaterThan(Long value) {
			addCriterion("id_atto_assegnazione >", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneGreaterThanOrEqualTo(Long value) {
			addCriterion("id_atto_assegnazione >=", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneLessThan(Long value) {
			addCriterion("id_atto_assegnazione <", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneLessThanOrEqualTo(Long value) {
			addCriterion("id_atto_assegnazione <=", value, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneIn(List<Long> values) {
			addCriterion("id_atto_assegnazione in", values, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneNotIn(List<Long> values) {
			addCriterion("id_atto_assegnazione not in", values, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneBetween(Long value1, Long value2) {
			addCriterion("id_atto_assegnazione between", value1, value2, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andIdAttoAssegnazioneNotBetween(Long value1, Long value2) {
			addCriterion("id_atto_assegnazione not between", value1, value2, "idAttoAssegnazione");
			return (Criteria) this;
		}

		public Criteria andDescBreveIsNull() {
			addCriterion("desc_breve is null");
			return (Criteria) this;
		}

		public Criteria andDescBreveIsNotNull() {
			addCriterion("desc_breve is not null");
			return (Criteria) this;
		}

		public Criteria andDescBreveEqualTo(String value) {
			addCriterion("desc_breve =", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveNotEqualTo(String value) {
			addCriterion("desc_breve <>", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveGreaterThan(String value) {
			addCriterion("desc_breve >", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveGreaterThanOrEqualTo(String value) {
			addCriterion("desc_breve >=", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveLessThan(String value) {
			addCriterion("desc_breve <", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveLessThanOrEqualTo(String value) {
			addCriterion("desc_breve <=", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveLike(String value) {
			addCriterion("desc_breve like", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveNotLike(String value) {
			addCriterion("desc_breve not like", value, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveIn(List<String> values) {
			addCriterion("desc_breve in", values, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveNotIn(List<String> values) {
			addCriterion("desc_breve not in", values, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveBetween(String value1, String value2) {
			addCriterion("desc_breve between", value1, value2, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescBreveNotBetween(String value1, String value2) {
			addCriterion("desc_breve not between", value1, value2, "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescEstesaIsNull() {
			addCriterion("desc_estesa is null");
			return (Criteria) this;
		}

		public Criteria andDescEstesaIsNotNull() {
			addCriterion("desc_estesa is not null");
			return (Criteria) this;
		}

		public Criteria andDescEstesaEqualTo(String value) {
			addCriterion("desc_estesa =", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaNotEqualTo(String value) {
			addCriterion("desc_estesa <>", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaGreaterThan(String value) {
			addCriterion("desc_estesa >", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaGreaterThanOrEqualTo(String value) {
			addCriterion("desc_estesa >=", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaLessThan(String value) {
			addCriterion("desc_estesa <", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaLessThanOrEqualTo(String value) {
			addCriterion("desc_estesa <=", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaLike(String value) {
			addCriterion("desc_estesa like", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaNotLike(String value) {
			addCriterion("desc_estesa not like", value, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaIn(List<String> values) {
			addCriterion("desc_estesa in", values, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaNotIn(List<String> values) {
			addCriterion("desc_estesa not in", values, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaBetween(String value1, String value2) {
			addCriterion("desc_estesa between", value1, value2, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDescEstesaNotBetween(String value1, String value2) {
			addCriterion("desc_estesa not between", value1, value2, "descEstesa");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaIsNull() {
			addCriterion("data_inizio_validita is null");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaIsNotNull() {
			addCriterion("data_inizio_validita is not null");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaEqualTo(Date value) {
			addCriterionForJDBCDate("data_inizio_validita =", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaNotEqualTo(Date value) {
			addCriterionForJDBCDate("data_inizio_validita <>", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaGreaterThan(Date value) {
			addCriterionForJDBCDate("data_inizio_validita >", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_inizio_validita >=", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaLessThan(Date value) {
			addCriterionForJDBCDate("data_inizio_validita <", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_inizio_validita <=", value, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaIn(List<Date> values) {
			addCriterionForJDBCDate("data_inizio_validita in", values, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaNotIn(List<Date> values) {
			addCriterionForJDBCDate("data_inizio_validita not in", values, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_inizio_validita between", value1, value2, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataInizioValiditaNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_inizio_validita not between", value1, value2, "dataInizioValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaIsNull() {
			addCriterion("data_fine_validita is null");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaIsNotNull() {
			addCriterion("data_fine_validita is not null");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaEqualTo(Date value) {
			addCriterionForJDBCDate("data_fine_validita =", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaNotEqualTo(Date value) {
			addCriterionForJDBCDate("data_fine_validita <>", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaGreaterThan(Date value) {
			addCriterionForJDBCDate("data_fine_validita >", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_fine_validita >=", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaLessThan(Date value) {
			addCriterionForJDBCDate("data_fine_validita <", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_fine_validita <=", value, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaIn(List<Date> values) {
			addCriterionForJDBCDate("data_fine_validita in", values, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaNotIn(List<Date> values) {
			addCriterionForJDBCDate("data_fine_validita not in", values, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_fine_validita between", value1, value2, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDataFineValiditaNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_fine_validita not between", value1, value2, "dataFineValidita");
			return (Criteria) this;
		}

		public Criteria andDescBreveLikeInsensitive(String value) {
			addCriterion("upper(desc_breve) like", value.toUpperCase(), "descBreve");
			return (Criteria) this;
		}

		public Criteria andDescEstesaLikeInsensitive(String value) {
			addCriterion("upper(desc_estesa) like", value.toUpperCase(), "descEstesa");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
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
     * This class corresponds to the database table rebusc_t_atto_assegnazione
     *
     * @mbg.generated do_not_delete_during_merge Thu Nov 18 15:04:08 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}