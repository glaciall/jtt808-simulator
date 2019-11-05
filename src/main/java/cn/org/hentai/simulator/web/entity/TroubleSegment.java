package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;

public class TroubleSegment implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long routeId;

    /**
     *
     */
    private Integer startIndex;

    /**
     *
     */
    private Integer endIndex;

    /**
     *
     */
    private String eventCode;

    /**
     *
     */
    private Integer ratio;

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_trouble_segment.id
     *
     * @return the value of x_trouble_segment.id
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public TroubleSegment withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.id
     *
     * @param id the value for x_trouble_segment.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_trouble_segment.routeId
     *
     * @return the value of x_trouble_segment.routeId
     */
    public Long getRouteId() {
        return routeId;
    }

    /**
     */
    public TroubleSegment withRouteId(Long routeId) {
        this.setRouteId(routeId);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.routeId
     *
     * @param routeId the value for x_trouble_segment.routeId
     */
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    /**
     * This method returns the value of the database column x_trouble_segment.startIndex
     *
     * @return the value of x_trouble_segment.startIndex
     */
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     */
    public TroubleSegment withStartIndex(Integer startIndex) {
        this.setStartIndex(startIndex);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.startIndex
     *
     * @param startIndex the value for x_trouble_segment.startIndex
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * This method returns the value of the database column x_trouble_segment.endIndex
     *
     * @return the value of x_trouble_segment.endIndex
     */
    public Integer getEndIndex() {
        return endIndex;
    }

    /**
     */
    public TroubleSegment withEndIndex(Integer endIndex) {
        this.setEndIndex(endIndex);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.endIndex
     *
     * @param endIndex the value for x_trouble_segment.endIndex
     */
    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * This method returns the value of the database column x_trouble_segment.eventCode
     *
     * @return the value of x_trouble_segment.eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     */
    public TroubleSegment withEventCode(String eventCode) {
        this.setEventCode(eventCode);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.eventCode
     *
     * @param eventCode the value for x_trouble_segment.eventCode
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * This method returns the value of the database column x_trouble_segment.ratio
     *
     * @return the value of x_trouble_segment.ratio
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     */
    public TroubleSegment withRatio(Integer ratio) {
        this.setRatio(ratio);
        return this;
    }

    /**
     * This method sets the value of the database column x_trouble_segment.ratio
     *
     * @param ratio the value for x_trouble_segment.ratio
     */
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    /**
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", routeId=").append(routeId);
        sb.append(", startIndex=").append(startIndex);
        sb.append(", endIndex=").append(endIndex);
        sb.append(", eventCode=").append(eventCode);
        sb.append(", ratio=").append(ratio);
        sb.append("]");
        return sb.toString();
    }

    /**
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TroubleSegment other = (TroubleSegment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getStartIndex() == null ? other.getStartIndex() == null : this.getStartIndex().equals(other.getStartIndex()))
            && (this.getEndIndex() == null ? other.getEndIndex() == null : this.getEndIndex().equals(other.getEndIndex()))
            && (this.getEventCode() == null ? other.getEventCode() == null : this.getEventCode().equals(other.getEventCode()))
            && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()));
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getStartIndex() == null) ? 0 : getStartIndex().hashCode());
        result = prime * result + ((getEndIndex() == null) ? 0 : getEndIndex().hashCode());
        result = prime * result + ((getEventCode() == null) ? 0 : getEventCode().hashCode());
        result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
        return result;
    }

    /**
     */
    public enum Column {
        id("id"),
        routeId("routeId"),
        startIndex("startIndex"),
        endIndex("endIndex"),
        eventCode("eventCode"),
        ratio("ratio");

        /**
         */
        private final String column;

        /**
         */
        public String value() {
            return this.column;
        }

        /**
         */
        public String getValue() {
            return this.column;
        }

        /**
         */
        Column(String column) {
            this.column = column;
        }

        /**
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}