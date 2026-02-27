package com.lz.manage.model.dto.appointmentInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.AppointmentInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息Vo对象 tb_appointment_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class AppointmentInfoInsert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图书馆
     */
    private Long libraryId;

    /**
     * 座位
     */
    private Long seatId;

    /**
     * 标题
     */
    private String name;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
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

    /**
     * 对象转封装类
     *
     * @param appointmentInfoInsert 插入对象
     * @return AppointmentInfoInsert
     */
    public static AppointmentInfo insertToObj(AppointmentInfoInsert appointmentInfoInsert) {
        if (appointmentInfoInsert == null) {
            return null;
        }
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        BeanUtils.copyProperties(appointmentInfoInsert, appointmentInfo);
        return appointmentInfo;
    }
}
