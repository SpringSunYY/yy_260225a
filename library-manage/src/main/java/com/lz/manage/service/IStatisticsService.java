package com.lz.manage.service;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.vo.StatisticsVo;

import java.util.List;

/**
 * 统计
 *
 * @Project: Library
 * @Author: YY
 * @CreateTime: 2026-02-27  17:19
 * @Version: 1.0
 */
public interface IStatisticsService {
    List<StatisticsVo> appointmentStatistics(StatisticsRequest request);
}
