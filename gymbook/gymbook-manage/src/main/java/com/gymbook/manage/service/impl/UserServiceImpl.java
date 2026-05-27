package com.gymbook.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.gymbook.common.utils.DateUtils;
import com.gymbook.common.utils.StringUtils;
import com.gymbook.manage.DTO.CoachDTO;
import com.gymbook.manage.DTO.CourseDTO;
import com.gymbook.manage.vo.CoachNameReportVO;
import com.gymbook.manage.vo.CourseNameReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gymbook.manage.mapper.UserMapper;
import com.gymbook.manage.domain.User;
import com.gymbook.manage.service.IUserService;

/**
 * 用户信息Service业务层处理
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
@Service
public class UserServiceImpl implements IUserService 
{
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public User selectUserByUserId(Long userId)
    {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        List<User> users = userMapper.selectUserWithRoleList(user);
        // 打印返回的用户数据，检查字段是否完整
        users.forEach(u -> System.out.println(u));
        return users;
        //return userMapper.selectUserWithRoleList(user);
        //return userMapper.selectUserList(user);
    }

    @Override
    public List<User> getAllCoaches(User user) {
        return userMapper.selectAllCoaches(user);
    }

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        user.setCreateTime(DateUtils.getNowDate());
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        user.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserIds(Long[] userIds)
    {
        return userMapper.deleteUserByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserId(Long userId)
    {
        return userMapper.deleteUserByUserId(userId);
    }

    /**
     * 查询最多预约的教练
     * @return
     */
    @Override
    public User getMostCoach() {
        return userMapper.getMostCoach();
    }

    /**
     * 获取教练id和名字
     * @return
     */
    @Override
    public CoachNameReportVO getCoachName() {
        List<CoachDTO> coachNameList = userMapper.getCoachName();

        // 获取courseNameList中的课程id和课程名
        List<Integer> ids = coachNameList.stream().map(CoachDTO::getId).collect(Collectors.toList());
        String idList = StringUtils.join(ids, ",");

        List<String> names = coachNameList.stream().map(CoachDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");

        // 封装返回对象
        return CoachNameReportVO
                .builder()
                .idList(idList)
                .nameList(nameList)
                .build();
    }
}
