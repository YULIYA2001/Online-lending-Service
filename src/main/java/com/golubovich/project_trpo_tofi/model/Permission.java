package com.golubovich.project_trpo_tofi.model;

public enum Permission {
    USER_CAN_WRITE("user:write"),
    ADMIN_CAN_READ("admin:read"),
    ADMIN_CAN_WRITE("admin:write"),
    SUPER_ADMIN_CAN_WRITE("superAdmin:write"),
    USER_ADMIN_CAN_WRITE("user_admin:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
