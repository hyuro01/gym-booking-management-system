package com.gymbook.manage.service;

import java.util.List;

import com.gymbook.manage.DTO.CoachDTO;
import com.gymbook.manage.domain.User;
import com.gymbook.manage.vo.CoachNameReportVO;

/**
 * 用户信息Service接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
public interface IUserService 
{
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public User selectUserByUserId(Long userId);

    /**
     * 查询用户信息列表
     * 
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> selectUserList(User user);

    List<User> getAllCoaches(User user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 查询最多预约的教练
     * @return
     */
    User getMostCoach();

    /**
     * 获取教练id和名称
     * @return
     */
    CoachNameReportVO getCoachName();
}
