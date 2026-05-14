package com.lz.manage.model.dto.appointmentInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.manage.model.domain.AppointmentInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 预约信息Query对象 tb_appointment_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class AppointmentInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 图书馆
     */
    private Long libraryId;

    /**
     * 分区
     */
    private Long partitionId;

    /**
     * 座位
     */
    private Long seatId;

    /**
     * 标题
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param appointmentInfoQuery 查询对象
     * @return AppointmentInfo
     */
    public static AppointmentInfo queryToObj(AppointmentInfoQuery appointmentInfoQuery) {
        if (appointmentInfoQuery == null) {
            return null;
        }
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        BeanUtils.copyProperties(appointmentInfoQuery, appointmentInfo);
        return appointmentInfo;
    }
}
