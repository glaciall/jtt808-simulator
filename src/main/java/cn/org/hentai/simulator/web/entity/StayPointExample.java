package cn.org.hentai.simulator.web.entity;

import java.util.ArrayList;
import java.util.List;

public class StayPointExample {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected boolean distinct;

    /**
     */
    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    /**
     */
    public StayPointExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    /**
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setPageInfo(Integer currentPage, Integer pageSize) {
        if(pageSize<1) throw new IllegalArgumentException("页大小不能小于1！");
        this.limit=pageSize;
        if(currentPage<1) throw new IllegalArgumentException("页数不能小于1！");
        this.offset=(currentPage-1)*pageSize;
    }

    /**
     */
    public StayPointExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     */
    public StayPointExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    /**
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRouteidIsNull() {
            addCriterion("routeid is null");
            return (Criteria) this;
        }

        public Criteria andRouteidIsNotNull() {
            addCriterion("routeid is not null");
            return (Criteria) this;
        }

        public Criteria andRouteidEqualTo(Long value) {
            addCriterion("routeid =", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidNotEqualTo(Long value) {
            addCriterion("routeid <>", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidGreaterThan(Long value) {
            addCriterion("routeid >", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidGreaterThanOrEqualTo(Long value) {
            addCriterion("routeid >=", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidLessThan(Long value) {
            addCriterion("routeid <", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidLessThanOrEqualTo(Long value) {
            addCriterion("routeid <=", value, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidIn(List<Long> values) {
            addCriterion("routeid in", values, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidNotIn(List<Long> values) {
            addCriterion("routeid not in", values, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidBetween(Long value1, Long value2) {
            addCriterion("routeid between", value1, value2, "routeid");
            return (Criteria) this;
        }

        public Criteria andRouteidNotBetween(Long value1, Long value2) {
            addCriterion("routeid not between", value1, value2, "routeid");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andMinTimeIsNull() {
            addCriterion("minTime is null");
            return (Criteria) this;
        }

        public Criteria andMinTimeIsNotNull() {
            addCriterion("minTime is not null");
            return (Criteria) this;
        }

        public Criteria andMinTimeEqualTo(Integer value) {
            addCriterion("minTime =", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeNotEqualTo(Integer value) {
            addCriterion("minTime <>", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeGreaterThan(Integer value) {
            addCriterion("minTime >", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("minTime >=", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeLessThan(Integer value) {
            addCriterion("minTime <", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeLessThanOrEqualTo(Integer value) {
            addCriterion("minTime <=", value, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeIn(List<Integer> values) {
            addCriterion("minTime in", values, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeNotIn(List<Integer> values) {
            addCriterion("minTime not in", values, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeBetween(Integer value1, Integer value2) {
            addCriterion("minTime between", value1, value2, "minTime");
            return (Criteria) this;
        }

        public Criteria andMinTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("minTime not between", value1, value2, "minTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeIsNull() {
            addCriterion("maxTime is null");
            return (Criteria) this;
        }

        public Criteria andMaxTimeIsNotNull() {
            addCriterion("maxTime is not null");
            return (Criteria) this;
        }

        public Criteria andMaxTimeEqualTo(Integer value) {
            addCriterion("maxTime =", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeNotEqualTo(Integer value) {
            addCriterion("maxTime <>", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeGreaterThan(Integer value) {
            addCriterion("maxTime >", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxTime >=", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeLessThan(Integer value) {
            addCriterion("maxTime <", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeLessThanOrEqualTo(Integer value) {
            addCriterion("maxTime <=", value, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeIn(List<Integer> values) {
            addCriterion("maxTime in", values, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeNotIn(List<Integer> values) {
            addCriterion("maxTime not in", values, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeBetween(Integer value1, Integer value2) {
            addCriterion("maxTime between", value1, value2, "maxTime");
            return (Criteria) this;
        }

        public Criteria andMaxTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("maxTime not between", value1, value2, "maxTime");
            return (Criteria) this;
        }

        public Criteria andRatioIsNull() {
            addCriterion("ratio is null");
            return (Criteria) this;
        }

        public Criteria andRatioIsNotNull() {
            addCriterion("ratio is not null");
            return (Criteria) this;
        }

        public Criteria andRatioEqualTo(Integer value) {
            addCriterion("ratio =", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotEqualTo(Integer value) {
            addCriterion("ratio <>", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThan(Integer value) {
            addCriterion("ratio >", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThanOrEqualTo(Integer value) {
            addCriterion("ratio >=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThan(Integer value) {
            addCriterion("ratio <", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThanOrEqualTo(Integer value) {
            addCriterion("ratio <=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioIn(List<Integer> values) {
            addCriterion("ratio in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotIn(List<Integer> values) {
            addCriterion("ratio not in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioBetween(Integer value1, Integer value2) {
            addCriterion("ratio between", value1, value2, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotBetween(Integer value1, Integer value2) {
            addCriterion("ratio not between", value1, value2, "ratio");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         */
        private StayPointExample example;

        /**
         */
        protected Criteria(StayPointExample example) {
            super();
            this.example = example;
        }

        /**
         */
        public StayPointExample example() {
            return this.example;
        }

        /**
         */
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public interface ICriteriaAdd {
            /**
             */
            Criteria add(Criteria add);
        }
    }

    /**
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