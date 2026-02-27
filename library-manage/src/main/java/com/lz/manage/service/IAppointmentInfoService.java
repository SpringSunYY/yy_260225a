package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.AppointmentInfo;
import com.lz.manage.model.vo.appointmentInfo.AppointmentInfoVo;
import com.lz.manage.model.dto.appointmentInfo.AppointmentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 预约信息Service接口
 *
 * @author YY
 * @date 2026-02-27
 */
public interface IAppointmentInfoService extends IService<AppointmentInfo>
{
    //region mybatis代码
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
     * 批量删除预约信息
     *
     * @param ids 需要删除的预约信息主键集合
     * @return 结果
     */
    public int deleteAppointmentInfoByIds(Long[] ids);

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteAppointmentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param appointmentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<AppointmentInfo> getQueryWrapper(AppointmentInfoQuery appointmentInfoQuery);

    /**
     * 转换vo
     *
     * @param appointmentInfoList AppointmentInfo集合
     * @return AppointmentInfoVO集合
     */
    List<AppointmentInfoVo> convertVoList(List<AppointmentInfo> appointmentInfoList);

    /**
     * 自动更新预约信息
     */
    void autoUpdateAppointmentInfo();

}
