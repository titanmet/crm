package com.ratnikov.crm.enums;

import lombok.Getter;

@Getter
public enum ApplicationUserPermission {
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
}
