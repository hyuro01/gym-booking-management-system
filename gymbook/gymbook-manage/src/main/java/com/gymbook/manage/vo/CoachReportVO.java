package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachReportVO {

    // 教练名，以逗号分隔，例如：张三,李四
    private String nameList;

    // 预约数量，以逗号分隔，例如：10,4,7
    private String numberList;
}
