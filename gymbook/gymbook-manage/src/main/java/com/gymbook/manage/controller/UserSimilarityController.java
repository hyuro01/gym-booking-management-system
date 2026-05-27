package com.gymbook.manage.controller;

import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.utils.SecurityUtils;
import com.gymbook.manage.DTO.UserCourseDTO;
import com.gymbook.manage.DTO.UserSimilarityDTO;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.domain.Course;
import com.gymbook.manage.mapper.BookMapper;
import com.gymbook.manage.mapper.CourseMapper;
import com.gymbook.manage.service.IBookService;
import com.gymbook.manage.service.ICourseService;
import com.gymbook.manage.service.UserSimilarityService;
import com.gymbook.manage.service.UserStatService;
import com.gymbook.manage.util.CollaborativeFilteringTool;
import com.gymbook.manage.util.UserSimilarityUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 个性化健身课程推荐Controller
 *
 * @author wooyoungnauh
 * @date 2025-03-14
 */
@RestController
@RequestMapping("/manage/personalization")
@Api(tags = "个性化健身课程推荐接口")
@Slf4j
public class UserSimilarityController extends BaseController {

    @Autowired
    private UserSimilarityService userSimilarityService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CourseMapper courseMapper;


    /**
     * 协同过滤推荐课程
     */
    @PreAuthorize("@ss.hasPermi('manage:personalization:list')")
    @GetMapping("/list")
    public AjaxResult recommend() {
        // 获取所有用户预约课程
        List<Book> books = bookMapper.getUserBooking();
        //System.out.println("books = " + books);

        // 创建工具类实例
        CollaborativeFilteringTool cfTool = new CollaborativeFilteringTool();

        for (Book book : books) {
            cfTool.addUserItem(book.getUserId().intValue(), book.getCourseId().intValue());
        }


        // 为用户1生成推荐
        int targetUserId = SecurityUtils.getUserId().intValue();
        int topN = 5;

        // 获取相似用户及其相似度
        Map<Integer, Double> similarUsers = cfTool.getSimilarUsersWithSimilarity(targetUserId, topN);
        System.out.println("与用户" + targetUserId + "最相似的用户及其相似度:");
        for (Map.Entry<Integer, Double> entry : similarUsers.entrySet()) {
            System.out.println("用户 " + entry.getKey() + " 的相似度: " + entry.getValue());
        }

        // 生成推荐及其推荐分数
        Map<Integer, Double> recommendations = cfTool.recommendItemsWithScores(targetUserId, topN);
        // 输出推荐
        Set<Integer> courseIds = recommendations.keySet();
        System.out.println("为用户" + targetUserId + "推荐的物品: " + courseIds);
        System.out.println("为用户" + targetUserId + "推荐的物品及其推荐分数:");
        List<Course> courses = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : recommendations.entrySet()) {
            System.out.println("物品 " + entry.getKey() + " 的推荐分数: " + entry.getValue());
            Integer key = entry.getKey();
            Course course = courseMapper.selectCourseById(key.longValue());
            if (course != null) {
                courses.add(course);
            }
        }

        // 清空数据
        cfTool.clearData();
        System.out.println("数据已清空，当前用户-物品收藏数据: " + cfTool.getUserItemMap());

        return AjaxResult.success(courses);
    }

}
