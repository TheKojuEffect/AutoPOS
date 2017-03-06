package com.kapilkoju.autopos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Purchase.
 */
@Entity
@Table(name = "purchase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Purchase implements Serializable {

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

    @ManyToOne
    private Vendor vendor;

    @OneToMany(mappedBy = "purchase")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PurchaseLine> purchaseLines = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Purchase date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Purchase invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Purchase discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getRemarks() {
        return remarks;
    }

    public Purchase remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Purchase vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Set<PurchaseLine> getPurchaseLines() {
        return purchaseLines;
    }

    public Purchase purchaseLines(Set<PurchaseLine> purchaseLines) {
        this.purchaseLines = purchaseLines;
        return this;
    }

    public Purchase addPurchaseLine(PurchaseLine purchaseLine) {
        this.purchaseLines.add(purchaseLine);
        purchaseLine.setPurchase(this);
        return this;
    }

    public Purchase removePurchaseLine(PurchaseLine purchaseLine) {
        this.purchaseLines.remove(purchaseLine);
        purchaseLine.setPurchase(null);
        return this;
    }

    public void setPurchaseLines(Set<PurchaseLine> purchaseLines) {
        this.purchaseLines = purchaseLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Purchase purchase = (Purchase) o;
        if (purchase.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Purchase{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", invoiceNumber='" + invoiceNumber + "'" +
            ", discount='" + discount + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
