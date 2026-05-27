package com.gymbook.manage.controller;


import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.manage.service.CoachStatService;
import com.gymbook.manage.service.GymStatService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 健身教练数据统计Controller
 * 
 * @author wooyoungnauh
 * @date 2025-03-30
 */
@RestController
@RequestMapping("/manage/statistic3")
@Api(tags = "健身教练数据统计接口")
@Slf4j
public class CoachStatController {

    @Autowired
    private CoachStatService coachStatService;

    /**
     * 评分统计
     * @param userId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic3:list')")
    @GetMapping("/list")
    public AjaxResult coachRateStatistics(@RequestParam("userId") Long userId){
        return AjaxResult.success(coachStatService.getCoachRateStatistics(userId));
    }

    /**
     * 课程设置状态统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic3:coachCourseStatus')")
    @GetMapping("/coachCourseStatus")
    public AjaxResult coachCourseStatus(
            @RequestParam("userId") Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("课程设置状态统计： {}, {}", begin, end);
        return AjaxResult.success(coachStatService.getCoachCourseStatus(userId,begin,end));
    }

    /**
     * 课程预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic3:courseStudent')")
    @GetMapping("/courseStudent")
    public AjaxResult courseStudent(
            @RequestParam("userId") Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("课程预约量统计： {}, {}", begin, end);
        return AjaxResult.success(coachStatService.getCourseStudent(userId,begin,end));
    }

    /**
     * 教练预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic3:coachStudent')")
    @GetMapping("/coachStudent")
    public AjaxResult coachStudent(
            @RequestParam("userId") Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("教练预约量统计： {}, {}", begin, end);
        return AjaxResult.success(coachStatService.getCoachStudent(userId,begin,end));
    }

}
