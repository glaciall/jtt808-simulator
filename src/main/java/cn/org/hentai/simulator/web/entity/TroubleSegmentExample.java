package cn.org.hentai.simulator.web.entity;

import java.util.ArrayList;
import java.util.List;

public class TroubleSegmentExample {
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
    public TroubleSegmentExample() {
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
    public TroubleSegmentExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     */
    public TroubleSegmentExample orderBy(String ... orderByClauses) {
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

        public Criteria andRouteIdIsNull() {
            addCriterion("routeId is null");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNotNull() {
            addCriterion("routeId is not null");
            return (Criteria) this;
        }

        public Criteria andRouteIdEqualTo(Long value) {
            addCriterion("routeId =", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotEqualTo(Long value) {
            addCriterion("routeId <>", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThan(Long value) {
            addCriterion("routeId >", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThanOrEqualTo(Long value) {
            addCriterion("routeId >=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThan(Long value) {
            addCriterion("routeId <", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThanOrEqualTo(Long value) {
            addCriterion("routeId <=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdIn(List<Long> values) {
            addCriterion("routeId in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotIn(List<Long> values) {
            addCriterion("routeId not in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdBetween(Long value1, Long value2) {
            addCriterion("routeId between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotBetween(Long value1, Long value2) {
            addCriterion("routeId not between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andStartIndexIsNull() {
            addCriterion("startIndex is null");
            return (Criteria) this;
        }

        public Criteria andStartIndexIsNotNull() {
            addCriterion("startIndex is not null");
            return (Criteria) this;
        }

        public Criteria andStartIndexEqualTo(Integer value) {
            addCriterion("startIndex =", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexNotEqualTo(Integer value) {
            addCriterion("startIndex <>", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexGreaterThan(Integer value) {
            addCriterion("startIndex >", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("startIndex >=", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexLessThan(Integer value) {
            addCriterion("startIndex <", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexLessThanOrEqualTo(Integer value) {
            addCriterion("startIndex <=", value, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexIn(List<Integer> values) {
            addCriterion("startIndex in", values, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexNotIn(List<Integer> values) {
            addCriterion("startIndex not in", values, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexBetween(Integer value1, Integer value2) {
            addCriterion("startIndex between", value1, value2, "startIndex");
            return (Criteria) this;
        }

        public Criteria andStartIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("startIndex not between", value1, value2, "startIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexIsNull() {
            addCriterion("endIndex is null");
            return (Criteria) this;
        }

        public Criteria andEndIndexIsNotNull() {
            addCriterion("endIndex is not null");
            return (Criteria) this;
        }

        public Criteria andEndIndexEqualTo(Integer value) {
            addCriterion("endIndex =", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexNotEqualTo(Integer value) {
            addCriterion("endIndex <>", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexGreaterThan(Integer value) {
            addCriterion("endIndex >", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("endIndex >=", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexLessThan(Integer value) {
            addCriterion("endIndex <", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexLessThanOrEqualTo(Integer value) {
            addCriterion("endIndex <=", value, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexIn(List<Integer> values) {
            addCriterion("endIndex in", values, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexNotIn(List<Integer> values) {
            addCriterion("endIndex not in", values, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexBetween(Integer value1, Integer value2) {
            addCriterion("endIndex between", value1, value2, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEndIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("endIndex not between", value1, value2, "endIndex");
            return (Criteria) this;
        }

        public Criteria andEventCodeIsNull() {
            addCriterion("eventCode is null");
            return (Criteria) this;
        }

        public Criteria andEventCodeIsNotNull() {
            addCriterion("eventCode is not null");
            return (Criteria) this;
        }

        public Criteria andEventCodeEqualTo(String value) {
            addCriterion("eventCode =", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeNotEqualTo(String value) {
            addCriterion("eventCode <>", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeGreaterThan(String value) {
            addCriterion("eventCode >", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeGreaterThanOrEqualTo(String value) {
            addCriterion("eventCode >=", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeLessThan(String value) {
            addCriterion("eventCode <", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeLessThanOrEqualTo(String value) {
            addCriterion("eventCode <=", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeLike(String value) {
            addCriterion("eventCode like", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeNotLike(String value) {
            addCriterion("eventCode not like", value, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeIn(List<String> values) {
            addCriterion("eventCode in", values, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeNotIn(List<String> values) {
            addCriterion("eventCode not in", values, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeBetween(String value1, String value2) {
            addCriterion("eventCode between", value1, value2, "eventCode");
            return (Criteria) this;
        }

        public Criteria andEventCodeNotBetween(String value1, String value2) {
            addCriterion("eventCode not between", value1, value2, "eventCode");
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

        public Criteria andEventCodeLikeInsensitive(String value) {
            addCriterion("upper(eventCode) like", value.toUpperCase(), "eventCode");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         */
        private TroubleSegmentExample example;

        /**
         */
        protected Criteria(TroubleSegmentExample example) {
            super();
            this.example = example;
        }

        /**
         */
        public TroubleSegmentExample example() {
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