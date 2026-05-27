package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseReportVO {

    // 课程名，以逗号分隔，例如：上肢肌肉训练,test,动感单车训练
    private String nameList;

    // 课程报名数量，以逗号分隔，例如：10,4,7
    private String numberList;
}
