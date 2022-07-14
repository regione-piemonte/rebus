/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SirtplaTDepositoSelector {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public SirtplaTDepositoSelector() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
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

		public Criteria andDenomDepositoIsNull() {
			addCriterion("denom_deposito is null");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoIsNotNull() {
			addCriterion("denom_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoEqualTo(String value) {
			addCriterion("denom_deposito =", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoNotEqualTo(String value) {
			addCriterion("denom_deposito <>", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoGreaterThan(String value) {
			addCriterion("denom_deposito >", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("denom_deposito >=", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoLessThan(String value) {
			addCriterion("denom_deposito <", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoLessThanOrEqualTo(String value) {
			addCriterion("denom_deposito <=", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoLike(String value) {
			addCriterion("denom_deposito like", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoNotLike(String value) {
			addCriterion("denom_deposito not like", value, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoIn(List<String> values) {
			addCriterion("denom_deposito in", values, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoNotIn(List<String> values) {
			addCriterion("denom_deposito not in", values, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoBetween(String value1, String value2) {
			addCriterion("denom_deposito between", value1, value2, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoNotBetween(String value1, String value2) {
			addCriterion("denom_deposito not between", value1, value2, "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoIsNull() {
			addCriterion("toponimo_deposito is null");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoIsNotNull() {
			addCriterion("toponimo_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoEqualTo(String value) {
			addCriterion("toponimo_deposito =", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoNotEqualTo(String value) {
			addCriterion("toponimo_deposito <>", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoGreaterThan(String value) {
			addCriterion("toponimo_deposito >", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("toponimo_deposito >=", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoLessThan(String value) {
			addCriterion("toponimo_deposito <", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoLessThanOrEqualTo(String value) {
			addCriterion("toponimo_deposito <=", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoLike(String value) {
			addCriterion("toponimo_deposito like", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoNotLike(String value) {
			addCriterion("toponimo_deposito not like", value, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoIn(List<String> values) {
			addCriterion("toponimo_deposito in", values, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoNotIn(List<String> values) {
			addCriterion("toponimo_deposito not in", values, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoBetween(String value1, String value2) {
			addCriterion("toponimo_deposito between", value1, value2, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoNotBetween(String value1, String value2) {
			addCriterion("toponimo_deposito not between", value1, value2, "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoIsNull() {
			addCriterion("indirizzo_deposito is null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoIsNotNull() {
			addCriterion("indirizzo_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoEqualTo(String value) {
			addCriterion("indirizzo_deposito =", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoNotEqualTo(String value) {
			addCriterion("indirizzo_deposito <>", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoGreaterThan(String value) {
			addCriterion("indirizzo_deposito >", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("indirizzo_deposito >=", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoLessThan(String value) {
			addCriterion("indirizzo_deposito <", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoLessThanOrEqualTo(String value) {
			addCriterion("indirizzo_deposito <=", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoLike(String value) {
			addCriterion("indirizzo_deposito like", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoNotLike(String value) {
			addCriterion("indirizzo_deposito not like", value, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoIn(List<String> values) {
			addCriterion("indirizzo_deposito in", values, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoNotIn(List<String> values) {
			addCriterion("indirizzo_deposito not in", values, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoBetween(String value1, String value2) {
			addCriterion("indirizzo_deposito between", value1, value2, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoNotBetween(String value1, String value2) {
			addCriterion("indirizzo_deposito not between", value1, value2, "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoIsNull() {
			addCriterion("num_civico_deposito is null");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoIsNotNull() {
			addCriterion("num_civico_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoEqualTo(String value) {
			addCriterion("num_civico_deposito =", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoNotEqualTo(String value) {
			addCriterion("num_civico_deposito <>", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoGreaterThan(String value) {
			addCriterion("num_civico_deposito >", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("num_civico_deposito >=", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoLessThan(String value) {
			addCriterion("num_civico_deposito <", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoLessThanOrEqualTo(String value) {
			addCriterion("num_civico_deposito <=", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoLike(String value) {
			addCriterion("num_civico_deposito like", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoNotLike(String value) {
			addCriterion("num_civico_deposito not like", value, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoIn(List<String> values) {
			addCriterion("num_civico_deposito in", values, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoNotIn(List<String> values) {
			addCriterion("num_civico_deposito not in", values, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoBetween(String value1, String value2) {
			addCriterion("num_civico_deposito between", value1, value2, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoNotBetween(String value1, String value2) {
			addCriterion("num_civico_deposito not between", value1, value2, "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoIsNull() {
			addCriterion("cap_deposito is null");
			return (Criteria) this;
		}

		public Criteria andCapDepositoIsNotNull() {
			addCriterion("cap_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andCapDepositoEqualTo(String value) {
			addCriterion("cap_deposito =", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoNotEqualTo(String value) {
			addCriterion("cap_deposito <>", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoGreaterThan(String value) {
			addCriterion("cap_deposito >", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("cap_deposito >=", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoLessThan(String value) {
			addCriterion("cap_deposito <", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoLessThanOrEqualTo(String value) {
			addCriterion("cap_deposito <=", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoLike(String value) {
			addCriterion("cap_deposito like", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoNotLike(String value) {
			addCriterion("cap_deposito not like", value, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoIn(List<String> values) {
			addCriterion("cap_deposito in", values, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoNotIn(List<String> values) {
			addCriterion("cap_deposito not in", values, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoBetween(String value1, String value2) {
			addCriterion("cap_deposito between", value1, value2, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoNotBetween(String value1, String value2) {
			addCriterion("cap_deposito not between", value1, value2, "capDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoIsNull() {
			addCriterion("id_comune_deposito is null");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoIsNotNull() {
			addCriterion("id_comune_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoEqualTo(Long value) {
			addCriterion("id_comune_deposito =", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoNotEqualTo(Long value) {
			addCriterion("id_comune_deposito <>", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoGreaterThan(Long value) {
			addCriterion("id_comune_deposito >", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoGreaterThanOrEqualTo(Long value) {
			addCriterion("id_comune_deposito >=", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoLessThan(Long value) {
			addCriterion("id_comune_deposito <", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoLessThanOrEqualTo(Long value) {
			addCriterion("id_comune_deposito <=", value, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoIn(List<Long> values) {
			addCriterion("id_comune_deposito in", values, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoNotIn(List<Long> values) {
			addCriterion("id_comune_deposito not in", values, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoBetween(Long value1, Long value2) {
			addCriterion("id_comune_deposito between", value1, value2, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andIdComuneDepositoNotBetween(Long value1, Long value2) {
			addCriterion("id_comune_deposito not between", value1, value2, "idComuneDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoIsNull() {
			addCriterion("telefono_deposito is null");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoIsNotNull() {
			addCriterion("telefono_deposito is not null");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoEqualTo(String value) {
			addCriterion("telefono_deposito =", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoNotEqualTo(String value) {
			addCriterion("telefono_deposito <>", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoGreaterThan(String value) {
			addCriterion("telefono_deposito >", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoGreaterThanOrEqualTo(String value) {
			addCriterion("telefono_deposito >=", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoLessThan(String value) {
			addCriterion("telefono_deposito <", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoLessThanOrEqualTo(String value) {
			addCriterion("telefono_deposito <=", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoLike(String value) {
			addCriterion("telefono_deposito like", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoNotLike(String value) {
			addCriterion("telefono_deposito not like", value, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoIn(List<String> values) {
			addCriterion("telefono_deposito in", values, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoNotIn(List<String> values) {
			addCriterion("telefono_deposito not in", values, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoBetween(String value1, String value2) {
			addCriterion("telefono_deposito between", value1, value2, "telefonoDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoNotBetween(String value1, String value2) {
			addCriterion("telefono_deposito not between", value1, value2, "telefonoDeposito");
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

		public Criteria andFlagDepositoPrevalenteIsNull() {
			addCriterion("flag_deposito_prevalente is null");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteIsNotNull() {
			addCriterion("flag_deposito_prevalente is not null");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteEqualTo(Boolean value) {
			addCriterion("flag_deposito_prevalente =", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteNotEqualTo(Boolean value) {
			addCriterion("flag_deposito_prevalente <>", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteGreaterThan(Boolean value) {
			addCriterion("flag_deposito_prevalente >", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteGreaterThanOrEqualTo(Boolean value) {
			addCriterion("flag_deposito_prevalente >=", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteLessThan(Boolean value) {
			addCriterion("flag_deposito_prevalente <", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteLessThanOrEqualTo(Boolean value) {
			addCriterion("flag_deposito_prevalente <=", value, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteIn(List<Boolean> values) {
			addCriterion("flag_deposito_prevalente in", values, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteNotIn(List<Boolean> values) {
			addCriterion("flag_deposito_prevalente not in", values, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteBetween(Boolean value1, Boolean value2) {
			addCriterion("flag_deposito_prevalente between", value1, value2, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andFlagDepositoPrevalenteNotBetween(Boolean value1, Boolean value2) {
			addCriterion("flag_deposito_prevalente not between", value1, value2, "flagDepositoPrevalente");
			return (Criteria) this;
		}

		public Criteria andDenomDepositoLikeInsensitive(String value) {
			addCriterion("upper(denom_deposito) like", value.toUpperCase(), "denomDeposito");
			return (Criteria) this;
		}

		public Criteria andToponimoDepositoLikeInsensitive(String value) {
			addCriterion("upper(toponimo_deposito) like", value.toUpperCase(), "toponimoDeposito");
			return (Criteria) this;
		}

		public Criteria andIndirizzoDepositoLikeInsensitive(String value) {
			addCriterion("upper(indirizzo_deposito) like", value.toUpperCase(), "indirizzoDeposito");
			return (Criteria) this;
		}

		public Criteria andNumCivicoDepositoLikeInsensitive(String value) {
			addCriterion("upper(num_civico_deposito) like", value.toUpperCase(), "numCivicoDeposito");
			return (Criteria) this;
		}

		public Criteria andCapDepositoLikeInsensitive(String value) {
			addCriterion("upper(cap_deposito) like", value.toUpperCase(), "capDeposito");
			return (Criteria) this;
		}

		public Criteria andTelefonoDepositoLikeInsensitive(String value) {
			addCriterion("upper(telefono_deposito) like", value.toUpperCase(), "telefonoDeposito");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sirtpla_t_deposito
	 * @mbg.generated  Wed Jul 01 13:08:26 CEST 2020
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
     * This class corresponds to the database table sirtpla_t_deposito
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 21 14:07:51 CEST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}