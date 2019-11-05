package cn.org.hentai.simulator.entity;

/**
 * @Description: 问题路段
 * @author:
 * @date: 2018/11/21 9:30
 * @version: V1.0
 */
public class XTroubleSegment
{

    /**
     * 问题路段开始索引
     */
    private Integer startIndex;

    /**
     * 问题路段结束索引
     */
    private Integer endIndex;

    /**
     * 安全事件
     */
    private String event;


    /**
     * 概率
     * 根据概率决定此路段要不要发生问题
     */
    private Integer ratio;


    public XTroubleSegment(Integer startIndex, Integer endIndex, String event, Integer ratio)
    {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.event = event;
        this.ratio = ratio;
    }

    public Integer getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex)
    {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex()
    {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex)
    {
        this.endIndex = endIndex;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public Integer getRatio()
    {
        return ratio;
    }

    public void setRatio(Integer ratio)
    {
        this.ratio = ratio;
    }
}
