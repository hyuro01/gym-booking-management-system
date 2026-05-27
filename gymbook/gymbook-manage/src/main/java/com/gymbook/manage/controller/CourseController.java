package com.gymbook.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.gymbook.manage.service.IVenueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gymbook.common.annotation.Log;
import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.enums.BusinessType;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.service.ICourseService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 课程信息Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
@RestController
@RequestMapping("/manage/courseinfo")
public class CourseController extends BaseController
{
    @Autowired
    private ICourseService courseService;

    @Autowired
    private IVenueService venueService;

    /**
     * 查询课程信息列表
     */
    /**@PreAuthorize("@ss.hasPermi('manage:courseinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        return getDataTable(list);
    }*/

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseService.selectCourseWithVenueList(course);
        return getDataTable(list);
    }

    /**
     * 获取场馆名和id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:venueName')")
    @GetMapping("/venueName")
    public AjaxResult getVenueName(){
        return AjaxResult.success(venueService.getVenueName());
    }

    /**
     * 导出课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:export')")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course)
    {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程信息数据");
    }

    /**
     * 获取课程信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseService.selectCourseById(id));
    }

    /**
     * 新增课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:add')")
    @Log(title = "课程信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course)
    {
        return toAjax(courseService.insertCourse(course));
    }

    /**
     * 修改课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:edit')")
    @Log(title = "课程信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course)
    {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseinfo:remove')")
    @Log(title = "课程信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseService.deleteCourseByIds(ids));
    }
}
