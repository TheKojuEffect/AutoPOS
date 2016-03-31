package io.koju.autopos.user.domain;

import io.koju.autopos.kernel.domain.AbstractBaseEntity;
import io.koju.autopos.user.domain.Role.RoleConverter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends AbstractBaseEntity {

    @Id
    @SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", initialValue = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "authority_id_seq")
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
