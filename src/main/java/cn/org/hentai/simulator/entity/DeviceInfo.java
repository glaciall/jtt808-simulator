package cn.org.hentai.simulator.entity;

import cn.org.hentai.simulator.jtt808.util.DeviceAttributesetUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by matrixy when 2020/5/8.
 * 车载终端信息
 */
public class DeviceInfo implements Serializable
{
    // 终端信息，用于车辆注册时上报
    private String vehicleNumber;               // 车辆标识，这里就是车牌号
    private int provinceId;                     // 省域ID，2个字节
    private int cityId;                         // 市县域ID，2个字节
    private String manufacturerId;              // 制造商ID，最多5个字节
    private String deviceModel;                 // 终端型号，最多20个字节
    private int plateColor;                     // 车牌颜色，1个字节

    public Map<Integer, byte[]> attributeset;

    public byte[] getAttribute(String name)
    {
        return getAttribute(DeviceAttributesetUtils.getAttributeId(name));
    }

    public byte[] getAttribute(int attrId)
    {
        if (attrId == -1) return null;
        return null;
    }

    public String getVehicleNumber()
    {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber)
    {
        this.vehicleNumber = vehicleNumber;
    }

    public DeviceInfo withVehicleNumber(String vehicleNumber)
    {
        setVehicleNumber(vehicleNumber);
        return this;
    }

    public int getProvinceId()
    {
        return provinceId;
    }

    public void setProvinceId(int provinceId)
    {
        this.provinceId = provinceId;
    }

    public DeviceInfo withProvinceId(int provinceId)
    {
        setProvinceId(provinceId);
        return this;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }

    public DeviceInfo withCityId(int cityId)
    {
        setCityId(cityId);
        return this;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public DeviceInfo withManufacturerId(String manufacturerId)
    {
        setManufacturerId(manufacturerId);
        return this;
    }

    public String getDeviceModel()
    {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel)
    {
        this.deviceModel = deviceModel;
    }

    public DeviceInfo withDeviceModel(String deviceModel)
    {
        setDeviceModel(deviceModel);
        return this;
    }

    public int getPlateColor()
    {
        return plateColor;
    }

    public void setPlateColor(int plateColor)
    {
        this.plateColor = plateColor;
    }

    public DeviceInfo withPlateColor(int color)
    {
        setPlateColor(color);
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof DeviceInfo)) return false;

        DeviceInfo that = (DeviceInfo) o;

        if (getProvinceId() != that.getProvinceId()) return false;
        if (getCityId() != that.getCityId()) return false;
        if (getPlateColor() != that.getPlateColor()) return false;
        if (getVehicleNumber() != null ? !getVehicleNumber().equals(that.getVehicleNumber()) : that.getVehicleNumber() != null)
            return false;
        if (getManufacturerId() != null ? !getManufacturerId().equals(that.getManufacturerId()) : that.getManufacturerId() != null)
            return false;
        return getDeviceModel() != null ? getDeviceModel().equals(that.getDeviceModel()) : that.getDeviceModel() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getVehicleNumber() != null ? getVehicleNumber().hashCode() : 0;
        result = 31 * result + getProvinceId();
        result = 31 * result + getCityId();
        result = 31 * result + (getManufacturerId() != null ? getManufacturerId().hashCode() : 0);
        result = 31 * result + (getDeviceModel() != null ? getDeviceModel().hashCode() : 0);
        result = 31 * result + getPlateColor();
        return result;
    }

    @Override
    public String toString()
    {
        return "DeviceInfo{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", manufacturerId='" + manufacturerId + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", plateColor=" + plateColor +
                '}';
    }
}
