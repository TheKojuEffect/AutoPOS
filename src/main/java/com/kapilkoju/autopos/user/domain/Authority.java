package com.kapilkoju.autopos.user.domain;

import com.kapilkoju.autopos.kernel.domain.AbstractBaseEntity;
import com.kapilkoju.autopos.user.domain.Role.RoleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authority")
public class Authority extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "role_name", length = 40, unique = true, nullable = false) // role is reserved DB keyword
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Override
    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return role.getName();
    }
}
