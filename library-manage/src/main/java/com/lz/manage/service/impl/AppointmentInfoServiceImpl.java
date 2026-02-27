package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.AppointmentInfoMapper;
import com.lz.manage.model.domain.AppointmentInfo;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.domain.SeatInfo;
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoQuery;
import com.lz.manage.model.enums.ManageAppointmentStatusEnum;
import com.lz.manage.model.enums.ManageLibraryStatusEnum;
import com.lz.manage.model.enums.ManageSeatStatusEnum;
import com.lz.manage.model.enums.ManageViolationStatusEnum;
import com.lz.manage.model.vo.appointmentInfo.AppointmentInfoVo;
import com.lz.manage.service.IAppointmentInfoService;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.service.ISeatInfoService;
import com.lz.manage.service.IViolationInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预约信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class AppointmentInfoServiceImpl extends ServiceImpl<AppointmentInfoMapper, AppointmentInfo> implements IAppointmentInfoService {

    @Resource
    private AppointmentInfoMapper appointmentInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ILibraryInfoService libraryInfoService;

    @Resource
    private ISeatInfoService seatInfoService;

    @Resource
    private IViolationInfoService violationInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    //region mybatis代码

    /**
     * 查询预约信息
     *
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public AppointmentInfo selectAppointmentInfoById(Long id) {
        return appointmentInfoMapper.selectAppointmentInfoById(id);
    }

    /**
     * 查询预约信息列表
     *
     * @param appointmentInfo 预约信息
     * @return 预约信息
     */
    @Override
    public List<AppointmentInfo> selectAppointmentInfoList(AppointmentInfo appointmentInfo) {
        List<AppointmentInfo> appointmentInfos = appointmentInfoMapper.selectAppointmentInfoList(appointmentInfo);
        for (AppointmentInfo info : appointmentInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(info.getLibraryId());
            if (StringUtils.isNotNull(libraryInfo)) {
                info.setLibraryName(libraryInfo.getName());
            }
            SeatInfo seatInfo = seatInfoService.selectSeatInfoById(info.getSeatId());
            if (StringUtils.isNotNull(seatInfo)) {
                info.setSeatName(seatInfo.getName());
            }
        }
        return appointmentInfos;
    }

    /**
     * 新增预约信息
     *
     * @param appointmentInfo 预约信息
     * @return 结果
     */
    @Override
    public int insertAppointmentInfo(AppointmentInfo appointmentInfo) {
        //首先校验数据
        //判断时间段是否冲突
        //开始时间不能晚于结束时间
        ThrowUtils.throwIf(appointmentInfo.getStartTime().after(appointmentInfo.getEndTime()), "开始时间不能晚于结束时间");
        //开始时间不能早于当前时间
        ThrowUtils.throwIf(appointmentInfo.getStartTime().before(new Date()), "开始时间不能早于当前时间");
        //开始时间和结束时间不能相同
        ThrowUtils.throwIf(appointmentInfo.getStartTime().equals(appointmentInfo.getEndTime()), "开始时间和结束时间不能相同");
        //判断图书馆是否存在
        LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(appointmentInfo.getLibraryId());
        ThrowUtils.throwIf(
                StringUtils.isNull(libraryInfo)
                        || !libraryInfo.getStatus().equals(ManageLibraryStatusEnum.MANAGE_LIBRARY_STATUS_1.getValue()),
                "图书馆不存在或者已经闭馆");

        //判断座位是否存在
        SeatInfo seatInfo = seatInfoService.selectSeatInfoById(appointmentInfo.getSeatId());
        ThrowUtils.throwIf(
                StringUtils.isNull(seatInfo)
                        || !seatInfo.getStatus().equals(ManageSeatStatusEnum.MANAGE_SEAT_STATUS_1.getValue()),
                "座位不存在或者不可预约"
        );
        //判断是否有违规
        List<ViolationInfo> list = violationInfoService.list(new LambdaQueryWrapper<ViolationInfo>()
                .eq(ViolationInfo::getUserId, SecurityUtils.getUserId())
                .eq(ViolationInfo::getLibraryId, appointmentInfo.getLibraryId())
                .eq(ViolationInfo::getStatus, ManageViolationStatusEnum.MANAGE_VIOLATION_STATUS_1.getValue()));
        ThrowUtils.throwIf(!list.isEmpty(), "您有违规，不可预约此图书馆");


        // 冲突条件：新预约的开始时间 < 已有预约的结束时间 且 新预约的结束时间 > 已有预约的开始时间
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppointmentInfo::getLibraryId, appointmentInfo.getLibraryId());
        queryWrapper.eq(AppointmentInfo::getSeatId, appointmentInfo.getSeatId());
        queryWrapper.lt(AppointmentInfo::getStartTime, appointmentInfo.getEndTime())
                .gt(AppointmentInfo::getEndTime, appointmentInfo.getStartTime());
        long count = this.count(queryWrapper);
        ThrowUtils.throwIf(count > 0, "时间段冲突，不可预约");

        appointmentInfo.setStatus(ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_1.getValue());
        appointmentInfo.setUserId(SecurityUtils.getUserId());
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
    public int updateAppointmentInfo(AppointmentInfo appointmentInfo) {
        appointmentInfo.setUpdateTime(DateUtils.getNowDate());
        appointmentInfo.setUpdateBy(SecurityUtils.getUsername());
        return appointmentInfoMapper.updateAppointmentInfo(appointmentInfo);
    }

    /**
     * 批量删除预约信息
     *
     * @param ids 需要删除的预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentInfoByIds(Long[] ids) {
        return appointmentInfoMapper.deleteAppointmentInfoByIds(ids);
    }

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentInfoById(Long id) {
        return appointmentInfoMapper.deleteAppointmentInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<AppointmentInfo> getQueryWrapper(AppointmentInfoQuery appointmentInfoQuery) {
        QueryWrapper<AppointmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = appointmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = appointmentInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long libraryId = appointmentInfoQuery.getLibraryId();
        queryWrapper.eq(StringUtils.isNotNull(libraryId), "library_id", libraryId);

        Long seatId = appointmentInfoQuery.getSeatId();
        queryWrapper.eq(StringUtils.isNotNull(seatId), "seat_id", seatId);

        String name = appointmentInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = appointmentInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Date createTime = appointmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<AppointmentInfoVo> convertVoList(List<AppointmentInfo> appointmentInfoList) {
        if (StringUtils.isEmpty(appointmentInfoList)) {
            return Collections.emptyList();
        }
        return appointmentInfoList.stream().map(AppointmentInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public void autoUpdateAppointmentInfo() {
        //更新预约信息，首先查询到大约开始时间，小于结束时间的预约信息
        LambdaQueryWrapper<AppointmentInfo> queryWrapper = new LambdaQueryWrapper<>();
        Date nowDate = DateUtils.getNowDate();
        queryWrapper.lt(AppointmentInfo::getStartTime, nowDate);
        queryWrapper.gt(AppointmentInfo::getEndTime, nowDate);
        queryWrapper.eq(AppointmentInfo::getStatus, ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_1.getValue());
        List<AppointmentInfo> appointmentInfoList = this.list(queryWrapper);
        ArrayList<SeatInfo> progressSeatList = new ArrayList<>();
        for (AppointmentInfo appointmentInfo : appointmentInfoList) {
            appointmentInfo.setStatus(ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_2.getValue());
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setId(appointmentInfo.getSeatId());
            seatInfo.setStatus(ManageSeatStatusEnum.MANAGE_SEAT_STATUS_2.getValue());
            progressSeatList.add(seatInfo);
        }

        //查询进行中但是结束时间小于当前时间的
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(AppointmentInfo::getEndTime, nowDate);
        queryWrapper.eq(AppointmentInfo::getStatus, ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_2.getValue());
        List<AppointmentInfo> progressAppointmentInfoList = this.list(queryWrapper);
        List<SeatInfo> endSeatList = new ArrayList<>();
        for (AppointmentInfo appointmentInfo : progressAppointmentInfoList) {
            appointmentInfo.setStatus(ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_3.getValue());
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setId(appointmentInfo.getSeatId());
            seatInfo.setStatus(ManageSeatStatusEnum.MANAGE_SEAT_STATUS_1.getValue());
            endSeatList.add(seatInfo);
        }

        //批量更新
        transactionTemplate.executeWithoutResult(status -> {
            appointmentInfoMapper.updateById(appointmentInfoList);
            appointmentInfoMapper.updateById(progressAppointmentInfoList);
            seatInfoService.updateBatchById(progressSeatList);
            seatInfoService.updateBatchById(endSeatList);
        });
    }

}
