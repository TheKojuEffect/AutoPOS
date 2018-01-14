package com.kapilkoju.autopos.trade.sale.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.catalog.domain.SaleStatus;
import com.kapilkoju.autopos.kernel.domain.AuditableEntity;
import com.kapilkoju.autopos.kernel.json.Views;
import com.kapilkoju.autopos.party.domain.Vehicle;
import com.kapilkoju.autopos.trade.domain.LineItem;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sale")
public class Sale extends AuditableEntity {

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

    @NotNull
    @Column(name = "date", nullable = false)
    @JsonView(Views.Summary.class)
    private Instant date;

    @NotNull
    @Min(value = 0)
    @Column(name = "discount", precision = 10, scale = 2, nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    @Size(min = 1, max = 50)
    @Column(name = "invoice_number", length = 50)
    @JsonView(Views.Summary.class)
    private String invoiceNumber;

    @Size(max = 250)
    @Column(name = "remarks", length = 250)
    private String remarks;

    @JsonView(Views.Summary.class)
    public String getClient() {

        if (vehicle != null) {
            return vehicle.getTitle();
        }
        return buyer;
    }

    @Column(name = "vat", nullable = false)
    private boolean vat;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean getVat() {
        return vat;
    }

    public void setVat(boolean vat) {
        this.vat = vat;
    }
}
