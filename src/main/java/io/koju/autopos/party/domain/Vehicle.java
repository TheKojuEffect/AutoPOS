package io.koju.autopos.party.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class Vehicle extends AuditableBaseEntity {

    private static final String ID_SEQ = "vehicle_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "number", length = 20, nullable = false, unique = true)
    private String number;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne
    private Customer owner;

}
