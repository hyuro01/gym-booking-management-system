package com.gymbook.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.common.utils.StringUtils;
import com.gymbook.manage.DTO.CoachDTO;
import com.gymbook.manage.DTO.VenueDTO;
import com.gymbook.manage.vo.CoachNameReportVO;
import com.gymbook.manage.vo.VenueNameReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.VenueMapper;
import com.gymbook.manage.domain.Venue;
import com.gymbook.manage.service.IVenueService;

/**
 * 场馆信息Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-19
 */
@Service
public class VenueServiceImpl implements IVenueService 
{
    @Autowired
    private VenueMapper venueMapper;

    /**
     * 查询场馆信息
     * 
     * @param id 场馆信息主键
     * @return 场馆信息
     */
    @Override
    public Venue selectVenueById(Long id)
    {
        return venueMapper.selectVenueById(id);
    }

    /**
     * 查询场馆信息列表
     * 
     * @param venue 场馆信息
     * @return 场馆信息
     */
    @Override
    public List<Venue> selectVenueList(Venue venue)
    {
        return venueMapper.selectVenueList(venue);
    }

    /**
     * 新增场馆信息
     * 
     * @param venue 场馆信息
     * @return 结果
     */
    @Override
    public int insertVenue(Venue venue)
    {
        venue.setCreateTime(DateUtils.getNowDate());
        return venueMapper.insertVenue(venue);
    }

    /**
     * 修改场馆信息
     * 
     * @param venue 场馆信息
     * @return 结果
     */
    @Override
    public int updateVenue(Venue venue)
    {
        return venueMapper.updateVenue(venue);
    }

    /**
     * 批量删除场馆信息
     * 
     * @param ids 需要删除的场馆信息主键
     * @return 结果
     */
    @Override
    public int deleteVenueByIds(Long[] ids)
    {
        return venueMapper.deleteVenueByIds(ids);
    }

    /**
     * 删除场馆信息信息
     * 
     * @param id 场馆信息主键
     * @return 结果
     */
    @Override
    public int deleteVenueById(Long id)
    {
        return venueMapper.deleteVenueById(id);
    }

    /**
     * 查询最多预约的场馆
     * @return
     */
    @Override
    public Venue getMostVenue() {
        return venueMapper.getMostVenue();
    }

    /**
     * 获取场馆名
     * @return
     */
    @Override
    public VenueNameReportVO getVenueName() {
        List<VenueDTO> venueNameList = venueMapper.getVenueIdName();

        // 获取courseNameList中的课程id和课程名
        List<Integer> ids = venueNameList.stream().map(VenueDTO::getId).collect(Collectors.toList());
        String idList = StringUtils.join(ids, ",");

        List<String> names = venueNameList.stream().map(VenueDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        // 封装返回对象
        return VenueNameReportVO
                .builder()
                .idList(idList)
                .nameList(nameList)
                .build();
    }
}
