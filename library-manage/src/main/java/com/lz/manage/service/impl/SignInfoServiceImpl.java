package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.SignInfoMapper;
import com.lz.manage.model.domain.AppointmentInfo;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.domain.SeatInfo;
import com.lz.manage.model.domain.SignInfo;
import com.lz.manage.model.dto.signInfo.SignInfoQuery;
import com.lz.manage.model.enums.ManageAppointmentStatusEnum;
import com.lz.manage.model.enums.ManageSignTypeEnum;
import com.lz.manage.model.vo.signInfo.SignInfoVo;
import com.lz.manage.service.IAppointmentInfoService;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.service.ISeatInfoService;
import com.lz.manage.service.ISignInfoService;
import com.lz.system.service.ISysConfigService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 签到信息Service业务层处理
 *
 * @author YY
 * @date 2026-03-18
 */
@Service
public class SignInfoServiceImpl extends ServiceImpl<SignInfoMapper, SignInfo> implements ISignInfoService {

    @Resource
    private SignInfoMapper signInfoMapper;

    @Resource
    private IAppointmentInfoService appointmentInfoService;

    @Resource
    private ILibraryInfoService libraryInfoService;

    @Resource
    private ISeatInfoService seatInfoService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysConfigService sysConfigService;
    //region mybatis代码

    /**
     * 查询签到信息
     *
     * @param id 签到信息主键
     * @return 签到信息
     */
    @Override
    public SignInfo selectSignInfoById(Long id) {
        return signInfoMapper.selectSignInfoById(id);
    }

    /**
     * 查询签到信息列表
     *
     * @param signInfo 签到信息
     * @return 签到信息
     */
    @Override
    @DataScope(deptAlias = "tb_sign_info", userAlias = "tb_sign_info")
    public List<SignInfo> selectSignInfoList(SignInfo signInfo) {
        List<SignInfo> signInfos = signInfoMapper.selectSignInfoList(signInfo);
        for (SignInfo info : signInfos) {
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
            AppointmentInfo appointmentInfo = appointmentInfoService.selectAppointmentInfoById(info.getAppointmentId());
            if (StringUtils.isNotNull(appointmentInfo)) {
                info.setAppointmentName(appointmentInfo.getName());
            }
        }
        return signInfos;
    }

    /**
     * 新增签到信息
     *
     * @param signInfo 签到信息
     * @return 结果
     */
    @Override
    public int insertSignInfo(SignInfo signInfo) {
        //首先查询预约信息
        AppointmentInfo appointmentInfo = appointmentInfoService.selectAppointmentInfoById(signInfo.getAppointmentId());
        if (StringUtils.isNull(appointmentInfo)) {
            throw new ServiceException("预约信息不存在");
        }
        //签到必须在预约开始时间之后，预约结束时间之前
        Date now = new Date();
        if (appointmentInfo.getStartTime().after(now)) {
            throw new ServiceException("预约还未开始");
        }
        if (appointmentInfo.getEndTime().before(now)) {
            throw new ServiceException("预约时间已过");
        }
        //如果不是自己的预约信息
        Long userId = SecurityUtils.getUserId();
        if (!appointmentInfo.getUserId().equals(userId)) {
            throw new ServiceException("不能操作别人的预约信息");
        }
        //如果用户已经签到该类型
        LambdaQueryWrapper<SignInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SignInfo::getAppointmentId, signInfo.getAppointmentId())
                .eq(SignInfo::getSignType, signInfo.getSignType())
                .eq(SignInfo::getUserId, userId);
        List<SignInfo> list = this.list(queryWrapper);
        if (!StringUtils.isEmpty(list)) {
            throw new ServiceException("用户已经签到该类型");
        }
        String signTimeStr = sysConfigService.selectConfigByKey("manage.sign.time");
        long signTime = 30L;
        try {
            signTime = Long.parseLong(signTimeStr);
        } catch (Exception e) {
            signTime = 30;
        }
        //如果预约开始时间已经超过30分钟且是签到
        if (signInfo.getSignType().equals(ManageSignTypeEnum.MANAGE_SIGN_TYPE_1.getValue())
                && System.currentTimeMillis() - appointmentInfo.getStartTime().getTime() > signTime * 60 * 1000) {
            throw new ServiceException(StringUtils.format("预约开始时间已超过{}分钟，无法签到", signTime));
        }
        //如果是签退，必须要有签到
        if (signInfo.getSignType().equals(ManageSignTypeEnum.MANAGE_SIGN_TYPE_2.getValue())) {
            //如果签到不是开始
            LambdaQueryWrapper<SignInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SignInfo::getAppointmentId, signInfo.getAppointmentId())
                    .eq(SignInfo::getUserId, userId)
                    .eq(SignInfo::getSignType, ManageSignTypeEnum.MANAGE_SIGN_TYPE_1.getValue());
            List<SignInfo> signInfoList = this.list(wrapper);
            if (StringUtils.isEmpty(signInfoList)) {
                throw new ServiceException("请先签到");
            }
            //签退更新预约信息为结束
            appointmentInfo.setStatus(ManageAppointmentStatusEnum.MANAGE_APPOINTMENT_STATUS_3.getValue());
            appointmentInfo.setEndTime(DateUtils.getNowDate());
            appointmentInfoService.updateById(appointmentInfo);
        }

        signInfo.setUserId(userId);
        signInfo.setUpdateTime(DateUtils.getNowDate());
        signInfo.setSeatId(appointmentInfo.getSeatId());
        signInfo.setLibraryId(appointmentInfo.getLibraryId());
        signInfo.setCreateTime(DateUtils.getNowDate());
        return signInfoMapper.insertSignInfo(signInfo);
    }

    /**
     * 修改签到信息
     *
     * @param signInfo 签到信息
     * @return 结果
     */
    @Override
    public int updateSignInfo(SignInfo signInfo) {
        signInfo.setUpdateTime(DateUtils.getNowDate());
        return signInfoMapper.updateSignInfo(signInfo);
    }

    /**
     * 批量删除签到信息
     *
     * @param ids 需要删除的签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignInfoByIds(Long[] ids) {
        return signInfoMapper.deleteSignInfoByIds(ids);
    }

    /**
     * 删除签到信息信息
     *
     * @param id 签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignInfoById(Long id) {
        return signInfoMapper.deleteSignInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SignInfo> getQueryWrapper(SignInfoQuery signInfoQuery) {
        QueryWrapper<SignInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = signInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = signInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long appointmentId = signInfoQuery.getAppointmentId();
        queryWrapper.eq(StringUtils.isNotNull(appointmentId), "appointment_id", appointmentId);

        Long libraryId = signInfoQuery.getLibraryId();
        queryWrapper.eq(StringUtils.isNotNull(libraryId), "library_id", libraryId);

        Long seatId = signInfoQuery.getSeatId();
        queryWrapper.eq(StringUtils.isNotNull(seatId), "seat_id", seatId);

        String signType = signInfoQuery.getSignType();
        queryWrapper.eq(StringUtils.isNotEmpty(signType), "sign_type", signType);

        Long userId = signInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = signInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<SignInfoVo> convertVoList(List<SignInfo> signInfoList) {
        if (StringUtils.isEmpty(signInfoList)) {
            return Collections.emptyList();
        }
        return signInfoList.stream().map(SignInfoVo::objToVo).collect(Collectors.toList());
    }

}
