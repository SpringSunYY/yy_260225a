package com.lz.manage.service.impl;

import com.lz.common.utils.DateUtils;
import com.lz.manage.mapper.StatisticsMapper;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.po.StatisticsPo;
import com.lz.manage.model.statistics.vo.StatisticsVo;
import com.lz.manage.service.IStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计
 *
 * @Project: Library
 * @Author: YY
 * @CreateTime: 2026-02-27  17:20
 * @Version: 1.0
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public List<StatisticsVo> appointmentStatistics(StatisticsRequest request) {
        Date endDate = DateUtils.getNowDate();
        Date startDate = DateUtils.getDay(endDate, 14);
        String startTime = DateUtils.dateTime(startDate);
        request.setStartTime(startTime + " 00:00:00");
        String endTime = DateUtils.dateTime(endDate);
        System.out.println("endTime = " + endTime);
        System.out.println("startTime = " + startTime);
        request.setEndTime(endTime + " 23:59:59");
        List<String> dateRanges = DateUtils.getDateRanges(startTime, endTime);
        HashMap<String, Long> resultMap = new HashMap<>();
        System.out.println("request = " + request);
        if (dateRanges != null) {
            for (String dateRange : dateRanges) {
                resultMap.put(dateRange, 0L);
            }
        }

        // 查询
        List<StatisticsPo> statisticsPos = statisticsMapper.appointmentStatistics(request);
        for (StatisticsPo statisticsPo : statisticsPos) {
            resultMap.put(statisticsPo.getName(), statisticsPo.getValue());
        }
        List<StatisticsVo> list = new ArrayList<>();
        resultMap.forEach((key, value) -> {
            StatisticsVo statisticsVo = new StatisticsVo();
            statisticsVo.setName(key);
            statisticsVo.setValue(value);
            list.add(statisticsVo);
        });
        return list.stream().sorted(Comparator.comparing(StatisticsVo::getName)).collect(Collectors.toList());
    }
}
