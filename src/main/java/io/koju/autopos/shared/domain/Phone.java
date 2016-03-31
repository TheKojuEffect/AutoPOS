package io.koju.autopos.shared.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "phone")
@Getter
@Setter
public class Phone extends AuditableBaseEntity {

    private static final String ID_SEQ = "phone_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @Size(min = 7, max = 10)
    @Column(name = "number", length = 10, nullable = false, unique = true)
    private String number;

}
