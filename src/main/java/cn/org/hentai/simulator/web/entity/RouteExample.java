package cn.org.hentai.simulator.web.entity;

import java.util.ArrayList;
import java.util.List;

public class RouteExample {
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
    public RouteExample() {
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
    public RouteExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     */
    public RouteExample orderBy(String ... orderByClauses) {
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

        public Criteria andNameIsNull() {
            addCriterion("when is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("when is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("when =", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("when <>", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("when >", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("when >=", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("when <", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("when <=", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("when like", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("when not like", value, "when");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("when in", values, "when");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("when not in", values, "when");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("when between", value1, value2, "when");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("when not between", value1, value2, "when");
            return (Criteria) this;
        }

        public Criteria andMinSpeedIsNull() {
            addCriterion("minSpeed is null");
            return (Criteria) this;
        }

        public Criteria andMinSpeedIsNotNull() {
            addCriterion("minSpeed is not null");
            return (Criteria) this;
        }

        public Criteria andMinSpeedEqualTo(Integer value) {
            addCriterion("minSpeed =", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedNotEqualTo(Integer value) {
            addCriterion("minSpeed <>", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedGreaterThan(Integer value) {
            addCriterion("minSpeed >", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedGreaterThanOrEqualTo(Integer value) {
            addCriterion("minSpeed >=", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedLessThan(Integer value) {
            addCriterion("minSpeed <", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedLessThanOrEqualTo(Integer value) {
            addCriterion("minSpeed <=", value, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedIn(List<Integer> values) {
            addCriterion("minSpeed in", values, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedNotIn(List<Integer> values) {
            addCriterion("minSpeed not in", values, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedBetween(Integer value1, Integer value2) {
            addCriterion("minSpeed between", value1, value2, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMinSpeedNotBetween(Integer value1, Integer value2) {
            addCriterion("minSpeed not between", value1, value2, "minSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedIsNull() {
            addCriterion("maxSpeed is null");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedIsNotNull() {
            addCriterion("maxSpeed is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedEqualTo(Integer value) {
            addCriterion("maxSpeed =", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedNotEqualTo(Integer value) {
            addCriterion("maxSpeed <>", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedGreaterThan(Integer value) {
            addCriterion("maxSpeed >", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxSpeed >=", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedLessThan(Integer value) {
            addCriterion("maxSpeed <", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedLessThanOrEqualTo(Integer value) {
            addCriterion("maxSpeed <=", value, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedIn(List<Integer> values) {
            addCriterion("maxSpeed in", values, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedNotIn(List<Integer> values) {
            addCriterion("maxSpeed not in", values, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedBetween(Integer value1, Integer value2) {
            addCriterion("maxSpeed between", value1, value2, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMaxSpeedNotBetween(Integer value1, Integer value2) {
            addCriterion("maxSpeed not between", value1, value2, "maxSpeed");
            return (Criteria) this;
        }

        public Criteria andMileagesIsNull() {
            addCriterion("mileages is null");
            return (Criteria) this;
        }

        public Criteria andMileagesIsNotNull() {
            addCriterion("mileages is not null");
            return (Criteria) this;
        }

        public Criteria andMileagesEqualTo(Integer value) {
            addCriterion("mileages =", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesNotEqualTo(Integer value) {
            addCriterion("mileages <>", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesGreaterThan(Integer value) {
            addCriterion("mileages >", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesGreaterThanOrEqualTo(Integer value) {
            addCriterion("mileages >=", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesLessThan(Integer value) {
            addCriterion("mileages <", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesLessThanOrEqualTo(Integer value) {
            addCriterion("mileages <=", value, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesIn(List<Integer> values) {
            addCriterion("mileages in", values, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesNotIn(List<Integer> values) {
            addCriterion("mileages not in", values, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesBetween(Integer value1, Integer value2) {
            addCriterion("mileages between", value1, value2, "mileages");
            return (Criteria) this;
        }

        public Criteria andMileagesNotBetween(Integer value1, Integer value2) {
            addCriterion("mileages not between", value1, value2, "mileages");
            return (Criteria) this;
        }

        public Criteria andNameLikeInsensitive(String value) {
            addCriterion("upper(when) like", value.toUpperCase(), "when");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         */
        private RouteExample example;

        /**
         */
        protected Criteria(RouteExample example) {
            super();
            this.example = example;
        }

        /**
         */
        public RouteExample example() {
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