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
public class CoachDTO implements Serializable {
    // 教练id
    private Integer id;

    // 教练名
    private String name;

}
