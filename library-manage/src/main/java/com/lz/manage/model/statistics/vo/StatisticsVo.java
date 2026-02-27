package com.lz.manage.model.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 饼图统计VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVo {
    private String name;
    private Long value;
}
