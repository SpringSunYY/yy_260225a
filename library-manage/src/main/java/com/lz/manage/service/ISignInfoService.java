package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SignInfo;
import com.lz.manage.model.vo.signInfo.SignInfoVo;
import com.lz.manage.model.dto.signInfo.SignInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 签到信息Service接口
 * 
 * @author YY
 * @date 2026-03-18
 */
public interface ISignInfoService extends IService<SignInfo>
{
    //region mybatis代码
    /**
     * 查询签到信息
     * 
     * @param id 签到信息主键
     * @return 签到信息
     */
    public SignInfo selectSignInfoById(Long id);

    /**
     * 查询签到信息列表
     * 
     * @param signInfo 签到信息
     * @return 签到信息集合
     */
    public List<SignInfo> selectSignInfoList(SignInfo signInfo);

    /**
     * 新增签到信息
     * 
     * @param signInfo 签到信息
     * @return 结果
     */
    public int insertSignInfo(SignInfo signInfo);

    /**
     * 修改签到信息
     * 
     * @param signInfo 签到信息
     * @return 结果
     */
    public int updateSignInfo(SignInfo signInfo);

    /**
     * 批量删除签到信息
     * 
     * @param ids 需要删除的签到信息主键集合
     * @return 结果
     */
    public int deleteSignInfoByIds(Long[] ids);

    /**
     * 删除签到信息信息
     * 
     * @param id 签到信息主键
     * @return 结果
     */
    public int deleteSignInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param signInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SignInfo> getQueryWrapper(SignInfoQuery signInfoQuery);

    /**
     * 转换vo
     *
     * @param signInfoList SignInfo集合
     * @return SignInfoVO集合
     */
    List<SignInfoVo> convertVoList(List<SignInfo> signInfoList);
}
