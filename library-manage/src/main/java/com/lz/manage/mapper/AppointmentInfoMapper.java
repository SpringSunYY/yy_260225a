package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.AppointmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 预约信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-27
 */
public interface AppointmentInfoMapper extends BaseMapper<AppointmentInfo>
{
    /**
     * 查询预约信息
     * 
     * @param id 预约信息主键
     * @return 预约信息
     */
    public AppointmentInfo selectAppointmentInfoById(Long id);

    /**
     * 查询预约信息列表
     * 
     * @param appointmentInfo 预约信息
     * @return 预约信息集合
     */
    public List<AppointmentInfo> selectAppointmentInfoList(AppointmentInfo appointmentInfo);

    /**
     * 新增预约信息
     * 
     * @param appointmentInfo 预约信息
     * @return 结果
     */
    public int insertAppointmentInfo(AppointmentInfo appointmentInfo);

    /**
     * 修改预约信息
     * 
     * @param appointmentInfo 预约信息
     * @return 结果
     */
    public int updateAppointmentInfo(AppointmentInfo appointmentInfo);

    /**
     * 删除预约信息
     * 
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteAppointmentInfoById(Long id);

    /**
     * 批量删除预约信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppointmentInfoByIds(Long[] ids);
}
