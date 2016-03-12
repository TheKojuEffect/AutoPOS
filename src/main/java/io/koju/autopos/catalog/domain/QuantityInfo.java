package io.koju.autopos.catalog.domain;

import io.koju.autopos.shared.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Entity
@Getter
@Setter
@Table(name = "quantity_info")
public class QuantityInfo extends AuditableBaseEntity {

    private final static String ID_SEQ = "quantity_info_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    @Setter(NONE)
    private Long id;

    @NotNull
    @Min(0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

}
