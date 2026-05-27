package com.gymbook.manage.controller;

import com.gymbook.common.annotation.Log;
import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.core.page.TableDataInfo;
import com.gymbook.common.enums.BusinessType;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.service.ICourseApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 课程信息Controller
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@RestController
@RequestMapping("/manage/courseapply")
public class CourseApplyController extends BaseController
{
    @Autowired
    private ICourseApplyService courseApplyService;

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseApplyService.selectCourseApplyList(course);
        return getDataTable(list);
    }

    /**
     * 导出课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:export')")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course)
    {
        List<Course> list = courseApplyService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程申请数据");
    }

    /**
     * 获取课程信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseApplyService.selectCourseById(id));
    }

    /**
     * 新增课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:add')")
    @Log(title = "课程申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course)
    {
        return toAjax(courseApplyService.insertCourse(course));
    }

    /**
     * 修改课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:edit')")
    @Log(title = "课程申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course)
    {
        return toAjax(courseApplyService.updateCourse(course));
    }

    /**
     * 删除课程信息
     */
    @PreAuthorize("@ss.hasPermi('manage:courseapply:remove')")
    @Log(title = "课程申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(courseApplyService.deleteCourseByIds(ids));
    }
}
