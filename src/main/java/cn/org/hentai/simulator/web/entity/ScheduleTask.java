package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;
import java.util.Date;

public class ScheduleTask implements Serializable {
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
    private Long driverId;

    /**
     *
     */
    private Long vehicleId;

    /**
     *
     */
    private String fromTime;

    /**
     *
     */
    private String endTime;

    /**
     *
     */
    private Integer ratio;

    /**
     *
     */
    private Integer daysInterval;

    /**
     *
     */
    private Integer driveCount;

    /**
     *
     */
    private Date lastDriveTime;

    /**
     * 运行次数
     */
    private Integer runCount;

    /**
     * 驾驶员名称
     */
    private String driverName;

    /**
     * 线路名称
     */
    private String routeName;

    /**
     * 车辆名称
     */
    private String vehicleName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_schedule_task.id
     *
     * @return the value of x_schedule_task.id
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public ScheduleTask withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.id
     *
     * @param id the value for x_schedule_task.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_schedule_task.routeId
     *
     * @return the value of x_schedule_task.routeId
     */
    public Long getRouteId() {
        return routeId;
    }

    /**
     */
    public ScheduleTask withRouteId(Long routeId) {
        this.setRouteId(routeId);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.routeId
     *
     * @param routeId the value for x_schedule_task.routeId
     */
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    /**
     * This method returns the value of the database column x_schedule_task.driverId
     *
     * @return the value of x_schedule_task.driverId
     */
    public Long getDriverId() {
        return driverId;
    }

    /**
     */
    public ScheduleTask withDriverId(Long driverId) {
        this.setDriverId(driverId);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.driverId
     *
     * @param driverId the value for x_schedule_task.driverId
     */
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    /**
     * This method returns the value of the database column x_schedule_task.vehicleId
     *
     * @return the value of x_schedule_task.vehicleId
     */
    public Long getVehicleId() {
        return vehicleId;
    }

    /**
     */
    public ScheduleTask withVehicleId(Long vehicleId) {
        this.setVehicleId(vehicleId);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.vehicleId
     *
     * @param vehicleId the value for x_schedule_task.vehicleId
     */
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * This method returns the value of the database column x_schedule_task.fromTime
     *
     * @return the value of x_schedule_task.fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     */
    public ScheduleTask withFromTime(String fromTime) {
        this.setFromTime(fromTime);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.fromTime
     *
     * @param fromTime the value for x_schedule_task.fromTime
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * This method returns the value of the database column x_schedule_task.endTime
     *
     * @return the value of x_schedule_task.endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     */
    public ScheduleTask withEndTime(String endTime) {
        this.setEndTime(endTime);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.endTime
     *
     * @param endTime the value for x_schedule_task.endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * This method returns the value of the database column x_schedule_task.ratio
     *
     * @return the value of x_schedule_task.ratio
     */
    public Integer getRatio() {
        return ratio;
    }

    /**
     */
    public ScheduleTask withRatio(Integer ratio) {
        this.setRatio(ratio);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.ratio
     *
     * @param ratio the value for x_schedule_task.ratio
     */
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    /**
     * This method returns the value of the database column x_schedule_task.daysInterval
     *
     * @return the value of x_schedule_task.daysInterval
     */
    public Integer getDaysInterval() {
        return daysInterval;
    }

    /**
     */
    public ScheduleTask withDaysInterval(Integer daysInterval) {
        this.setDaysInterval(daysInterval);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.daysInterval
     *
     * @param daysInterval the value for x_schedule_task.daysInterval
     */
    public void setDaysInterval(Integer daysInterval) {
        this.daysInterval = daysInterval;
    }

    /**
     * This method returns the value of the database column x_schedule_task.driveCount
     *
     * @return the value of x_schedule_task.driveCount
     */
    public Integer getDriveCount() {
        return driveCount;
    }

    /**
     */
    public ScheduleTask withDriveCount(Integer driveCount) {
        this.setDriveCount(driveCount);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.driveCount
     *
     * @param driveCount the value for x_schedule_task.driveCount
     */
    public void setDriveCount(Integer driveCount) {
        this.driveCount = driveCount;
    }

    /**
     * This method returns the value of the database column x_schedule_task.lastDriveTime
     *
     * @return the value of x_schedule_task.lastDriveTime
     */
    public Date getLastDriveTime() {
        return lastDriveTime;
    }

    /**
     */
    public ScheduleTask withLastDriveTime(Date lastDriveTime) {
        this.setLastDriveTime(lastDriveTime);
        return this;
    }

    /**
     * This method sets the value of the database column x_schedule_task.lastDriveTime
     *
     * @param lastDriveTime the value for x_schedule_task.lastDriveTime
     */
    public void setLastDriveTime(Date lastDriveTime) {
        this.lastDriveTime = lastDriveTime;
    }

    public Integer getRunCount()
    {
        return runCount;
    }

    public ScheduleTask setRunCount(Integer runCount)
    {
        this.runCount = runCount;
        return this;
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
        sb.append(", driverId=").append(driverId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", fromTime=").append(fromTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", ratio=").append(ratio);
        sb.append(", daysInterval=").append(daysInterval);
        sb.append(", driveCount=").append(driveCount);
        sb.append(", lastDriveTime=").append(lastDriveTime);
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
        ScheduleTask other = (ScheduleTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getDriverId() == null ? other.getDriverId() == null : this.getDriverId().equals(other.getDriverId()))
            && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
            && (this.getFromTime() == null ? other.getFromTime() == null : this.getFromTime().equals(other.getFromTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getRatio() == null ? other.getRatio() == null : this.getRatio().equals(other.getRatio()))
            && (this.getDaysInterval() == null ? other.getDaysInterval() == null : this.getDaysInterval().equals(other.getDaysInterval()))
            && (this.getDriveCount() == null ? other.getDriveCount() == null : this.getDriveCount().equals(other.getDriveCount()))
            && (this.getLastDriveTime() == null ? other.getLastDriveTime() == null : this.getLastDriveTime().equals(other.getLastDriveTime()));
    }

    /**
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getDriverId() == null) ? 0 : getDriverId().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getFromTime() == null) ? 0 : getFromTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getRatio() == null) ? 0 : getRatio().hashCode());
        result = prime * result + ((getDaysInterval() == null) ? 0 : getDaysInterval().hashCode());
        result = prime * result + ((getDriveCount() == null) ? 0 : getDriveCount().hashCode());
        result = prime * result + ((getLastDriveTime() == null) ? 0 : getLastDriveTime().hashCode());
        return result;
    }

    /**
     */
    public enum Column {
        id("id"),
        routeId("routeId"),
        driverId("driverId"),
        vehicleId("vehicleId"),
        fromTime("fromTime"),
        endTime("endTime"),
        ratio("ratio"),
        daysInterval("daysInterval"),
        driveCount("driveCount"),
        lastDriveTime("lastDriveTime");

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