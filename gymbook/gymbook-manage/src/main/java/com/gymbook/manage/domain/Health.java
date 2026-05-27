package com.gymbook.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 健康管理对象 health
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
public class Health extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 健康数据id */
    private Long id;

    /** 数据所属用户id */
    @Excel(name = "数据所属用户id")
    private Long userId;

    /** 体重（kg） */
    @Excel(name = "体重", readConverterExp = "kg")
    private Double weight;

    /** 身高（m） */
    @Excel(name = "身高", readConverterExp = "m")
    private Double height;

    /** 健身目标 */
    @Excel(name = "健身目标")
    private String goal;

    /** 健身目标完成度 */
    @Excel( name = "健身目标完成度")
    private Long percent;

    public void setId(Long id) 
    {
        this.id = id;
    }
    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }
    public Long getUserId() 
    {
        return userId;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }
    public Double getWeight()
    {
        return weight;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }
    public Double getHeight()
    {
        return height;
    }

    public void setGoal(String goal) 
    {
        this.goal = goal;
    }
    public String getGoal() 
    {
        return goal;
    }

    public void setPercent(Long percent)
    {
        this.percent = percent;
    }
    public Long getPercent()
    {
        return percent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("weight", getWeight())
            .append("height", getHeight())
            .append("goal", getGoal())
            .append("percent", getPercent())
            .append("createTime", getCreateTime())
            .toString();
    }
}
