package com.lz.manage.model.dto.libraryInfo;

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
import com.lz.manage.model.domain.LibraryInfo;
/**
 * 图书馆信息Query对象 tb_library_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class LibraryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param libraryInfoQuery 查询对象
     * @return LibraryInfo
     */
    public static LibraryInfo queryToObj(LibraryInfoQuery libraryInfoQuery) {
        if (libraryInfoQuery == null) {
            return null;
        }
        LibraryInfo libraryInfo = new LibraryInfo();
        BeanUtils.copyProperties(libraryInfoQuery, libraryInfo);
        return libraryInfo;
    }
}
