package com.gymbook.manage.service;

import com.gymbook.manage.vo.*;

import java.time.LocalDate;

public interface GymStatService {

    /**
     * 统计时间段内每个课程的报名人数
     * @param begin
     * @param end
     * @return
     */
    CourseReportVO getCourseStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计时间段内每个教练的预约人数
     * @param begin
     * @param end
     * @return
     */
    CoachReportVO getCoachStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计时间区间内每个场馆每天的预约人数
     * @param begin
     * @param end
     * @return
     */
    VenueReportVO getVenueStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计各个预约状态的预约数量
     * @param begin
     * @param end
     * @return
     */
    BookStatusReportVO getBookStatusStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计各个设置状态的课程数量
     * @param begin
     * @param end
     * @return
     */
    CourseStatusReportVO getCourseStatusStatistics(LocalDate begin, LocalDate end);
}
