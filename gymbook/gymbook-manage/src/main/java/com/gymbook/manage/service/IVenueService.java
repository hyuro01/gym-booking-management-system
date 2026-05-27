package com.gymbook.manage.service;

import java.util.List;
import com.gymbook.manage.domain.Venue;
import com.gymbook.manage.vo.VenueNameReportVO;

/**
 * 场馆信息Service接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-19
 */
public interface IVenueService 
{
    /**
     * 查询场馆信息
     * 
     * @param id 场馆信息主键
     * @return 场馆信息
     */
    public Venue selectVenueById(Long id);

    /**
     * 查询场馆信息列表
     * 
     * @param venue 场馆信息
     * @return 场馆信息集合
     */
    public List<Venue> selectVenueList(Venue venue);

    /**
     * 新增场馆信息
     * 
     * @param venue 场馆信息
     * @return 结果
     */
    public int insertVenue(Venue venue);

    /**
     * 修改场馆信息
     * 
     * @param venue 场馆信息
     * @return 结果
     */
    public int updateVenue(Venue venue);

    /**
     * 批量删除场馆信息
     * 
     * @param ids 需要删除的场馆信息主键集合
     * @return 结果
     */
    public int deleteVenueByIds(Long[] ids);

    /**
     * 删除场馆信息信息
     * 
     * @param id 场馆信息主键
     * @return 结果
     */
    public int deleteVenueById(Long id);

    /**
     * 查询最多预约的场馆
     * @return
     */
    Venue getMostVenue();

    /**
     * 获取场馆名
     * @return
     */
    VenueNameReportVO getVenueName();
}
