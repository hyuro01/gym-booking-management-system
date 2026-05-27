package com.gymbook.manage.service.impl;

import com.gymbook.manage.domain.Health;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.HealthMapper;
import com.gymbook.manage.service.UserStatService;
import com.gymbook.manage.vo.BmiReportVO;
import com.gymbook.manage.vo.BookReportVO;
import com.gymbook.manage.vo.WeightReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserStatServiceImpl implements UserStatService {

    @Autowired
    private HealthMapper healthMapper;

    @Autowired
    private BookMapper bookMapper;

    /**
     * 统计指定时间区间内的体重
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    public WeightReportVO getWeightStatistics(Long userId, LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while(!begin.equals(end)){
            // 计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 存放每天的体重数据
        List<Double> weightList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 查询date日期对应的体重数据
            // select weight from health where create_time = ? and user_id = ?
            Map map = new HashMap();
            map.put("date",date);
            // 添加userId条件
            map.put("userId", userId);
            Double weight = healthMapper.weightByMap(map);
            weight = weight == null ? 0.0 : weight;
            weightList.add(weight);


        }
        log.info("dateList,weightList:{},{}",dateList,weightList);
        // 封装返回结果
        return WeightReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .weightList(StringUtils.join(weightList,","))
                .build();
    }


    /**
     * 统计指定时间区间内的bmi
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    public BmiReportVO getBmiStatistics(Long userId, LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while(!begin.equals(end)){
            // 计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 存放每天的体重数据
        List<Double> bmiList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 查询date日期对应的bmi数据，保留两位小数
            // select ROUND(weight/(height*height),2) from health where create_time = ? and user_id = ?
            Map map = new HashMap();
            map.put("date",date);
            // 添加userId条件
            map.put("userId", userId);
            Double bmi = healthMapper.bmiByMap(map);
            bmi = bmi == null ? 0.0 : bmi;
            bmiList.add(bmi);
        }
        log.info("dateList,bmiList:{},{}",dateList,bmiList);
        // 封装返回结果
        return BmiReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .bmiList(StringUtils.join(bmiList,","))
                .build();
    }

    /**
     * 统计健身目标最新完成情况
     * @param userId
     * @param end
     * @return
     */
    @Override
    public Health getGoalStatistics(Long userId, LocalDate end) {
        return healthMapper.selectGoalByTime(userId,end);
    }

    /**
     * 统计全年健身房预约情况
     * @param userId
     * @return
     */
    @Override
    public BookReportVO getBookStatistics(Long userId, int year) {
        // 获取该年的第一天和最后一天
        LocalDate begin = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        // 当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while(!begin.equals(end)){
            // 计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 存放每天的预约数据
        List<Integer> bookList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 查询date日期对应的预约数据
            // select count(*) from book where create_time = ? and user_id = ?
            Map map = new HashMap();
            map.put("date",date);
            // 添加userId条件
            map.put("userId", userId);
            Integer book = bookMapper.bookTimeByMap(map);
            book = book == null ? 0 : book;
            bookList.add(book);


        }
        log.info("dateList,bookList:{},{}",dateList,bookList);
        // 封装返回结果
        return BookReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .bookList(StringUtils.join(bookList,","))
                .build();

    }
}
