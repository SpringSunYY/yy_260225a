package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 座位状态枚举
 * 对应字典类型：manage_seat_status
 */
@Getter
public enum ManageSeatStatusEnum {

    MANAGE_SEAT_STATUS_0("0", "不可预约"),
    MANAGE_SEAT_STATUS_1("1", "正常"),
    MANAGE_SEAT_STATUS_2("2", "已预约");

    private final String value;
    private final String label;

    ManageSeatStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageSeatStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageSeatStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageSeatStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
