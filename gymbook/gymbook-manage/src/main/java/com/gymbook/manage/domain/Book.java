package com.gymbook.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 预约信息对象 book
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约id */
    private Long id;

    /** 申请预约的用户 */
    @Excel(name = "申请预约的用户")
    private Long userId;

    /** 预约的场馆 */
    @Excel(name = "预约的场馆")
    private Long venueId;

    /** 预约的课程 */
    @Excel(name = "预约的课程")
    private Long courseId;

    /** 预约的教练 */
    @Excel(name = "预约的教练")
    private Long coachId;

    /** 引入User，Venue和Course */
    private User user;

    private Venue venue;

    private Course course;

    /** 教练名 */
    private String coachName;

    /** 预约用户名 */
    private String nickName;

    /** 课程名 */
    private String courseName;

    /** 场馆名 */
    private String venueName;

    /** 预约申请的状态（0：待审核，1：已通过，2：已取消，3：已完成） */
    @Excel(name = "预约申请的状态", readConverterExp = "0=：待审核，1：已通过，2：已取消，3：已完成")
    private Long status;

    /** 预约的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约的时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date bookTime;

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

    public void setVenueId(Long venueId) 
    {
        this.venueId = venueId;
    }
    public Long getVenueId() 
    {
        return venueId;
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

    public void setStatus(Long status) 
    {
        this.status = status;
    }
    public Long getStatus() 
    {
        return status;
    }

    public void setBookTime(Date bookTime) 
    {
        this.bookTime = bookTime;
    }
    public Date getBookTime() 
    {
        return bookTime;
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

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }
    public String getCourseName()
    {
        return courseName;
    }

    public void setVenueName(String venueName)
    {
        this.venueName = venueName;
    }
    public String getVenueName()
    {
        return venueName;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public Venue getVenue() {
        return venue;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("venueId", getVenueId())
            .append("courseId", getCourseId())
            .append("coachId", getCoachId())
            .append("status", getStatus())
            .append("bookTime", getBookTime())
            .append("createTime", getCreateTime())
            .append("user", getUser())
            .append("venue", getVenue())
            .append("course", getCourse())
            .append("coachName", getCoachName())
            .append("nickName", getNickName())
            .append("courseName", getCourseName())
            .append("venueName", getVenueName())
            .toString();
    }
}
