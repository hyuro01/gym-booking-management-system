package com.gymbook.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 场馆信息对象 venue
 * 
 * @author wooyoungnauh
 * @date 2025-02-19
 */
public class Venue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 场馆id */
    private Long id;

    /** 场馆名称 */
    @Excel(name = "场馆名称")
    private String name;

    /** 最大容纳人数 */
    @Excel(name = "最大容纳人数")
    private Long capacity;

    /** 营业开始时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "营业开始时间", width = 30, dateFormat = "HH:mm:ss")
    private Date openTime;

    /** 营业结束时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "营业结束时间", width = 30, dateFormat = "HH:mm:ss")
    private Date closeTime;

    /** 场馆可以进行的运动 */
    @Excel(name = "场馆器材")
    private String info;

    /** 场馆状态（1：营业，2：关闭） */
    @Excel(name = "场馆状态", readConverterExp = "1=：营业，2：关闭")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }
    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
    public String getName() 
    {
        return name;
    }

    public void setCapacity(Long capacity) 
    {
        this.capacity = capacity;
    }
    public Long getCapacity() 
    {
        return capacity;
    }

    public void setOpenTime(Date openTime) 
    {
        this.openTime = openTime;
    }
    public Date getOpenTime() 
    {
        return openTime;
    }

    public void setCloseTime(Date closeTime) 
    {
        this.closeTime = closeTime;
    }
    public Date getCloseTime() 
    {
        return closeTime;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
    public String getInfo()
    {
        return info;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }
    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("capacity", getCapacity())
            .append("openTime", getOpenTime())
            .append("closeTime", getCloseTime())
            .append("info", getInfo())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
