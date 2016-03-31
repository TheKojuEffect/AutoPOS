package io.koju.autopos.party.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
import io.koju.autopos.shared.domain.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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
    private String name;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @OneToMany(cascade = ALL)
    @JoinTable(name = "vendor_phone",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private List<Phone> phones = new ArrayList<>();

}
