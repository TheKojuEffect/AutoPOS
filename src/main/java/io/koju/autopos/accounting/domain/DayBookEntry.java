package io.koju.autopos.accounting.domain;

import io.koju.autopos.kernel.domain.AuditableBaseEntity;
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
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "day_book_entry")
@Getter
@Setter
public class DayBookEntry extends AuditableBaseEntity {

    private static final String ID_SEQ = "day_book_entry_id_seq";

    @Id
    @SequenceGenerator(name = ID_SEQ, sequenceName = ID_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = ID_SEQ)
    private Long id;

    @NotNull
    @Column(name = "txn_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Min(value = 0)
    @Column(name = "incoming_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal incomingAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "outgoing_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal outgoingAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "misc_expenses", precision = 10, scale = 2, nullable = false)
    private BigDecimal miscExpenses = BigDecimal.ZERO;

    @Size(max = 500)
    @Column(name = "remarks", length = 500)
    private String remarks;

    @Override
    public Long getId() {
        return id;
    }

    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }
}
