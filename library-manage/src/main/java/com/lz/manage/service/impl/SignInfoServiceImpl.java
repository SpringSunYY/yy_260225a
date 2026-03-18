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
import com.lz.manage.mapper.SignInfoMapper;
import com.lz.manage.model.domain.SignInfo;
import com.lz.manage.service.ISignInfoService;
import com.lz.manage.model.dto.signInfo.SignInfoQuery;
import com.lz.manage.model.vo.signInfo.SignInfoVo;

/**
 * 签到信息Service业务层处理
 *
 * @author YY
 * @date 2026-03-18
 */
@Service
public class SignInfoServiceImpl extends ServiceImpl<SignInfoMapper, SignInfo> implements ISignInfoService
{

    @Resource
    private SignInfoMapper signInfoMapper;

    //region mybatis代码
    /**
     * 查询签到信息
     *
     * @param id 签到信息主键
     * @return 签到信息
     */
    @Override
    public SignInfo selectSignInfoById(Long id)
    {
        return signInfoMapper.selectSignInfoById(id);
    }

    /**
     * 查询签到信息列表
     *
     * @param signInfo 签到信息
     * @return 签到信息
     */
    @Override
    public List<SignInfo> selectSignInfoList(SignInfo signInfo)
    {
        return signInfoMapper.selectSignInfoList(signInfo);
    }

    /**
     * 新增签到信息
     *
     * @param signInfo 签到信息
     * @return 结果
     */
    @Override
    public int insertSignInfo(SignInfo signInfo)
    {
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
    public int updateSignInfo(SignInfo signInfo)
    {
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
    public int deleteSignInfoByIds(Long[] ids)
    {
        return signInfoMapper.deleteSignInfoByIds(ids);
    }

    /**
     * 删除签到信息信息
     *
     * @param id 签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignInfoById(Long id)
    {
        return signInfoMapper.deleteSignInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<SignInfo> getQueryWrapper(SignInfoQuery signInfoQuery){
        QueryWrapper<SignInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = signInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = signInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long appointmentId = signInfoQuery.getAppointmentId();
        queryWrapper.eq( StringUtils.isNotNull(appointmentId),"appointment_id",appointmentId);

        Long libraryId = signInfoQuery.getLibraryId();
        queryWrapper.eq( StringUtils.isNotNull(libraryId),"library_id",libraryId);

        Long seatId = signInfoQuery.getSeatId();
        queryWrapper.eq( StringUtils.isNotNull(seatId),"seat_id",seatId);

        String signType = signInfoQuery.getSignType();
        queryWrapper.eq(StringUtils.isNotEmpty(signType) ,"sign_type",signType);

        Long userId = signInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = signInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
