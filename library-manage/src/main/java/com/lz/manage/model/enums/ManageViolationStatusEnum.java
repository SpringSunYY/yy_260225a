package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 违规状态枚举
 * 对应字典类型：manage_violation_status
 */
@Getter
public enum ManageViolationStatusEnum {

    MANAGE_VIOLATION_STATUS_1("1", "处罚中"),
    MANAGE_VIOLATION_STATUS_2("2", "处罚结束");

    private final String value;
    private final String label;

    ManageViolationStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageViolationStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageViolationStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageViolationStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
