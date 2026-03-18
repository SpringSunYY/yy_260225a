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
 * 签到信息对象 tb_sign_info
 *
 * @author YY
 * @date 2026-03-18
 */
@TableName("tb_sign_info")
@Data
public class SignInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 签到
     */
    @Excel(name = "预约",type = Excel.Type.IMPORT)
    private Long appointmentId;
    @TableField(exist = false)
    @Excel(name = "预约",type = Excel.Type.EXPORT)
    private String appointmentName;

    /**
     * 图书馆
     */
    @Excel(name = "图书馆",type = Excel.Type.IMPORT)
    private Long libraryId;
    @TableField(exist = false)
    @Excel(name = "图书馆",type = Excel.Type.EXPORT)
    private String libraryName;

    /**
     * 座位
     */
    @Excel(name = "座位",type = Excel.Type.IMPORT)
    private Long seatId;
    @TableField(exist = false)
    @Excel(name = "座位",type = Excel.Type.EXPORT)
    private String seatName;

    /**
     * 凭证
     */
    @Excel(name = "凭证")
    private String certificateImage;

    /**
     * 签到类型
     */
    @Excel(name = "签到类型", dictType = "manage_sign_type")
    private String signType;

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
