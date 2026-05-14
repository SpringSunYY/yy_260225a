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
import com.lz.manage.mapper.PartitionInfoMapper;
import com.lz.manage.model.domain.PartitionInfo;
import com.lz.manage.service.IPartitionInfoService;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoQuery;
import com.lz.manage.model.vo.partitionInfo.PartitionInfoVo;

/**
 * 分区信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-14
 */
@Service
public class PartitionInfoServiceImpl extends ServiceImpl<PartitionInfoMapper, PartitionInfo> implements IPartitionInfoService
{

    @Resource
    private PartitionInfoMapper partitionInfoMapper;

    //region mybatis代码
    /**
     * 查询分区信息
     *
     * @param id 分区信息主键
     * @return 分区信息
     */
    @Override
    public PartitionInfo selectPartitionInfoById(Long id)
    {
        return partitionInfoMapper.selectPartitionInfoById(id);
    }

    /**
     * 查询分区信息列表
     *
     * @param partitionInfo 分区信息
     * @return 分区信息
     */
    @Override
    public List<PartitionInfo> selectPartitionInfoList(PartitionInfo partitionInfo)
    {
        return partitionInfoMapper.selectPartitionInfoList(partitionInfo);
    }

    /**
     * 新增分区信息
     *
     * @param partitionInfo 分区信息
     * @return 结果
     */
    @Override
    public int insertPartitionInfo(PartitionInfo partitionInfo)
    {
        partitionInfo.setCreateTime(DateUtils.getNowDate());
        return partitionInfoMapper.insertPartitionInfo(partitionInfo);
    }

    /**
     * 修改分区信息
     *
     * @param partitionInfo 分区信息
     * @return 结果
     */
    @Override
    public int updatePartitionInfo(PartitionInfo partitionInfo)
    {
        partitionInfo.setUpdateTime(DateUtils.getNowDate());
        return partitionInfoMapper.updatePartitionInfo(partitionInfo);
    }

    /**
     * 批量删除分区信息
     *
     * @param ids 需要删除的分区信息主键
     * @return 结果
     */
    @Override
    public int deletePartitionInfoByIds(Long[] ids)
    {
        return partitionInfoMapper.deletePartitionInfoByIds(ids);
    }

    /**
     * 删除分区信息信息
     *
     * @param id 分区信息主键
     * @return 结果
     */
    @Override
    public int deletePartitionInfoById(Long id)
    {
        return partitionInfoMapper.deletePartitionInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<PartitionInfo> getQueryWrapper(PartitionInfoQuery partitionInfoQuery){
        QueryWrapper<PartitionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = partitionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = partitionInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = partitionInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = partitionInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String description = partitionInfoQuery.getDescription();
        queryWrapper.eq(StringUtils.isNotEmpty(description) ,"description",description);

        Long userId = partitionInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = partitionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<PartitionInfoVo> convertVoList(List<PartitionInfo> partitionInfoList) {
        if (StringUtils.isEmpty(partitionInfoList)) {
            return Collections.emptyList();
        }
        return partitionInfoList.stream().map(PartitionInfoVo::objToVo).collect(Collectors.toList());
    }

}
