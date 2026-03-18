package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 签到类型枚举
 * dict_type: manage_sign_type
 */
@Getter
public enum ManageSignTypeEnum {

    MANAGE_SIGN_TYPE_1("1", "签到"),
    MANAGE_SIGN_TYPE_2("2", "签退");

    private final String value;
    private final String label;

    ManageSignTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageSignTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageSignTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageSignTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
