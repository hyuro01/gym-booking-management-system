package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseNameReportVO {

    // 课程id，以逗号分隔，例如：1,2,3
    private String idList;

    // 课程名，以逗号分隔，例如：上肢肌肉训练,test,动感单车训练
    private String nameList;
}
