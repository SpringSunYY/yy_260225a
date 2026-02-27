package com.lz.manage.model.dto.libraryInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.LibraryInfo;
/**
 * 图书馆信息Vo对象 tb_library_info
 *
 * @author YY
 * @date 2026-02-27
 */
@Data
public class LibraryInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param libraryInfoEdit 编辑对象
     * @return LibraryInfo
     */
    public static LibraryInfo editToObj(LibraryInfoEdit libraryInfoEdit) {
        if (libraryInfoEdit == null) {
            return null;
        }
        LibraryInfo libraryInfo = new LibraryInfo();
        BeanUtils.copyProperties(libraryInfoEdit, libraryInfo);
        return libraryInfo;
    }
}
