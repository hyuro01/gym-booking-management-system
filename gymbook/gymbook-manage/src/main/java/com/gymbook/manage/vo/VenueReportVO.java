package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueReportVO implements Serializable {
    // 日期，以逗号分隔，例如：2025-01-01,2025-01-02,2025-01-03
    private String dateList;

    // 场馆名称，以逗号分隔，例如：场馆1,场馆2,场馆3
    private String nameList;

    // 预约数量，以逗号分隔，例如：4,1,7
    private List<List<Integer>> numberList;
}
