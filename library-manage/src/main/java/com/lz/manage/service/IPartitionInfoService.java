package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.PartitionInfo;
import com.lz.manage.model.vo.partitionInfo.PartitionInfoVo;
import com.lz.manage.model.dto.partitionInfo.PartitionInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 分区信息Service接口
 * 
 * @author YY
 * @date 2026-05-14
 */
public interface IPartitionInfoService extends IService<PartitionInfo>
{
    //region mybatis代码
    /**
     * 查询分区信息
     * 
     * @param id 分区信息主键
     * @return 分区信息
     */
    public PartitionInfo selectPartitionInfoById(Long id);

    /**
     * 查询分区信息列表
     * 
     * @param partitionInfo 分区信息
     * @return 分区信息集合
     */
    public List<PartitionInfo> selectPartitionInfoList(PartitionInfo partitionInfo);

    /**
     * 新增分区信息
     * 
     * @param partitionInfo 分区信息
     * @return 结果
     */
    public int insertPartitionInfo(PartitionInfo partitionInfo);

    /**
     * 修改分区信息
     * 
     * @param partitionInfo 分区信息
     * @return 结果
     */
    public int updatePartitionInfo(PartitionInfo partitionInfo);

    /**
     * 批量删除分区信息
     * 
     * @param ids 需要删除的分区信息主键集合
     * @return 结果
     */
    public int deletePartitionInfoByIds(Long[] ids);

    /**
     * 删除分区信息信息
     * 
     * @param id 分区信息主键
     * @return 结果
     */
    public int deletePartitionInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param partitionInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PartitionInfo> getQueryWrapper(PartitionInfoQuery partitionInfoQuery);

    /**
     * 转换vo
     *
     * @param partitionInfoList PartitionInfo集合
     * @return PartitionInfoVO集合
     */
    List<PartitionInfoVo> convertVoList(List<PartitionInfo> partitionInfoList);
}
