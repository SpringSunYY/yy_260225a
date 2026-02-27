package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.ViolationInfoMapper;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.model.dto.violationInfo.ViolationInfoQuery;
import com.lz.manage.model.vo.violationInfo.ViolationInfoVo;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.service.IViolationInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 违规信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class ViolationInfoServiceImpl extends ServiceImpl<ViolationInfoMapper, ViolationInfo> implements IViolationInfoService {

    @Resource
    private ViolationInfoMapper violationInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ILibraryInfoService libraryInfoService;


    //region mybatis代码

    /**
     * 查询违规信息
     *
     * @param id 违规信息主键
     * @return 违规信息
     */
    @Override
    public ViolationInfo selectViolationInfoById(Long id) {
        return violationInfoMapper.selectViolationInfoById(id);
    }

    /**
     * 查询违规信息列表
     *
     * @param violationInfo 违规信息
     * @return 违规信息
     */
    @Override
    public List<ViolationInfo> selectViolationInfoList(ViolationInfo violationInfo) {
        List<ViolationInfo> violationInfos = violationInfoMapper.selectViolationInfoList(violationInfo);
        for (ViolationInfo info : violationInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(info.getLibraryId());
            if (StringUtils.isNotNull(libraryInfo)) {
                info.setLibraryName(libraryInfo.getName());
            }
        }
        return violationInfos;
    }

    /**
     * 新增违规信息
     *
     * @param violationInfo 违规信息
     * @return 结果
     */
    @Override
    public int insertViolationInfo(ViolationInfo violationInfo) {
        violationInfo.setCreateBy(SecurityUtils.getUsername());
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
    public int updateViolationInfo(ViolationInfo violationInfo) {
        violationInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteViolationInfoByIds(Long[] ids) {
        return violationInfoMapper.deleteViolationInfoByIds(ids);
    }

    /**
     * 删除违规信息信息
     *
     * @param id 违规信息主键
     * @return 结果
     */
    @Override
    public int deleteViolationInfoById(Long id) {
        return violationInfoMapper.deleteViolationInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ViolationInfo> getQueryWrapper(ViolationInfoQuery violationInfoQuery) {
        QueryWrapper<ViolationInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = violationInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = violationInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long libraryId = violationInfoQuery.getLibraryId();
        queryWrapper.eq(StringUtils.isNotNull(libraryId), "library_id", libraryId);

        String name = violationInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = violationInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = violationInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = violationInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
