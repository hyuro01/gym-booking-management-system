package com.gymbook.manage.controller;


import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.manage.service.GymStatService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 健身房数据统计Controller
 * 
 * @author wooyoungnauh
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/manage/statistic1")
@Api(tags = "健身房数据统计接口")
@Slf4j
public class GymStatController {

    @Autowired
    private GymStatService gymStatService;

    /**
     * 课程统计
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic1:list')")
    @GetMapping("/list")
    public AjaxResult courseStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("课程数据top10统计： {}, {}", begin, end);
        return AjaxResult.success(gymStatService.getCourseStatistics(begin,end));
    }

    /**
     * 教练统计
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic1:coach')")
    @GetMapping("/coach")
    public AjaxResult coachStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("教练数据top10统计： {}, {}", begin, end);
        return AjaxResult.success(gymStatService.getCoachStatistics(begin,end));
    }

    /**
     * 场馆统计
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic1:venueCount')")
    @GetMapping("/venueCount")
    public AjaxResult venueStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("场馆每天预约数据统计： {}, {}", begin, end);
        return AjaxResult.success(gymStatService.getVenueStatistics(begin,end));
    }

    /**
     * 预约状态统计
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic1:bookStatus')")
    @GetMapping("/bookStatus")
    public AjaxResult bookStatusStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("预约状态统计： {}, {}", begin, end);
        return AjaxResult.success(gymStatService.getBookStatusStatistics(begin,end));
    }

    /**
     * 课程设置状态统计
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic1:courseStatus')")
    @GetMapping("/courseStatus")
    public AjaxResult courseStatusStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("课程设置状态统计： {}, {}", begin, end);
        return AjaxResult.success(gymStatService.getCourseStatusStatistics(begin,end));
    }

}
