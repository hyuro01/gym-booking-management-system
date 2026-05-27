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
public class CourseCountDTO implements Serializable {
    // 课程名称
    private String name;

    // 预约量
    private Integer number;

}
