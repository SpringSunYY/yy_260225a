package com.lz.manage.model.dto.violationInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ViolationInfo;
/**
 * 违规信息Vo对象 tb_violation_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class ViolationInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 图书馆 */
    private Long libraryId;

    /** 标题 */
    private String name;

    /** 原因 */
    private String cause;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 状态 */
    private String status;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param violationInfoEdit 编辑对象
     * @return ViolationInfo
     */
    public static ViolationInfo editToObj(ViolationInfoEdit violationInfoEdit) {
        if (violationInfoEdit == null) {
            return null;
        }
        ViolationInfo violationInfo = new ViolationInfo();
        BeanUtils.copyProperties(violationInfoEdit, violationInfo);
        return violationInfo;
    }
}
