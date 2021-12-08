package com.ratnikov.crm.enums;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ratnikov.crm.enums.ApplicationUserPermission.CUSTOMER_READ;
import static com.ratnikov.crm.enums.ApplicationUserPermission.CUSTOMER_WRITE;

@Getter
public enum UserRole {
    EMPLOYEE(Sets.newHashSet(
            CUSTOMER_READ)
    ),
    MANAGER(Sets.newHashSet(
            CUSTOMER_READ,
            CUSTOMER_WRITE)
    ),
    ADMIN(Sets.newHashSet(
            CUSTOMER_READ,
            CUSTOMER_WRITE)
    );

    private final Set<ApplicationUserPermission> permissions;

    UserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}