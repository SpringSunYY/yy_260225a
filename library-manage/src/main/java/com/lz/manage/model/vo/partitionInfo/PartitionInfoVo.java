package com.lz.manage.model.vo.partitionInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PartitionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 分区信息Vo对象 tb_partition_info
 *
 * @author YY
 * @date 2026-05-14
 */
@Data
public class PartitionInfoVo implements Serializable {
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
     * @param partitionInfo PartitionInfo实体对象
     * @return PartitionInfoVo
     */
    public static PartitionInfoVo objToVo(PartitionInfo partitionInfo) {
        if (partitionInfo == null) {
            return null;
        }
        PartitionInfoVo partitionInfoVo = new PartitionInfoVo();
        BeanUtils.copyProperties(partitionInfo, partitionInfoVo);
        return partitionInfoVo;
    }
}
