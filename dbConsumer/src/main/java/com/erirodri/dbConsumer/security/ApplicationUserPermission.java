package com.erirodri.dbConsumer.security;

public enum ApplicationUserPermission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    APIEXTERNAL_READ("apiExternal:read");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }

}
