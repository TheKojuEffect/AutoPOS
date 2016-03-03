package io.koju.autopos.user.domain;

import io.koju.autopos.shared.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends BaseEntity {

    @NotNull
    @Column(name = "role_name", length = 40, unique = true, nullable = false) // role is reserved DB keyword
    @Enumerated(STRING)
    private Role role;

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
