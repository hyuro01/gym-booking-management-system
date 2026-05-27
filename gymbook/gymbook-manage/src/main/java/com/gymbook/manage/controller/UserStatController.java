package com.gymbook.manage.controller;


import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.result.Result;
import com.gymbook.manage.service.UserStatService;
import com.gymbook.manage.vo.WeightReportVO;
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
 * 个人数据统计Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/manage/statistic2")
@Api(tags = "个人数据统计接口")
@Slf4j
public class UserStatController {

    @Autowired
    private UserStatService userStatService;

    /**
     * 体重统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic2:list')")
    @GetMapping("/list")
    public AjaxResult weightStatistics(
            @RequestParam("userId") Long userId, // 添加 userId 作为请求参数
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("体重数据统计：{}, {}, {}", userId, begin, end);
        return AjaxResult.success(userStatService.getWeightStatistics(userId,begin,end));
    }


    /**
     * bmi统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic2:bmi')")
    @GetMapping("/bmi")
    public AjaxResult bmiStatistics(
            @RequestParam("userId") Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("bmi数据统计：{},{}",begin,end);
        return AjaxResult.success(userStatService.getBmiStatistics(userId,begin,end));
    }

    /**
     * 健身目标统计
     * @param userId
     * @param end
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic2:goal')")
    @GetMapping("/goal")
    public AjaxResult goalStatistics(
            @RequestParam("userId") Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("健身目标数据统计：{}",end);
        return AjaxResult.success(userStatService.getGoalStatistics(userId,end));
    }

    /**
     * 全年健身预约数量统计
     * @param userId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistic2:booktime')")
    @GetMapping("/booktime")
    public AjaxResult bookStatistics(@RequestParam("userId") Long userId, int year){
        log.info("健身预约数据统计：{}, {}",userId, year);
        return AjaxResult.success(userStatService.getBookStatistics(userId, year));
    }
}
