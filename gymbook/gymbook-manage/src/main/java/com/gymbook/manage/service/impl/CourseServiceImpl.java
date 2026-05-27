package com.gymbook.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.common.utils.StringUtils;
import com.gymbook.manage.DTO.BookStatusCountDTO;
import com.gymbook.manage.DTO.CourseDTO;
import com.gymbook.manage.vo.BookStatusReportVO;
import com.gymbook.manage.vo.CourseNameReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.service.ICourseService;

/**
 * 课程信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@Service
public class CourseServiceImpl implements ICourseService 
{
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 查询课程信息
     * 
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public Course selectCourseById(Long id)
    {
        return courseMapper.selectCourseById(id);
    }

    /**
     * 查询课程信息列表
     * 
     * @param course 课程信息
     * @return 课程信息
     */
    @Override
    public List<Course> selectCourseList(Course course)
    {
        return courseMapper.selectCourseList(course);
    }

    @Override
    public List<Course> selectCourseWithVenueList(Course course) {
        // 打印传入的查询参数
        System.out.println("Received venueName: " + course.getVenueName());
        return courseMapper.selectCourseWithVenueList(course);
    }

    /**
     * 新增课程信息
     * 
     * @param course 课程信息
     * @return 结果
     */
    @Override
    public int insertCourse(Course course)
    {
        course.setCreateTime(DateUtils.getNowDate());
        return courseMapper.insertCourse(course);
    }

    /**
     * 修改课程信息
     * 
     * @param course 课程信息
     * @return 结果
     */
    @Override
    public int updateCourse(Course course)
    {
        return courseMapper.updateCourse(course);
    }

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseByIds(Long[] ids)
    {
        return courseMapper.deleteCourseByIds(ids);
    }

    /**
     * 删除课程信息信息
     * 
     * @param id 课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseById(Long id)
    {
        return courseMapper.deleteCourseById(id);
    }

    /**
     * 查询成功报名课程的学员信息
     * @param course
     * @return
     */
    @Override
    public List<Course> selectStudentList(Course course) {
        return courseMapper.selectStudentList(course);
    }

    /**
     * 查询最多预约的课程
     * @return
     */
    @Override
    public Course getMostCourse() {
        return courseMapper.getMostCourse();
    }

    /**
     * 获取课程名和id
     * @return
     */
    @Override
    public CourseNameReportVO getCourseName() {
        // 获取各个id对应的课程名
        List<CourseDTO> courseNameList = courseMapper.getCourseName();

        // 获取courseNameList中的课程id和课程名
        List<Integer> ids = courseNameList.stream().map(CourseDTO::getId).collect(Collectors.toList());
        String idList = StringUtils.join(ids, ",");

        List<String> names = courseNameList.stream().map(CourseDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        // 封装返回对象
        return CourseNameReportVO
                .builder()
                .idList(idList)
                .nameList(nameList)
                .build();
    }


}
