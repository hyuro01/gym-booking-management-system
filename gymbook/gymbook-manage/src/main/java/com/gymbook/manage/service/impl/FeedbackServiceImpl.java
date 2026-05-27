package com.gymbook.manage.service.impl;

import java.util.List;
import com.gymbook.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.FeedbackMapper;
import com.gymbook.manage.domain.Feedback;
import com.gymbook.manage.service.IFeedbackService;

/**
 * 查看评价Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-22
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService 
{
    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 查询查看评价
     * 
     * @param id 查看评价主键
     * @return 查看评价
     */
    @Override
    public Feedback selectFeedbackById(Long id)
    {
        return feedbackMapper.selectFeedbackById(id);
    }

    /**
     * 查询查看评价列表
     * 
     * @param feedback 查看评价
     * @return 查看评价
     */
    @Override
    public List<Feedback> selectFeedbackList(Feedback feedback)
    {
        return feedbackMapper.selectFeedbackList(feedback);
    }

    /**
     * 新增查看评价
     * 
     * @param feedback 查看评价
     * @return 结果
     */
    @Override
    public int insertFeedback(Feedback feedback)
    {
        feedback.setCreateTime(DateUtils.getNowDate());
        return feedbackMapper.insertFeedback(feedback);
    }

    /**
     * 修改查看评价
     * 
     * @param feedback 查看评价
     * @return 结果
     */
    @Override
    public int updateFeedback(Feedback feedback)
    {
        return feedbackMapper.updateFeedback(feedback);
    }

    /**
     * 批量删除查看评价
     * 
     * @param ids 需要删除的查看评价主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByIds(Long[] ids)
    {
        return feedbackMapper.deleteFeedbackByIds(ids);
    }

    /**
     * 删除查看评价信息
     * 
     * @param id 查看评价主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackById(Long id)
    {
        return feedbackMapper.deleteFeedbackById(id);
    }
}
