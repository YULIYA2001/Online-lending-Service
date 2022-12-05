package com.golubovich.project_trpo_tofi.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.CREDITS_READ)),
    ADMIN(Set.of(Permission.CREDITS_READ, Permission.CREDITS_WRITE, Permission.ADMIN_READ)),
    SUPER_ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_CHANGE));

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
