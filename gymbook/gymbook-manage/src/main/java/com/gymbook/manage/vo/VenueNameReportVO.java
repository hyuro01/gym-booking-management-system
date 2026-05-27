package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueNameReportVO {

    // 场馆id，以逗号分隔，例如：1,2,3
    private String idList;

    // 场馆名，以逗号分隔，例如：1号馆,2号馆,3号馆
    private String nameList;
}
