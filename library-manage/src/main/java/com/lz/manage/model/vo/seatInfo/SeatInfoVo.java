package com.lz.manage.model.vo.seatInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SeatInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 座位信息Vo对象 tb_seat_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class SeatInfoVo implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

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
     * @param seatInfo SeatInfo实体对象
     * @return SeatInfoVo
     */
    public static SeatInfoVo objToVo(SeatInfo seatInfo) {
        if (seatInfo == null) {
            return null;
        }
        SeatInfoVo seatInfoVo = new SeatInfoVo();
        BeanUtils.copyProperties(seatInfo, seatInfoVo);
        return seatInfoVo;
    }
}
