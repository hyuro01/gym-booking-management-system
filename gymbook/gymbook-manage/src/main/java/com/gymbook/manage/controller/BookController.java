package com.gymbook.manage.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import com.gymbook.common.utils.SecurityUtils;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.service.ICourseService;
import com.gymbook.manage.service.IUserService;
import com.gymbook.manage.service.IVenueService;
import com.gymbook.manage.util.CollaborativeFilteringTool;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gymbook.common.annotation.Log;
import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.enums.BusinessType;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.service.IBookService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 预约信息Controller
 *
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@RestController
@RequestMapping("/manage/bookinfo")
public class BookController extends BaseController
{
    @Autowired
    private IBookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVenueService venueService;

    /**
     * 查询预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Book book)
    {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 导出预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:export')")
    @Log(title = "预约信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Book book)
    {
        List<Book> list = bookService.selectBookList(book);
        ExcelUtil<Book> util = new ExcelUtil<Book>(Book.class);
        util.exportExcel(response, list, "预约信息数据");
    }


    /**
     * 获取课程名和id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:courseName')")
    @GetMapping("/courseName")
    public AjaxResult getCourseName(){
        return AjaxResult.success(courseService.getCourseName());
    }


    /**
     * 获取教练名和id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:coachName')")
    @GetMapping("/coachName")
    public AjaxResult getCoachName(){
        return AjaxResult.success(userService.getCoachName());
    }

    /**
     * 获取场馆名和id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:venueName')")
    @GetMapping("/venueName")
    public AjaxResult getVenueName(){
        return AjaxResult.success(venueService.getVenueName());
    }

    /**
     * 获取预约信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookService.selectBookById(id));
    }

    /**
     * 新增预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:add')")
    @Log(title = "预约信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book)
    {
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:edit')")
    @Log(title = "预约信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book)
    {
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:bookinfo:remove')")
    @Log(title = "预约信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookService.deleteBookByIds(ids));
    }




    /**
     * 协同过滤推荐课程
     */
//    @GetMapping("/recommend")
//    public AjaxResult recommend() {
//        // 获取所有用户预约课程
//
//        List<Book> books = bookMapper.getUserBooking();
//        // 创建工具类实例
//        CollaborativeFilteringTool cfTool = new CollaborativeFilteringTool();
//
//        for (Book book : books) {
//            cfTool.addUserItem(book.getUserId().intValue(), book.getCourseId().intValue());
//        }
//
//
//
//        // 为用户1生成推荐
//        int targetUserId = SecurityUtils.getUserId().intValue();
//        int topN = 5;
//
//        // 获取相似用户及其相似度
//        Map<Integer, Double> similarUsers = cfTool.getSimilarUsersWithSimilarity(targetUserId, topN);
//        System.out.println("与用户" + targetUserId + "最相似的用户及其相似度:");
//        for (Map.Entry<Integer, Double> entry : similarUsers.entrySet()) {
//            System.out.println("用户 " + entry.getKey() + " 的相似度: " + entry.getValue());
//        }
//
//        // 生成推荐及其推荐分数
//        Map<Integer, Double> recommendations = cfTool.recommendItemsWithScores(targetUserId, topN);
//        // 输出推荐
//        Set<Integer> courseIds = recommendations.keySet();
//        System.out.println("为用户" + targetUserId + "推荐的物品: " + courseIds);
//        System.out.println("为用户" + targetUserId + "推荐的物品及其推荐分数:");
//        List<Course> courses = new ArrayList<>();
//        for (Map.Entry<Integer, Double> entry : recommendations.entrySet()) {
//            System.out.println("物品 " + entry.getKey() + " 的推荐分数: " + entry.getValue());
//            Integer key = entry.getKey();
//            Course course = courseMapper.selectCourseById(key.longValue());
//            if (course != null) {
//                courses.add(course);
//            }
//        }
//
//        // 清空数据
//        cfTool.clearData();
//        System.out.println("数据已清空，当前用户-物品收藏数据: " + cfTool.getUserItemMap());
//
//        return success(courses);
//    }
}
