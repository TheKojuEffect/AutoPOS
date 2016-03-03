package io.koju.autopos.user.domain;

import io.koju.autopos.shared.domain.AbstractEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", initialValue = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "authority_id_seq")
    private Long id;

    @NotNull
    @Column(name = "role_name", length = 40, unique = true, nullable = false) // role is reserved DB keyword
    @Enumerated(STRING)
    private Role role;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
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
