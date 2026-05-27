package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateReportVO {

    // 个人评分,平均评分
    private String nameList;

    // 评分，以逗号分隔，例如：4.2,3.7
    private String rateList;
}
