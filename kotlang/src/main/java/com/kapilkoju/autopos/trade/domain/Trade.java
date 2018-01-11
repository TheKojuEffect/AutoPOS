package com.kapilkoju.autopos.trade.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.kapilkoju.autopos.kernel.domain.AuditableEntity;
import com.kapilkoju.autopos.kernel.json.Views;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@MappedSuperclass
public abstract class Trade extends AuditableEntity {

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

}
