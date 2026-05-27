package com.gymbook.manage.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.gymbook.manage.DTO.CourseCountDTO;
import com.gymbook.manage.DTO.CourseDTO;
import com.gymbook.manage.DTO.CourseStatusCountDTO;
import com.gymbook.manage.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@Mapper
public interface CourseMapper 
{
    /**
     * 查询课程信息
     * 
     * @param id 课程信息主键
     * @return 课程信息
     */
    public Course selectCourseById(Long id);

    /**
     * 查询最多预约的课程
     *
     * @return 课程信息
     */
    public Course getMostCourse();

    /**
     * 查询课程信息列表
     * 
     * @param course 课程信息
     * @return 课程信息集合
     */
    public List<Course> selectCourseList(Course course);

    /**
     * 查询课程信息与场馆名称
     * @param course
     * @return
     */
    public List<Course> selectCourseWithVenueList(Course course);


    /**
     * 新增课程信息
     * 
     * @param course 课程信息
     * @return 结果
     */
    public int insertCourse(Course course);

    /**
     * 修改课程信息
     * 
     * @param course 课程信息
     * @return 结果
     */
    public int updateCourse(Course course);

    /**
     * 删除课程信息
     * 
     * @param id 课程信息主键
     * @return 结果
     */
    public int deleteCourseById(Long id);

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseByIds(Long[] ids);

    /**
     * 查询成功报名课程的学员信息
     * @param course
     * @return
     */
    List<Course> selectStudentList(Course course);


    /**
     * 统计指定时间区间内的课程预约量排名
     * @param begin
     * @param end
     * @return
     */
    List<CourseCountDTO> getCourseTop10(@Param("begin") LocalDateTime begin,@Param("end") LocalDateTime end);

    /**
     * 统计指定时间区间内某个教练的课程预约量
     * @param coachId
     * @param begin
     * @param end
     * @return
     */
    List<CourseCountDTO> getCoachCourse(@Param("coachId") Long coachId, @Param("begin") LocalDateTime begin,@Param("end") LocalDateTime end);

    List<CourseStatusCountDTO> getCourseStatusCount(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

    List<CourseStatusCountDTO> getCoachCourseStatusCount(@Param("coachId") Long coachId, @Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

    /**
     * 获取课程id和课程名
     * @return
     */
    List<CourseDTO> getCourseName();
}
