/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RebuspDMotorizzazioneSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public RebuspDMotorizzazioneSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
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
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
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

		public Criteria andIdMotorizzazioneIsNull() {
			addCriterion("id_motorizzazione is null");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneIsNotNull() {
			addCriterion("id_motorizzazione is not null");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneEqualTo(Long value) {
			addCriterion("id_motorizzazione =", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneNotEqualTo(Long value) {
			addCriterion("id_motorizzazione <>", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneGreaterThan(Long value) {
			addCriterion("id_motorizzazione >", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneGreaterThanOrEqualTo(Long value) {
			addCriterion("id_motorizzazione >=", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneLessThan(Long value) {
			addCriterion("id_motorizzazione <", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneLessThanOrEqualTo(Long value) {
			addCriterion("id_motorizzazione <=", value, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneIn(List<Long> values) {
			addCriterion("id_motorizzazione in", values, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneNotIn(List<Long> values) {
			addCriterion("id_motorizzazione not in", values, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneBetween(Long value1, Long value2) {
			addCriterion("id_motorizzazione between", value1, value2, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIdMotorizzazioneNotBetween(Long value1, Long value2) {
			addCriterion("id_motorizzazione not between", value1, value2, "idMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneIsNull() {
			addCriterion("desc_motorizzazione is null");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneIsNotNull() {
			addCriterion("desc_motorizzazione is not null");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneEqualTo(String value) {
			addCriterion("desc_motorizzazione =", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneNotEqualTo(String value) {
			addCriterion("desc_motorizzazione <>", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneGreaterThan(String value) {
			addCriterion("desc_motorizzazione >", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneGreaterThanOrEqualTo(String value) {
			addCriterion("desc_motorizzazione >=", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneLessThan(String value) {
			addCriterion("desc_motorizzazione <", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneLessThanOrEqualTo(String value) {
			addCriterion("desc_motorizzazione <=", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneLike(String value) {
			addCriterion("desc_motorizzazione like", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneNotLike(String value) {
			addCriterion("desc_motorizzazione not like", value, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneIn(List<String> values) {
			addCriterion("desc_motorizzazione in", values, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneNotIn(List<String> values) {
			addCriterion("desc_motorizzazione not in", values, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneBetween(String value1, String value2) {
			addCriterion("desc_motorizzazione between", value1, value2, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneNotBetween(String value1, String value2) {
			addCriterion("desc_motorizzazione not between", value1, value2, "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIndirizzoIsNull() {
			addCriterion("indirizzo is null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoIsNotNull() {
			addCriterion("indirizzo is not null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoEqualTo(String value) {
			addCriterion("indirizzo =", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoNotEqualTo(String value) {
			addCriterion("indirizzo <>", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoGreaterThan(String value) {
			addCriterion("indirizzo >", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoGreaterThanOrEqualTo(String value) {
			addCriterion("indirizzo >=", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoLessThan(String value) {
			addCriterion("indirizzo <", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoLessThanOrEqualTo(String value) {
			addCriterion("indirizzo <=", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoLike(String value) {
			addCriterion("indirizzo like", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoNotLike(String value) {
			addCriterion("indirizzo not like", value, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoIn(List<String> values) {
			addCriterion("indirizzo in", values, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoNotIn(List<String> values) {
			addCriterion("indirizzo not in", values, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoBetween(String value1, String value2) {
			addCriterion("indirizzo between", value1, value2, "indirizzo");
			return (Criteria) this;
		}

		public Criteria andIndirizzoNotBetween(String value1, String value2) {
			addCriterion("indirizzo not between", value1, value2, "indirizzo");
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

		public Criteria andPecIsNull() {
			addCriterion("pec is null");
			return (Criteria) this;
		}

		public Criteria andPecIsNotNull() {
			addCriterion("pec is not null");
			return (Criteria) this;
		}

		public Criteria andPecEqualTo(String value) {
			addCriterion("pec =", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecNotEqualTo(String value) {
			addCriterion("pec <>", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecGreaterThan(String value) {
			addCriterion("pec >", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecGreaterThanOrEqualTo(String value) {
			addCriterion("pec >=", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecLessThan(String value) {
			addCriterion("pec <", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecLessThanOrEqualTo(String value) {
			addCriterion("pec <=", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecLike(String value) {
			addCriterion("pec like", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecNotLike(String value) {
			addCriterion("pec not like", value, "pec");
			return (Criteria) this;
		}

		public Criteria andPecIn(List<String> values) {
			addCriterion("pec in", values, "pec");
			return (Criteria) this;
		}

		public Criteria andPecNotIn(List<String> values) {
			addCriterion("pec not in", values, "pec");
			return (Criteria) this;
		}

		public Criteria andPecBetween(String value1, String value2) {
			addCriterion("pec between", value1, value2, "pec");
			return (Criteria) this;
		}

		public Criteria andPecNotBetween(String value1, String value2) {
			addCriterion("pec not between", value1, value2, "pec");
			return (Criteria) this;
		}

		public Criteria andDescMotorizzazioneLikeInsensitive(String value) {
			addCriterion("upper(desc_motorizzazione) like", value.toUpperCase(), "descMotorizzazione");
			return (Criteria) this;
		}

		public Criteria andIndirizzoLikeInsensitive(String value) {
			addCriterion("upper(indirizzo) like", value.toUpperCase(), "indirizzo");
			return (Criteria) this;
		}

		public Criteria andPecLikeInsensitive(String value) {
			addCriterion("upper(pec) like", value.toUpperCase(), "pec");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusp_d_motorizzazione
	 * @mbg.generated  Mon Nov 02 13:29:57 CET 2020
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
     * This class corresponds to the database table rebusp_d_motorizzazione
     *
     * @mbg.generated do_not_delete_during_merge Thu Jan 23 11:27:40 CET 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}