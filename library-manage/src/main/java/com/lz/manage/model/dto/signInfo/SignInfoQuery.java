package com.lz.manage.model.dto.signInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.SignInfo;
/**
 * 签到信息Query对象 tb_sign_info
 *
 * @author YY
 * @date 2026-03-18
 */
@Data
public class SignInfoQuery implements Serializable
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

    /** 签到类型 */
    private String signType;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param signInfoQuery 查询对象
     * @return SignInfo
     */
    public static SignInfo queryToObj(SignInfoQuery signInfoQuery) {
        if (signInfoQuery == null) {
            return null;
        }
        SignInfo signInfo = new SignInfo();
        BeanUtils.copyProperties(signInfoQuery, signInfo);
        return signInfo;
    }
}
