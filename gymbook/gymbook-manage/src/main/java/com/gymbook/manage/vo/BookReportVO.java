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
public class BookReportVO implements Serializable {

    // 日期，以逗号分隔，例如：2025-01-01,2025-01-02,2025-01-03
    private String dateList;

    // 预约次数，以逗号分隔，例如：1,0,2
    private String bookList;
}
