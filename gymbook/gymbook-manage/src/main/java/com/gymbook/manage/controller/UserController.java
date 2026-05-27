package com.gymbook.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.gymbook.manage.domain.User;
import com.gymbook.manage.service.IUserService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
@RestController
@RequestMapping("/manage/userinfo")
public class UserController extends BaseController
{
    @Autowired
    private IUserService userService;

    /**
     * 查询用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> list = userService.selectUserList(user);
        //List<User> list = userService.getAllCoaches();
        return getDataTable(list);
    }

    /**
     * 查询教练信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:coachlist')")
    @GetMapping("/coachlist")
    public TableDataInfo coachlist(User user)
    {
        startPage();
        List<User> list = userService.getAllCoaches(user);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user)
    {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(userService.selectUserByUserId(userId));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user)
    {
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user)
    {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userinfo:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userService.deleteUserByUserIds(userIds));
    }
}
