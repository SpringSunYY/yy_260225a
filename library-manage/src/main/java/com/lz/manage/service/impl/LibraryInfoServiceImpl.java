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
import com.lz.manage.mapper.LibraryInfoMapper;
import com.lz.manage.model.domain.LibraryInfo;
import com.lz.manage.service.ILibraryInfoService;
import com.lz.manage.model.dto.libraryInfo.LibraryInfoQuery;
import com.lz.manage.model.vo.libraryInfo.LibraryInfoVo;

/**
 * 图书馆信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-27
 */
@Service
public class LibraryInfoServiceImpl extends ServiceImpl<LibraryInfoMapper, LibraryInfo> implements ILibraryInfoService
{

    @Resource
    private LibraryInfoMapper libraryInfoMapper;

    //region mybatis代码
    /**
     * 查询图书馆信息
     *
     * @param id 图书馆信息主键
     * @return 图书馆信息
     */
    @Override
    public LibraryInfo selectLibraryInfoById(Long id)
    {
        return libraryInfoMapper.selectLibraryInfoById(id);
    }

    /**
     * 查询图书馆信息列表
     *
     * @param libraryInfo 图书馆信息
     * @return 图书馆信息
     */
    @Override
    public List<LibraryInfo> selectLibraryInfoList(LibraryInfo libraryInfo)
    {
        return libraryInfoMapper.selectLibraryInfoList(libraryInfo);
    }

    /**
     * 新增图书馆信息
     *
     * @param libraryInfo 图书馆信息
     * @return 结果
     */
    @Override
    public int insertLibraryInfo(LibraryInfo libraryInfo)
    {
        libraryInfo.setCreateTime(DateUtils.getNowDate());
        return libraryInfoMapper.insertLibraryInfo(libraryInfo);
    }

    /**
     * 修改图书馆信息
     *
     * @param libraryInfo 图书馆信息
     * @return 结果
     */
    @Override
    public int updateLibraryInfo(LibraryInfo libraryInfo)
    {
        libraryInfo.setUpdateTime(DateUtils.getNowDate());
        return libraryInfoMapper.updateLibraryInfo(libraryInfo);
    }

    /**
     * 批量删除图书馆信息
     *
     * @param ids 需要删除的图书馆信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryInfoByIds(Long[] ids)
    {
        return libraryInfoMapper.deleteLibraryInfoByIds(ids);
    }

    /**
     * 删除图书馆信息信息
     *
     * @param id 图书馆信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryInfoById(Long id)
    {
        return libraryInfoMapper.deleteLibraryInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<LibraryInfo> getQueryWrapper(LibraryInfoQuery libraryInfoQuery){
        QueryWrapper<LibraryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = libraryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = libraryInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = libraryInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = libraryInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Date createTime = libraryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<LibraryInfoVo> convertVoList(List<LibraryInfo> libraryInfoList) {
        if (StringUtils.isEmpty(libraryInfoList)) {
            return Collections.emptyList();
        }
        return libraryInfoList.stream().map(LibraryInfoVo::objToVo).collect(Collectors.toList());
    }

}
