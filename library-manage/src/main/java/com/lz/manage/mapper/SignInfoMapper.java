package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SignInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 签到信息Mapper接口
 * 
 * @author YY
 * @date 2026-03-18
 */
public interface SignInfoMapper extends BaseMapper<SignInfo>
{
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
     * 删除签到信息
     * 
     * @param id 签到信息主键
     * @return 结果
     */
    public int deleteSignInfoById(Long id);

    /**
     * 批量删除签到信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSignInfoByIds(Long[] ids);
}
