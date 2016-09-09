package io.koju.autopos.party.domain;

import com.fasterxml.jackson.annotation.JsonView;
import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import io.koju.autopos.kernel.json.Views;
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
@Table(name = "vendor")
@Getter
@Setter
public class Vendor extends AuditableBaseEntity {

    private static final String ID_SEQ = "vendor_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    @JsonView(Views.Summary.class)
    private String name;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

}
