package com.gymbook.manage.service;

import com.gymbook.manage.DTO.UserSimilarityDTO;

import java.util.List;

public interface UserSimilarityService {


    /**
     * 新增用户相似度数据
     * @param userSimilarityDTO 用户相似度数据
     * @return true则新增用户相似度成功，false则失败
     */
    boolean saveUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    /**
     * 更新用户相似度数据
     * @param userSimilarityDTO 用户相似度数据
     * @return 受影响到的行数，0表示影响0行，1表示影响1行
     */
    boolean updateUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    /**
     * 判断某两个用户之间的相似度是否已经存在
     * @param userSimilarityDTO 存储两个用户的id
     * @return true表示已经存在，false表示不存在
     */
    boolean isExistsUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    /**
     * 查询某个用户与其他用户之间的相似度列表
     * @param userId 带查询的用户id
     * @return 该用户与其他用户的相似度列表
     */
    List<UserSimilarityDTO> listUserSimilarityByUId(Long userId);

}
