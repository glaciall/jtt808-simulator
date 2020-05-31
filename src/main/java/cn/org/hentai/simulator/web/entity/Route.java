package cn.org.hentai.simulator.web.entity;

import java.io.Serializable;

public class Route implements Serializable {
    /**
     *   id
     *
     */
    private Long id;

    /**
     *   when
     *
     */
    private String name;

    /**
     *   minSpeed（公里每小时）
     *
     */
    private Integer minSpeed;

    /**
     *   maxSpeed
     *
     */
    private Integer maxSpeed;

    /**
     *   里程（公里）
     *
     */
    private Integer mileages;

    private String fingerPrint;

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column x_route.id
     *
     * @return the value of x_route.id
     */
    public Long getId() {
        return id;
    }

    /**
     */
    public Route withId(Long id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column x_route.id
     *
     * @param id the value for x_route.id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column x_route.when
     *
     * @return the value of x_route.when
     */
    public String getName() {
        return name;
    }

    /**
     */
    public Route withName(String name) {
        this.setName(name);
        return this;
    }

    /**
     * This method sets the value of the database column x_route.when
     *
     * @param name the value for x_route.when
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the value of the database column x_route.minSpeed
     *
     * @return the value of x_route.minSpeed
     */
    public Integer getMinSpeed() {
        return minSpeed;
    }

    /**
     */
    public Route withMinSpeed(Integer minSpeed) {
        this.setMinSpeed(minSpeed);
        return this;
    }

    /**
     * This method sets the value of the database column x_route.minSpeed
     *
     * @param minSpeed the value for x_route.minSpeed
     */
    public void setMinSpeed(Integer minSpeed) {
        this.minSpeed = minSpeed;
    }

    /**
     * This method returns the value of the database column x_route.maxSpeed
     *
     * @return the value of x_route.maxSpeed
     */
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    /**
     */
    public Route withMaxSpeed(Integer maxSpeed) {
        this.setMaxSpeed(maxSpeed);
        return this;
    }

    /**
     * This method sets the value of the database column x_route.maxSpeed
     *
     * @param maxSpeed the value for x_route.maxSpeed
     */
    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * This method returns the value of the database column x_route.mileages
     *
     * @return the value of x_route.mileages
     */
    public Integer getMileages() {
        return mileages;
    }

    /**
     */
    public Route withMileages(Integer mileages) {
        this.setMileages(mileages);
        return this;
    }

    public Integer getKilometers()
    {
        if (this.mileages == null) return null;
        else return this.mileages / 1000;
    }

    /**
     * This method sets the value of the database column x_route.mileages
     *
     * @param mileages the value for x_route.mileages
     */
    public void setMileages(Integer mileages) {
        this.mileages = mileages;
    }

    public String getFingerPrint()
    {
        return fingerPrint;
    }

    public Route setFingerPrint(String fingerPrint)
    {
        this.fingerPrint = fingerPrint;
        return this;
    }

    /**
     */
    @Override
    public String toString()
    {
        return "Route{" + "id=" + id + ", when='" + name + '\'' + ", minSpeed=" + minSpeed + ", maxSpeed=" + maxSpeed + ", mileages=" + mileages + ", fingerPrint='" + fingerPrint + '\'' + '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (getId() != null ? !getId().equals(route.getId()) : route.getId() != null) return false;
        if (getName() != null ? !getName().equals(route.getName()) : route.getName() != null) return false;
        if (getMinSpeed() != null ? !getMinSpeed().equals(route.getMinSpeed()) : route.getMinSpeed() != null)
            return false;
        if (getMaxSpeed() != null ? !getMaxSpeed().equals(route.getMaxSpeed()) : route.getMaxSpeed() != null)
            return false;
        if (getMileages() != null ? !getMileages().equals(route.getMileages()) : route.getMileages() != null)
            return false;
        if (getFingerPrint() != null ? !getFingerPrint().equals(route.getFingerPrint()) : route.getFingerPrint() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getMinSpeed() != null ? getMinSpeed().hashCode() : 0);
        result = 31 * result + (getMaxSpeed() != null ? getMaxSpeed().hashCode() : 0);
        result = 31 * result + (getMileages() != null ? getMileages().hashCode() : 0);
        result = 31 * result + (getFingerPrint() != null ? getFingerPrint().hashCode() : 0);
        return result;
    }

    /**
     */
    public enum Column {
        id("id"),
        name("when"),
        minSpeed("minSpeed"),
        maxSpeed("maxSpeed"),
        mileages("mileages"),
        fingerPrint("fingerPrint");

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