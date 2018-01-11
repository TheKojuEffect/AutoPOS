package com.kapilkoju.autopos.trade.domain;

import com.kapilkoju.autopos.catalog.domain.Item;
import com.kapilkoju.autopos.kernel.domain.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class LineItem extends AuditableEntity {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    @Min(value = 1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    public BigDecimal getAmount() {
        return getRate().multiply(new BigDecimal(quantity));
    }

    public abstract BigDecimal getRate();

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
