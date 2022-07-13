/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.List;

public class RebusDTipoMessaggioSistemaSelector {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public RebusDTipoMessaggioSistemaSelector() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
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
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
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

        public Criteria andIdTipoMessaggioSistemaIsNull() {
            addCriterion("id_tipo_messaggio_sistema is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaIsNotNull() {
            addCriterion("id_tipo_messaggio_sistema is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaEqualTo(Long value) {
            addCriterion("id_tipo_messaggio_sistema =", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaNotEqualTo(Long value) {
            addCriterion("id_tipo_messaggio_sistema <>", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaGreaterThan(Long value) {
            addCriterion("id_tipo_messaggio_sistema >", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaGreaterThanOrEqualTo(Long value) {
            addCriterion("id_tipo_messaggio_sistema >=", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaLessThan(Long value) {
            addCriterion("id_tipo_messaggio_sistema <", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaLessThanOrEqualTo(Long value) {
            addCriterion("id_tipo_messaggio_sistema <=", value, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaIn(List<Long> values) {
            addCriterion("id_tipo_messaggio_sistema in", values, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaNotIn(List<Long> values) {
            addCriterion("id_tipo_messaggio_sistema not in", values, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaBetween(Long value1, Long value2) {
            addCriterion("id_tipo_messaggio_sistema between", value1, value2, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andIdTipoMessaggioSistemaNotBetween(Long value1, Long value2) {
            addCriterion("id_tipo_messaggio_sistema not between", value1, value2, "idTipoMessaggioSistema");
            return (Criteria) this;
        }

        public Criteria andTestoIsNull() {
            addCriterion("testo is null");
            return (Criteria) this;
        }

        public Criteria andTestoIsNotNull() {
            addCriterion("testo is not null");
            return (Criteria) this;
        }

        public Criteria andTestoEqualTo(String value) {
            addCriterion("testo =", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoNotEqualTo(String value) {
            addCriterion("testo <>", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoGreaterThan(String value) {
            addCriterion("testo >", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoGreaterThanOrEqualTo(String value) {
            addCriterion("testo >=", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoLessThan(String value) {
            addCriterion("testo <", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoLessThanOrEqualTo(String value) {
            addCriterion("testo <=", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoLike(String value) {
            addCriterion("testo like", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoNotLike(String value) {
            addCriterion("testo not like", value, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoIn(List<String> values) {
            addCriterion("testo in", values, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoNotIn(List<String> values) {
            addCriterion("testo not in", values, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoBetween(String value1, String value2) {
            addCriterion("testo between", value1, value2, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoNotBetween(String value1, String value2) {
            addCriterion("testo not between", value1, value2, "testo");
            return (Criteria) this;
        }

        public Criteria andTestoLikeInsensitive(String value) {
            addCriterion("upper(testo) like", value.toUpperCase(), "testo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated do_not_delete_during_merge Thu Nov 05 12:18:32 CET 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebus_d_tipo_messaggio_sistema
     *
     * @mbg.generated Thu Nov 05 12:18:32 CET 2020
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