/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RebuspDTipoStampaSelector {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    public RebuspDTipoStampaSelector() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:31 CET 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
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

        public Criteria andIdTipoStampaIsNull() {
            addCriterion("id_tipo_stampa is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaIsNotNull() {
            addCriterion("id_tipo_stampa is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaEqualTo(Long value) {
            addCriterion("id_tipo_stampa =", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaNotEqualTo(Long value) {
            addCriterion("id_tipo_stampa <>", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaGreaterThan(Long value) {
            addCriterion("id_tipo_stampa >", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaGreaterThanOrEqualTo(Long value) {
            addCriterion("id_tipo_stampa >=", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaLessThan(Long value) {
            addCriterion("id_tipo_stampa <", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaLessThanOrEqualTo(Long value) {
            addCriterion("id_tipo_stampa <=", value, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaIn(List<Long> values) {
            addCriterion("id_tipo_stampa in", values, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaNotIn(List<Long> values) {
            addCriterion("id_tipo_stampa not in", values, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaBetween(Long value1, Long value2) {
            addCriterion("id_tipo_stampa between", value1, value2, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andIdTipoStampaNotBetween(Long value1, Long value2) {
            addCriterion("id_tipo_stampa not between", value1, value2, "idTipoStampa");
            return (Criteria) this;
        }

        public Criteria andDescrizioneIsNull() {
            addCriterion("descrizione is null");
            return (Criteria) this;
        }

        public Criteria andDescrizioneIsNotNull() {
            addCriterion("descrizione is not null");
            return (Criteria) this;
        }

        public Criteria andDescrizioneEqualTo(String value) {
            addCriterion("descrizione =", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneNotEqualTo(String value) {
            addCriterion("descrizione <>", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneGreaterThan(String value) {
            addCriterion("descrizione >", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneGreaterThanOrEqualTo(String value) {
            addCriterion("descrizione >=", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneLessThan(String value) {
            addCriterion("descrizione <", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneLessThanOrEqualTo(String value) {
            addCriterion("descrizione <=", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneLike(String value) {
            addCriterion("descrizione like", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneNotLike(String value) {
            addCriterion("descrizione not like", value, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneIn(List<String> values) {
            addCriterion("descrizione in", values, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneNotIn(List<String> values) {
            addCriterion("descrizione not in", values, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneBetween(String value1, String value2) {
            addCriterion("descrizione between", value1, value2, "descrizione");
            return (Criteria) this;
        }

        public Criteria andDescrizioneNotBetween(String value1, String value2) {
            addCriterion("descrizione not between", value1, value2, "descrizione");
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

        public Criteria andDescrizioneLikeInsensitive(String value) {
            addCriterion("upper(descrizione) like", value.toUpperCase(), "descrizione");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated do_not_delete_during_merge Tue Mar 03 17:30:32 CET 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusp_d_tipo_stampa
     *
     * @mbg.generated Tue Mar 03 17:30:32 CET 2020
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
}