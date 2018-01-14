package com.kapilkoju.autopos.trade.purchase.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.domain.AuditableEntity;
import com.kapilkoju.autopos.kernel.json.Views;
import com.kapilkoju.autopos.party.domain.Vendor;
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
@Table(name = "purchase")
public class Purchase extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonView(Views.Summary.class)
    private Vendor vendor;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PurchaseLine> lines = new ArrayList<>();

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

    @Column(name = "vat", nullable = false)
    private boolean vat;

    public boolean getVat() {
        return vat;
    }

    public void setVat(boolean vat) {
        this.vat = vat;
    }

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

    @JsonView(Views.Summary.class)
    public BigDecimal getGrandTotal() {
        return lines.stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal::add)
                .map(t -> t.subtract(getDiscount()))
                .orElse(BigDecimal.ZERO);
    }

}
