package io.koju.autopos.catalog.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "category")
@Getter
@Setter
public class Category extends AuditableBaseEntity {

    @Id
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "category_id_seq")
    private Long id;

    @NotNull
    @Size(min = 2, max = 3)
    @Column(name = "short_name", length = 3, nullable = false)
    private String shortName;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @PrePersist
    @PreUpdate
    void capitalizeShortName() {
        shortName = shortName.toUpperCase();
    }
}
