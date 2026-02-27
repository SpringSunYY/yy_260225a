package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SeatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 座位信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface SeatInfoMapper extends BaseMapper<SeatInfo>
{
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
     * 删除座位信息
     * 
     * @param id 座位信息主键
     * @return 结果
     */
    public int deleteSeatInfoById(Long id);

    /**
     * 批量删除座位信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeatInfoByIds(Long[] ids);
}
