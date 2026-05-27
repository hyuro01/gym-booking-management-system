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
import com.gymbook.manage.domain.Venue;
import com.gymbook.manage.service.IVenueService;
import com.gymbook.common.utils.poi.ExcelUtil;
import com.gymbook.common.core.page.TableDataInfo;

/**
 * 场馆信息Controller
 * 
 * @author wooyoungnauh
 * @date 2025-02-19
 */
@RestController
@RequestMapping("/manage/venueinfo")
public class VenueController extends BaseController
{
    @Autowired
    private IVenueService venueService;

    /**
     * 查询场馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Venue venue)
    {
        startPage();
        List<Venue> list = venueService.selectVenueList(venue);
        return getDataTable(list);
    }

    /**
     * 导出场馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:export')")
    @Log(title = "场馆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Venue venue)
    {
        List<Venue> list = venueService.selectVenueList(venue);
        ExcelUtil<Venue> util = new ExcelUtil<Venue>(Venue.class);
        util.exportExcel(response, list, "场馆信息数据");
    }

    /**
     * 获取场馆信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(venueService.selectVenueById(id));
    }

    /**
     * 新增场馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:add')")
    @Log(title = "场馆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Venue venue)
    {
        return toAjax(venueService.insertVenue(venue));
    }

    /**
     * 修改场馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:edit')")
    @Log(title = "场馆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Venue venue)
    {
        return toAjax(venueService.updateVenue(venue));
    }

    /**
     * 删除场馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:venueinfo:remove')")
    @Log(title = "场馆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(venueService.deleteVenueByIds(ids));
    }
}
