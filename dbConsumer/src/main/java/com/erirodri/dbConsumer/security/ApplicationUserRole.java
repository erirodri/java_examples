package com.erirodri.dbConsumer.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.erirodri.dbConsumer.security.ApplicationUserPermission.*;

//Se utiliza enum para definir constantes a utilizar en diferentes clases.
public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(USERS_WRITE, USERS_READ)),
    STUDENT(Sets.newHashSet(USERS_READ)),
    APIEXTERNAL(Sets.newHashSet(APIEXTERNAL_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
