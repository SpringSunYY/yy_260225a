package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.PartitionInfoMapper;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.domain.PartitionInfo;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoQuery;
import com.lz.manage.model.vo.partitionInfo.PartitionInfoVo;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.service.IPartitionInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分区信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-14
 */
@Service
public class PartitionInfoServiceImpl extends ServiceImpl<PartitionInfoMapper, PartitionInfo> implements IPartitionInfoService {

    @Resource
    private PartitionInfoMapper partitionInfoMapper;

    @Resource
    private ILibraryInfoService libraryInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询分区信息
     *
     * @param id 分区信息主键
     * @return 分区信息
     */
    @Override
    public PartitionInfo selectPartitionInfoById(Long id) {
        return partitionInfoMapper.selectPartitionInfoById(id);
    }

    /**
     * 查询分区信息列表
     *
     * @param partitionInfo 分区信息
     * @return 分区信息
     */
    @Override
    public List<PartitionInfo> selectPartitionInfoList(PartitionInfo partitionInfo) {
        List<PartitionInfo> partitionInfos = partitionInfoMapper.selectPartitionInfoList(partitionInfo);
        for (PartitionInfo info : partitionInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(info.getLibraryId());
            if (StringUtils.isNotNull(libraryInfo)) {
                info.setLibraryName(libraryInfo.getName());
            }
        }
        return partitionInfos;
    }

    /**
     * 新增分区信息
     *
     * @param partitionInfo 分区信息
     * @return 结果
     */
    @Override
    public int insertPartitionInfo(PartitionInfo partitionInfo) {
        //先查询图书馆
        LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(partitionInfo.getLibraryId());
        ThrowUtils.throwIf(StringUtils.isNull(libraryInfo), "图书馆不存在");
        partitionInfo.setUserId(SecurityUtils.getUserId());
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
    public int updatePartitionInfo(PartitionInfo partitionInfo) {
        //先查询图书馆
        LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(partitionInfo.getLibraryId());
        ThrowUtils.throwIf(StringUtils.isNull(libraryInfo), "图书馆不存在");
        partitionInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deletePartitionInfoByIds(Long[] ids) {
        return partitionInfoMapper.deletePartitionInfoByIds(ids);
    }

    /**
     * 删除分区信息信息
     *
     * @param id 分区信息主键
     * @return 结果
     */
    @Override
    public int deletePartitionInfoById(Long id) {
        return partitionInfoMapper.deletePartitionInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<PartitionInfo> getQueryWrapper(PartitionInfoQuery partitionInfoQuery) {
        QueryWrapper<PartitionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = partitionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = partitionInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String name = partitionInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = partitionInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String description = partitionInfoQuery.getDescription();
        queryWrapper.eq(StringUtils.isNotEmpty(description), "description", description);

        Long userId = partitionInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = partitionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
