package com.lz.manage.model.vo.violationInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ViolationInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 违规信息Vo对象 tb_violation_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class ViolationInfoVo implements Serializable {
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
     * 用户
     */
    private Long userId;
    private String userName;

    /**
     * 标题
     */
    private String name;

    /**
     * 原因
     */
    private String cause;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    private String createBy;

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
     * @param violationInfo ViolationInfo实体对象
     * @return ViolationInfoVo
     */
    public static ViolationInfoVo objToVo(ViolationInfo violationInfo) {
        if (violationInfo == null) {
            return null;
        }
        ViolationInfoVo violationInfoVo = new ViolationInfoVo();
        BeanUtils.copyProperties(violationInfo, violationInfoVo);
        return violationInfoVo;
    }
}
