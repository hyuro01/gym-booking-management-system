package com.gymbook.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookStatusReportVO implements Serializable {

    // 预约状态，逗号分隔，例如：
    private String nameList;

    // 不同状态的数量，以逗号分隔，例如：1,0,2
    private String numberList;
}
