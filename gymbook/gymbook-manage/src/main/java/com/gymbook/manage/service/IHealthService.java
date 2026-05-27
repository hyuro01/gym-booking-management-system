package com.gymbook.manage.service;

import java.util.List;
import com.gymbook.manage.domain.Health;

/**
 * 健康管理Service接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
public interface IHealthService 
{
    /**
     * 查询健康管理
     * 
     * @param id 健康管理主键
     * @return 健康管理
     */
    public Health selectHealthById(Long id);

    /**
     * 查询健康管理列表
     * 
     * @param health 健康管理
     * @return 健康管理集合
     */
    public List<Health> selectHealthList(Health health);

    /**
     * 新增健康管理
     * 
     * @param health 健康管理
     * @return 结果
     */
    public int insertHealth(Health health);

    /**
     * 修改健康管理
     * 
     * @param health 健康管理
     * @return 结果
     */
    public int updateHealth(Health health);

    /**
     * 批量删除健康管理
     * 
     * @param ids 需要删除的健康管理主键集合
     * @return 结果
     */
    public int deleteHealthByIds(Long[] ids);

    /**
     * 删除健康管理信息
     * 
     * @param id 健康管理主键
     * @return 结果
     */
    public int deleteHealthById(Long id);
}
