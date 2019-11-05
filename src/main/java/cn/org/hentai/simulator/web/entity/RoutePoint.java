package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;

public class RoutePoint implements Serializable {
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
    private Double longitude;

    /**
     *
     */
    private Double latitude;

    private Double lng;
    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_route_point.id
     *
     * @return the value of x_route_point.id
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public RoutePoint withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_route_point.id
     *
     * @param id the value for x_route_point.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_route_point.routeId
     *
     * @return the value of x_route_point.routeId
     */
    public Long getRouteId() {
        return routeId;
    }

    /**
     */
    public RoutePoint withRouteId(Long routeId) {
        this.setRouteId(routeId);
        return this;
    }

    /**
     * This method sets the value of the database column x_route_point.routeId
     *
     * @param routeId the value for x_route_point.routeId
     */
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    /**
     * This method returns the value of the database column x_route_point.longitude
     *
     * @return the value of x_route_point.longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     */
    public RoutePoint withLongitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    /**
     * This method sets the value of the database column x_route_point.longitude
     *
     * @param longitude the value for x_route_point.longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method returns the value of the database column x_route_point.latitude
     *
     * @return the value of x_route_point.latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     */
    public RoutePoint withLatitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    /**
     * This method sets the value of the database column x_route_point.latitude
     *
     * @param latitude the value for x_route_point.latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
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
        RoutePoint other = (RoutePoint) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()));
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        return result;
    }

    /**
     */
    public enum Column {
        id("id"),
        routeId("routeId"),
        longitude("longitude"),
        latitude("latitude");

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