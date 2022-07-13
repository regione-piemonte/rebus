/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RebuscTOrdineAcquistoSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public RebuscTOrdineAcquistoSelector() {
		oredCriteria = new ArrayList<>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_ordine_acquisto
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_ordine_acquisto
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

		public Criteria andIdOrdineAcquistoIsNull() {
			addCriterion("id_ordine_acquisto is null");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoIsNotNull() {
			addCriterion("id_ordine_acquisto is not null");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoEqualTo(Long value) {
			addCriterion("id_ordine_acquisto =", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoNotEqualTo(Long value) {
			addCriterion("id_ordine_acquisto <>", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoGreaterThan(Long value) {
			addCriterion("id_ordine_acquisto >", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoGreaterThanOrEqualTo(Long value) {
			addCriterion("id_ordine_acquisto >=", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoLessThan(Long value) {
			addCriterion("id_ordine_acquisto <", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoLessThanOrEqualTo(Long value) {
			addCriterion("id_ordine_acquisto <=", value, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoIn(List<Long> values) {
			addCriterion("id_ordine_acquisto in", values, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoNotIn(List<Long> values) {
			addCriterion("id_ordine_acquisto not in", values, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoBetween(Long value1, Long value2) {
			addCriterion("id_ordine_acquisto between", value1, value2, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andIdOrdineAcquistoNotBetween(Long value1, Long value2) {
			addCriterion("id_ordine_acquisto not between", value1, value2, "idOrdineAcquisto");
			return (Criteria) this;
		}

		public Criteria andCupMasterIsNull() {
			addCriterion("cup_master is null");
			return (Criteria) this;
		}

		public Criteria andCupMasterIsNotNull() {
			addCriterion("cup_master is not null");
			return (Criteria) this;
		}

		public Criteria andCupMasterEqualTo(String value) {
			addCriterion("cup_master =", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterNotEqualTo(String value) {
			addCriterion("cup_master <>", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterGreaterThan(String value) {
			addCriterion("cup_master >", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterGreaterThanOrEqualTo(String value) {
			addCriterion("cup_master >=", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterLessThan(String value) {
			addCriterion("cup_master <", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterLessThanOrEqualTo(String value) {
			addCriterion("cup_master <=", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterLike(String value) {
			addCriterion("cup_master like", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterNotLike(String value) {
			addCriterion("cup_master not like", value, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterIn(List<String> values) {
			addCriterion("cup_master in", values, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterNotIn(List<String> values) {
			addCriterion("cup_master not in", values, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterBetween(String value1, String value2) {
			addCriterion("cup_master between", value1, value2, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupMasterNotBetween(String value1, String value2) {
			addCriterion("cup_master not between", value1, value2, "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupIsNull() {
			addCriterion("cup is null");
			return (Criteria) this;
		}

		public Criteria andCupIsNotNull() {
			addCriterion("cup is not null");
			return (Criteria) this;
		}

		public Criteria andCupEqualTo(String value) {
			addCriterion("cup =", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupNotEqualTo(String value) {
			addCriterion("cup <>", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupGreaterThan(String value) {
			addCriterion("cup >", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupGreaterThanOrEqualTo(String value) {
			addCriterion("cup >=", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupLessThan(String value) {
			addCriterion("cup <", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupLessThanOrEqualTo(String value) {
			addCriterion("cup <=", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupLike(String value) {
			addCriterion("cup like", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupNotLike(String value) {
			addCriterion("cup not like", value, "cup");
			return (Criteria) this;
		}

		public Criteria andCupIn(List<String> values) {
			addCriterion("cup in", values, "cup");
			return (Criteria) this;
		}

		public Criteria andCupNotIn(List<String> values) {
			addCriterion("cup not in", values, "cup");
			return (Criteria) this;
		}

		public Criteria andCupBetween(String value1, String value2) {
			addCriterion("cup between", value1, value2, "cup");
			return (Criteria) this;
		}

		public Criteria andCupNotBetween(String value1, String value2) {
			addCriterion("cup not between", value1, value2, "cup");
			return (Criteria) this;
		}

		public Criteria andCigIsNull() {
			addCriterion("cig is null");
			return (Criteria) this;
		}

		public Criteria andCigIsNotNull() {
			addCriterion("cig is not null");
			return (Criteria) this;
		}

		public Criteria andCigEqualTo(String value) {
			addCriterion("cig =", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigNotEqualTo(String value) {
			addCriterion("cig <>", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigGreaterThan(String value) {
			addCriterion("cig >", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigGreaterThanOrEqualTo(String value) {
			addCriterion("cig >=", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigLessThan(String value) {
			addCriterion("cig <", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigLessThanOrEqualTo(String value) {
			addCriterion("cig <=", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigLike(String value) {
			addCriterion("cig like", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigNotLike(String value) {
			addCriterion("cig not like", value, "cig");
			return (Criteria) this;
		}

		public Criteria andCigIn(List<String> values) {
			addCriterion("cig in", values, "cig");
			return (Criteria) this;
		}

		public Criteria andCigNotIn(List<String> values) {
			addCriterion("cig not in", values, "cig");
			return (Criteria) this;
		}

		public Criteria andCigBetween(String value1, String value2) {
			addCriterion("cig between", value1, value2, "cig");
			return (Criteria) this;
		}

		public Criteria andCigNotBetween(String value1, String value2) {
			addCriterion("cig not between", value1, value2, "cig");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneIsNull() {
			addCriterion("data_aggiudicazione is null");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneIsNotNull() {
			addCriterion("data_aggiudicazione is not null");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneEqualTo(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione =", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneNotEqualTo(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione <>", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneGreaterThan(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione >", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione >=", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneLessThan(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione <", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_aggiudicazione <=", value, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneIn(List<Date> values) {
			addCriterionForJDBCDate("data_aggiudicazione in", values, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneNotIn(List<Date> values) {
			addCriterionForJDBCDate("data_aggiudicazione not in", values, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_aggiudicazione between", value1, value2, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataAggiudicazioneNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_aggiudicazione not between", value1, value2, "dataAggiudicazione");
			return (Criteria) this;
		}

		public Criteria andDataStipulaIsNull() {
			addCriterion("data_stipula is null");
			return (Criteria) this;
		}

		public Criteria andDataStipulaIsNotNull() {
			addCriterion("data_stipula is not null");
			return (Criteria) this;
		}

		public Criteria andDataStipulaEqualTo(Date value) {
			addCriterionForJDBCDate("data_stipula =", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaNotEqualTo(Date value) {
			addCriterionForJDBCDate("data_stipula <>", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaGreaterThan(Date value) {
			addCriterionForJDBCDate("data_stipula >", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_stipula >=", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaLessThan(Date value) {
			addCriterionForJDBCDate("data_stipula <", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_stipula <=", value, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaIn(List<Date> values) {
			addCriterionForJDBCDate("data_stipula in", values, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaNotIn(List<Date> values) {
			addCriterionForJDBCDate("data_stipula not in", values, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_stipula between", value1, value2, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andDataStipulaNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_stipula not between", value1, value2, "dataStipula");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineIsNull() {
			addCriterion("numero_ordine is null");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineIsNotNull() {
			addCriterion("numero_ordine is not null");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineEqualTo(String value) {
			addCriterion("numero_ordine =", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineNotEqualTo(String value) {
			addCriterion("numero_ordine <>", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineGreaterThan(String value) {
			addCriterion("numero_ordine >", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineGreaterThanOrEqualTo(String value) {
			addCriterion("numero_ordine >=", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineLessThan(String value) {
			addCriterion("numero_ordine <", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineLessThanOrEqualTo(String value) {
			addCriterion("numero_ordine <=", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineLike(String value) {
			addCriterion("numero_ordine like", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineNotLike(String value) {
			addCriterion("numero_ordine not like", value, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineIn(List<String> values) {
			addCriterion("numero_ordine in", values, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineNotIn(List<String> values) {
			addCriterion("numero_ordine not in", values, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineBetween(String value1, String value2) {
			addCriterion("numero_ordine between", value1, value2, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineNotBetween(String value1, String value2) {
			addCriterion("numero_ordine not between", value1, value2, "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineIsNull() {
			addCriterion("data_ordine is null");
			return (Criteria) this;
		}

		public Criteria andDataOrdineIsNotNull() {
			addCriterion("data_ordine is not null");
			return (Criteria) this;
		}

		public Criteria andDataOrdineEqualTo(Date value) {
			addCriterionForJDBCDate("data_ordine =", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineNotEqualTo(Date value) {
			addCriterionForJDBCDate("data_ordine <>", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineGreaterThan(Date value) {
			addCriterionForJDBCDate("data_ordine >", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_ordine >=", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineLessThan(Date value) {
			addCriterionForJDBCDate("data_ordine <", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("data_ordine <=", value, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineIn(List<Date> values) {
			addCriterionForJDBCDate("data_ordine in", values, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineNotIn(List<Date> values) {
			addCriterionForJDBCDate("data_ordine not in", values, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_ordine between", value1, value2, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andDataOrdineNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("data_ordine not between", value1, value2, "dataOrdine");
			return (Criteria) this;
		}

		public Criteria andFornitoreIsNull() {
			addCriterion("fornitore is null");
			return (Criteria) this;
		}

		public Criteria andFornitoreIsNotNull() {
			addCriterion("fornitore is not null");
			return (Criteria) this;
		}

		public Criteria andFornitoreEqualTo(String value) {
			addCriterion("fornitore =", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreNotEqualTo(String value) {
			addCriterion("fornitore <>", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreGreaterThan(String value) {
			addCriterion("fornitore >", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreGreaterThanOrEqualTo(String value) {
			addCriterion("fornitore >=", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreLessThan(String value) {
			addCriterion("fornitore <", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreLessThanOrEqualTo(String value) {
			addCriterion("fornitore <=", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreLike(String value) {
			addCriterion("fornitore like", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreNotLike(String value) {
			addCriterion("fornitore not like", value, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreIn(List<String> values) {
			addCriterion("fornitore in", values, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreNotIn(List<String> values) {
			addCriterion("fornitore not in", values, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreBetween(String value1, String value2) {
			addCriterion("fornitore between", value1, value2, "fornitore");
			return (Criteria) this;
		}

		public Criteria andFornitoreNotBetween(String value1, String value2) {
			addCriterion("fornitore not between", value1, value2, "fornitore");
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

		public Criteria andCupMasterLikeInsensitive(String value) {
			addCriterion("upper(cup_master) like", value.toUpperCase(), "cupMaster");
			return (Criteria) this;
		}

		public Criteria andCupLikeInsensitive(String value) {
			addCriterion("upper(cup) like", value.toUpperCase(), "cup");
			return (Criteria) this;
		}

		public Criteria andCigLikeInsensitive(String value) {
			addCriterion("upper(cig) like", value.toUpperCase(), "cig");
			return (Criteria) this;
		}

		public Criteria andNumeroOrdineLikeInsensitive(String value) {
			addCriterion("upper(numero_ordine) like", value.toUpperCase(), "numeroOrdine");
			return (Criteria) this;
		}

		public Criteria andFornitoreLikeInsensitive(String value) {
			addCriterion("upper(fornitore) like", value.toUpperCase(), "fornitore");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table rebusc_t_ordine_acquisto
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
     * This class corresponds to the database table rebusc_t_ordine_acquisto
     *
     * @mbg.generated do_not_delete_during_merge Fri Nov 19 10:39:26 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}