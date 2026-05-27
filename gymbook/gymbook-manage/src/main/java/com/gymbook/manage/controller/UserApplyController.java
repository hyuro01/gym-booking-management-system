package com.gymbook.manage.controller;

import com.gymbook.common.annotation.Log;
import com.gymbook.common.core.controller.BaseController;
import com.gymbook.common.core.domain.AjaxResult;
import com.gymbook.common.core.page.TableDataInfo;
import com.gymbook.common.enums.BusinessType;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.manage.domain.Book;
import com.gymbook.manage.service.IBookService;
import com.gymbook.manage.service.IUserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户申请Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
@RestController
@RequestMapping("/manage/userapply")
public class UserApplyController extends BaseController
{
    @Autowired
    private IUserApplyService userApplyService;

    /**
     * 查询用户预约申请信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:list')")
    @GetMapping("/list")
    public TableDataInfo list(Book book)
    {
        startPage();
        List<Book> list = userApplyService.selectUserApplyList(book);
        return getDataTable(list);
    }

    /**
     * 导出预约信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:export')")
    @Log(title = "用户申请信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Book book)
    {
        List<Book> list = userApplyService.selectUserApplyList(book);
        ExcelUtil<Book> util = new ExcelUtil<Book>(Book.class);
        util.exportExcel(response, list, "待处理的用户申请数据");
    }

    /**
     * 获取用户申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userApplyService.selectBookById(id));
    }

    /**
     * 新增用户申请信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:add')")
    @Log(title = "用户申请信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book)
    {
        return toAjax(userApplyService.insertBook(book));
    }

    /**
     * 修改预约信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:edit')")
    @Log(title = "用户申请信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book)
    {
        return toAjax(userApplyService.updateBook(book));
    }

    /**
     * 删除用户申请信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userapply:remove')")
    @Log(title = "用户申请信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userApplyService.deleteBookByIds(ids));
    }
}
