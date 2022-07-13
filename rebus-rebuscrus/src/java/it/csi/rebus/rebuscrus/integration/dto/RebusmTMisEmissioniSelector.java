/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.util.ArrayList;
import java.util.List;

public class RebusmTMisEmissioniSelector {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public RebusmTMisEmissioniSelector() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
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
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
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

        public Criteria andIdMisurazioneIsNull() {
            addCriterion("id_misurazione is null");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneIsNotNull() {
            addCriterion("id_misurazione is not null");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneEqualTo(Long value) {
            addCriterion("id_misurazione =", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneNotEqualTo(Long value) {
            addCriterion("id_misurazione <>", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneGreaterThan(Long value) {
            addCriterion("id_misurazione >", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneGreaterThanOrEqualTo(Long value) {
            addCriterion("id_misurazione >=", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneLessThan(Long value) {
            addCriterion("id_misurazione <", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneLessThanOrEqualTo(Long value) {
            addCriterion("id_misurazione <=", value, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneIn(List<Long> values) {
            addCriterion("id_misurazione in", values, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneNotIn(List<Long> values) {
            addCriterion("id_misurazione not in", values, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneBetween(Long value1, Long value2) {
            addCriterion("id_misurazione between", value1, value2, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdMisurazioneNotBetween(Long value1, Long value2) {
            addCriterion("id_misurazione not between", value1, value2, "idMisurazione");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoIsNull() {
            addCriterion("id_tipo_allestimento is null");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoIsNotNull() {
            addCriterion("id_tipo_allestimento is not null");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoEqualTo(Long value) {
            addCriterion("id_tipo_allestimento =", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoNotEqualTo(Long value) {
            addCriterion("id_tipo_allestimento <>", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoGreaterThan(Long value) {
            addCriterion("id_tipo_allestimento >", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoGreaterThanOrEqualTo(Long value) {
            addCriterion("id_tipo_allestimento >=", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoLessThan(Long value) {
            addCriterion("id_tipo_allestimento <", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoLessThanOrEqualTo(Long value) {
            addCriterion("id_tipo_allestimento <=", value, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoIn(List<Long> values) {
            addCriterion("id_tipo_allestimento in", values, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoNotIn(List<Long> values) {
            addCriterion("id_tipo_allestimento not in", values, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoBetween(Long value1, Long value2) {
            addCriterion("id_tipo_allestimento between", value1, value2, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andIdTipoAllestimentoNotBetween(Long value1, Long value2) {
            addCriterion("id_tipo_allestimento not between", value1, value2, "idTipoAllestimento");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiIsNull() {
            addCriterion("km_percorsi is null");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiIsNotNull() {
            addCriterion("km_percorsi is not null");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiEqualTo(Double value) {
            addCriterion("km_percorsi =", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiNotEqualTo(Double value) {
            addCriterion("km_percorsi <>", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiGreaterThan(Double value) {
            addCriterion("km_percorsi >", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiGreaterThanOrEqualTo(Double value) {
            addCriterion("km_percorsi >=", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiLessThan(Double value) {
            addCriterion("km_percorsi <", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiLessThanOrEqualTo(Double value) {
            addCriterion("km_percorsi <=", value, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiIn(List<Double> values) {
            addCriterion("km_percorsi in", values, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiNotIn(List<Double> values) {
            addCriterion("km_percorsi not in", values, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiBetween(Double value1, Double value2) {
            addCriterion("km_percorsi between", value1, value2, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andKmPercorsiNotBetween(Double value1, Double value2) {
            addCriterion("km_percorsi not between", value1, value2, "kmPercorsi");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2IsNull() {
            addCriterion("emissioni_co2 is null");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2IsNotNull() {
            addCriterion("emissioni_co2 is not null");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2EqualTo(Double value) {
            addCriterion("emissioni_co2 =", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2NotEqualTo(Double value) {
            addCriterion("emissioni_co2 <>", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2GreaterThan(Double value) {
            addCriterion("emissioni_co2 >", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2GreaterThanOrEqualTo(Double value) {
            addCriterion("emissioni_co2 >=", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2LessThan(Double value) {
            addCriterion("emissioni_co2 <", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2LessThanOrEqualTo(Double value) {
            addCriterion("emissioni_co2 <=", value, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2In(List<Double> values) {
            addCriterion("emissioni_co2 in", values, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2NotIn(List<Double> values) {
            addCriterion("emissioni_co2 not in", values, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2Between(Double value1, Double value2) {
            addCriterion("emissioni_co2 between", value1, value2, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniCo2NotBetween(Double value1, Double value2) {
            addCriterion("emissioni_co2 not between", value1, value2, "emissioniCo2");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxIsNull() {
            addCriterion("emissioni_nox is null");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxIsNotNull() {
            addCriterion("emissioni_nox is not null");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxEqualTo(Double value) {
            addCriterion("emissioni_nox =", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxNotEqualTo(Double value) {
            addCriterion("emissioni_nox <>", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxGreaterThan(Double value) {
            addCriterion("emissioni_nox >", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxGreaterThanOrEqualTo(Double value) {
            addCriterion("emissioni_nox >=", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxLessThan(Double value) {
            addCriterion("emissioni_nox <", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxLessThanOrEqualTo(Double value) {
            addCriterion("emissioni_nox <=", value, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxIn(List<Double> values) {
            addCriterion("emissioni_nox in", values, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxNotIn(List<Double> values) {
            addCriterion("emissioni_nox not in", values, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxBetween(Double value1, Double value2) {
            addCriterion("emissioni_nox between", value1, value2, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniNoxNotBetween(Double value1, Double value2) {
            addCriterion("emissioni_nox not between", value1, value2, "emissioniNox");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoIsNull() {
            addCriterion("emissioni_pm10_peso is null");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoIsNotNull() {
            addCriterion("emissioni_pm10_peso is not null");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoEqualTo(Double value) {
            addCriterion("emissioni_pm10_peso =", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoNotEqualTo(Double value) {
            addCriterion("emissioni_pm10_peso <>", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoGreaterThan(Double value) {
            addCriterion("emissioni_pm10_peso >", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoGreaterThanOrEqualTo(Double value) {
            addCriterion("emissioni_pm10_peso >=", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoLessThan(Double value) {
            addCriterion("emissioni_pm10_peso <", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoLessThanOrEqualTo(Double value) {
            addCriterion("emissioni_pm10_peso <=", value, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoIn(List<Double> values) {
            addCriterion("emissioni_pm10_peso in", values, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoNotIn(List<Double> values) {
            addCriterion("emissioni_pm10_peso not in", values, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoBetween(Double value1, Double value2) {
            addCriterion("emissioni_pm10_peso between", value1, value2, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10PesoNotBetween(Double value1, Double value2) {
            addCriterion("emissioni_pm10_peso not between", value1, value2, "emissioniPm10Peso");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroIsNull() {
            addCriterion("emissioni_pm10_numero is null");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroIsNotNull() {
            addCriterion("emissioni_pm10_numero is not null");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroEqualTo(Double value) {
            addCriterion("emissioni_pm10_numero =", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroNotEqualTo(Double value) {
            addCriterion("emissioni_pm10_numero <>", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroGreaterThan(Double value) {
            addCriterion("emissioni_pm10_numero >", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroGreaterThanOrEqualTo(Double value) {
            addCriterion("emissioni_pm10_numero >=", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroLessThan(Double value) {
            addCriterion("emissioni_pm10_numero <", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroLessThanOrEqualTo(Double value) {
            addCriterion("emissioni_pm10_numero <=", value, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroIn(List<Double> values) {
            addCriterion("emissioni_pm10_numero in", values, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroNotIn(List<Double> values) {
            addCriterion("emissioni_pm10_numero not in", values, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroBetween(Double value1, Double value2) {
            addCriterion("emissioni_pm10_numero between", value1, value2, "emissioniPm10Numero");
            return (Criteria) this;
        }

        public Criteria andEmissioniPm10NumeroNotBetween(Double value1, Double value2) {
            addCriterion("emissioni_pm10_numero not between", value1, value2, "emissioniPm10Numero");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated do_not_delete_during_merge Fri Dec 10 10:30:29 CET 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table rebusm_t_mis_emissioni
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
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