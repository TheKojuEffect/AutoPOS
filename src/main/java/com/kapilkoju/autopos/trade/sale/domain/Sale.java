package com.kapilkoju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.catalog.domain.SaleStatus;
import com.kapilkoju.autopos.kernel.json.Views;
import com.kapilkoju.autopos.party.domain.Vehicle;
import com.kapilkoju.autopos.trade.domain.LineItem;
import com.kapilkoju.autopos.trade.domain.Trade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sale")
public class Sale extends Trade {

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "buyer")
    private String buyer;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SaleLine> lines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JsonView(Views.Summary.class)
    private SaleStatus status;

    @JsonView(Views.Summary.class)
    public String getClient() {

        if (vehicle != null) {
            return vehicle.getTitle();
        }
        return buyer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public List<SaleLine> getLines() {
        return lines;
    }

    public void setLines(List<SaleLine> lines) {
        this.lines = lines;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    @JsonView(Views.Summary.class)
    public BigDecimal getGrandTotal() {
        return lines.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal::add)
                .map(t -> t.subtract(getDiscount()))
                .orElse(BigDecimal.ZERO);
    }

    @PrePersist
    @PreUpdate
    public void normalizeClient() {
        if (vehicle != null) {
            buyer = null;
        }
    }

}
