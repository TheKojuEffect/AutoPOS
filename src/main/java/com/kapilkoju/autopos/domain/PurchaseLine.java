package com.kapilkoju.autopos.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A PurchaseLine.
 */
@Entity
@Table(name = "purchase_line")
public class PurchaseLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "rate", precision=10, scale=2, nullable = false)
    private BigDecimal rate;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    private Purchase purchase;

    @ManyToOne(optional = false)
    @NotNull
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseLine quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public PurchaseLine rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRemarks() {
        return remarks;
    }

    public PurchaseLine remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public PurchaseLine purchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Item getItem() {
        return item;
    }

    public PurchaseLine item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseLine purchaseLine = (PurchaseLine) o;
        if (purchaseLine.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, purchaseLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PurchaseLine{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", rate='" + rate + "'" +
            ", remarks='" + remarks + "'" +
            '}';
    }
}
