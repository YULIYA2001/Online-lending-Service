package com.golubovich.project_trpo_tofi.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USER_CAN_WRITE, Permission.USER_ADMIN_CAN_WRITE)),
    ADMIN(Set.of(Permission.ADMIN_CAN_WRITE, Permission.ADMIN_CAN_READ, Permission.USER_ADMIN_CAN_WRITE)),
    SUPER_ADMIN(Set.of(Permission.ADMIN_CAN_WRITE, Permission.ADMIN_CAN_READ, Permission.SUPER_ADMIN_CAN_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
