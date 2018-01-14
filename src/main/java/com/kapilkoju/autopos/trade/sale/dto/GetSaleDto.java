package com.kapilkoju.autopos.trade.sale.dto;

import com.kapilkoju.autopos.catalog.domain.SaleStatus;

// TODO: Replace this with kotlin data class with spring 5 https://jira.spring.io/browse/SPR-15199
public class GetSaleDto {

    private SaleStatus status;

    private boolean vat;

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public boolean getVat() {
        return vat;
    }

    public void setVat(boolean vat) {
        this.vat = vat;
    }
}
