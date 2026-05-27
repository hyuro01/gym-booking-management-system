package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachNameReportVO {

    // 教练id，以逗号分隔，例如：1,2,3
    private String idList;

    // 教练名，以逗号分隔，例如：leejaehyunow,tbzuyeon,for_everyoung10
    private String nameList;
}
