package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;

/**
 * Created By MBG-GUI-EXTENSION https://github.com/spawpaw/mybatis-generator-gui-extension
 * Description:
 * 
 *
 * @author 
 */
public class DriverPhoto implements Serializable {
    /**
     *
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    private Long id;

    /**
     *   驾驶员ID
     *
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    private Long driverId;

    /**
     * 驾驶员名称
     */
    private String driverName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public DriverPhoto widthDriverName(String driverName) {
        this.setDriverName(driverName);
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     *   驾驶员头像
     *
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    private String photo;

    public DriverPhoto() {
    }

    public DriverPhoto(Long driverId, String photo) {
        this.driverId = driverId;
        this.photo = photo;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_driver_photo.id
     *
     * @return the value of x_driver_photo.id
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public DriverPhoto withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_driver_photo.id
     *
     * @param id the value for x_driver_photo.id
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_driver_photo.driverId
     *
     * @return the value of x_driver_photo.driverId
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public Long getDriverId() {
        return driverId;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public DriverPhoto withDriverId(Long driverId) {
        this.setDriverId(driverId);
        return this;
    }

    /**
     * This method sets the value of the database column x_driver_photo.driverId
     *
     * @param driverId the value for x_driver_photo.driverId
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    /**
     * This method returns the value of the database column x_driver_photo.photo
     *
     * @return the value of x_driver_photo.photo
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public DriverPhoto withPhoto(String photo) {
        this.setPhoto(photo);
        return this;
    }

    /**
     * This method sets the value of the database column x_driver_photo.photo
     *
     * @param photo the value for x_driver_photo.photo
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", driverId=").append(driverId);
        sb.append(", photo=").append(photo);
        sb.append("]");
        return sb.toString();
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
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
        DriverPhoto other = (DriverPhoto) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDriverId() == null ? other.getDriverId() == null : this.getDriverId().equals(other.getDriverId()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()));
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDriverId() == null) ? 0 : getDriverId().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        return result;
    }

    /**
     *
     * @mbg.generated Mon Dec 03 11:03:06 CST 2018
     */
    public enum Column {
        id("id"),
        driverId("driverId"),
        photo("photo");

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        private final String column;

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        public String value() {
            return this.column;
        }

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        public String getValue() {
            return this.column;
        }

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        Column(String column) {
            this.column = column;
        }

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        public String desc() {
            return this.column + " DESC";
        }

        /**
         *
         * @mbg.generated Mon Dec 03 11:03:06 CST 2018
         */
        public String asc() {
            return this.column + " ASC";
        }
    }
}