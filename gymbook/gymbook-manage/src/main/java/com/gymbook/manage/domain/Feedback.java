package com.gymbook.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 查看评价对象 feedback
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
public class Feedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价id */
    private Long id;

    /** 评价用户id */
    @Excel(name = "评价用户id")
    private Long userId;

    /** 被评价课程id */
    @Excel(name = "被评价课程id")
    private Long courseId;

    /** 被评价教练id */
    @Excel(name = "被评价教练id")
    private Long coachId;

    /** 被评价场馆id */
    @Excel(name = "被评价场馆id")
    private Long venueId;

    /** 评分 */
    @Excel(name = "评分")
    private Long rate;

    @Excel(name = "内容")
    private String remark;

    @Excel(name = "教练名")
    private String coachName;

    @Excel(name = "发布评价的用户名")
    private String nickName;

    /** 被评价课程 */
    @Excel(name = "被评价课程")
    private String courseName;

    /** 被评价场馆 */
    @Excel(name = "被评价场馆")
    private String venueName;


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

    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }
    public Long getCourseId() 
    {
        return courseId;
    }

    public void setCoachId(Long coachId) 
    {
        this.coachId = coachId;
    }
    public Long getCoachId() 
    {
        return coachId;
    }

    public void setVenueId(Long venueId) 
    {
        this.venueId = venueId;
    }
    public Long getVenueId() 
    {
        return venueId;
    }

    public void setRate(Long rate)
    {
        this.rate = rate;
    }
    public Long getRate()
    {
        return rate;
    }

    public void setRemark(String remark) { this.remark = remark; }
    public String getRemark() { return remark; }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    public String getNickName()
    {
        return nickName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
    public String getCourseName()
    {
        return courseName;
    }

    public void setCoachName(String coachName)
    {
        this.coachName = coachName;
    }
    public String getCoachName()
    {
        return coachName;
    }

    public void setVenueName(String venueName)
    {
        this.venueName = venueName;
    }
    public String getVenueName()
    {
        return venueName;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("courseId", getCourseId())
            .append("coachId", getCoachId())
            .append("venueId", getVenueId())
            .append("rate", getRate())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("nickName", getNickName())
            .append("courseName", getCourseName())
            .append("coachName", getCoachName())
            .append("venueName", getVenueName())
            .toString();
    }
}
