package com.gymbook.manage.mapper;

import java.util.List;
import com.gymbook.manage.domain.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * 查看评价Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@Mapper
public interface FeedbackMapper 
{
    /**
     * 查询查看评价
     * 
     * @param id 查看评价主键
     * @return 查看评价
     */
    public Feedback selectFeedbackById(Long id);

    /**
     * 查询查看评价列表
     * 
     * @param feedback 查看评价
     * @return 查看评价集合
     */
    public List<Feedback> selectFeedbackList(Feedback feedback);

    /**
     * 新增查看评价
     * 
     * @param feedback 查看评价
     * @return 结果
     */
    public int insertFeedback(Feedback feedback);

    /**
     * 修改查看评价
     * 
     * @param feedback 查看评价
     * @return 结果
     */
    public int updateFeedback(Feedback feedback);

    /**
     * 删除查看评价
     * 
     * @param id 查看评价主键
     * @return 结果
     */
    public int deleteFeedbackById(Long id);

    /**
     * 批量删除查看评价
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFeedbackByIds(Long[] ids);


    /**
     * 根据教练id计算评分
     * @param coachId
     * @return
     */
    Double getCoachRate(Long coachId);

    /**
     * 获取所有教练的平均评分
     * @return
     */
    Double getAvgRate();
}
