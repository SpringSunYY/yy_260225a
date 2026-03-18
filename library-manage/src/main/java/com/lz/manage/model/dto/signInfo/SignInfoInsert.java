package com.lz.manage.model.dto.signInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SignInfo;
/**
 * 签到信息Vo对象 tb_sign_info
 *
 * @author YY
 * @date 2026-03-18
 */
@Data
public class SignInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

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

    /**
     * 对象转封装类
     *
     * @param signInfoInsert 插入对象
     * @return SignInfoInsert
     */
    public static SignInfo insertToObj(SignInfoInsert signInfoInsert) {
        if (signInfoInsert == null) {
            return null;
        }
        SignInfo signInfo = new SignInfo();
        BeanUtils.copyProperties(signInfoInsert, signInfo);
        return signInfo;
    }
}
