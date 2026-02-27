package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 场馆状态枚举
 * 对应字典类型：manage_library_status
 */
@Getter
public enum ManageLibraryStatusEnum {

    MANAGE_LIBRARY_STATUS_1("1", "正常"),
    MANAGE_LIBRARY_STATUS_2("2", "闭馆");

    private final String value;
    private final String label;

    ManageLibraryStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageLibraryStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageLibraryStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageLibraryStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
