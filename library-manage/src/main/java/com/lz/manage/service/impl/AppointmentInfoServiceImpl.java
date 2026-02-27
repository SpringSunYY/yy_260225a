package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.AppointmentInfoMapper;
import com.lz.manage.model.domain.AppointmentInfo;
import com.lz.manage.service.IAppointmentInfoService;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoQuery;
import com.lz.manage.model.vo.appointmentInfo.AppointmentInfoVo;

/**
 * 预约信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class AppointmentInfoServiceImpl extends ServiceImpl<AppointmentInfoMapper, AppointmentInfo> implements IAppointmentInfoService
{

    @Resource
    private AppointmentInfoMapper appointmentInfoMapper;

    //region mybatis代码
    /**
     * 查询预约信息
     *
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public AppointmentInfo selectAppointmentInfoById(Long id)
    {
        return appointmentInfoMapper.selectAppointmentInfoById(id);
    }

    /**
     * 查询预约信息列表
     *
     * @param appointmentInfo 预约信息
     * @return 预约信息
     */
    @Override
    public List<AppointmentInfo> selectAppointmentInfoList(AppointmentInfo appointmentInfo)
    {
        return appointmentInfoMapper.selectAppointmentInfoList(appointmentInfo);
    }

    /**
     * 新增预约信息
     *
     * @param appointmentInfo 预约信息
     * @return 结果
     */
    @Override
    public int insertAppointmentInfo(AppointmentInfo appointmentInfo)
    {
        appointmentInfo.setCreateTime(DateUtils.getNowDate());
        return appointmentInfoMapper.insertAppointmentInfo(appointmentInfo);
    }

    /**
     * 修改预约信息
     *
     * @param appointmentInfo 预约信息
     * @return 结果
     */
    @Override
    public int updateAppointmentInfo(AppointmentInfo appointmentInfo)
    {
        appointmentInfo.setUpdateTime(DateUtils.getNowDate());
        return appointmentInfoMapper.updateAppointmentInfo(appointmentInfo);
    }

    /**
     * 批量删除预约信息
     *
     * @param ids 需要删除的预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentInfoByIds(Long[] ids)
    {
        return appointmentInfoMapper.deleteAppointmentInfoByIds(ids);
    }

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentInfoById(Long id)
    {
        return appointmentInfoMapper.deleteAppointmentInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<AppointmentInfo> getQueryWrapper(AppointmentInfoQuery appointmentInfoQuery){
        QueryWrapper<AppointmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = appointmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = appointmentInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long libraryId = appointmentInfoQuery.getLibraryId();
        queryWrapper.eq( StringUtils.isNotNull(libraryId),"library_id",libraryId);

        Long seatId = appointmentInfoQuery.getSeatId();
        queryWrapper.eq( StringUtils.isNotNull(seatId),"seat_id",seatId);

        String name = appointmentInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = appointmentInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Date createTime = appointmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<AppointmentInfoVo> convertVoList(List<AppointmentInfo> appointmentInfoList) {
        if (StringUtils.isEmpty(appointmentInfoList)) {
            return Collections.emptyList();
        }
        return appointmentInfoList.stream().map(AppointmentInfoVo::objToVo).collect(Collectors.toList());
    }

}
