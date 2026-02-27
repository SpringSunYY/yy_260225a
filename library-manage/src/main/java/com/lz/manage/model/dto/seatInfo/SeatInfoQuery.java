package com.lz.manage.model.dto.seatInfo;

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
import com.lz.manage.model.domain.SeatInfo;
/**
 * 座位信息Query对象 tb_seat_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class SeatInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 图书馆 */
    private Long libraryId;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

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
     * @param seatInfoQuery 查询对象
     * @return SeatInfo
     */
    public static SeatInfo queryToObj(SeatInfoQuery seatInfoQuery) {
        if (seatInfoQuery == null) {
            return null;
        }
        SeatInfo seatInfo = new SeatInfo();
        BeanUtils.copyProperties(seatInfoQuery, seatInfo);
        return seatInfo;
    }
}
