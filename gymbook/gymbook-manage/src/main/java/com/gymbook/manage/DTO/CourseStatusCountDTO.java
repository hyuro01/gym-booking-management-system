package com.gymbook.manage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseStatusCountDTO implements Serializable {
    // 预约状态代码
    private Integer status;

    // 数量
    private Integer number;
}
