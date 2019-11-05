package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;

public class StayPoint implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long routeid;

    /**
     *
     */
    private Double longitude;

    /**
     *
     */
    private Double latitude;

    /**
     *
     */
    private Integer minTime;

    /**
     *
     */
    private Integer maxTime;

    /**
     *
     */
    private Integer ratio;

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_stay_point.id
     *
     * @return the value of x_stay_point.id
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public StayPoint withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.id
     *
     * @param id the value for x_stay_point.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_stay_point.routeid
     *
     * @return the value of x_stay_point.routeid
     */
    public Long getRouteid() {
        return routeid;
    }

    /**
     */
    public StayPoint withRouteid(Long routeid) {
        this.setRouteid(routeid);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.routeid
     *
     * @param routeid the value for x_stay_point.routeid
     */
    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    /**
     * This method returns the value of the database column x_stay_point.longitude
     *
     * @return the value of x_stay_point.longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     */
    public StayPoint withLongitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.longitude
     *
     * @param longitude the value for x_stay_point.longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method returns the value of the database column x_stay_point.latitude
     *
     * @return the value of x_stay_point.latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     */
    public StayPoint withLatitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.latitude
     *
     * @param latitude the value for x_stay_point.latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * This method returns the value of the database column x_stay_point.minTime
     *
     * @return the value of x_stay_point.minTime
     */
    public Integer getMinTime() {
        return minTime;
    }

    /**
     */
    public StayPoint withMinTime(Integer minTime) {
        this.setMinTime(minTime);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.minTime
     *
     * @param minTime the value for x_stay_point.minTime
     */
    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    /**
     * This method returns the value of the database column x_stay_point.maxTime
     *
     * @return the value of x_stay_point.maxTime
     */
    public Integer getMaxTime() {
        return maxTime;
    }

    /**
     */
    public StayPoint withMaxTime(Integer maxTime) {
        this.setMaxTime(maxTime);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.maxTime
     *
     * @param maxTime the value for x_stay_point.maxTime
     */
    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * This method returns the value of the database column x_stay_point.ratio
     *
     * @return the value of x_stay_point.ratio
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     */
    public StayPoint withRatio(Integer ratio) {
        this.setRatio(ratio);
        return this;
    }

    /**
     * This method sets the value of the database column x_stay_point.ratio
     *
     * @param ratio the value for x_stay_point.ratio
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
        sb.append(", routeid=").append(routeid);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", minTime=").append(minTime);
        sb.append(", maxTime=").append(maxTime);
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
        StayPoint other = (StayPoint) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRouteid() == null ? other.getRouteid() == null : this.getRouteid().equals(other.getRouteid()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getMinTime() == null ? other.getMinTime() == null : this.getMinTime().equals(other.getMinTime()))
            && (this.getMaxTime() == null ? other.getMaxTime() == null : this.getMaxTime().equals(other.getMaxTime()))
            && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()));
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRouteid() == null) ? 0 : getRouteid().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getMinTime() == null) ? 0 : getMinTime().hashCode());
        result = prime * result + ((getMaxTime() == null) ? 0 : getMaxTime().hashCode());
        result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
        return result;
    }

    /**
     */
    public enum Column {
        id("id"),
        routeid("routeid"),
        longitude("longitude"),
        latitude("latitude"),
        minTime("minTime"),
        maxTime("maxTime"),
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