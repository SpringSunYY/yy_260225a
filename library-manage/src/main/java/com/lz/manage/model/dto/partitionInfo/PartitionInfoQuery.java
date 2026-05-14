package com.lz.manage.model.dto.partitionInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.manage.model.domain.PartitionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 分区信息Query对象 tb_partition_info
 *
 * @author YY
 * @date 2026-05-14
 */
@Data
public class PartitionInfoQuery implements Serializable {
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
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param partitionInfoQuery 查询对象
     * @return PartitionInfo
     */
    public static PartitionInfo queryToObj(PartitionInfoQuery partitionInfoQuery) {
        if (partitionInfoQuery == null) {
            return null;
        }
        PartitionInfo partitionInfo = new PartitionInfo();
        BeanUtils.copyProperties(partitionInfoQuery, partitionInfo);
        return partitionInfo;
    }
}
