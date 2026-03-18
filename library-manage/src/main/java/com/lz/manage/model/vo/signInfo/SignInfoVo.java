package com.lz.manage.model.vo.signInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SignInfo;
/**
 * 签到信息Vo对象 tb_sign_info
 *
 * @author YY
 * @date 2026-03-18
 */
@Data
public class SignInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 签到 */
    private Long appointmentId;

    /** 图书馆 */
    private Long libraryId;

    /** 座位 */
    private Long seatId;

    /** 凭证 */
    private String certificateImage;

    /** 签到类型 */
    private String signType;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param signInfo SignInfo实体对象
     * @return SignInfoVo
     */
    public static SignInfoVo objToVo(SignInfo signInfo) {
        if (signInfo == null) {
            return null;
        }
        SignInfoVo signInfoVo = new SignInfoVo();
        BeanUtils.copyProperties(signInfo, signInfoVo);
        return signInfoVo;
    }
}
