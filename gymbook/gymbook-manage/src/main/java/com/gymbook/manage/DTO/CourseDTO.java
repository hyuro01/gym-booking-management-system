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
public class CourseDTO implements Serializable {
    // 课程id
    private Integer id;

    // 课程名
    private String name;

}
