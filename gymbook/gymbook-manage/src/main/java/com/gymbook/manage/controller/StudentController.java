package com.gymbook.manage.controller;

import com.gymbook.common.annotation.Log;
import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.page.TableDataInfo;
import com.gymbook.common.enums.BusinessType;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 学员管理Controller
 *
 * @author wooyoungnauh
 * @date 2025-03-01
 */
@RestController
@RequestMapping("/manage/student")
public class StudentController extends BaseController {
    @Autowired
    private ICourseService courseService;


    /**
     * 查询学员管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseService.selectStudentList(course);
        return getDataTable(list);
    }

    /**
     * 导出学员管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:student:export')")
    @Log(title = "学员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course)
    {
        List<Course> list = courseService.selectStudentList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "学员管理数据");
    }

}
