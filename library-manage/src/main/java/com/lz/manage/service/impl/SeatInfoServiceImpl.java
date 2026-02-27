package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.SeatInfoMapper;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.model.domain.SeatInfo;
import com.lz.manage.model.dto.seatInfo.SeatInfoQuery;
import com.lz.manage.model.vo.seatInfo.SeatInfoVo;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.service.ISeatInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 座位信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class SeatInfoServiceImpl extends ServiceImpl<SeatInfoMapper, SeatInfo> implements ISeatInfoService {

    @Resource
    private SeatInfoMapper seatInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ILibraryInfoService libraryInfoService;

    //region mybatis代码

    /**
     * 查询座位信息
     *
     * @param id 座位信息主键
     * @return 座位信息
     */
    @Override
    public SeatInfo selectSeatInfoById(Long id) {
        return seatInfoMapper.selectSeatInfoById(id);
    }

    /**
     * 查询座位信息列表
     *
     * @param seatInfo 座位信息
     * @return 座位信息
     */
    @Override
    public List<SeatInfo> selectSeatInfoList(SeatInfo seatInfo) {
        List<SeatInfo> seatInfos = seatInfoMapper.selectSeatInfoList(seatInfo);
        for (SeatInfo info : seatInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            LibraryInfo libraryInfo = libraryInfoService.selectLibraryInfoById(info.getLibraryId());
            if (StringUtils.isNotNull(libraryInfo)) {
                info.setLibraryName(libraryInfo.getName());
            }
        }
        return seatInfos;
    }

    /**
     * 新增座位信息
     *
     * @param seatInfo 座位信息
     * @return 结果
     */
    @Override
    public int insertSeatInfo(SeatInfo seatInfo) {
        seatInfo.setUserId(SecurityUtils.getUserId());
        seatInfo.setCreateTime(DateUtils.getNowDate());
        return seatInfoMapper.insertSeatInfo(seatInfo);
    }

    /**
     * 修改座位信息
     *
     * @param seatInfo 座位信息
     * @return 结果
     */
    @Override
    public int updateSeatInfo(SeatInfo seatInfo) {
        seatInfo.setUpdateBy(SecurityUtils.getUsername());
        seatInfo.setUpdateTime(DateUtils.getNowDate());
        return seatInfoMapper.updateSeatInfo(seatInfo);
    }

    /**
     * 批量删除座位信息
     *
     * @param ids 需要删除的座位信息主键
     * @return 结果
     */
    @Override
    public int deleteSeatInfoByIds(Long[] ids) {
        return seatInfoMapper.deleteSeatInfoByIds(ids);
    }

    /**
     * 删除座位信息信息
     *
     * @param id 座位信息主键
     * @return 结果
     */
    @Override
    public int deleteSeatInfoById(Long id) {
        return seatInfoMapper.deleteSeatInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SeatInfo> getQueryWrapper(SeatInfoQuery seatInfoQuery) {
        QueryWrapper<SeatInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = seatInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = seatInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long libraryId = seatInfoQuery.getLibraryId();
        queryWrapper.eq(StringUtils.isNotNull(libraryId), "library_id", libraryId);

        String name = seatInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = seatInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Date createTime = seatInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<SeatInfoVo> convertVoList(List<SeatInfo> seatInfoList) {
        if (StringUtils.isEmpty(seatInfoList)) {
            return Collections.emptyList();
        }
        return seatInfoList.stream().map(SeatInfoVo::objToVo).collect(Collectors.toList());
    }

}
