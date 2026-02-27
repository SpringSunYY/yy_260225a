package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 预约信息对象 tb_appointment_info
 *
 * @author YY
 * @date 2026-02-27
 */
@TableName("tb_appointment_info")
@Data
public class AppointmentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 图书馆
     */
    @Excel(name = "图书馆", type = Excel.Type.IMPORT)
    private Long libraryId;
    @TableField(exist = false)
    @Excel(name = "图书馆", type = Excel.Type.EXPORT)
    private String libraryName;

    /**
     * 座位
     */
    @Excel(name = "座位", type = Excel.Type.IMPORT)
    private Long seatId;
    @TableField(exist = false)
    @Excel(name = "座位", type = Excel.Type.EXPORT)
    private String seatName;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String name;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "manage_appointment_status")
    private String status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
