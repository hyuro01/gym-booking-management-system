package com.gymbook.manage.service;

import com.gymbook.manage.domain.Health;
import com.gymbook.manage.vo.BmiReportVO;
import com.gymbook.manage.vo.BookReportVO;
import com.gymbook.manage.vo.WeightReportVO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserStatService {

    /**
     * 统计指定时间区间内的体重
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    WeightReportVO getWeightStatistics(Long userId, LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内的bmi
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    BmiReportVO getBmiStatistics(Long userId, LocalDate begin, LocalDate end);

    /**
     * 统计健身目标最新完成情况
     * @param userId
     * @param end
     * @return
     */
    Health getGoalStatistics(Long userId, LocalDate end);


    /**
     * 统计全年健身房预约情况
     * @param userId
     * @return
     */
    BookReportVO getBookStatistics(Long userId, int year);
}
