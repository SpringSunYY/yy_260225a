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
import com.lz.manage.mapper.ViolationInfoMapper;
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.service.IViolationInfoService;
import com.lz.manage.model.dto.violationInfo.ViolationInfoQuery;
import com.lz.manage.model.vo.violationInfo.ViolationInfoVo;

/**
 * 违规信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class ViolationInfoServiceImpl extends ServiceImpl<ViolationInfoMapper, ViolationInfo> implements IViolationInfoService
{

    @Resource
    private ViolationInfoMapper violationInfoMapper;

    //region mybatis代码
    /**
     * 查询违规信息
     *
     * @param id 违规信息主键
     * @return 违规信息
     */
    @Override
    public ViolationInfo selectViolationInfoById(Long id)
    {
        return violationInfoMapper.selectViolationInfoById(id);
    }

    /**
     * 查询违规信息列表
     *
     * @param violationInfo 违规信息
     * @return 违规信息
     */
    @Override
    public List<ViolationInfo> selectViolationInfoList(ViolationInfo violationInfo)
    {
        return violationInfoMapper.selectViolationInfoList(violationInfo);
    }

    /**
     * 新增违规信息
     *
     * @param violationInfo 违规信息
     * @return 结果
     */
    @Override
    public int insertViolationInfo(ViolationInfo violationInfo)
    {
        violationInfo.setCreateTime(DateUtils.getNowDate());
        return violationInfoMapper.insertViolationInfo(violationInfo);
    }

    /**
     * 修改违规信息
     *
     * @param violationInfo 违规信息
     * @return 结果
     */
    @Override
    public int updateViolationInfo(ViolationInfo violationInfo)
    {
        violationInfo.setUpdateTime(DateUtils.getNowDate());
        return violationInfoMapper.updateViolationInfo(violationInfo);
    }

    /**
     * 批量删除违规信息
     *
     * @param ids 需要删除的违规信息主键
     * @return 结果
     */
    @Override
    public int deleteViolationInfoByIds(Long[] ids)
    {
        return violationInfoMapper.deleteViolationInfoByIds(ids);
    }

    /**
     * 删除违规信息信息
     *
     * @param id 违规信息主键
     * @return 结果
     */
    @Override
    public int deleteViolationInfoById(Long id)
    {
        return violationInfoMapper.deleteViolationInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ViolationInfo> getQueryWrapper(ViolationInfoQuery violationInfoQuery){
        QueryWrapper<ViolationInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = violationInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = violationInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long libraryId = violationInfoQuery.getLibraryId();
        queryWrapper.eq( StringUtils.isNotNull(libraryId),"library_id",libraryId);

        String name = violationInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = violationInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long userId = violationInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = violationInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<ViolationInfoVo> convertVoList(List<ViolationInfo> violationInfoList) {
        if (StringUtils.isEmpty(violationInfoList)) {
            return Collections.emptyList();
        }
        return violationInfoList.stream().map(ViolationInfoVo::objToVo).collect(Collectors.toList());
    }

}
