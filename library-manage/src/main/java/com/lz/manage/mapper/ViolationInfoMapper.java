package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ViolationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 违规信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface ViolationInfoMapper extends BaseMapper<ViolationInfo>
{
    /**
     * 查询违规信息
     * 
     * @param id 违规信息主键
     * @return 违规信息
     */
    public ViolationInfo selectViolationInfoById(Long id);

    /**
     * 查询违规信息列表
     * 
     * @param violationInfo 违规信息
     * @return 违规信息集合
     */
    public List<ViolationInfo> selectViolationInfoList(ViolationInfo violationInfo);

    /**
     * 新增违规信息
     * 
     * @param violationInfo 违规信息
     * @return 结果
     */
    public int insertViolationInfo(ViolationInfo violationInfo);

    /**
     * 修改违规信息
     * 
     * @param violationInfo 违规信息
     * @return 结果
     */
    public int updateViolationInfo(ViolationInfo violationInfo);

    /**
     * 删除违规信息
     * 
     * @param id 违规信息主键
     * @return 结果
     */
    public int deleteViolationInfoById(Long id);

    /**
     * 批量删除违规信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViolationInfoByIds(Long[] ids);
}
