package com.kapilkoju.autopos.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

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
    @Column(name = "received_by", length = 100, nullable = false)
    private String receivedBy;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    private Customer receivedFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Receipt date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Receipt amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public Receipt receiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        return this;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public Receipt receivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
        return this;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public Receipt remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Customer getReceivedFrom() {
        return receivedFrom;
    }

    public Receipt receivedFrom(Customer customer) {
        this.receivedFrom = customer;
        return this;
    }

    public void setReceivedFrom(Customer customer) {
        this.receivedFrom = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) o;
        if (receipt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, receipt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", amount='" + amount + "'" +
            ", receiptNumber='" + receiptNumber + "'" +
            ", receivedBy='" + receivedBy + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
