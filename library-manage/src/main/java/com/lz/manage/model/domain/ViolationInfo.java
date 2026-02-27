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
 * 违规信息对象 tb_violation_info
 *
 * @author YY
 * @date 2026-02-27
 */
@TableName("tb_violation_info")
@Data
public class ViolationInfo implements Serializable {
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
     * 用户
     */
    @Excel(name = "用户", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "用户", type = Excel.Type.EXPORT)
    private String userName;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String name;

    /**
     * 原因
     */
    @Excel(name = "原因")
    private String cause;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "manage_violation_status")
    private String status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

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
