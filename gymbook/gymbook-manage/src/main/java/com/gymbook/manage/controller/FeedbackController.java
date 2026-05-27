package com.gymbook.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.gymbook.common.utils.SecurityUtils;
import com.gymbook.manage.service.IBookService;
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
import com.gymbook.manage.domain.Feedback;
import com.gymbook.manage.service.IFeedbackService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 查看评价Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@RestController
@RequestMapping("/manage/feedbackinfo")
public class FeedbackController extends BaseController
{
    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IBookService bookService;

    /**
     * 查询查看评价列表
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Feedback feedback)
    {
        startPage();
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        return getDataTable(list);
    }


    /**
     * 获取未评价的预约
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:endbook')")
    @GetMapping("/endbook")
    public AjaxResult getEndBook(){
        // 获取当前账号的id
        int userId = SecurityUtils.getUserId().intValue();
        // 获取预约信息
        return AjaxResult.success(bookService.getEndBook(userId));
    }


    /**
     * 导出查看评价列表
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:export')")
    @Log(title = "查看评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Feedback feedback)
    {
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback.class);
        util.exportExcel(response, list, "查看评价数据");
    }

    /**
     * 获取查看评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(feedbackService.selectFeedbackById(id));
    }

    /**
     * 新增查看评价
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:add')")
    @Log(title = "查看评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.insertFeedback(feedback));
    }

    /**
     * 修改查看评价
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:edit')")
    @Log(title = "查看评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.updateFeedback(feedback));
    }

    /**
     * 删除查看评价
     */
    @PreAuthorize("@ss.hasPermi('manage:feedbackinfo:remove')")
    @Log(title = "查看评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(feedbackService.deleteFeedbackByIds(ids));
    }
}
