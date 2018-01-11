package com.kapilkoju.autopos.trade.purchase.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.json.Views;
import com.kapilkoju.autopos.party.domain.Vendor;
import com.kapilkoju.autopos.trade.domain.LineItem;
import com.kapilkoju.autopos.trade.domain.Trade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "purchase")
public class Purchase extends Trade {

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonView(Views.Summary.class)
    private Vendor vendor;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PurchaseLine> lines = new ArrayList<>();

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<PurchaseLine> getLines() {
        return lines;
    }

    public void setLines(List<PurchaseLine> lines) {
        this.lines = lines;
    }

    @JsonView(Views.Summary.class)
    public BigDecimal getGrandTotal() {
        return lines.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal::add)
                .map(t -> t.subtract(getDiscount()))
                .orElse(BigDecimal.ZERO);
    }

}
