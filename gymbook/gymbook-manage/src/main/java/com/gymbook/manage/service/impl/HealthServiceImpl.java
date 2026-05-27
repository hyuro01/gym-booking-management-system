package com.gymbook.manage.service.impl;

import java.util.List;
import com.gymbook.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.HealthMapper;
import com.gymbook.manage.domain.Health;
import com.gymbook.manage.service.IHealthService;

/**
 * 健康管理Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
@Service
public class HealthServiceImpl implements IHealthService 
{
    @Autowired
    private HealthMapper healthMapper;

    /**
     * 查询健康管理
     * 
     * @param id 健康管理主键
     * @return 健康管理
     */
    @Override
    public Health selectHealthById(Long id)
    {
        return healthMapper.selectHealthById(id);
    }

    /**
     * 查询健康管理列表
     * 
     * @param health 健康管理
     * @return 健康管理
     */
    @Override
    public List<Health> selectHealthList(Health health)
    {
        return healthMapper.selectHealthList(health);
    }

    /**
     * 新增健康管理
     * 
     * @param health 健康管理
     * @return 结果
     */
    @Override
    public int insertHealth(Health health)
    {
        health.setCreateTime(DateUtils.getNowDate());
        return healthMapper.insertHealth(health);
    }

    /**
     * 修改健康管理
     * 
     * @param health 健康管理
     * @return 结果
     */
    @Override
    public int updateHealth(Health health)
    {
        return healthMapper.updateHealth(health);
    }

    /**
     * 批量删除健康管理
     * 
     * @param ids 需要删除的健康管理主键
     * @return 结果
     */
    @Override
    public int deleteHealthByIds(Long[] ids)
    {
        return healthMapper.deleteHealthByIds(ids);
    }

    /**
     * 删除健康管理信息
     * 
     * @param id 健康管理主键
     * @return 结果
     */
    @Override
    public int deleteHealthById(Long id)
    {
        return healthMapper.deleteHealthById(id);
    }
}
