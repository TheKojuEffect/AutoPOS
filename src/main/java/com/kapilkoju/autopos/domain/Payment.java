package com.kapilkoju.autopos.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {

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
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Size(max = 10)
    @Column(name = "receipt_number", length = 10)
    private String receiptNumber;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "paid_by", length = 100, nullable = false)
    private String paidBy;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    private Vendor paidTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Payment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Payment amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public Payment receiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        return this;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public Payment paidBy(String paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public Payment remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Vendor getPaidTo() {
        return paidTo;
    }

    public Payment paidTo(Vendor vendor) {
        this.paidTo = vendor;
        return this;
    }

    public void setPaidTo(Vendor vendor) {
        this.paidTo = vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if (payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", amount='" + amount + "'" +
            ", receiptNumber='" + receiptNumber + "'" +
            ", paidBy='" + paidBy + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
