package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 预约状态枚举
 * 对应字典类型：manage_appointment_status
 */
@Getter
public enum ManageAppointmentStatusEnum {

    MANAGE_APPOINTMENT_STATUS_1("1", "成功预约"),
    MANAGE_APPOINTMENT_STATUS_2("2", "进行中"),
    MANAGE_APPOINTMENT_STATUS_3("3", "已过期");

    private final String value;
    private final String label;

    ManageAppointmentStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageAppointmentStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageAppointmentStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageAppointmentStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
