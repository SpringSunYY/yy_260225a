package com.lz.manage.model.vo.appointmentInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.AppointmentInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息Vo对象 tb_appointment_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class AppointmentInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 图书馆
     */
    private Long libraryId;
    private String libraryName;

    /**
     * 座位
     */
    private Long seatId;
    private String seatName;

    /**
     * 标题
     */
    private String name;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param appointmentInfo AppointmentInfo实体对象
     * @return AppointmentInfoVo
     */
    public static AppointmentInfoVo objToVo(AppointmentInfo appointmentInfo) {
        if (appointmentInfo == null) {
            return null;
        }
        AppointmentInfoVo appointmentInfoVo = new AppointmentInfoVo();
        BeanUtils.copyProperties(appointmentInfo, appointmentInfoVo);
        return appointmentInfoVo;
    }
}
