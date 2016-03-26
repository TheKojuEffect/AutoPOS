package io.koju.autopos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DayBookEntry.
 */
@Entity
@Table(name = "day_book_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DayBookEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Min(value = 0)
    @Column(name = "incoming_amount", precision=10, scale=2, nullable = false)
    private BigDecimal incomingAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "outgoing_amount", precision=10, scale=2, nullable = false)
    private BigDecimal outgoingAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "misc_expenses", precision=10, scale=2, nullable = false)
    private BigDecimal miscExpenses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncomingAmount() {
        return incomingAmount;
    }

    public void setIncomingAmount(BigDecimal incomingAmount) {
        this.incomingAmount = incomingAmount;
    }

    public BigDecimal getOutgoingAmount() {
        return outgoingAmount;
    }

    public void setOutgoingAmount(BigDecimal outgoingAmount) {
        this.outgoingAmount = outgoingAmount;
    }

    public BigDecimal getMiscExpenses() {
        return miscExpenses;
    }

    public void setMiscExpenses(BigDecimal miscExpenses) {
        this.miscExpenses = miscExpenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DayBookEntry dayBookEntry = (DayBookEntry) o;
        if(dayBookEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dayBookEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DayBookEntry{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", incomingAmount='" + incomingAmount + "'" +
            ", outgoingAmount='" + outgoingAmount + "'" +
            ", miscExpenses='" + miscExpenses + "'" +
            '}';
    }
}
