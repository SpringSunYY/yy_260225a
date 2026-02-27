package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.vo.violationInfo.ViolationInfoVo;
import com.lz.manage.service.IStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计
 *
 * @Project: Library
 * @Author: YY
 * @CreateTime: 2026-02-27  17:18
 * @Version: 1.0
 */
@RestController
@RequestMapping("/manage/statistics")
public class StatisticsController extends BaseController {
    @Resource
    private IStatisticsService statisticsService;

    /**
     * 预约信息统计
     */
    @PreAuthorize("@ss.hasPermi('manage:statistics')")
    @GetMapping(value = "/appointment")
    public AjaxResult appointmentStatistics(StatisticsRequest request)
    {
        return success(statisticsService.appointmentStatistics(request));
    }
}
