package com.kapilkoju.autopos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kapilkoju.autopos.domain.enumeration.SaleStatus;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Size(min = 1, max = 50)
    @Column(name = "invoice_number", length = 50)
    private String invoiceNumber;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "discount", precision=10, scale=2, nullable = false)
    private BigDecimal discount;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @Size(min = 2, max = 50)
    @Column(name = "buyer", length = 50)
    private String buyer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SaleStatus status;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "sale")
    @JsonIgnore
    private Set<SaleLine> lines = new HashSet<>();

    @ManyToOne
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Sale date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Sale invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Sale discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getRemarks() {
        return remarks;
    }

    public Sale remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBuyer() {
        return buyer;
    }

    public Sale buyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public Sale status(SaleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Sale customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<SaleLine> getLines() {
        return lines;
    }

    public Sale lines(Set<SaleLine> saleLines) {
        this.lines = saleLines;
        return this;
    }

    public Sale addLine(SaleLine saleLine) {
        this.lines.add(saleLine);
        saleLine.setSale(this);
        return this;
    }

    public Sale removeLine(SaleLine saleLine) {
        this.lines.remove(saleLine);
        saleLine.setSale(null);
        return this;
    }

    public void setLines(Set<SaleLine> saleLines) {
        this.lines = saleLines;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Sale vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sale sale = (Sale) o;
        if (sale.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sale{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", invoiceNumber='" + invoiceNumber + "'" +
            ", discount='" + discount + "'" +
            ", remarks='" + remarks + "'" +
            ", buyer='" + buyer + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
