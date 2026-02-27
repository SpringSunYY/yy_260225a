package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SeatInfo;
import com.lz.manage.model.vo.seatInfo.SeatInfoVo;
import com.lz.manage.model.dto.seatInfo.SeatInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 座位信息Service接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface ISeatInfoService extends IService<SeatInfo>
{
    //region mybatis代码
    /**
     * 查询座位信息
     * 
     * @param id 座位信息主键
     * @return 座位信息
     */
    public SeatInfo selectSeatInfoById(Long id);

    /**
     * 查询座位信息列表
     * 
     * @param seatInfo 座位信息
     * @return 座位信息集合
     */
    public List<SeatInfo> selectSeatInfoList(SeatInfo seatInfo);

    /**
     * 新增座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    public int insertSeatInfo(SeatInfo seatInfo);

    /**
     * 修改座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    public int updateSeatInfo(SeatInfo seatInfo);

    /**
     * 批量删除座位信息
     * 
     * @param ids 需要删除的座位信息主键集合
     * @return 结果
     */
    public int deleteSeatInfoByIds(Long[] ids);

    /**
     * 删除座位信息信息
     * 
     * @param id 座位信息主键
     * @return 结果
     */
    public int deleteSeatInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param seatInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SeatInfo> getQueryWrapper(SeatInfoQuery seatInfoQuery);

    /**
     * 转换vo
     *
     * @param seatInfoList SeatInfo集合
     * @return SeatInfoVO集合
     */
    List<SeatInfoVo> convertVoList(List<SeatInfo> seatInfoList);
}
