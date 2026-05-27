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
public class VenueDTO implements Serializable {
    // 场馆id
    private Integer id;

    // 场馆名
    private String name;

}
