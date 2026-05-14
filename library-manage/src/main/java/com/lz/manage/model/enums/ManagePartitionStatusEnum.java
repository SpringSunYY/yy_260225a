package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum ManagePartitionStatusEnum {

    MANAGE_PARTITION_STATUS_0("0", "不可预约"),
    MANAGE_PARTITION_STATUS_1("1", "正常");

    private final String value;
    private final String label;

    ManagePartitionStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManagePartitionStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManagePartitionStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManagePartitionStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
