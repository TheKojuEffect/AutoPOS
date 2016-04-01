package io.koju.autopos.trade.domain;

import io.koju.autopos.catalog.domain.Item;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BillItem.
 */
@Entity
@Table(name = "bill_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "rate", precision=10, scale=2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @Min(value = 0)
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @Size(min = 2, max = 100)
    @Column(name = "issued_by", length = 100)
    private String issuedBy;

    @ManyToOne
    private Item particular;

    @ManyToOne
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
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

    public Item getParticular() {
        return particular;
    }

    public void setParticular(Item item) {
        this.particular = item;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BillItem billItem = (BillItem) o;
        if(billItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, billItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillItem{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", rate='" + rate + "'" +
            ", amount='" + amount + "'" +
            ", date='" + date + "'" +
            ", remarks='" + remarks + "'" +
            ", issuedBy='" + issuedBy + "'" +
            '}';
    }
}
