package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ViolationInfo;
import com.lz.manage.model.vo.violationInfo.ViolationInfoVo;
import com.lz.manage.model.dto.violationInfo.ViolationInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 违规信息Service接口
 *
 * @author YY
 * @date 2026-02-27
 */
public interface IViolationInfoService extends IService<ViolationInfo>
{
    //region mybatis代码
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
     * 批量删除违规信息
     *
     * @param ids 需要删除的违规信息主键集合
     * @return 结果
     */
    public int deleteViolationInfoByIds(Long[] ids);

    /**
     * 删除违规信息信息
     *
     * @param id 违规信息主键
     * @return 结果
     */
    public int deleteViolationInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param violationInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ViolationInfo> getQueryWrapper(ViolationInfoQuery violationInfoQuery);

    /**
     * 转换vo
     *
     * @param violationInfoList ViolationInfo集合
     * @return ViolationInfoVO集合
     */
    List<ViolationInfoVo> convertVoList(List<ViolationInfo> violationInfoList);

    void autoUpdateViolationInfo();
}
