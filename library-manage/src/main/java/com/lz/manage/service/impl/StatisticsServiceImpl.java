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
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        // 如果前端没有传入参数，默认最近三个月
        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            Date endDate = DateUtils.getNowDate();
            Date startDate = DateUtils.getDay(endDate, -89);
            int startYear = Integer.parseInt(DateUtils.parseDateToStr("yyyy", startDate));
            int startMonth = Integer.parseInt(DateUtils.parseDateToStr("MM", startDate));
            int endYear = Integer.parseInt(DateUtils.parseDateToStr("yyyy", endDate));
            int endMonth = Integer.parseInt(DateUtils.parseDateToStr("MM", endDate));
            startTime = String.format("%04d%02d", startYear, startMonth);
            endTime = String.format("%04d%02d", endYear, endMonth);
        }

        // 解析 yyyyMM -> 该月第一天 00:00:00 / 最后一天 23:59:59
        String sqlStartTime = DateUtils.getMonthFirstDay(startTime);
        String sqlEndTime = DateUtils.getMonthLastDay(endTime);

        request.setStartTime(sqlStartTime);
        request.setEndTime(sqlEndTime);

        // 填充月份范围（保证无数据的月份也有 0 值）
        List<String> monthRanges = DateUtils.getMonthRanges(startTime, endTime);
        HashMap<String, Long> resultMap = new HashMap<>();
        if (monthRanges != null) {
            for (String month : monthRanges) {
                resultMap.put(month, 0L);
            }
        }

        // 查询数据库
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
