package com.lz.manage.model.dto.partitionInfo;

import com.lz.manage.model.domain.PartitionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 分区信息Vo对象 tb_partition_info
 *
 * @author YY
 * @date 2026-05-14
 */
@Data
public class PartitionInfoEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 图书馆
     */
    private Long libraryId;

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

    /**
     * 对象转封装类
     *
     * @param partitionInfoEdit 编辑对象
     * @return PartitionInfo
     */
    public static PartitionInfo editToObj(PartitionInfoEdit partitionInfoEdit) {
        if (partitionInfoEdit == null) {
            return null;
        }
        PartitionInfo partitionInfo = new PartitionInfo();
        BeanUtils.copyProperties(partitionInfoEdit, partitionInfo);
        return partitionInfo;
    }
}
