/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VContrattiSoggettiSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public VContrattiSoggettiSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
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

		public Criteria andIdSgIsNull() {
			addCriterion("id_sg is null");
			return (Criteria) this;
		}

		public Criteria andIdSgIsNotNull() {
			addCriterion("id_sg is not null");
			return (Criteria) this;
		}

		public Criteria andIdSgEqualTo(Long value) {
			addCriterion("id_sg =", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgNotEqualTo(Long value) {
			addCriterion("id_sg <>", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgGreaterThan(Long value) {
			addCriterion("id_sg >", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgGreaterThanOrEqualTo(Long value) {
			addCriterion("id_sg >=", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgLessThan(Long value) {
			addCriterion("id_sg <", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgLessThanOrEqualTo(Long value) {
			addCriterion("id_sg <=", value, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgIn(List<Long> values) {
			addCriterion("id_sg in", values, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgNotIn(List<Long> values) {
			addCriterion("id_sg not in", values, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgBetween(Long value1, Long value2) {
			addCriterion("id_sg between", value1, value2, "idSg");
			return (Criteria) this;
		}

		public Criteria andIdSgNotBetween(Long value1, Long value2) {
			addCriterion("id_sg not between", value1, value2, "idSg");
			return (Criteria) this;
		}

		public Criteria andContrattoIsNull() {
			addCriterion("contratto is null");
			return (Criteria) this;
		}

		public Criteria andContrattoIsNotNull() {
			addCriterion("contratto is not null");
			return (Criteria) this;
		}

		public Criteria andContrattoEqualTo(String value) {
			addCriterion("contratto =", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoNotEqualTo(String value) {
			addCriterion("contratto <>", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoGreaterThan(String value) {
			addCriterion("contratto >", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoGreaterThanOrEqualTo(String value) {
			addCriterion("contratto >=", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoLessThan(String value) {
			addCriterion("contratto <", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoLessThanOrEqualTo(String value) {
			addCriterion("contratto <=", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoLike(String value) {
			addCriterion("contratto like", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoNotLike(String value) {
			addCriterion("contratto not like", value, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoIn(List<String> values) {
			addCriterion("contratto in", values, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoNotIn(List<String> values) {
			addCriterion("contratto not in", values, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoBetween(String value1, String value2) {
			addCriterion("contratto between", value1, value2, "contratto");
			return (Criteria) this;
		}

		public Criteria andContrattoNotBetween(String value1, String value2) {
			addCriterion("contratto not between", value1, value2, "contratto");
			return (Criteria) this;
		}

		public Criteria andEnteCommIsNull() {
			addCriterion("ente_comm is null");
			return (Criteria) this;
		}

		public Criteria andEnteCommIsNotNull() {
			addCriterion("ente_comm is not null");
			return (Criteria) this;
		}

		public Criteria andEnteCommEqualTo(String value) {
			addCriterion("ente_comm =", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommNotEqualTo(String value) {
			addCriterion("ente_comm <>", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommGreaterThan(String value) {
			addCriterion("ente_comm >", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommGreaterThanOrEqualTo(String value) {
			addCriterion("ente_comm >=", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommLessThan(String value) {
			addCriterion("ente_comm <", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommLessThanOrEqualTo(String value) {
			addCriterion("ente_comm <=", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommLike(String value) {
			addCriterion("ente_comm like", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommNotLike(String value) {
			addCriterion("ente_comm not like", value, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommIn(List<String> values) {
			addCriterion("ente_comm in", values, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommNotIn(List<String> values) {
			addCriterion("ente_comm not in", values, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommBetween(String value1, String value2) {
			addCriterion("ente_comm between", value1, value2, "enteComm");
			return (Criteria) this;
		}

		public Criteria andEnteCommNotBetween(String value1, String value2) {
			addCriterion("ente_comm not between", value1, value2, "enteComm");
			return (Criteria) this;
		}

		public Criteria andDIniCIsNull() {
			addCriterion("d_ini_c is null");
			return (Criteria) this;
		}

		public Criteria andDIniCIsNotNull() {
			addCriterion("d_ini_c is not null");
			return (Criteria) this;
		}

		public Criteria andDIniCEqualTo(Date value) {
			addCriterionForJDBCDate("d_ini_c =", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCNotEqualTo(Date value) {
			addCriterionForJDBCDate("d_ini_c <>", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCGreaterThan(Date value) {
			addCriterionForJDBCDate("d_ini_c >", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("d_ini_c >=", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCLessThan(Date value) {
			addCriterionForJDBCDate("d_ini_c <", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("d_ini_c <=", value, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCIn(List<Date> values) {
			addCriterionForJDBCDate("d_ini_c in", values, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCNotIn(List<Date> values) {
			addCriterionForJDBCDate("d_ini_c not in", values, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("d_ini_c between", value1, value2, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDIniCNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("d_ini_c not between", value1, value2, "dIniC");
			return (Criteria) this;
		}

		public Criteria andDFinCIsNull() {
			addCriterion("d_fin_c is null");
			return (Criteria) this;
		}

		public Criteria andDFinCIsNotNull() {
			addCriterion("d_fin_c is not null");
			return (Criteria) this;
		}

		public Criteria andDFinCEqualTo(Date value) {
			addCriterionForJDBCDate("d_fin_c =", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCNotEqualTo(Date value) {
			addCriterionForJDBCDate("d_fin_c <>", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCGreaterThan(Date value) {
			addCriterionForJDBCDate("d_fin_c >", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("d_fin_c >=", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCLessThan(Date value) {
			addCriterionForJDBCDate("d_fin_c <", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("d_fin_c <=", value, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCIn(List<Date> values) {
			addCriterionForJDBCDate("d_fin_c in", values, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCNotIn(List<Date> values) {
			addCriterionForJDBCDate("d_fin_c not in", values, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("d_fin_c between", value1, value2, "dFinC");
			return (Criteria) this;
		}

		public Criteria andDFinCNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("d_fin_c not between", value1, value2, "dFinC");
			return (Criteria) this;
		}

		public Criteria andRuoloIsNull() {
			addCriterion("ruolo is null");
			return (Criteria) this;
		}

		public Criteria andRuoloIsNotNull() {
			addCriterion("ruolo is not null");
			return (Criteria) this;
		}

		public Criteria andRuoloEqualTo(String value) {
			addCriterion("ruolo =", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloNotEqualTo(String value) {
			addCriterion("ruolo <>", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloGreaterThan(String value) {
			addCriterion("ruolo >", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloGreaterThanOrEqualTo(String value) {
			addCriterion("ruolo >=", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloLessThan(String value) {
			addCriterion("ruolo <", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloLessThanOrEqualTo(String value) {
			addCriterion("ruolo <=", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloLike(String value) {
			addCriterion("ruolo like", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloNotLike(String value) {
			addCriterion("ruolo not like", value, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloIn(List<String> values) {
			addCriterion("ruolo in", values, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloNotIn(List<String> values) {
			addCriterion("ruolo not in", values, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloBetween(String value1, String value2) {
			addCriterion("ruolo between", value1, value2, "ruolo");
			return (Criteria) this;
		}

		public Criteria andRuoloNotBetween(String value1, String value2) {
			addCriterion("ruolo not between", value1, value2, "ruolo");
			return (Criteria) this;
		}

		public Criteria andCapofilaIsNull() {
			addCriterion("capofila is null");
			return (Criteria) this;
		}

		public Criteria andCapofilaIsNotNull() {
			addCriterion("capofila is not null");
			return (Criteria) this;
		}

		public Criteria andCapofilaEqualTo(String value) {
			addCriterion("capofila =", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaNotEqualTo(String value) {
			addCriterion("capofila <>", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaGreaterThan(String value) {
			addCriterion("capofila >", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaGreaterThanOrEqualTo(String value) {
			addCriterion("capofila >=", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaLessThan(String value) {
			addCriterion("capofila <", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaLessThanOrEqualTo(String value) {
			addCriterion("capofila <=", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaLike(String value) {
			addCriterion("capofila like", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaNotLike(String value) {
			addCriterion("capofila not like", value, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaIn(List<String> values) {
			addCriterion("capofila in", values, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaNotIn(List<String> values) {
			addCriterion("capofila not in", values, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaBetween(String value1, String value2) {
			addCriterion("capofila between", value1, value2, "capofila");
			return (Criteria) this;
		}

		public Criteria andCapofilaNotBetween(String value1, String value2) {
			addCriterion("capofila not between", value1, value2, "capofila");
			return (Criteria) this;
		}

		public Criteria andSgAffIsNull() {
			addCriterion("sg_aff is null");
			return (Criteria) this;
		}

		public Criteria andSgAffIsNotNull() {
			addCriterion("sg_aff is not null");
			return (Criteria) this;
		}

		public Criteria andSgAffEqualTo(String value) {
			addCriterion("sg_aff =", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffNotEqualTo(String value) {
			addCriterion("sg_aff <>", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffGreaterThan(String value) {
			addCriterion("sg_aff >", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffGreaterThanOrEqualTo(String value) {
			addCriterion("sg_aff >=", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffLessThan(String value) {
			addCriterion("sg_aff <", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffLessThanOrEqualTo(String value) {
			addCriterion("sg_aff <=", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffLike(String value) {
			addCriterion("sg_aff like", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffNotLike(String value) {
			addCriterion("sg_aff not like", value, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffIn(List<String> values) {
			addCriterion("sg_aff in", values, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffNotIn(List<String> values) {
			addCriterion("sg_aff not in", values, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffBetween(String value1, String value2) {
			addCriterion("sg_aff between", value1, value2, "sgAff");
			return (Criteria) this;
		}

		public Criteria andSgAffNotBetween(String value1, String value2) {
			addCriterion("sg_aff not between", value1, value2, "sgAff");
			return (Criteria) this;
		}

		public Criteria andDIniAIsNull() {
			addCriterion("d_ini_a is null");
			return (Criteria) this;
		}

		public Criteria andDIniAIsNotNull() {
			addCriterion("d_ini_a is not null");
			return (Criteria) this;
		}

		public Criteria andDIniAEqualTo(String value) {
			addCriterion("d_ini_a =", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniANotEqualTo(String value) {
			addCriterion("d_ini_a <>", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniAGreaterThan(String value) {
			addCriterion("d_ini_a >", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniAGreaterThanOrEqualTo(String value) {
			addCriterion("d_ini_a >=", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniALessThan(String value) {
			addCriterion("d_ini_a <", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniALessThanOrEqualTo(String value) {
			addCriterion("d_ini_a <=", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniALike(String value) {
			addCriterion("d_ini_a like", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniANotLike(String value) {
			addCriterion("d_ini_a not like", value, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniAIn(List<String> values) {
			addCriterion("d_ini_a in", values, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniANotIn(List<String> values) {
			addCriterion("d_ini_a not in", values, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniABetween(String value1, String value2) {
			addCriterion("d_ini_a between", value1, value2, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDIniANotBetween(String value1, String value2) {
			addCriterion("d_ini_a not between", value1, value2, "dIniA");
			return (Criteria) this;
		}

		public Criteria andDFinAIsNull() {
			addCriterion("d_fin_a is null");
			return (Criteria) this;
		}

		public Criteria andDFinAIsNotNull() {
			addCriterion("d_fin_a is not null");
			return (Criteria) this;
		}

		public Criteria andDFinAEqualTo(String value) {
			addCriterion("d_fin_a =", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinANotEqualTo(String value) {
			addCriterion("d_fin_a <>", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinAGreaterThan(String value) {
			addCriterion("d_fin_a >", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinAGreaterThanOrEqualTo(String value) {
			addCriterion("d_fin_a >=", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinALessThan(String value) {
			addCriterion("d_fin_a <", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinALessThanOrEqualTo(String value) {
			addCriterion("d_fin_a <=", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinALike(String value) {
			addCriterion("d_fin_a like", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinANotLike(String value) {
			addCriterion("d_fin_a not like", value, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinAIn(List<String> values) {
			addCriterion("d_fin_a in", values, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinANotIn(List<String> values) {
			addCriterion("d_fin_a not in", values, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinABetween(String value1, String value2) {
			addCriterion("d_fin_a between", value1, value2, "dFinA");
			return (Criteria) this;
		}

		public Criteria andDFinANotBetween(String value1, String value2) {
			addCriterion("d_fin_a not between", value1, value2, "dFinA");
			return (Criteria) this;
		}

		public Criteria andIdCIsNull() {
			addCriterion("id_c is null");
			return (Criteria) this;
		}

		public Criteria andIdCIsNotNull() {
			addCriterion("id_c is not null");
			return (Criteria) this;
		}

		public Criteria andIdCEqualTo(Long value) {
			addCriterion("id_c =", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCNotEqualTo(Long value) {
			addCriterion("id_c <>", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCGreaterThan(Long value) {
			addCriterion("id_c >", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCGreaterThanOrEqualTo(Long value) {
			addCriterion("id_c >=", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCLessThan(Long value) {
			addCriterion("id_c <", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCLessThanOrEqualTo(Long value) {
			addCriterion("id_c <=", value, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCIn(List<Long> values) {
			addCriterion("id_c in", values, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCNotIn(List<Long> values) {
			addCriterion("id_c not in", values, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCBetween(Long value1, Long value2) {
			addCriterion("id_c between", value1, value2, "idC");
			return (Criteria) this;
		}

		public Criteria andIdCNotBetween(Long value1, Long value2) {
			addCriterion("id_c not between", value1, value2, "idC");
			return (Criteria) this;
		}

		public Criteria andScadutoIsNull() {
			addCriterion("scaduto is null");
			return (Criteria) this;
		}

		public Criteria andScadutoIsNotNull() {
			addCriterion("scaduto is not null");
			return (Criteria) this;
		}

		public Criteria andScadutoEqualTo(Boolean value) {
			addCriterion("scaduto =", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoNotEqualTo(Boolean value) {
			addCriterion("scaduto <>", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoGreaterThan(Boolean value) {
			addCriterion("scaduto >", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoGreaterThanOrEqualTo(Boolean value) {
			addCriterion("scaduto >=", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoLessThan(Boolean value) {
			addCriterion("scaduto <", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoLessThanOrEqualTo(Boolean value) {
			addCriterion("scaduto <=", value, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoIn(List<Boolean> values) {
			addCriterion("scaduto in", values, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoNotIn(List<Boolean> values) {
			addCriterion("scaduto not in", values, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoBetween(Boolean value1, Boolean value2) {
			addCriterion("scaduto between", value1, value2, "scaduto");
			return (Criteria) this;
		}

		public Criteria andScadutoNotBetween(Boolean value1, Boolean value2) {
			addCriterion("scaduto not between", value1, value2, "scaduto");
			return (Criteria) this;
		}

		public Criteria andContrattoLikeInsensitive(String value) {
			addCriterion("upper(contratto) like", value.toUpperCase(), "contratto");
			return (Criteria) this;
		}

		public Criteria andEnteCommLikeInsensitive(String value) {
			addCriterion("upper(ente_comm) like", value.toUpperCase(), "enteComm");
			return (Criteria) this;
		}

		public Criteria andRuoloLikeInsensitive(String value) {
			addCriterion("upper(ruolo) like", value.toUpperCase(), "ruolo");
			return (Criteria) this;
		}

		public Criteria andCapofilaLikeInsensitive(String value) {
			addCriterion("upper(capofila) like", value.toUpperCase(), "capofila");
			return (Criteria) this;
		}

		public Criteria andSgAffLikeInsensitive(String value) {
			addCriterion("upper(sg_aff) like", value.toUpperCase(), "sgAff");
			return (Criteria) this;
		}

		public Criteria andDIniALikeInsensitive(String value) {
			addCriterion("upper(d_ini_a) like", value.toUpperCase(), "dIniA");
			return (Criteria) this;
		}

		public Criteria andDFinALikeInsensitive(String value) {
			addCriterion("upper(d_fin_a) like", value.toUpperCase(), "dFinA");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
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
     * This class corresponds to the database table sirtpl_trasv.v_contratti_soggetti
     *
     * @mbg.generated do_not_delete_during_merge Thu Jan 28 15:01:42 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}