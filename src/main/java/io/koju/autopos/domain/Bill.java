package io.koju.autopos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.koju.autopos.party.domain.Vehicle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Min(value = 0)
    @Column(name = "sub_total", precision=10, scale=2, nullable = false)
    private BigDecimal subTotal;

    @NotNull
    @Min(value = 0)
    @Column(name = "discount", precision=10, scale=2, nullable = false)
    private BigDecimal discount;

    @NotNull
    @Min(value = 0)
    @Column(name = "taxable_amount", precision=10, scale=2, nullable = false)
    private BigDecimal taxableAmount;

    @NotNull
    @Min(value = 0)
    @Column(name = "tax", precision=10, scale=2, nullable = false)
    private BigDecimal tax;

    @NotNull
    @Min(value = 0)
    @Column(name = "grand_total", precision=10, scale=2, nullable = false)
    private BigDecimal grandTotal;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "client", length = 100, nullable = false)
    private String client;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "issued_by", length = 100, nullable = false)
    private String issuedBy;

    @OneToMany(mappedBy = "bill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillItem> billItems = new HashSet<>();

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

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Set<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(Set<BillItem> billItems) {
        this.billItems = billItems;
    }

    public Vehicle getVehicle() {
        return vehicle;
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
        Bill bill = (Bill) o;
        if(bill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", subTotal='" + subTotal + "'" +
            ", discount='" + discount + "'" +
            ", taxableAmount='" + taxableAmount + "'" +
            ", tax='" + tax + "'" +
            ", grandTotal='" + grandTotal + "'" +
            ", client='" + client + "'" +
            ", remarks='" + remarks + "'" +
            ", issuedBy='" + issuedBy + "'" +
            '}';
    }
}
