package com.gymbook.manage.mapper;

import java.util.List;
import java.util.Map;

import com.gymbook.manage.DTO.VenueDTO;
import com.gymbook.manage.domain.Venue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 场馆信息Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-19
 */
@Mapper
public interface VenueMapper 
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
     * 删除场馆信息
     * 
     * @param id 场馆信息主键
     * @return 结果
     */
    public int deleteVenueById(Long id);

    /**
     * 批量删除场馆信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVenueByIds(Long[] ids);

    /**
     * 获取场馆名称
     * @return
     */
    @Select("SELECT name FROM venue")
    List<String> getVenueName();

    /**
     * 根据时间查询各个场馆当天预约量
     * @param map
     * @return
     */
    int getVenueCount(Map map);

    /**
     * 查询最多预约的场馆
     * @return
     */
    Venue getMostVenue();

    List<VenueDTO> getVenueIdName();
}
