package com.golubovich.project_trpo_tofi.model;

public enum Permission {
    CREDITS_READ("credits:read"),
    CREDITS_WRITE("credits:write"),
    ADMIN_READ("admin:read"),
    ADMIN_CHANGE("admin:change");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
