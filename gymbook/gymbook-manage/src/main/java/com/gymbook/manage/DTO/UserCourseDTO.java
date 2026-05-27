package com.gymbook.manage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 存储用户的健身课程预约行为
 *
 * @author wooyoungnauh
 * @date 2025-03-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseDTO implements Serializable{

    // 用户id
    private Long userId;

    // 课程id
    private Long courseId;

    // 课程预约次数
    private Long count;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }


}
