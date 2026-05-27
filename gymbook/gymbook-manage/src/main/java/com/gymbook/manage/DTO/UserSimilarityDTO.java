package com.gymbook.manage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 存储用户之间的相似度
 *
 * @author wooyoungnauh
 * @date 2025-03-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSimilarityDTO implements Serializable,Comparable<UserSimilarityDTO> {

    // 用户id
    private Long userId;

    // 做比较的其他用户id
    private Long userRefId;

    // 相似度
    private Double similarity;


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserRefId() {
        return userRefId;
    }
    public void setUserRefId(Long userRefId) {
        this.userRefId = userRefId;
    }

    public Double getSimilarity() {
        return similarity;
    }
    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    @Override
    public int compareTo(UserSimilarityDTO o) {
        // 对用户之间的相似度降序排序
        return Double.compare(o.getSimilarity(), this.getSimilarity());
    }
}
