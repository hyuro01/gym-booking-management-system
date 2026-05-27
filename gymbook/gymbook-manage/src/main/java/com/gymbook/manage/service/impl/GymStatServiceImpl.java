package com.gymbook.manage.service.impl;

import com.gymbook.common.utils.StringUtils;
import com.gymbook.manage.DTO.BookStatusCountDTO;
import com.gymbook.manage.DTO.CoachCountDTO;
import com.gymbook.manage.DTO.CourseCountDTO;
import com.gymbook.manage.DTO.CourseStatusCountDTO;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.mapper.VenueMapper;
import com.gymbook.manage.service.GymStatService;
import com.gymbook.manage.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GymStatServiceImpl implements GymStatService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private VenueMapper venueMapper;

    /**
     * 统计时间段内每个课程的报名人数
     * @param begin
     * @param end
     * @return
     */
    public CourseReportVO getCourseStatistics(LocalDate begin, LocalDate end){
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同课程的预约数量
        List<CourseCountDTO> courseTop10 = courseMapper.getCourseTop10(beginTime, endTime);

        // 获取courseTop10中的课程名和预约量
        List<String> names = courseTop10.stream().map(CourseCountDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        List<Integer> numbers = courseTop10.stream().map(CourseCountDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(numbers, ",");

        // 封装返回结果数据
        return CourseReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 统计时间段内每个教练的预约人数
     * @param begin
     * @param end
     * @return
     */
    public CoachReportVO getCoachStatistics(LocalDate begin, LocalDate end) {
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同教练的预约数量
        List<CoachCountDTO> coachTop10 = bookMapper.getCoachTop10(beginTime, endTime);

        // 获取coachTop10中的课程名和预约量
        List<String> names = coachTop10.stream().map(CoachCountDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        List<Integer> numbers = coachTop10.stream().map(CoachCountDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(numbers, ",");

        // 封装返回对象
        return CoachReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }


    /**
     * 统计时间区间内每个场馆每天的预约人数
     * @param begin
     * @param end
     * @return
     */
    public VenueReportVO getVenueStatistics(LocalDate begin, LocalDate end) {
        // 当前集合用于存放从begin到end范围内的每天日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while(!begin.equals(end)){
            // 计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 存放场馆名
        List<String> nameList = venueMapper.getVenueName();
        log.info("场馆列表: {}", nameList);

        // 存放所有场馆每天的预约量[[1,2,0],[2,3,1],[0,4,3]]
        List<List<Integer>> numberList = new ArrayList<>();

        // 循环获取每个场馆每天的预约量
        for (String name : nameList) {
            // 存储当前场馆每天的预约量
            List<Integer> dailyNumber = new ArrayList<>();

            // 遍历每一天，如果没有数据，设置为0
            for (LocalDate date : dateList) {
                // 查询每天各个场馆的预约量
                Map map = new HashMap();
                map.put("date",date);
                map.put("name",name);

                // 获取场馆预约数量number
                Integer number = venueMapper.getVenueCount(map);
                // 存入dailyNumber
                dailyNumber.add(number);

            }
            // 添加到总列表
            numberList.add(dailyNumber);
        }

        log.info("dateList,nameList,numberList:{},{},{}",dateList,nameList,numberList);

        // 封装返回结果
        return VenueReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .nameList(StringUtils.join(nameList,","))
                .numberList(numberList)
                .build();
    }


    /**
     * 统计各个预约状态的预约数量
     * @param begin
     * @param end
     * @return
     */
    public BookStatusReportVO getBookStatusStatistics(LocalDate begin, LocalDate end) {
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同预约状态的预约数量
        List<BookStatusCountDTO> bookStatusCount = bookMapper.getBookStatusCount(beginTime, endTime);

        // 获取bookStatusCount中的状态名和预约量
        List<Integer> names = bookStatusCount.stream().map(BookStatusCountDTO::getStatus).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        List<Integer> numbers = bookStatusCount.stream().map(BookStatusCountDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(numbers, ",");

        // 封装返回对象
        return BookStatusReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 统计各个设置状态的课程数量
     * @param begin
     * @param end
     * @return
     */
    public CourseStatusReportVO getCourseStatusStatistics(LocalDate begin, LocalDate end) {
        // 日期范围精确到时分秒
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取不同预约状态的预约数量
        List<CourseStatusCountDTO> courseStatusCount = courseMapper.getCourseStatusCount(beginTime, endTime);

        // 获取bookStatusCount中的状态名和预约量
        List<Integer> names = courseStatusCount.stream().map(CourseStatusCountDTO::getStatus).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        List<Integer> numbers = courseStatusCount.stream().map(CourseStatusCountDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(numbers, ",");

        // 封装返回对象
        return CourseStatusReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }
}
