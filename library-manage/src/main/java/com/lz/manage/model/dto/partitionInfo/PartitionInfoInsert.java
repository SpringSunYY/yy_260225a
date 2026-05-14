package com.lz.manage.model.dto.partitionInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PartitionInfo;
/**
 * 分区信息Vo对象 tb_partition_info
 *
 * @author YY
 * @date 2026-05-14
 */
@Data
public class PartitionInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

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
     * @param partitionInfoInsert 插入对象
     * @return PartitionInfoInsert
     */
    public static PartitionInfo insertToObj(PartitionInfoInsert partitionInfoInsert) {
        if (partitionInfoInsert == null) {
            return null;
        }
        PartitionInfo partitionInfo = new PartitionInfo();
        BeanUtils.copyProperties(partitionInfoInsert, partitionInfo);
        return partitionInfo;
    }
}
