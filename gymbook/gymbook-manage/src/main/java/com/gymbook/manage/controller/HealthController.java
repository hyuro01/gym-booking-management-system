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
import com.gymbook.manage.domain.Health;
import com.gymbook.manage.service.IHealthService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 健康管理Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
@RestController
@RequestMapping("/manage/health")
public class HealthController extends BaseController
{
    @Autowired
    private IHealthService healthService;

    /**
     * 查询健康管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:health:list')")
    @GetMapping("/list")
    public TableDataInfo list(Health health)
    {
        startPage();
        List<Health> list = healthService.selectHealthList(health);
        return getDataTable(list);
    }

    /**
     * 导出健康管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:health:export')")
    @Log(title = "健康管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Health health)
    {
        List<Health> list = healthService.selectHealthList(health);
        ExcelUtil<Health> util = new ExcelUtil<Health>(Health.class);
        util.exportExcel(response, list, "健康管理数据");
    }

    /**
     * 获取健康管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:health:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long Id)
    {
        return success(healthService.selectHealthById(Id));
    }

    /**
     * 新增健康管理
     */
    @PreAuthorize("@ss.hasPermi('manage:health:add')")
    @Log(title = "健康管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Health health)
    {
        return toAjax(healthService.insertHealth(health));
    }

    /**
     * 修改健康管理
     */
    @PreAuthorize("@ss.hasPermi('manage:health:edit')")
    @Log(title = "健康管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Health health)
    {
        return toAjax(healthService.updateHealth(health));
    }

    /**
     * 删除健康管理
     */
    @PreAuthorize("@ss.hasPermi('manage:health:remove')")
    @Log(title = "健康管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(healthService.deleteHealthByIds(ids));
    }
}
