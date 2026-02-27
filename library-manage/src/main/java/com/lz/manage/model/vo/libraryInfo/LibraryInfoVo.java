package com.lz.manage.model.vo.libraryInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.LibraryInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书馆信息Vo对象 tb_library_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class LibraryInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

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
     * @param libraryInfo LibraryInfo实体对象
     * @return LibraryInfoVo
     */
    public static LibraryInfoVo objToVo(LibraryInfo libraryInfo) {
        if (libraryInfo == null) {
            return null;
        }
        LibraryInfoVo libraryInfoVo = new LibraryInfoVo();
        BeanUtils.copyProperties(libraryInfo, libraryInfoVo);
        return libraryInfoVo;
    }
}
