package com.lz.manage.model.dto.seatInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SeatInfo;
/**
 * 座位信息Vo对象 tb_seat_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class SeatInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 图书馆 */
    private Long libraryId;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 图片 */
    private String image;

    /** 描述 */
    private String description;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param seatInfoInsert 插入对象
     * @return SeatInfoInsert
     */
    public static SeatInfo insertToObj(SeatInfoInsert seatInfoInsert) {
        if (seatInfoInsert == null) {
            return null;
        }
        SeatInfo seatInfo = new SeatInfo();
        BeanUtils.copyProperties(seatInfoInsert, seatInfo);
        return seatInfo;
    }
}
