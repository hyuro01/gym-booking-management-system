package com.gymbook.manage.service;

import com.gymbook.manage.vo.*;

import java.time.LocalDate;

public interface CoachStatService {

    /**
     * 获取当前登录的教练评分和总的教练平均评分
     * @param userId
     * @return
     */
    RateReportVO getCoachRateStatistics(Long userId);

    /**
     * 课程设置状态统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    CourseStatusReportVO getCoachCourseStatus(Long userId, LocalDate begin, LocalDate end);

    /**
     * 课程预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    CourseReportVO getCourseStudent(Long userId, LocalDate begin, LocalDate end);

    /**
     * 教练预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    BookReportVO getCoachStudent(Long userId, LocalDate begin, LocalDate end);
}
