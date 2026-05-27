package com.gymbook.manage.mapper;

import java.util.List;

import com.gymbook.manage.DTO.CoachDTO;
import com.gymbook.manage.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息Mapper接口
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
@Mapper
public interface UserMapper 
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

    /**
     * 查询用户信息，包括用户的身份（管理员，客户，教练）
     * @param user
     * @return
     */
    public List<User> selectUserWithRoleList(User user);

    /** 查询角色为教练的用户
     * @param user
     * @return
     */
    public List<User> selectAllCoaches(User user);

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
     * 删除用户信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 查询最多预约的教练
     * @return
     */
    User getMostCoach();

    /**
     * 查询教练id和名字
     * @return
     */
    List<CoachDTO> getCoachName();
}
