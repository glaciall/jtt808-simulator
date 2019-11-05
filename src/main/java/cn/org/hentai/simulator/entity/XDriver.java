package cn.org.hentai.simulator.entity;

/**
 * @Description: 驾驶员
 * @author:
 * @date: 2018/11/21 9:49
 * @version: V1.0
 */
public class XDriver
{

    /**
     * 驾驶员ID
     */
    private Long id;

    /**
     * 驾驶员名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String photoPath;

    public XDriver(String photoPath)
    {
        this.photoPath = photoPath;
    }

    public Long getId()
    {
        return id;
    }

    public XDriver setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public XDriver setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getPhotoPath()
    {
        return photoPath;
    }

    public XDriver setPhotoPath(String photoPath)
    {
        this.photoPath = photoPath;
        return this;
    }
}
