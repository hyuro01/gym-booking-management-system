package com.gymbook.manage.controller;

import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.utils.SecurityUtils;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.domain.User;
import com.gymbook.manage.domain.Venue;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.service.*;
import com.gymbook.manage.util.CollaborativeFilteringTool;
import com.gymbook.system.domain.SysNotice;
import com.gymbook.system.service.ISysNoticeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 首页Controller
 *
 * @author wooyoungnauh
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/index")
@Api(tags = "首页接口")
@Slf4j
public class HomePageController extends BaseController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private IVenueService venueService;


    /**
     * 查询最新公告
     */
    @GetMapping("/late")
    public AjaxResult lateNotice() {
        SysNotice lateNotice = noticeService.getLatestNotice();
        return AjaxResult.success(lateNotice);
    }

    /**
     * 查询最多预约的课程
     */
    @GetMapping("/mostCourse")
    public AjaxResult mostCourse() {
        Course mostCourse = courseService.getMostCourse();
        return AjaxResult.success(mostCourse);
    }

    /**
     * 查询最多预约的教练
     */
    @GetMapping("/mostCoach")
    public AjaxResult mostCoach() {
        User mostCoach = userService.getMostCoach();
        return AjaxResult.success(mostCoach);
    }

    /**
     * 查询最多预约的场馆
     */
    @GetMapping("/mostVenue")
    public AjaxResult mostVenue() {
        Venue mostVenue = venueService.getMostVenue();
        return AjaxResult.success(mostVenue);
    }
}
