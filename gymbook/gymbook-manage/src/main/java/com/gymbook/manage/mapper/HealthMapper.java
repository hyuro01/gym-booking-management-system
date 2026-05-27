package com.gymbook.manage.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.gymbook.manage.domain.Health;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 健康管理Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-23
 */
@Mapper
public interface HealthMapper 
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
     * 删除健康管理
     * 
     * @param id 健康管理主键
     * @return 结果
     */
    public int deleteHealthById(Long id);

    /**
     * 批量删除健康管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHealthByIds(Long[] ids);

    /**
     * 根据动态条件统计体重数据
     * @param map
     * @return
     */
    Double weightByMap(Map map);

    /**
     * 根据动态条件统计bmi数据
     * @param map
     * @return
     */
    Double bmiByMap(Map map);

    /**
     * 健身目标统计
     * @param userId
     * @param end
     * @return
     */
    @Select("SELECT * FROM health WHERE user_id = #{userId} ORDER BY create_time DESC LIMIT 1")
    Health selectGoalByTime(@Param("userId") Long userId, @Param("end") LocalDate end);
}
