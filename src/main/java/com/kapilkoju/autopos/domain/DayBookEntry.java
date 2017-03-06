package com.kapilkoju.autopos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "incoming_amount", precision=10, scale=2, nullable = false)
    private BigDecimal incomingAmount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "outgoing_amount", precision=10, scale=2, nullable = false)
    private BigDecimal outgoingAmount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "misc_expenses", precision=10, scale=2, nullable = false)
    private BigDecimal miscExpenses;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public DayBookEntry date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncomingAmount() {
        return incomingAmount;
    }

    public DayBookEntry incomingAmount(BigDecimal incomingAmount) {
        this.incomingAmount = incomingAmount;
        return this;
    }

    public void setIncomingAmount(BigDecimal incomingAmount) {
        this.incomingAmount = incomingAmount;
    }

    public BigDecimal getOutgoingAmount() {
        return outgoingAmount;
    }

    public DayBookEntry outgoingAmount(BigDecimal outgoingAmount) {
        this.outgoingAmount = outgoingAmount;
        return this;
    }

    public void setOutgoingAmount(BigDecimal outgoingAmount) {
        this.outgoingAmount = outgoingAmount;
    }

    public BigDecimal getMiscExpenses() {
        return miscExpenses;
    }

    public DayBookEntry miscExpenses(BigDecimal miscExpenses) {
        this.miscExpenses = miscExpenses;
        return this;
    }

    public void setMiscExpenses(BigDecimal miscExpenses) {
        this.miscExpenses = miscExpenses;
    }

    public String getRemarks() {
        return remarks;
    }

    public DayBookEntry remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        if (dayBookEntry.id == null || id == null) {
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
            ", remarks='" + remarks + "'" +
            '}';
    }
}
