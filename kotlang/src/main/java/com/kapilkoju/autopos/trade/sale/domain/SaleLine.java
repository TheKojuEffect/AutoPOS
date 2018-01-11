package com.kapilkoju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kapilkoju.autopos.trade.domain.LineItem;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table(name = "sale_line")
public class SaleLine extends LineItem {

    @NotNull
    @Min(value = 0)
    @Column(name = "rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal rate;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id")
    @JsonBackReference
    private Sale sale;

    @Column(name = "buyer")
    private String buyer;

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
