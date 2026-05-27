package com.gymbook.manage.service.impl;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.mapper.CourseApplyMapper;
import com.gymbook.manage.service.ICourseApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@Service
public class CourseApplyServiceImpl implements ICourseApplyService
{
    @Autowired
    private CourseApplyMapper courseApplyMapper;

    /**
     * 查询课程信息
     * 
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public Course selectCourseById(Long id)
    {
        return courseApplyMapper.selectCourseById(id);
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
        return courseApplyMapper.selectCourseList(course);
    }

    @Override
    public List<Course> selectCourseApplyList(Course course) {
        return courseApplyMapper.selectCourseApplyList(course);
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
        return courseApplyMapper.insertCourse(course);
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
        return courseApplyMapper.updateCourse(course);
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
        return courseApplyMapper.deleteCourseByIds(ids);
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
        return courseApplyMapper.deleteCourseById(id);
    }
}
