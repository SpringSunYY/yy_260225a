package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PartitionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 分区信息Mapper接口
 * 
 * @author YY
 * @date 2026-05-14
 */
public interface PartitionInfoMapper extends BaseMapper<PartitionInfo>
{
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
     * 删除分区信息
     * 
     * @param id 分区信息主键
     * @return 结果
     */
    public int deletePartitionInfoById(Long id);

    /**
     * 批量删除分区信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePartitionInfoByIds(Long[] ids);
}
