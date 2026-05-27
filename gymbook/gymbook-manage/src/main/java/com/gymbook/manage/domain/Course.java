package com.gymbook.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 课程信息对象 course
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
public class Course extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程id */
    private Long id;

    /** 课程名 */
    @Excel(name = "课程名")
    private String name;

    /** 授课教练id */
    @Excel(name = "授课教练id")
    private Long coachId;

    /** 开课状态（申请中，授课中，已结束） */
    @Excel(name = "开课状态", readConverterExp = "申=请中，授课中，已结束")
    private Long status;

    /** 课程器材 */
    @Excel(name = "课程器材")
    private String equipment;

    /** 最大报名人数 */
    @Excel(name = "最大报名人数")
    private Long capacity;

    /** 开课场馆id */
    @Excel(name = "开课场馆id")
    private Long venueId;

    /** 开课时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "开课时间", width = 30, dateFormat = "HH:mm:ss")
    private Date startTime;

    private String venueName;  // 新添加的场馆名称字段

    private Venue venue; // 引入Venue类

    private User user;  // 引入User类

    private String coachName;

    // 报名的学员昵称
    @Excel(name = "学员昵称")
    private String nickName;

    // 学员手机号码
    @Excel(name = "学员手机号码")
    private String phonenumber;

    // 学员邮箱
    @Excel(name = "学员邮箱")
    private String email;

    /** 结束时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "HH:mm:ss")
    private Date closeTime;

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

    public void setCoachId(Long coachId) 
    {
        this.coachId = coachId;
    }
    public Long getCoachId() 
    {
        return coachId;
    }

    public void setCoachName(String coachName)
    {
        this.coachName = coachName;
    }
    public String getCoachName()
    {
        return coachName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    public String getNickName()
    {
        return nickName;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return email;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }
    public Long getStatus() 
    {
        return status;
    }

    public void setEquipment(String equipment) 
    {
        this.equipment = equipment;
    }
    public String getEquipment() 
    {
        return equipment;
    }

    public void setCapacity(Long capacity) 
    {
        this.capacity = capacity;
    }
    public Long getCapacity() 
    {
        return capacity;
    }

    public void setVenueId(Long venueId) 
    {
        this.venueId = venueId;
    }
    public Long getVenueId() 
    {
        return venueId;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }
    public Date getStartTime() 
    {
        return startTime;
    }

    public void setCloseTime(Date closeTime) 
    {
        this.closeTime = closeTime;
    }
    public Date getCloseTime() 
    {
        return closeTime;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("coachId", getCoachId())
            .append("status", getStatus())
            .append("equipment", getEquipment())
            .append("capacity", getCapacity())
            .append("venueId", getVenueId())
            .append("startTime", getStartTime())
            .append("closeTime", getCloseTime())
            .append("createTime", getCreateTime())
            .append("venueName",getVenueName())
            .append("user",getUser())
            .append("coachName", getCoachName())
            .append("nickName", getNickName())
            .append("phonenumber", getPhonenumber())
            .append("email", getEmail())
            .toString();
    }
}
