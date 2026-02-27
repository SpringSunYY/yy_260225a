package com.lz.manage.mapper;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.po.StatisticsPo;

import java.util.List;

public interface StatisticsMapper {
    /**
     * 预约统计
     *
     * @param request
     * @return
     */
    List<StatisticsPo> appointmentStatistics(StatisticsRequest request);
}
