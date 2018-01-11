package com.kapilkoju.autopos.trade.purchase.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kapilkoju.autopos.trade.domain.LineItem;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table(name = "purchase_line")
public class PurchaseLine extends LineItem {

    @NotNull
    @Min(value = 0)
    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
