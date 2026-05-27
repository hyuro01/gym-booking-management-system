package com.gymbook.manage.service.impl;

import com.gymbook.manage.DTO.BookStatusCountDTO;
import com.gymbook.manage.DTO.CoachCountDTO;
import com.gymbook.manage.DTO.CourseCountDTO;
import com.gymbook.manage.DTO.CourseStatusCountDTO;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.mapper.FeedbackMapper;
import com.gymbook.manage.mapper.VenueMapper;
import com.gymbook.manage.service.CoachStatService;
import com.gymbook.manage.service.GymStatService;
import com.gymbook.manage.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CoachStatServiceImpl implements CoachStatService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private BookMapper bookMapper;

    /**
     * 获取当前登录的教练评分和总的教练平均评分
     * @param userId
     * @return
     */
    @Override
    public RateReportVO getCoachRateStatistics(Long userId) {
        // 当前集合用于存放个人评分,平均评分名称
        List<String> nameList = new ArrayList<>();
        nameList.add("个人评分");
        nameList.add("平均评分");

        // 获取平均评分和当前教练个人评分
        List<Double> rateList = new ArrayList<>();
        Double coachRate=feedbackMapper.getCoachRate(userId);
        Double avgRate=feedbackMapper.getAvgRate();
        rateList.add(coachRate);
        rateList.add(avgRate);

        log.info("nameList,rateList:{},{}",nameList,rateList);

        // 封装返回结果
        return RateReportVO
                .builder()
                .nameList(StringUtils.join(nameList,","))
                .rateList(StringUtils.join(rateList,","))
                .build();
    }

    /**
     * 课程设置状态统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @Override
    public CourseStatusReportVO getCoachCourseStatus(Long userId, LocalDate begin, LocalDate end) {
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同预约状态的预约数量
        List<CourseStatusCountDTO> courseStatusCount = courseMapper.getCoachCourseStatusCount(userId, beginTime, endTime);

        // 获取bookStatusCount中的状态名和预约量
        List<Integer> names = courseStatusCount.stream().map(CourseStatusCountDTO::getStatus).collect(Collectors.toList());
        String nameList = com.gymbook.common.utils.StringUtils.join(names, ",");

        List<Integer> numbers = courseStatusCount.stream().map(CourseStatusCountDTO::getNumber).collect(Collectors.toList());
        String numberList = com.gymbook.common.utils.StringUtils.join(numbers, ",");

        // 封装返回对象
        return CourseStatusReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 课程预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @Override
    public CourseReportVO getCourseStudent(Long userId, LocalDate begin, LocalDate end) {
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同课程的预约数量
        List<CourseCountDTO> courseStudent = courseMapper.getCoachCourse(userId, beginTime, endTime);

        // 获取courseTop10中的课程名和预约量
        List<String> names = courseStudent.stream().map(CourseCountDTO::getName).collect(Collectors.toList());
        String nameList = com.gymbook.common.utils.StringUtils.join(names, ",");

        List<Integer> numbers = courseStudent.stream().map(CourseCountDTO::getNumber).collect(Collectors.toList());
        String numberList = com.gymbook.common.utils.StringUtils.join(numbers, ",");

        // 封装返回结果数据
        return CourseReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 教练预约量统计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BookReportVO getCoachStudent(Long userId, LocalDate begin, LocalDate end) {
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
            // select count(*) from book where create_time = ? and coach_id = ?
            Map map = new HashMap();
            map.put("date",date);
            // 添加coachId条件
            map.put("coachId", userId);
            Integer book = bookMapper.coachStudentByMap(map);
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
